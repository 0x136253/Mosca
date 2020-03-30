package com.mutiny.demo.dto;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;

/**
 * @Author: Anonsmd
 * @Date: 2020/1/11 15:58
 */
public class PermissRequestDTO {
    @ApiModelProperty(value = "项目ID", required  = true)
    private int ProjectID;
    @ApiModelProperty(value = "模型ID", required  = true)
    private int ModuleID;

    public int getProjectID() {
        return ProjectID;
    }

    public void setProjectID(int projectID) {
        ProjectID = projectID;
    }

    public int getModuleID() {
        return ModuleID;
    }

    public void setModuleID(int moduleID) {
        ModuleID = moduleID;
    }
}
