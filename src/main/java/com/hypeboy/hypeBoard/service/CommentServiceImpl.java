package com.hypeboy.hypeBoard.service;


import com.hypeboy.hypeBoard.dto.CommentDto;
import com.hypeboy.hypeBoard.dto.ServiceDto;
import com.hypeboy.hypeBoard.entity.Comment;
import com.hypeboy.hypeBoard.entity.CommentStatus;
import com.hypeboy.hypeBoard.repository.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }


    @Override
    public ServiceDto<Boolean> makeStatusDeletedByPostId(Integer postId) {
        ServiceDto<Boolean> resDto = new ServiceDto<>();

        try {
            boolean isSuccess = commentRepository.updateStatusDeleteByPostId(postId);
            resDto.setData(isSuccess);
        } catch (Exception ex) {
            resDto.setError(ex.getMessage());
        }

        return resDto;
    }


    @Override
    public ServiceDto<Boolean> makeStatusDeletedById(Integer commentId) {
        ServiceDto<Boolean> resDto = new ServiceDto<>();

        try {
            boolean isSuccess = commentRepository.updateStatusDeleteById(commentId);
            resDto.setData(isSuccess);
        } catch (Exception ex) {
            resDto.setError(ex.getMessage());
        }

        return resDto;
    }

    @Override
    public ServiceDto<Boolean> editStatus(Integer commentId, CommentStatus status) {
        ServiceDto<Boolean> resDto = new ServiceDto<>();

        try {
            Optional<Comment> optionalComment = commentRepository.findById(commentId);

            if (optionalComment.isPresent()) {
                Comment comment = optionalComment.get();
                comment.setStatus(status);
                boolean isSuccess = commentRepository.update(comment);
                resDto.setData(isSuccess);
            } else {
                resDto.setData(false);
            }

        } catch (Exception ex) {
            resDto.setError(ex.getMessage());
        }

        return resDto;
    }

    @Override
    public ServiceDto<Boolean> editComment(CommentDto dto) {
        ServiceDto<Boolean> resDto = new ServiceDto<>();

        Comment comment = fromDtoToComment(dto);

        try {
            boolean result = commentRepository.update(comment);
            resDto.setData(result);
        } catch (Exception ex) {
            resDto.setError(ex.getMessage());
        }


        return resDto;
    }

    @Override
    public ServiceDto<Boolean> createComment(CommentDto dto) {
        ServiceDto<Boolean> resDto = new ServiceDto<>();

        Comment comment = fromDtoToComment(dto);

        try {
            boolean result = commentRepository.insert(comment);
            resDto.setData(result);
        } catch (Exception ex) {
            resDto.setError(ex.getMessage());
        }


        return resDto;
    }


    @Override
    public ServiceDto<List<Comment>> getCommentList(Integer postId, Integer lastId, Integer count) {
        ServiceDto<List<Comment>> resDto = new ServiceDto<>();

        try {
            List<Comment> result = commentRepository.findByPostId(postId, lastId, count);
            resDto.setData(result);
        } catch (Exception ex) {
            resDto.setError(ex.getMessage());
        }

        return resDto;
    }



    @Override
    public  ServiceDto<List<Comment>> getCommentByPostId(Integer postId, Integer count) {
        Integer defaultLastId = 0;

        ServiceDto<List<Comment>> resDto = new ServiceDto<>();

        try {
            List<Comment> result = commentRepository.findByPostId(postId, defaultLastId, count);
            resDto.setData(result);
        } catch (Exception ex) {
            resDto.setError(ex.getMessage());
        }

        return resDto;
    }



    private Comment fromDtoToComment(CommentDto dto) {
        int postId = dto.getPostId();
        String userId = dto.getUserId();
        String text = dto.getText();
        Integer parentId = dto.getParentId();

        Comment comment = new Comment(postId, userId, text);
        comment.setParentId(parentId);

        return comment;
    }



}
