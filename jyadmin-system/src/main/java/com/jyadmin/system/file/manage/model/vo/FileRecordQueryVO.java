package com.jyadmin.system.file.manage.model.vo;

import com.jyadmin.domain.base.BasePageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 系统附件记录
 */
@ApiModel("系统附件记录-查找-数据模型")
@Data
public class FileRecordQueryVO extends BasePageVO implements Serializable {

    /**
     * 文件名
     */
    @ApiModelProperty(value = "文件名", name = "name")
    private String name;

    /**
     * 文件类型
     */
    @ApiModelProperty(value = "文件类型", name = "type")
    private String type;

    /**
     * 文件后缀
     */
    @ApiModelProperty(value = "文件后缀", name = "suffix")
    private String suffix;

    /**
     * 文件存储平台（阿里云、七牛云、腾讯云、本地）
     */
    @ApiModelProperty(value = "文件存储平台", name = "source")
    private String source;

    /**
     * 原文件名称
     */
    @ApiModelProperty(value = "原文件名称", name = "realName")
    private String realName;

    /**
     * 业务标识
     */
    @ApiModelProperty(value = "业务标识", name = "relevance")
    private String relevance;



}