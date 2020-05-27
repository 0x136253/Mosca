package com.mutiny.demo.Service.Impl;

import com.mutiny.demo.FHE.Key;
import com.mutiny.demo.Service.ModuleService;
import com.mutiny.demo.component.KeyFileComponent;
import com.mutiny.demo.dao.*;
import com.mutiny.demo.dto.*;
import com.mutiny.demo.pojo.*;
import com.mutiny.demo.util.CSVUtils;
import com.mutiny.demo.util.FunctionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.Reader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: Anonsmd
 * @Date: 2020/2/1 13:31
 */
@Service
public class ModuleServiceImpl implements ModuleService {
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
    private FileAnswerMapper fileAnswerMapper;
    @Autowired
    private KeyFileComponent keyFileComponent;

    @Override
    public String addefaultData(ModuleDefaultDataAddDTO moduleDefaultDataAddDTO,String username) throws Exception {
        DefaultDataExample defaultDataExample = new DefaultDataExample();
        defaultDataExample.createCriteria();
        int defaultModuleID = defaultDataMapper.selectByExample(defaultDataExample).size()+1;
        if (!checkExistDefaultModule(moduleDefaultDataAddDTO.getDefaultID())){
            throw new Exception("Not Exist!!");
        }
        DefaultData defaultData =new DefaultData();
        defaultData.setKeyfileid(keyFileComponent.creatKeyFile(128));
        defaultData.setDefaultmoduleId(defaultModuleID);
        defaultData.setDefaultId(moduleDefaultDataAddDTO.getDefaultID());
        defaultData.setDataName(moduleDefaultDataAddDTO.getDataName());
        defaultData.setDescription(moduleDefaultDataAddDTO.getDescription());
        defaultData.setCreatetime(new Date());
        defaultDataMapper.insertSelective(defaultData);
        ModuleUser moduleUser =new ModuleUser();
        moduleUser.setIsPermiss(1);
        moduleUser.setModuleId(defaultModuleID);
        moduleUser.setUserId(username);
        moduleUser.setType("uploader");
        moduleUserMapper.insertSelective(moduleUser);
        return "Success";
    }

    @Override
    public List<DefaultData> defaultModuleData(int defaultID) throws Exception {
        if (!checkExistDefaultModule(defaultID)){
            throw new Exception("Not Exist!!");
        }
        DefaultDataExample defaultDataExample = new DefaultDataExample();
        defaultDataExample.createCriteria().andDefaultIdEqualTo(defaultID).andIsUserfulEqualTo(true);
        return defaultDataMapper.selectByExample(defaultDataExample);
    }

    @Override
    public List<DefaultModuleDTO> defaultModule() throws Exception {
        DefaultModuleExample defaultModuleExample = new DefaultModuleExample();
        defaultModuleExample.createCriteria().andIsuserfulEqualTo(true);
        List<DefaultModule> defaultModuleList = defaultModuleMapper.selectByExample(defaultModuleExample);
        List<DefaultModuleDTO> answ = new ArrayList<>();
        for (DefaultModule r:defaultModuleList){
            DefaultModuleDTO defaultModuleDTO = new DefaultModuleDTO(r);
            answ.add(defaultModuleDTO);
        }
        return answ;
    }

    @Override
    public DefaultModuleDTO defaultModule(int DefaultId) throws Exception {
        DefaultModule r = defaultModuleMapper.selectByPrimaryKey(DefaultId);
        return new DefaultModuleDTO(r);
    }

