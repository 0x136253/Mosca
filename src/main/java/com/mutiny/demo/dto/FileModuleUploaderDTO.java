package com.mutiny.demo.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

/**
 * @Author: Anonsmd
 * @Date: 2020/1/11 14:07
 */
public class FileModuleUploaderDTO {
    @ApiModelProperty(value = "模型ID", required = true)
    private int moduleID;
    @ApiModelProperty(value = "加密算法", required = true)
    private String EncodeAlgorithm;
    @ApiModelProperty(value = "密钥", required = true)
    private int KeyFileId;
    @ApiModelProperty(value = "一共分几块", required = true)
    private int total;
    @ApiModelProperty(value = "现在第几块", required = true)
    private int now;
    @ApiModelProperty(value = "数据内容", required = true)
    private Map<String, List<String>> data;
    @ApiModelProperty(value = "是否为固定模型数据", required = true)
    private boolean is_default;


    public boolean isIs_default() {
        return is_default;
    }

    public void setIs_default(boolean is_default) {
        this.is_default = is_default;
    }

    public String getEncodeAlgorithm() {
        return EncodeAlgorithm;
    }

    public void setEncodeAlgorithm(String encodeAlgorithm) {
        EncodeAlgorithm = encodeAlgorithm;
    }

    public int getKeyFileId() {
        return KeyFileId;
    }

    public void setKeyFileId(int keyFileId) {
        KeyFileId = keyFileId;
    }

    public Map<String, List<String>> getData() {
        return data;
    }

    public void setData(Map<String, List<String>> data) {
        this.data = data;
    }

    public int getModuleID() {
        return moduleID;
    }

    public void setModuleID(int moduleID) {
        this.moduleID = moduleID;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getNow() {
        return now;
    }

    public void setNow(int now) {
        this.now = now;
    }
}
