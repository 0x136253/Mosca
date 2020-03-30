package com.mutiny.demo.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: Anonsmd
 * @Date: 2020/1/12 17:33
 */
public class DefaultModuleDataInfoDTO {
    @ApiModelProperty(value = "固定模型数据ID", required = true)
    private String defaultDataID;
    @ApiModelProperty(value = "固定模型ID", required = true)
    private String defaultID;
    @ApiModelProperty(value = "数据名", required = true)
    private String dataName;
    @ApiModelProperty(value = "数据与参数对应关系", required = true)
    private String DataParam;
    @ApiModelProperty(value = "描述", required = true)
    private String description;
    @ApiModelProperty(value = "文件ID", required = true)
    private String fileID;
    @ApiModelProperty(value = "是否有效", required = true)
    private boolean Is_Useful;
    @ApiModelProperty(value = "是否计算完毕", required = true)
    private String isCalculate;

    public String getDefaultDataID() {
        return defaultDataID;
    }

    public void setDefaultDataID(String defaultDataID) {
        this.defaultDataID = defaultDataID;
    }

    public String getDefaultID() {
        return defaultID;
    }

    public void setDefaultID(String defaultID) {
        this.defaultID = defaultID;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public String getDataParam() {
        return DataParam;
    }

    public void setDataParam(String dataParam) {
        DataParam = dataParam;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFileID() {
        return fileID;
    }

    public void setFileID(String fileID) {
        this.fileID = fileID;
    }

    public boolean isIs_Useful() {
        return Is_Useful;
    }

    public void setIs_Useful(boolean is_Useful) {
        Is_Useful = is_Useful;
    }

    public String getIsCalculate() {
        return isCalculate;
    }

    public void setIsCalculate(String isCalculate) {
        this.isCalculate = isCalculate;
    }
}
