package com.imooc.example.dto;

/**
 * Created by mavlarn on 2018/2/14.
 */
//优化服务间的调用，创建共同对象
public class OrderDTO {

    private Long id;

    private String title;

    private String detail;

    private int amount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
