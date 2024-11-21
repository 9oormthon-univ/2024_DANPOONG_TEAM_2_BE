package com.moa.moabackend.store.api.dto.request;

import java.util.List;

public record StoreReqDto(
                String name,
                String category,
                String profileImage,
                String caption,
                Long fundingTarget,
                List<String> images,
                String content,
                Double x,
                Double y) {

}
