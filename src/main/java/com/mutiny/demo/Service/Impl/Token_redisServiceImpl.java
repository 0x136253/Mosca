package com.mutiny.demo.Service.Impl;

import com.mutiny.demo.Service.Token_redisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service("token_redisService")
public class Token_redisServiceImpl implements Token_redisService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Override
    public boolean vavlid(String IcNumber,String token) {
        return true;
//       Object tem= redisTemplate.opsForValue().get(IcNumber);
//       String ty = (String) tem;
//       if(token.equals(ty.toString())){
//           return true;
//       }
//        return false;
    }

    @Override
    public void add(String token, String IcNumber) {
        return;
//        redisTemplate.opsForValue().set(IcNumber, token);
//        redisTemplate.expire(IcNumber, 30, TimeUnit.MINUTES);
    }

    @Override
    public void delete(String IcNumber) {
        return;
//        redisTemplate.delete(IcNumber);
    }
}
