package com.moa.moabackend.store.api;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moa.moabackend.global.template.RspTemplate;
import com.moa.moabackend.store.api.dto.request.SearchByLocationReqDto;
import com.moa.moabackend.store.api.dto.request.StoreReqDto;
import com.moa.moabackend.store.api.dto.response.StoreResDto;
import com.moa.moabackend.store.application.StoreService;
import com.moa.moabackend.store.domain.Store;
import com.moa.moabackend.store.exception.RevGeocodeNotFoundException;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import jakarta.persistence.EntityNotFoundException;
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
        } catch (Exception e) {
            e.printStackTrace();
            return new RspTemplate<>(HttpStatus.INTERNAL_SERVER_ERROR, "상점 생성 중 문제가 발생했습니다.", null);
        }
        return new RspTemplate<>(HttpStatus.OK, "상점 생성", result);
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public RspTemplate<StoreResDto> getStore(
            @Parameter(name = "id", description = "상점 ID", in = ParameterIn.PATH) @PathVariable(name = "id") Long storeId) {
        StoreResDto result = null;
        try {
            StoreResDto store = storeService.getStore(storeId);
            result = store;
        } catch (EntityNotFoundException e) {
            return new RspTemplate<>(HttpStatus.NOT_FOUND, "상점을 찾을 수 없습니다.", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new RspTemplate<>(HttpStatus.INTERNAL_SERVER_ERROR, "상점 삭제 중 문제가 발생했습니다.", null);
        }

        return new RspTemplate<>(HttpStatus.OK, "상점 조회", result);
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
        } catch (Exception e) {
            e.printStackTrace();
            return new RspTemplate<>(HttpStatus.INTERNAL_SERVER_ERROR, "가게를 찾는 중에 문제가 발생했습니다.", true);
        }

        return new RspTemplate<>(HttpStatus.OK, "상점 수정", true);
    }

    @DeleteMapping("/{id}")
    public RspTemplate<Boolean> deleteStore(
            @Parameter(name = "id", description = "상점 ID", in = ParameterIn.PATH) @PathVariable(name = "id") Long storeId) {
        try {
            storeService.deleteStore(storeId);
        } catch (EntityNotFoundException e) {
            return new RspTemplate<>(HttpStatus.NOT_FOUND, "상점을 찾을 수 없습니다.", true);
        } catch (Exception e) {
            e.printStackTrace();
            return new RspTemplate<>(HttpStatus.INTERNAL_SERVER_ERROR, "상점 삭제 중 문제가 발생했습니다.", true);
        }
        return new RspTemplate<>(HttpStatus.OK, "상점 삭제", true);
    }

    @GetMapping("/map/search")
    @Transactional(readOnly = true)
    public RspTemplate<List<StoreResDto>> searchStoreByAddress(
            @RequestBody SearchByLocationReqDto searchByLocationReqDto) {
        List<StoreResDto> result = null;
        try {
            List<StoreResDto> store = storeService.searchStoreByAddress(searchByLocationReqDto);
            result = store;
        } catch (EntityNotFoundException e) {
            return new RspTemplate<>(HttpStatus.NOT_FOUND, "검색된 상점이 없습니다.", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new RspTemplate<>(HttpStatus.INTERNAL_SERVER_ERROR, "상점을 찾는 중 문제가 발생했습니다.", null);
        }
        return new RspTemplate<>(HttpStatus.OK, "주소로 상점 찾기", result);
    }
}
