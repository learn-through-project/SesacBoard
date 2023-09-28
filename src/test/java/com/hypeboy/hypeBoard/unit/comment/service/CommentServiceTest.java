package com.hypeboy.hypeBoard.unit.comment.service;

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

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {
    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentServiceImpl commentService;


    private Comment dummyComment;

    private void createDummyComment() throws Exception {
        int existCommentId = 1;
        int existPostId = 1;
        String existUserId = "test1";
        String sampleText = "updated sample text";
        Integer existParentId = null;
        CommentStatus status = new CommentStatus("blocked");

        dummyComment = new Comment(existCommentId, existPostId, existUserId, sampleText, existParentId, status);
    }

    @BeforeEach
    public void setUp() throws Exception {
        createDummyComment();
    }

    @Test
    public void test() {
        Assertions.assertThat(true).isTrue();
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
                .mapToObj((i) -> dummyComment)
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
                .mapToObj((i) -> dummyComment)
                .collect(Collectors.toList());

        when(commentRepository.findByPostId(anyInt(), anyInt(), anyInt())).thenReturn(mockList);

        ServiceDto<List<Comment>> result = commentService.getCommentByPostId(postId, count);

        Assertions.assertThat(result.isOk()).isTrue();
        Assertions.assertThat(result.getData()).isEqualTo(mockList);
    }


}
