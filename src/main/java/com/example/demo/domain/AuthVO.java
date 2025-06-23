package com.example.demo.domain;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Getter
public class AuthVO {
    private long id;
    private String email;
    private String auth;
}
