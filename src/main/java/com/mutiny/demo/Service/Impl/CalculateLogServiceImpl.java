package com.mutiny.demo.Service.Impl;

import com.mutiny.demo.Service.CalculateLogService;
import com.mutiny.demo.dao.DefaultDataMapper;
import com.mutiny.demo.dao.DefaultModuleMapper;
import com.mutiny.demo.dao.LogCalculateMapper;
import com.mutiny.demo.dao.ModuleMapper;
import com.mutiny.demo.dto.CalculateShowCalDTO;
import com.mutiny.demo.dto.CalculateShowModuleDTO;
import com.mutiny.demo.pojo.LogCalculate;
import com.mutiny.demo.pojo.LogCalculateExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: Anonsmd
 * @Date: 2020/2/10 12:22
 */
@Service
public class CalculateLogServiceImpl implements CalculateLogService {
    @Autowired
    private LogCalculateMapper logCalculateMapper;
    @Autowired
    private ModuleMapper moduleMapper;
    @Autowired
    private DefaultDataMapper defaultDataMapper;
    @Autowired
    private DefaultModuleMapper defaultModuleMapper;
    @Override
    public CalculateShowModuleDTO showansw(int day, int moduleId, String type, boolean isDefault) throws Exception {
        LogCalculateExample logCalculateExample = new LogCalculateExample();
        logCalculateExample.createCriteria().andTypeEqualTo(type).andIsDefaultEqualTo(isDefault).andModuleidEqualTo(moduleId);
        List<LogCalculate> logCalculates = logCalculateMapper.selectByExample(logCalculateExample);
        SimpleDateFormat sdf1 =new SimpleDateFormat("yyyy-MM-dd" );
        Date d1= new Date();
        Map<String,Integer> map = new HashMap<>();
        for (int i=0;i<day;i++){
            String tem = sdf1.format(d1);
            System.out.println(" "+tem);
            map.put(tem,0);
            d1 = new Date(d1.getTime()-86400000);
        }
        for (LogCalculate record : logCalculates){
            Date date = record.getOperationTime();
            String key = sdf1.format(date);
            int values = map.get(key)+1;
            if(map.containsKey(key)){
                map.put(key,values);
            }

        }
        Set<String> entryset = map.keySet();
        List<CalculateShowCalDTO> calculateShowCalDTOS = new ArrayList<>();
        for (String record:entryset){
            CalculateShowCalDTO calculateShowCalDTO = new CalculateShowCalDTO();
            calculateShowCalDTO.setData(record);
            calculateShowCalDTO.setNum(map.get(record));
            calculateShowCalDTOS.add(calculateShowCalDTO);
        }
        CalculateShowModuleDTO calculateShowModuleDTO = new CalculateShowModuleDTO();
        calculateShowModuleDTO.setLists(calculateShowCalDTOS);
        if(isDefault){
            calculateShowModuleDTO.fromDefaultData(defaultDataMapper.selectByPrimaryKey(moduleId));
        }
        else{
            calculateShowModuleDTO.fromModule(moduleMapper.selectByPrimaryKey(moduleId));
        }
        return calculateShowModuleDTO;
    }

    @Override
    public List<CalculateShowModuleDTO> showDefaultAnswAll(int day, String answ) throws Exception {
        LogCalculateExample logCalculateExample = new LogCalculateExample();
        logCalculateExample.createCriteria().andTypeEqualTo("answ").andIsDefaultEqualTo(true);
        List<LogCalculate> logCalculates = logCalculateMapper.selectByExample(logCalculateExample);
        SimpleDateFormat sdf1 =new SimpleDateFormat("yyyy-MM-dd" );
        Date d1= new Date();
        Map<String,Integer> daymap = new HashMap<>();
        for (int i=0;i<day;i++){
            String tem = sdf1.format(d1);
            System.out.println(" "+tem);
            daymap.put(tem,0);
            d1 = new Date(d1.getTime()-86400000);
        }
        d1 = new Date(d1.getTime()+86400000);
        Map<Integer,Map<String,Integer>> map = new HashMap<>();
        for (LogCalculate record : logCalculates){
            if (!map.containsKey(record.getModuleid()) && record.getOperationTime().after(d1)){
                map.put(record.getModuleid(),new HashMap<>(daymap));
            }
        }
        for (LogCalculate record : logCalculates){
            Date date = record.getOperationTime();
            String key = sdf1.format(date);
            if(map.containsKey(record.getModuleid()) && map.get(record.getModuleid()).containsKey(key)){
                int values = map.get(record.getModuleid()).get(key)+1;
                map.get(record.getModuleid()).put(key,values);
            }
        }
        Set<Integer> entryset = map.keySet();
        List<CalculateShowModuleDTO> answList = new ArrayList<>();
        for (Integer id:entryset){
            List<CalculateShowCalDTO> calculateShowCalDTOS = new ArrayList<>();
            Set<String> entryset2 = map.get(id).keySet();
            for (String record:entryset2){
                CalculateShowCalDTO calculateShowCalDTO = new CalculateShowCalDTO();
                calculateShowCalDTO.setData(record);
                calculateShowCalDTO.setNum(map.get(id).get(record));
                calculateShowCalDTOS.add(calculateShowCalDTO);
            }
            CalculateShowModuleDTO calculateShowModuleDTO = new CalculateShowModuleDTO();
            calculateShowModuleDTO.setLists(calculateShowCalDTOS);
            calculateShowModuleDTO.fromDefaultData(defaultDataMapper.selectByPrimaryKey(id));
            answList.add(calculateShowModuleDTO);
        }
        return answList;
    }
}
