package com.example.demo.controller;

import com.example.demo.domain.BoardDTO;
import com.example.demo.domain.BoardVO;
import com.example.demo.domain.FileVO;
import com.example.demo.domain.PagingVO;
import com.example.demo.handler.FileHandler;
import com.example.demo.handler.FileRemoveHandler;
import com.example.demo.handler.PagingHandler;
import com.example.demo.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("/board/*")
@RequiredArgsConstructor
@Slf4j
@Controller
public class BoardController {

    private final BoardService boardService;
    private final FileHandler fileHandler;
    private final FileRemoveHandler fileRemoveHandler;

    @GetMapping("/list")
    public void list(Model model, PagingVO pagingVO) {
        long totalCount = boardService.getTotalCount(pagingVO);
        List<BoardVO> list = boardService.getList(pagingVO);
        PagingHandler pagingHandler = new PagingHandler(totalCount, pagingVO);
        model.addAttribute("list", list);
        model.addAttribute("ph", pagingHandler);
    }

    @GetMapping({"/detail", "/modify"})
    public void detail(Model model, @RequestParam("bno") long bno) {
        model.addAttribute("bdto", boardService.getDetail(bno));
    }

    @GetMapping("/register")
    public void register() {}

    @PostMapping("/register")
    public String register(BoardVO bvo,
                           @RequestParam(name="files", required = false)MultipartFile[] files) {
        List<FileVO> fileList = null;
        if (files != null && files[0].getSize() > 0) {
            fileList = fileHandler.uploadFiles(files);
            log.info(">>>> fileList in register >> {}", fileList);
        }

        int isOk = boardService.register(new BoardDTO(bvo, fileList));
        log.info(">>>> bvo register >> {}", isOk > 0 ? "성공" : "실패");
        return "redirect:/board/list";
    }

    @PostMapping("/update")
    public String update(BoardVO boardVO,
                         @RequestParam(name="files", required = false)MultipartFile[] files) {
        List<FileVO> fileList = null;
        if (files != null && files[0].getSize() > 0) {
            fileList = fileHandler.uploadFiles(files);
            log.info(">>>> fileList in update >> {}", fileList);
            for (FileVO fileVO : fileList) {
                fileVO.setBno(boardVO.getBno());
            }
        }
        boardService.update(new BoardDTO(boardVO, fileList));
        return "redirect:/board/detail?bno=" + boardVO.getBno();
    }

    @GetMapping("/remove")
    public String remove(long bno) {
        int isOk = boardService.remove(bno);
        List<FileVO> fileVOList = boardService.getFiles(bno);
        for (FileVO fileVO : fileVOList) {
            fileRemoveHandler.deleteFile(fileVO);
        }
        log.info(">>>> file remove {}", boardService.removeFiles(bno) > 0 ? "성공" : "실패");
        log.info(">>>> remove {} >> {}", bno, isOk > 0 ? "성공" : "실패");
        return "redirect:/board/list";
    }

    @ResponseBody
    @DeleteMapping("/removeFile/{uuid}")
    public ResponseEntity<String> removeFile(@PathVariable("uuid") String uuid) {
        log.info(">>>> remove file uuid {}", uuid);
        FileVO fileVO = boardService.getFile(uuid);
        boolean isDel = fileRemoveHandler.deleteFile(fileVO);
        return new ResponseEntity<>(String.valueOf(boardService.removeFile(uuid)), HttpStatus.OK);
    }
}
