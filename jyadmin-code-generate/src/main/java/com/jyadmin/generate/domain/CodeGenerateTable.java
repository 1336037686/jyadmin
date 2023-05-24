package com.jyadmin.generate.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.jyadmin.domain.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 代码生成器-数据库表信息
 * @TableName code_generate_table
 */
@TableName(value ="code_generate_table")
@Accessors(chain = true)
@Data
public class CodeGenerateTable extends BaseEntity implements Serializable {
    /**
     * ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 表名
     */
    @TableField(value = "table_name")
    private String tableName;

    /**
     * 备注
     */
    @TableField(value = "table_remark")
    private String tableRemark;

    /**
     * 存储引擎
     */
    @TableField(value = "table_engine")
    private String tableEngine;

    /**
     * 字符集
     */
    @TableField(value = "table_charset")
    private String tableCharset;

    /**
     * 排序规则
     */
    @TableField(value = "table_order")
    private String tableOrder;

    /**
     * 表创建DDL
     */
    @TableField(value = "table_ddl")
    private String tableDdl;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}