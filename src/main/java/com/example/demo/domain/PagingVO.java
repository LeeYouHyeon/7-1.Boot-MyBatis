package com.example.demo.domain;

import lombok.*;

@ToString
@AllArgsConstructor
@Setter
@Getter
public class PagingVO {
    private long pageNo; // 현재 페이지 번호
    private int qty; // 한 페이지에 표시할 게시글 수
    
    //검색
    private String type;
    private String keyword;

    public PagingVO() {
        // 페이징 기본값
        this.pageNo = 1;
        this.qty = 10;
    }

    public PagingVO(int pageNo, int qty) {
        this.pageNo = pageNo;
        this.qty = qty;
    }

    public long getStartIndex() {
        return (this.pageNo - 1) * this.qty;
    }

    public String[] getTypeToArray() {
        return this.type == null ? new String[] {} : type.split("");
    }
}
