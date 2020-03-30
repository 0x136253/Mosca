package com.mutiny.demo.dto;

import com.mutiny.demo.pojo.Project;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: Anonsmd
 * @Date: 2020/1/11 8:59
 */
public class ProjectInfoDTO {
    @ApiModelProperty(value = "项目ID", required = true)
    private int Project_ID;
    @ApiModelProperty(value = "项目名", required = true)
    private String projectName;
    @ApiModelProperty(value = "项目用途", required = true)
    private String projectPurpose;
    @ApiModelProperty(value = "项目描述", required = true)
    private String projectDescription;
    @ApiModelProperty(value = "模型描述", required = true)
    private String moduleDescription;
    @ApiModelProperty(value = "创建者", required = true)
    private String creater;
    @ApiModelProperty(value = "一级邀请码", required = true)
    private String invitecode1;
    @ApiModelProperty(value = "二级邀请码", required = true)
    private String invitecode2;
    @ApiModelProperty(value = "是否结束", required = true)
    private boolean Isend;
    @ApiModelProperty(value = "是否为固定模型", required = true)
    private boolean Is_Default;
    @ApiModelProperty(value = "状态", required = true)
    private int stage;

    public ProjectInfoDTO(Project project,String creater) {
        if (creater!=null) {
            this.creater=creater;
        }
        if (project.getProjectId()!=null) {
            this.Project_ID=project.getProjectId();
        }
        if (project.getProjectName()!=null) {
            this.projectName=project.getProjectName();
        }
        if (project.getProjectPurpose()!=null) {
            this.projectPurpose=project.getProjectPurpose();
        }
        if (project.getProjectDescription()!=null) {
            this.projectDescription=project.getProjectDescription();
        }
        if (project.getModuleDescription()!=null) {
            this.moduleDescription=project.getModuleDescription();
        }
        if (project.getInviteCode1()!=null) {
            this.invitecode1=project.getInviteCode1();
        }
        if (project.getInviteCode2()!=null) {
            this.invitecode2=project.getInviteCode2();
        }
        if (project.getIsEnd()!=null) {
            this.Isend=project.getIsEnd();
        }
        if (project.getIsDefault()!=null) {
            this.Is_Default=project.getIsDefault();
        }
        if (project.getStage()!=null) {
            this.stage=project.getStage();
        }
    }

    public ProjectInfoDTO() {
    }

    public int getProject_ID() {
        return Project_ID;
    }

    public void setProject_ID(int project_ID) {
        Project_ID = project_ID;
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

    public String getModuleDescription() {
        return moduleDescription;
    }

    public void setModuleDescription(String moduleDescription) {
        this.moduleDescription = moduleDescription;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getInvitecode1() {
        return invitecode1;
    }

    public void setInvitecode1(String invitecode1) {
        this.invitecode1 = invitecode1;
    }

    public String getInvitecode2() {
        return invitecode2;
    }

    public void setInvitecode2(String invitecode2) {
        this.invitecode2 = invitecode2;
    }

    public boolean isIsend() {
        return Isend;
    }

    public void setIsend(boolean isend) {
        Isend = isend;
    }

    public boolean isIs_Default() {
        return Is_Default;
    }

    public void setIs_Default(boolean is_Default) {
        Is_Default = is_Default;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }
}