    @Override
    public List<Map<String,String>> shouAnsw(int ModuleID,String username) throws Exception {
        if (!checkExistModule(ModuleID)){
            throw new Exception("Not Exist!!");
        }
        Module module = moduleMapper.selectByPrimaryKey(ModuleID);
        ProjectUserExample projectUserExample =  new ProjectUserExample();
        projectUserExample.createCriteria().andProjectIdEqualTo(module.getProjectId()).andUserIdEqualTo(username);
        List<ProjectUser> projectUserList = projectUserMapper.selectByExample(projectUserExample);
        if (projectUserList.size()==0){
            throw new  Exception("Your Have No Permission!");
        }
        if (!module.getIsCalculate()){
            throw new  Exception("Calculate not complete");
        }
        if(!module.getIsUserful()){
            throw new  Exception("Module not Userful");
        }
        FileAnswer fileAnswer;
        List<FileAnswer> fileAnswerList = new ArrayList<>();
        if (module.getIsDefault()){
            DefaultData defaultData = defaultDataMapper.selectByPrimaryKey(module.getDefaultmoduleId());
            if (defaultData.getIsCalculate()){
                throw new  Exception("DefaultData Answ File Not Exist,Calculate not complete");
            }
            else if(!defaultData.getIsUserful()){
                throw new  Exception("Module not Userful");
            }
            fileAnswer = fileAnswerMapper.selectByPrimaryKey(defaultData.getFileaswid());
        }
        else{
            FileAnswerExample fileAnswerExample = new FileAnswerExample();
            fileAnswerExample.createCriteria().andModuleIdEqualTo(module.getModuleId());
            fileAnswerList = fileAnswerMapper.selectByExample(fileAnswerExample);
            if (fileAnswerList.size()==0){
                module.setIsCalculate(false);
                moduleMapper.updateByPrimaryKeySelective(module);
                throw new  Exception("Module Answ File Not Exist,Calculate not complete");
            }
            fileAnswer = fileAnswerList.get(0);
        }
        String url = fileAnswer.getFileUrl();
        url = pathHandle(url);
        File file = new File(url);
        if (!file.exists()){
            module.setIsCalculate(false);
            moduleMapper.updateByPrimaryKeySelective(module);
            if (module.getIsDefault()){
                DefaultData defaultData = defaultDataMapper.selectByPrimaryKey(module.getDefaultmoduleId());
                defaultData.setIsCalculate(false);
                defaultDataMapper.updateByPrimaryKeySelective(defaultData);
            }
            for(FileAnswer records:fileAnswerList){
                fileAnswerMapper.deleteByPrimaryKey(records.getFileId());
            }
            throw new  Exception("Can't Find AnswFile,Calculate May not complete");
        }
        Map<String, List<String>> map = CSVUtils.readCsvFile(url,-1);
        List<Map<String,String>> answList = new ArrayList<>();
        Set<String> keySet = map.keySet();
        int length = map.get(keySet.iterator().next()).size();
        for(int i=0;i<length;i++){
            Map<String,String> tempMap = new HashMap<>();
            tempMap.put("multNum",module.getMultnum().toString());
            for(String str:keySet){
                tempMap.put(str,map.get(str).get(i));
            }
            answList.add(tempMap);
        }

        return answList;
    }

    /**
     * @param ModuleID
     * @return
     * @throws Exception
     */
    @Override
    public String delete(int ModuleID,String username) throws Exception {
        if (!checkExistModule(ModuleID)){
            throw new Exception("Not Exist!!");
        }
        Module module = moduleMapper.selectByPrimaryKey(ModuleID);
        if(!projectUserMapper.findCreater(module.getProjectId()).equals(username)){
            throw new  Exception("Your Have No Permission!");
        }
        module.setModuleId(ModuleID);
        module.setIsUserful(false);
        moduleMapper.updateByPrimaryKeySelective(module);
        return "success";
    }

    @Override
    public String deleteDefaultModule(int DefauleModuleID,String username) throws Exception {
        DefaultModule defaultModule = new DefaultModule();
        if (!checkExistDefaultModule(DefauleModuleID)){
            throw new Exception("Not Exist!!");
        }
        defaultModule.setDefaultId(DefauleModuleID);
        defaultModule.setIsuserful(false);
        defaultModuleMapper.updateByPrimaryKeySelective(defaultModule);
        return "success";
    }

    @Override
    public String deleteDefaultData(int defaultDataId,String username) throws Exception {
        DefaultData defaultData = new DefaultData();
        if (!checkExistDefaultDate(defaultDataId)){
            throw new Exception("Not Exist!!");
        }
        if(!moduleUserMapper.findCreater(defaultDataId).equals(username)){
            throw new  Exception("Your Have No Permission!");
        }
        defaultData.setDefaultmoduleId(defaultDataId);
        defaultData.setIsUserful(false);
        defaultDataMapper.updateByPrimaryKeySelective(defaultData);
        return "success";
    }

