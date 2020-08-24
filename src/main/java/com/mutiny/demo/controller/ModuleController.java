package com.mutiny.demo.controller;

import com.mutiny.demo.Service.ModuleService;
import com.mutiny.demo.api.CommonResult;
import com.mutiny.demo.api.MyLog;
import com.mutiny.demo.dto.*;
import com.mutiny.demo.pojo.DefaultData;
import com.mutiny.demo.pojo.DefaultModule;
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
 * @Date: 2020/1/10 14:05
 */
@Controller
@Api(tags = "ModuleController", description = "模型管理")
@RequestMapping("/module")
public class ModuleController {
    @Autowired
    private ModuleService moduleService;
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

    @MyLog(operation = "增加固定模型数据源", database = "DefaultData")
    @ApiOperation(value = "增加固定模型数据源")
    @RequestMapping(value = "/addefaultData", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAnyRole('GOVER','SYSTEM')")
    public ResponseEntity<Map<String,Object>> addefaultData(@RequestBody ModuleDefaultDataAddDTO moduleDefaultDataAddDTO){
        String str = null;
        try {
            str= moduleService.addefaultData(moduleDefaultDataAddDTO,GetUsername());
        }catch (Exception e){
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(str);
    }


    @MyLog(operation = "查看政府数据源list", database = "DefaultData")
    @ApiOperation(value = "查看政府数据源list")
    @RequestMapping(value = "/showdefaultData", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAnyRole('GOVER','SYSTEM')")
    public ResponseEntity<Map<String,Object>> showDefaultData(){
        List<ShowDefaultDataDTO> str = null;
        try {
            str= moduleService.showDefaultData(GetUsername());
        }catch (Exception e){
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(str);
    }

    @MyLog(operation = "返回固定模型对应数据源list", database = "DefaultData")
    @ApiOperation(value = "返回固定模型对应数据源list")
    @RequestMapping(value = "/defaultModuleData/{defaultID}", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAnyRole('ADMIN','GOVER','SYSTEM')")
    public ResponseEntity<Map<String,Object>> defaultModuleData(@PathVariable int defaultID){
        List<DefaultData> defaultDatas = null;
        try {
            defaultDatas= moduleService.defaultModuleData(defaultID);
        }catch (Exception e){
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(defaultDatas);
    }



    @MyLog(operation = "返回全部固定模型信息", database = "DefaultData")
    @ApiOperation(value = "返回全部固定模型信息")
    @RequestMapping(value = "/defaultModule", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAnyRole('ADMIN','GOVER','SYSTEM')")
    public ResponseEntity<Map<String,Object>> defaultModule(){
        List<DefaultModuleDTO> defaultModules = null;
        try {
            defaultModules= moduleService.defaultModule();
        }catch (Exception e){
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(defaultModules);
    }

    @MyLog(operation = "返回单个固定模型信息", database = "DefaultData")
    @ApiOperation(value = "返回单个固定模型信息")
    @RequestMapping(value = "/defaultModule/{DefaultID}", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAnyRole('ADMIN','GOVER','SYSTEM')")
    public ResponseEntity<Map<String,Object>> defaultModule(@PathVariable int DefaultID){
        DefaultModuleDTO defaultModules = null;
        try {
            defaultModules= moduleService.defaultModule(DefaultID);
        }catch (Exception e){
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(defaultModules);
    }


    @MyLog(operation = "删除模型", database = "Module")
    @ApiOperation(value = "删除模型")
    @RequestMapping(value = "/delete/{ModuleID}", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAnyRole('ADMIN','GOVER','SYSTEM')")
    public ResponseEntity<Map<String,Object>> delete(@PathVariable int ModuleID) {
        String str = null;
        try {
            str = moduleService.delete(ModuleID,GetUsername());
        } catch (Exception e) {
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(str);
    }

    @MyLog(operation = "删除固定模型", database = "DefauleModule")
    @ApiOperation(value = "删除固定模型")
    @RequestMapping(value = "/deletedefaultmodule/{defaultModuleId}", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasRole('SYSTEM')")
    public ResponseEntity<Map<String,Object>> deleteDefaultModule(@PathVariable int defaultModuleId) {
        String str = null;
        try {
            str = moduleService.deleteDefaultModule(defaultModuleId,GetUsername());
        } catch (Exception e) {
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(str);
    }

    @MyLog(operation = "删除固定模型数据源", database = "DefaultData")
    @ApiOperation(value = "删除固定模型数据源")
    @RequestMapping(value = "/deletedefaultdata/{defaultDataId}", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAnyRole('GOVER','SYSTEM')")
    public ResponseEntity<Map<String,Object>> deleteDefaultData(@PathVariable int defaultDataId) {
        String str = null;
        try {
            str = moduleService.deleteDefaultData(defaultDataId,GetUsername());
        } catch (Exception e) {
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(str);
    }

    @MyLog(operation = "上传全新的模型", database = "Module")
    @ApiOperation(value = "上传全新的模型")
    @RequestMapping(value = "/addDefualt", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasRole('SYSTEM')")
    public ResponseEntity<Map<String,Object>> addDefualt(@RequestBody DefaultModuleAddDTO defaultModuleAddDTO){
        String str = null;
        try {
            str= moduleService.addDefualt(defaultModuleAddDTO);
        }catch (Exception e){
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(str);
    }

    @MyLog(operation = "自定义模型请求密钥", database = "Module")
    @ApiOperation(value = "自定义模型请求密钥")
    @RequestMapping(value = "/applykeymodule/{moduleId}", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAnyRole('ADMIN','GOVER','SYSTEM')")
    public ResponseEntity<Map<String,Object>> applyKeyDefualt(@PathVariable int moduleId){
        KeyDTO str = null;
        try {
            str= moduleService.returnKey(moduleId,false);
        }catch (Exception e){
            e.printStackTrace();
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(str);
    }

    @MyLog(operation = "固定模型请求密钥", database = "Module")
    @ApiOperation(value = "固定模型请求密钥")
    @RequestMapping(value = "/applykeydefault/{defaultDataId}", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAnyRole('ADMIN','GOVER','SYSTEM')")
    public ResponseEntity<Map<String,Object>> applyKeyModule(@PathVariable int defaultDataId){
        KeyDTO str = null;
        try {
            str= moduleService.returnKey(defaultDataId,true);
        }catch (Exception e){
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(str);
    }

    @MyLog(operation = "根据ModuleId请求模型详情", database = "Module")
    @ApiOperation(value = "根据ModuleId请求模型详情(自定义模型)")
    @RequestMapping(value = "/getModule/{ModuleId}", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAnyRole('ADMIN','GOVER','SYSTEM')")
    public ResponseEntity<Map<String,Object>> getModule(@PathVariable int ModuleId){
        ModuleInfoDTO str = null;
        try {
            str= moduleService.getModule(ModuleId);
        }catch (Exception e){
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(str);
    }

    @MyLog(operation = "返回function中的变量名(自定义模型)", database = "Module")
    @ApiOperation(value = "返回function中的变量名(自定义模型)")
    @RequestMapping(value = "/getfunctions/{ModuleId}", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAnyRole('ADMIN','GOVER','SYSTEM')")
    public ResponseEntity<Map<String,Object>> getfunctions(@PathVariable int ModuleId){
        List<String> str = null;
        try {
            str= moduleService.getfunctions(ModuleId);
        }catch (Exception e){
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(str);
    }

    @MyLog(operation = "返回function中的变量名(固定模型)", database = "Default_module")
    @ApiOperation(value = "返回function中的变量名(固定模型)")
    @RequestMapping(value = "/getfunctionsDefault/{DefaultId}", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAnyRole('ADMIN','GOVER','SYSTEM')")
    public ResponseEntity<Map<String,Object>> getfunctionsDefault(@PathVariable int DefaultId){
        List<String> str = null;
        try {
            str= moduleService.getfunctionsDefault(DefaultId);
        }catch (Exception e){
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(str);
    }

    @MyLog(operation = "返回固定模型数据源信息", database = "Module")
    @ApiOperation(value = "返回固定模型数据源信息")
    @RequestMapping(value = "/getDefaultDataInfo/{defaultDateId}", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAnyRole('ADMIN','GOVER','SYSTEM')")
    public ResponseEntity<Map<String,Object>> getDefaultDataInfo(@PathVariable int defaultDateId){
        DefaultData str = null;
        try {
            str= moduleService.getDefaultDataInfo(defaultDateId);
        }catch (Exception e){
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(str);
    }

    @MyLog(operation = "返回计算完成的module信息", database = "Module")
    @ApiOperation(value = "返回计算完成的moudle信息")
    @RequestMapping(value = "/getCompleteModuleInfo", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAnyRole('ADMIN','GOVER','SYSTEM')")
    public ResponseEntity<Map<String,Object>> getCompleteModuleInfo(){
        List<ModuleInfoDTO> str = null;
        try {
            str= moduleService.getCompleteModuleInfo(GetUsername());
        }catch (Exception e){
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(str);
    }

    @MyLog(operation = "返回固定模型管理所需数据", database = "Module")
    @ApiOperation(value = "返回固定模型管理所需数据")
    @RequestMapping(value = "/getDefaultManager", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAnyRole('SYSTEM')")
    public ResponseEntity<Map<String,Object>> getDefaultManager(){
        List<DefaultModuleManagerDTO> str = null;
        try {
            str= moduleService.getDefaultManager();
        }catch (Exception e){
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(str);
    }

    @MyLog(operation = "更改固定模型状态", database = "Module")
    @ApiOperation(value = "更改固定模型状态(DefaultId 为 固定模型ID，pos位true则部署模型，为false则卸载模型)")
    @RequestMapping(value = "/changeDefaultManager/{DefaultId}/{pos}", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAnyRole('SYSTEM')")
    public ResponseEntity<Map<String,Object>> changeDefaultManager(@PathVariable int DefaultId,@PathVariable boolean pos){
        String str = null;
        try {
            str= moduleService.changeDefaultManager(DefaultId,pos);
        }catch (Exception e){
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(str);
    }
}
