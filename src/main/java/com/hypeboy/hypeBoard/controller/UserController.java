package com.hypeboy.hypeBoard.controller;

import com.hypeboy.hypeBoard.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
@SessionAttributes("user")
public class UserController {
    private UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("login")
    public String callLoginPage(){
        return "login";
    }
    @PostMapping("/myPage")
    public String login(@RequestParam("id") String id, @RequestParam("pwd") String pwd, Model model){
        model.addAttribute("user",userService.join(id,pwd));
        return "mypage";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "login";
    }
}
