package com.jyadmin.system.datadict.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-04-07 22:06 <br>
 * @description: SysDataDictVO <br>
 */
@ApiModel("数据字典-创建-普通节点-数据模型")
@Data
public class DataDictCreateNodeVO {

    /**
     * 字典类型
     */
    @ApiModelProperty(value = "字典类型", name = "dictType")
    @NotBlank(message = "字典类型不能为空")
    @Length(min = 1, max = 50, message = "字典类型长度须在1-50字符之间")
    private String dictType;

    /**
     * 字典名称
     */
    @ApiModelProperty(value = "字典名称", name = "name")
    @NotBlank(message = "字典名称不能为空")
    @Length(min = 1, max = 50, message = "字典名称长度须在1-50字符之间")
    private String name;

    /**
     * 字典编码
     */
    @ApiModelProperty(value = "字典编码", name = "code")
    @NotBlank(message = "字典编码不能为空")
    @Length(min = 1, max = 50, message = "字典编码长度须在1-50字符之间")
    private String code;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序", name = "sort")
    @NotNull(message = "排序不能为空")
    private Integer sort;

    /**
     * 父类别ID
     */
    @ApiModelProperty(value = "父类别ID", name = "parentId")
    @NotNull(message = "父类别ID不能为空")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long parentId;


}
