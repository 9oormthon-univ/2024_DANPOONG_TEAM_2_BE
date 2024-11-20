package com.moa.moabackend.store.application;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moa.moabackend.store.api.dto.request.StoreReqDto;
import com.moa.moabackend.store.api.dto.response.AddressResDto;
import com.moa.moabackend.store.domain.Store;
import com.moa.moabackend.store.domain.StoreImage;
import com.moa.moabackend.store.domain.StoreLocation;
import com.moa.moabackend.store.domain.repository.StoreImageRepository;
import com.moa.moabackend.store.domain.repository.StoreLocationRepository;
import com.moa.moabackend.store.domain.repository.StoreRepository;
import com.moa.moabackend.store.exception.RevGeocodeNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class StoreService {
    private final StoreRepository storeRepository;
    private final StoreImageRepository storeImageRepository;
    private final StoreLocationRepository storeLocationRepository;

    @Value("${KAKAO_API_KEY}")
    private String kakaoApiKey;

    public StoreService(StoreRepository storeRepository, StoreImageRepository storeImageRepository,
            StoreLocationRepository storeLocationRepository) {
        this.storeRepository = storeRepository;
        this.storeImageRepository = storeImageRepository;
        this.storeLocationRepository = storeLocationRepository;
    }

    @Transactional
    public Store createStore(StoreReqDto payload) throws JsonProcessingException, IOException, InterruptedException {
        Double x = payload.x();
        Double y = payload.y();

        // 1-1. 상점 객체 빌드
        Store newStore = Store.builder().name(payload.name()).category(payload.category())
                .profileImage(payload.profileImage()).caption(payload.caption()).fundingCurrent(0)
                .fundingTarget(payload.fundingTarget()).content(payload.content()).build();

        // 1-2. 상점 등록
        Store store = storeRepository.save(newStore);

        // 2-1. 상점 이미지 정보를 순차적으로 저장
        payload.images().stream().forEach(imageUrl -> {
            StoreImage newStoreImage = StoreImage.builder().imageUrl(imageUrl).store(store).build();
            storeImageRepository.save(newStoreImage);
        });

        // 3-1. 상점 좌표 도로명주소로 변환
        String roadAddress = this.coord2Address(x, y);

        // 3-2. 상점 좌표 정보 저장
        StoreLocation storeLocation = StoreLocation.builder().address(roadAddress).x(x).y(y).store(store).build();
        storeLocationRepository.save(storeLocation);

        return store;
    }

    public Store getStore(Long storeId) {
        // ID로 상점 찾기
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new EntityNotFoundException("ID가 일치하는 상점을 찾을 수 없습니다."));

        return store;
    }

    @Transactional
    public void updateStore(Long storeId, StoreReqDto payload)
            throws JsonProcessingException, IOException, InterruptedException {
        // TODO(security): 소유자만 상점 정보 변경할 수 있도록 변경

        // 1. 받은 storeId로 상점 존재 검사 및 객체 확보
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new EntityNotFoundException("ID가 일치하는 상점을 찾을 수 없습니다."));

        // 2-1. 받은 상점 정보로 기존 상점 정보 업데이트
        storeRepository.updateStoreInfo(storeId, payload.name() != null ? payload.name() : store.getName(),
                payload.category() != null ? payload.category() : store.getCategory(),
                payload.profileImage() != null ? payload.profileImage() : store.getProfileImage(),
                payload.caption() != null ? payload.caption() : store.getCaption(),
                payload.content() != null ? payload.content() : store.getContent(),
                payload.fundingTarget() != null ? payload.fundingTarget() : store.getFundingTarget());

        if (payload.images() != null) {
            // 3-1. 기존 상점 이미지 정보 제거
            storeImageRepository.deleteByStore_Id(storeId);

            // 3-2. 업데이트를 희망하는 상점 이미지 정보를 순차적으로 저장
            payload.images().stream().forEach(imageUrl -> {
                StoreImage newStoreImage = StoreImage.builder().imageUrl(imageUrl).store(store).build();
                storeImageRepository.save(newStoreImage);
            });
        }

        if (payload.x() != null && payload.y() != null) {
            // 4-1. 상점 좌표 도로명주소로 변환
            String roadAddress = this.coord2Address(payload.x(), payload.y());

            // 4-2. 상점 좌표 정보 저장
            storeLocationRepository.deleteByStoreId(storeId);
            StoreLocation storeLocation = StoreLocation.builder().address(roadAddress).x(payload.x()).y(payload.y())
                    .store(store)
                    .build();
            storeLocationRepository.save(storeLocation);
        }

    }

    public void deleteStore(Long storeId) {
        Store store = getStore(storeId);
        storeRepository.delete(store);
    }

    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }

    public List<Store> getStoresByLocation(Long radius, Double x, Double y) {
        // 1. 좌표 주변에 있는 상점들의 ID를 가져옴
        List<StoreLocation> storeIdList = storeLocationRepository.findStoresWithinDistance(x, y, radius)
                .orElseThrow(() -> new EntityNotFoundException("ID가 일치하는 상점을 찾을 수 없습니다."));

        // 2. 받아온 상점 좌표 객체들을 다시 상점 객체로 변환 후 저장
        List<Store> result = new ArrayList<Store>();
        for (StoreLocation storeLocation : storeIdList) {
            result.add(storeLocation.getStore());
        }

        // 3. 좌표 주변에 있는 상점들의 상점 정보 반환
        return result;
    }

    public String coord2Address(Double x, Double y)
            throws IOException, InterruptedException, JsonProcessingException {
        System.out.println("x:" + x + ", y:" + y);
        // 1. 카카오맵 API 요청 (리버스 지오코딩)
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(
                        String.format(
                                "https://dapi.kakao.com/v2/local/geo/coord2address.json?input_coord=WGS84&x=%s&y=%s", x,
                                y)))
                .header("Authorization", "KakaoAK " + kakaoApiKey)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.body());

        // 2. 받은 응답을 파싱, DTO 불일치하면 JsonProcessingException 예외 발생
        ObjectMapper objectMapper = new ObjectMapper();
        AddressResDto addressResponse = objectMapper.readValue(response.body(), AddressResDto.class);

        List<AddressResDto.Document> documents = addressResponse.documents();

        // 3. 파싱된 객체에서 도로명주소 반환
        if (documents.size() < 1) {
            throw new RevGeocodeNotFoundException();
        }
        return documents.get(0).road_address().address_name();
    }
}
