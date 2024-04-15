package com.ex.ticket.security.handler;

import java.io.IOException;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.ex.ticket.auth.PrincipalDetails;
import com.ex.ticket.common.PalagoStatic;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        String grantedAuthority = authResult.getAuthorities().stream()
        	.map(GrantedAuthority::getAuthority)
        	.findAny()
        	.orElseThrow()
        	.toString();

        log.info("authResult 로 가져와볼것이다.");
        log.info("{} {}", ((PrincipalDetails)authResult.getPrincipal()).getUsername(), grantedAuthority);
        // String access = tokenService.generateAccessToken(authResult.getPrincipal().toString(), grantedAuthority);

        String access = tokenService.generateAccessToken(principalDetails.getUsername(), grantedAuthority);

        response.addHeader(PalagoStatic.AUTH_HEADER, PalagoStatic.BEARER + access);

        // 권한 확인 후에 리디렉션을 수행
        if (authorities.stream().anyMatch(auth -> auth.getAuthority().equals("MASTER") || auth.getAuthority().equals("MANAGER"))) {
            System.out.println("마스터?");
            response.sendRedirect("/api/group");
        } else {
            System.out.println("아님");
            response.sendRedirect("/home");
        }
    }
}
