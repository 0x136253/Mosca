package com.mutiny.demo.dto;

/**
 * @Author: Anonsmd
 * @Date: 2020/3/23 16:33
 */
public class ModuleUploadInfoDTO {
    private String name;
    private String Destription;
    private String type;
    private int length;

    public ModuleUploadInfoDTO() {
    }

    public ModuleUploadInfoDTO(String name, String destription, String type, int length) {
        this.name = name;
        Destription = destription;
        this.type = type;
        this.length = length;
    }

    public String getDestription() {
        return Destription;
    }

    public void setDestription(String destription) {
        Destription = destription;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
