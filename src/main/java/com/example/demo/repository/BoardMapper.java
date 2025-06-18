package com.example.demo.repository;

import com.example.demo.domain.BoardVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    List<BoardVO> getList();

    BoardVO getDetail(long bno);

    int register(BoardVO bvo);

    int remove(long bno);

    void update(BoardVO bvo);
}
