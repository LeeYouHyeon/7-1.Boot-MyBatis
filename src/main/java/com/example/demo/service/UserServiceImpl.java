package com.example.demo.service;

import com.example.demo.domain.PagingVO;
import com.example.demo.domain.UserVO;
import com.example.demo.repository.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    @Transactional
    @Override
    public int signup(UserVO userVO) {
        int isOk = userMapper.register(userVO);
        if (isOk == 0) return 0;
        return userMapper.insertAuthInit(userVO.getEmail());
    }

    @Override
    public void update(UserVO userVO) {
        userMapper.update(userVO);
    }

    @Override
    public long getTotalCount(PagingVO pagingVO) {
        return userMapper.getTotalCount(pagingVO);
    }

    @Override
    public List<UserVO> getList(PagingVO pagingVO) {
        List<UserVO> list = userMapper.getList(pagingVO);
        for (UserVO userVO : list) {
            userVO.setAuthList(userMapper.getAuthList(userVO.getEmail()));
        }
        return list;
    }

    @Transactional
    @Override
    public void delete(String email) {
        userMapper.deleteAuths(email);
        userMapper.deleteUser(email);
    }
}
