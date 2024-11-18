package com.moa.moabackend.auth.application;


import com.moa.moabackend.auth.api.dto.response.UserInfo;

public interface AuthService {

    UserInfo getUserInfo(String code);

    String getProvider();
}
