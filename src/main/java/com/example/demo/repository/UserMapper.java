package com.example.demo.repository;

import com.example.demo.domain.AuthVO;
import com.example.demo.domain.PagingVO;
import com.example.demo.domain.UserVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    int register(UserVO userVO);

    int insertAuthInit(String email);

    UserVO getUserByEmail(String email);

    List<AuthVO> getAuthList(String email);

    void update(UserVO userVO);

    long getTotalCount(PagingVO pagingVO);

    List<UserVO> getList(PagingVO pagingVO);

    void deleteAuths(String email);

    void deleteUser(String email);
}
