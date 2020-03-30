package com.mutiny.demo.FHE;/*This class is used for encryption and decryption purpose by extracting keys from the KeyFile*/

import java.math.BigInteger;
import java.util.Random;

public class EncryptDecrypt {

	//This function is used to encrypt the given message by the user using the keys stored in the KeyFile.
	public static BigInteger encryption(BigInteger message, Key keyFile)
	{
		//Generates a random number r
		Random rand = new Random();
		BigInteger r = new BigInteger(32, rand);
		BigInteger emessage;
		BigInteger P1;
		BigInteger N;
		
		//Takes the required keys for encryption by calling the keyExtraction method
		P1 = keyFile.P1;
		N = keyFile.N;

		//Performs encryption
		emessage = (r.multiply(P1)).add(mod(message,N));

		return emessage;
	}

	public static BigInteger encryption(BigInteger message,BigInteger r, Key keyFile)
	{
//		//Generates a random number r
//		Random rand = new Random();
//		BigInteger r = new BigInteger(32, rand);
		BigInteger emessage;
		BigInteger P1;
		BigInteger N;

		//Takes the required keys for encryption by calling the keyExtraction method
		P1 = keyFile.P1;
		N = keyFile.N;

		//Performs encryption
		emessage = (r.multiply(P1)).add(mod(message,N));

		return emessage;
	}

	//This function is used to decrypt the given message by the user using the keys stored in the KeyFile
	public static BigInteger decryption(BigInteger emessage, Key KeyFile)
	{
		BigInteger message;
		BigInteger P1;
		
		//Takes the required keys for decryption by calling the keyExtraction method
		P1 = KeyFile.P1;

		BigInteger P_2 = P1.divide(new BigInteger("2"));
		BigInteger _P_2 = P1.multiply(new BigInteger("-1"));

		//Performs decryption
		message = mod(emessage,P1);

		if (message.compareTo(P_2)==1){
			message=message.subtract(P1);
		}
		else if (message.compareTo(_P_2)==-1){
			message=message.add(P1);
		}
		return message;
	}

	public static BigInteger mod(BigInteger num,BigInteger q){
		BigInteger v = num.divide(q);
		return num.subtract(q.multiply(num.divide(q)));
	}

	 public static void main(String[] args) {
		System.out.println(mod(new BigInteger("-123"),new BigInteger("100")));
	}
}
