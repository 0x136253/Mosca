package com.mutiny.demo.controller;

import com.mutiny.demo.Service.MessageService;
import com.mutiny.demo.api.CommonResult;
import com.mutiny.demo.api.MyLog;
import com.mutiny.demo.dto.*;
import com.mutiny.demo.message.MessageDeta;
import com.mutiny.demo.message.MessageInto;
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
 * @Date: 2020/2/12 15:31
 */
@Controller
@Api(tags = "MessageController", description = "消息管理")
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private MessageService messageService;
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

    @MyLog(operation = "发送Public消息", database = "Message_Info,Message_User")
    @ApiOperation(value = "发送Public消息")
    @RequestMapping(value = "/sendpublic", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasRole('SYSTEM')")
    public ResponseEntity<Map<String,Object>> SendPublic(@RequestBody MessageSendPublicDTO messageSendPublicDTO){
        String str = null;
        try {
            str= messageService.SendPublic(messageSendPublicDTO,GetUsername());
        }catch (Exception e){
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(str);
    }

    @MyLog(operation = "发送Group消息", database = "Message_Info,Message_User")
    @ApiOperation(value = "发送Group消息")
    @RequestMapping(value = "/sendsroup", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasRole('SYSTEM')")
    public ResponseEntity<Map<String,Object>> SendGroup(@RequestBody MessageSendGroupDTO messageSendGroupDTO){
        String str = null;
        try {
            str= messageService.SendGroup(messageSendGroupDTO,GetUsername());
        }catch (Exception e){
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(str);
    }

    @MyLog(operation = "发送private消息", database = "Message_Info,Message_User")
    @ApiOperation(value = "发送private消息")
    @RequestMapping(value = "/sendprivate", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasRole('SYSTEM')")
    public ResponseEntity<Map<String,Object>> SendPrivate(@RequestBody MessageSendPrivateDTO messageSendPrivateDTO){
        String str = null;
        try {
            str= messageService.SendPrivate(messageSendPrivateDTO,GetUsername());
        }catch (Exception e){
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(str);
    }


    @MyLog(operation = "获取消息概要", database = "Message_Send")
    @ApiOperation(value = "获取消息概要")
    @RequestMapping(value = "/getmessagesmall", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAnyRole('USER','ADMIN','GOVER','SYSTEM')")
    public ResponseEntity<Map<String,Object>> getMessageSmall(){
        List<MessageInto> str = null;
        try {
            str= messageService.getMessageSmall(GetUsername());
        }catch (Exception e){
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(str);
    }

    @MyLog(operation = "获取消息详情", database = "Message_Send")
    @ApiOperation(value = "获取消息详情")
    @RequestMapping(value = "/getmessagebig/{messageId}", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAnyRole('USER','ADMIN','GOVER','SYSTEM')")
    public ResponseEntity<Map<String,Object>> getMessageBig(@PathVariable int messageId){
        MessageDeta str = null;
        try {
            str= messageService.getMessageBig(GetUsername(),messageId);
        }catch (Exception e){
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(str);
    }

    @MyLog(operation = "删除消息", database = "Message_Send")
    @ApiOperation(value = "删除消息")
    @RequestMapping(value = "/deleteMessage/{messageId}", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAnyRole('USER','ADMIN','GOVER','SYSTEM')")
    public ResponseEntity<Map<String,Object>> deleteMessage(@PathVariable int messageId){
        boolean str = false;
        try {
            str= messageService.deleteMessage(GetUsername(),messageId);
        }catch (Exception e){
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(str);
    }

    @MyLog(operation = "根据title返回消息数量", database = "Message_Send")
    @ApiOperation(value = "根据title返回消息数量")
    @RequestMapping(value = "/getMessageNumByTitle/{title}", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAnyRole('USER','ADMIN','GOVER','SYSTEM')")
    public ResponseEntity<Map<String,Object>> getMessageNumByTitle(@PathVariable String title){
        Map<String,Object> str = null;
        try {
            str= messageService.getMessageNumByTitle(GetUsername(),title);
        }catch (Exception e){
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(str);
    }

    @MyLog(operation = "根据title返回消息概要", database = "Message_Send")
    @ApiOperation(value = "根据title返回消息概要")
    @RequestMapping(value = "/getMessageSmallByTitle/{title}", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAnyRole('USER','ADMIN','GOVER','SYSTEM')")
    public ResponseEntity<Map<String,Object>> getMessageSmallByTitle(@PathVariable String title){
        List<MessageInto> str = null;
        try {
            str= messageService.getMessageSmallByTitle(GetUsername(),title);
        }catch (Exception e){
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(str);
    }
}
