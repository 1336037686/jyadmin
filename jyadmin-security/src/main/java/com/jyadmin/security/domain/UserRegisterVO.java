package com.jyadmin.security.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2023-08-24 10:26 <br>
 * @description: UserRegisterVO <br>
 */
@ApiModel("系统用户-用户注册-数据模型")
@Data
@Accessors(chain = true)
public class UserRegisterVO {


    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号码", name = "phone")
    @NotBlank(message = "手机号码不能为空")
    private String phone;

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
