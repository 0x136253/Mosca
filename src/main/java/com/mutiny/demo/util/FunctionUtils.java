package com.mutiny.demo.util;

import com.mutiny.demo.pojo.DefaultModule;
import com.mutiny.demo.pojo.Module;
import javafx.util.Pair;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: Anonsmd
 * @Date: 2020/3/10 15:56
 */

/**
 * q请避免使用不必要的括号
 */
public class FunctionUtils {
    //x= exp
    private Set<String> map;
    // a-z
    private Set<String> allIden;
    // the char in function
    private Set<String> funIden;
    // alliden - funiden
    private Iterator<String> it;
    //exp
    private Set<String> tmp;
    //char
    private Set<String> charss;
    //mapp
    private Map<String,String> dmap;

    private int multCount;

    private FunctionUtils() {
        dmap = new HashMap<>();
        map = new HashSet<>();
        tmp = new HashSet<>();
        allIden = new HashSet<>();
        charss = new HashSet<>();
        allIden.add("a");
        allIden.add("b");
        allIden.add("c");
        allIden.add("d");
        allIden.add("e");
        allIden.add("f");
        allIden.add("g");
        allIden.add("h");
        allIden.add("i");
        allIden.add("j");
        allIden.add("k");
        allIden.add("l");
        allIden.add("m");
        allIden.add("n");
        allIden.add("o");
        allIden.add("p");
        allIden.add("q");
        allIden.add("r");
        allIden.add("s");
        allIden.add("t");
        allIden.add("u");
        allIden.add("v");
        allIden.add("w");
        allIden.add("x");
        allIden.add("y");
        allIden.add("z");
    }

    private FunctionUtils(Set<String> map) {
        this.map = map;
    }

    /**
     * need to improve ,替换exo中原函数不存在的参数
     * 解决e = x*y ,b =x*y 重合的问题也可在此解决
     * @param exp
     * @return
     */
    private String mapPut(String exp){
        String[] strs = exp.split("=");
        String newIden = strs[0];
        String oldIdens = strs[1];
        /**
         * 查看tmp中有没有oldIdens若有替换newIden;===========?????? ,新的newIden怎么返回
         */
        if (tmp.contains(oldIdens)){
            //......
        }
        /**
         * 把old中的参数提取出来，对照funIden中有没有，若无，则替换
         */
        String pattern = "[a-zA-Z]+";
        Pattern r = Pattern.compile(pattern);
        Matcher matcher = r.matcher(oldIdens);
        while (matcher.find()){
            String tem = matcher.group();
            if (tem.equals("max") || tem.equals("MAX") ||tem.equals("min") ||tem.equals("MIN")){
                continue;
            }
            if (charss.contains(tem)){
                oldIdens=oldIdens.replaceAll(tem,dmap.get(tem));
            }
        }
        map.add(newIden+"="+oldIdens);
        dmap.put(newIden,oldIdens);
        charss.add(newIden);
        tmp.add(oldIdens);
        return newIden+"="+oldIdens;
    }

    private void updateMap(String func){
        Set<String> idd = getIdentifiy(func);
        Iterator<String> ittt = idd.iterator();
        while (ittt.hasNext()){
            String tmppp = ittt.next();
            if (!charss.contains(tmppp)){
                map.add(tmppp+"="+tmppp);
            }
        }
        ittt = charss.iterator();
        while (ittt.hasNext()){
            String tmpppp = ittt.next();
            if (!idd.contains(tmpppp)){
//                charss.remove(tmpppp);
                String vale = dmap.get(tmpppp);
                dmap.remove(tmpppp);
                map.remove(tmpppp+"="+vale);
            }
        }
    }


    /**
     * 此处可优化，相同的exp可用方法替代,
     * 也可考虑优化mapPut，不改这里
     * @param func
     * @return
     */
    private String expHandle(String func){
        String answ = null;
        boolean flag =false;
        String[] strs = func.split("\\+");
        String pattern = "(max|min)\\(.*,.*\\)";
        Pattern r_cmp = Pattern.compile(pattern);
        Matcher mx = r_cmp.matcher(func);
        if (mx.matches()){
            return func;
        }
        if (strs.length==1){
            Matcher m = r_cmp.matcher(strs[0]);
            if (m.matches()){
                return strs[0];
            }
            else{
                for (int k=0;k<strs[0].length();k++){
                    Character tme = strs[0].charAt(k);
                    if (tme<='z' && tme>='a'){
                        if (strs[0].length()==1){
                            return tme+"";
                        }
                        else {
                            return it.next()+"="+strs[0];
                        }
                    }
                }
            }
        }
        return it.next()+"="+func;
    }

