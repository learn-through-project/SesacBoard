package com.hypeboy.hypeBoard.service;

import com.hypeboy.hypeBoard.dto.UserDetailDto;

import java.sql.SQLException;
import java.util.Optional;

public interface UserService {
    Optional<UserDetailDto> getUserDetail(String userId) throws SQLException;
}
