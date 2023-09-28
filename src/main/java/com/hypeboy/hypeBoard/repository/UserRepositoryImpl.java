package com.hypeboy.hypeBoard.repository;

import com.hypeboy.hypeBoard.connectionpool.ConnectionPool;
import com.hypeboy.hypeBoard.entity.User;
import com.hypeboy.hypeBoard.enums.tablesColumns.TableColumnsUser;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
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
        String selectQuery = "select * from "
                + TableColumnsUser.TABLE.getString()
                + " where " + TableColumnsUser.ID.getString() + " = ? ";

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
        Timestamp createdAt = rs.getTimestamp(TableColumnsUser.CREATED_AT.getString());
        Timestamp updatedAt = rs.getTimestamp(TableColumnsUser.UPDATED_AT.getString());


        User user = new User();
        user.setId(rs.getString(TableColumnsUser.ID.getString()));
        user.setName(rs.getString(TableColumnsUser.NAME.getString()));
        user.setPhone(rs.getString(TableColumnsUser.PHONE.getString()));
        user.setEmail(rs.getString(TableColumnsUser.EMAIL.getString()));
        user.setPwd(rs.getString(TableColumnsUser.PWD.getString()));
        user.setNickname(rs.getString(TableColumnsUser.NICKNAME.getString()));
        user.setCreatedAt(createdAt != null ? createdAt.toLocalDateTime() : null);
        user.setUpdatedAt(updatedAt != null ? updatedAt.toLocalDateTime() : null);

        return user;
    }
}
