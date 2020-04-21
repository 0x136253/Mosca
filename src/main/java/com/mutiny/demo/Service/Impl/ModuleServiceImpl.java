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
    public String shouAnsw(int ModuleID) throws Exception {
        return null;
    }

    /**
     * @param ModuleID
     * @return
     * @throws Exception
     */
    @Override
    public String delete(int ModuleID,String username) throws Exception {
        Module module = moduleMapper.selectByPrimaryKey(ModuleID);
        if (!checkExistModule(ModuleID)){
            throw new Exception("Not Exist!!");
        }
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
}
