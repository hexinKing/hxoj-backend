package com.hexin.hxoj.judge.strategy;

import com.hexin.hxoj.model.dto.questionsubmit.JudgeInfo;

/**
 * 判题策略
 */
public interface JudgeStrategy {

    /**
     * 执行判题
     * @return
     */
    JudgeInfo doJudge(JudgeContext judgeContext);
}
