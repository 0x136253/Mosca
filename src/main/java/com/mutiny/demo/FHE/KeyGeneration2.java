package com.mutiny.demo.FHE;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

public class KeyGeneration2 {

	public static void main(String[] args) {
		keyGeneration(128);
	}
	public static void keyGeneration(int KeySize) {
		BigInteger P1, P2, N, Q, P3, T;
	    BigInteger two = BigInteger.valueOf(2);
	    String a="";
	    while (true) {
	        P1 = BigInteger.probablePrime(KeySize, new Random());
	        Q = (two.multiply(P1)).add(BigInteger.ONE);
	        if (Q.isProbablePrime(1)) {
	            break;
	        }
	    }
	    P2 = BigInteger.probablePrime(KeySize, new Random());
	    N = P1.multiply(P2);
	    P3 = BigInteger.probablePrime(KeySize, new Random());
	    T = Q.multiply(P3);
	    BigInteger expo = two.multiply(P3.subtract(BigInteger.ONE));
	    Random r1 = new Random();
	    ArrayList<BigInteger> g = new ArrayList<BigInteger>();
	    BigInteger h;
	    for (int i = 0; i < 2; i++) {
	        h = new BigInteger(KeySize, r1);
	        g.add(h.modPow(expo, T));
	    }
//	    for (BigInteger num : g)  {
//	        a+=num.toString()+"\t";
//	        }
//	    String line=P1.toString()+"\t"+N.toString()+"\n"+a+"\n"+T.toString();
//	    System.out.println(line);
	    //�������ɵ���Կ������keys����
	    ArrayList<BigInteger> keys = new ArrayList<BigInteger>();
	    keys.add(P1);
	    keys.add(N);
	    for(int i = 0 ; i < g.size() ; i++){
	    	   keys.add(g.get(i));
	    }
	    keys.add(T);
	    System.out.println("Key:"+keys);
	   //���￪ʼ�Ǽ��ܹ���
//	    BigInteger message=new BigInteger("100000");
//	    BigInteger encry=encryption(message,keys);
//	    System.out.println("��100000���ܵĽ���ǣ�"+encry);
//	    BigInteger decry=decryption(encry,keys);
//	    System.out.println("��100000���ܵĽ���ǣ�"+decry);
//	    
		BigInteger message1=new BigInteger("100000");
		BigInteger message2=new BigInteger("200000");
		BigInteger encry1=encryption(message1,keys);
		BigInteger encry2=encryption(message2,keys);
		System.out.println("对100000加密的结果是："+encry1);
		System.out.println("对200000加密的结果是："+encry2);
		BigInteger answAdd= homomorphicAddition(encry1,encry2,keys);
		BigInteger answMult = homomorphicMultiplication(encry1, encry2, keys);
		boolean answEqual =homomorphicEquality(encry1, encry2, keys);

		System.out.println("同态相加的结果:"+answAdd);
		System.out.println("同态相乘的结果:"+answMult);
		System.out.println("同态相等的结果:"+answEqual);
		System.out.println("同态相加的结果解密:"+decryption(answAdd, keys));
		System.out.println("同态相乘的结果解密:"+decryption(answMult, keys));
		//	    BigInteger decry=decryption(,keys);
	}
	public static BigInteger encryption(BigInteger message,ArrayList<BigInteger> keys) {
		//Generates a random number r
		Random rand = new Random();
		BigInteger r = new BigInteger(32, rand);
		BigInteger emessage;
		BigInteger P1;
		BigInteger N;
		//Takes the required keys for encryption by calling the keyExtraction method 
		P1 = keys.get(0);
		N = keys.get(1);
		//Performs encryption
//		emessage = (r.multiply(P1)).add(message.mod(N));
		emessage = (r.multiply(P1)).add(mod(message,N));
		return emessage;
	}
	public static BigInteger decryption(BigInteger emessage, ArrayList<BigInteger> keys)
	{
		BigInteger message;
		BigInteger P1;
		P1 = keys.get(0);
		BigInteger P_2 = P1.divide(new BigInteger("2"));
		BigInteger _P_2 = P1.multiply(new BigInteger("-1"));
//		message = emessage.mod(P1);
		message = mod(emessage,P1);
		if (message.compareTo(P_2)==1){
			message=message.subtract(P1);
		}
		else if (message.compareTo(_P_2)==-1){
			message=message.add(P1);
		}
		return message;
	}
	
	public static BigInteger homomorphicAddition(BigInteger emessage1, BigInteger emessage2, ArrayList<BigInteger> keys)
	{
		BigInteger amessage;
		BigInteger N;
		N = keys.get(1);
		//Performs homomorphic addition
//		amessage = (emessage1.add(emessage2)).mod(N);
		amessage = mod(emessage1.add(emessage2),N);
		return amessage;
	}
	public static BigInteger homomorphicMultiplication(BigInteger emessage1, BigInteger emessage2, ArrayList<BigInteger> keys)
	{
		BigInteger mmessage;
		BigInteger N;
		N = keys.get(1);
		//Performs homomorphic multiplication
		mmessage = (emessage1.multiply(emessage2)).mod(N);
		mmessage = mod(emessage1.multiply(emessage2),N);
		return mmessage;
	}
	public static boolean homomorphicEquality(BigInteger emessage1, BigInteger emessage2, ArrayList<BigInteger> keys)
	{

		ArrayList<BigInteger> al = new ArrayList<BigInteger>();
		BigInteger T;
		for(int i = 2; i<keys.size()-1; i++)
		al.add(keys.get(i));
		T = keys.get(keys.size() - 1);
		BigInteger diff;
		//Verifies which ciphertext is greater and subtracts them accordingly so that we always get positive value
		if(emessage1.compareTo(emessage2)==1)
			diff = emessage1.subtract(emessage2);
		else
			diff = emessage2.subtract(emessage1);
		
		//Verifies equality and if any one tests is false it returns false 
		//if all tests are passed then it return true
			int i = 0;
			while(i<al.size())
			{
				BigInteger g = al.get(i);
				if(g.modPow(diff, T).equals(BigInteger.ONE))
					i++;
				else
					return false;
			}
		return true;
	}

	
	public static BigInteger mod(BigInteger num,BigInteger q){
		BigInteger v = num.divide(q);
		return num.subtract(q.multiply(num.divide(q)));
	}
	
	
	

}
