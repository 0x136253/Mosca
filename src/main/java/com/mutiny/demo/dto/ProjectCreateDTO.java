package com.mutiny.demo.dto;

import com.mutiny.demo.pojo.Project;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @Author: Anonsmd
 * @Date: 2020/1/11 8:58
 */
public class ProjectCreateDTO {
    @ApiModelProperty(value = "项目名", required = true)
    private String projectName;
    @ApiModelProperty(value = "项目用途", required = true)
    private String projectPurpose;
    @ApiModelProperty(value = "项目描述", required = true)
    private String projectDescription;

    public Project ToProject(){
        Project project=new Project();
        project.setProjectName(projectName);
        project.setProjectPurpose(projectPurpose);
        project.setProjectDescription(projectDescription);
        project.setCreateTime(new Date());
        return project;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectPurpose() {
        return projectPurpose;
    }

    public void setProjectPurpose(String projectPurpose) {
        this.projectPurpose = projectPurpose;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

}
