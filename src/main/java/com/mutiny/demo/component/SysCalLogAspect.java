package com.mutiny.demo.component;

import com.mutiny.demo.api.MyCalLog;
import com.mutiny.demo.api.MyLog;
import com.mutiny.demo.dao.DefaultDataMapper;
import com.mutiny.demo.dao.LogCalculateMapper;
import com.mutiny.demo.dao.LogMapper;
import com.mutiny.demo.dao.ModuleMapper;
import com.mutiny.demo.pojo.DefaultData;
import com.mutiny.demo.pojo.Log;
import com.mutiny.demo.pojo.LogCalculate;
import com.mutiny.demo.pojo.Module;
import com.mutiny.demo.util.IPUtils;
import com.mutiny.demo.util.JwtTokenUtil;
import com.mutiny.demo.util.ObjectUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统日志：切面处理类
 */
@Aspect
@Component
public class SysCalLogAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);
    @Autowired
    private ObjectUtils objectUtils;
    @Autowired
    private ModuleMapper moduleMapper;
    @Autowired
    private LogCalculateMapper logDAO;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Pointcut("@annotation( com.mutiny.demo.api.MyCalLog)")
    public void logPointCut(){
    }

    @AfterReturning("logPointCut()")
    public void saveSysLogs(JoinPoint joinPoint){
        LOGGER.info(">>>>>>>>>>>>>>>>>>>>>>>Callogs");
//        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>logs");
        LogCalculate sysLog = new LogCalculate();

        //从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取切入点所在的方法
        Method method = signature.getMethod();

        //获取操作
        MyCalLog myLog = method.getAnnotation(MyCalLog.class);
        boolean isDefault =false;
        if (myLog != null) {
            String type = myLog.Type();
            isDefault = myLog.IsDefault();
            sysLog.setType(type);//保存获取的操作
        }
        //保存所有请求参数，用于输出到日志中
        Object[] paramValues = joinPoint.getArgs();
        String[] paramNames = ((CodeSignature) joinPoint.getSignature()).getParameterNames();
        int moduleID = 0;
        for (int i=0;i<paramNames.length;i++){
            if (paramNames[i].equals("ModuleID")){
                moduleID = (Integer) paramValues[i];
            }
        }
        sysLog.setModuleid(moduleID);
        sysLog.setIsDefault(isDefault);
        Module module = moduleMapper.selectByPrimaryKey(moduleID);
        if (module!=null && module.getIsDefault()){
            sysLog.setIsDefault(module.getIsDefault());
            sysLog.setModuleid(module.getDefaultmoduleId());
        }
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //获取用户名
        String authHeader = request.getHeader(this.tokenHeader);
        if (authHeader != null) {
            String authToken = authHeader;
            String username = jwtTokenUtil.getUserNameFromToken(authToken);
            sysLog.setUserId(username);
        }
        //获取用户ip地址
        try {
            sysLog.setIp(IPUtils.getIpAddress(request));
            sysLog.setIp(request.getRemoteAddr());
        } catch (IOException e) {
            e.printStackTrace();
        }
        logDAO.insertSelective(sysLog);
        LOGGER.info("logs<<<<<<<<<<<<<<<<<<<<<<<");
//        System.out.println("logs<<<<<<<<<<<<<<<<<<<<<<<");
    }
}


