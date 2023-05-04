package com.jyadmin.system.post.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jyadmin.domain.BaseTrEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 系统用户角色中间表
 * @TableName tr_user_post
 */
@TableName(value ="tr_user_post")
@Data
public class UserPost extends BaseTrEntity implements Serializable {
    /**
     * ID
     */
    @TableId(value = "id")
    private String id;

    /**
     * 用户ID
     */
    @TableField(value = "user_id")
    private String userId;

    /**
     * 岗位ID
     */
    @TableField(value = "post_id")
    private String postId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}