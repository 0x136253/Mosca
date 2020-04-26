package com.mutiny.demo.controller;

import com.mutiny.demo.Service.CalculateService;
import com.mutiny.demo.Service.ModuleService;
import com.mutiny.demo.api.CommonResult;
import com.mutiny.demo.api.MyCalLog;
import com.mutiny.demo.api.MyLog;
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

/**
 * @Author: Anonsmd
 * @Date: 2020/2/10 12:20
 */
@Controller
@Api(tags = "CalculateController", description = "计算管理")
@RequestMapping("/calculate")
public class CalculateController {
    @Autowired
    private CalculateService calculateService;
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

    @MyCalLog(Type = "cal")
    @ApiOperation(value = "计算")
    @RequestMapping(value = "/cal/{ModuleID}", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAnyRole('ADMIN','GOVER','SYSTEM')")
    public ResponseEntity<Map<String,Object>> cal(@PathVariable int ModuleID){
        String answ = null;
        try {
            answ = calculateService.calModule(ModuleID,GetUsername());
        } catch (Exception e) {
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(answ);
    }

    @MyCalLog(Type = "cal")
    @ApiOperation(value = "计算固定模型")
    @RequestMapping(value = "/caldefault/{defaultDataId}", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAnyRole('GOVER','SYSTEM')")
    public ResponseEntity<Map<String,Object>> calDefault(@PathVariable int defaultDataId){
        String answ = null;
        try {
            answ = calculateService.calDefaultModule(defaultDataId,GetUsername());
        } catch (Exception e) {
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(answ);
    }

    @MyCalLog(Type = "answ")
    @ApiOperation(value = "查看结果")
    @RequestMapping(value = "/shouAnsw/{ModuleID}", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAnyRole('ADMIN','GOVER','SYSTEM')")
    public ResponseEntity<Map<String,Object>> shouAnsw(@PathVariable int ModuleID){
        List<Map<String,String>> str = null;
        try {
            str= moduleService.shouAnsw(ModuleID,GetUsername());
        }catch (Exception e){
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(str);
    }
}
