package com.jyadmin.domain.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 关系表基础字段
 * @author LGX_TvT
 * @date 2022-04-29 14:40
 */
@Data
public class BaseTrEntity implements Serializable {

    private static final long serialVersionUID = 1;

    /**
     * 创建人
     */
    @JsonIgnore
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 创建时间
     */
    @JsonIgnore
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 是否删除
     */
    @JsonIgnore
    @TableField(value = "deleted")
    @TableLogic
    private Integer deleted;

}
