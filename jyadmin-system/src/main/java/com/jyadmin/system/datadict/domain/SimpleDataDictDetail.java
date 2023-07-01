package com.jyadmin.system.datadict.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.jyadmin.domain.base.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 通用数据字典详情
 * @TableName sys_simple_data_dict_detail
 */
@TableName(value ="sys_simple_data_dict_detail")
@Data
public class SimpleDataDictDetail extends BaseEntity implements Serializable {

    /**
     * 字段名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 字段编码
     */
    @TableField(value = "code")
    private String code;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

    /**
     * 通用字典ID
     */
    @TableField(value = "data_dict_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long dataDictId;

}