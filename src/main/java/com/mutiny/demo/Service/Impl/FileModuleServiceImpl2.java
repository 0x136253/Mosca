package com.mutiny.demo.Service.Impl;

import com.mutiny.demo.Service.FileModuleService;
import com.mutiny.demo.dao.*;
import com.mutiny.demo.dto.FileModuleConDTO;
import com.mutiny.demo.dto.FileModuleTempShowDTO;
import com.mutiny.demo.dto.FileModuleTempShowListDTO;
import com.mutiny.demo.dto.FileModuleUploaderDTO;
import com.mutiny.demo.pojo.*;
import com.mutiny.demo.util.CSVUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Anonsmd
 * @Date: 2020/1/30 12:19
 * problem:
 * 同一人是否可同时上传两份文件 否
 * 同一人同一个模型是否可以上传两份文件 可
 * 上传后是否可以删除 可
 *
 */
//@Service
public class FileModuleServiceImpl2{
    private Map<String, FileModuleConDTO> fileModuleConDTOMap = new HashMap<>();

    @Value("${file.datapath}")
    private String datapath;
    @Autowired
    private FileModuleTempMapper fileModuleTempMapper;
    @Autowired
    private ModuleMapper moduleMapper;
    @Autowired
    private FileModuleMapper fileModuleMapper;
    @Autowired
    private DefaultDataMapper defaultDataMapper;
    @Autowired
    private DefaultModuleMapper defaultModuleMapper;

    public int check(int moduleID,boolean Isdefault){
        int totleID;
        if (Isdefault){
            int defaultModuleID = defaultDataMapper.selectByPrimaryKey(moduleID).getDefaultId();
            totleID = defaultModuleMapper.selectByPrimaryKey(defaultModuleID).getParamNumber();
        }
        else {
            totleID=moduleMapper.selectByPrimaryKey(moduleID).getParamNumber();
        }
        int num =0;
        try {
            if (Isdefault){
                num = fileModuleTempMapper.GetSumByDefaultModuleId(moduleID);
            }
            else {
                num=fileModuleTempMapper.GetSumByModuleId(moduleID);
            }
        }
        catch (Exception e){
            System.out.println("Not Exist!!");
        }
        if (num < totleID){
            //small
            return  0;
        }
        else if (num == totleID){
            // equle
            return 1;
        }
        else{
            // big
            return 2;
        }
    }

    public void Synthesis(int  moduleID,boolean IsDefault){ //true for default
        FileModuleTempExample fileModuleTempExample =new FileModuleTempExample();
        if (IsDefault){
            fileModuleTempExample.createCriteria().andDefaultidEqualTo(moduleID);
        }
        else {
            fileModuleTempExample.createCriteria().andModuleIdEqualTo(moduleID);
        }
        List<FileModuleTemp> fileModuleTemps = fileModuleTempMapper.selectByExample(fileModuleTempExample);
        FileModule fileModule = new FileModule();
        fileModule.setEncodeAlgorithm(fileModuleTemps.get(0).getEncodeAlgorithm());
        if (!IsDefault){
            fileModule.setModuleId(fileModuleTemps.get(0).getModuleId());
        }
        if (fileModuleTemps.get(0).getKeyfileid()!=null){
            fileModule.setKeyfileid(fileModuleTemps.get(0).getKeyfileid());
        }
        Map<String,List<String>> map = new HashMap<>();
        for (FileModuleTemp record:fileModuleTemps){
            Map<String,List<String>> temp = CSVUtils.readCsvFile(record.getFileUrl(),-1);
            for (Map.Entry<String, List<String>> entry : temp.entrySet()) {
                map.put(entry.getKey(),entry.getValue());
//                System.out.println(entry.getKey() + ":" + entry.getValue());
            }
        }
        String url=CSVUtils.writeCsvFile(datapath+"ModuleFile",map);
        fileModule.setFileUrl(url);
        fileModuleMapper.insertSelective(fileModule);
        if (IsDefault){
            FileModuleExample fileModuleExample = new FileModuleExample();
            fileModuleExample.createCriteria().andFileUrlEqualTo(url);
            FileModule fileModule1 =fileModuleMapper.selectByExample(fileModuleExample).get(0);
            DefaultData defaultData = new DefaultData();
            defaultData.setDefaultmoduleId(moduleID);
            defaultData.setFileId(fileModule1.getFileId());
            defaultDataMapper.updateByPrimaryKeySelective(defaultData);
        }
    }

