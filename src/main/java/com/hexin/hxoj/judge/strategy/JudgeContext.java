package com.hexin.hxoj.judge.strategy;


import com.hexin.hxoj.model.dto.question.JudgeCase;
import com.hexin.hxoj.model.dto.questionsubmit.JudgeInfo;
import com.hexin.hxoj.model.entity.Question;
import com.hexin.hxoj.model.entity.QuestionSubmit;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 上下文类（用于定义在策略中传递的参数）
 */
@Data
@Builder
public class JudgeContext {

    /**
     * 沙盒代码判题信息
     */
    private JudgeInfo judgeInfo;
    /**
     * 沙盒代码输入用例列表
     */
    private List<String> inputList;
    /**
     * 沙盒代码输出用例列表
     */
    private List<String> outputList;
    /**
     * 题目判题用例列表
     */
    private List<JudgeCase> judgeCaseList;

    private Question question;

    private QuestionSubmit questionSubmit;


}
