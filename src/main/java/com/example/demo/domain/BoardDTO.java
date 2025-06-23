package com.example.demo.domain;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Getter
public class BoardDTO {
    private BoardVO boardVO;
    private List<FileVO> fileList;
}
