package com.jyadmin.system.announcements.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 系统公告 <br>
 * @author jyadmin code generate <br>
 * @version 1.0 <br>
 * Create by 2023-07-12 20:08:30 <br>
 * @description: AnnouncementsUpdateReqVO <br>
 */
@ApiModel("系统公告-修改-数据模型")
@Data
public class AnnouncementsUpdateReqVO implements Serializable {

    /**
     * ID
     */
    @ApiModelProperty(value = "ID", name = "id")
    @NotNull(message = "ID不能为空")
    private Long id;

    /**
     * 公告标题
     */
    @ApiModelProperty(value = "公告标题", name = "title")
    @NotBlank(message = "公告标题不能为空")
    private String title;

    /**
     * 公告内容
     */
    @ApiModelProperty(value = "公告内容", name = "content")
    @NotBlank(message = "公告内容不能为空")
    private String content;

    /**
     * 公告状态草稿（draft）、已发布（published）、已过期（expired）
     */
    @ApiModelProperty(value = "公告状态草稿（draft）、已发布（published）、已过期（expired）", name = "status")
    @NotBlank(message = "公告状态草稿（draft）、已发布（published）、已过期（expired）不能为空")
    private String status;
}
