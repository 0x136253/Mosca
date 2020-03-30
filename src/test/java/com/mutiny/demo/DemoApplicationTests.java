package com.mutiny.demo;

import com.alibaba.fastjson.JSONObject;
import com.mutiny.demo.FHE.EncryptDecrypt;
import com.mutiny.demo.FHE.Key;
import com.mutiny.demo.FHE.KeyGeneration;
import com.mutiny.demo.FHE.RecursiveDescent;
import com.mutiny.demo.Service.Impl.ModuleServiceImpl;
import com.mutiny.demo.component.KeyFileComponent;
import com.mutiny.demo.component.MessageRabbitMqSender;
import com.mutiny.demo.dao.*;
import com.mutiny.demo.dto.AdminUserDetails;
import com.mutiny.demo.pojo.*;
import com.mutiny.demo.util.GenerateTestUtils;
import com.mutiny.demo.util.JwtTokenUtil;
import com.mutiny.demo.util.RecursiveDescentUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigInteger;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {
    @Autowired
    private KeyFileComponent keyFileComponent;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ProjectMapper projectMapper;
    @Autowired
    private FileModuleTempMapper fileModuleTempMapper;
    @Autowired
    private ModuleMapper moduleMapper;
    @Autowired
    private DefaultDataMapper defaultDataMapper;
    @Autowired
    private DefaultModuleMapper defaultModuleMapper;
    @Autowired
    private KeyFileMapper keyFileMapper;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private ModuleServiceImpl moduleService;
    @Autowired
    private MessageRabbitMqSender helloSender;
//    @Autowired
//    private MessageReceiver helloReceiver = new MessageReceiver();
//    @Autowired
//    private MessageReceiver helloReceiver2= new MessageReceiver();
    @Test
    public void contextLoads() {
//        System.out.println(roleMapper.SelectByUserID("123456789"));
        System.out.println(fileModuleTempMapper.GetSumByModuleId(5));
    }

    @Test
    public void Test2(){
//        System.out.println(projectMapper.GetProjectList("123456789","creater",0,1));
    }

    @Test
    public void Test(){
        System.out.println(userMapper.selectByPrimaryKey("123456789"));
    }

    @Test
    public void Test1(){
        System.out.println(projectMapper.GetIsEndCount("123456789","creater",1));
    }


    @Test
    public void Test2s(){
        User user = userMapper.selectByPrimaryKey("Anon");
        RoleExample roleExample=new RoleExample();
        roleExample.createCriteria().andUserIdEqualTo(user.getId());
        List<Role> roles=roleMapper.selectByExample(roleExample);
        AdminUserDetails userDetails = new AdminUserDetails(user,roles);
        String token=jwtTokenUtil.generateToken(userDetails);
//        token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjpbeyJyb2xlSWQiOjcsInVzZXJJZCI6IkFub24iLCJyb2xlTmFtZSI6IuS8geS4muiBjOWRmCIsInJvbGVUeXBlIjoiUk9MRV9VU0VSIiwiY3JlYXRlVGltZSI6MTU3OTM0MTM1MjAwMCwiZGVzY3JpcHRpb24iOiLkvIHkuJrmma7pgJrlt6XkvZzkurrlkZgiLCJpc1VzZXJmdWwiOnRydWV9XSwiaWQiOiJBbm9uIiwiZXhwIjoxNTgwNjIxODMzMDAwfQ.Nyg-tu20tAXAKF9A6O0JROL4-J0vi5Zbuo0TR9Xbbic";
//        jwtTokenUtil.validateToken(token);
        System.out.println(jwtTokenUtil.getExpiredDateFromToken(token));
        System.out.println(jwtTokenUtil.validateToken(token));
    }

    @Test
    public void re(){
//        MessageReceiver messageReceiver = new MessageReceiver();
        for (int i=0;i<20;i++){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("email", "wangsheng@163.com");
            jsonObject.put("code", i);
            jsonObject.put("timestamp", System.currentTimeMillis());
            try {
                helloSender.send(jsonObject,"MoscaDirectExchange","MoscaDirectRouting");
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
//        helloReceiver.process();
//        System.out.println(moduleService.getIdentifiy("x+y").toString());
//        System.out.println(moduleService.getIdentifiy("x^z+yx*yh").toString());
    }

    @Test
    public void Test_Cal(){
        Key key = KeyGeneration.keyGeneration(128);
        String func = "x*y+c+z";
//        String func = "c+z";
        Random random = new Random(System.currentTimeMillis());
        for (int i=0;i<100;i++){
            int x = random.nextInt(1000);
            int y = random.nextInt(1000);
            int c = random.nextInt(1000);
            int z = random.nextInt(1000);
            int answ = x*y+c+z;
//            int answ = c+z;
            Map<Character, BigInteger> map = new HashMap<>();
            map.put('x', EncryptDecrypt.encryption(new BigInteger(x+""),key));
            map.put('y', EncryptDecrypt.encryption(new BigInteger(y+""),key));
            map.put('c', EncryptDecrypt.encryption(new BigInteger(c+""),key));
            map.put('z', EncryptDecrypt.encryption(new BigInteger(z+""),key));
            RecursiveDescent recursiveDescent = new RecursiveDescent(func,map,key);
            int answw = EncryptDecrypt.decryption(recursiveDescent.exp(),key).intValue();
            if (answ == answw){
            }
            else {
                System.out.println("ERROR "+answ+" "+answw);
            }
        }
    }

    @Test
    public void key(){
        DefaultDataExample defaultDataExample = new DefaultDataExample();
        defaultDataExample.createCriteria().andIsUserfulEqualTo(true);
        List<DefaultData> defaultDataList = defaultDataMapper.selectByExample(defaultDataExample);
        for (DefaultData defaultData:defaultDataList){
            DefaultModule defaultModule = defaultModuleMapper.selectByPrimaryKey(defaultData.getDefaultId());
            KeyFile keyFile = keyFileMapper.selectByPrimaryKey(defaultData.getKeyfileid());
            GenerateTestUtils.gen(defaultModule.getTfunction(),defaultModule.getChangefun(),keyFile.getFileurl(),defaultModule.getName());
//            GenerateTestUtils.gen(defaultModule.getTfunction(),defaultModule.getChangefun(),keyFile.getFileurl(),defaultModule.getName());
        }
    }
}
