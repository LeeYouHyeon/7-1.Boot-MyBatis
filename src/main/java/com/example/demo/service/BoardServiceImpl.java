package com.example.demo.service;

import com.example.demo.domain.BoardDTO;
import com.example.demo.domain.BoardVO;
import com.example.demo.domain.FileVO;
import com.example.demo.domain.PagingVO;
import com.example.demo.repository.BoardMapper;
import com.example.demo.repository.FileMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class BoardServiceImpl implements BoardService {

    private final BoardMapper boardMapper;
    private final FileMapper fileMapper;

    @Override
    public List<BoardVO> getList(PagingVO pagingVO) {
        return boardMapper.getList(pagingVO);
    }

    @Override
    public BoardDTO getDetail(long bno) {
        BoardVO boardVO = boardMapper.getDetail(bno);
        List<FileVO> fileVOList = fileMapper.getFiles(bno);
        return new BoardDTO(boardVO, fileVOList);
    }

    @Transactional
    @Override
    public int register(BoardDTO boardDTO) {
        int isOk = boardMapper.register(boardDTO.getBoardVO());
        if (boardDTO.getFileList() == null) return isOk;

        if (!boardDTO.getFileList().isEmpty()) {
            // fileVO를 저장
            // 가장 큰 bno를 가져옴
            long bno = boardMapper.getBno();
            for (FileVO fileVO : boardDTO.getFileList()) {
                fileVO.setBno(bno);
                isOk *= fileMapper.insertFile(fileVO);
            }
        }

        return isOk;
    }

    @Override
    public int remove(long bno) {
        return boardMapper.remove(bno);
    }

    @Transactional
    @Override
    public void update(BoardDTO boardDTO) {
        List<FileVO> files = boardDTO.getFileList();
        if (files != null)
            for (FileVO fileVO : boardDTO.getFileList()) {
                fileMapper.insertFile(fileVO);
            }
        boardMapper.update(boardDTO.getBoardVO());
    }

    @Override
    public long getTotalCount(PagingVO pagingVO) {
        return boardMapper.getTotalCount(pagingVO);
    }

    @Override
    public int removeFile(String uuid) {
        return fileMapper.remove(uuid);
    }

    @Override
    public FileVO getFile(String uuid) {
        return fileMapper.getFile(uuid);
    }

    @Override
    public int removeFiles(long bno) {
        return fileMapper.removeFiles(bno);
    }

    @Override
    public List<FileVO> getFiles(long bno) {
        return fileMapper.getFiles(bno);
    }
}
