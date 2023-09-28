package com.hypeboy.hypeBoard.controller;

import com.hypeboy.hypeBoard.dto.UserDetailDto;
import com.hypeboy.hypeBoard.enums.EndPoint;
import com.hypeboy.hypeBoard.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.Optional;

@Log4j2
@Controller
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @Autowired
    public UserControllerImpl(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(EndPoint.Path.USER_DETAIL)
    @Override
    public String getUserDetail(@PathVariable("userId") String userId, BindingResult br, Model model) {
        String viewName = "user_detail/user_detail";

        if (br.hasErrors()) {
            model.addAttribute("detail", null);
            return viewName;
        }


        UserDetailDto detail = null;
        try {
            Optional<UserDetailDto> userDetail = userService.getUserDetail(userId);
            detail = userDetail.orElse(null);
        } catch (SQLException ex) {
            log.error("Error in getUserDetail: >> " + ex.getMessage());
        }


        model.addAttribute("detail", detail);
        return viewName;
    }

}
