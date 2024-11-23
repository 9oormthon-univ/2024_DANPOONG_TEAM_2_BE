package com.moa.moabackend.store.api;

import com.moa.moabackend.global.template.RspTemplate;
import com.moa.moabackend.member.domain.Member;
import com.moa.moabackend.store.api.dto.request.GetStoreListDto;
import com.moa.moabackend.store.api.dto.request.StoreReqDto;
import com.moa.moabackend.store.api.dto.response.StoreResDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public interface StoreControllerDocs {

        @Operation(summary = "상점 생성", description = "새로운 상점을 생성합니다.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "상점 생성 성공"),
                        @ApiResponse(responseCode = "400", description = "해당하는 좌표로 가게를 찾지 못했습니다."),
                        @ApiResponse(responseCode = "500", description = "좌표를 주소로 변환하는데 실패했습니다.")
        })
        RspTemplate<Long> createStore(@RequestBody StoreReqDto storeReqDto);

        @Operation(summary = "상점 조회", description = "상점 ID로 상점 정보를 조회합니다.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "상점 조회 성공"),
                        @ApiResponse(responseCode = "404", description = "상점을 찾을 수 없습니다."),
                        @ApiResponse(responseCode = "500", description = "상점 조회 중 문제가 발생했습니다.")
        })
        RspTemplate<StoreResDto> getStore(
                        @Parameter(name = "id", description = "상점 ID", in = ParameterIn.PATH) @PathVariable(name = "id") Long storeId);

        @Operation(summary = "상점 수정", description = "상점 정보를 수정합니다.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "상점 수정 성공"),
                        @ApiResponse(responseCode = "400", description = "해당하는 좌표로 가게를 찾지 못했습니다."),
                        @ApiResponse(responseCode = "500", description = "좌표를 주소로 변환하는데 실패했습니다.")
        })
        RspTemplate<Boolean> putStore(
                        @Parameter(name = "id", description = "상점 ID", in = ParameterIn.PATH) @PathVariable(name = "id") Long storeId,
                        @RequestBody StoreReqDto storeReqDto);

        @Operation(summary = "상점 삭제", description = "상점을 삭제합니다.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "상점 삭제 성공"),
                        @ApiResponse(responseCode = "404", description = "상점을 찾을 수 없습니다."),
                        @ApiResponse(responseCode = "500", description = "상점 삭제 중 문제가 발생했습니다.")
        })
        RspTemplate<Boolean> deleteStore(
                        @Parameter(name = "id", description = "상점 ID", in = ParameterIn.PATH) @PathVariable(name = "id") Long storeId);

        @Operation(summary = "주소로 상점 검색", description = "지정된 주소 기준으로 상점들을 검색합니다.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "상점 검색 성공"),
                        @ApiResponse(responseCode = "404", description = "검색된 상점이 없습니다."),
                        @ApiResponse(responseCode = "500", description = "상점을 찾는 중 문제가 발생했습니다.")
        })
        RspTemplate<List<StoreResDto>> searchStoreByAddress(@RequestParam(name = "address") String address);

        @Operation(summary = "찜한 상점 목록 조회", description = "사용자가 찜한 상점 목록을 조회합니다.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "찜한 상점 목록 조회 성공"),
                        @ApiResponse(responseCode = "404", description = "찜한 상점이 없거나, 유효한 유저가 아닙니다."),
                        @ApiResponse(responseCode = "500", description = "찜한 상점 리스트를 가져오는 중 문제가 발생했습니다.")
        })
        RspTemplate<List<StoreResDto>> getScrapStoreList(@AuthenticationPrincipal Member member);

        @Operation(summary = "상점 찜하기", description = "상점을 찜합니다.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "상점 찜하기 성공"),
                        @ApiResponse(responseCode = "404", description = "상점을 찾을 수 없거나, 유효한 유저가 아닙니다."),
                        @ApiResponse(responseCode = "500", description = "상점 찜하기 중 문제가 발생했습니다.")
        })
        RspTemplate<Boolean> scrapStore(@AuthenticationPrincipal Member member,
                        @Parameter(name = "id", description = "상점 ID", in = ParameterIn.PATH) @PathVariable(name = "id") Long storeId);

        @Operation(summary = "상점 찜하기 취소", description = "상점 찜하기를 취소합니다.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "상점 찜하기 취소 성공"),
                        @ApiResponse(responseCode = "404", description = "찜하지 않은 상태에서 찜하기 취소를 시도했습니다."),
                        @ApiResponse(responseCode = "500", description = "상점 찜하기 취소 처리 중 문제가 발생했습니다.")
        })
        RspTemplate<Boolean> unscrapStore(@AuthenticationPrincipal Member member,
                        @Parameter(name = "id", description = "상점 ID", in = ParameterIn.PATH) @PathVariable(name = "id") Long storeId);

        @Operation(summary = "상점 전체 조회", description = "모든 상점을 조회합니다.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "모든 상점을 정상적으로 조회했습니다."),
                        @ApiResponse(responseCode = "404", description = "등록된 상점을 1개 이상 찾을 수 없었습니다."),
                        @ApiResponse(responseCode = "500", description = "모든 상점을 불러오는 중 문제가 발생했습니다.")
        })
        RspTemplate<List<StoreResDto>> getAllStores();

        @Operation(summary = "상점 리스트 조회", description = "CertifiedType에 따라 상점 리스트를 조회합니다.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "조건에 일치하는 상점 리스트를 정상적으로 조회했습니다."),
                        @ApiResponse(responseCode = "404", description = "조건에 일치하는 상점이 없습니다."),
                        @ApiResponse(responseCode = "500", description = "상점 리스트를 불러오는 중 문제가 발생했습니다.")
        })
        RspTemplate<List<StoreResDto>> getStoreList(@RequestBody GetStoreListDto getStoreListDto);

        @Operation(summary = "관심분야 설정", description = "유저가 선호하는 CertifiedType들을 지정합니다.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "관심분야 설정 성공"),
                        @ApiResponse(responseCode = "500", description = "관심분야를 설정하는 중 문제가 발생했습니다.")
        })
        RspTemplate<List<StoreResDto>> getStoreListByFavoriteType(@AuthenticationPrincipal Member member);

}
