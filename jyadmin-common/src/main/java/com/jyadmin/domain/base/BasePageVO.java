package com.jyadmin.domain.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 分页请求公共实体类
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-04-07 20:44 <br>
 * @description: BasePageVO <br>
 */
@Data
public class BasePageVO extends BaseReqVO implements Serializable {

    private static final long serialVersionUID = 97792549823353464L;

    /**
     * 页码
     */
    @ApiModelProperty(value = "页码", name = "pageNumber")
    @NotNull(message = "页码不能为空")
    private int pageNumber = 1;

    /**
     * 每页结果数
     */
    @ApiModelProperty(value = "每页结果数", name = "pageSize")
    @NotNull(message = "每页结果数不能为空")
    @Min(value = 1, message = "最小值不能小于1")
    private int pageSize = 10;

}
