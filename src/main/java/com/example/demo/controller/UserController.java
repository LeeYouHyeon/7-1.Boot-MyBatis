package com.example.demo.controller;

import com.example.demo.domain.PagingVO;
import com.example.demo.domain.UserVO;
import com.example.demo.handler.PagingHandler;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@RequestMapping("/user/*")
@RequiredArgsConstructor
@Slf4j
@Controller
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/signup")
    public void signup() { }

    @PostMapping("/signup")
    public String signup(UserVO userVO, RedirectAttributes redirectAttributes) {
        userVO.setPwd(passwordEncoder.encode(userVO.getPwd()));
        int isOk = userService.signup(userVO);

        return isOk > 0 ? "redirect:/user/login" : "redirect:/user/signup";
    }

    @GetMapping("/login")
    public void login() {}

    @GetMapping("/info")
    public void info() {}

    @PostMapping("/update")
    public String update(UserVO userVO) {
        userService.update(userVO);
        return "redirect:/user/logout";
    }

    @GetMapping("/list")
    public void list(Model model, PagingVO pagingVO) {
        long totalCount = userService.getTotalCount(pagingVO);
        List<UserVO> list = userService.getList(pagingVO);

        PagingHandler pagingHandler = new PagingHandler(totalCount, pagingVO);
        model.addAttribute("list", list);
        model.addAttribute("ph", pagingHandler);
    }

    @GetMapping("/remove")
    public String remove(Principal principal) {
        userService.delete(principal.getName());
        return "redirect:/user/logout";
    }
}
