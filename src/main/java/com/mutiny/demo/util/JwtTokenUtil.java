package com.mutiny.demo.util;

import com.mutiny.demo.Service.Token_redisService;
import com.mutiny.demo.dto.AdminUserDetails;
import com.mutiny.demo.pojo.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * JwtToken生成的工具类
 * JWT token的格式：header.payload.signature
 * header的格式（算法、token的类型）：
 * {"alg": "HS512","typ": "JWT"}
 * payload的格式（用户名、创建时间、生成时间）：
 * {"sub":"wang","created":1489079981393,"exp":1489684781}
 * signature的生成算法：
 * HMACSHA512(base64UrlEncode(header) + "." +base64UrlEncode(payload),secret)
 * Created by macro on 2018/4/26.
 */
@Component
public class JwtTokenUtil {
    private static final String CLAIM_KEY_USERNAME = "id";
    private static final String CLAIM_KEY_TIME = "exp";
    private static final String CLAIM_KEY_ROLE = "role";
    @Autowired
    private Token_redisService token_redisService;
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private int expiration;

    /**
     * 根据负责生成JWT的token
     */
    public String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256,secret)
                .compact();
    }

    /**
     * 从token中获取JWT中的负载
     */
    public Claims getClaimsFromToken(String token, String ser) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(ser)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return claims;
    }

    /**
     * 生成token的过期时间
     */
    public Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    /**
     * 从token中获取登录用户名
     */
    public String getUserNameFromToken(String token) {
        String username;
        try {
            Claims claims = getClaimsFromToken(token,secret);
            username = (String) claims.get(CLAIM_KEY_USERNAME);
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    /**
     * 验证token是否还有效
     *
     * @param token       客户端传入的token
     * @param userDetails 从数据库中查询出来的用户信息
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getUserNameFromToken(token);
        if (username.equals(userDetails.getUsername()) && !isTokenExpired(token)){
            return true;
        }
        return false;
    }


    public boolean validateToken(String token){
        Claims claims = getClaimsFromToken(token,secret);
        String username = (String) claims.get(CLAIM_KEY_USERNAME);
        if (claims != null && !isTokenExpired(token)){
            return true;
        }
        return false;
    }

    /**
     * 判断token是否已经失效
     */
    public boolean isTokenExpired(String token) {
        Date expiredDate = getExpiredDateFromToken(token);
        return expiredDate.before(new Date());
    }

    /**
     * 从token中获取过期时间
     */
    public Date getExpiredDateFromToken(String token) {
        Claims claims = getClaimsFromToken(token,secret);
        long date = claims.getExpiration().getTime();
        return new Date(date/1000);
    }

    /**
     * 根据用户信息生成token
     */
    public String generateToken(AdminUserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME,userDetails.getUsername());
        claims.put(CLAIM_KEY_TIME,generateExpirationDate());
        List<String> list = new ArrayList<>();
        for (Role record:userDetails.getPermissionList()){
            list.add(record.getRoleType());
        }
        claims.put(CLAIM_KEY_ROLE,list);
        String token= generateToken(claims);
        token_redisService.add(token,userDetails.getUsername());
        return token;
    }

    public void deletetoken(String username){
        token_redisService.delete(username);
    }
    /**
     * 判断token是否可以被刷新
     */
    public boolean canRefresh(String token) {
        return !isTokenExpired(token);
    }

    /**
     * 刷新token
     */
    public String refreshToken(String token) {
        Claims claims = getClaimsFromToken(token,secret);
        claims.setExpiration(new Date(generateExpirationDate().getTime()*1000));
        String username = getUserNameFromToken(token);
        token_redisService.delete(username);
        String tokens = generateToken(claims);
        token_redisService.add(tokens,username);
        return tokens;
    }
}
