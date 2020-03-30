package com.mutiny.demo.pojo;

import java.io.Serializable;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table Company
 *
 * @mbg.generated do_not_delete_during_merge 2020-03-25 16:55:44
 */
public class Company implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column Company.CompanyId
     *
     * @mbg.generated 2020-03-25 16:55:44
     */
    private Integer companyid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column Company.Name
     *
     * @mbg.generated 2020-03-25 16:55:44
     */
    private String name;

    /**
     * Database Column Remarks:
     *   文件ID[企业为营业执照|政府为组织机构代码证]
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column Company.FileId
     *
     * @mbg.generated 2020-03-25 16:55:44
     */
    private Integer fileid;

    /**
     * Database Column Remarks:
     *   认证code[企业为社会统一信用代码|政府为组织机构代码
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column Company.Cer_Code
     *
     * @mbg.generated 2020-03-25 16:55:44
     */
    private String cerCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table Company
     *
     * @mbg.generated 2020-03-25 16:55:44
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column Company.CompanyId
     *
     * @return the value of Company.CompanyId
     *
     * @mbg.generated 2020-03-25 16:55:44
     */
    public Integer getCompanyid() {
        return companyid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column Company.CompanyId
     *
     * @param companyid the value for Company.CompanyId
     *
     * @mbg.generated 2020-03-25 16:55:44
     */
    public void setCompanyid(Integer companyid) {
        this.companyid = companyid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column Company.Name
     *
     * @return the value of Company.Name
     *
     * @mbg.generated 2020-03-25 16:55:44
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column Company.Name
     *
     * @param name the value for Company.Name
     *
     * @mbg.generated 2020-03-25 16:55:44
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column Company.FileId
     *
     * @return the value of Company.FileId
     *
     * @mbg.generated 2020-03-25 16:55:44
     */
    public Integer getFileid() {
        return fileid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column Company.FileId
     *
     * @param fileid the value for Company.FileId
     *
     * @mbg.generated 2020-03-25 16:55:44
     */
    public void setFileid(Integer fileid) {
        this.fileid = fileid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column Company.Cer_Code
     *
     * @return the value of Company.Cer_Code
     *
     * @mbg.generated 2020-03-25 16:55:44
     */
    public String getCerCode() {
        return cerCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column Company.Cer_Code
     *
     * @param cerCode the value for Company.Cer_Code
     *
     * @mbg.generated 2020-03-25 16:55:44
     */
    public void setCerCode(String cerCode) {
        this.cerCode = cerCode == null ? null : cerCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table Company
     *
     * @mbg.generated 2020-03-25 16:55:44
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", companyid=").append(companyid);
        sb.append(", name=").append(name);
        sb.append(", fileid=").append(fileid);
        sb.append(", cerCode=").append(cerCode);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}