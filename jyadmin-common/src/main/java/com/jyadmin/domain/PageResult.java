package com.jyadmin.domain;

import com.jyadmin.consts.JyResultStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-04-05 16:10 <br>
 * @description: PageResult <br>
 */
@ApiModel("统一分页返回值")
@Data
@Accessors(chain = true)
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    @ApiModelProperty(value = "状态码", name = "code")
    private Integer code;

    /**
     * 执行状态
     */
    @ApiModelProperty(value = "执行状态", name = "success")
    private Boolean success;

    /**
     * 响应消息
     */
    @ApiModelProperty(value = "响应消息", name = "msg")
    private String msg;

    /**
     * 当前页页码
     */
    @ApiModelProperty(value = "当前页码", name = "pageNumber")
    private Long pageNumber = 1L;

    /**
     * 每页结果数，默认 10
     */
    @ApiModelProperty(value = "每页结果数", name = "pageSize")
    private Long pageSize = 10L;

    /**
     * 总页数
     */
    @ApiModelProperty(value = "总页数", name = "pages")
    private Long pages = 0L;

    /**
     * 总条数
     */
    @ApiModelProperty(value = "总条数", name = "total")
    private Long total = 0L;

    /**
     * 数据
     */
    @ApiModelProperty(value = "当前页数据", name = "records")
    private List<T> records;

    /**
     * 是否存在上一页
     */
    @ApiModelProperty(value = "是否存在上一页", name = "hasPrevious")
    private Boolean hasPrevious;

    /**
     * 是否存在下一页
     */
    @ApiModelProperty(value = "是否存在下一页", name = "hasNext")
    private Boolean hasNext;

    public PageResult<T> setStatus(JyResultStatus status) {
        this.code = status.getValue();
        this.msg = status.getReasonPhrase();
        return this;
    }

    public PageResult<T> setSuccess(Boolean success) {
        this.success = success;
        return this;
    }
}
