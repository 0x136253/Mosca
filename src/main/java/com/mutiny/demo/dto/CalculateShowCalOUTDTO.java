package com.mutiny.demo.dto;

import java.util.Date;

/**
 * @Author: Anonsmd
 * @Date: 2020/2/10 12:32
 */
public class CalculateShowCalOUTDTO {
    private String data;
    private int num;
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public CalculateShowCalOUTDTO() {
    }

    public CalculateShowCalOUTDTO(String data, int num) {
        this.data = data;
        this.num = num;
    }
}
