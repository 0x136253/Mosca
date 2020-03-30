package com.mutiny.demo.Service;

import com.mutiny.demo.dto.*;
import com.mutiny.demo.pojo.Project;
import io.swagger.models.auth.In;

/**
 * @Author: Anonsmd
 * @Date: 2020/1/16 18:19
 */
public interface ProjectService {
    ProjectInfoDTO ProjectCreate(Project project,String username) throws Exception;
    String ProjectAddModuleDescription(ProjectModulesDescriptionDTO projectModulesDescriptionDTO,String username) throws Exception;
    String changeCode(ProjectChangeCodeDTO projectChangeCodeDTO,String username) throws Exception;
    String addModule(ProjectModuleAddDTO projectModuleAddDTO,String username) throws Exception;
    String addDefaultModule(ProjectDefaultAddDTO projectDefaultAddDTO,String username) throws Exception;
    ProjectInfoDTO getInfo(int projectID) throws Exception;
    ProjectCompleteDTO getIsEnd(String username) throws Exception;
    ProjectListDTO getProject(String username, String type, Integer is_default, int currIndex, int pagesize) throws Exception;
    ProjectListDTO getProjectAll(String username, String type, Integer is_default) throws Exception;
    String delete(String username,int projectID) throws Exception;
    ModuleListDTO getModules(int projectID) throws Exception;
}