    private Pair<String,String> getCmpExps(String Exps){
        String exp1="";
        String exp2="";
        int cntt =0;
        for (int k=0;k<Exps.length();k++){
            Character tem3 = Exps.charAt(k);
            if (tem3=='('){
                cntt++;
            }
            else if (tem3 == ')'){
                cntt--;
            }
            if (cntt==0 && tem3==','){
                exp1 = Exps.substring(0,k);
                exp2 = Exps.substring(k+1,Exps.length());
                break;
            }
        }
        return new Pair<>(exp1,exp2);
    }

    private String numHandle(String func){
        String pattern = ".*\\-.*";
        Pattern r_f = Pattern.compile(pattern);
        Matcher m_f = r_f.matcher(pattern);
        if (m_f.matches()){
            return numberHandle(func,it.next());
        }
        else {
            return func;
        }
    }

    /**
     *  1.公式中的负号
     *      a. apear in the head : / -0.5*x*y
     *      b. apear in the middle :  / + x* -0.5*y // -x*y???
     *  2. appear in the cmp
     *      a. max(max(-xy,z),c)
     * @param func
     * @return
     */
    private String numberHandle(String func,String chars){
        String[] exps = spiltPunction(func);
        String answ = "";
        for (int i=0;i<exps.length;i++){
            if (i != 0 && (exps[i].charAt(0)!='-' || exps[i].length()==2)){
                answ=answ+"+";
            }
           answ =answ + numCmpHandle(exps[i],chars);

        }
        return answ;
    }

    private String numCmpHandle(String exp,String chars){
        String pattern = "(max|min)\\(.*,.*\\)";
        Pattern r_cmp = Pattern.compile(pattern);
        Matcher m = r_cmp.matcher(exp);
        boolean flag = m.matches();
        if (flag){
            String tem = exp.substring(0,3);
            if (tem.equals("max") || tem.equals("min")){
                String pre = exp.substring(4,exp.length()-1);
                Pair<String, String> pair = getCmpExps(pre);
                String exp1 = pair.getKey();
                String exp2 = pair.getValue();
                exp1 = numberHandle(exp1,chars);
                exp2 = numberHandle(exp2,chars);
                exp = exp.substring(0,3)+"("+exp1+","+exp2+")";
            }
        }
        else {
            String[] muls = exp.split("\\*");
            String pattern2 = "\\-[a-z]";
            Pattern r_num = Pattern.compile(pattern2);
            String answ="";
            boolean flag2 = false;
            for (int i=0;i<muls.length;i++){
                Matcher m_num = r_num.matcher(muls[i]);
                if (m_num.matches()){
                    flag2 =true;
                    String tmpp = chars+'*'+muls[i].split("\\-")[1];
                    mapPut(chars+"=-1");
//                    map.add(chars+"=-1");
                    answ=answ+tmpp;
                }
                else {
                    answ=answ+muls[i];
                }
                if (i!=muls.length-1){
                    answ=answ+"*";
                }
            }

            exp = answ;
        }
        return exp;

    }

    /**
     * 自定义模型中不可含f参数
     * @param func
     * @return
     */
    private String funcHandel(String func){
        System.out.println("Before    Handle: "+func);
        Set<String> params = new HashSet<>();
        funIden =getIdentifiy(func);
        params.addAll(allIden);
        params.removeAll(funIden);
        it = params.iterator();
        func=func.replaceAll("MAX","max");
        func=func.replaceAll("MIN","min");
        func=func.replaceAll("Max","max");
        func=func.replaceAll("Min","min");
        System.out.println("After Fir Handle: "+func);
        func = numHandle(func);
        System.out.println("After Num Handle: "+func);
        func = multFunction(func);
        System.out.println("After Mul Handle: "+func);
        //替换 比较 运算中的复杂表达式;
        for (int i=2;i<func.length();i++){
            String tem = func.substring(i-2,i+1);
            if (tem.equals("max") || tem.equals("min")){
                int cnt =0;
                for (int j=i+1;;j++){
                    Character tem2 = func.charAt(j);
                    if (tem2=='('){
                        cnt++;
                    }
                    else if (tem2 == ')'){
                        cnt--;
                    }
                    if (cnt==0){
                        String pre = func.substring(i+2,j);
                        Pair<String,String> pair = getCmpExps(pre);
                        String exp1=expHandle(pair.getKey());
                        String exp2=expHandle(pair.getValue());
                        if (exp1.split("=").length>1){
                            mapPut(exp1);
                            exp1=exp1.split("=")[0];
                        }
                        if (exp2.split("=").length>1){
                            mapPut(exp2);
                            exp2=exp2.split("=")[0];
                        }
                        pre = exp1 + "," +exp2;
                        func=func.substring(0,i+2)+pre+func.substring(j,func.length());
                        break;
                    }
                }
            }
        }
        System.out.println("After Cmp Handle: "+func);
        func = replaceNumber(func);
        System.out.println("After Rep Handle: "+func);
        updateMap(func);
        return func;
    }

