package com.mutiny.demo.controller;

import com.mutiny.demo.Service.ProjectService;
import com.mutiny.demo.api.CommonResult;
import com.mutiny.demo.api.MyLog;
import com.mutiny.demo.dto.*;
import com.mutiny.demo.util.JwtTokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Author: Anonsmd
 * @Date: 2020/1/10 14:02
 */
@Controller
@Api(tags = "ProjectController", description = "项目管理")
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;


    private String GetUsername(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //获取用户名
        String authHeader = request.getHeader(this.tokenHeader);
        String username=null;
        if (authHeader != null) {
            username = jwtTokenUtil.getUserNameFromToken(authHeader);
        }
        return username;
    }


    @MyLog(operation = "创建项目",database = "Project,Project_User")
    @ApiOperation(value = "创建项目")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAnyRole('ADMIN','GOVER','SYSTEM')")
    public ResponseEntity<Map<String,Object>> CreateProject(@RequestBody ProjectCreateDTO projectCreateDTO){
        ProjectInfoDTO str = null;
        try {
            str= projectService.ProjectCreate(projectCreateDTO.ToProject(),GetUsername());
        }catch (Exception e){
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(str);
    }


    @MyLog(operation = "增加模型描述",database = "Project")
    @ApiOperation(value = "增加模型描述[即被邀请者须知]")
    @RequestMapping(value = "/moduleDes", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAnyRole('ADMIN','GOVER','SYSTEM')")
    public ResponseEntity<Map<String,Object>> moduleDes(@RequestBody ProjectModulesDescriptionDTO projectModulesDescriptionDTO){
        String str = null;
        try {
            str= projectService.ProjectAddModuleDescription(projectModulesDescriptionDTO,GetUsername());
        }catch (Exception e){
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(str);
    }


    @MyLog(operation = "修改邀请码",database = "Project")
    @ApiOperation(value = "修改邀请码")
    @RequestMapping(value = "/changeCode", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAnyRole('ADMIN','GOVER','SYSTEM')")
    public ResponseEntity<Map<String,Object>> changeCode(@RequestBody ProjectChangeCodeDTO projectChangeCodeDTO){
        String str = null;
        try {
            str= projectService.changeCode(projectChangeCodeDTO,GetUsername());
        }catch (Exception e){
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(str);
    }


    @MyLog(operation = "对项目增加自定义模型",database = "Module,Module_User")
    @ApiOperation(value = "对项目增加自定义模型")
    @RequestMapping(value = "/addModule", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAnyRole('ADMIN','GOVER','SYSTEM')")
    public ResponseEntity<Map<String,Object>> addModule(@RequestBody ProjectModuleAddDTO projectModuleAddDTO){
        String str = null;
        try {
            str= projectService.addModule(projectModuleAddDTO,GetUsername());
        }catch (Exception e){
            System.err.println(e.getMessage());
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(str);
    }

    @MyLog(operation = "对项目增加固定模型",database = "Module,Module_User")
    @ApiOperation(value = "对项目增加固定模型")
    @RequestMapping(value = "/addDefaultModule", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAnyRole('ADMIN','GOVER','SYSTEM')")
    public ResponseEntity<Map<String,Object>> addDefaultModule(@RequestBody ProjectDefaultAddDTO projectDefaultAddDTO){
        String str = null;
        try {
            str= projectService.addDefaultModule(projectDefaultAddDTO,GetUsername());
        }catch (Exception e){
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(str);
    }

    @MyLog(operation = "查看项目情况",database = "Project,Project_User")
    @ApiOperation(value = "查看项目情况")
    @RequestMapping(value = "/getInfo/{projectID}", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAnyRole('ADMIN','GOVER','SYSTEM')")
    public ResponseEntity<Map<String,Object>> getInfo(@PathVariable int projectID){
        ProjectInfoDTO str = null;
        try {
            str= projectService.getInfo(projectID);
        }catch (Exception e){
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(str);
    }

    @MyLog(operation = "查看完成结束项目个数",database = "Project,Project_User")
    @ApiOperation(value = "查看完成结束项目个数")
    @RequestMapping(value = "/getIsEnd", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAnyRole('USER','ADMIN','GOVER','SYSTEM')")
    public ResponseEntity<Map<String,Object>> getIsEnd(){
        ProjectCompleteDTO str = null;
        try {
            str= projectService.getIsEnd(GetUsername());
        }catch (Exception e){
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(str);
    }

    @MyLog(operation = "查看创建的项目",database = "Project,Project_User")
    @ApiOperation(value = "查看创建的项目")
    @RequestMapping(value = "/getProjectCreate/{currindex}/{pagesize}", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAnyRole('ADMIN','GOVER','SYSTEM')")
    public ResponseEntity<Map<String,Object>> getProjectCreate(@PathVariable int currindex,@PathVariable int pagesize){
        ProjectListDTO str = null;
        try {
            str= projectService.getProject(GetUsername(),"creater",0,currindex-1,pagesize);
        }catch (Exception e){
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(str);
    }


    @MyLog(operation = "查看参与的项目",database = "Project,Project_User")
    @ApiOperation(value = "查看参与的项目")
    @RequestMapping(value = "/getProjectJoin/{currindex}/{pagesize}", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAnyRole('ADMIN','GOVER','SYSTEM')")
    public ResponseEntity<Map<String,Object>> getProjectJoin(@PathVariable int currindex,@PathVariable int pagesize){
        ProjectListDTO str = null;
        try {
            str= projectService.getProject(GetUsername(),"parter",0,currindex-1,pagesize);
        }catch (Exception e){
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(str);
    }

    @MyLog(operation = "查看旁观的项目",database = "Project,Project_User")
    @ApiOperation(value = "查看旁观的项目")
    @RequestMapping(value = "/getProjectWatch/{currindex}/{pagesize}", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAnyRole('USER','ADMIN','GOVER','SYSTEM')")
    public ResponseEntity<Map<String,Object>> getProjectWatch(@PathVariable int currindex,@PathVariable int pagesize){
        ProjectListDTO str = null;
        try {
            str= projectService.getProject(GetUsername(),"watcher",0,currindex-1,pagesize);
        }catch (Exception e){
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(str);
    }

    @MyLog(operation = "查看创建的固定项目",database = "Project,Project_User")
    @ApiOperation(value = "查看创建的固定项目")
    @RequestMapping(value = "/getProjectCreatedefault/{currindex}/{pagesize}", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAnyRole('ADMIN','GOVER','SYSTEM')")
    public ResponseEntity<Map<String,Object>> getProjectCreatedefault(@PathVariable int currindex,@PathVariable int pagesize){
        ProjectListDTO str = null;
        try {
            str= projectService.getProject(GetUsername(),"creater",1,currindex-1,pagesize);
        }catch (Exception e){
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(str);
    }


    @MyLog(operation = "查看参与的固定项目",database = "Project,Project_User")
    @ApiOperation(value = "查看参与的固定项目")
    @RequestMapping(value = "/getProjectJoindefault/{currindex}/{pagesize}", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAnyRole('ADMIN','GOVER','SYSTEM')")
    public ResponseEntity<Map<String,Object>> getProjectJoindefault(@PathVariable int currindex,@PathVariable int pagesize){
        ProjectListDTO str = null;
        try {
            str= projectService.getProject(GetUsername(),"parter",1,currindex-1,pagesize);
        }catch (Exception e){
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(str);
    }

    @MyLog(operation = "查看旁观的固定项目",database = "Project,Project_User")
    @ApiOperation(value = "查看旁观的固定项目")
    @RequestMapping(value = "/getProjectWatchdefault/{currindex}/{pagesize}", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAnyRole('USER','ADMIN','GOVER','SYSTEM')")
    public ResponseEntity<Map<String,Object>> getProjectWatchdefault(@PathVariable int currindex,@PathVariable int pagesize){
        ProjectListDTO str = null;
        try {
            str= projectService.getProject(GetUsername(),"watcher",1,currindex-1,pagesize);
        }catch (Exception e){
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(str);
    }

    @MyLog(operation = "查看全部创建的项目",database = "Project,Project_User")
    @ApiOperation(value = "查看全部创建的项目")
    @RequestMapping(value = "/getProjectCreate", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAnyRole('ADMIN','GOVER','SYSTEM')")
    public ResponseEntity<Map<String,Object>> getProjectCreateAll(){
        ProjectListDTO str = null;
        try {
            str= projectService.getProjectAll(GetUsername(),"creater",0);
        }catch (Exception e){
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(str);
    }


    @MyLog(operation = "查看全部参与的项目",database = "Project,Project_User")
    @ApiOperation(value = "查看全部参与的项目")
    @RequestMapping(value = "/getProjectJoin", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAnyRole('ADMIN','GOVER','SYSTEM')")
    public ResponseEntity<Map<String,Object>> getProjectJoinAll(){
        ProjectListDTO str = null;
        try {
            str= projectService.getProjectAll(GetUsername(),"parter",0);
        }catch (Exception e){
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(str);
    }

    @MyLog(operation = "查看全部旁观的项目",database = "Project,Project_User")
    @ApiOperation(value = "查看全部旁观的项目")
    @RequestMapping(value = "/getProjectWatch", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAnyRole('USER','ADMIN','GOVER','SYSTEM')")
    public ResponseEntity<Map<String,Object>> getProjectWatchAll(){
        ProjectListDTO str = null;
        try {
            str= projectService.getProjectAll(GetUsername(),"watcher",0);
        }catch (Exception e){
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(str);
    }

    @MyLog(operation = "查看全部创建的固定项目",database = "Project,Project_User")
    @ApiOperation(value = "查看全部创建的固定项目")
    @RequestMapping(value = "/getProjectCreatedefault", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAnyRole('ADMIN','GOVER','SYSTEM')")
    public ResponseEntity<Map<String,Object>> getProjectCreatedefaultAll(){
        ProjectListDTO str = null;
        try {
            str= projectService.getProjectAll(GetUsername(),"creater",1);
        }catch (Exception e){
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(str);
    }


    @MyLog(operation = "查看全部参与的固定项目",database = "Project,Project_User")
    @ApiOperation(value = "查看全部参与的固定项目")
    @RequestMapping(value = "/getProjectJoindefault", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAnyRole('ADMIN','GOVER','SYSTEM')")
    public ResponseEntity<Map<String,Object>> getProjectJoindefaultAll(){
        ProjectListDTO str = null;
        try {
            str= projectService.getProjectAll(GetUsername(),"parter",1);
        }catch (Exception e){
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(str);
    }

    @MyLog(operation = "查看全部旁观的固定项目",database = "Project,Project_User")
    @ApiOperation(value = "查看全部旁观的固定项目")
    @RequestMapping(value = "/getProjectWatchdefault", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAnyRole('USER','ADMIN','GOVER','SYSTEM')")
    public ResponseEntity<Map<String,Object>> getProjectWatchdefaultAll(){
        ProjectListDTO str = null;
        try {
            str= projectService.getProjectAll(GetUsername(),"watcher",1);
        }catch (Exception e){
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(str);
    }




    @MyLog(operation = "删除项目",database = "Project,Project_User")
    @ApiOperation(value = "删除项目")
    @RequestMapping(value = "/delete/{projectID}", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAnyRole('ADMIN','GOVER','SYSTEM')")
    public ResponseEntity<Map<String,Object>> delete(@PathVariable int projectID){
        String str = null;
        try {
            str= projectService.delete(GetUsername(),projectID);
        }catch (Exception e){
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(str);
    }

    @MyLog(operation = "根据项目ID请求模型",database = "Project")
    @ApiOperation(value = "根据项目ID请求模型")
    @RequestMapping(value = "/getModules/{projectID}", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAnyRole('ADMIN','GOVER','SYSTEM')")
    public ResponseEntity<Map<String,Object>> getModules(@PathVariable int projectID){
        ModuleListDTO str = null;
        try {
            str= projectService.getModules(projectID);
        }catch (Exception e){
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(str);
    }

    @MyLog(operation = "查看项目成员",database = "Project,Project_User")
    @ApiOperation(value = "查看项目成员")
    @RequestMapping(value = "/getMember/{projectID}", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAnyRole('ADMIN','GOVER','SYSTEM')")
    public ResponseEntity<Map<String,Object>> getMember(@PathVariable int projectID){
        List<ProjectMemberDTO> str = null;
        try {
            str= projectService.getProjectMember(projectID);
        }catch (Exception e){
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(str);
    }

    @MyLog(operation = "踢出项目成员",database = "Project,Project_User")
    @ApiOperation(value = "踢出项目成员")
    @RequestMapping(value = "/getMemberOut/{projectID}/{username}", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAnyRole('ADMIN','GOVER','SYSTEM')")
    public ResponseEntity<Map<String,Object>> getMemberOut(@PathVariable int projectID,@PathVariable String username){
        String str = null;
        try {
            str= projectService.getMemberOut(projectID,username,GetUsername());
        }catch (Exception e){
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(str);
    }

    @MyLog(operation = "创建了项目没继续的",database = "Project,Project_User,Module")
    @ApiOperation(value = "创建了项目没继续的")
    @RequestMapping(value = "/showProjectNoModule", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAnyRole('ADMIN','GOVER','SYSTEM')")
    public ResponseEntity<Map<String,Object>> showProjectNoModule(){
        List<ProjectNoModuleDTO> str = null;
        try {
            str= projectService.showProjectNoModule(GetUsername());
        }catch (Exception e){
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(str);
    }
}
