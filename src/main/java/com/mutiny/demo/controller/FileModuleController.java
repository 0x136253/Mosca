package com.mutiny.demo.controller;

import com.mutiny.demo.Service.FileModuleService;
import com.mutiny.demo.api.CommonResult;
import com.mutiny.demo.api.MyLog;
import com.mutiny.demo.dto.*;
import com.mutiny.demo.message.MessageSender;
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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @Author: Anonsmd
 * @Date: 2020/1/10 16:12
 */
@Controller
@Api(tags = "FileModuleController", description = "数据文件管理")
@RequestMapping("/file/module")
public class FileModuleController {
    @Autowired
    private FileModuleService fileModuleService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    /**
     * 如果此处上传为固定模型数据，则返回FileID，在增加数据对应关系时带上
     * @param
     * @return
     */
//    @MyLog(operation = "上传数据文件",database = "File_Module",flag = false)
    @MyLog(operation = "上传数据文件",database = "File_Module_Temp")
    @ApiOperation(value = "上传数据文件")
    @PostMapping(value = "/upload")
    @ResponseBody
    @PreAuthorize("hasAnyRole('ADMIN','GOVER','SYSTEM')")
    public ResponseEntity<Map<String,Object>> uploadData(@RequestParam(value = "file") MultipartFile file,@RequestParam(value = "moduleId") int moduleId,@RequestParam(value = "isDefault") boolean isDefault){
        String str = null;
        try {
            str= fileModuleService.uploadData(file,moduleId,isDefault,GetUsername());
        }catch (Exception e){
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(str);
    }

    @MyLog(operation = "删除文件",database = "File_Module_Temp")
    @ApiOperation(value = "删除文件")
    @GetMapping(value = "/delete/{fileid}")
    @ResponseBody
    @PreAuthorize("hasAnyRole('ADMIN','GOVER','SYSTEM')")
    public ResponseEntity<Map<String,Object>> delete(@PathVariable int fileid){
        String str = null;
        try {
            str= fileModuleService.delete(GetUsername(),fileid);
        }catch (Exception e){
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(str);
    }

    /**
     * 出于安全性考虑，可能需要修改....
     * @param moduleid
     * @return
     */
    @MyLog(operation = "显示自定义模型数据上传文件",database = "File_Module_Temp")
    @ApiOperation(value = "显示自定义模型数据上传文件")
    @GetMapping(value = "/show/{moduleid}")
    @ResponseBody
    @PreAuthorize("hasAnyRole('ADMIN','GOVER','SYSTEM')")
    public ResponseEntity<Map<String,Object>> show(@PathVariable int moduleid){
        FileModuleTempShowListDTO str = null;
        try {
            str= fileModuleService.show(moduleid,false);
        }catch (Exception e){
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(str);
    }

    /**
     * 出于安全性考虑，可能需要修改....
     * @param DefaultDataID
     * @return
     */
    @MyLog(operation = "显示固定模型数据上传文件",database = "File_Module_Temp")
    @ApiOperation(value = "显示固定模型数据上传文件")
    @GetMapping(value = "/showDefault/{DefaultDataID}")
    @ResponseBody
    @PreAuthorize("hasAnyRole('GOVER','SYSTEM')")
    public ResponseEntity<Map<String,Object>> showDefault(@PathVariable int DefaultDataID){
        FileModuleTempShowListDTO str = null;
        try {
            str= fileModuleService.show(DefaultDataID,true);
        }catch (Exception e){
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(str);
//        return CommonResult.success(fileModuleService.show(DefaultDataID,true));
    }

    @MyLog(operation = "显示自定义模型数据上传所需数据",database = "Module")
    @ApiOperation(value = "显示自定义模型数据上传所需数据")
    @GetMapping(value = "/showModuleNeed/{moduleid}")
    @ResponseBody
    @PreAuthorize("hasAnyRole('ADMIN','GOVER','SYSTEM')")
    public ResponseEntity<Map<String,Object>> showModuleNeed(@PathVariable int moduleid){
        List<ModuleUploadInfoDTO> str = null;
        try {
            str= fileModuleService.showUploadNeed(GetUsername(),moduleid,false);
        }catch (Exception e){
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(str);
    }

    /**
     * 出于安全性考虑，可能需要修改....
     * @param DefaultDataID
     * @return
     */
    @MyLog(operation = "显示固定模型数据所需数据",database = "DefaultModulem,DefaultData")
    @ApiOperation(value = "显示固定模型数据所需数据")
    @GetMapping(value = "/showDefaultNeed/{DefaultDataID}")
    @ResponseBody
    @PreAuthorize("hasAnyRole('GOVER','SYSTEM')")
    public ResponseEntity<Map<String,Object>> showDefaultNeed(@PathVariable int DefaultDataID){
        List<ModuleUploadInfoDTO> str = null;
        try {
            str= fileModuleService.showUploadNeed(GetUsername(),DefaultDataID,true);
        }catch (Exception e){
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(str);
//        return CommonResult.success(fileModuleService.show(DefaultDataID,true));
    }


    @MyLog(operation = "获取数据源状态，政府",database = "DefaultModulem,DefaultData")
    @ApiOperation(value = "获取数据源状态，政府")
    @GetMapping(value = "/showDefaultStatus")
    @ResponseBody
    @PreAuthorize("hasAnyRole('GOVER','SYSTEM')")
    public ResponseEntity<Map<String,Object>> showDefaultStatus(){
        List<DefaultDataStatusDTO> str = null;
        try {
            str= fileModuleService.showDefaultDataStatus(GetUsername());
        }catch (Exception e){
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(str);
//        return CommonResult.success(fileModuleService.show(DefaultDataID,true));
    }



    @MyLog(operation = "获取数据源状态，企业",database = "DefaultModulem,DefaultData")
    @ApiOperation(value = "获取数据源状态，企业")
    @GetMapping(value = "/showModuleStatus")
    @ResponseBody
    @PreAuthorize("hasAnyRole('GOVER','ADMIN','SYSTEM')")
    public ResponseEntity<Map<String,Object>> showModuleStatus(){
        List<ModuleStatusDTO> str = null;
        try {
            str= fileModuleService.showModuleStatus(GetUsername());
        }catch (Exception e){
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(str);
//        return CommonResult.success(fileModuleService.show(DefaultDataID,true));
    }


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
}