    private String multExp(String exp,int num){
        String pattern = "(max|min)\\(.*,.*\\)";
        Pattern r_cmp = Pattern.compile(pattern);
        Matcher m = r_cmp.matcher(exp);
        boolean flag = m.matches();
        if (flag){
            String tem = exp.substring(0,3);
            if (tem.equals("max") || tem.equals("min")){
                String pre = exp.substring(4,exp.length()-1);
                Pair<String, String> pair = getCmpExps(pre);
                String exp1 = pair.getKey();
                String exp2 = pair.getValue();
                exp1 = multFunction(exp1,num);
                exp2 = multFunction(exp2,num);
                exp = exp.substring(0,3)+"("+exp1+","+exp2+")";
            }
        }
        else {
            String[] muls = exp.split("\\*");
            String pattern2 = "^(\\-)?\\d+(\\.\\d{1,2})?";
            Pattern r_num = Pattern.compile(pattern2);
            String answ="";
            boolean flag2 = false;
            for (int i=0;i<muls.length;i++){
                Matcher m_num = r_num.matcher(muls[i]);
                if (m_num.matches()){
                    flag2 =true;
                    Double t1 = Double.parseDouble(muls[i]);
                    double x =  (t1*num);
                    if(x<0){
                        x-=0.5;
                    }
                    else if(x>0){
                        x+=0.5;
                    }
                    int tmpp = (int) x;
                    answ=answ+tmpp;
                }
                else {
                    answ=answ+muls[i];
                }
                if (i!=muls.length-1){
                    answ=answ+"*";
                }
            }
            if (!flag2){
                answ = num+"*"+answ;
            }
            exp = answ;
        }
        return exp;
    }

    private String[] spiltPunction(String func){
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
        System.out.println(answ);
        return answ.toArray(new String[answ.size()]);
    }

    private String multFunction(String func,int num){
        if(num ==1){
            return func;
        }
        String[] exps = spiltPunction(func);
        String answ = "";
        for (int i=0;i<exps.length;i++){
            answ =answ + multExp(exps[i],num);
            if (i != exps.length -1){
                answ=answ+"+";
            }
        }
        return answ;
    }

    private String multFunction(String func){
        String pattern = "(\\-)?\\d+(\\.\\d{1,2})?";
        Pattern r_num = Pattern.compile(pattern);
        Matcher matcher = r_num.matcher(func);
        int cnt = 0;
        while (matcher.find()){
            String tem = matcher.group();
            String[] nums = tem.split("\\.");
            if (nums.length==1){
                cnt=Math.max(cnt,1);
            }
            else {
                cnt=Math.max(cnt,(int)Math.pow(10,nums[1].length()));
            }
        }
        this.multCount=cnt;
        return multFunction(func,cnt);
    }
    private String replaceNumber(String func) {

        String pattern = "(\\-)?\\d+(\\.\\d{1,2})?";
        Pattern r_num = Pattern.compile(pattern);
        Matcher matcher = r_num.matcher(func);
        while (matcher.find()){
            String tem = matcher.group();
//            System.out.println(tem);
            String rep="";
            if (it.hasNext()){
                rep=it.next();
            }
            mapPut(rep+"="+tem);
            func=func.replaceFirst(tem,rep);
        }
        return func;
    }

    public static Set<String> getIdentifiy(String function){
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

    private int getParamNumber(String func){
        return getIdentifiy(func).size();
    }

    private Object[] getMap() {
        return map.toArray();
    }

    private void setMap(Set<String> map) {
        this.map = map;
    }

    public static Module moduleHandle(Module module){
        String func = module.getFunction();
        FunctionUtils functionUtils = new FunctionUtils();
        func = functionUtils.funcHandel(func);
        module.setTfunction(func);
        module.setParamNumber(functionUtils.getParamNumber(func));
        module.setChangefun(Arrays.toString(functionUtils.getMap()));
        module.setMultnum(functionUtils.multCount);
        return module;
    }

    public static DefaultModule defaultModuleHandle(DefaultModule module){
        String func = module.getFunction();
        FunctionUtils functionUtils = new FunctionUtils();
        func = functionUtils.funcHandel(func);
        module.setTfunction(func);
        module.setParamNumber(functionUtils.getParamNumber(func));
        module.setChangefun(Arrays.toString(functionUtils.getMap()));
        module.setMultnum(functionUtils.multCount);
        return module;
    }

    public static void main(String[] args) {
        FunctionUtils functionUtils = new FunctionUtils();
//        System.out.println(Arrays.toString(functionUtils.spiltPunction("max(min(x*y+z,z),max(x*c,z))+max(min(x*y,z),z)+f*0.15*c+5.1+9+x*y+z")));
        String answ = functionUtils.funcHandel("0.5*a+45.8*b-c-d");
        System.out.println(answ);
        System.out.println(Arrays.toString(functionUtils.getMap()));
    }
}
