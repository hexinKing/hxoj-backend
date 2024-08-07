package com.hexin.hxoj.model.dto.questionsubmit;

import com.hexin.hxoj.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 查询请求
 *
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class QuestionSubmitQueryRequest extends PageRequest implements Serializable {

    /**
     * 编程语言
     */
    private String submitLanguage;

    /**
     * 提交状态
     */
    private Integer submitStatus;

    /**
     * 题目 id
     */
    private Long questionId;


    /**
     * 用户 id
     */
    private Long userId;

    private static final long serialVersionUID = 1L;
}