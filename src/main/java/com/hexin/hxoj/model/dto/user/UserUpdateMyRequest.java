package com.hexin.hxoj.model.dto.user;

import java.io.Serializable;
import lombok.Data;

/**
 * 用户更新个人信息请求
 *
 *
 */
@Data
public class UserUpdateMyRequest implements Serializable {

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 简介
     */
    private String userProfile;

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