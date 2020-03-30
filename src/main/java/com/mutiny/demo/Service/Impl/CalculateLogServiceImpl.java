package com.mutiny.demo.Service.Impl;

import com.mutiny.demo.Service.CalculateLogService;
import com.mutiny.demo.dao.LogCalculateMapper;
import com.mutiny.demo.dto.CalculateShowCalDTO;
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

    @Override
    public List<CalculateShowCalDTO> showansw(int day) throws Exception {
        LogCalculateExample logCalculateExample = new LogCalculateExample();
        logCalculateExample.createCriteria().andTypeEqualTo("answ");
        List<LogCalculate> logCalculates = logCalculateMapper.selectByExample(logCalculateExample);
        SimpleDateFormat sdf1 =new SimpleDateFormat("yyyy-MM-dd" );
        Date d1= new Date();
        Map<String,Integer> map = new HashMap<>();
        for (int i=0;i<day;i++){
            Date date = new Date(d1.getTime()-86400000*i);
            String tem = sdf1.format(date);
            map.put(tem,0);
        }
        for (LogCalculate record : logCalculates){
            Date date = record.getOperationTime();
            String key = sdf1.format(date);
            int values = map.get(key)+1;
            map.put(key,values);
        }
        Set<String> entryset = map.keySet();
        List<CalculateShowCalDTO> calculateShowCalDTOS = new ArrayList<>();
        for (String record:entryset){
            CalculateShowCalDTO calculateShowCalDTO = new CalculateShowCalDTO();
            calculateShowCalDTO.setData(record);
            calculateShowCalDTO.setNum(map.get(record));
            calculateShowCalDTOS.add(calculateShowCalDTO);
        }
        return calculateShowCalDTOS;
    }
}
