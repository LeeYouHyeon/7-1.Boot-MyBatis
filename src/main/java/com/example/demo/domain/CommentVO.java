package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Setter
@Getter
public class CommentVO {
    private long cno;
    private long bno;
    private String writer;
    private String content;
    private String regDate;
}
