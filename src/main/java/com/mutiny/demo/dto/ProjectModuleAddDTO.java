package com.mutiny.demo.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @Author: Anonsmd
 * @Date: 2020/1/11 9:18
 */
public class ProjectModuleAddDTO {
    @ApiModelProperty(value = "项目ID", required = true)
    private int Project_ID;
    @ApiModelProperty(value = "模型list", required = true)
    private List<ModuleAddDTO> moduleAddDTOS;

    public int getProject_ID() {
        return Project_ID;
    }

    public void setProject_ID(int project_ID) {
        Project_ID = project_ID;
    }

    public List<ModuleAddDTO> getModuleAddDTOS() {
        return moduleAddDTOS;
    }

    public void setModuleAddDTOS(List<ModuleAddDTO> moduleAddDTOS) {
        this.moduleAddDTOS = moduleAddDTOS;
    }
}
