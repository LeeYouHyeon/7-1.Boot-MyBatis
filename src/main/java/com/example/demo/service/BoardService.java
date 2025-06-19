package com.example.demo.service;

import com.example.demo.domain.BoardDTO;
import com.example.demo.domain.BoardVO;
import com.example.demo.domain.FileVO;
import com.example.demo.domain.PagingVO;

import java.util.List;

public interface BoardService {
    List<BoardVO> getList(PagingVO pagingVO);

    BoardDTO getDetail(long bno);

    int register(BoardDTO boardDTO);

    int remove(long bno);

    void update(BoardDTO boardDTOvo);

    long getTotalCount(PagingVO pagingVO);

    int removeFile(String uuid);

    FileVO getFile(String uuid);

    int removeFiles(long bno);

    List<FileVO> getFiles(long bno);
}
