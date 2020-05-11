package com.mutiny.demo.dto;

public class UpdateMailDTO {
    private String oldMail;
    private String newMail;

    public String getOldMail() {
        return oldMail;
    }

    public void setOldMail(String oldMail) {
        this.oldMail = oldMail;
    }

    public String getNewMail() {
        return newMail;
    }

    public void setNewMail(String newMail) {
        this.newMail = newMail;
    }

    public UpdateMailDTO() {
    }

    public UpdateMailDTO(String oldMail, String newMail) {
        this.oldMail = oldMail;
        this.newMail = newMail;
    }
}