    @Override
    public String addDefualt(DefaultModuleAddDTO defaultModuleAddDTO) throws Exception {
        DefaultModule defaultModule = defaultModuleAddDTO.ToModule();
        defaultModule = FunctionUtils.defaultModuleHandle(defaultModule);
        defaultModuleMapper.insertSelective(defaultModule);
        return "success";
    }

    @Override
    public KeyDTO returnKey(int moduleId, boolean isDefaultId) throws Exception {
        int keyId = 0;
        if (isDefaultId){
            DefaultData defaultData = defaultDataMapper.selectByPrimaryKey(moduleId);
            keyId = defaultData.getKeyfileid();
        }
        else{
            Module module = moduleMapper.selectByPrimaryKey(moduleId);
            keyId = module.getKeyfileid();
        }
        Key key = keyFileComponent.getKey(keyId);
        return new KeyDTO(key);
    }

    @Override
    public List<ShowDefaultDataDTO> showDefaultData(String getUsername) throws Exception {
        ModuleUserExample moduleUserExample = new ModuleUserExample();
        moduleUserExample.createCriteria().andTypeEqualTo("uploader").andUserIdEqualTo(getUsername);
        List<ModuleUser> moduleUserList = moduleUserMapper.selectByExample(moduleUserExample);
        List<ShowDefaultDataDTO> answList = new ArrayList<>();
        for (ModuleUser record:moduleUserList){
            DefaultData defaultData = defaultDataMapper.selectByPrimaryKey(record.getModuleId());
            DefaultModule defaultModule = defaultModuleMapper.selectByPrimaryKey(defaultData.getDefaultId());
            String ModuleName = defaultModule.getName();
            String DataName = defaultData.getDataName();
            ShowDefaultDataDTO showDefaultDataDTO = new ShowDefaultDataDTO(ModuleName,defaultData.getDefaultmoduleId(),DataName,defaultData.getStatus());
            if (defaultData.getUptime()!=null){
                showDefaultDataDTO.setUpDate(defaultData.getUptime());
            }
            if (defaultData.getCaltime()!=null){
                showDefaultDataDTO.setCalDate(defaultData.getCaltime());
            }
            answList.add(showDefaultDataDTO);
        }
        return answList;
    }

    @Override
    public ModuleInfoDTO getModule(int moduleId) throws Exception {
        return new ModuleInfoDTO(moduleMapper.selectByPrimaryKey(moduleId));
    }

    @Override
    public List<String> getfunctions(int moduleId) throws Exception {
        if (!checkExistModule(moduleId)){
            throw new Exception("Not Exist!!");
        }
        Module module = moduleMapper.selectByPrimaryKey(moduleId);
        return new ArrayList<>(FunctionUtils.getIdentifiy(module.getFunction()));
    }

    @Override
    public DefaultData getDefaultDataInfo(int defaultDateId) throws Exception {
        return defaultDataMapper.selectByPrimaryKey(defaultDateId);
    }

    @Override
    public List<ModuleInfoDTO> getCompleteModuleInfo(String getUsername) throws Exception {
        ProjectUserExample projectUserExample = new ProjectUserExample();
        projectUserExample.createCriteria().andUserIdEqualTo(getUsername);
        List<ProjectUser>  projectUserList= projectUserMapper.selectByExample(projectUserExample);
        List<Module> moduleList = new ArrayList<>();
        List<ModuleInfoDTO> answ = new ArrayList<>();
        for(ProjectUser record:projectUserList){
           ModuleExample moduleExample = new ModuleExample();
           moduleExample.createCriteria().andProjectIdEqualTo(record.getProjectId());
           List<Module> tempModuleList = moduleMapper.selectByExample(moduleExample);
           for(Module mod:tempModuleList){
                if (mod.getIsCalculate()){
                    ModuleInfoDTO temp = new ModuleInfoDTO(mod);
                    projectUserExample = new ProjectUserExample();
                    projectUserExample.createCriteria().andProjectIdEqualTo(record.getProjectId()).andTypeEqualTo("creater");
                    List<ProjectUser>  tempProjectUserList= projectUserMapper.selectByExample(projectUserExample);
                    temp.setCreater(tempProjectUserList.get(0).getUserId());
                    answ.add(temp);
                }
            }
        }
        return answ;
    }

