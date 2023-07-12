package com.jyadmin.system.announcements.model.vo;

import com.jyadmin.domain.base.BasePageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 系统公告 <br>
 * @author jyadmin code generate <br>
 * @version 1.0 <br>
 * Create by 2023-07-12 20:08:30 <br>
 * @description: AnnouncementsQueryReqVO <br>
 */
@ApiModel("系统公告-查询-数据模型")
@Data
public class AnnouncementsQueryReqVO extends BasePageVO implements Serializable {

    /**
     * 公告标题
     */
    @ApiModelProperty(value = "公告标题", name = "title")
    private String title;

    /**
     * 公告状态草稿（draft）、已发布（published）、已过期（expired）
     */
    @ApiModelProperty(value = "公告状态草稿（draft）、已发布（published）、已过期（expired）", name = "status")
    private String status;
}
