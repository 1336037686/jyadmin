package com.jyadmin.system.user.model.dto;

import com.jyadmin.system.user.domain.User;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2023-05-04 16:02 <br>
 * @description: UserResponsePageVO <br>
 */
@ApiModel("系统用户-分页响应-数据模型")
@Data
public class UserDTO extends User {

    /**
     * 角色名称列表
     */
    private List<String> roleNames;

    /**
     * 岗位名称
     */
    private String departmentName;

    /**
     * 角色名称
     */
    private String postName;

}
