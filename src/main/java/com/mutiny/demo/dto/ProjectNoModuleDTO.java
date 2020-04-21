package com.mutiny.demo.dto;

import java.util.Date;

public class ProjectNoModuleDTO {
    private int projectId;
    private Date createTime;
    private String name;

    public ProjectNoModuleDTO() {
    }

    public ProjectNoModuleDTO(int projectId, String name) {
        this.projectId = projectId;
        this.name = name;
    }

    public ProjectNoModuleDTO(int projectId, Date createTime, String name) {
        this.projectId = projectId;
        this.createTime = createTime;
        this.name = name;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
