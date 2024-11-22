package com.moa.moabackend.store.api.dto.request;

import java.util.List;

import com.moa.moabackend.store.domain.CertifiedType;

import jakarta.validation.constraints.NotNull;

public record GetCurationDto(
        Integer type,
        CertifiedType certifiedType) {
}
