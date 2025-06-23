package com.example.demo.security;

import com.example.demo.domain.UserVO;
import com.example.demo.repository.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Slf4j
public class CustomUserService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info(">>> user email >> {}", username);
        UserVO userVO = userMapper.getUserByEmail(username);
        if (userVO == null) {
            log.info("user not found");
            throw new UsernameNotFoundException("이메일을 찾을 수 없습니다.");
        }
        log.info(">>> user >> {}", userVO);

        userVO.setAuthList(userMapper.getAuthList(username));
        return new AuthUser(userVO);
    }
}
