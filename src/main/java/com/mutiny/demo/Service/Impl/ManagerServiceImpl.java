package com.mutiny.demo.Service.Impl;

import com.mutiny.demo.Service.ManagerService;
import com.mutiny.demo.dao.*;
import com.mutiny.demo.dto.LogModuleDTO;
import com.mutiny.demo.dto.ModuleDataInfoDTO;
import com.mutiny.demo.dto.ModuleSInfo;
import com.mutiny.demo.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ManagerServiceImpl implements ManagerService {
    @Autowired
    private LogCalculateMapper logCalculateMapper;
    @Autowired
    private ModuleMapper moduleMapper;
    @Autowired
    private ProjectMapper projectMapper;
    @Autowired
    private ProjectUserMapper projectUserMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CompanyMapper companyMapper;
    @Override
    public List<LogModuleDTO> getModuleLog() throws Exception {
        return logCalculateMapper.getModuleManager();
    }

    @Override
    public ModuleSInfo getModuleLogInfo(int moduleId) throws Exception {
        return moduleMapper.GetModulesInfo(moduleId);
    }

    @Override
    public List<ModuleDataInfoDTO> getModuleDataInfo() throws Exception {
        return moduleMapper.GetModuleDataInfo();
    }

}
