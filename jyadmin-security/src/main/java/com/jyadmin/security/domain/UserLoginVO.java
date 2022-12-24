package com.jyadmin.security.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author LGX_TvT
 * @date 2022-04-29 10:46
 */
@ApiModel("系统用户-用户登录-数据模型")
@Data
@Accessors(chain = true)
public class UserLoginVO implements Serializable {

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名", name = "username")
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码", name = "id")
    @NotBlank(message = "密码不能为空")
    private String password;

    /**
     * 验证码唯一ID
     */
    @ApiModelProperty(value = "验证码唯一ID", name = "uniqueId")
    @NotBlank(message = "验证码唯一ID不能为空")
    private String uniqueId;

    /**
     * 验证码
     */
    @ApiModelProperty(value = "验证码", name = "captcha")
    @NotBlank(message = "验证码不能为空")
    private String captcha;

}
