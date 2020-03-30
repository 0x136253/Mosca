package com.mutiny.demo.util;

import com.mutiny.demo.FHE.HomomorphicOperations;
import com.mutiny.demo.FHE.Key;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: Anonsmd
 * @Date: 2020/3/1 12:54
 */
class Operation {
    private static HomomorphicOperations op;
    public static BigInteger mul(BigInteger c1,BigInteger c2,Key keyFile){
        BigInteger answ = c1.multiply(c2);
//        BigInteger answ = HomomorphicOperations.homomorphicMultiplication(c1,c2,keyFile);
        return answ;
    }
    public static BigInteger add(BigInteger c1,BigInteger c2,Key keyFile){
        BigInteger answ = c1.add(c2);
//        BigInteger answ = HomomorphicOperations.homomorphicAddition(c1,c2,keyFile);
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


public class RecursiveDescentUtils {
    private static Pattern exp =Pattern.compile("[\\(a-z].*");
    private static Pattern term;
    private static Pattern factor;
    private static Pattern identify = Pattern.compile("[a-z].*");
    private static Pattern compare = Pattern.compile("(max|min).*");
    private static Pattern brackets = Pattern.compile("\\(.*");
    private static Pattern max = Pattern.compile("max\\(.*");;
    private static Pattern min = Pattern.compile("min\\(.*");;
    private String func;
    private Map<Character,BigInteger> map;
    private Key keyFile;


    public RecursiveDescentUtils() {
    }

    public RecursiveDescentUtils(String func, Map<Character, BigInteger> map) {
        this.func = func;
        this.map = map;
    }

    public RecursiveDescentUtils(String func, Map<Character, BigInteger> map, Key keyFile) {
        this.func = func;
        this.map = map;
        this.keyFile = keyFile;
    }

    /**
     * exp -> term {addop term}
     * addop -> +
     * term -> factor {mulop factor}
     * mulop -> *
     * factor -> (exp) | number | identifiy | cmp
     * cmp -> max(exp,exp)|min(exp,exp)
     */

    public BigInteger exp(){
        BigInteger temp =term();
        if (func.length()==0){
            return temp;
        }
        while(func.substring(0,1).equals("+")){
           func = func.substring(1,func.length());
//            temp = temp.add(term());
            temp = Operation.add(temp,term(),keyFile);
            if (func.length()==0){
                break;
            }
        }
        return temp;
    }


    public BigInteger factor(){
        BigInteger temp = BigInteger.ZERO;
        Matcher m_brackets = this.brackets.matcher(func);
        Matcher m_identify = this.identify.matcher(func);
        Matcher m_compare = this.compare.matcher(func);
        if (m_brackets.matches()){
            func= func.substring(1,func.length());
            temp=temp.add(exp());
            func= func.substring(1,func.length());
        }
        else if(m_compare.matches()){
            temp=cmp();
        }
        else if(m_identify.matches()){
            temp = map.get(func.charAt(0));
            func= func.substring(1,func.length());
        }
        return temp;
    }

    public BigInteger cmp(){
        BigInteger cmp1 = BigInteger.ZERO;
        BigInteger cmp2 = BigInteger.ZERO;
        BigInteger temp = BigInteger.ZERO;
        Matcher m_max = max.matcher(func);
        Matcher m_min = min.matcher(func);
        if (m_max.matches()){
            func=func.substring(4,func.length());
            cmp1 = exp();
            func=func.substring(1,func.length());
            cmp2 = exp();
            func=func.substring(1,func.length());
            temp= Operation.max(cmp1,cmp2);
        }
        else if (m_min.matches()){
            func=func.substring(4,func.length());
            cmp1 = exp();
            func=func.substring(1,func.length());
            cmp2 = exp();
            func=func.substring(1,func.length());
            temp= Operation.min(cmp1,cmp2);
        }
        return temp;
    }

    public BigInteger term(){
        BigInteger temp =factor();
        if (func.length()==0){
            return temp;
        }
        while(func.substring(0,1).equals("*")){
            func = func.substring(1,func.length());
//            temp = temp.multiply(factor());
            temp = Operation.mul(temp,factor(),keyFile);
            if (func.length()==0){
                break;
            }
        }
        return temp;
    }

    //String pattern = "^[\\(]";
    public static void main(String args[]) {
        Map<Character,BigInteger> map = new HashMap<>();
        map.put('a',new BigInteger("3"));
        map.put('b',new BigInteger("2"));
        map.put('c',new BigInteger("3"));
        map.put('d',new BigInteger("3"));
        map.put('e',new BigInteger("3"));
        RecursiveDescentUtils recursiveDescent = new RecursiveDescentUtils("(b+c)*a+min(d+c,e)",map);
        BigInteger answ = recursiveDescent.exp();
        System.out.println(answ);
    }
}
