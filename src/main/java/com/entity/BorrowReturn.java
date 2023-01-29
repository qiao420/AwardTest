package com.entity;

public class BorrowReturn {
    private Integer borrowId;
    private Integer userId;
    private Integer bookId;
    //×´Ì¬  1ÔÚ½è  2ÒÑ»¹
    private Integer status;
    private Long borrowTime;
    private Long returnTime;
    public Integer getBorrowId() {
        return borrowId;
    }
    public void setBorrowId(Integer borrowId) {
        this.borrowId = borrowId;
    }
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public Integer getBookId() {
        return bookId;
    }
    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    public Long getBorrowTime() {
        return borrowTime;
    }
    public void setBorrowTime(Long borrowTime) {
        this.borrowTime = borrowTime;
    }
    public Long getReturnTime() {
        return returnTime;
    }
    public void setReturnTime(Long returnTime) {
        this.returnTime = returnTime;
    }
}
