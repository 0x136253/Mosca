package com.mutiny.demo.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Anonsmd
 * @Date: 2020/1/31 15:35
 */
public class FileModuleTempShowDTO {
    private String username;
    private int FileID;
    private Map<String, List<String> > data;

    public FileModuleTempShowDTO() {
        this.data = new HashMap<>();
    }

    public int getFileID() {
        return FileID;
    }

    public void setFileID(int fileID) {
        FileID = fileID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Map<String, List<String>> getData() {
        return data;
    }

    public void setData(Map<String, List<String>> data) {
        this.data = data;
    }
}
