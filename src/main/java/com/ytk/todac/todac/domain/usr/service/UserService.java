package com.ytk.todac.todac.domain.usr.service;

import com.ytk.todac.todac.domain.usr.dto.UserDTO;
import com.ytk.todac.todac.domain.usr.dto.UserMapper;
import com.ytk.todac.todac.domain.usr.entity.User;
import com.ytk.todac.todac.domain.usr.repository.UserRepository;
import com.ytk.todac.todac.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    // 유저 엔티티 저장소
    private final UserRepository userRepository;
    // 매퍼 클래스
    private final UserMapper userMapper;

    public UserDTO save(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        user = userRepository.save(user);
        return userMapper.toDTO(user);
    }

    public UserDTO findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new NotFoundException("User not found with id: " + id)
        );
        return userMapper.toDTO(user);
    }

    public UserDTO findBySocialUniqueId(String serviceName, String socialUniqueId) {
        List<User> users = userRepository.findAllBySocialUniqueId(socialUniqueId); // 소셜 아이디 기반 유저 탐색
        if (users.isEmpty()) throw new NotFoundException("User not found with id: " + socialUniqueId);
        else if(users.size() == 1) return userMapper.toDTO(users.get(0));

        for(User user : users){
            if(user.getRegisterService().equals(serviceName)) return userMapper.toDTO(user);
        }
        throw new NotFoundException("User not found with id: " + socialUniqueId);
    }
}
