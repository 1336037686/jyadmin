package com.jyadmin.system.email.config.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-11-23 23:10 <br>
 * @description: EmailConfigUpdateVO <br>
 */
@ApiModel("系统邮件配置-修改-数据模型")
@Data
@Accessors(chain = true)
public class EmailConfigUpdateVO {

    /**
     * 托管平台
     */
    @ApiModelProperty(value = "托管平台", name = "storageType")
    @NotBlank(message = "托管平台不能为空")
    private String storageType;

    /**
     * 相应配置
     */
    @ApiModelProperty(value = "相应配置", name = "config")
    @NotBlank(message = "相应配置不能为空")
    private String config;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}
