package com.ytk.todac.todac.global.authentication;

import com.ytk.todac.todac.domain.usr.dto.UserDTO;
import com.ytk.todac.todac.domain.usr.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;
    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        UserDTO target = userService.findById(Long.parseLong(id));
        return new CustomUserDetails(String.valueOf(target.getId()), target.getUsername());
    }
}
