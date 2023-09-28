package com.hypeboy.hypeBoard.entity;

import com.hypeboy.hypeBoard.dto.UserDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Data
@Getter
//테이블 이름도 컬럼과 동일하게 이름을 설정해줘야 한다.
public class User {

    //컬럼 네임이 테이블과 같아야 한다.
    private String id;

    private String name;

    private String pwd;

    private String phone;

    private String email;

    private String nickname;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public User(String id, String name, String pwd, String phone, String email, String nickname) {
        this.id = id;
        this.name = name;
        this.pwd = pwd;
        this.phone = phone;
        this.email = email;
        this.nickname = nickname;
    }
    public User(){
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    public User(UserDto userDto) {
        this.id = userDto.getId();
        this.pwd = userDto.getPwd();
        this.name = userDto.getName();
        this.phone = userDto.getPhone();
        this.email = userDto.getEmail();
        this.nickname = userDto.getNickname();
    }
    public User(String id, String pwd){
        this.id = id;
        this.pwd = pwd;
    }

}