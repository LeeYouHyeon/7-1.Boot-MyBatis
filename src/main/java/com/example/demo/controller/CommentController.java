package com.example.demo.controller;

import com.example.demo.domain.CommentVO;
import com.example.demo.domain.PagingVO;
import com.example.demo.handler.PagingHandler;
import com.example.demo.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/comment/*")
@Slf4j
@RequiredArgsConstructor
@RestController
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/post")
    @ResponseBody
    public String post(@RequestBody CommentVO commentVO) {
        return String.valueOf(commentService.post(commentVO));
    }

    @ResponseBody
    @GetMapping("/{bno}/{page}")
    public PagingHandler getList(@PathVariable("bno") long bno, @PathVariable("page") int page) {
        int totalCount = commentService.getTotalCount(bno);

        PagingVO pagingVO = new PagingVO(page, 10);
        List<CommentVO> commentVOList = commentService.getList(bno, pagingVO);
        return new PagingHandler(totalCount, pagingVO, commentVOList);
    }

    @ResponseBody
    @PutMapping("/update")
    public String update(@RequestBody CommentVO commentVO) {
        return String.valueOf(commentService.update(commentVO));
    }

    @ResponseBody
    @DeleteMapping("/{cno}")
    public String delete(@PathVariable("cno") long cno) {
        return String.valueOf(commentService.delete(cno));
    }
}
