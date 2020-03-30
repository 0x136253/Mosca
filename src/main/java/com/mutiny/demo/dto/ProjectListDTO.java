package com.mutiny.demo.dto;

import java.util.List;

/**
 * @Author: Anonsmd
 * @Date: 2020/1/11 11:34
 */
public class ProjectListDTO {
    private List<ProjectInfoDTO> projectInfoDTOS;

    public List<ProjectInfoDTO> getProjectInfoDTOS() {
        return projectInfoDTOS;
    }

    public void setProjectInfoDTOS(List<ProjectInfoDTO> projectInfoDTOS) {
        this.projectInfoDTOS = projectInfoDTOS;
    }
}
