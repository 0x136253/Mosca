package com.mutiny.demo.dto;

import com.mutiny.demo.pojo.Module;
import io.swagger.annotations.ApiModelProperty;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: Anonsmd
 * @Date: 2020/1/11 9:40
 */
public class ModuleAddDTO {
    @ApiModelProperty(value = "模型名", required = true)
    private String moduleName;
    @ApiModelProperty(value = "模型函数", required = true)
    private String function;
    @ApiModelProperty(value = "模型描述", required = true)
    private String description;

    public Module toModule(int ModuleID,int projectID){
        Module module=new Module();
        module.setModuleId(ModuleID);
        module.setProjectId(projectID);
        module.setModuleName(moduleName);
        module.setFunction(function);
        module.setIsCalculate(false);
        module.setIsDefault(false);
        module.setDescription(description);
        return module;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
