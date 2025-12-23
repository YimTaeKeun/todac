package com.ytk.todac.todac.global.authentication;

import com.ytk.todac.todac.global.exception.UnAuthorizedException;
import com.ytk.todac.todac.global.exception.ValidationException;
import com.ytk.todac.todac.global.jwt.token.TOKEN_TYPE;
import com.ytk.todac.todac.global.jwt.token.TokenManager;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {
    private final String AUTH_TYPE = "Bearer"; // 토큰 인증 방식
    private final TokenManager tokenManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 요청 로그 생성
        log.info(request.getMethod() + " " + request.getRequestURI());
        // 여기서 토큰 검증 및 사용자 업로드를 진행합니다.
        // 1. 요청 토큰 검증
        String header = request.getHeader("Authorization");
        if(header != null){
            try{
                String[] authorizationHeader = header.split(" ");
                if(authorizationHeader.length != 2 || !authorizationHeader[0].equals(AUTH_TYPE)) throw new UnAuthorizedException("INVALID");
                String token = authorizationHeader[1];
                Claims validClaims = tokenManager.verifyToken(token, TOKEN_TYPE.ACCESS_TOKEN); // 액세스 토큰 검증
                log.debug("Validated JWT TOKENS");
                uploadUser(validClaims.getSubject());
            } catch (UnAuthorizedException ex) {
                // 인증 실패 시
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.setCharacterEncoding("UTF-8");
                // 응답 본문에 담길 JSON 메시지 작성
                Map<String, String> errorResponse = new HashMap<>();
                log.debug("unauthorized");
                errorResponse.put("error", "Unauthorized");
                errorResponse.put("message", "인증에 실패했습니다.");

                // 4. JSON 메시지를 응답 스트림에 쓰기
                String jsonResponse = objectMapper.writeValueAsString(errorResponse);
                response.getWriter().write(jsonResponse);
                return;
            }
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
