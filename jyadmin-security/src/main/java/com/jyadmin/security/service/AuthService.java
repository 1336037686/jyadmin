package com.jyadmin.security.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jyadmin.security.domain.PermissionAction;
import com.jyadmin.security.domain.User;
import com.jyadmin.security.domain.UserInfo;
import com.jyadmin.security.domain.UserRegisterVO;

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

    Map<String, Object> register(HttpServletRequest request, UserRegisterVO vo);

    User getByUserName(String userName);

    List<PermissionAction> getAllPermissions();

    List<PermissionAction> getPermissionsByUserId(Long userId);

    List<Map<String, Object>> getMenus(Long userId);

    UserInfo getUserInfo(Long userId);

    void logout(String username);

    String getIdempotentToken();

    void getCaptcha(String uniqueId, HttpServletResponse response) throws IOException;

    String refreshToken(String refreshToken);

    List<String> getApiPermissionByUserId(Long id);

}
