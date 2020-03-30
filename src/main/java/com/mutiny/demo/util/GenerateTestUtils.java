package com.mutiny.demo.util;

import com.mutiny.demo.FHE.EncryptDecrypt;
import com.mutiny.demo.FHE.Key;
import com.mutiny.demo.FHE.KeyGeneration;
import com.mutiny.demo.FHE.RecursiveDescent;
import com.mutiny.demo.pojo.Module;

import java.math.BigInteger;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: Anonsmd
 * @Date: 2020/3/16 16:06
 */
public class GenerateTestUtils {

    public static Key getKey(String path){
        Map<String, List<String>> keyFileMap = CSVUtils.readCsvFile(path,-1);
        Key key = new Key();
        key.P1 = new BigInteger(keyFileMap.get("P1").get(0));
        key.N = new BigInteger(keyFileMap.get("N").get(0));
        key.g1 = new BigInteger(keyFileMap.get("g1").get(0));
        key.g2 = new BigInteger(keyFileMap.get("g2").get(0));
        key.T = new BigInteger(keyFileMap.get("T").get(0));
        return key;
    }

    public static Map<String,List<String>> generate(Module module,int cnt,Key key){
        Map<String,List<String>> finalAnswMap = new HashMap<>();
        String func = module.getTfunction();
        Set<String>[] sets = getEncode(func);
        String Changefun = module.getChangefun().substring(1,module.getChangefun().length()-1);
        List<String> iden = Arrays.asList(Changefun.split(", "));
//        System.out.println(iden);
        Set<String> idens = new HashSet<>();
        idens.addAll(iden);
        Random random = new Random(System.currentTimeMillis());
        String pattern = "(\\-)?\\d+(\\.\\d{1,2})?";
        Pattern r_num = Pattern.compile(pattern);
        Map<Character, BigInteger> map = new HashMap<Character, BigInteger>();
        String[] charrs = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
        Set<String> alliden = new HashSet<>();
        alliden.addAll(Arrays.asList(charrs));
//        Key key = KeyGeneration.keyGeneration(128);
        Pattern r_full_num = Pattern.compile("^(\\-)?\\d+(\\.\\d{1,2})?");
        for (int k = 0;k<cnt;k++){
            for (int i=0;i<iden.size();i++){
                String chars = iden.get(i).split("=")[0];
                String exps = iden.get(i).split("=")[1];
                Matcher m_full_num = r_full_num.matcher(exps);
                if (m_full_num.matches()){
                    Character charss = chars.charAt(0);
                    BigInteger vals = new BigInteger(exps);
//                    System.out.println(charss);
                    map.put(charss,vals);
                    idens.remove(iden.get(i));
                }
            }
            Iterator<String> stringIterator = idens.iterator();
            while (stringIterator.hasNext()){
                Map<Character,BigInteger> tempMap = new HashMap<>();
                String funx = stringIterator.next();
                if (funx.charAt(0)==' '){
                    funx=funx.substring(1,funx.length());
                }
//                System.out.println(funx);
                String chars = funx.split("=")[0];
                String exps = funx.split("=")[1];
                Matcher m_num = r_num.matcher(exps);
                Set<String> useChars = new HashSet<>();
                useChars.addAll(alliden);
                useChars.removeAll(getIdentifiy(exps));
                Iterator<String> iterator = useChars.iterator();
                String newExps = exps;
                while (m_num.find()){
                    String tem = m_num.group();
                    BigInteger num = new BigInteger(tem);
                    String cha = iterator.next();
                    newExps = newExps.replaceAll(tem,cha);
                    tempMap.put(cha.charAt(0),num);
                }
//                System.out.println(newExps);
                Set<String> identufuy = getIdentifiy(newExps);
                Iterator<String> idefuys = identufuy.iterator();
                while (idefuys.hasNext()){
                    String chaa = idefuys.next();
                    if (tempMap.containsKey(chaa.charAt(0))){
                        continue;
                    }
                    else {
                        tempMap.put(chaa.charAt(0),new BigInteger(random.nextInt(100)+""));
                    }
                }
//                System.out.println(tempMap);
                RecursiveDescentUtils recursiveDescentUtils = new RecursiveDescentUtils(newExps,tempMap);
                BigInteger answ = recursiveDescentUtils.exp();
//                System.out.println(answ);
                map.put(chars.charAt(0),answ);
            }
            System.out.println(map);
            RecursiveDescentUtils recursiveDescentUtils = new RecursiveDescentUtils(func,map);
            System.out.println(recursiveDescentUtils.exp());
            map = encode(sets,map,key);
            System.out.println(map);
            RecursiveDescent recursiveDescent = new RecursiveDescent(func,map,key);
            System.out.println(EncryptDecrypt.decryption(recursiveDescent.exp(),key));
            Set<Character> keySet = map.keySet();
            Iterator<Character> keySetIterator = keySet.iterator();
            while(keySetIterator.hasNext()){
                Character testKey = keySetIterator.next();
                if (!finalAnswMap.containsKey(testKey+"")){
                    List<String> tempList = new ArrayList<>();
                    tempList.add(map.get(testKey)+"");
                    finalAnswMap.put(String.valueOf(testKey),tempList);
                }
                else {
                    finalAnswMap.get(String.valueOf(testKey)).add(map.get(testKey)+"");
                }
            }
            map = new HashMap<>();
        }
        return finalAnswMap;
    }

