package com.example.demo.service;

import com.example.demo.domain.BoardVO;
import com.example.demo.repository.BoardMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class BoardServiceImpl implements BoardService {

    private final BoardMapper boardMapper;

    @Override
    public List<BoardVO> getList() {
        return boardMapper.getList();
    }

    @Override
    public BoardVO getDetail(long bno) {
        return boardMapper.getDetail(bno);
    }

    @Override
    public int register(BoardVO bvo) {
        return boardMapper.register(bvo);
    }

    @Override
    public int remove(long bno) {
        return boardMapper.remove(bno);
    }

    @Override
    public void update(BoardVO bvo) {
        boardMapper.update(bvo);
    }
}
