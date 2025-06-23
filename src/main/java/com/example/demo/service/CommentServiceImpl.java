package com.example.demo.service;

import com.example.demo.domain.CommentVO;
import com.example.demo.domain.PagingVO;
import com.example.demo.repository.CommentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;

    @Override
    public int post(CommentVO commentVO) {
        return commentMapper.register(commentVO);
    }

    @Override
    public int getTotalCount(long bno) {
        return commentMapper.getTotalCount(bno);
    }

    @Override
    public List<CommentVO> getList(long bno, PagingVO pagingVO) {
        return commentMapper.getList(bno, pagingVO);
    }

    @Override
    public int update(CommentVO commentVO) {
        return commentMapper.update(commentVO);
    }

    @Override
    public int delete(long cno) {
        return commentMapper.delete(cno);
    }
}
