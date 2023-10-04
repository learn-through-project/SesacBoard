package com.hypeboy.hypeBoard.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class PostDto {
  //  @NotBlank(message = "제목은 필수 입력 값입니다.")
    private String title;
    @NotBlank(message = "텍스트는 필수 입력 값입니다.")
    private String textContent;
    @NotBlank(message = "카테고리는 필수 입력 값입니다.")
    private String category;
    private String nickname;

    private String userId;

    public PostDto(String title, String textContent, String category, String nickname, String userId) {
        this.title = title;
        this.textContent = textContent;
        this.category = category;
        this.nickname = nickname;
        this.userId = userId;
    }

    public PostDto(String title, String textContent, String category) {
        this.title = title;
        this.textContent = textContent;
        this.category = category;
    }


}
