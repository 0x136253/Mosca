package com.mutiny.demo.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Anonsmd
 * @Date: 2020/1/31 15:36
 */
public class FileModuleTempShowListDTO {
    private List<FileModuleTempShowDTO> fileModuleTempShowDTOS;

    public FileModuleTempShowListDTO() {
        this.fileModuleTempShowDTOS = new ArrayList<>();
    }

    public boolean add(FileModuleTempShowDTO fileModuleTempShowDTO){
        return fileModuleTempShowDTOS.add(fileModuleTempShowDTO);
    }

    public List<FileModuleTempShowDTO> getFileModuleTempShowDTOS() {
        return fileModuleTempShowDTOS;
    }

    public void setFileModuleTempShowDTOS(List<FileModuleTempShowDTO> fileModuleTempShowDTOS) {
        this.fileModuleTempShowDTOS = fileModuleTempShowDTOS;
    }
}
