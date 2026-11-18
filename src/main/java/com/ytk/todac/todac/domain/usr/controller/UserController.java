package com.ytk.todac.todac.domain.usr.controller;

import com.ytk.todac.todac.domain.usr.dto.UserDTO;
import com.ytk.todac.todac.domain.usr.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getUser(){
        UserDTO user = userService.getLoginUser();
        return ResponseEntity.ok(user);
    }
}
