package com.moa.moabackend.store.api;

import java.io.IOException;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moa.moabackend.global.template.RspTemplate;
import com.moa.moabackend.store.api.dto.request.StoreReqDto;
import com.moa.moabackend.store.api.dto.response.StoreResDto;
import com.moa.moabackend.store.application.StoreService;
import com.moa.moabackend.store.domain.Store;
import com.moa.moabackend.store.exception.RevGeocodeNotFoundException;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/store")
@RequiredArgsConstructor
public class StoreController implements StoreControllerDocs {

    private final StoreService storeService;

    @PostMapping("")
    public RspTemplate<Long> createStore(@RequestBody StoreReqDto storeReqDto) {
        Long result = null;
        try {
            Store store = storeService.createStore(storeReqDto);
            result = store.getId();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return new RspTemplate<>(HttpStatus.INTERNAL_SERVER_ERROR, "좌표를 주소로 변환하는데 실패했습니다.", null);
        } catch (RevGeocodeNotFoundException e) {
            return new RspTemplate<>(HttpStatus.BAD_REQUEST, "해당하는 좌표로 가게를 찾지 못했습니다.", null);
        }
        return new RspTemplate<>(HttpStatus.OK, "상점 생성", result);
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public RspTemplate<StoreResDto> getStore(
            @Parameter(name = "id", description = "상점 ID", in = ParameterIn.PATH) @PathVariable(name = "id") Long storeId) {
        Store store = storeService.getStore(storeId);

        StoreResDto storeResDto = new StoreResDto(
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
                store.getEndAt());

        return new RspTemplate<>(HttpStatus.OK, "상점 조회", storeResDto);
    }

    @PutMapping("/{id}")
    public RspTemplate<Boolean> putStore(
            @Parameter(name = "id", description = "상점 ID", in = ParameterIn.PATH) @PathVariable(name = "id") Long storeId,
            @RequestBody StoreReqDto storeReqDto) {

        try {
            storeService.updateStore(storeId, storeReqDto);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return new RspTemplate<>(HttpStatus.INTERNAL_SERVER_ERROR, "좌표를 주소로 변환하는데 실패했습니다.", null);
        } catch (RevGeocodeNotFoundException e) {
            return new RspTemplate<>(HttpStatus.BAD_REQUEST, "해당하는 좌표로 가게를 찾지 못했습니다.", null);
        }

        return new RspTemplate<>(HttpStatus.OK, "상점 수정", true);
    }

    @DeleteMapping("/{id}")
    public RspTemplate<Boolean> deleteStore(
            @Parameter(name = "id", description = "상점 ID", in = ParameterIn.PATH) @PathVariable(name = "id") Long storeId) {
        storeService.deleteStore(storeId);
        return new RspTemplate<>(HttpStatus.OK, "상점 삭제", true);
    }

    // @GetMapping("/map")
    // public RspTemplate<List<Number>> searchStoreByLocation(
    // @RequestBody SearchByLocationReqDto searchByLocationReqDto) {
    // List<Number> stores =
    // storeService.getStoresByLocation(searchByLocationReqDto.radius(),
    // searchByLocationReqDto.x(),
    // searchByLocationReqDto.y()).stream().map(store ->
    // store.getId()).collect(Collectors.toList());

    // return new RspTemplate<>(HttpStatus.OK, "좌표 주변 상점 찾기", stores);
    // }
}
