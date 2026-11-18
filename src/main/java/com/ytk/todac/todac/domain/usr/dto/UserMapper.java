package com.ytk.todac.todac.domain.usr.dto;

import com.ytk.todac.todac.domain.usr.entity.User;
import org.springframework.stereotype.Component;

/**
 * 해당 클래스는 DTO와 User라는 엔티티 간에 서로 변환해주기 위해 사용됩니다.
 * */
@Component
public class UserMapper {

    public User toEntity(UserDTO userDTO) {
        return User.builder()
                .socialUniqueId(userDTO.getSocialUniqueId())
                .registerService(userDTO.getRegisterService())
                .username(userDTO.getUsername())
                .build();
    }

    public UserDTO toDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .socialUniqueId(user.getSocialUniqueId())
                .registerService(user.getRegisterService())
                .username(user.getUsername())
                .build();
    }
}
