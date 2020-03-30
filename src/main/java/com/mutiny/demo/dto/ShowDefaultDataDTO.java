package com.mutiny.demo.dto;

import java.util.Date;

/**
 * @Author: Anonsmd
 * @Date: 2020/3/28 20:57
 */
public class ShowDefaultDataDTO {
    private String ModuleName;
    private int defaultDataId;
    private String DataName;
    private int status;
    private Date upDate;
    private Date calDate;

    public ShowDefaultDataDTO() {
    }

    public ShowDefaultDataDTO(String moduleName, int defaultDataId, String dataName, int status) {
        ModuleName = moduleName;
        this.defaultDataId = defaultDataId;
        DataName = dataName;
        this.status = status;
    }

    public ShowDefaultDataDTO(String moduleName, int defaultDataId, String dataName, int status, Date upDate, Date calDate) {
        ModuleName = moduleName;
        this.defaultDataId = defaultDataId;
        DataName = dataName;
        this.status = status;
        this.upDate = upDate;
        this.calDate = calDate;
    }

    public String getModuleName() {
        return ModuleName;
    }

    public void setModuleName(String moduleName) {
        ModuleName = moduleName;
    }

    public int getDefaultDataId() {
        return defaultDataId;
    }

    public void setDefaultDataId(int defaultDataId) {
        this.defaultDataId = defaultDataId;
    }

    public String getDataName() {
        return DataName;
    }

    public void setDataName(String dataName) {
        DataName = dataName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getUpDate() {
        return upDate;
    }

    public void setUpDate(Date upDate) {
        this.upDate = upDate;
    }

    public Date getCalDate() {
        return calDate;
    }

    public void setCalDate(Date calDate) {
        this.calDate = calDate;
    }
}
