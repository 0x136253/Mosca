package com.mutiny.demo.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: Anonsmd
 * @Date: 2020/1/11 9:56
 */
public class DefaultModuleInfoDTO {
    @ApiModelProperty(value = "固定模型ID", required = true)
    private String defaultID;
    @ApiModelProperty(value = "模型名", required = true)
    private String moduleName;
    @ApiModelProperty(value = "固定模型描述", required = true)
    private String description;
    @ApiModelProperty(value = "风控函数", required = true)
    private String function;
    @ApiModelProperty(value = "参数数量", required = true)
    private String paramNumber;

    public String getParamNumber() {
        return paramNumber;
    }

    public void setParamNumber(String paramNumber) {
        this.paramNumber = paramNumber;
    }

    public String getDefaultID() {
        return defaultID;
    }

    public void setDefaultID(String defaultID) {
        this.defaultID = defaultID;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
