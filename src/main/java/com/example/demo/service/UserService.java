package com.example.demo.service;

import com.example.demo.domain.PagingVO;
import com.example.demo.domain.UserVO;

import java.util.List;

public interface UserService {
    int signup(UserVO userVO);

    void update(UserVO userVO);

    long getTotalCount(PagingVO pagingVO);

    List<UserVO> getList(PagingVO pagingVO);

    void delete(String email);
}
