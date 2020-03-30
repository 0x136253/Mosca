package com.mutiny.demo.dto;

import com.mutiny.demo.pojo.User;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @Author: Anonsmd
 * @Date: 2020/1/10 16:55
 */
public class UserRegisterUserDTO {
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

    public User ToUser(){
        User user= new User();
        user.setId(ID);
        user.setPassword(Password);
        user.setName(Name);
        user.setTel(Tel);
        user.setEmail(Email);
        user.setType("企业员工");
        user.setCreattime(new Date());
        return user;
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
}
