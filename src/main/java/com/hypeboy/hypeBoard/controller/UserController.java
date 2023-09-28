package com.hypeboy.hypeBoard.controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

public interface UserController {
    public String getUserDetail(@PathVariable String userId, BindingResult br, Model model);

}
