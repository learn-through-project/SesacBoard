package com.hypeboy.hypeBoard.repository;

import com.hypeboy.hypeBoard.connectionpool.ConnectionPool;
import com.hypeboy.hypeBoard.entity.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Log4j2
@Repository
public class UserRepositoryImpl implements UserRepository {
    private final ConnectionPool connPool;

    @Autowired
    public UserRepositoryImpl(ConnectionPool coonPool) {
        this.connPool = coonPool;
    }

    public Optional<User> findById(String userId) throws SQLException {
        String selectQuery = "select * from users where ID = ?";
        User user = null;

        try (Connection conn = connPool.getConnection()) {
            try (PreparedStatement statement = conn.prepareStatement(selectQuery)) {
                statement.setString(1, userId);

                try (ResultSet rs = statement.executeQuery()) {
                    if (rs.next()) {
                        user = resultSetToUser(rs);
                    }
                }
            }
        }

        return Optional.ofNullable(user);
    }

    private User resultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getString("ID"));
        user.setName(rs.getString("NAME"));
        user.setPhone(rs.getString("PHONE"));
        user.setEmail(rs.getString("EMAIL"));
        user.setPwd(rs.getString("PWD"));
        user.setNickname(rs.getString("NICKNAME"));

        return user;
    }
}
