package com.hexin.hxoj.model.dto.user;

import com.hexin.hxoj.common.PageRequest;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户查询请求
 *
 *
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserQueryRequest extends PageRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 开放平台id
     */
    private String unionId;

    /**
     * 公众号openId
     */
    private String mpOpenId;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 简介
     */
    private String userProfile;

    /**
     * 用户角色：user/admin/ban
     */
    private String userRole;

    /**
     * 电话号码
     */
    private String phone;

    /**
     * 性别：男/女/其他
     */
    private String gender;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 用户状态：正常/封号/注销
     */
    private String userState;

    private static final long serialVersionUID = 1L;
}