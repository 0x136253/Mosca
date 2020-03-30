package com.mutiny.demo.FHE;

import java.math.BigInteger;

/**
 * @Author: Anonsmd
 * @Date: 2020/3/1 12:54
 */
class Operation {
    private static HomomorphicOperations op;
    public static BigInteger mul(BigInteger c1,BigInteger c2,Key keyFile){
//        BigInteger answ = c1.multiply(c2);
        BigInteger answ = HomomorphicOperations.homomorphicMultiplication(c1,c2,keyFile);
        return answ;
    }
    public static BigInteger add(BigInteger c1,BigInteger c2,Key keyFile){
//        BigInteger answ = c1.add(c2);
        BigInteger answ = HomomorphicOperations.homomorphicAddition(c1,c2,keyFile);
        return answ;
    }
    public static BigInteger max(BigInteger c1,BigInteger c2){
        BigInteger answ = c2;
        if (c1.compareTo(c2)==1){
            answ=c1;
        }
        return answ;
    }
    public static BigInteger min(BigInteger c1,BigInteger c2){
        BigInteger answ = c1;
        if (c1.compareTo(c2)==1){
            answ=c2;
        }
        return answ;
    }
}