    @Override
    public List<String> getfunctionsDefault(int defaultId) throws Exception {
        if (!checkExistDefaultModule(defaultId)){
            throw new Exception("Not Exist!!");
        }
        DefaultModule defaultModule = defaultModuleMapper.selectByPrimaryKey(defaultId);
        return new ArrayList<>(FunctionUtils.getIdentifiy(defaultModule.getFunction()));
    }

    @Override
    public Map<String, Object> shoucalInfo(int moduleID, String getUsername) throws Exception {
        Date startTime=null;
        Date endTIme = null;
        Module module = moduleMapper.selectByPrimaryKey(moduleID);
        if (module==null || !module.getIsUserful()){
            throw new Exception("Not Exist!!");
        }
        DefaultData defaultData = null;
        if (module.getIsDefault()){
            defaultData = defaultDataMapper.selectByPrimaryKey(module.getDefaultmoduleId());
            if (defaultData==null || !defaultData.getIsUserful()){
                throw new Exception("Not Exist!!");
            }
            if (defaultData.getCaltime()==null){
                throw new Exception("Calculate Not Start!!");
            }
            else{
                startTime = defaultData.getCaltime();
            }
            if (defaultData.getCompletetime()!=null){
                endTIme = defaultData.getCompletetime();
            }
        }
        else{
            if (module.getCaltime()==null){
                throw new Exception("Calculate Not Start!!");
            }
            else{
                startTime = module.getCaltime();
            }
            if (module.getCompletetime()!=null){
                endTIme = module.getCompletetime();
            }
        }
        return getCalInfoTime(startTime,endTIme);
    }

    @Override
    public Map<String, Object> shoucalInfoDefualt(int defaultDataId, String username) throws Exception {
        Date startTime=null;
        Date endTIme = null;
        DefaultData defaultData = defaultDataMapper.selectByPrimaryKey(defaultDataId);
        if (defaultData==null || !defaultData.getIsUserful()){
            throw new Exception("Not Exist!!");
        }
        if (defaultData.getCaltime()==null){
            throw new Exception("Calculate Not Start!!");
        }
        else{
            startTime = defaultData.getCaltime();
        }
        if (defaultData.getCompletetime()!=null){
            endTIme = defaultData.getCompletetime();
        }
        return getCalInfoTime(startTime,endTIme);
    }

    @Override
    public List<DefaultModuleManagerDTO> getDefaultManager() throws Exception {
        List<DefaultModuleManagerDTO> answ = new ArrayList<>();
        DefaultModuleExample defaultModuleExample = new DefaultModuleExample();
        defaultModuleExample.createCriteria();
        List<DefaultModule> defaultModuleList = defaultModuleMapper.selectByExample(defaultModuleExample);
        for (DefaultModule record:defaultModuleList){
            int dataNum = getDataNumByDefaultModule(record);
            int projectNum = getprojectNumByDefaultModule(record);
            answ.add(new DefaultModuleManagerDTO(record,dataNum,projectNum));
        }
        return answ;
    }

    @Override
    public String changeDefaultManager(int defaultId, boolean pos) throws Exception {
        DefaultModule defaultModule = defaultModuleMapper.selectByPrimaryKey(defaultId);
        if (defaultModule == null){
            throw new Exception("DefaultModule not exist");
        }
        defaultModule.setIsuserful(pos);
        defaultModuleMapper.updateByPrimaryKey(defaultModule);
        return "Success";
    }

