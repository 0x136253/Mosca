package com.mutiny.demo.dto;

import java.util.Date;

public class LogModuleDTO {
    private String username;
    private int moduleId;
    private String moduleName;
    private int projectId;
    private String projectCreateUsername;
    private int compantId;
    private String projectCreateCompany;
    private Date projectCreateTime;
    private boolean isCalculate;

    public int getCompantId() {
        return compantId;
    }

    public void setCompantId(int compantId) {
        this.compantId = compantId;
    }

    public LogModuleDTO(String username, int moduleId, String moduleName, int projectId, String projectCreateUsername, int compantId, String projectCreateCompany, Date projectCreateTime, boolean isCalculate) {
        this.username = username;
        this.moduleId = moduleId;
        this.moduleName = moduleName;
        this.projectId = projectId;
        this.projectCreateUsername = projectCreateUsername;
        this.compantId = compantId;
        this.projectCreateCompany = projectCreateCompany;
        this.projectCreateTime = projectCreateTime;
        this.isCalculate = isCalculate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getProjectCreateUsername() {
        return projectCreateUsername;
    }

    public void setProjectCreateUsername(String projectCreateUsername) {
        this.projectCreateUsername = projectCreateUsername;
    }

    public String getProjectCreateCompany() {
        return projectCreateCompany;
    }

    public void setProjectCreateCompany(String projectCreateCompany) {
        this.projectCreateCompany = projectCreateCompany;
    }

    public Date getProjectCreateTime() {
        return projectCreateTime;
    }

    public void setProjectCreateTime(Date projectCreateTime) {
        this.projectCreateTime = projectCreateTime;
    }

    public boolean isCalculate() {
        return isCalculate;
    }

    public void setCalculate(boolean calculate) {
        isCalculate = calculate;
    }

    public LogModuleDTO() {
    }
}
