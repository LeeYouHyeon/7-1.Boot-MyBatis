package com.example.demo.service;

import com.example.demo.domain.CommentVO;
import com.example.demo.domain.PagingVO;

import java.util.List;

public interface CommentService {
    int post(CommentVO commentVO);

    int getTotalCount(long bno);

    List<CommentVO> getList(long bno, PagingVO pagingVO);

    int update(CommentVO commentVO);

    int delete(long cno);
}
