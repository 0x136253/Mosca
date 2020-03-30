package com.mutiny.demo.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @Author: Anonsmd
 * @Date: 2020/1/12 17:22
 */
public class ProjectDefaultAddDTO {
    @ApiModelProperty(value = "固定模型ID", required = true)
    private List<Integer> defaultDataID;
    @ApiModelProperty(value = "项目ID", required = true)
    private int projectID;

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public List<Integer> getDefaultDataID() {
        return defaultDataID;
    }

    public void setDefaultDataID(List<Integer> defaultDataID) {
        this.defaultDataID = defaultDataID;
    }
}
