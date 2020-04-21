package com.mutiny.demo.Service.Impl;

import com.mutiny.demo.Service.PermissService;
import com.mutiny.demo.dao.*;
import com.mutiny.demo.dto.PermissRequestDTO;
import com.mutiny.demo.dto.PermissionApplyDTO;
import com.mutiny.demo.dto.ProjectInfoDTO;
import com.mutiny.demo.dto.ProjectListDTO;
import com.mutiny.demo.message.MessageSender;
import com.mutiny.demo.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: Anonsmd
 * @Date: 2020/2/5 11:36
 */
@Service
public class PermissServiceImpl implements PermissService {

    @Autowired
    private ProjectUserMapper projectUserMapper;
    @Autowired
    private ProjectMapper projectMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ModuleMapper moduleMapper;
    @Autowired
    private DefaultDataMapper defaultDataMapper;
    @Autowired
    private DefaultModuleMapper defaultModuleMapper;
    @Autowired
    private ModuleUserMapper moduleUserMapper;
    @Autowired
    private CompanyMapper companyMapper;
    @Autowired
    private MessageSender messageSender;

    @Override
    public String partJoin(String inviteCode,String username) throws Exception {
        ProjectExample projectExample = new ProjectExample();
        projectExample.createCriteria().andInviteCode1EqualTo(inviteCode);
        Project project = projectMapper.selectByExample(projectExample).get(0);
        if (!project.getInviteCode1().equals(inviteCode) ){
            throw new Exception("Invite Code Not Exist Or it's not a parter code");
        }
        if (!project.getIsUserful()){
            throw new Exception("Invite Code Not Exist Or it's not a parter code");
        }
//        User user = userMapper.selectByPrimaryKey(username);
//        if (user.getType().equals("企业职员")){
//            throw new Exception("Your Have No Permiss");
//        }
        ProjectUser projectUser = new ProjectUser();
        projectUser.setProjectId(project.getProjectId());
        projectUser.setType("parter");
        projectUser.setUserId(username);
        projectUser.setIntime(new Date());
        projectUserMapper.insertSelective(projectUser);

        ProjectUserExample projectUserExample = new ProjectUserExample();
        projectUserExample.createCriteria().andTypeEqualTo("creater").andProjectIdEqualTo(project.getProjectId());
        ProjectUser creat = projectUserMapper.selectByExample(projectUserExample).get(0);
        String text = String.format("您的项目(ID=%d)有新成员加入",creat.getProjectId());
        messageSender.sendPrivate("项目情况通报",text,creat.getUserId());
        return "success";
    }

    @Override
    public String watchJoin(String inviteCode,String username) throws Exception {
        ProjectExample projectExample = new ProjectExample();
        projectExample.createCriteria().andInviteCode2EqualTo(inviteCode);
        Project project = projectMapper.selectByExample(projectExample).get(0);
        if (!project.getInviteCode2().equals(inviteCode)){
            throw new Exception("Invite Code Not Exist Or it's not a parter code");
        }
        ProjectUser projectUser = new ProjectUser();
        projectUser.setProjectId(project.getProjectId());
        projectUser.setType("watcher");
        projectUser.setIntime(new Date());
        projectUser.setUserId(username);
        projectUserMapper.insertSelective(projectUser);
        return "Success";
    }

    @Override
    public String govePermissRequest(PermissRequestDTO permissRequestDTO,String username) throws Exception {
        ProjectUserExample projectUserExample = new ProjectUserExample();
        projectUserExample.createCriteria().andProjectIdEqualTo(permissRequestDTO.getProjectID()).andTypeEqualTo("creater");
        ProjectUser projectUser = projectUserMapper.selectByExample(projectUserExample).get(0);
        if (!username.equals(projectUser.getUserId())){
            throw new Exception("Your Hava No Permiss!!");
        }
        Module module = moduleMapper.selectByPrimaryKey(permissRequestDTO.getModuleID());
        if (module == null){
            throw new Exception("Module Not Exist!!");
        }
        if (!module.getIsDefault()){
            throw new Exception("This is not a Default Module!!");
        }
        int DefaultData = module.getDefaultmoduleId();
        ModuleUser moduleUser = new ModuleUser();
        moduleUser.setType("user");
        moduleUser.setUserId(username);
        moduleUser.setModuleId(DefaultData);
//        moduleUser.setIsPermiss(false);
        moduleUserMapper.insertSelective(moduleUser);
        return "success";
    }

