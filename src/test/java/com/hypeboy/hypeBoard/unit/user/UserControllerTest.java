package com.hypeboy.hypeBoard.unit.user;

import com.hypeboy.hypeBoard.controller.UserControllerImpl;
import com.hypeboy.hypeBoard.dto.UserDetailDto;
import com.hypeboy.hypeBoard.service.UserService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.sql.SQLException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private Model model;

    @Mock
    private BindingResult br;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserControllerImpl userController;

    @Nested
    class GetUserDetail_Test {
        private final String KEY_DETAIL = "detail";
        private final String viewName = "user_detail/user_detail";
        private final String userId = "test1";
        private UserDetailDto dummyDto = new UserDetailDto();

        @Test
        public void getUserDetail_Return_View_With_Null_When_PathVariable_Error() {
            when(br.hasErrors()).thenReturn(true);
            String view = userController.getUserDetail(userId, br, model);

            verify(model).addAttribute(KEY_DETAIL, null);
            assertThat(view).isEqualTo(viewName);
        }

        @Test
        public void getUserDetail_Return_View_With_Null_When_Service_Throws() throws SQLException {
            when(br.hasErrors()).thenReturn(false);
            when(userService.getUserDetail(userId)).thenThrow(SQLException.class);
            String view = userController.getUserDetail(userId, br, model);

            verify(model).addAttribute(KEY_DETAIL, null);
            assertThat(view).isEqualTo(viewName);

        }

        @Test
        public void getUserDetail_Return_View_With_Null_When_No_User_Detail() throws SQLException {
            when(br.hasErrors()).thenReturn(false);
            when(userService.getUserDetail(userId)).thenReturn(Optional.empty());

            String view = userController.getUserDetail(userId, br, model);

            verify(model).addAttribute(KEY_DETAIL, null);
            assertThat(view).isEqualTo(viewName);
        }

        @Test
        public void getUserDetail_Return_View_With_Detail() throws SQLException {
            when(br.hasErrors()).thenReturn(false);
            when(userService.getUserDetail(userId)).thenReturn(Optional.of(dummyDto));
            String view = userController.getUserDetail(userId, br, model);

            verify(model).addAttribute(KEY_DETAIL, dummyDto);
            assertThat(view).isEqualTo(viewName);
        }
    }

}
