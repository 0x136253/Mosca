package com.mutiny.demo.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: Anonsmd
 * @Date: 2020/1/11 11:43
 */
public class ProjectCompleteDTO {
    @ApiModelProperty(value = "自己创建项目未完成", required = true)
    private int CreateNotComplete;
    @ApiModelProperty(value = "自己创建项目完成", required = true)
    private int CreateComplete;
    @ApiModelProperty(value = "参与项目未完成", required = true)
    private int JoinNotComplete;
    @ApiModelProperty(value = "参与项目完成", required = true)
    private int JoinComplete;
    @ApiModelProperty(value = "旁观项目未完成", required = true)
    private int WatchNotComplete;
    @ApiModelProperty(value = "旁观项目完成", required = true)
    private int WatchComplete;

    public int getCreateNotComplete() {
        return CreateNotComplete;
    }

    public void setCreateNotComplete(int createNotComplete) {
        CreateNotComplete = createNotComplete;
    }

    public int getCreateComplete() {
        return CreateComplete;
    }

    public void setCreateComplete(int createComplete) {
        CreateComplete = createComplete;
    }

    public int getJoinNotComplete() {
        return JoinNotComplete;
    }

    public void setJoinNotComplete(int joinNotComplete) {
        JoinNotComplete = joinNotComplete;
    }

    public int getJoinComplete() {
        return JoinComplete;
    }

    public void setJoinComplete(int joinComplete) {
        JoinComplete = joinComplete;
    }

    public int getWatchNotComplete() {
        return WatchNotComplete;
    }

    public void setWatchNotComplete(int watchNotComplete) {
        WatchNotComplete = watchNotComplete;
    }

    public int getWatchComplete() {
        return WatchComplete;
    }

    public void setWatchComplete(int watchComplete) {
        WatchComplete = watchComplete;
    }
}
