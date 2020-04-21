package com.mutiny.demo.dto;

import java.util.Date;

public class ProjectMemberDTO {
    /**
     * username
     * 头衔
     * name
     * 加入时间
     * type
     */
    private String username;
    private String Name;
    private Date inTime;
    private String PortraitURL;
    private String Type;

    public ProjectMemberDTO() {
    }

    public ProjectMemberDTO(String username, Date inTime, String type) {
        this.username = username;
        this.inTime = inTime;
        Type = type;
    }

    public ProjectMemberDTO(String username, String name, Date inTime, String portraitURL, String type) {
        this.username = username;
        Name = name;
        this.inTime = inTime;
        PortraitURL = portraitURL;
        Type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Date getInTime() {
        return inTime;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }

    public String getPortraitURL() {
        return PortraitURL;
    }

    public void setPortraitURL(String portraitURL) {
        PortraitURL = portraitURL;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
