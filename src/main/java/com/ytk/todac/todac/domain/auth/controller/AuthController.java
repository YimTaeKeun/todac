package com.ytk.todac.todac.domain.auth.controller;

import com.ytk.todac.todac.domain.auth.dto.AuthRequestDTO;
import com.ytk.todac.todac.domain.auth.dto.UserAuthDTO;
import com.ytk.todac.todac.domain.auth.service.LoginRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final LoginRegisterService loginRegisterService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDTO authRequestDTO) {
        // 로그인 혹은 회원가입을 진행합니다.
        var a = loginRegisterService.loginOrRegister(authRequestDTO);
        // 토큰을 반환합니다.
        return ResponseEntity.ok(a);
    }
}
