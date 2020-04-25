package com.mutiny.demo.Service.Impl;

import com.mutiny.demo.Service.AdminService;
import com.mutiny.demo.Service.Token_redisService;
import com.mutiny.demo.component.PortraitComponent;
import com.mutiny.demo.dao.CompanyMapper;
import com.mutiny.demo.dao.GovernMapper;
import com.mutiny.demo.dao.RoleMapper;
import com.mutiny.demo.dao.UserMapper;
import com.mutiny.demo.dto.AdminUserDetails;
import com.mutiny.demo.pojo.*;
import com.mutiny.demo.util.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminService.class);
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CompanyMapper companyMapper;
    @Autowired
    private GovernMapper governMapper;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private Token_redisService token_redisService;
    @Autowired
    private PortraitComponent portraitComponent;
    @Override
    public String login(String username, String password) throws Exception{
        AdminUserDetails userDetails = null;
//        try {
            User user = userMapper.selectByPrimaryKey(username);
            if(user == null){
                throw new BadCredentialsException("账户或密码不正确");
            }
            if (!passwordEncoder.matches(password, user.getPassword())) {
//            if (!password.equals(user.getPassword())) {
                System.out.println("your password"+password+" correct:"+ user.getPassword());
                throw new BadCredentialsException("密码不正确");
            }
            RoleExample roleExample=new RoleExample();
            roleExample.createCriteria().andUserIdEqualTo(user.getId());
            List<Role> roles=roleMapper.selectByExample(roleExample);
            userDetails = new AdminUserDetails(user,roles);
//            userDetails = new AdminUserDetails(user,null);
            //   UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            //  SecurityContextHolder.getContext().setAuthentication(authentication);
//        } catch (AuthenticationException e) {
//            LOGGER.warn("登录异常:{}", e.getMessage());
//        }
//        try{
            if(userDetails == null){
                throw new BadCredentialsException("密码不正确");
            }
            else{
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
//        }
//        catch (AuthenticationException e) {
//            LOGGER.warn("登录异常:{}", e.getMessage());
//            return null;
//        }
        return jwtTokenUtil.generateToken(userDetails);
    }

    @Override
    public User getAdminByUsername(String username)  throws Exception{
        User example;
        example = userMapper.selectByPrimaryKey(username);
        return example;
    }

    @Override
    public User register_govern(User user,Govern govern) throws Exception{
        User umsAdmin = new User();
        BeanUtils.copyProperties(user, umsAdmin);
        if (userMapper.selectByPrimaryKey(umsAdmin.getId())!=null) {
            throw new Exception("该账号已存在");
        }
        //将密码进行加密操作
        String encodePassword = passwordEncoder.encode(umsAdmin.getPassword());
        GovernExample governExample = new GovernExample();
        governExample.createCriteria();
        List<Govern> governList = governMapper.selectByExample(governExample);
        int Size = governList.size()+1;
        govern.setGovernid(Size);
        governMapper.insertSelective(govern);
        umsAdmin.setGovernid(Size);
        umsAdmin.setPassword(encodePassword);
        userMapper.insertSelective(umsAdmin);
        Role role=new Role();
        role.setCreateTime(new Date());
        role.setUserId(umsAdmin.getId());
        String roleType= umsAdmin.getType();
        role.setRoleName("政府工作人员");
        role.setRoleType("ROLE_GOVER");
        role.setDescription("政府数据管理与授权人员");
        roleMapper.insertSelective(role);
        return umsAdmin;
    }

    @Override
    public User register_user(User user, String company) throws Exception{
        User umsAdmin = new User();
        BeanUtils.copyProperties(user, umsAdmin);
        if (userMapper.selectByPrimaryKey(umsAdmin.getId())!=null) {
            throw new Exception("该账号已存在");
        }
        //将密码进行加密操作
        String encodePassword = passwordEncoder.encode(umsAdmin.getPassword());
        CompanyExample companyExample = new CompanyExample();
        companyExample.createCriteria().andNameEqualTo(company);
        List<Company> companyList = companyMapper.selectByExample(companyExample);
        if (companyList.size()==0){
            throw new Exception("No Such Company");
        }
        Company company1 = companyList.get(0);
        umsAdmin.setCompanyid(company1.getCompanyid());
        umsAdmin.setPassword(encodePassword);
        userMapper.insertSelective(umsAdmin);
        Role role=new Role();
        role.setCreateTime(new Date());
        role.setUserId(umsAdmin.getId());
        String roleType= umsAdmin.getType();
        role.setRoleName("企业职员");
        role.setRoleType("ROLE_USER");
        role.setDescription("企业普通工作人员");
        roleMapper.insertSelective(role);
        return umsAdmin;
    }

    @Override
    public User register_admin(User user,Company company) throws Exception{
        User umsAdmin = new User();
        BeanUtils.copyProperties(user, umsAdmin);
        if (userMapper.selectByPrimaryKey(umsAdmin.getId())!=null) {
            throw new Exception("该账号已存在");
        }
        //将密码进行加密操作
        String encodePassword = passwordEncoder.encode(umsAdmin.getPassword());
        CompanyExample companyExample = new CompanyExample();
        companyExample.createCriteria();
        List<Company> companyList = companyMapper.selectByExample(companyExample);
        int Size = companyList.size()+1;
        company.setCompanyid(Size);
        companyMapper.insertSelective(company);
        umsAdmin.setCompanyid(Size);
        umsAdmin.setPassword(encodePassword);
        userMapper.insertSelective(umsAdmin);
        Role role=new Role();
        role.setCreateTime(new Date());
        role.setUserId(umsAdmin.getId());
        String roleType= umsAdmin.getType();
        role.setRoleName("企业工作人员");
        role.setRoleType("ROLE_ADMIN");
        role.setDescription("企业数据管理与合作计算人员");
        roleMapper.insertSelective(role);
        return umsAdmin;
    }

//    public User register(User umsAdminParam) throws Exception{
//        User umsAdmin = new User();
//        BeanUtils.copyProperties(umsAdminParam, umsAdmin);
////             umsAdmin.setCreateTime(new Date());
//        //查询是否有相同用户名的用户
//        if (userMapper.selectByPrimaryKey(umsAdminParam.getId())!=null) {
//            throw new Exception("该账号已存在");
//        }
//        //将密码进行加密操作
//        String encodePassword = passwordEncoder.encode(umsAdmin.getPassword());
//        umsAdmin.setPassword(encodePassword);
//        userMapper.insertSelective(umsAdmin);
//        Role role=new Role();
//        role.setCreateTime(new Date());
//        role.setUserId(umsAdmin.getId());
//        String roleType= umsAdmin.getType();
//        if(roleType.equals("政府")){
//            role.setRoleName("政府工作人员");
//            role.setRoleType("ROLE_GOVER");
//            role.setDescription("政府数据管理与授权人员");
//        }
//        else if(roleType.equals("企业管理员")){
//            role.setRoleName("企业工作人员");
//            role.setRoleType("ROLE_ADMIN");
//            role.setDescription("企业数据管理与合作计算人员");
//        }
//        else{
//            role.setRoleName("企业职员");
//            role.setRoleType("ROLE_USER");
//            role.setDescription("企业普通工作人员");
//        }
//        roleMapper.insertSelective(role);
//        return umsAdmin;
//    }

    @Override
    public void logout(String username) throws Exception {
        token_redisService.delete(username);
    }

    @Override
    public User showme(String username)  throws Exception {
        return userMapper.selectByPrimaryKey(username);
    }

    @Override
    public Company showCompany(int companyId) throws Exception {
        return companyMapper.selectByPrimaryKey(companyId);
    }

    @Override
    public Govern showGovern(int GovernId) throws Exception {
        return governMapper.selectByPrimaryKey(GovernId);
    }

    @Override
    public String showPortrait(String username) throws Exception{
        return "/image/"+portraitComponent.getImg(username);
    }

//    @Override
//    public boolean updatepassword(UpdatepasswordDTO updatepasswordDTO) {
//        token_redisService.delete(updatepasswordDTO.getUsername());
//        User user=userMapper.selectByPrimaryKey(updatepasswordDTO.getUsername());
//        String password =passwordEncoder.encode(updatepasswordDTO.getNewpassword());
//        user.setPassword(password);
//        userMapper.updateByPrimaryKey(user);
//        return true;
//    }

}
