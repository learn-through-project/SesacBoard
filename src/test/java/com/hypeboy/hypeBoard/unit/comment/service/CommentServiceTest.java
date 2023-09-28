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
import java.util.Optional;
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


    private void createDummyComment() throws Exception {
        int existCommentId = 1;
        int existPostId = 1;
        String existUserId = "test1";
        String sampleText = "updated sample text";
        Integer existParentId = null;
        CommentStatus status = new CommentStatus("blocked");

        validComment = new Comment(existCommentId, existPostId, existUserId, sampleText, existParentId, status);

    }

    private void createDummyDto() {
        int existPostId = 1;
        String existUserId = "test1";
        String sampleText = "updated sample text!!!!!";
        Integer existParentId = null;

        validDto = new CommentDto(existPostId, existUserId, sampleText);
        validDto.setParentId(existParentId);

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
    public void deleteAllDeletedStatusComment_Return_Fail() throws Exception {
        String errorMsg = "An error occurred";
        when(commentRepository.deletePermanently()).thenThrow(new Exception(errorMsg));
        ServiceDto<Boolean> result = commentService.deleteAllDeletedStatusComment();

        Assertions.assertThat(result.isOk()).isFalse();
        Assertions.assertThat(result.getError().getMsg()).isEqualTo(errorMsg);
    }



    @Test
    public void deleteAllDeletedStatusComment_Return_Success() throws Exception {
        when(commentRepository.deletePermanently()).thenReturn(true);
        ServiceDto<Boolean> result = commentService.deleteAllDeletedStatusComment();

        Assertions.assertThat(result.isOk()).isTrue();
        Assertions.assertThat(result.getData()).isTrue();
    }

    @Test
    public void makeStatusDeletedByPostId_Return_Fail() throws Exception {
        Integer invalidPostId = 1 + 10000;
        String errorMsg = "An error occurred";
        when(commentRepository.updateStatusDeleteByPostId(anyInt())).thenThrow(new Exception(errorMsg));

        ServiceDto<Boolean> result = commentService.makeStatusDeletedByPostId(invalidPostId);

        Assertions.assertThat(result.isOk()).isFalse();
        Assertions.assertThat(result.getError().getMsg()).isEqualTo(errorMsg);
    }

    @Test
    public void makeStatusDeletedByPostId_Return_Success() throws Exception {
        Integer validPostId = 1;
        when(commentRepository.updateStatusDeleteByPostId(anyInt())).thenReturn(true);

        ServiceDto<Boolean> result = commentService.makeStatusDeletedByPostId(validPostId);

        Assertions.assertThat(result.isOk()).isTrue();
        Assertions.assertThat(result.getData()).isTrue();
    }

    @Test
    public void makeStatusDeletedById_Return_Fail() throws Exception {
        Integer invalidCommentId = 1 + 10000;
        String errorMsg = "An error occurred";
        when(commentRepository.updateStatusDeleteById(anyInt())).thenThrow(new Exception(errorMsg));

        ServiceDto<Boolean> result = commentService.makeStatusDeletedById(invalidCommentId);

        Assertions.assertThat(result.isOk()).isFalse();
        Assertions.assertThat(result.getError().getMsg()).isEqualTo(errorMsg);
    }

    @Test
    public void makeStatusDeletedById_Return_Success() throws Exception {
        Integer validCommentId = 1;
        when(commentRepository.updateStatusDeleteById(anyInt())).thenReturn(true);

        ServiceDto<Boolean> result = commentService.makeStatusDeletedById(validCommentId);

        Assertions.assertThat(result.isOk()).isTrue();
        Assertions.assertThat(result.getData()).isTrue();
    }


    @Test
    public void markReported_Return_Fail_When_Select_Throw_Exception() throws Exception {
        Integer invalidCommentId = 1 + 10000;
        String errorMsg = "An error occurred";
        CommentStatus status = new CommentStatus("reported");
        when(commentRepository.findById(anyInt())).thenThrow(new Exception(errorMsg));

        ServiceDto<Boolean> result = commentService.editStatus(invalidCommentId, status);

        Assertions.assertThat(result.isOk()).isFalse();
        Assertions.assertThat(result.getError().getMsg()).isEqualTo(errorMsg);
    }

    @Test
    public void markReported_Return_Success_When_Find_Return_Empty() throws Exception {
        Integer invalidCommentId = 1 + 10000;
        CommentStatus status = new CommentStatus("reported");
        when(commentRepository.findById(anyInt())).thenReturn(Optional.empty());

        ServiceDto<Boolean> result = commentService.editStatus(invalidCommentId, status);

        Assertions.assertThat(result.isOk()).isTrue();
        Assertions.assertThat(result.getData()).isFalse();
    }

    @Test
    public void markReported_Return_Fail_When_Update_Throw_Exception() throws Exception {
        Integer invalidCommentId = 1 + 10000;
        String errorMsg = "An error occurred";
        CommentStatus status = new CommentStatus("reported");
        when(commentRepository.findById(anyInt())).thenReturn(Optional.of(validComment));
        when(commentRepository.update(any(Comment.class))).thenThrow(new SQLException(errorMsg));

        ServiceDto<Boolean> result = commentService.editStatus(invalidCommentId, status);

        Assertions.assertThat(result.isOk()).isFalse();
        Assertions.assertThat(result.getError().getMsg()).isEqualTo(errorMsg);
    }

    @Test
    public void markReported_Return_Success_When_Update_Return_Success() throws Exception {
        Integer validCommentId = 1;
        CommentStatus status = new CommentStatus("reported");
        when(commentRepository.findById(anyInt())).thenReturn(Optional.of(validComment));
        when(commentRepository.update(any(Comment.class))).thenReturn(true);

        ServiceDto<Boolean> result = commentService.editStatus(validCommentId, status);

        Assertions.assertThat(result.isOk()).isTrue();
        Assertions.assertThat(result.getData()).isTrue();
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
