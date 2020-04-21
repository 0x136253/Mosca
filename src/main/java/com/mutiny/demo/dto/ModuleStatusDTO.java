package com.mutiny.demo.dto;

import java.util.Date;

public class ModuleStatusDTO {
    private int projectId;
    private int moduleId;
    private String projectName;
    private String moduleName;
    private Date createTime;
    private Date upTime;
    private int status;

    public ModuleStatusDTO() {
    }

    public ModuleStatusDTO(int projectId, int moduleId, String projectName, String moduleName) {
        this.projectId = projectId;
        this.moduleId = moduleId;
        this.projectName = projectName;
        this.moduleName = moduleName;
    }

    public ModuleStatusDTO(int projectId) {
        this.projectId = projectId;
    }

    public ModuleStatusDTO(int projectId, int moduleId, String projectName, String moduleName, Date createTime, Date upTime, int status) {
        this.projectId = projectId;
        this.moduleId = moduleId;
        this.projectName = projectName;
        this.moduleName = moduleName;
        this.createTime = createTime;
        this.upTime = upTime;
        this.status = status;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpTime() {
        return upTime;
    }

    public void setUpTime(Date upTime) {
        this.upTime = upTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
