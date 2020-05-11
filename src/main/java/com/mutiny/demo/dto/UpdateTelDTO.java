package com.mutiny.demo.dto;

public class UpdateTelDTO {
    private String oldTel;
    private String newTel;

    public String getOldTel() {
        return oldTel;
    }

    public void setOldTel(String oldTel) {
        this.oldTel = oldTel;
    }

    public String getNewTel() {
        return newTel;
    }

    public void setNewTel(String newTel) {
        this.newTel = newTel;
    }

    public UpdateTelDTO() {
    }

    public UpdateTelDTO(String oldTel, String newTel) {
        this.oldTel = oldTel;
        this.newTel = newTel;
    }
}
