package com.example.demo.repository;

import com.example.demo.domain.BoardVO;
import com.example.demo.domain.PagingVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    List<BoardVO> getList(PagingVO pagingVO);

    BoardVO getDetail(long bno);

    int register(BoardVO boardDTO);

    int remove(long bno);

    void update(BoardVO bvo);

    long getTotalCount(PagingVO pagingVO);

    long getBno();
}
