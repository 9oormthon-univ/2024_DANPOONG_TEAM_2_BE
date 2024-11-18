package com.moa.moabackend.member.domain;

import com.moa.moabackend.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String email;

    private String picture;

    private String nickname;

    @Enumerated(value = EnumType.STRING)
    private SocialType socialType;

    @Builder
    public Member(Role role, String email, String picture, String nickname, SocialType socialType) {
        this.role = role;
        this.email = email;
        this.picture = picture;
        this.nickname = nickname;
        this.socialType = socialType;
    }

}
