package com.mutiny.demo.Service;

import com.mutiny.demo.dto.LogModuleDTO;
import com.mutiny.demo.dto.ModuleDataInfoDTO;
import com.mutiny.demo.dto.ModuleSInfo;

import java.util.List;

public interface ManagerService {
    List<LogModuleDTO> getModuleLog() throws Exception;

    ModuleSInfo getModuleLogInfo(int moduleId) throws Exception;
    List<ModuleDataInfoDTO> getModuleDataInfo() throws Exception;
}
