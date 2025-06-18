package com.example.demo.service;

import com.example.demo.domain.BoardVO;

import java.util.List;

public interface BoardService {
    List<BoardVO> getList();

    BoardVO getDetail(long bno);

    int register(BoardVO bvo);

    int remove(long bno);

    void update(BoardVO bvo);
}
