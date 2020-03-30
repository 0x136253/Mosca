package com.mutiny.demo.dto;

import java.util.Date;

/**
 * @Author: Anonsmd
 * @Date: 2020/3/23 17:34
 */
public class PermissionApplyDTO {
    private int ID;
    private String ModuleName;
    private int defaultDataId;
    private String DataName;
    private int status;
    private Date applyDate;
    private Date handleDate;
    private int projectId;
    private String companyName;

    public PermissionApplyDTO() {
    }

    public PermissionApplyDTO(int ID, String moduleName, int defaultDataId, String dataName, int status, Date applyDate, Date handleDate, int projectId, String companyName) {
        this.ID = ID;
        ModuleName = moduleName;
        this.defaultDataId = defaultDataId;
        DataName = dataName;
        this.status = status;
        this.applyDate = applyDate;
        this.handleDate = handleDate;
        this.projectId = projectId;
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getDefaultDataId() {
        return defaultDataId;
    }

    public void setDefaultDataId(int defaultDataId) {
        this.defaultDataId = defaultDataId;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getModuleName() {
        return ModuleName;
    }

    public void setModuleName(String moduleName) {
        ModuleName = moduleName;
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

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public Date getHandleDate() {
        return handleDate;
    }

    public void setHandleDate(Date handleDate) {
        this.handleDate = handleDate;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }
}
