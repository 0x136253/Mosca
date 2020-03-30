package com.mutiny.demo.dto;

import com.mutiny.demo.pojo.DefaultModule;
import io.swagger.annotations.ApiModelProperty;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: Anonsmd
 * @Date: 2020/1/12 17:14
 */
public class DefaultModuleAddDTO {
    @ApiModelProperty(value = "模型名", required = true)
    private String moduleName;
    @ApiModelProperty(value = "固定模型描述", required = true)
    private String description;
    @ApiModelProperty(value = "风控函数", required = true)
    private String function;

    public DefaultModule ToModule(){
        DefaultModule defaultModule = new DefaultModule();
        defaultModule.setName(moduleName);
        defaultModule.setDescription(description);
        defaultModule.setFunction(function);
        return defaultModule;
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

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

}
