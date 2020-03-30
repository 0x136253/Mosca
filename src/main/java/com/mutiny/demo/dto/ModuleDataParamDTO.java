package com.mutiny.demo.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.Map;

/**
 * @Author: Anonsmd
 * @Date: 2020/1/11 9:45
 */
public class ModuleDataParamDTO {
    @ApiModelProperty(value = "模型ID", required = true)
    private int moduleID;
    @ApiModelProperty(value = "数据与参数对应关系", required = true)
    private Map<String,String> dataParam;

    public int getModuleID() {
        return moduleID;
    }

    public void setModuleID(int moduleID) {
        this.moduleID = moduleID;
    }

    public Map<String, String> getDataParam() {
        return dataParam;
    }

    public void setDataParam(Map<String, String> dataParam) {
        this.dataParam = dataParam;
    }
}
