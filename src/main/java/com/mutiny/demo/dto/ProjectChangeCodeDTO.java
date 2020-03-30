package com.mutiny.demo.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: Anonsmd
 * @Date: 2020/1/11 9:15
 */
public class ProjectChangeCodeDTO {
    @ApiModelProperty(value = "项目ID", required = true)
    private int Project_ID;
    @ApiModelProperty(value = "一级邀请码", required = true)
    private String invitecode1;
    @ApiModelProperty(value = "二级邀请码", required = true)
    private String invitecode2;

    public int getProject_ID() {
        return Project_ID;
    }

    public void setProject_ID(int project_ID) {
        Project_ID = project_ID;
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
}
