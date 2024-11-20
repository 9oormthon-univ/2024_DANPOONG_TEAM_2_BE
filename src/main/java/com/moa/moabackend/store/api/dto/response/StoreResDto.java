package com.moa.moabackend.store.api.dto.response;

import java.util.List;

public record StoreResDto(
                String name,
                String category,
                String profileImage,
                String caption,
                Long fundingTarget,
                Long fundingCurrent,
                List<String> images,
                String content,
                Double x,
                Double y) {

}
