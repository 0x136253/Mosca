package com.mutiny.demo.Service.Impl;

import com.mutiny.demo.Service.ProjectService;
import com.mutiny.demo.component.KeyFileComponent;
import com.mutiny.demo.dao.*;
import com.mutiny.demo.dto.*;
import com.mutiny.demo.message.MessageSender;
import com.mutiny.demo.pojo.*;
import com.mutiny.demo.util.FunctionUtils;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author: Anonsmd
 * @Date: 2020/1/16 18:32
 */
@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectMapper projectMapper;
    @Autowired
    private ProjectUserMapper projectUserMapper;
    @Autowired
    private ModuleMapper moduleMapper;
    @Autowired
    private DefaultDataMapper defaultDataMapper;
    @Autowired
    private KeyFileComponent keyFileComponent;
    @Autowired
    private ModuleUserMapper moduleUserMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private FileOtherMapper fileOtherMapper;
    @Autowired
    private MessageSender messageSender;
    /**
     * 还需加入creater
     * @param project
     * @return
     */
    @Override
    public ProjectInfoDTO ProjectCreate(Project project,String username)  throws Exception{
        project.setStage(1);
        int projectID=projectMapper.GetCount()+1;
        project.setProjectId(projectID);
        String code1=getRandomString(6);
        String code2=getRandomString(6);
        while (!CheckCode(code1)){
            code1=getRandomString(6);
        }
        while (!CheckCode(code2)){
            code2=getRandomString(6);
        }
        project.setInviteCode1(code1);
        project.setInviteCode2(code2);
        projectMapper.insertSelective(project);
        ProjectUser projectUser=new ProjectUser();
        projectUser.setProjectId(projectID);
        projectUser.setUserId(username);
        projectUser.setType("creater");
        projectUser.setIntime(new Date());
        projectUserMapper.insertSelective(projectUser);
        messageSender.sendPrivate("项目情况通报","项目创建成功",username);
        return new ProjectInfoDTO(projectMapper.selectByPrimaryKey(projectID),username);
    }

    @Override
    public String ProjectAddModuleDescription(ProjectModulesDescriptionDTO projectModulesDescriptionDTO,String username)  throws Exception{
        Project project=new Project();
        project.setProjectId(projectModulesDescriptionDTO.getProject_ID());
        if (!CheckExist(projectModulesDescriptionDTO.getProject_ID())){
            throw new  Exception("Not Exist Project");
        }
        if (!CheckPermiss(username,projectModulesDescriptionDTO.getProject_ID())){
            throw new Exception("Your hava no permission");
        }
        project.setModuleDescription(projectModulesDescriptionDTO.getModuleDescription());
        projectMapper.updateByPrimaryKeySelective(project);
        return "修改成功";
    }

    @Override
    public String changeCode(ProjectChangeCodeDTO projectChangeCodeDTO,String username)  throws Exception{
        Project project=new Project();
        project.setProjectId(projectChangeCodeDTO.getProject_ID());
        if (!CheckExist(projectChangeCodeDTO.getProject_ID())){
            throw new  Exception("Not Exist Project");
        }
        if (!CheckPermiss(username,projectChangeCodeDTO.getProject_ID())){
            throw new Exception("Your hava no permission");
        }
        if (!projectChangeCodeDTO.getInvitecode1().equals("")){
            if (!CheckCode(projectChangeCodeDTO.getInvitecode1())){
                throw new  Exception("Code1 Already Exist!!");
        }
            project.setInviteCode1(projectChangeCodeDTO.getInvitecode1());
        }
        if (!projectChangeCodeDTO.getInvitecode2().equals("")){
            if (!CheckCode(projectChangeCodeDTO.getInvitecode2())){
                throw new  Exception("Code2 Already Exist!!");
            }
            project.setInviteCode2(projectChangeCodeDTO.getInvitecode2());
        }
        projectMapper.updateByPrimaryKeySelective(project);
        return "修改成功";
    }

    @Override
    public String addModule(ProjectModuleAddDTO projectModuleAddDTO,String username)  throws Exception{
        int projectID = projectModuleAddDTO.getProject_ID();
        if (!CheckExist(projectID)){
            throw new  Exception("Not Exist Project");
        }
        if (!CheckPermiss(username,projectModuleAddDTO.getProject_ID())){
            throw new Exception("Your hava no permission");
        }
        Project project = projectMapper.selectByPrimaryKey(projectID);
        if (project.getIsDefault()){
            throw new  Exception("This a Default Project!!");
        }
        project.setStage(2);
        projectMapper.updateByPrimaryKeySelective(project);
        List<ModuleAddDTO> moduleAddDTOList =projectModuleAddDTO.getModuleAddDTOS();
        int count=moduleMapper.GetCount();
        for (int i=0;i<moduleAddDTOList.size();i++){
            count++;
            Module module = moduleAddDTOList.get(i).toModule(count,projectID);
            module = FunctionUtils.moduleHandle(module);
            module.setCreatetime(new Date());
            module.setKeyfileid(keyFileComponent.creatKeyFile(128));
            moduleMapper.insertSelective(module);
        }
        return "增加成功";
    }

    @Override
    public String addDefaultModule(ProjectDefaultAddDTO projectDefaultAddDTO,String username)  throws Exception{
        List<Integer> IDs = projectDefaultAddDTO.getDefaultDataID();
        int projectID = projectDefaultAddDTO.getProjectID();
        if (!CheckExist(projectID)){
            throw new  Exception("Not Exist Project");
        }
        if (!CheckPermiss(username,projectID)){
            throw new Exception("Your hava no permission");
        }
        Project project = projectMapper.selectByPrimaryKey(projectID);
        ModuleExample moduleExample = new ModuleExample();
        moduleExample.createCriteria().andProjectIdEqualTo(projectID);
        List<Module> modules = moduleMapper.selectByExample(moduleExample);
        for (Module record:modules){
            if (!record.getIsDefault()){
                throw new Exception("This Project Could't be use as an Default Module!");
            }
        }
        for (int i:IDs){
            DefaultData defaultData = defaultDataMapper.selectByPrimaryKey(i);
            if (defaultData == null){
                throw new Exception("DefaultData:"+i+"is Not Exist!!");
            }
            if (!defaultData.getIsUserful()){
                throw new Exception("DefaultData:"+i+"is Not Userful!!");
            }
            Module module = new Module();
            module.setIsDefault(true);
            module.setProjectId(projectID);
            module.setDefaultmoduleId(i);
            module.setDescription(defaultData.getDescription());
            module.setModuleName(defaultData.getDataName());
            module.setIsCalculate(defaultData.getIsCalculate());
            moduleMapper.insertSelective(module);

            ModuleUser moduleUser = new ModuleUser();
            moduleUser.setUserId(username);
            moduleUser.setType("user");
            moduleUser.setStarttime(new Date());
            moduleUser.setProjectid(projectID);
            moduleUser.setModuleId(i);
            moduleUserMapper.insertSelective(moduleUser);
            ModuleUserExample moduleUserExample = new ModuleUserExample();
            moduleUserExample.createCriteria().andModuleIdEqualTo(i).andTypeEqualTo("uploader");
            ModuleUser up = moduleUserMapper.selectByExample(moduleUserExample).get(0);
            messageSender.sendPrivate("待审核通知","您有一条请求待审核",up.getUserId());
        }
        project.setIsDefault(true);
        project.setStage(2);
        projectMapper.updateByPrimaryKeySelective(project);
        return "success";
    }

    @Override
    public ProjectInfoDTO getInfo(int projectID)  throws Exception{
        if (!CheckExist(projectID)) {
            throw new  Exception("project not exist!!");
        }
        return new ProjectInfoDTO(projectMapper.selectByPrimaryKey(projectID),projectUserMapper.findCreater(projectID));
    }

    @Override
    public ProjectCompleteDTO getIsEnd(String username)  throws Exception{
        ProjectCompleteDTO projectCompleteDTO=new ProjectCompleteDTO();
        updateProjectStatusByUsername(username);
        projectCompleteDTO.setCreateComplete(projectMapper.GetIsEndCount(username,"creater",1));
        projectCompleteDTO.setCreateNotComplete(projectMapper.GetIsEndCount(username,"creater",0));
        projectCompleteDTO.setJoinComplete(projectMapper.GetIsEndCount(username,"join",1));
        projectCompleteDTO.setJoinNotComplete(projectMapper.GetIsEndCount(username,"join",0));
        projectCompleteDTO.setWatchComplete(projectMapper.GetIsEndCount(username,"watch",1));
        projectCompleteDTO.setWatchNotComplete(projectMapper.GetIsEndCount(username,"watch",0));
        return projectCompleteDTO;
    }

    private void updateProjectStatusByUsername(String username) {
        ProjectUserExample projectUserExample = new ProjectUserExample();
        projectUserExample.createCriteria().andUserIdEqualTo(username);
        List<ProjectUser> projectUserList = projectUserMapper.selectByExample(projectUserExample);
        for(ProjectUser record:projectUserList){
            if (!checkModuleStatusByProjectId(record.getProjectId())){
                continue;
            }
            Project project = new Project();
            project.setProjectId(record.getProjectId());
            project.setIsEnd(true);
            projectMapper.updateByPrimaryKeySelective(project);
        }
    }

    private boolean checkModuleStatusByProjectId(Integer projectId) {
        ModuleExample moduleExample = new ModuleExample();
        moduleExample.createCriteria().andProjectIdEqualTo(projectId);
        List<Module> moduleList = moduleMapper.selectByExample(moduleExample);
        for (Module record:moduleList){
            if (record.getIsDefault()){
                DefaultData defaultData = defaultDataMapper.selectByPrimaryKey(record.getDefaultmoduleId());
                if (defaultData ==null || (!defaultData.getIsCalculate() && defaultData.getIsUserful())){
                    return false;
                }
            }
            else {
                if (!record.getIsCalculate() && record.getIsUserful()){
                    return false;
                }
            }
        }
        return true;
    }


    @Override
    public ProjectListDTO getProject(String username,String type,Integer is_default,int currIndex,int pagesize)  throws Exception{
        List<Project> projectList=projectMapper.GetProjectList(username,type,is_default,currIndex,pagesize);
        ProjectListDTO projectListDTO=new ProjectListDTO();
        List<ProjectInfoDTO> projectInfoDTOList=new ArrayList<>();
        for (int i=0;i<projectList.size();i++){
            projectInfoDTOList.add(new ProjectInfoDTO(projectList.get(i),username));
        }
        projectListDTO.setProjectInfoDTOS(projectInfoDTOList);
        return projectListDTO;
    }

    @Override
    public ProjectListDTO getProjectAll(String username, String type, Integer is_default) throws Exception {
        List<Project> projectList=projectMapper.GetProjectListAll(username,type,is_default);
        ProjectListDTO projectListDTO=new ProjectListDTO();
        List<ProjectInfoDTO> projectInfoDTOList=new ArrayList<>();
        for (int i=0;i<projectList.size();i++){
            projectInfoDTOList.add(new ProjectInfoDTO(projectList.get(i),username));
        }
        projectListDTO.setProjectInfoDTOS(projectInfoDTOList);
        return projectListDTO;
    }

    @Override
    public String delete(String username,int projectID)  throws Exception{
        if(!projectUserMapper.findCreater(projectID).equals(username)){
            throw new  Exception("Your Have No Permission!");
        }
        Project project=new Project();
        project.setProjectId(projectID);
        project.setIsUserful(false);
        projectMapper.updateByPrimaryKeySelective(project);
        return "删除成功";
    }

    @Override
    public ModuleListDTO getModules(int projectID)  throws Exception{
        if (!CheckExist(projectID)){
            throw new Exception("Not Exist!!");
        }
        ModuleExample moduleExample = new ModuleExample();
        moduleExample.createCriteria().andProjectIdEqualTo(projectID).andIsUserfulEqualTo(true);
        List<Module> moduleList=moduleMapper.selectByExample(moduleExample);
        List<ModuleInfoDTO> moduleInfoDTOS=new ArrayList<>();
        for (int i=0;i<moduleList.size();i++){
            moduleInfoDTOS.add(new ModuleInfoDTO(moduleList.get(i)));
        }
        ModuleListDTO moduleListDTO =new ModuleListDTO();
        moduleListDTO.setModuleInfoDTOList(moduleInfoDTOS);
        return moduleListDTO;
    }

    @Override
    public List<ProjectMemberDTO> getProjectMember(int projectID) throws Exception {
        ProjectUserExample projectUserExample = new ProjectUserExample();
        projectUserExample.createCriteria().andProjectIdEqualTo(projectID);
        List<ProjectUser> projectUserList = projectUserMapper.selectByExample(projectUserExample);
        List<ProjectMemberDTO> answ = new ArrayList<>();
        for (ProjectUser r:projectUserList){
            ProjectMemberDTO projectMemberDTO = new ProjectMemberDTO(r.getUserId(),r.getIntime(),r.getType());
            User user = userMapper.selectByPrimaryKey(r.getUserId());
            projectMemberDTO.setName(user.getName());
            if (user.getPortraitId()!=null){
                FileOther fileOther = new FileOther();
                projectMemberDTO.setPortraitURL(fileOther.getFileUrl());
            }
            answ.add(projectMemberDTO);
        }
        return answ;
    }

    @Override
    public List<ProjectNoModuleDTO> showProjectNoModule(String username) throws Exception {
        ProjectUserExample projectUserExample = new ProjectUserExample();
        projectUserExample.createCriteria().andUserIdEqualTo(username).andTypeEqualTo("creater");
        List<ProjectUser> projectUserList = projectUserMapper.selectByExample(projectUserExample);
        List<ProjectNoModuleDTO> answ = new ArrayList<>();
        for (ProjectUser r:projectUserList){
            Project project = projectMapper.selectByPrimaryKey(r.getProjectId());
            if (!project.getIsUserful()){
                continue;
            }
            ModuleExample moduleExample = new ModuleExample();
            moduleExample.clear();
            moduleExample.createCriteria().andProjectIdEqualTo(r.getProjectId());
            List<Module> moduleList = moduleMapper.selectByExample(moduleExample);
            if (moduleList.size()!=0){
                continue;
            }
            ProjectNoModuleDTO projectNoModuleDTO = new ProjectNoModuleDTO(project.getProjectId(),project.getProjectName());
            if (project.getCreateTime()!=null){
                projectNoModuleDTO.setCreateTime(project.getCreateTime());
            }
            answ.add(projectNoModuleDTO);
        }
        return answ;
    }


    private boolean CheckCode(String code){
        ProjectExample projectExample=new ProjectExample();
        projectExample.createCriteria().andInviteCode1EqualTo(code);
        ProjectExample projectExample2=new ProjectExample();
        projectExample2.createCriteria().andInviteCode2EqualTo(code);
        List<Project> project1=projectMapper.selectByExample(projectExample);
        List<Project> project2=projectMapper.selectByExample(projectExample2);
        if (project1.size()==0 && project2.size()==0){
            return true; //不冲突
        }
        return false;//冲突
    }

    public  String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    public boolean CheckExist(int ID){
        Project project=projectMapper.selectByPrimaryKey(ID);
        if (project==null || project.getIsUserful()==false){
            return false;
        }
        return true;
    }

    private boolean CheckPermiss(String username,int Id) {
        String uploader;
        uploader = projectUserMapper.findCreater(Id);
        if (!uploader.equals(username)){
            return false;
        }
        return true;
    }
}
