package com.hypeboy.hypeBoard.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class CommentDto {

    @NotNull(message = "게시글 id는 필수 항목입니다.")
    private final Integer postId;

    @NotBlank(message = "유저 id는 필수 항목입니다.")
    private final String userId;

    @NotEmpty(message = "내용은 필수 항목입니다.")
    private final String text;

    private Integer parentId;
    private Integer commentId;
    private LocalDateTime updatedAt;
}