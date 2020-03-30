package com.mutiny.demo.Service;

import com.mutiny.demo.FHE.Key;
import com.mutiny.demo.dto.*;
import com.mutiny.demo.pojo.DefaultData;
import com.mutiny.demo.pojo.DefaultModule;

import java.util.List;

/**
 * @Author: Anonsmd
 * @Date: 2020/2/1 13:30
 */
public interface ModuleService {
    String addefaultData(ModuleDefaultDataAddDTO moduleDefaultDataAddDTO,String username) throws Exception;
    List<DefaultData> defaultModuleData(int defaultID) throws Exception;
    List<DefaultModule> defaultModule() throws Exception;
    DefaultModule defaultModule(int DefaultId) throws Exception;
    String shouAnsw(int ModuleID) throws Exception;
    String delete(int ModuleID,String username) throws Exception;
    String deleteDefaultData(int defaultDataId,String username) throws Exception;
    String deleteDefaultModule(int DefauleModuleID,String username) throws Exception;
    String addDefualt(DefaultModuleAddDTO defaultModuleAddDTO) throws Exception;
    KeyDTO returnKey(int moduleId, boolean isDefaultId) throws Exception;
    List<ShowDefaultDataDTO> showDefaultData(String getUsername) throws Exception;
}
