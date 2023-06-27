package com.jyadmin.monitor.offline.model.vo;

import com.jyadmin.domain.base.BasePageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-05-13 22:48 <br>
 * @description: UserQueryVO <br>
 */
@ApiModel("用户下线-查询-数据模型")
@Data
public class UserQueryVO extends BasePageVO {

    @ApiModelProperty(value = "用户名称", name = "username")
    private String username;

}
