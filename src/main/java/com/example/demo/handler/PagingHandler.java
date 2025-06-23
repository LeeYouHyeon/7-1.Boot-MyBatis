package com.example.demo.handler;

import com.example.demo.domain.CommentVO;
import com.example.demo.domain.PagingVO;
import lombok.*;

import java.util.List;

@ToString
@Setter
@Getter
public class PagingHandler {
    private long startPage;
    private long endPage;
    private long realEndPage;
    private boolean prev, next;

    private long totalCount;
    private PagingVO pagingVO;

    private List<CommentVO> commentVOList;

    public PagingHandler(long totalCount, PagingVO pagingVO) {
        this.pagingVO = pagingVO;
        this.totalCount = totalCount;

        this.endPage = ceil(pagingVO.getPageNo(), pagingVO.getQty())*10;
        this.startPage = this.endPage - 9;

        this.realEndPage = ceil(totalCount, pagingVO.getQty());
        if (this.realEndPage < this.endPage) this.endPage = this.realEndPage;

        this.prev = this.startPage > 1;
        this.next = this.endPage < this.realEndPage;
    }

    // 댓글용 생성자
    public PagingHandler(int totalCount, PagingVO pagingVO, List<CommentVO> cmtList) {
        this(totalCount, pagingVO);
        this.commentVOList = cmtList;
    }

    private long ceil(long n, long m) {
        return (n - 1)/m + 1;
    }
}
