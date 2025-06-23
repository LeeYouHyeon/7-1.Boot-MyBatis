package com.example.demo.repository;

import com.example.demo.domain.FileVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FileMapper {
    int insertFile(FileVO fileVO);

    List<FileVO> getFiles(long bno);

    int remove(String uuid);

    FileVO getFile(String uuid);

    int removeFiles(long bno);
}
