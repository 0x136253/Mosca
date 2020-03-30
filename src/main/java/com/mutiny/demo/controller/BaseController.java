package com.mutiny.demo.controller;

import com.mutiny.demo.api.CommonResult;
import com.mutiny.demo.dto.UserLoginDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Anonsmd
 * @Date: 2020/1/16 17:43
 */
@Controller
@Api(tags = "BaseController", description = "Base")
@RequestMapping("/Base")
public class BaseController {
    @ApiOperation(value = "ADMIN")
    @RequestMapping(value = "/ADMIN", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAnyRole('ADMIN','SYSTEM')")
    public ResponseEntity<Map<String,Object>> ADMIN(){
        return CommonResult.success("ADMIN");
    }

    @ApiOperation(value = "ROOT")
    @RequestMapping(value = "/ROOT", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasRole('ROOT')")
    public ResponseEntity<Map<String,Object>> ROOT(){
        return CommonResult.success("ROOT");
    }
    @ApiOperation(value = "SYSTEM")
    @RequestMapping(value = "/SYSTEM", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasRole('SYSTEM')")
    public ResponseEntity<Map<String,Object>> SYSTEM(){
        return CommonResult.success("SYSTEM");
    }
    @ApiOperation(value = "USER")
    @RequestMapping(value = "/USER", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Map<String,Object>> USER(){
        return CommonResult.success("USER");
    }
}
