package com.moa.moabackend.global.error.dto;

public record ErrorResponse(
        int statusCode,
        String message
) {
}