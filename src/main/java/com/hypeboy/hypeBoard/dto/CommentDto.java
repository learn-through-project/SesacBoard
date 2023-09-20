package com.hypeboy.hypeBoard.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class CommentDto {

    @NotNull(message = "게시글 id는 필수 항목입니다.")
    private Integer postId;

    @NotBlank(message = "유저 id는 필수 항목입니다.")
    private String userId;

    @NotEmpty(message = "내용은 필수 항목입니다.")
    private String text;

    private Integer parentId;
    private Integer commentId;
    private LocalDateTime updatedAt;
}

