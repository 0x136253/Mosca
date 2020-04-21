package com.mutiny.demo.dto;

import com.mutiny.demo.pojo.DefaultData;
import com.mutiny.demo.pojo.DefaultModule;
import com.mutiny.demo.pojo.Module;

import java.io.Serializable;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefaultModuleDTO {
    private Integer defaultId;
    private String name;
    private String function;
    private Integer paramNumber;
    private String tfunction;
    private String changefun;
    private Integer multnum;
    private Boolean isuserful;
    private String protocoldetails;
    private List<ModuleUploadInfoDTO> map;

    public DefaultModuleDTO(DefaultModule module) {
        this.defaultId = module.getDefaultId();
        this.name = module.getName();
        this.function = module.getFunction();
        this.paramNumber = module.getParamNumber();
        this.tfunction = module.getTfunction();
        this.changefun = module.getChangefun();
        this.multnum = module.getMultnum();
        this.isuserful = module.getIsuserful();
        this.protocoldetails = module.getProtocoldetails();
        this.map = showUploadNeed(getFunction(),module.getDescription());
    }

    public DefaultModuleDTO(Integer defaultId, String name, String function, Integer paramNumber, String tfunction, String changefun, Integer multnum, Boolean isuserful, String protocoldetails, List<ModuleUploadInfoDTO> map) {
        this.defaultId = defaultId;
        this.name = name;
        this.function = function;
        this.paramNumber = paramNumber;
        this.tfunction = tfunction;
        this.changefun = changefun;
        this.multnum = multnum;
        this.isuserful = isuserful;
        this.protocoldetails = protocoldetails;
        this.map = map;
    }

    public Integer getDefaultId() {
        return defaultId;
    }

    public void setDefaultId(Integer defaultId) {
        this.defaultId = defaultId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public Integer getParamNumber() {
        return paramNumber;
    }

    public void setParamNumber(Integer paramNumber) {
        this.paramNumber = paramNumber;
    }

    public String getTfunction() {
        return tfunction;
    }

    public void setTfunction(String tfunction) {
        this.tfunction = tfunction;
    }

    public String getChangefun() {
        return changefun;
    }

    public void setChangefun(String changefun) {
        this.changefun = changefun;
    }

    public Integer getMultnum() {
        return multnum;
    }

    public void setMultnum(Integer multnum) {
        this.multnum = multnum;
    }

    public Boolean getIsuserful() {
        return isuserful;
    }

    public void setIsuserful(Boolean isuserful) {
        this.isuserful = isuserful;
    }

    public String getProtocoldetails() {
        return protocoldetails;
    }

    public void setProtocoldetails(String protocoldetails) {
        this.protocoldetails = protocoldetails;
    }

    public List<ModuleUploadInfoDTO> getMap() {
        return map;
    }

    public void setMap(List<ModuleUploadInfoDTO> map) {
        this.map = map;
    }


    private List<ModuleUploadInfoDTO> showUploadNeed(String func, String des){
        Map<String,String> map = getCharDes(des);
        Iterator<String> chars = getIdentifiy(func).iterator();
        List<ModuleUploadInfoDTO> answ = new ArrayList<>();
        while (chars.hasNext()){
            String tmp = chars.next();
            String dess = null;
            if (map.containsKey(tmp)){
                dess=map.get(tmp);
            }
            ModuleUploadInfoDTO temp = new ModuleUploadInfoDTO(tmp,dess,"整数",16);
            answ.add(temp);
        }
        return answ;
    }


    private Map<String,String> getCharDes(String Desc){
        Map<String,String> answ = new HashMap<>();
        String[] strs = Desc.split(" ");
        for (String record:strs){
            String[] temps = record.split(":");
            if (temps.length<2){
                continue;
            }
            answ.put(temps[0],temps[1]);
        }
        return answ;
    }

    private Set<String> getIdentifiy(String function){
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
}