package com.vitalog.spring_diet.vo;

import org.apache.ibatis.type.Alias;

@Alias("PagingVo")
public class PagingVo {

    private int count;
    private int currentPage;
    private int pageOfContentCount;
    private long mno; //add_25.08.27
    private final int PAGE_GROUP_OF_COUNT = 4;

    public PagingVo(int count, int currentPage, int pageOfContentCount){
        this.count = count;
        this.currentPage = currentPage;
        this.pageOfContentCount = pageOfContentCount;
    }

    public int getCurrentPage(){
        return currentPage;
    }

    public int getPageOfContentCount(){
        return pageOfContentCount;
    }

    public void setPageOfContentCount(int pageOfContentCount){
        this.pageOfContentCount = pageOfContentCount;
    }

    //add_25.08.27
    public long getMno() {
        return mno;
    }
    public void setMno(long mno) {
        this.mno = mno;
    }

    //전체 페이지 개수 : 전체 게시글 개수 / 한페이지당 출력할 게시글 개수 + (나머지가 0 아니면 1)
    public int getTotalPage() {
        return count / pageOfContentCount + (count % pageOfContentCount == 0 ? 0 : 1);
    }

    //전체 페이지 그룹 개수 : 전체 페이지 개수 / 게시판 하단에 나타낼 페이지 번호 개수 + (나머지가 0 아니면 1)
    public int getTotalPageGroup() {
        return getTotalPage() / PAGE_GROUP_OF_COUNT +
                (getTotalPage() % PAGE_GROUP_OF_COUNT == 0 ? 0 : 1);
    }

    //현재 페이지의 그룹번호
    public int getCurrentPageGroupNo() {
        return currentPage / PAGE_GROUP_OF_COUNT +
                (currentPage % PAGE_GROUP_OF_COUNT == 0 ? 0 : 1);
    }

    //현재 페이지 그룹의 시작 페이지 번호
    public int getStartPageOfPageGroup() {
        return (getCurrentPageGroupNo()-1) * PAGE_GROUP_OF_COUNT + 1;
    }
    //현재 페이지 그룹의 마지막 페이지 번호
    public int getEndPageOfPageGroup() {
        int lastNo = getCurrentPageGroupNo() * PAGE_GROUP_OF_COUNT;
        if(lastNo > getTotalPage())
            lastNo = getTotalPage();
        return lastNo;
    }
    //이전 페이지 그룹이 있냐?
    public boolean isPreviousPageGroup() {
        return getCurrentPageGroupNo() > 1;
    }
    //다음 페이지 그룹이 있냐?
    public boolean isNextPageGroup() {
        return getCurrentPageGroupNo() <  getTotalPageGroup();
    }

    @Override
    public String toString() {
        return "PaggingVO [count=" + count + ", currentPage=" + currentPage + ", pageOfContentCount="
                + pageOfContentCount + ", PAGE_GROUP_OF_COUNT=" + PAGE_GROUP_OF_COUNT + ", getCurrentPage()="
                + getCurrentPage() + ", getTotalPage()=" + getTotalPage() + ", getTotalPageGroup()="
                + getTotalPageGroup() + ", getCurrentPageGroupNo()=" + getCurrentPageGroupNo()
                + ", getStartPageOfPageGroup()=" + getStartPageOfPageGroup() + ", getEndPageOfPageGroup()="
                + getEndPageOfPageGroup() + ", isPreviousPageGroup()=" + isPreviousPageGroup() + ", isNextPageGroup()="
                + isNextPageGroup() + "]";
    }

}