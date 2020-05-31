package com.mutiny.demo.Service.Impl;

import com.mutiny.demo.Service.ManagerService;
import com.mutiny.demo.dao.*;
import com.mutiny.demo.dto.*;
import com.mutiny.demo.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ManagerServiceImpl implements ManagerService {
    @Autowired
    private LogCalculateMapper logCalculateMapper;
    @Autowired
    private LogMapper logMapper;
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
    @Autowired
    private DefaultDataMapper defaultDataMapper;
    @Autowired
    private ModuleUserMapper moduleUserMapper;
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

    @Override
    public List<CalculateShowCalOUTDTO> showApiData(int day) throws Exception {
       List<CalculateShowCalDTO> dateList = logMapper.showApiData();
       List<CalculateShowCalOUTDTO> answ = new ArrayList<>();
        SimpleDateFormat sdf1 =new SimpleDateFormat("yyyy-MM-dd" );
        Date d1= new Date();
        Map<String,Integer> map = new HashMap<>();
        for (int i=0;i<day;i++){
            String tem = sdf1.format(d1);
            System.out.println(" "+tem);
            map.put(tem,0);
            d1 = new Date(d1.getTime()-86400000);
        }
        for (CalculateShowCalDTO record : dateList){
            Date date = record.getData();
            int num = record.getNum();
            String key = sdf1.format(date);
            if(map.containsKey(key)){
                map.put(key,num);
            }
        }
        Set<String> entryset = map.keySet();
        for (String record:entryset){
            answ.add(new CalculateShowCalOUTDTO(record,map.get(record)));
        }
       return answ;
    }

    @Override
    public Map<String, Integer> Getkeyindicators() throws Exception {
        Map<String,Integer> map = new HashMap<>();
        DefaultDataExample defaultDataExample = new DefaultDataExample();
        defaultDataExample.createCriteria().andIsUserfulEqualTo(true);
        map.put("DefaultDataNum",defaultDataMapper.selectByExample(defaultDataExample).size());
        LogCalculateExample logCalculateExample = new LogCalculateExample();
        logCalculateExample.createCriteria().andIsDefaultEqualTo(true);
        map.put("DefaultDataUseNum",logCalculateMapper.selectByExample(logCalculateExample).size());
        ModuleUserExample moduleUserExample = new ModuleUserExample();
        moduleUserExample.createCriteria().andTypeEqualTo("User");
        map.put("PermissionNum",moduleUserMapper.selectByExample(moduleUserExample).size());
        return map;
    }

}
