package com.moa.moabackend.member.api.dto.request;

import com.moa.moabackend.member.domain.MemberType;

public record MemberTypeUpdateReqDto(
        String memberType
) {
    public MemberType toMemberType() {
        return MemberType.fromString(this.memberType);
    }
}
