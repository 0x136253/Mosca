package com.mutiny.demo.dto;

import com.mutiny.demo.FHE.Key;

import java.math.BigInteger;

/**
 * @Author: Anonsmd
 * @Date: 2020/3/20 19:51
 */
public class KeyDTO {
    private String P1;
    private String N;
    private String g1;
    private String g2;
    private String T;

    public KeyDTO(Key key) {
        this.P1 = key.P1.toString();
        this.N = key.N.toString();
        this.g1 = key.g1.toString();
        this.g2 = key.g2.toString();
        this.T = key.T.toString();
    }

    public KeyDTO() {
    }

    public String getP1() {
        return P1;
    }

    public void setP1(String p1) {
        P1 = p1;
    }

    public String getN() {
        return N;
    }

    public void setN(String n) {
        N = n;
    }

    public String getG1() {
        return g1;
    }

    public void setG1(String g1) {
        this.g1 = g1;
    }

    public String getG2() {
        return g2;
    }

    public void setG2(String g2) {
        this.g2 = g2;
    }

    public String getT() {
        return T;
    }

    public void setT(String t) {
        T = t;
    }
}
