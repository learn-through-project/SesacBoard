package com.ms.controller;

import com.ms.dto.UserDto;
import com.ms.service.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@Controller("/users")
public class UserController {

    @Autowired
    private UserServiceImpl userService;



    @PostMapping("/signup")
    public String signup(@RequestBody @Valid UserDto dto, Errors errors) {
        log.info("wtf");
        if (errors.hasErrors()) {
            log.info("/signup invalid: >>" + errors.getFieldError());
            return "home";
        }

        log.info("/signup controller: >> " + dto);
        userService.save(dto);

        return "home";
    }

}
