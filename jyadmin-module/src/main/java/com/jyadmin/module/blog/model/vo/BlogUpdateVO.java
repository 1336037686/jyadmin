package com.jyadmin.module.blog.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-04-10 17:31 <br>
 * @description: BlogCreateVO <br>
 */
@ApiModel("博文-更新-数据模型")
@Data
public class BlogUpdateVO {

    /**
     * ID
     */
    @ApiModelProperty(value = "文章ID", name = "id")
    @NotBlank(message = "文章ID不能为空")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 文章名称
     */
    @ApiModelProperty(value = "文章名称", name = "title")
    @NotBlank(message = "文章名称不能为空")
    private String title;

    /**
     * 摘要
     */
    @ApiModelProperty(value = "摘要", name = "intro")
    private String intro;

    /**
     * 类别
     */
    @ApiModelProperty(value = "类别", name = "category")
    @NotBlank(message = "文章类别不能为空")
    private String category;

    /**
     * 标签
     */
    @ApiModelProperty(value = "标签", name = "tag")
    @NotBlank(message = "文章标签不能为空")
    private String tag;

    /**
     * 封面
     */
    @ApiModelProperty(value = "封面", name = "cover")
    @NotBlank(message = "文章封面不能为空")
    private String cover;

    /**
     * 文章来源
     */
    @ApiModelProperty(value = "文章来源", name = "source")
    @NotNull(message = "文章来源不能为空")
    private Integer source;

    /**
     * 文章内容
     */
    @ApiModelProperty(value = "文章内容", name = "content")
    @NotBlank(message = "文章内容不能为空")
    private String content;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态", name = "status")
    @NotNull(message = "状态不能为空")
    private Integer status;

    /**
     * 仅自己可见
     */
    @ApiModelProperty(value = "仅自己可见", name = "visible")
    @NotNull(message = "仅自己可见不能为空")
    private Integer visible;

    /**
     * 作者
     */
    @ApiModelProperty(value = "作者", name = "author")
    @NotBlank(message = "作者不能为空")
    private String author;

}
