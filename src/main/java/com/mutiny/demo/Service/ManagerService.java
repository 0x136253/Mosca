package com.mutiny.demo.Service;

import com.mutiny.demo.dto.*;

import java.util.List;
import java.util.Map;

public interface ManagerService {
    List<LogModuleDTO> getModuleLog() throws Exception;

    ModuleSInfo getModuleLogInfo(int moduleId) throws Exception;
    List<ModuleDataInfoDTO> getModuleDataInfo() throws Exception;

    List<CalculateShowCalOUTDTO> showApiData(int day) throws Exception;

    Map<String, Integer> Getkeyindicators() throws Exception;
}
