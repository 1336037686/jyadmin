package com.jyadmin.system.datadict.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jyadmin.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 数据字典表
 * @TableName sys_data_dict
 */
@TableName(value ="sys_data_dict")
@Data
public class DataDict extends BaseEntity implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 字典类型
     */
    @TableField(value = "dict_type")
    private String dictType;

    /**
     * 字典名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 字典编码
     */
    @TableField(value = "code")
    private String code;

    /**
     * 字典值
     */
    @TableField(value = "value")
    private String value;

    /**
     * 排序
     */
    @TableField(value = "sort")
    private Integer sort;

    /**
     * 父类别ID
     */
    @TableField(value = "parent_id")
    private String parentId;

//    private String parentName;

    /**
     * 是否根结点
     */
    @TableField(value = "is_root")
    private Integer isRoot;

}