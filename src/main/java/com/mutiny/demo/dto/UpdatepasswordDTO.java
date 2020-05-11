package com.mutiny.demo.dto;

public class UpdatepasswordDTO {
    private String oldPassWord;
    private String newPassWord;

    public UpdatepasswordDTO() {
    }

    public String getOldPassWord() {
        return oldPassWord;
    }

    public void setOldPassWord(String oldPassWord) {
        this.oldPassWord = oldPassWord;
    }

    public String getNewPassWord() {
        return newPassWord;
    }

    public void setNewPassWord(String newPassWord) {
        this.newPassWord = newPassWord;
    }

    public UpdatepasswordDTO(String oldPassWord, String newPassWord) {
        this.oldPassWord = oldPassWord;
        this.newPassWord = newPassWord;
    }
}
