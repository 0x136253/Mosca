package com.mutiny.demo.controller;


import com.mutiny.demo.Service.ManagerService;
import com.mutiny.demo.api.CommonResult;
import com.mutiny.demo.api.MyLog;
import com.mutiny.demo.dto.LogModuleDTO;
import com.mutiny.demo.dto.ModuleDataInfoDTO;
import com.mutiny.demo.dto.ModuleSInfo;
import com.mutiny.demo.dto.UserLoginDTO;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Api(tags = "ManagerController", description = "后台管理")
@RequestMapping("/manager")
public class ManagerController {
    @Autowired
    private ManagerService managerService;
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

    @MyLog(operation = "模型调用情况",database = "User,Role")
    @ApiOperation(value = "模型调用情况")
    @RequestMapping(value = "/log/module", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAnyRole('SYSTEM')")
    public ResponseEntity<Map<String,Object>> logModule(){
        List<LogModuleDTO> answ = null;
        try {
            answ = managerService.getModuleLog();
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(answ);
    }

    @MyLog(operation = "模型详情",database = "User,Role")
    @ApiOperation(value = "模型详情")
    @RequestMapping(value = "/log/module/info/{moduleId}", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAnyRole('SYSTEM')")
    public ResponseEntity<Map<String,Object>> logModuleinfo(@PathVariable int moduleId){
        ModuleSInfo answ = null;
        try {
            answ = managerService.getModuleLogInfo(moduleId);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(answ);
    }

    @MyLog(operation = "模型数据详情",database = "User,Role")
    @ApiOperation(value = "模型数据详情")
    @RequestMapping(value = "/log/data", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAnyRole('SYSTEM')")
    public ResponseEntity<Map<String,Object>> logdatefo(){
        List<ModuleDataInfoDTO> answ = null;
        try {
            answ = managerService.getModuleDataInfo();
            for (ModuleDataInfoDTO record:answ){
                if (record.getFileURl()==null){
                    continue;
                }
                record.setFileURl("/image/"+pathHandle(record.getFileURl()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(answ);
    }


    public String pathHandle(String path){
        if (path.startsWith("D://temp-rainy//")){
            path =  path.substring(16,path.length());
        }
        else if (path.startsWith("/temp-rainy/")){
            path = path.substring(12,path.length());
        }
        System.out.println(path);
//        String system = System.getProperty("os.name");
        path=path.replaceAll("//","/");
        return path;
    }
}
