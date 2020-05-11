package com.mutiny.demo.Service;

import com.mutiny.demo.dto.UserCheckDTO;
import com.mutiny.demo.pojo.Company;
import com.mutiny.demo.pojo.Govern;
import com.mutiny.demo.pojo.User;

import java.util.List;

public interface AdminService {

    String login(String username, String password) throws Exception;
    User getAdminByUsername(String username) throws Exception;
    User register_govern(User user, Govern govern) throws Exception;
    User register_user(User user, String company) throws Exception;
    User register_admin(User user,Company company) throws Exception;
    void logout(String username) throws Exception;
    User showme(String username) throws Exception;
    Company showCompany(int companyId) throws Exception;
    Govern showGovern(int GovernId) throws Exception;

    String showPortrait(String username) throws Exception;
//    boolean updatepassword(UpdatepasswordDTO updatepasswordDTO);
    List<UserCheckDTO> showUserNotPass() throws Exception;

    String changeUserCheck(String username, boolean pos) throws Exception;
}
