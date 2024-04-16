package com.ex.ticket.security.handler;

import com.ex.ticket.auth.PrincipalDetails;
import com.ex.ticket.common.PalagoStatic;
import com.ex.ticket.security.CookieHelper;
import com.ex.ticket.security.jwt.TokenService;
import com.ex.ticket.user.domain.dto.response.SignInResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private CookieHelper cookieHelper;

    private final TokenService tokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        System.out.println("success 핸들러 시작!");
        String grantedAuthority = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findAny()
                .orElseThrow()
                .toString();

        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

        System.out.println(grantedAuthority);
        String access = tokenService.generateAccessToken(principalDetails.getUsername(), grantedAuthority);
        String refresh = tokenService.generateRefreshToken(principalDetails.getUsername());
//        TokenAndUserResponse tokenAndUserResponse =
//        cookieHelper
        response.addHeader(PalagoStatic.AUTH_HEADER, PalagoStatic.BEARER + access);

        // 권한 확인 후에 리디렉션을 수행
        if (grantedAuthority.equals("MASTER") || grantedAuthority.equals("MANAGER")) {
            System.out.println("마스터?");
        } else {
            System.out.println("아님");
        }


    }


    private byte[] getSignInResponse(PrincipalDetails principalDetails) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        SignInResponse signInResponse = principalDetails.getSignInResponse();

        System.out.println("getSignInResponse 동작 중");
        log.info("{} {} {} {}", signInResponse.getUserId(), signInResponse.getUserName(), signInResponse.getName(), signInResponse.getRole());

        return objectMapper.writeValueAsBytes(signInResponse);
    }

}
