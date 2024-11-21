package com.moa.moabackend.store.api.dto.request;

public record SearchByLocationReqDto(
        Long radius,
        Double x,
        Double y) {

}