    public static Map<Character,BigInteger> encode(Set<String>[] sets, Map<Character,BigInteger> map, Key key){
        Map<Character,BigInteger> answmap = new HashMap<>();
        for (int i=0;i<sets.length;i++){
            answmap=encode(sets[i],map,key,answmap);
        }
        return answmap;
    }

    public static Map<Character,BigInteger> encode(Set<String> sets,Map<Character,BigInteger> valmap,Key key,Map<Character,BigInteger> answmap){
        Random rand = new Random();
        BigInteger r = new BigInteger(32, rand);
        Iterator<String> it = sets.iterator();
        while (it.hasNext()){
            Character chars = it.next().charAt(0);
//            System.out.println(chars);
            BigInteger num = EncryptDecrypt.encryption(valmap.get(chars),r,key);
            answmap.put(chars,num);
        }
        return answmap;
    }

    private static Set<String> getIdentifiy(String function){
        String pattern = "[a-zA-Z]+";
        Pattern r = Pattern.compile(pattern);
        Matcher matcher = r.matcher(function);
        Set<String> answ = new HashSet<>();
        while (matcher.find()){
            String tem = matcher.group();
            if (tem.equals("max") || tem.equals("MAX") ||tem.equals("min") ||tem.equals("MIN")){
                continue;
            }
            answ.add(tem);
        }
        return answ;
    }

    private static Set<String>[] getEncode(String function){
        List<Set<String>> setList = new ArrayList<>();
        String[] exps = spiltPunction(function);
        String pattern = "(max|min)\\(.*,.*\\)";
        Pattern r_cmp = Pattern.compile(pattern);
        for (int i=0;i<exps.length;i++){
            Set<String> temp = getIdentifiy(exps[i]);
            Matcher m = r_cmp.matcher(exps[i]);
            if (m.matches()){
                setList.add(temp);
            }
            else{
                Iterator<String> it = temp.iterator();
                while (it.hasNext()){
                    Set<String> tmep2 = new HashSet<>();
                    tmep2.add(it.next());
                    setList.add(tmep2);
                }
            }
        }
        Set<String>[] sets = setList.toArray(new Set[setList.size()]);
        return sets;
    }

    private static String[] spiltPunction(String func){
        List<String> answ = new ArrayList<>();
        int cnt =0;
        int q =0;
        Character pre = '+';
        for (int i=0;i<func.length();i++) {
            Character tem2 = func.charAt(i);
            if (tem2 == '(') {
                cnt++;
            } else if (tem2 == ')') {
                cnt--;
            }
            if (cnt == 0 && (tem2 =='+' || tem2 == '-') && func.charAt(i-1)!='*') {
                if (pre == '+') {
                    answ.add(func.substring(q,i));
                }
                else {
                    answ.add('-'+func.substring(q,i));
                }
                q=i+1;
                pre =tem2;
            }
            if (cnt == 0 && i == func.length()-1) {
                if (pre == '+') {
                    answ.add(func.substring(q,i+1));
                }
                else {
                    answ.add('-'+func.substring(q,i+1));
                }
                q=i+1;
                pre = tem2;
            }
        }
//        System.out.println(answ);
        return answ.toArray(new String[answ.size()]);
    }

