package com.jyadmin.system.user.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-04-09 15:54 <br>
 * @description: TagCreateVO <br>
 */
@ApiModel("系统用户-修改-数据模型")
@Data
public class UserUpdateVO implements Serializable {

    /**
     * ID
     */
    @ApiModelProperty(value = "ID", name = "id")
    @NotNull(message = "ID不能为空")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 帐号
     */
    @ApiModelProperty(value = "帐号", name = "username")
    @NotBlank(message = "帐号不能为空")
    private String username;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称", name = "nickname")
    @NotBlank(message = "昵称不能为空")
    private String nickname;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像", name = "avatar")
    private String avatar;

    /**
     * 电话号码
     */
    @ApiModelProperty(value = "电话号码", name = "phone")
    @NotBlank(message = "电话号码不能为空")
    private String phone;

    /**
     * 账号类型
     */
    @ApiModelProperty(value = "账号类型", name = "type")
    @NotNull(message = "账号类型不能为空")
    private Integer type;

    /**
     * 所属部门
     */
    @ApiModelProperty(value = "所属部门", name = "department")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long department;

    /**
     * 所属岗位
     */
    @ApiModelProperty(value = "所属岗位", name = "post")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long post;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态", name = "status")
    @NotNull(message = "账号状态不能为空")
    private Integer status;
}
