package com.mutiny.demo.FHE;

import java.math.BigInteger;

/**
 * @Author: Anonsmd
 * @Date: 2020/2/28 16:39
 */
public class Key {
    public BigInteger P1;
    public BigInteger N;
    public BigInteger g1;
    public BigInteger g2;
    public BigInteger T;

    public Key() {
    }

    public Key(BigInteger p1, BigInteger n, BigInteger g1, BigInteger g2, BigInteger t) {
        P1 = p1;
        N = n;
        this.g1 = g1;
        this.g2 = g2;
        T = t;
    }
}