    public static void saveCsv(Map<String,List<String>> map,int count,String name){
        Set<String> keySet = map.keySet();
        String[] keySetArray = keySet.toArray(new String[keySet.size()]);
        Random random = new Random();
        Set<Integer> chosenSet = new HashSet<>();
        int[] chosenLength = new int[count];
        int sum=0;
        for (int i=0;i<count;i++){
            chosenLength[i]=random.nextInt((keySet.size()-sum)/2)+1;
            if (i == count -1 ){
                chosenLength[i] = keySet.size() - sum;
            }
            sum+=chosenLength[i];
        }
        Map<String, List<String>> answmap;
        for (int i=0;i<count;i++){
            answmap = new HashMap<>();
            for (int k=0;k<chosenLength[i];k++){
                int chose = random.nextInt(keySet.size());
                while (chosenSet.contains(chose)){
                    chose = random.nextInt(keySet.size());
                }
                chosenSet.add(chose);
                answmap.put(keySetArray[chose],map.get(keySetArray[chose]));
            }
            CSVUtils.writeCsvFile("C:\\Users\\Anonsmd\\Desktop\\moscaTestCsv"+"\\"+name,answmap);
        }
    }

    public static void gen(String tFunction,String changeFun,String keyURL,String name){
        Module module = new Module();
        module.setTfunction(tFunction);
        module.setChangefun(changeFun);
        Key key = GenerateTestUtils.getKey(keyURL);
        Map<String, List<String>> map = GenerateTestUtils.generate(module,20000,key);
        Iterator<String> keySet = map.keySet().iterator();
        int size = map.get(keySet.next()).size();
        List<String> nameList = new ArrayList<>();
        Random random = new Random();
        for (int i=0;i<size;i++){
            nameList.add(random_s(random.nextInt(4)+7));
        }
        map.put("Name",nameList);
        saveCsv(map,1,name);
    }

    public static String random_s(int cnt){
        Random random=new Random();
        int pre=random.nextInt(cnt/2);
        pre-=random.nextInt(2);
        int fon = cnt -pre;
        String small="abcdefghijklmnopqrstuvwxyz";
        String big="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuffer sb=new StringBuffer();
        int number = random.nextInt(26);
        sb.append(big.charAt(number));
        for(int i=0;i<pre-1;i++){
            number=random.nextInt(26);
            sb.append(small.charAt(number));
        }
        sb.append(' ');
        for(int i=0;i<fon;i++){
            number=random.nextInt(26);
            sb.append(small.charAt(number));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
//        Module module = new Module();
//        module.setFunction("max(min(x*y-z,z),max(x*c,z))+max(min(x*y,z),z)-0.15*c+5.1+q*-9*x*y+z");
//        module.setTfunction("max(min(b,d),max(e,f))+max(min(h,i),g)+j*c+k+q*l*x*y+m*z");
//        module.setChangefun("[l=-900, k=510, i=100*z, q=q, d=100*z, c=c, g=100*z, b=100*x*y+100*-1*z, j=-15, h=100*x*y, x=x, y=y, f=100*z, z=z, e=100*x*c, m=100]");
//        Key key = GenerateTestUtils.getKey("D://temp-rainy//key//3d4cbdb1-baeb-44e4-a21a-5fb05259da2b.csv");
//        Map<String, List<String>> map = GenerateTestUtils.generate(module,200000,key);
//        saveCsv(map,3);
        gen("f*a+g*b+h*e*c+i*e*d","[e=-1, i=10, h=10, a=a, g=458, b=b, c=c, d=d, f=5]","D://temp-rainy//key//3d4cbdb1-baeb-44e4-a21a-5fb05259da2b.csv","月均可支配收入模型");
//        CSVUtils.writeCsvFile("C:\\Users\\Anonsmd\\Desktop\\moscaTestCsv",map);
//        System.out.println(CSVUtils.readCsvFile("C:\\Users\\Anonsmd\\Desktop\\123.csv\\fd77a967-f99b-4972-aa5b-5a55499f7f5f.csv",2));
    }
}
