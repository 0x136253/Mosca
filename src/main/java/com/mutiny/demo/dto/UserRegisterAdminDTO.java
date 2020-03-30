package com.mutiny.demo.dto;

import com.mutiny.demo.pojo.Govern;
import com.mutiny.demo.pojo.Company;
import com.mutiny.demo.pojo.User;
import io.swagger.annotations.ApiModelProperty;

import java.io.File;
import java.util.Date;

/**
 * @Author: Anonsmd
 * @Date: 2020/1/10 16:55
 */
public class UserRegisterAdminDTO {
    @ApiModelProperty(value = "用户名Username", required = true)
    private String ID;
    @ApiModelProperty(value = "Password", required = true)
    private String Password;
    @ApiModelProperty(value = "姓名", required = true)
    private String Name;
    @ApiModelProperty(value = "电话", required = true)
    private String Tel;
    @ApiModelProperty(value = "Email", required = true)
    private String Email;
    @ApiModelProperty(value = "公司", required = true)
    private String Company;
    @ApiModelProperty(value = "存放公司的营业执照图片的FileID", required = true)
    private int FileID;
    @ApiModelProperty(value = "社会统一信用代码", required = true)
    private String CerCode;

    public Company ToCompany(){
        Company company = new Company();
        company.setName(Company);
        company.setCerCode(CerCode);
        company.setFileid(FileID);
        return company;
    }

    public User ToUser(){
        User user= new User();
        user.setId(ID);
        user.setPassword(Password);
        user.setName(Name);
        user.setTel(Tel);
        user.setEmail(Email);
        user.setCreattime(new Date());
        user.setType("企业管理员");
        return user;
    }

    public String getCerCode() {
        return CerCode;
    }

    public void setCerCode(String cerCode) {
        CerCode = cerCode;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getTel() {
        return Tel;
    }

    public void setTel(String tel) {
        Tel = tel;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getCompany() {
        return Company;
    }

    public void setCompany(String company) {
        Company = company;
    }

    public int getFileID() {
        return FileID;
    }

    public void setFileID(int fileID) {
        FileID = fileID;
    }
}
