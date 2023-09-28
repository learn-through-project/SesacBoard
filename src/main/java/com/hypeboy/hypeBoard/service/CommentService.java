package com.hypeboy.hypeBoard.service;

import com.hypeboy.hypeBoard.dto.CommentDto;
import com.hypeboy.hypeBoard.dto.ServiceDto;
import com.hypeboy.hypeBoard.entity.Comment;
import com.hypeboy.hypeBoard.entity.CommentStatus;

import java.util.List;

public interface CommentService {
    public ServiceDto<List<Comment>> getCommentByPostId(Integer postId, Integer count);

    public ServiceDto<List<Comment>> getCommentList(Integer postId, Integer lastId, Integer count);

    public ServiceDto<Boolean> createComment(CommentDto dto);

    public ServiceDto<Boolean> editComment(CommentDto dto);

    public  ServiceDto<Boolean> editStatus(Integer commentId, CommentStatus status);

    public ServiceDto<Boolean> makeStatusDeletedById(Integer commentId);

    public ServiceDto<Boolean> makeStatusDeletedByPostId(Integer postId);
}
