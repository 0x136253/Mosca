package com.mutiny.demo.dto;

import com.mutiny.demo.pojo.DefaultData;
import com.mutiny.demo.pojo.DefaultModule;
import com.mutiny.demo.pojo.Module;

import java.util.List;

public class CalculateShowModuleDTO {
    private int Id;
    private String name;
    private List<CalculateShowCalDTO> lists;

    public boolean fromModule(Module module){
        Id = module.getModuleId();
        name = module.getModuleName();
        return true;
    }

    public boolean fromDefaultModule(DefaultModule defaultModule){
        Id = defaultModule.getDefaultId();
        name = defaultModule.getName();
        return true;
    }

    public boolean fromDefaultData(DefaultData defaultData){
        Id = defaultData.getDefaultmoduleId();
        name = defaultData.getDataName();
        return true;
    }

    public CalculateShowModuleDTO() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CalculateShowCalDTO> getLists() {
        return lists;
    }

    public void setLists(List<CalculateShowCalDTO> lists) {
        this.lists = lists;
    }
}
