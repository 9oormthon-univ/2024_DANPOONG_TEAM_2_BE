package com.moa.moabackend.store.api;

import org.springframework.web.bind.annotation.PathVariable;

import com.moa.moabackend.global.template.RspTemplate;
import com.moa.moabackend.store.api.dto.request.StoreReqDto;
import com.moa.moabackend.store.api.dto.response.StoreResDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

public interface StoreControllerDocs {

    @Operation(summary = "상점 생성", description = "새로운 상점을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상점 생성 성공"),
            @ApiResponse(responseCode = "400", description = "해당하는 좌표로 가게를 찾지 못했습니다."),
            @ApiResponse(responseCode = "500", description = "좌표를 주소로 변환하는데 실패했습니다.")
    })
    RspTemplate<Boolean> createStore(@RequestBody StoreReqDto storeReqDto);

    @Operation(summary = "상점 조회", description = "상점 ID로 상점 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상점 조회 성공"),
            @ApiResponse(responseCode = "404", description = "상점을 찾을 수 없습니다.")
    })
    RspTemplate<StoreResDto> getStore(
            @Parameter(name = "id", description = "상점 ID", in = ParameterIn.PATH) @PathVariable(name = "id") Long storeId);

    @Operation(summary = "상점 수정", description = "상점 정보를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상점 수정 성공"),
            @ApiResponse(responseCode = "400", description = "해당하는 좌표로 가게를 찾지 못했습니다."),
            @ApiResponse(responseCode = "404", description = "수정할 상점을 찾을 수 없습니다."),
            @ApiResponse(responseCode = "500", description = "좌표를 주소로 변환하는데 실패했습니다.")
    })
    RspTemplate<Boolean> putStore(
            @Parameter(name = "id", description = "상점 ID", in = ParameterIn.PATH) @PathVariable(name = "id") Long storeId,
            @RequestBody StoreReqDto storeReqDto);

    @Operation(summary = "상점 삭제", description = "상점을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상점 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "삭제할 상점을 찾을 수 없습니다.")
    })
    RspTemplate<Boolean> deleteStore(
            @Parameter(name = "id", description = "상점 ID", in = ParameterIn.PATH) @PathVariable(name = "id") Long storeId);

    // @Operation(summary = "주변 상점 검색", description = "지정된 좌표 반경 내의 상점들을 검색합니다.")
    // @ApiResponses(value = {
    // @ApiResponse(responseCode = "200", description = "상점 검색 성공")
    // })
    // RspTemplate<List<Number>> searchStoreByLocation(
    // @RequestBody SearchByLocationReqDto searchByLocationReqDto
    // );
}