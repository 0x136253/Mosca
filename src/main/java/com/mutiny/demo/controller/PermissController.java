package com.mutiny.demo.controller;

import com.mutiny.demo.Service.PermissService;
import com.mutiny.demo.api.CommonResult;
import com.mutiny.demo.api.MyLog;
import com.mutiny.demo.dto.PermissRequestDTO;
import com.mutiny.demo.dto.PermissionApplyDTO;
import com.mutiny.demo.dto.ProjectListDTO;
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
 * @Date: 2020/1/11 10:08
 */
@CrossOrigin
@Controller
@Api(tags = "PermissController", description = "权限管理")
@RequestMapping("/permiss")
public class PermissController {

    @Autowired
    private PermissService permissService;
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

    /**
     * 企业职工不可加入
     * @param InviteCode
     * @return
     */
    @MyLog(operation = "参与者加入", database = "Module_User")
    @ApiOperation(value = "参与者加入")
    @RequestMapping(value = "/partJoin", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAnyRole('ADMIN','GOVER','SYSTEM')")
    public ResponseEntity<Map<String,Object>> partJoin(@RequestBody String InviteCode){
//        return CommonResult.success("加入成功/加入失败");
        String str = null;
        try {
            str= permissService.partJoin(InviteCode,GetUsername());
        }catch (Exception e){
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(str);
    }

    @MyLog(operation = "旁观者加入", database = "Module_User")
    @ApiOperation(value = "旁观者加入")
    @RequestMapping(value = "/watchJoin", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAnyRole('USER','ADMIN','GOVER','SYSTEM')")
    public ResponseEntity<Map<String,Object>> watchJoin(@RequestBody String InviteCode){
        String str = null;
        try {
            str= permissService.watchJoin(InviteCode,GetUsername());
        }catch (Exception e){
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(str);
//        return CommonResult.success("加入成功/加入失败");
    }



    @MyLog(operation = "请求政府授权", database = "Module_User")
    @ApiOperation(value = "请求政府授权")
    @RequestMapping(value = "/govePermissRequest", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAnyRole('ADMIN','GOVER','SYSTEM')")
    public ResponseEntity<Map<String,Object>> govePermissRequest(@RequestBody PermissRequestDTO permissRequestDTO){
        String str = null;
        try {
            str= permissService.govePermissRequest(permissRequestDTO,GetUsername());
        }catch (Exception e){
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(str);
//        return CommonResult.success("请求成功/请求失败");
    }


    /**
     * 此处是否可以返回该公司请求了多少数据，作为警告提醒
     * @param
     * @return
     */
    @MyLog(operation = "查看请求授权list", database = "Module_User")
    @ApiOperation(value = "查看请求授权list")
    @RequestMapping(value = "/showPermissGet", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAnyRole('GOVER','SYSTEM')")
    public ResponseEntity<Map<String,Object>> showPermissGet(){
        List<PermissionApplyDTO> str = null;
        try {
            str= permissService.showPermissGet(GetUsername());
        }catch (Exception e){
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(str);
//        return CommonResult.success(new ProjectListDTO());
    }

    @MyLog(operation = "授权Api", database = "Module_User")
    @ApiOperation(value = "授权Api")
    @RequestMapping(value = "/pushAPi/{ID}/{status}", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAnyRole('GOVER','SYSTEM')")
    public ResponseEntity<Map<String,Object>> PermissPush(@PathVariable int ID,@PathVariable int status){
        String str = null;
        try {
            str= permissService.pushPermiss(ID,status,GetUsername());
        }catch (Exception e){
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(str);
//        return CommonResult.success(new ProjectListDTO());
    }
}
