package com.mutiny.demo.dto;

import com.mutiny.demo.pojo.Module;
import com.mutiny.demo.pojo.Project;
import io.swagger.annotations.ApiModelProperty;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: Anonsmd
 * @Date: 2020/1/11 9:13
 */
public class ModuleInfoDTO {
    @ApiModelProperty(value = "模型ID", required = false)
    private int moduleID;
    @ApiModelProperty(value = "模型名", required = true)
    private String moduleName;
    @ApiModelProperty(value = "原始模型函数", required = true)
    private String function;
    @ApiModelProperty(value = "改进模型函数", required = true)
    private String Tfunction;
    @ApiModelProperty(value = "模型参数", required = true)
    private String changeFun;
    @ApiModelProperty(value = "密钥Id", required = true)
    private int keyFileId;
    @ApiModelProperty(value = "模型参数数量", required = true)
    private int paramNumber;
    @ApiModelProperty(value = "扩大倍数", required = true)
    private int multNum;
//    @ApiModelProperty(value = "模型描述", required = true)
//    private String description;
    @ApiModelProperty(value = "项目ID", required = false)
    private int projectID;
    @ApiModelProperty(value = "是否计算完毕", required = false)
    private boolean isCalculate;
    @ApiModelProperty(value = "是否为固定模型", required = true)
    private boolean Is_Default;
    @ApiModelProperty(value = "是否有效", required = true)
    private boolean Is_Useful;
    @ApiModelProperty(value = "固定模型对应的数据源,仅当模型是固定模型的时候有", required = true)
    private int DefaultModule_ID;
    private Date upTime;
    private Date createTime;
    private int status;
    private String creater;
    private List<ModuleUploadInfoDTO> map;

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public ModuleInfoDTO() {
    }

    public ModuleInfoDTO(Module module) {
        if (module.getModuleId()!=null) {
            this.moduleID=module.getModuleId();
        }
        if (module.getModuleName()!=null) {
            this.moduleName=module.getModuleName();
        }
        if (module.getFunction()!=null) {
            this.function=module.getFunction();
        }
        if (module.getParamNumber()!=null) {
            this.paramNumber=module.getParamNumber();
        }
        if (module.getDescription()!=null && module.getFunction()!=null) {
            this.map=showUploadNeed(module.getFunction(),module.getDescription());
        }
        if (module.getProjectId()!=null) {
            this.projectID=module.getProjectId();
        }
        if (module.getIsCalculate()!=null) {
            this.isCalculate=module.getIsCalculate();
        }
        if (module.getTfunction()!=null){
            this.Tfunction=module.getTfunction();
        }
        if (module.getChangefun()!=null){
            this.changeFun=module.getChangefun();
        }
        if (module.getKeyfileid()!=null){
            this.keyFileId=module.getKeyfileid();
        }
        if (module.getMultnum()!=null){
            this.multNum=module.getMultnum();
        }
        if (module.getIsDefault()!=null) {
            this.Is_Default=module.getIsDefault();
        }
        if (module.getIsUserful()!=null) {
            this.Is_Useful=module.getIsUserful();
        }
        if (module.getDefaultmoduleId()!=null) {
            this.DefaultModule_ID=module.getDefaultmoduleId();
        }
        if (module.getCreatetime()!=null){
            this.createTime = module.getCreatetime();
        }
        if (module.getUptime()!=null){
            this.upTime = module.getUptime();
        }
        if (module.getStatus()!=null){
            this.status = module.getStatus();
        }
    }

    public int getMultNum() {
        return multNum;
    }

    public void setMultNum(int multNum) {
        this.multNum = multNum;
    }

    public int getModuleID() {
        return moduleID;
    }

    public void setModuleID(int moduleID) {
        this.moduleID = moduleID;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getTfunction() {
        return Tfunction;
    }

    public void setTfunction(String tfunction) {
        Tfunction = tfunction;
    }

    public String getChangeFun() {
        return changeFun;
    }

    public void setChangeFun(String changeFun) {
        this.changeFun = changeFun;
    }

    public int getKeyFileId() {
        return keyFileId;
    }

    public void setKeyFileId(int keyFileId) {
        this.keyFileId = keyFileId;
    }

    public int getParamNumber() {
        return paramNumber;
    }

    public void setParamNumber(int paramNumber) {
        this.paramNumber = paramNumber;
    }

    public Date getUpTime() {
        return upTime;
    }

    public void setUpTime(Date upTime) {
        this.upTime = upTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<ModuleUploadInfoDTO> getMap() {
        return map;
    }

    public void setMap(List<ModuleUploadInfoDTO> map) {
        this.map = map;
    }

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public boolean isCalculate() {
        return isCalculate;
    }

    public void setCalculate(boolean calculate) {
        isCalculate = calculate;
    }

    public boolean isIs_Default() {
        return Is_Default;
    }

    public void setIs_Default(boolean is_Default) {
        Is_Default = is_Default;
    }

    public boolean isIs_Useful() {
        return Is_Useful;
    }

    public void setIs_Useful(boolean is_Useful) {
        Is_Useful = is_Useful;
    }

    public int getDefaultModule_ID() {
        return DefaultModule_ID;
    }

    public void setDefaultModule_ID(int defaultModule_ID) {
        DefaultModule_ID = defaultModule_ID;
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
