package com.hypeboy.hypeBoard.controller;

import com.hypeboy.hypeBoard.dto.CommentDto;
import com.hypeboy.hypeBoard.dto.ErrorDto;
import com.hypeboy.hypeBoard.enums.EndPoint;
import com.hypeboy.hypeBoard.service.CommentService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
public class CommentController {

    @Autowired
    CommentService commentServiceImpl;

    @PostMapping(EndPoint.Path.COMMENT)
    public ModelAndView createComment(@Valid CommentDto dto, BindingResult br) {

        if (br.hasErrors()) {
            ModelAndView mv = new ModelAndView(EndPoint.Path.COMMENT_FAIL);
            ErrorDto error = makeErrorDto(br);
            mv.addObject("error", error);
            return mv;
        }

        ModelAndView mv = new ModelAndView(EndPoint.Path.COMMENT_SUCCESS);
        CommentDto result = null;
        mv.addObject("result", result);
        return mv;
    }


    private ErrorDto makeErrorDto(BindingResult bindingResult) {
        String field = bindingResult.getFieldError().getField();
        String message = bindingResult.getFieldError().getDefaultMessage();
        return new ErrorDto(field, message);
    }

}
