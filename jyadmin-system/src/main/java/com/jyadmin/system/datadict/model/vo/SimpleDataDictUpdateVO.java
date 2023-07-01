package com.jyadmin.system.datadict.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 通用数据字典-修改-普通节点-数据模型
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-10-30 16:15 <br>
 * @description: SimpleDataDictCreateVO <br>
 */
@ApiModel("通用数据字典-修改-普通节点-数据模型")
@Data
public class SimpleDataDictUpdateVO implements Serializable {

    /**
     * 节点ID
     */
    @ApiModelProperty(value = "节点ID", name = "id")
    @NotNull(message = "id不能为空")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

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
     * 备注
     */
    @ApiModelProperty(value = "备注", name = "remark")
    private String remark;

}
