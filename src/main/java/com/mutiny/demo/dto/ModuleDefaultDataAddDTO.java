package com.mutiny.demo.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.Map;

/**
 * @Author: Anonsmd
 * @Date: 2020/1/11 9:45
 */
public class ModuleDefaultDataAddDTO {
    @ApiModelProperty(value = "固定模型ID", required = true)
    private int defaultID;
    @ApiModelProperty(value = "数据名", required = true)
    private String dataName;
    @ApiModelProperty(value = "描述", required = true)
    private String description;

    public int getDefaultID() {
        return defaultID;
    }

    public void setDefaultID(int defaultID) {
        this.defaultID = defaultID;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
