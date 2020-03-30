package com.mutiny.demo.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @Author: Anonsmd
 * @Date: 2020/1/12 11:35
 */
public class ModuleListDTO {

    @ApiModelProperty(value = "模型List", required  = true)
    private List<ModuleInfoDTO> moduleInfoDTOList;

    public List<ModuleInfoDTO> getModuleInfoDTOList() {
        return moduleInfoDTOList;
    }

    public void setModuleInfoDTOList(List<ModuleInfoDTO> moduleInfoDTOList) {
        this.moduleInfoDTOList = moduleInfoDTOList;
    }
}
