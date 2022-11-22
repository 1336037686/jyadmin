package com.jyadmin.system.file.config.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 附件基础配置
 * @TableName sys_file_config
 */
@ApiModel("附件基础配置-修改-数据模型")
@Data
public class FileConfigUpdateVO implements Serializable {

    /**
     * 托管平台 （本地存储、阿里OSS、七牛云OSS、腾讯云OSS）
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