package com.hypeboy.hypeBoard.service;

import com.hypeboy.hypeBoard.dto.CommentDto;
import com.hypeboy.hypeBoard.dto.ServiceDto;
import com.hypeboy.hypeBoard.entity.Comment;

import java.util.List;

public interface CommentService {
    public ServiceDto<List<Comment>> getCommentByPostId(Integer postId, Integer count);

    public ServiceDto<List<Comment>> getCommentList(Integer postId, Integer lastId, Integer count);
}
