package com.hypeboy.hypeBoard.service;

import com.hypeboy.hypeBoard.dto.UserDetailDto;
import com.hypeboy.hypeBoard.entity.User;
import com.hypeboy.hypeBoard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<UserDetailDto> getUserDetail(String userId) throws SQLException {
        Optional<User> user = userRepository.findById(userId);
        UserDetailDto dto = user.isPresent() ? fromUserToUserDetailDto(user.get()) : null;

        return Optional.ofNullable(dto);
    };

    private UserDetailDto fromUserToUserDetailDto(User user) {
        UserDetailDto dto =  new UserDetailDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setPhone(user.getPhone());
        dto.setEmail(user.getEmail());
        dto.setNickname(user.getNickname());

        return dto;
    }
}