    public void DTOFile(FileModuleUploaderDTO fileModuleUploaderDTO,String username){
        String url = CSVUtils.writeCsvFile(datapath+"TempFile//"+username,fileModuleUploaderDTO.getData());
        FileModuleTemp fileModuleTemp = new FileModuleTemp();
        fileModuleTemp.setEncodeAlgorithm(fileModuleUploaderDTO.getEncodeAlgorithm());
        fileModuleTemp.setFileUrl(url);
        fileModuleTemp.setIsDefault(fileModuleUploaderDTO.isIs_default());
        if (fileModuleTemp.getIsDefault()){
            fileModuleTemp.setDefaultid(fileModuleUploaderDTO.getModuleID());
        }
        else {
            fileModuleTemp.setModuleId(fileModuleUploaderDTO.getModuleID());
        }
        fileModuleTemp.setNowparam(fileModuleUploaderDTO.getData().size());
        if (fileModuleUploaderDTO.getKeyFileId()!=0) {
            fileModuleTemp.setKeyfileid(fileModuleUploaderDTO.getKeyFileId());
        }
        fileModuleTemp.setUploader(username);
        if (fileModuleUploaderDTO.isIs_default()){
            int defaultModuleID = defaultDataMapper.selectByPrimaryKey(fileModuleUploaderDTO.getModuleID()).getDefaultId();
            fileModuleTemp.setTotleparam(defaultModuleMapper.selectByPrimaryKey(defaultModuleID).getParamNumber());
        }
        else {
            Module module = moduleMapper.selectByPrimaryKey(fileModuleTemp.getModuleId());
            fileModuleTemp.setTotleparam(module.getParamNumber());
        }
        fileModuleTempMapper.insertSelective(fileModuleTemp);
        if (check(fileModuleUploaderDTO.getModuleID(),fileModuleUploaderDTO.isIs_default()) == 1){
            Synthesis(fileModuleUploaderDTO.getModuleID(),fileModuleUploaderDTO.isIs_default());
        }
    }

    public String uploadData(FileModuleUploaderDTO fileModuleUploaderDTO, String username)  throws Exception{
        if (fileModuleConDTOMap.containsKey(username)){
            FileModuleConDTO fileModuleConDTO=fileModuleConDTOMap.get(username);
            fileModuleConDTO.getFileModuleUploaderDTOList().add(fileModuleUploaderDTO);
            fileModuleConDTO.setNow(fileModuleConDTO.getNow()+1);
            if (fileModuleConDTO.getNow()==fileModuleConDTO.getTotle()){
                FileModuleUploaderDTO fileModuleUploaderDTO1=fileModuleConDTO.Synthesis();
                DTOFile(fileModuleUploaderDTO1,username);
                fileModuleConDTOMap.remove(username);
            }
            else if (fileModuleConDTO.getNow() > fileModuleConDTO.getTotle()){
                throw new Exception("出现异常，建议清空缓存");
            }
        }
        else {
            FileModuleConDTO fileModuleConDTO=new FileModuleConDTO();
            fileModuleConDTO.setNow(1);
            fileModuleConDTO.setTotle(fileModuleUploaderDTO.getTotal());
            List<FileModuleUploaderDTO> fileModuleUploaderDTOS=new ArrayList<>();
            fileModuleUploaderDTOS.add(fileModuleUploaderDTO);
            fileModuleConDTO.setFileModuleUploaderDTOList(fileModuleUploaderDTOS);
            fileModuleConDTOMap.put(username,fileModuleConDTO);
            if (fileModuleConDTO.getNow()==fileModuleConDTO.getTotle()){
                FileModuleUploaderDTO fileModuleUploaderDTO2=fileModuleConDTO.Synthesis();
                DTOFile(fileModuleUploaderDTO2,username);
                fileModuleConDTOMap.remove(username);
            }
            else if (fileModuleConDTO.getNow() > fileModuleConDTO.getTotle()){
                throw new Exception("出现异常，建议清空缓存");
            }
        }
        if (check(fileModuleUploaderDTO.getModuleID(),fileModuleUploaderDTO.isIs_default()) == 2){
//            return "文件上传异常,参数数量过大，建议复查！！！";
            throw new Exception("文件上传异常,参数数量过大，建议复查！！！");
        }
        return "拼接成功";
    }

    public String flush(String username)  throws Exception{
        fileModuleConDTOMap.remove(username);
        return "清空成功";
    }

    public String delete(String username, int FileID)  throws Exception{
        FileModuleTemp fileModuleTemp = fileModuleTempMapper.selectByPrimaryKey(FileID);
        if (!fileModuleTemp.getUploader().equals(username)){
//            return "Your Hava No Permission!!";
            throw new Exception("Your Hava No Permission!!");
        }
        fileModuleTempMapper.deleteByPrimaryKey(FileID);
        File file = new File(fileModuleTemp.getFileUrl());
        if (!file.exists()){
//            return "Not Exist!!";
            throw new Exception("Not Exist!!");
        }
        if (file.isFile()) {
            file.delete();
        }
        return "删除成功";
    }

    public FileModuleTempShowListDTO show(int moduleid,boolean IsDefualt)  throws Exception{
        FileModuleTempShowListDTO answ = new FileModuleTempShowListDTO();
        FileModuleTempExample fileModuleTempExample = new FileModuleTempExample();
        if (IsDefualt){
            fileModuleTempExample.createCriteria().andDefaultidEqualTo(moduleid);
        }
        else {
            fileModuleTempExample.createCriteria().andModuleIdEqualTo(moduleid);
        }
        List<FileModuleTemp> fileModuleTemps = fileModuleTempMapper.selectByExample(fileModuleTempExample);
        for (FileModuleTemp index:fileModuleTemps){
            FileModuleTempShowDTO fileModuleTempShowDTO = new FileModuleTempShowDTO();
            fileModuleTempShowDTO.setUsername(index.getUploader());
            fileModuleTempShowDTO.setFileID(index.getFileId());
            fileModuleTempShowDTO.setData(CSVUtils.readCsvFile(index.getFileUrl(),10));
            answ.add(fileModuleTempShowDTO);
        }
        return answ;
    }

    public String uploadData(MultipartFile file, int moduleId, boolean isDefault, String username) throws Exception {
        return null;
    }
}
