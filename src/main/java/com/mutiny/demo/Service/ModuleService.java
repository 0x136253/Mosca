package com.mutiny.demo.Service;

import com.mutiny.demo.FHE.Key;
import com.mutiny.demo.dto.*;
import com.mutiny.demo.pojo.DefaultData;
import com.mutiny.demo.pojo.DefaultModule;

import java.util.List;
import java.util.Map;

/**
 * @Author: Anonsmd
 * @Date: 2020/2/1 13:30
 */
public interface ModuleService {
    String addefaultData(ModuleDefaultDataAddDTO moduleDefaultDataAddDTO,String username) throws Exception;
    List<DefaultData> defaultModuleData(int defaultID) throws Exception;
    List<DefaultModuleDTO> defaultModule() throws Exception;
    DefaultModuleDTO defaultModule(int DefaultId) throws Exception;
    List<Map<String,String>> shouAnsw(int ModuleID,String username) throws Exception;
    String delete(int ModuleID,String username) throws Exception;
    String deleteDefaultData(int defaultDataId,String username) throws Exception;
    String deleteDefaultModule(int DefauleModuleID,String username) throws Exception;
    String addDefualt(DefaultModuleAddDTO defaultModuleAddDTO) throws Exception;
    KeyDTO returnKey(int moduleId, boolean isDefaultId) throws Exception;
    List<ShowDefaultDataDTO> showDefaultData(String getUsername) throws Exception;
    ModuleInfoDTO getModule(int moduleId) throws Exception;

    List<String> getfunctions(int moduleId) throws Exception;

    DefaultData getDefaultDataInfo(int defaultDateId) throws Exception;

    List<ModuleInfoDTO> getCompleteModuleInfo(String getUsername)throws Exception;

    List<String> getfunctionsDefault(int defaultId)throws Exception;

    Map<String, Object> shoucalInfo(int moduleID, String getUsername)throws Exception;

    Map<String, Object> shoucalInfoDefualt(int defaultDataId, String username)throws Exception;
}