    @Override
    public List<PermissionApplyDTO> showPermissGet(String username) throws Exception {
        ModuleUserExample moduleUserExample = new ModuleUserExample();
        moduleUserExample.createCriteria().andTypeEqualTo("uploader").andUserIdEqualTo(username);
        List<ModuleUser> moduleUserList = moduleUserMapper.selectByExample(moduleUserExample);
        List<PermissionApplyDTO> answList = new ArrayList<>();
        for (ModuleUser record:moduleUserList){
            DefaultData defaultData = defaultDataMapper.selectByPrimaryKey(record.getModuleId());
            DefaultModule defaultModule = defaultModuleMapper.selectByPrimaryKey(defaultData.getDefaultId());
            String ModuleName = defaultModule.getName();
            String DataName = defaultData.getDataName();
            moduleUserExample.clear();
            moduleUserExample.createCriteria().andModuleIdEqualTo(record.getModuleId()).andTypeEqualTo("user");
            List<ModuleUser> applyList = moduleUserMapper.selectByExample(moduleUserExample);
            for (ModuleUser apply:applyList){
                PermissionApplyDTO permissionApplyDTO = new PermissionApplyDTO();
                permissionApplyDTO.setApplyDate(apply.getStarttime());
                permissionApplyDTO.setDataName(DataName);
                if (apply.getEndtime()!=null) {
                    permissionApplyDTO.setHandleDate(apply.getEndtime());
                }
                User user = userMapper.selectByPrimaryKey(apply.getUserId());
                if (user.getCompanyid()!=null){
                    Company company = companyMapper.selectByPrimaryKey(user.getCompanyid());
                    permissionApplyDTO.setCompanyName(company.getName());
                }
                permissionApplyDTO.setDefaultDataId(apply.getModuleId());
                permissionApplyDTO.setID(apply.getRelationId());
                permissionApplyDTO.setModuleName(ModuleName);
                permissionApplyDTO.setProjectId(apply.getProjectid());
                permissionApplyDTO.setStatus(apply.getIsPermiss());
                answList.add(permissionApplyDTO);
            }
        }
        return answList;
    }

    @Override
    public String pushPermiss(int retionId, int staus, String username) throws Exception {
        ModuleUser moduleUser = moduleUserMapper.selectByPrimaryKey(retionId);
        ModuleUserExample moduleUserExample = new ModuleUserExample();
        moduleUserExample.createCriteria().andModuleIdEqualTo(moduleUser.getModuleId()).andTypeEqualTo("uploader");
        ModuleUser up = moduleUserMapper.selectByExample(moduleUserExample).get(0);
        if (!up.getUserId().equals(username)){
            throw new Exception("Your hava no permiss");
        }
        moduleUser.setIsPermiss(staus);
        moduleUser.setEndtime(new Date());
        moduleUserMapper.updateByPrimaryKeySelective(moduleUser);
        String text = null;
        if (staus == 1){
            text = String.format("您于%s提交的申请已经审核完毕，您现在可前往您的项目(ID=%d)查看",moduleUser.getStarttime().toString(),moduleUser.getProjectid());
        }
        else if(staus ==2){
            text = String.format("您于%s在项目(ID=%d)提交的申请已被拒绝，如有疑问请联系客服。",moduleUser.getStarttime().toString(),moduleUser.getProjectid());

        }
        messageSender.sendPrivate("审核结果",text,moduleUser.getUserId());
        return "Success";
    }
}
