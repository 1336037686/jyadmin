package com.jyadmin.security.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jyadmin.security.domain.PermissionAction;
import com.jyadmin.security.domain.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;


/**
* @author 13360
* @description 针对表【sys_user(用户表)】的数据库操作Service
* @createDate 2022-04-12 23:19:40
*/
public interface AuthService extends IService<User> {

    Map<String, Object> login(HttpServletRequest request, String username, String password);

    User getByUserName(String userName);

    List<PermissionAction> getPermissions(String userId);

    List<Map<String, Object>> getMenus(String userId);

    Map<String, Object> getUserInfo(String userId);

    void logout(String username);

    String getIdempotentToken();

    void getCaptcha(String uniqueId, HttpServletResponse response) throws IOException;
}
