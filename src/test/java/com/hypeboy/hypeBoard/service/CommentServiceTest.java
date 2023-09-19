package com.hypeboy.hypeBoard.service;

import com.hypeboy.hypeBoard.dto.CommentDto;
import com.hypeboy.hypeBoard.entity.Comment;
import com.hypeboy.hypeBoard.repository.CommentRepository;
import com.hypeboy.hypeBoard.service.converter.CommentConverter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {
    @Mock
    private CommentRepository commentRepository;

    @Mock
    private CommentConverter commentConverter;

    @InjectMocks
    private CommentService commentService;

    @Test
    public void CommentService_createComment_ReturnsCommentDto() {
        CommentDto reqDto = CommentDto.builder()
                .postId(1)
                .userId("test1")
                .text("This is the test comment")
                .build();

        Comment commentDummy = Comment.builder()
                .id(1L)
                .postId((long) reqDto.getPostId())
                .userId(reqDto.getUserId())
                .text(reqDto.getText())
                .updatedAt(LocalDateTime.now())
                .parentId(null)
                .build();

        CommentDto resDto =  CommentDto.builder()
                .commentId(Math.toIntExact(commentDummy.getId()))
                .postId(Math.toIntExact(commentDummy.getPostId()))
                .userId(commentDummy.getUserId())
                .text(commentDummy.getText())
                .updatedAt(commentDummy.getUpdatedAt())
                .build();

        when(commentConverter.fromDtoToComment(Mockito.any(CommentDto.class))).thenReturn(commentDummy);
        when(commentConverter.fromCommentToDto(Mockito.any(Comment.class))).thenReturn(resDto);
        when(commentRepository.save(Mockito.any(Comment.class))).thenReturn(commentDummy);

        CommentDto dto = commentService.createComment(reqDto);

        Assertions.assertThat(dto.getCommentId()).isEqualTo(Math.toIntExact(commentDummy.getId()));
        Assertions.assertThat(dto.getPostId()).isEqualTo(Math.toIntExact(commentDummy.getPostId()));
        Assertions.assertThat(dto.getUserId()).isEqualTo(commentDummy.getUserId());
        Assertions.assertThat(dto.getText()).isEqualTo(commentDummy.getText());
        Assertions.assertThat(dto.getUpdatedAt()).isEqualTo(commentDummy.getUpdatedAt());
    }
}
