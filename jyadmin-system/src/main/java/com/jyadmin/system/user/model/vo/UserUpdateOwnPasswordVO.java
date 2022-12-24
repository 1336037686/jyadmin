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
@ApiModel("系统用户-修改用户自身密码-数据模型")
@Data
public class UserUpdateOwnPasswordVO {

    /**
     * ID
     */
    @ApiModelProperty(value = "ID", name = "id")
    @NotBlank(message = "ID不能为空")
    private String id;

    @ApiModelProperty(value = "旧密码", name = "oldPassword")
    @NotBlank(message = "旧密码不能为空")
    private String oldPassword;

    @ApiModelProperty(value = "新密码", name = "newPassword")
    @NotBlank(message = "新密码不能为空")
    private String newPassword;

}
