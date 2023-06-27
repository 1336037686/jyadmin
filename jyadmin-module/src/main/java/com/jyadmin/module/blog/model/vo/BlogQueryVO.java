package com.jyadmin.module.blog.model.vo;

import com.jyadmin.domain.base.BasePageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-04-10 17:31 <br>
 * @description: BlogCreateVO <br>
 */
@ApiModel("博文-查询-数据模型")
@Data
public class BlogQueryVO extends BasePageVO {

    /**
     * 文章名称
     */
    @ApiModelProperty(value = "文章名称", name = "title")
    private String title;

    /**
     * 类别
     */
    @ApiModelProperty(value = "类别", name = "category")
    private String category;

    /**
     * 标签
     */
    @ApiModelProperty(value = "标签", name = "tag")
    private String tag;

    /**
     * 文章来源
     */
    @ApiModelProperty(value = "文章来源", name = "source")
    private Integer source;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态", name = "status")
    private Integer status;

    /**
     * 仅自己可见
     */
    @ApiModelProperty(value = "仅自己可见", name = "visible")
    private Integer visible;

    /**
     * 作者
     */
    @ApiModelProperty(value = "作者", name = "author")
    private String author;

}
