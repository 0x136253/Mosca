package com.mutiny.demo.Service.Impl;

import cn.hutool.core.text.csv.CsvUtil;
import com.mutiny.demo.Service.FileModuleService;
import com.mutiny.demo.api.CommonResult;
import com.mutiny.demo.component.FilePath;
import com.mutiny.demo.dao.*;
import com.mutiny.demo.dto.*;
import com.mutiny.demo.message.MessageSender;
import com.mutiny.demo.pojo.*;
import com.mutiny.demo.util.CSVUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: Anonsmd
 * @Date: 2020/3/16 12:22
 */
@Service
public class FileModuleServiceImpl implements FileModuleService {
    @Autowired
    private FilePath filePaths;
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
    @Autowired
    private ModuleUserMapper moduleUserMapper;
    @Autowired
    private ProjectUserMapper projectUserMapper;
    @Autowired
    private MessageSender messageSender;
    @Autowired
    private ProjectMapper projectMapper;

    @Override
    public String delete(String username, int FileID) throws Exception {
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

    @Override
    public FileModuleTempShowListDTO show(int moduleid, boolean IsDefualt) throws Exception {
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
        if (fileModuleTemps.size()==0){
            if (IsDefualt){
                DefaultData defaultData = defaultDataMapper.selectByPrimaryKey(moduleid);
                FileModule fileModule = fileModuleMapper.selectByPrimaryKey(defaultData.getFileId());
                FileModuleTempShowDTO fileModuleTempShowDTO = new FileModuleTempShowDTO();
                fileModuleTempShowDTO.setUsername("None");
                fileModuleTempShowDTO.setFileID(fileModule.getFileId());
                fileModuleTempShowDTO.setData(CSVUtils.readCsvFile(fileModule.getFileUrl(),10));
                answ.add(fileModuleTempShowDTO);
            }else {
                FileModuleExample fileModuleExample = new FileModuleExample();
                fileModuleExample.createCriteria().andModuleIdEqualTo(moduleid);
                FileModule fileModule = fileModuleMapper.selectByExample(fileModuleExample).get(0);
                FileModuleTempShowDTO fileModuleTempShowDTO = new FileModuleTempShowDTO();
                fileModuleTempShowDTO.setUsername("None");
                fileModuleTempShowDTO.setFileID(fileModule.getFileId());
                fileModuleTempShowDTO.setData(CSVUtils.readCsvFile(fileModule.getFileUrl(),10));
                answ.add(fileModuleTempShowDTO);
            }
        }
        return answ;
    }

    /**
     * 读取文件，暂存至temp
     * @param file
     * @param moduleId
     * @param isDefault
     * @param username
     * @return
     * @throws Exception
     */
    @Override
    public String uploadData(MultipartFile file, int moduleId, boolean isDefault, String username) throws Exception {
        if (!CheckPermiss(username,isDefault,moduleId)){
            throw new Exception("Your have No Permission");
        }
        if (file.isEmpty()) {
            throw new Exception("File Is Empty");
        }
        // 获取文件名
        String fileName = file.getOriginalFilename();
        System.out.println(fileName);
        // 获取文件的后缀名
        String suffixName = ".csv";
        // 设置文件存储路径
        fileName = UUID.randomUUID() + suffixName;
        String filePath = "TempFile//"+username+"//";
        String path = filePaths.returnBasicPath()+filePath + fileName;
        File dest = new File(path);
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        file.transferTo(dest);
        FileModuleTemp fileModuleTemp = new FileModuleTemp();
        fileModuleTemp.setFileName(username+System.currentTimeMillis());
        fileModuleTemp.setUploader(username);
        if (isDefault){
            fileModuleTemp.setDefaultid(moduleId);
            DefaultData defaultData = defaultDataMapper.selectByPrimaryKey(moduleId);
            fileModuleTemp.setTotleparam(defaultModuleMapper.selectByPrimaryKey(defaultData.getDefaultId()).getParamNumber());
        }
        else {
            fileModuleTemp.setModuleId(moduleId);
            Module module = moduleMapper.selectByPrimaryKey(fileModuleTemp.getModuleId());
            fileModuleTemp.setTotleparam(module.getParamNumber());
        }
        fileModuleTemp.setIsDefault(isDefault);
        fileModuleTemp.setFileUrl(path);
        fileModuleTemp.setEncodeAlgorithm("None");
        Map<String, List<String>> map =  CSVUtils.readCsvFile(path,3);
        Set<String> keySet = map.keySet();
        Iterator<String> keySetIte = keySet.iterator();
        int countKeySize =0 ;
        while(keySetIte.hasNext()){
            if (keySetIte.next().length()==1){
                countKeySize++;
            }
        }
        fileModuleTemp.setNowparam(countKeySize);
        fileModuleTempMapper.insertSelective(fileModuleTemp);
        if (checkIsFull(moduleId,isDefault)){
            Synthesis(moduleId,isDefault);
            if (!isDefault){
                Module module = moduleMapper.selectByPrimaryKey(moduleId);
                module.setUptime(new Date());
                module.setStatus(1);
                String projectUser = projectUserMapper.findCreater(module.getProjectId());
                String text = String.format("您的ID为%d项目中的模型(ID=%d)参数已齐全，现在可前往平台开始计算",module.getProjectId(),moduleId);
                messageSender.sendPrivate("模型情况通报",text,projectUser);
            }
            else {
                DefaultData defaultData = new DefaultData();
                defaultData.setDefaultmoduleId(moduleId);
                defaultData.setUptime(new Date());
                defaultData.setStatus(1);
                defaultDataMapper.updateByPrimaryKeySelective(defaultData);
            }
            return "All Parma Is Uploaded";
        }
        else {
            if (!isDefault){
                Module module = moduleMapper.selectByPrimaryKey(moduleId);
                String projectUser = projectUserMapper.findCreater(module.getProjectId());
                String text = String.format("您的ID为%d项目中的模型(ID=%d)参数已更新,新增加的参数有"+Arrays.toString(keySet.toArray()),module.getProjectId(),moduleId);
                messageSender.sendPrivate("模型情况通报",text,projectUser);
            }
            return "You Have Upload "+countKeySize+" Parmas:"+Arrays.toString(keySet.toArray());
        }
    }

    /**
     * change tu functon
     * @param username
     * @param moduleId
     * @param isDefault
     * @return
     * @throws Exception
     */
    @Override
    public List<ModuleUploadInfoDTO> showUploadNeed(String username, int moduleId, boolean isDefault) throws Exception {
        String func;
        Map<String,String> map = new HashMap<>();
        if (isDefault){
            DefaultData defaultData = defaultDataMapper.selectByPrimaryKey(moduleId);
            DefaultModule defaultModule = defaultModuleMapper.selectByPrimaryKey(defaultData.getDefaultId());
            func = defaultModule.getFunction();
//            func = defaultModule.getTfunction();
            map = getCharDes(defaultModule.getDescription());
        }
        else {
            Module module = moduleMapper.selectByPrimaryKey(moduleId);
//            func = module.getTfunction();
            func = module.getFunction();
            map = getCharDes(module.getDescription());
        }
        Iterator<String>  chars = getIdentifiy(func).iterator();
        List<ModuleUploadInfoDTO> answ = new ArrayList<>();
        while (chars.hasNext()){
            String tmp = chars.next();
            String dess = null;
            if (map.containsKey(tmp)){
                dess=map.get(tmp);
            }
            ModuleUploadInfoDTO temp = new ModuleUploadInfoDTO(tmp,dess,"整数",16);
            answ.add(temp);
        }
        return answ;
    }

    @Override
    public List<DefaultDataStatusDTO> showDefaultDataStatus(String username) throws Exception {
        ModuleUserExample moduleUserExample = new ModuleUserExample();
        moduleUserExample.createCriteria().andTypeEqualTo("uploader").andUserIdEqualTo(username);
        List<ModuleUser> moduleUsers = moduleUserMapper.selectByExample(moduleUserExample);
        List<DefaultDataStatusDTO> answ = new ArrayList<>();
        for (ModuleUser record:moduleUsers){
            DefaultData defaultData = defaultDataMapper.selectByPrimaryKey(record.getModuleId());
            DefaultModule defaultModule = defaultModuleMapper.selectByPrimaryKey(defaultData.getDefaultId());
            if (!defaultData.getIsUserful() || !defaultModule.getIsuserful()){
                continue;
            }
            DefaultDataStatusDTO defaultDataStatusDTO = new DefaultDataStatusDTO(defaultModule.getName(),defaultData.getDataName(),defaultData.getDefaultmoduleId(),defaultData.getDefaultId());
            if (defaultData.getCreatetime()!=null){
                defaultDataStatusDTO.setCreateTime(defaultData.getCreatetime());
            }
            if (defaultData.getUptime()!=null){
                defaultDataStatusDTO.setUpTime(defaultData.getUptime());
            }
            if (defaultData.getStatus()!=null){
                defaultDataStatusDTO.setStatus(defaultData.getStatus());
            }
            answ.add(defaultDataStatusDTO);
        }
        return answ;
    }

    @Override
    public List<ModuleStatusDTO> showModuleStatus(String getUsername) throws Exception {
        ProjectUserExample projectUserExample = new ProjectUserExample();
        projectUserExample.createCriteria().andUserIdEqualTo(getUsername);
        List<ProjectUser> projectUserList = projectUserMapper.selectByExample(projectUserExample);
        List<ModuleStatusDTO> answ = new ArrayList<>();
        for (ProjectUser record:projectUserList){
            if (record.getType().equals("user")){
                continue;
            }
            Project project = projectMapper.selectByPrimaryKey(record.getProjectId());
            if (project.getIsDefault() || !project.getIsUserful()){
                continue;
            }
            ModuleExample moduleExample = new ModuleExample();
            moduleExample.clear();
            moduleExample.createCriteria().andProjectIdEqualTo(project.getProjectId()).andIsUserfulEqualTo(true);
            List<Module> moduleList = moduleMapper.selectByExample(moduleExample);
            ModuleStatusDTO moduleStatusDTO = new ModuleStatusDTO();
            if (moduleList.size()==0){
                continue;
            }
            for (Module res:moduleList){
                moduleStatusDTO.setModuleId(res.getModuleId());
                moduleStatusDTO.setModuleName(res.getModuleName());
                moduleStatusDTO.setProjectId(record.getProjectId());
                moduleStatusDTO.setProjectName(project.getProjectName());
                if (res.getCreatetime()!=null) {
                    moduleStatusDTO.setCreateTime(res.getCreatetime());
                }
                if (res.getUptime()!=null) {
                    moduleStatusDTO.setUpTime(res.getUptime());
                }
                if (res.getStatus()!=null) {
                    moduleStatusDTO.setStatus(res.getStatus());
                }
            }
            answ.add(moduleStatusDTO);

        }
        return answ;
    }

    private Map<String,String> getCharDes(String Desc){
        Map<String,String> answ = new HashMap<>();
        String[] strs = Desc.split(" ");
        for (String record:strs){
            String[] temps = record.split(":");
            if (temps.length<2){
                continue;
            }
            answ.put(temps[0],temps[1]);
        }
        return answ;
    }

    private Set<String> getIdentifiy(String function){
        String pattern = "[a-zA-Z]+";
        Pattern r = Pattern.compile(pattern);
        Matcher matcher = r.matcher(function);
        Set<String> answ = new HashSet<>();
        while (matcher.find()){
            String tem = matcher.group();
            if (tem.equals("max") || tem.equals("MAX") ||tem.equals("min") ||tem.equals("MIN")){
                continue;
            }
            answ.add(tem);
        }
        return answ;
    }

    public void Synthesis(int moduleID,boolean IsDefault){
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
        String url=CSVUtils.writeCsvFile(filePaths.returnBasicPath()+"ModuleFile",map);
        for (FileModuleTemp record:fileModuleTemps){
           fileModuleTempMapper.deleteByPrimaryKey(record.getFileId());
        }
        if (!IsDefault){
            FileModuleExample fileModuleExample = new FileModuleExample();
            fileModuleExample.createCriteria().andModuleIdEqualTo(moduleID);
            List<FileModule> fileModuleList = fileModuleMapper.selectByExample(fileModuleExample);
            if (fileModuleList.size()>=1){
                FileModule temp = fileModuleList.get(0);
                fileModule.setFileUrl(url);
                fileModule.setFileId(temp.getFileId());
                fileModuleMapper.updateByPrimaryKey(fileModule);
            }else{
                fileModule.setFileUrl(url);
                fileModuleMapper.insertSelective(fileModule);
            }
        }
        else {
            fileModule.setFileUrl(url);
            fileModuleMapper.insertSelective(fileModule);
        }
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

    private boolean checkIsFull(int moduleId, boolean isDefault) {
        int totleID;
        if (isDefault){
            int defaultModuleID = defaultDataMapper.selectByPrimaryKey(moduleId).getDefaultId();
            totleID = defaultModuleMapper.selectByPrimaryKey(defaultModuleID).getParamNumber();
        }
        else {
            totleID=moduleMapper.selectByPrimaryKey(moduleId).getParamNumber();
        }
        int num =0;
        try {
            if (isDefault){
                num = fileModuleTempMapper.GetSumByDefaultModuleId(moduleId);
            }
            else {
                num=fileModuleTempMapper.GetSumByModuleId(moduleId);
            }
        }
        catch (Exception e){
            System.out.println("Not Exist!!");
        }
        if (num < totleID){
            //small
            return  false;
        }
        else if (num == totleID){
            // equle
            return true;
        }
        else{
            // big
            return false;
        }
//        return false;
    }

    private boolean CheckPermiss(String username, boolean isDefault,int Id) {
        String uploader;
        if (isDefault){
            uploader = moduleUserMapper.findCreater(Id);
        }
        else {
            Module module = moduleMapper.selectByPrimaryKey(Id);
            uploader = projectUserMapper.findCreater(module.getProjectId());
        }
        System.out.println(uploader+"  "+username);
        if (!uploader.equals(username)){
            return false;
        }
        return true;
    }
}
