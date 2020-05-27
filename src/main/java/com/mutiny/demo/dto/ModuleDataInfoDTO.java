package com.mutiny.demo.dto;

import java.util.Date;

public class ModuleDataInfoDTO {
    private Integer moduleId;
    private String moduleName;
    private Date upTime;
    private boolean isCalculate;
    private Integer getAnswNum;
    private String FileURl;

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    public String getFileURl() {
        return FileURl;
    }

    public void setFileURl(String fileURl) {
        FileURl = fileURl;
    }


    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public Date getUpTime() {
        return upTime;
    }

    public void setUpTime(Date upTime) {
        this.upTime = upTime;
    }

    public boolean isCalculate() {
        return isCalculate;
    }

    public void setCalculate(boolean calculate) {
        isCalculate = calculate;
    }

    public Integer getGetAnswNum() {
        return getAnswNum;
    }

    public void setGetAnswNum(Integer getAnswNum) {
        this.getAnswNum = getAnswNum;
    }

    public ModuleDataInfoDTO() {
        System.out.println("ModuleDataInfoDTO");
    }

    public ModuleDataInfoDTO(Integer moduleId, String moduleName, Date upTime, boolean isCalculate, Integer getAnswNum, String fileURl) {
        this.moduleId = moduleId;
        this.moduleName = moduleName;
        this.upTime = upTime;
        this.isCalculate = isCalculate;
        this.getAnswNum = getAnswNum;
        FileURl = fileURl;
    }
}
