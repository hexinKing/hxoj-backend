package com.hexin.hxoj.model.dto.questionsubmit;

import lombok.Data;

import java.io.Serializable;

/**
 * 创建请求
 *
 *
 */
@Data
public class QuestionSubmitAddRequest implements Serializable {

    /**
     * 编程语言
     */
    private String submitLanguage;

    /**
     * 用户代码
     */
    private String submitCode;

    /**
     * 题目 id
     */
    private Long questionId;

    private static final long serialVersionUID = 1L;
}