package com.hypeboy.hypeBoard.unit.comment.service;

import com.hypeboy.hypeBoard.dto.CommentDto;
import com.hypeboy.hypeBoard.dto.ServiceDto;
import com.hypeboy.hypeBoard.entity.Comment;
import com.hypeboy.hypeBoard.entity.CommentStatus;
import com.hypeboy.hypeBoard.repository.CommentRepository;
import com.hypeboy.hypeBoard.service.CommentServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {
    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentServiceImpl commentService;


    private CommentDto validDto;
    private CommentDto invalidDto;
    private Comment validComment;
    private Comment invalidComment;

    private void createDummyComment() throws Exception {
        int existCommentId = 1;
        int existPostId = 1;
        String existUserId = "test1";
        String sampleText = "updated sample text";
        Integer existParentId = null;
        CommentStatus status = new CommentStatus("blocked");

        validComment = new Comment(existCommentId, existPostId, existUserId, sampleText, existParentId, status);

        invalidComment = new Comment(
                existCommentId + 10000,
                existPostId + 10000,
                existUserId + "invalid",
                sampleText,
                existParentId,
                status);
    }

    private void createDummyDto() throws  Exception {
        int existCommentId = 1;
        int existPostId = 1;
        String existUserId = "test1";
        String sampleText = "updated sample text!!!!!";
        Integer existParentId = null;
        CommentStatus status = new CommentStatus("blocked");

        validDto = new CommentDto(existPostId, existUserId, sampleText);

        invalidDto = new CommentDto(existPostId - 1000, existUserId, sampleText);
    }

    @BeforeEach
    public void setUp() throws Exception {
        createDummyDto();
        createDummyComment();
    }

    @Test
    public void test() {
        Assertions.assertThat(true).isTrue();
    }

    @Test
    public void editComment_Return_Fail() throws SQLException {
        String errorMsg = "An error occurred";
        when(commentRepository.update(any(Comment.class))).thenThrow(new SQLException(errorMsg));

        ServiceDto<Boolean> result = commentService.editComment(invalidDto);

        Assertions.assertThat(result.isOk()).isFalse();
        Assertions.assertThat(result.getError().getMsg()).isEqualTo(errorMsg);

    }
    @Test
    public void editComment_Return_Success() throws SQLException {
        when(commentRepository.update(any(Comment.class))).thenReturn(true);

        ServiceDto<Boolean> result = commentService.editComment(validDto);
        Assertions.assertThat(result.isOk()).isTrue();
        Assertions.assertThat(result.getData()).isTrue();
    }

    @Test
    public void createComment_Return_Fail() throws SQLException {
        String errorMsg = "An error occurred";

        when(commentRepository.insert(any(Comment.class))).thenThrow(new SQLException(errorMsg));

        ServiceDto<Boolean> result = commentService.createComment(invalidDto);

        Assertions.assertThat(result.isOk()).isFalse();
        Assertions.assertThat(result.getError().getMsg()).isEqualTo(errorMsg);
    }

    @Test
    public void createComment_Return_Success() throws SQLException {

        when(commentRepository.insert(any(Comment.class))).thenReturn(true);

        ServiceDto<Boolean> result = commentService.createComment(validDto);
        Assertions.assertThat(result.isOk()).isTrue();
        Assertions.assertThat(result.getData()).isTrue();
    }


    @Test
    public void getCommentList_Return_Fail() throws Exception {
        int postId = 1;
        int lastId = 2;
        int limit = 10;
        String errorMsg = "An error occurred";

        when(commentRepository.findByPostId(anyInt(), anyInt(), anyInt())).thenThrow(new Exception(errorMsg));

        ServiceDto<List<Comment>> result = commentService.getCommentList(postId, lastId, limit);

        Assertions.assertThat(result.isOk()).isFalse();
        Assertions.assertThat(result.getError().getMsg()).isEqualTo(errorMsg);
    }



    @Test
    public void getCommentList_Return_Success() throws Exception {
        int postId = 1;
        int lastId = 2;
        int limit = 10;

        List<Comment> mockList = IntStream
                .rangeClosed(lastId, lastId + limit)
                .mapToObj((i) -> validComment)
                .collect(Collectors.toList());

        when(commentRepository.findByPostId(anyInt(), anyInt(), anyInt())).thenReturn(mockList);

        ServiceDto<List<Comment>> result = commentService.getCommentList(postId, lastId, limit);

        Assertions.assertThat(result.isOk()).isTrue();
        Assertions.assertThat(result.getData()).isEqualTo(mockList);
    }

    @Test
    public void getCommentByPostId_Return_Fail() throws Exception {
        int postId = 1;
        int count = 10;
        String errorMsg = "An error occurred";

        when(commentRepository.findByPostId(anyInt(), anyInt(), anyInt())).thenThrow(new Exception(errorMsg));

        ServiceDto<List<Comment>> result = commentService.getCommentByPostId(postId, count);

        Assertions.assertThat(result.isOk()).isFalse();
        Assertions.assertThat(result.getError().getMsg()).isEqualTo(errorMsg);
    }



    @Test
    public void getCommentByPostId_Return_Success() throws Exception {
        int postId = 1;
        int count = 10;

        List<Comment> mockList = IntStream
                .rangeClosed(1, count)
                .mapToObj((i) -> validComment)
                .collect(Collectors.toList());

        when(commentRepository.findByPostId(anyInt(), anyInt(), anyInt())).thenReturn(mockList);

        ServiceDto<List<Comment>> result = commentService.getCommentByPostId(postId, count);

        Assertions.assertThat(result.isOk()).isTrue();
        Assertions.assertThat(result.getData()).isEqualTo(mockList);
    }


}
