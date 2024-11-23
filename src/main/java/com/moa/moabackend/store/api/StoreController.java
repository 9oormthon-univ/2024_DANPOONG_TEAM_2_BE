package com.moa.moabackend.store.api;

import com.moa.moabackend.global.template.RspTemplate;
import com.moa.moabackend.member.domain.Member;
import com.moa.moabackend.member.exception.MemberNotFoundException;
import com.moa.moabackend.store.api.dto.request.GetStoreListDto;
import com.moa.moabackend.store.api.dto.request.StoreReqDto;
import com.moa.moabackend.store.api.dto.response.StoreResDto;
import com.moa.moabackend.store.application.StoreService;
import com.moa.moabackend.store.domain.Store;
import com.moa.moabackend.store.exception.AlreadyScrapedException;
import com.moa.moabackend.store.exception.RevGeocodeNotFoundException;
import com.moa.moabackend.store.exception.StoreNotFoundException;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import jakarta.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/all")
    @Transactional(readOnly = true)
    public RspTemplate<List<StoreResDto>> getAllStores() {
        List<StoreResDto> result = new ArrayList<StoreResDto>();
        try {
            result = storeService.getAllStores();
        } catch (EntityNotFoundException e) {
            return new RspTemplate<>(HttpStatus.NOT_FOUND, "등록된 상점을 1개 이상 찾을 수 없었습니다.", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new RspTemplate<>(HttpStatus.INTERNAL_SERVER_ERROR, "상점을 찾는 중 문제가 발생했습니다.", null);
        }
        return new RspTemplate<>(HttpStatus.OK, "상점 전체 조회", result);
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
            @RequestParam(name = "address") String address) {
        List<StoreResDto> result = null;
        try {
            List<StoreResDto> store = storeService.searchStoreByAddress(address);
            result = store;
        } catch (EntityNotFoundException e) {
            return new RspTemplate<>(HttpStatus.NOT_FOUND, "검색된 상점이 없습니다.", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new RspTemplate<>(HttpStatus.INTERNAL_SERVER_ERROR, "상점을 찾는 중 문제가 발생했습니다.", null);
        }
        return new RspTemplate<>(HttpStatus.OK, "주소로 상점 찾기", result);
    }

    @GetMapping("/scrap")
    @Transactional(readOnly = true)
    public RspTemplate<List<StoreResDto>> getScrapStoreList(@AuthenticationPrincipal Member member) {
        List<StoreResDto> result = null;
        try {
            result = storeService.getScrapStoreList(member.getId());
        } catch (EntityNotFoundException e) {
            return new RspTemplate<>(HttpStatus.NOT_FOUND, "찜한 상점이 없거나, 유효한 유저가 아닙니다.", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new RspTemplate<>(HttpStatus.INTERNAL_SERVER_ERROR, "찜한 상점 리스트를 가져오는 중 문제가 발생했습니다.", null);
        }

        return new RspTemplate<>(HttpStatus.OK, "찜한 상점 전체 확인", result);
    }

    @PostMapping("/scrap/{id}")
    public RspTemplate<Boolean> scrapStore(@AuthenticationPrincipal Member member,
            @Parameter(name = "id", description = "상점 ID", in = ParameterIn.PATH) @PathVariable(name = "id") Long storeId) {
        try {
            storeService.scrapStore(storeId, member.getId());
        } catch (EntityNotFoundException e) {
            return new RspTemplate<>(HttpStatus.NOT_FOUND, "상점을 찾을 수 없거나, 유효한 유저가 아닙니다.", null);
        } catch (AlreadyScrapedException e) {
            return new RspTemplate<>(HttpStatus.BAD_REQUEST, "이미 찜한 상점입니다.", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new RspTemplate<>(HttpStatus.INTERNAL_SERVER_ERROR, "상점 찜하기 중 문제가 발생했습니다.", null);
        }

        return new RspTemplate<>(HttpStatus.OK, "상점 찜하기", true);
    }

    @DeleteMapping("/scrap/{id}")
    public RspTemplate<Boolean> unscrapStore(@AuthenticationPrincipal Member member,
            @Parameter(name = "id", description = "상점 ID", in = ParameterIn.PATH) @PathVariable(name = "id") Long storeId) {
        try {
            storeService.unscrapStore(storeId, member.getId());
        } catch (EntityNotFoundException e) {
            return new RspTemplate<>(HttpStatus.NOT_FOUND, "찜하지 않은 상태에서 찜하기 취소를 시도했습니다.", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new RspTemplate<>(HttpStatus.INTERNAL_SERVER_ERROR, "상점 찜하기 취소 처리 중 문제가 발생했습니다.", null);
        }

        return new RspTemplate<>(HttpStatus.OK, "상점 찜하기 취소", true);
    }

    @GetMapping("/list")
    @Transactional(readOnly = true)
    public RspTemplate<List<StoreResDto>> getStoreList(@RequestBody GetStoreListDto getStoreListDto) {
        List<StoreResDto> result = null;
        try {
            result = storeService.getTopNStoreList(getStoreListDto.certifiedType(), getStoreListDto.page(),
                    getStoreListDto.size());
        } catch (EntityNotFoundException e) {
            return new RspTemplate<>(HttpStatus.NOT_FOUND, "조건에 일치하는 상점이 없습니다.", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new RspTemplate<>(HttpStatus.INTERNAL_SERVER_ERROR, "상점 리스트를 가져오는 중 문제가 발생했습니다.", null);
        }

        return new RspTemplate<>(HttpStatus.OK, "상점 리스트 조회", result);
    }

    @GetMapping("/curation")
    @Transactional(readOnly = true)
    public RspTemplate<List<StoreResDto>> getStoreListByFavoriteType(@AuthenticationPrincipal Member member) {
        List<StoreResDto> result = null;
        try {
            result = storeService.getStoreListByFavoriteType(member.getId());
        } catch (MemberNotFoundException e) {
            return new RspTemplate<>(HttpStatus.BAD_REQUEST, "유효하지 않은 회원입니다.", null);
        } catch (StoreNotFoundException e) {
            return new RspTemplate<>(HttpStatus.NOT_FOUND, "해당 관심분야로 개설된 상점이 단 한개도 없습니다.", null);
        } catch (EntityNotFoundException e) {
            return new RspTemplate<>(HttpStatus.BAD_REQUEST, "관심분야를 설정하지 않았습니다.", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new RspTemplate<>(HttpStatus.INTERNAL_SERVER_ERROR, "상점을 추천하는 중 문제가 발생했습니다.", null);
        }

        return new RspTemplate<>(HttpStatus.OK, "관심분야 기반 상점 추천", result);
    }

}
