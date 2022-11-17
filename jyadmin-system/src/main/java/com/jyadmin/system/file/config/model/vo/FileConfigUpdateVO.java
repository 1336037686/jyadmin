package com.jyadmin.system.file.config.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jyadmin.domain.BaseEntity;
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
     * 存储类型 （本地存储、阿里OSS、七牛云OSS、腾讯云OSS）
     */
    @ApiModelProperty(value = "存储类型", name = "storageType")
    @NotBlank(message = "存储类型不能为空")
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