package com.mutiny.demo.controller;

import com.mutiny.demo.Service.CalculateLogService;
import com.mutiny.demo.api.CommonResult;
import com.mutiny.demo.dto.CalculateShowModuleDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @Author: Anonsmd
 * @Date: 2020/1/10 16:12
 */
@Controller
@Api(tags = "CalculateLogController", description = "计算日志管理")
@RequestMapping("/calculatelog")
public class CalculateLogController {
    @Autowired
    private CalculateLogService calculateLogService;

    @ApiOperation(value = "计算调用情况[仅测试]")
    @RequestMapping(value = "/showcal/{day}", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAnyRole('ADMIN','GOVER','SYSTEM')")
    public ResponseEntity<Map<String,Object>> showcal(@PathVariable int day){
        List<CalculateShowModuleDTO> answ = null;
        try {
            answ = calculateLogService.showDefaultAnswAll(day,"answ");
        } catch (Exception e) {
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(answ);
    }
}
