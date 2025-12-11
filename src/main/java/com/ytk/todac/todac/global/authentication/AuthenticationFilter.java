package com.ytk.todac.todac.global.authentication;

import com.ytk.todac.todac.global.exception.ValidationException;
import com.ytk.todac.todac.global.jwt.token.TokenManager;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {
    private final String AUTH_TYPE = "Bearer"; // 토큰 인증 방식
    private final TokenManager tokenManager;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 여기서 토큰 검증 및 사용자 업로드를 진행합니다.
        // 1. 요청 토큰 검증
        String header = request.getHeader("Authorization");
        if(header != null){
            String[] authorizationHeader = header.split(" ");
            if(authorizationHeader.length != 2 || !authorizationHeader[0].equals(AUTH_TYPE)) throw new ValidationException("INVALID");
            String token = authorizationHeader[1];
            Claims validClaims = tokenManager.verifyToken(token); // 토큰 검증
            log.debug("Validated JWT TOKENS");
            uploadUser(validClaims.getSubject());
        }
        doFilter(request,response,filterChain);
    }

    private void uploadUser(String sub){
        // DB로부터 유저를 찾아옵니다.
        CustomUserDetails userDetails = (CustomUserDetails) customUserDetailsService.loadUserByUsername(sub);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
