package com.mutiny.demo.FHE;/*This class generates all the keys required to perform all the required operations of FHEv1 and copies the keys to a text file*/

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

public class KeyGeneration 
{
	/*This function is called to generate all the keys and copies them to a File. 
	  It takes the keysize and name of the File to copy all the keys as arguments */
	public static Key keyGeneration(int KeySize) {
	BigInteger P1, P2, N, Q, P3, T;
	BigInteger two = BigInteger.valueOf(2);
	//Generates a random biginteger of given keysize length and verifies if it satisfies the condition Q=2P1+1 is also prime.
	//If it is prime it stores the values of P1 and Q.
	while(true)
	{
		 P1 = BigInteger.probablePrime(KeySize, new Random());
		 Q = (two.multiply(P1)).add(BigInteger.ONE);
		if(Q.isProbablePrime(1))
		{
			break;
		}
	}
	//P1 is a prime and Q = (2P1+1) is also a prime
	//N = P1*P2,P2 is a prime
	// T = 	Q* P3,P3 is also a prime
	//Generates P2,N,P3 and T
	 P2 = BigInteger.probablePrime(KeySize, new Random());
	 N = P1.multiply(P2);
	 P3 = BigInteger.probablePrime(KeySize, new Random());
	 T = Q.multiply(P3);
	 //Generates two gi's
	// expo = 2 * (P3 -1 )
	 BigInteger expo = two.multiply(P3.subtract(BigInteger.ONE));
	 Random r1 = new Random();
	 ArrayList<BigInteger> g = new ArrayList<BigInteger>();
	 BigInteger h;
	 // g = h^expo mod T
	 for(int i =0; i<2; i++)
	 {
		 h = new BigInteger(KeySize, r1);
		 g.add(h.modPow(expo, T));
	 }
	 //Writes all the generated keys to the File. BufferedWriter stores everything as String. 
	 //So, we are converting all the generated keys to String.
	Key key = new Key(P1,N,g.get(0),g.get(1),T);
	 return key;
	}
}

