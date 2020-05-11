package com.mutiny.demo.dto;

import com.mutiny.demo.pojo.User;

public class UserCheckDTO {
    private String username;
    private String name;
    private String mail;
    private String tel;
    private String companyOrGovernName;
    private int companyOrGovernID;
    private int FileId;
    private String FileURl;
    private String PortraitURL;

    public UserCheckDTO() {
    }

    public UserCheckDTO(User user,String PUrl,String partname,int FileId,String FileUrl){
        this.username = user.getId();
        this.name = user.getName();
        this.mail = user.getEmail();
        this.tel = user.getTel();
        this.PortraitURL = PUrl;
        if (user.getType().equals("企业管理员")){
            this.companyOrGovernID = user.getCompanyid();
            this.companyOrGovernName = partname;
            this.FileId = FileId;
            this.FileURl = FileUrl;
        }
        else if (user.getType().equals("政府")){
            this.companyOrGovernID = user.getGovernid();
            this.companyOrGovernName = partname;
            this.FileId = FileId;
            this.FileURl = FileUrl;
        }
    }

    public UserCheckDTO(String username, String name, String mail, String tel, String companyOrGovernName, int companyOrGovernID, int fileId, String fileURl, String portraitURL) {
        this.username = username;
        this.name = name;
        this.mail = mail;
        this.tel = tel;
        this.companyOrGovernName = companyOrGovernName;
        this.companyOrGovernID = companyOrGovernID;
        FileId = fileId;
        FileURl = fileURl;
        PortraitURL = portraitURL;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCompanyOrGovernName() {
        return companyOrGovernName;
    }

    public void setCompanyOrGovernName(String companyOrGovernName) {
        this.companyOrGovernName = companyOrGovernName;
    }

    public int getCompanyOrGovernID() {
        return companyOrGovernID;
    }

    public void setCompanyOrGovernID(int companyOrGovernID) {
        this.companyOrGovernID = companyOrGovernID;
    }

    public int getFileId() {
        return FileId;
    }

    public void setFileId(int fileId) {
        FileId = fileId;
    }

    public String getFileURl() {
        return FileURl;
    }

    public void setFileURl(String fileURl) {
        FileURl = fileURl;
    }

    public String getPortraitURL() {
        return PortraitURL;
    }

    public void setPortraitURL(String portraitURL) {
        PortraitURL = portraitURL;
    }
}
