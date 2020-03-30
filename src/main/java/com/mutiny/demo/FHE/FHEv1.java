package com.mutiny.demo.FHE;/* This class calls all other required classes to perform necessary operations for FHEv1
 based on the arguments given by the user in the command line */

import java.math.BigInteger;

public class FHEv1 {

	public static void main(String[] args){
	    //生成密钥
	    Key key = KeyGeneration.keyGeneration(128);

	    //加密数字
	    BigInteger c1 = EncryptDecrypt.encryption(new BigInteger("23"),key);
	    System.out.println("message: 23 " + "Cypt:"+EncryptDecrypt.decryption(c1,key));
	    BigInteger c2 = EncryptDecrypt.encryption(new BigInteger("123"),key);
        System.out.println("message: 123" + "Cypt:"+EncryptDecrypt.decryption(c2,key));
	    BigInteger c3 = EncryptDecrypt.encryption(new BigInteger("123"),key);
        System.out.println("message: 123" + "Cypt:"+EncryptDecrypt.decryption(c3,key));

        //测试加法
	    BigInteger add = HomomorphicOperations.homomorphicAddition(c1,c2,key);
        System.out.println("c1+c2 " + "Cypt:"+add);
        System.out.println("Dec(c1+c2):"+EncryptDecrypt.decryption(add,key));

        //测试乘法
	    BigInteger mul = HomomorphicOperations.homomorphicMultiplication(c1,c2,key);
        System.out.println("c1*c2 " + "Cypt:"+mul);
        System.out.println("Dec(c1*c2):"+EncryptDecrypt.decryption(mul,key));


        // 比较message是否相等
        if(HomomorphicOperations.homomorphicEquality(c1,c2,key)) {
            System.out.println("c1 and c2 are equal");
        } else {
            System.out.println("c1 and c2 are not equal");
        }

        if(HomomorphicOperations.homomorphicEquality(c2,c3,key)) {
            System.out.println("c2 and c3 are equal");
        } else {
            System.out.println("c2 and c3 are not equal");
        }
	}

}
