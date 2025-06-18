package com.example.demo.controller;

import com.example.demo.domain.BoardVO;
import com.example.demo.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/board/*")
@RequiredArgsConstructor
@Slf4j
@Controller
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    public void list(Model model) {
        model.addAttribute("list", boardService.getList());
    }

    @GetMapping({"/detail", "/modify"})
    public void detail(Model model, @RequestParam("bno") long bno) {
        model.addAttribute("bvo", boardService.getDetail(bno));
    }

    @GetMapping("/register")
    public void register() {}

    @PostMapping("/register")
    public String register(BoardVO bvo) {
        log.info(">>>> register bvo >> {}", bvo);
        int isOk = boardService.register(bvo);
        log.info(">>>> bvo register >> {}", isOk > 0 ? "성공" : "실패");
        return "redirect:/board/list";
    }

    @PostMapping("/update")
    public String update(BoardVO bvo) {
        boardService.update(bvo);
        return "redirect:/board/detail?bno=" + bvo.getBno();
    }

    @GetMapping("/remove")
    public String remove(@RequestParam("bno") long bno) {
        int isOk = boardService.remove(bno);
        log.info(">>>> remove {} >> {}", bno, isOk > 0 ? "성공" : "실패");
        return "redirect:/board/list";
    }
}
