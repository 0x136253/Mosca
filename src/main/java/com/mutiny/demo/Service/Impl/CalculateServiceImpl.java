package com.mutiny.demo.Service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.mutiny.demo.Service.CalculateService;
import com.mutiny.demo.component.MessageRabbitMqSender;
import com.mutiny.demo.dao.*;
import com.mutiny.demo.pojo.*;
import com.mutiny.demo.util.AddressUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: Anonsmd
 * @Date: 2020/2/10 12:21
 */
@Service
public class CalculateServiceImpl implements CalculateService {
    @Autowired
    private ModuleMapper moduleMapper;
    @Autowired
    private FileModuleMapper fileModuleMapper;
    @Autowired
    private MessageRabbitMqSender messageRabbitMqSender;
    @Autowired
    private KeyFileMapper keyFileMapper;
    @Autowired
    private DefaultDataMapper defaultDataMapper;
    @Autowired
    private DefaultModuleMapper defaultModuleMapper;
    @Autowired
    private ProjectUserMapper projectUserMapper;
    @Autowired
    private ModuleUserMapper moduleUserMapper;
    @Override
    public String calModule(int moduleID,String username) throws Exception {
        Module module = moduleMapper.selectByPrimaryKey(moduleID);
        FileModuleExample fileModuleExample = new FileModuleExample();
        fileModuleExample.createCriteria().andModuleIdEqualTo(moduleID);
        FileModule fileModule = fileModuleMapper.selectByExample(fileModuleExample).get(0);
        KeyFile keyFile = keyFileMapper.selectByPrimaryKey(module.getKeyfileid());
        if (!CheckPermiss(username,false,moduleID)){
            throw new Exception("Your hava no permission");
        }
        if (module == null || fileModule ==null){
            throw new Exception("Module is not ready for calculate");
        }
        if (!module.getIsUserful()){
            throw new Exception("Module is already delete");
        }
        if (module.getIsCalculate()){
            throw new Exception("Module is already Calculate");
        }
        if (module.getIsDefault()){
            throw new Exception("This is a Default Module");
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("isDefault",false);
        jsonObject.put("moduleId",module.getModuleId());
        jsonObject.put("keyFileId",module.getKeyfileid());
        jsonObject.put("keyFileUrl",keyFile.getFileurl());
//        jsonObject.put("keyFileUrl",keyFile.getFileurl());
        jsonObject.put("fileId",fileModule.getFileId());
        jsonObject.put("fileUrl",fileModule.getFileUrl());
        jsonObject.put("timestamp", System.currentTimeMillis());
        jsonObject.put("applicant",username);
//        jsonObject.put("sendIp", AddressUtils.getV4IP());
        jsonObject.put("sendIp", AddressUtils.getInnetIp());
        if(AddressUtils.getV4IP().equals("47.103.0.173")){
            jsonObject.put("sendIp", AddressUtils.getV4IP());
        }
//        MessageSender messageSender = new MessageSender();
        messageRabbitMqSender.send(jsonObject,"MoscaDirectExchange","MoscaDirectRouting");
        return null;
    }

    @Override
    public String calDefaultModule(int defaultDataId,String username) throws Exception{
        DefaultData defaultData = defaultDataMapper.selectByPrimaryKey(defaultDataId);
        DefaultModule defaultModule = defaultModuleMapper.selectByPrimaryKey(defaultData.getDefaultId());
        FileModule fileModule = fileModuleMapper.selectByPrimaryKey(defaultData.getFileId());
        KeyFile keyFile = keyFileMapper.selectByPrimaryKey(defaultData.getKeyfileid());
        if (!CheckPermiss(username,true,defaultDataId)){
            throw new Exception("Your hava no permission");
        }
        if (defaultData == null || defaultModule==null || fileModule ==null){
            throw new Exception("Module is not ready for calculate");
        }
        if (!defaultData.getIsUserful()){
            throw new Exception("Module is already delete");
        }
        if (defaultData.getIsCalculate()){
            throw new Exception("Module is already Calculate");
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("isDefault",true);
        jsonObject.put("moduleId",defaultData.getDefaultmoduleId());
        jsonObject.put("keyFileId",defaultData.getKeyfileid());
        jsonObject.put("keyFileUrl",keyFile.getFileurl());
//        jsonObject.put("keyFileUrl",keyFile.getFileurl());
        jsonObject.put("fileId",fileModule.getFileId());
        jsonObject.put("fileUrl",fileModule.getFileUrl());
        jsonObject.put("timestamp", System.currentTimeMillis());
        jsonObject.put("applicant",username);
//        jsonObject.put("sendIp", AddressUtils.getV4IP());
        jsonObject.put("sendIp", AddressUtils.getInnetIp());
        if(AddressUtils.getV4IP().equals("47.103.0.173")){
            jsonObject.put("sendIp", AddressUtils.getV4IP());
        }
//        MessageSender messageSender = new MessageSender();
        messageRabbitMqSender.send(jsonObject,"MoscaDirectExchange","MoscaDirectRouting");
        defaultData.setCaltime(new Date());
        defaultDataMapper.updateByPrimaryKeySelective(defaultData);
        return null;
    }


    private boolean CheckPermiss(String username, boolean isDefault,int Id) {
        String uploader;
        if (isDefault){
            uploader = moduleUserMapper.findCreater(Id);
        }
        else {
            uploader = projectUserMapper.findCreater(Id);
        }
        if (!uploader.equals(username)){
            return false;
        }
        return true;
    }
}
