package com.mutiny.demo.controller;

import com.mutiny.demo.Service.AdminService;
import com.mutiny.demo.api.CommonResult;
import com.mutiny.demo.api.MyLog;
import com.mutiny.demo.dto.*;
import com.mutiny.demo.pojo.Company;
import com.mutiny.demo.pojo.Govern;
import com.mutiny.demo.pojo.User;
import com.mutiny.demo.util.JwtTokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
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
 * @Date: 2020/1/10 14:03
 */
@Controller
@Api(tags = "AccountController", description = "账户管理[非关键、待修改]")
@RequestMapping("/admin")
public class AccountController {

    @Autowired
    private AdminService adminService;
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

    @MyLog(operation = "登陆",database = "User,Role")
    @ApiOperation(value = "登录以后返回token")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Map<String,Object>> login(@RequestBody UserLoginDTO userLoginDTO){
        Map<String, String> tokenMap = new HashMap<>();
        String token = null;
        try {
            token = adminService.login(userLoginDTO.getUsername(),userLoginDTO.getPassword());
        } catch (Exception e) {
            return CommonResult.failed(e.getMessage());
        }
        tokenMap.put("token", token);
        return CommonResult.success(tokenMap);
    }

    @MyLog(operation = "获取个人信息",database = "User")
    @ApiOperation(value = "获取个人信息")
    @RequestMapping(value = "/showme", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAnyRole('USER','ADMIN','GOVER','SYSTEM')")
    public ResponseEntity<Map<String,Object>> showme(){
        User user = null;
        try {
            user= adminService.showme(GetUsername());
        }catch (Exception e){
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(user);
    }

    @MyLog(operation = "获取头像",database = "User")
    @ApiOperation(value = "获取头像")
    @RequestMapping(value = "/showPortrait", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAnyRole('USER','ADMIN','GOVER','SYSTEM')")
    public ResponseEntity<Map<String,Object>> showPortrait(){
        String user = null;
        try {
            user= adminService.showPortrait(GetUsername());
        }catch (Exception e){
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(user);
    }


    @MyLog(operation = "获取公司信息",database = "Comapny")
    @ApiOperation(value = "获取公司信息")
    @RequestMapping(value = "/showcompany/{companyid}", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAnyRole('USER','ADMIN','GOVER','SYSTEM')")
    public ResponseEntity<Map<String,Object>> showcompany(@PathVariable int companyid){
        Company company = null;
        try {
            company= adminService.showCompany(companyid);
        }catch (Exception e){
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(company);
    }

    @MyLog(operation = "获取政府信息",database = "Govern")
    @ApiOperation(value = "获取政府信息")
    @RequestMapping(value = "/showgovern/{governid}", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAnyRole('GOVER','SYSTEM')")
    public ResponseEntity<Map<String,Object>> showgovern(@PathVariable int governid){
        Govern govern = null;
        try {
            govern= adminService.showGovern(governid);
        }catch (Exception e){
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(govern);
    }

    @MyLog(operation = "企业管理员注册",database = "User,Role")
    @ApiOperation(value = "企业管理员注册")
    @RequestMapping(value = "/register/admin", method = RequestMethod.POST)
    @ResponseBody

    public ResponseEntity<Map<String,Object>> registerAdmin(@RequestBody UserRegisterAdminDTO userRegisterAdminDTO){
        User user = null;
        try {
            user = adminService.register_admin(userRegisterAdminDTO.ToUser(),userRegisterAdminDTO.ToCompany());
        } catch (Exception e) {
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(user);
    }

    @MyLog(operation = "政府管理员注册",database = "User,Role")
    @ApiOperation(value = "政府管理员注册")
    @RequestMapping(value = "/register/gover", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Map<String,Object>> registerGovern(@RequestBody UserRegisterGoverDTO userRegisterGoverDTO){
        User user = null;
        try {
            user = adminService.register_govern(userRegisterGoverDTO.ToUser(),userRegisterGoverDTO.ToGovern());
        } catch (Exception e) {
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(user);
    }

    @MyLog(operation = "员工注册",database = "User,Role")
    @ApiOperation(value = "员工注册")
    @RequestMapping(value = "/register/user", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Map<String,Object>> registerUser(@RequestBody UserRegisterUserDTO userRegisterUserDTO){
        User user = null;
        try {
            user = adminService.register_user(userRegisterUserDTO.ToUser(),userRegisterUserDTO.getCompany());
        } catch (Exception e) {
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(user);
    }

    @MyLog(operation = "获取待审核名单",database = "User")
    @ApiOperation(value = "获取待审核名单")
    @RequestMapping(value = "/getUserCheck", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAnyRole('SYSTEM')")
    public ResponseEntity<Map<String,Object>> getUserCheck(){
        List<UserCheckDTO> user = null;
        try {
            user = adminService.showUserNotPass();
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(user);
    }

    @MyLog(operation = "更改审核状态",database = "User")
    @ApiOperation(value = "更改审核状态(username为用户Id，pos为true则通过审核，为false则不通过审核)")
    @RequestMapping(value = "/changeUserCheck/{username}/{pos}", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAnyRole('SYSTEM')")
    public ResponseEntity<Map<String,Object>> changeUserCheck(@PathVariable String username,@PathVariable boolean pos){
        String user = null;
        try {
            user = adminService.changeUserCheck(username,pos);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(user);
    }

    @MyLog(operation = "修改密码",database = "User")
    @ApiOperation(value = "修改密码")
    @RequestMapping(value = "/updatePassWord", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAnyRole('USER','ADMIN','GOVER','SYSTEM')")
    public ResponseEntity<Map<String,Object>> updatePassWord(@RequestBody UpdatepasswordDTO updatepasswordDTO){
        String user = null;
        try {
            user = adminService.updatepassword(updatepasswordDTO,GetUsername());
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(user);
    }

    @MyLog(operation = "修改手机",database = "User")
    @ApiOperation(value = "修改手机")
    @RequestMapping(value = "/UpdateTel", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAnyRole('USER','ADMIN','GOVER','SYSTEM')")
    public ResponseEntity<Map<String,Object>> UpdateTel(@RequestBody UpdateTelDTO UpdateTelDTO){
        String user = null;
        try {
            user = adminService.updateTel(UpdateTelDTO,GetUsername());
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(user);
    }

    @MyLog(operation = "修改邮箱",database = "User")
    @ApiOperation(value = "修改邮箱")
    @RequestMapping(value = "/updateMail", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAnyRole('USER','ADMIN','GOVER','SYSTEM')")
    public ResponseEntity<Map<String,Object>> updateMail(@RequestBody UpdateMailDTO UpdateMailDTO){
        String user = null;
        try {
            user = adminService.updateMail(UpdateMailDTO,GetUsername());
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(user);
    }



    @MyLog(operation = "验证token",database = "")
    @ApiOperation(value = "验证token")
    @RequestMapping(value = "/check", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Map<String,Object>> Check(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //获取用户名
        String authHeader = request.getHeader(this.tokenHeader);
//        Map<String,Object> map = new HashMap<String,Object>();
//        map.put("data",null);
//        HttpStatus httpStatus = HttpStatus.OK;
        try {
            if (authHeader == null){
//                map.put("message","没有token");
//                httpStatus=HttpStatus.NOT_ACCEPTABLE;
                return CommonResult.validateFailed("没有token");
            }
            else if (jwtTokenUtil.validateToken(authHeader)){
//                map.put("message",jwtTokenUtil.refreshToken(authHeader));
//                httpStatus = HttpStatus.OK;
//                return CommonResult.success("success");
                return CommonResult.success(jwtTokenUtil.refreshToken(authHeader));
            }
            else {
//                map.put("message","fail");
//                httpStatus = HttpStatus.UNAUTHORIZED;
                return CommonResult.failed();
            }
        }catch (Exception e){
//            map.put("message",e.getMessage());
//            httpStatus = HttpStatus.UNAUTHORIZED;
            return CommonResult.failed(e.getMessage());
        }
//        return new ResponseEntity<Map<String,Object>>(map,httpStatus);
    }

//    @MyLog(operation = "刷新token",database = "")
//    @ApiOperation(value = "刷新token")
//    @RequestMapping(value = "/reflush", method = RequestMethod.GET)
//    @ResponseBody
//    public CommonResult reflush(){
//        return CommonResult.failed();
//    }
}
