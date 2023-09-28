package com.hypeboy.hypeBoard.repository;

import com.hypeboy.hypeBoard.entity.User;

import java.sql.SQLException;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(String userId) throws SQLException;
}
