package com.mutiny.demo.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @Author: Anonsmd
 * @Date: 2020/1/11 9:55
 */
public class ModuleDefaultDataDTO {
    @ApiModelProperty(value = "固定模型数据源list", required = false)
    private List<DefaultModuleDataInfoDTO> defaultModuleDataInfoDTOS;

    public List<DefaultModuleDataInfoDTO> getDefaultModuleDataInfoDTOS() {
        return defaultModuleDataInfoDTOS;
    }

    public void setDefaultModuleDataInfoDTOS(List<DefaultModuleDataInfoDTO> defaultModuleDataInfoDTOS) {
        this.defaultModuleDataInfoDTOS = defaultModuleDataInfoDTOS;
    }
}
