package com.mutiny.demo.util;

import com.mutiny.demo.FHE.EncryptDecrypt;
import com.mutiny.demo.FHE.Key;
import com.mutiny.demo.FHE.RecursiveDescent;
import com.mutiny.demo.pojo.Module;

import java.io.IOException;
import java.math.BigInteger;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: Anonsmd
 * @Date: 2020/3/16 16:06
 */
public class GenerateWebDataUtils {
    public static Map<String,List<String>> generate(Module module,int cnt){
        Map<String,List<String>> finalAnswMap = new HashMap<>();
        String function = module.getFunction();
        Set<String> idens = getIdentifiy(function);
        Scanner sc = new Scanner(System.in);
        Random random = new Random();
        for (String record:idens){
            Set<String> innum = new HashSet<>();
            int beforenum = 0;
            int afternum = 0;
            System.out.println("Iden:"+record+"请输入值（1为范围，2为特定值）");
            int cmd = sc.nextInt();
            if(cmd == 1){
                beforenum = sc.nextInt();
                afternum = sc.nextInt();
            }
            else{
                for (int i=0;i<cmd;i++){
                    innum.add(sc.nextDouble()+"");
                }
            }
            List<String> list = new ArrayList<>();
            List<String> setlist = new ArrayList<>(innum);
            for (int i=0;i<cnt;i++){
                if (cmd ==1){
                    list.add((random.nextInt(afternum-beforenum)+beforenum)+"");
                }
                else{
                    list.add(setlist.get(random.nextInt(setlist.size())));
                }
            }
            finalAnswMap.put(record,list);
        }
        return finalAnswMap;
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

    /**
     * 数据切片
     * @param map
     * @param count
     * @param name
     */
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
//            CSVUtils.writeCsvFile("/home/anon/"+name,answmap);
            try {
                ExcelUtils.writeWithoutHead(answmap,name);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void gen(String Function,String name){
        Module module = new Module();
        module.setFunction(Function);
        Map<String, List<String>> map = GenerateWebDataUtils.generate(module,20000);
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
        gen("f*a+g*b","月均可支配收入模型");
//        CSVUtils.writeCsvFile("C:\\Users\\Anonsmd\\Desktop\\moscaTestCsv",map);
//        System.out.println(CSVUtils.readCsvFile("C:\\Users\\Anonsmd\\Desktop\\123.csv\\fd77a967-f99b-4972-aa5b-5a55499f7f5f.csv",2));
    }
}
