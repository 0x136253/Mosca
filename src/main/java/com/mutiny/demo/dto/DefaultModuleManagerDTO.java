package com.mutiny.demo.dto;

import com.mutiny.demo.pojo.DefaultModule;

public class DefaultModuleManagerDTO {
    private int DefaultId;
    private String name;
    private int userfulDataNum;
    private int projectNum;
    private boolean isUserful;

    public DefaultModuleManagerDTO(DefaultModule defaultModule,int DataNum,int projectUNum) {
        this.DefaultId = defaultModule.getDefaultId();
        this.name = defaultModule.getName();
        this.userfulDataNum = DataNum;
        this.projectNum = projectUNum;
        this.isUserful = defaultModule.getIsuserful();
    }

    public DefaultModuleManagerDTO() {
    }

    public DefaultModuleManagerDTO(int defaultId, String name, int userfulDataNum, int projectNum, boolean isUserful) {
        DefaultId = defaultId;
        this.name = name;
        this.userfulDataNum = userfulDataNum;
        this.projectNum = projectNum;
        this.isUserful = isUserful;
    }

    public int getDefaultId() {
        return DefaultId;
    }

    public void setDefaultId(int defaultId) {
        DefaultId = defaultId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserfulDataNum() {
        return userfulDataNum;
    }

    public void setUserfulDataNum(int userfulDataNum) {
        this.userfulDataNum = userfulDataNum;
    }

    public int getProjectNum() {
        return projectNum;
    }

    public void setProjectNum(int projectNum) {
        this.projectNum = projectNum;
    }

    public boolean isUserful() {
        return isUserful;
    }

    public void setUserful(boolean userful) {
        isUserful = userful;
    }
}
