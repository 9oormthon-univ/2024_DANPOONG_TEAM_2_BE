package com.moa.moabackend.store.application;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moa.moabackend.store.api.dto.request.SearchByLocationReqDto;
import com.moa.moabackend.store.api.dto.request.StoreReqDto;
import com.moa.moabackend.store.api.dto.response.AddressResDto;
import com.moa.moabackend.store.api.dto.response.StoreResDto;
import com.moa.moabackend.store.domain.Store;
import com.moa.moabackend.store.domain.StoreImage;
import com.moa.moabackend.store.domain.StoreLocation;
import com.moa.moabackend.store.domain.repository.StoreFundingRepository;
import com.moa.moabackend.store.domain.repository.StoreImageRepository;
import com.moa.moabackend.store.domain.repository.StoreLocationRepository;
import com.moa.moabackend.store.domain.repository.StoreRepository;
import com.moa.moabackend.store.domain.repository.StoreScrapRepository;
import com.moa.moabackend.store.exception.RevGeocodeNotFoundException;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    private final StoreImageRepository storeImageRepository;
    private final StoreLocationRepository storeLocationRepository;
    private final StoreFundingRepository storeFundingRepository;
    private final StoreScrapRepository storeScrapRepository;

    @Value("${KAKAO_API_KEY}")
    private String kakaoApiKey;

    @Transactional
    public Store createStore(StoreReqDto payload) throws JsonProcessingException, IOException, InterruptedException {
        Double x = payload.x();
        Double y = payload.y();

        // 1-1. 상점 객체 빌드
        Store newStore = Store.builder().name(payload.name()).category(payload.category())
                .profileImage(payload.profileImage()).caption(payload.caption()).fundingCurrent(0)
                .fundingTarget(payload.fundingTarget()).content(payload.content()).startAt(payload.startAt())
                .endAt(payload.endAt()).certifiedType(payload.certifiedType()).build();

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

    public StoreResDto getStore(Long storeId) {
        // ID로 상점 찾기
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new EntityNotFoundException("ID가 일치하는 상점을 찾을 수 없습니다."));

        return makeStoreDto(storeId, store);
    }

    public StoreResDto makeStoreDto(Long storeId, Store store) {
        StoreResDto result = new StoreResDto(
                storeId,
                store.getName(),
                store.getCategory(),
                store.getProfileImage(),
                store.getCaption(),
                store.getFundingTarget(),
                store.getFundingCurrent(),
                store.getStoreImages().stream().map(image -> image.getImageUrl()).collect(Collectors.toList()),
                store.getContent(),
                store.getStoreLocation().getX(),
                store.getStoreLocation().getY(),
                store.getCertifiedType(),
                store.getStartAt(),
                store.getEndAt(),
                storeFundingRepository.countByStore_id(storeId),
                storeScrapRepository.countByStore_id(storeId));

        return result;
    }

    @Transactional
    public void updateStore(Long storeId, StoreReqDto payload)
            throws JsonProcessingException, IOException, InterruptedException {
        // TODO(security): 소유자만 상점 정보 변경할 수 있도록 변경

        // 1. 받은 storeId로 상점 존재 검사 및 객체 확보
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new EntityNotFoundException("ID가 일치하는 상점을 찾을 수 없습니다."));

        // 2-1. 받은 상점 정보로 기존 상점 정보 업데이트
        Store patch = Store.builder().id(storeId).name(payload.name()).category(payload.category())
                .profileImage(payload.profileImage()).certifiedType(payload.certifiedType()).caption(payload.caption())
                .content(payload.content()).fundingTarget(payload.fundingTarget())
                .endAt(payload.endAt()).build();
        store.updateTo(patch);

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
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new EntityNotFoundException("ID가 일치하는 상점을 찾을 수 없습니다."));
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

    public List<StoreResDto> searchStoreByAddress(SearchByLocationReqDto payload) {
        List<StoreLocation> storeLocation = storeLocationRepository.findByAddressContaining(payload.address())
                .orElseThrow(() -> new EntityNotFoundException("주소가 일치하는 상점을 찾을 수 없습니다."));

        List<StoreResDto> result = new ArrayList<StoreResDto>();
        for (StoreLocation loc : storeLocation) {
            result.add(makeStoreDto(loc.getStore().getId(), loc.getStore()));
        }
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
