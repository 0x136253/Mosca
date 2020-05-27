package com.mutiny.demo.dto;

public class ModuleSInfo {
    private String projectName;
    private String projectPurpose;
    private String projectDescription;
    private String ModuleDescriptino;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectPurpose() {
        return projectPurpose;
    }

    public void setProjectPurpose(String projectPurpose) {
        this.projectPurpose = projectPurpose;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public String getModuleDescriptino() {
        return ModuleDescriptino;
    }

    public void setModuleDescriptino(String moduleDescriptino) {
        ModuleDescriptino = moduleDescriptino;
    }

    public ModuleSInfo() {
    }

    public ModuleSInfo(String projectName, String projectPurpose, String projectDescription, String moduleDescriptino) {
        this.projectName = projectName;
        this.projectPurpose = projectPurpose;
        this.projectDescription = projectDescription;
        ModuleDescriptino = moduleDescriptino;
    }
}
