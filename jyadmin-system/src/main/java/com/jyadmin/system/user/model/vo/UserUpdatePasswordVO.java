package com.jyadmin.system.user.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-04-27 18:01 <br>
 * @description: UserPasswordVO <br>
 */
@ApiModel("系统用户-修改密码-数据模型")
@Data
public class UserUpdatePasswordVO {

    /**
     * ID
     */
    @ApiModelProperty(value = "ID", name = "id")
    @NotBlank(message = "ID不能为空")
    private String id;

    @ApiModelProperty(value = "密码", name = "password;")
    @NotBlank(message = "密码不能为空")
    private String password;

}
