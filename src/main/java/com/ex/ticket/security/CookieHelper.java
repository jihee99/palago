package com.ex.ticket.security;

import com.ex.ticket.auth.model.dto.TokenAndUserResponse;
import com.ex.ticket.common.PalagoStatic;
import com.ex.ticket.common.annotation.Helper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;

import java.util.HashMap;
import java.util.Map;

@Helper
@RequiredArgsConstructor
public class CookieHelper {

    public Map<String, ResponseCookie> getTokenCookies(TokenAndUserResponse tokenAndUserResponse) {
        String sameSite = "None";
        ResponseCookie accessToken =
                ResponseCookie.from("accessToken", tokenAndUserResponse.getAccessToken())
                        .path("/")
                        .maxAge(tokenAndUserResponse.getAccessTokenAge())
                        .sameSite(sameSite)
//                        .httpOnly(true)
                        .secure(true)
                        .build();

        ResponseCookie refreshToken =
                ResponseCookie.from("refreshToken", tokenAndUserResponse.getRefreshToken())
                        .path("/")
                        .maxAge(tokenAndUserResponse.getRefreshTokenAge())
                        .sameSite(sameSite)
//                        .httpOnly(true)
                        .secure(true)
                        .build();

        Map<String, ResponseCookie > result = new HashMap<>();

        result.put(PalagoStatic.ACCESS_TOKEN, accessToken);
        result.put(PalagoStatic.REFRESH_TOKEN, refreshToken);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.SET_COOKIE, accessToken.toString());
        httpHeaders.add(HttpHeaders.SET_COOKIE, refreshToken.toString());

        return result;
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add(HttpHeaders.SET_COOKIE, accessToken.toString());
//        httpHeaders.add(HttpHeaders.SET_COOKIE, refreshToken.toString());

//        return httpHeaders;
    }

    public HttpHeaders deleteCookies() {
        String sameSite = "None";

        System.out.println("==========쿠키헬퍼의 deleteCookie==========");
        ResponseCookie accessToken = ResponseCookie.from("accessToken", null)
                        .path("/")
                        .maxAge(0)
                        .sameSite(sameSite)
//                        .httpOnly(true)
                        .secure(true)
                        .build();
        ResponseCookie refreshToken =
                ResponseCookie.from("refreshToken", null)
                        .path("/")
                        .maxAge(0)
                        .sameSite(sameSite)
//                        .httpOnly(true)
                        .secure(true)
                        .build();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.SET_COOKIE, accessToken.toString());
        httpHeaders.add(HttpHeaders.SET_COOKIE, refreshToken.toString());
        return httpHeaders;
    }
}
