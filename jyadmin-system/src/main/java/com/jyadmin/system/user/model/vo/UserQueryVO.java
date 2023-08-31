package com.jyadmin.system.user.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.jyadmin.domain.base.BasePageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-04-09 15:54 <br>
 * @description: TagCreateVO <br>
 */
@ApiModel("系统用户-查询-数据模型")
@Data
public class UserQueryVO extends BasePageVO implements Serializable {

    /**
     * 帐号
     */
    @ApiModelProperty(value = "帐号", name = "username")
    private String username;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称", name = "nickname")
    private String nickname;

    /**
     * 电话号码
     */
    @ApiModelProperty(value = "电话号码", name = "phone")
    private String phone;

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
     * 账号类型
     */
    @ApiModelProperty(value = "账号类型", name = "type")
    private Integer type;

    /**
     * 所属角色 多个角色用,分隔
     */
    @ApiModelProperty(value = "所属角色", name = "roles")
    private String roles;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态", name = "status")
    private Integer status;

    /**
     * 排除角色 多个角色用,分隔
     */
    private String excludeRoles;

}
