package com.example.demo.repository;

import com.example.demo.domain.CommentVO;
import com.example.demo.domain.PagingVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper {
    int register(CommentVO commentVO);

    int getTotalCount(long bno);

    List<CommentVO> getList(@Param("bno") long bno, @Param("pagingVO") PagingVO pagingVO);

    int update(CommentVO commentVO);

    int delete(long cno);
}
