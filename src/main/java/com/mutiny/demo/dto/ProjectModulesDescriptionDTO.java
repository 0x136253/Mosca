package com.mutiny.demo.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: Anonsmd
 * @Date: 2020/1/11 10:22
 */
public class ProjectModulesDescriptionDTO {
    @ApiModelProperty(value = "项目ID", required = true)
    private int project_ID;
    @ApiModelProperty(value = "模型描述", required = true)
    private String moduleDescription;

    public int getProject_ID() {
        return project_ID;
    }

    public void setProject_ID(int project_ID) {
        this.project_ID = project_ID;
    }

    public String getModuleDescription() {
        return moduleDescription;
    }

    public void setModuleDescription(String moduleDescription) {
        this.moduleDescription = moduleDescription;
    }
}
