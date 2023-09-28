package com.hypeboy.hypeBoard.unit.user;

import com.hypeboy.hypeBoard.dto.UserDetailDto;
import com.hypeboy.hypeBoard.entity.User;
import com.hypeboy.hypeBoard.repository.UserRepository;
import com.hypeboy.hypeBoard.service.UserServiceImpl;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Nested
    class GetUserDetail_Test {
        private String userId = "test1";

        private User dummyUser = new User();

        @Test
        public void getUserDetail_Throws_Exception_When_Repository_Throw_Exception() throws SQLException {
            when(userRepository.findById(userId)).thenThrow(SQLException.class);
            assertThrows(SQLException.class, () ->userService.getUserDetail(userId) );
        }

        @Test
        public void getUserDetail_Return_Empty_When_Repository_Return_Empty() throws SQLException {
            when(userRepository.findById(userId)).thenReturn(Optional.empty());

            Optional<UserDetailDto> detail = userService.getUserDetail(userId);

            assertThat(detail.isPresent()).isFalse();
        }

        @Test
        public void getUserDetail_Return_UserDetailDto() throws SQLException {
            when(userRepository.findById(userId)).thenReturn(Optional.of(dummyUser));

            Optional<UserDetailDto> detail = userService.getUserDetail(userId);

            assertThat(detail.isPresent()).isTrue();
            assertThat(detail.get().getId()).isEqualTo(dummyUser.getId());
        }
    }

}