    public List<Module> getCalculateOkList(String username)throws Exception{
        ProjectUserExample projectUserExample = new ProjectUserExample();
        projectUserExample.createCriteria().andUserIdEqualTo(username);
        List<ProjectUser> projectUserList = projectUserMapper.selectByExample(projectUserExample);
        List<Module> answ = new ArrayList<>();
        ModuleExample moduleExample = null;
        for(ProjectUser record:projectUserList){
            moduleExample = new ModuleExample();
            moduleExample.createCriteria().andProjectIdEqualTo(record.getProjectId()).andIsUserfulEqualTo(true);
            List<Module> tempModuleList = moduleMapper.selectByExample(moduleExample);
            for (Module re:tempModuleList){
                if (re.getIsCalculate()){
                    answ.add(re);
                }
            }
        }
        return answ;
    }

    @Override
    public  List<ModuleInfoDTO>  showCalcualteOKList(String Username) throws Exception {
        List<ModuleInfoDTO> answ = new ArrayList<>();
        List<Module> moduleList = getCalculateOkList(Username);
        for(Module reocord:moduleList){
            answ.add(new ModuleInfoDTO(reocord));
        }
        return answ;
    }

    @Override
    public Map<String, Object> showCalcualteNum(String Username) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("num",getCalculateOkList(Username).size());
        return map;
    }

    private  int getprojectNumByDefaultModule(DefaultModule defaultModule){
        int answ = 0;
        DefaultDataExample defaultDataExample = new DefaultDataExample();
        defaultDataExample.createCriteria().andDefaultIdEqualTo(defaultModule.getDefaultId()).andIsUserfulEqualTo(true);
        List<DefaultData> defaultDataList = defaultDataMapper.selectByExample(defaultDataExample);
        for (DefaultData record:defaultDataList){
            ModuleExample moduleExample = new ModuleExample();
            moduleExample.createCriteria().andIsDefaultEqualTo(true).andDefaultmoduleIdEqualTo(record.getDefaultmoduleId());
            answ = answ + moduleMapper.selectByExample(moduleExample).size();
        }
        return answ;
    }

    private  int getDataNumByDefaultModule(DefaultModule defaultModule){
        int answ = 0;
        DefaultDataExample defaultDataExample = new DefaultDataExample();
        defaultDataExample.createCriteria().andDefaultIdEqualTo(defaultModule.getDefaultId()).andIsUserfulEqualTo(true);
        answ = defaultDataMapper.selectByExample(defaultDataExample).size();
        return answ;
    }
    public boolean checkExistModule(int ID){
        Module module=moduleMapper.selectByPrimaryKey(ID);
        if (module==null || !module.getIsUserful()){
            return false;
        }
        return true;
    }

    public boolean checkExistDefaultDate(int ID){
        DefaultData defaultData=defaultDataMapper.selectByPrimaryKey(ID);
        if (defaultData==null || !defaultData.getIsUserful()){
            return false;
        }
        return true;
    }

    public boolean checkExistDefaultModule(int ID){
        DefaultModule module=defaultModuleMapper.selectByPrimaryKey(ID);
        if (module==null || !module.getIsuserful()){
            return false;
        }
        return true;
    }

    public String pathHandle(String path){
        if (path.startsWith("D://temp-rainy//")){
            path =  path.substring(16,path.length());
        }
        else if (path.startsWith("/temp-rainy/")){
            path = path.substring(12,path.length());
        }
        System.out.println(path);
        String system = System.getProperty("os.name");
        path=path.replaceAll("//","/");
        if (system.startsWith("Windows")){
            path = "D://temp-rainy//"+path;
        }
        else if (system.startsWith("Linux")){
            path = "/temp-rainy/"+path;
        }
        return path;
    }

    private Map<String,Object> getCalInfoTime(Date startTime,Date endTime) throws Exception {
        Map<String,Object> answMap = new HashMap<>();
        if (startTime==null){
            throw new Exception("startTime = null ,May be Calculate Not Start!!");
        }
        answMap.put("StartTime",startTime);
        if (endTime!=null){
            answMap.put("Precent","90%");
            answMap.put("EstimateTime",endTime);
//                answMap.put("EstimateTime",new Date(System.currentTimeMillis()+3600*1000));
        }
        else{
            answMap.put("Precent","0%");
            answMap.put("EstimateTime",new Date(System.currentTimeMillis()+3600*1000));
        }
        answMap.put("EndTime",endTime);
        return answMap;
    }
}
