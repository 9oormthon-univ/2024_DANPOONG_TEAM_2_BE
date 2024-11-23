package com.moa.moabackend.member.mypage.api;

import com.moa.moabackend.global.template.RspTemplate;
import com.moa.moabackend.member.domain.Member;
import com.moa.moabackend.member.mypage.api.api.request.UpdateCertifiedTypeReqDto;
import com.moa.moabackend.member.mypage.api.api.response.MyCertifiedTypesResDto;
import com.moa.moabackend.member.mypage.api.api.response.MyPageInfoResDto;
import com.moa.moabackend.member.mypage.api.api.response.MyScrapStoresResDto;
import com.moa.moabackend.member.mypage.application.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members/mypage")
public class MyPageController implements MyPageControllerDocs {

    private final MyPageService myPageService;

    @GetMapping("")
    public RspTemplate<MyPageInfoResDto> myProfileInfo(@AuthenticationPrincipal Member member) {
        return new RspTemplate<>(HttpStatus.OK, "내 프로필 정보", myPageService.findMyProfileByEmail(member.getEmail()));
    }

    @GetMapping("/scrap-stores")
    public RspTemplate<MyScrapStoresResDto> getMyScrapStores(@AuthenticationPrincipal Member member) {
        return new RspTemplate<>(HttpStatus.OK, "내 찜한 프로젝트", myPageService.findMyScrapStores(member.getEmail()));
    }

    @GetMapping("/certified-types")
    public RspTemplate<MyCertifiedTypesResDto> getMyCertifiedTypes(@AuthenticationPrincipal Member member) {
        return new RspTemplate<>(HttpStatus.OK, "내 관심 가치 목록", myPageService.getMyCertifiedTypes(member.getEmail()));
    }

    @PatchMapping("/certified-types")
    public RspTemplate<MyCertifiedTypesResDto> updateMyCertifiedTypes(@AuthenticationPrincipal Member member,
                                                                      @RequestBody UpdateCertifiedTypeReqDto updateCertifiedTypeReqDto) {
        return new RspTemplate<>(HttpStatus.OK,
                "내 관심 가치 수정",
                myPageService.updateMyCertifiedTypes(member.getEmail(), updateCertifiedTypeReqDto.certifiedType()));
    }

}
