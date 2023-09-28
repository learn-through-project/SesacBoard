package com.hypeboy.hypeBoard.unit.user;

import com.hypeboy.hypeBoard.connectionpool.ConnectionPool;
import com.hypeboy.hypeBoard.entity.User;
import com.hypeboy.hypeBoard.enums.tablesColumns.TableColumnsUser;
import com.hypeboy.hypeBoard.repository.UserRepositoryImpl;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryTest {
    @Mock
    private ConnectionPool pool;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement statement;

    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private UserRepositoryImpl userRepository;

    @Nested
    class FindById_Test {
        private String userId = "test1";

        @Test
        public void findById_Throws_Exception() throws SQLException {
            when(pool.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(anyString())).thenReturn(statement);
            when(statement.executeQuery()).thenThrow(SQLException.class);
            assertThrows(SQLException.class, () -> userRepository.findById(userId));
        }

        @Test
        public void findById_Return_Empty() throws SQLException {
            when(pool.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(anyString())).thenReturn(statement);
            when(statement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(false);

            Optional<User> user = userRepository.findById(userId);

            assertThat(user.isEmpty()).isTrue();
        }

        @Test
        public void findById_Return_User() throws SQLException {
            when(pool.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(anyString())).thenReturn(statement);
            when(statement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true);
            when(resultSet.getString(TableColumnsUser.ID.getString())).thenReturn(userId);

            Optional<User> user = userRepository.findById(userId);

            verify(statement).setString(1, userId);
            verify(resultSet).getString(TableColumnsUser.ID.getString());
            assertThat(user).isNotNull();
            assertThat(user.get().getId()).isEqualTo(userId);
        }

    }


}
