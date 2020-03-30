package com.mutiny.demo.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @Author: Anonsmd
 * @Date: 2020/1/11 9:55
 */
public class ModuleDefaultDTO {
    @ApiModelProperty(value = "固定模型list", required = false)
    private List<DefaultModuleInfoDTO> defaultModuleInfoDTOS;

    public List<DefaultModuleInfoDTO> getDefaultModuleInfoDTOS() {
        return defaultModuleInfoDTOS;
    }

    public void setDefaultModuleInfoDTOS(List<DefaultModuleInfoDTO> defaultModuleInfoDTOS) {
        this.defaultModuleInfoDTOS = defaultModuleInfoDTOS;
    }
}
