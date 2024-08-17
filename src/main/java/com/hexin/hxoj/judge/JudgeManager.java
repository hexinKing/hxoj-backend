package com.hexin.hxoj.judge;

import com.hexin.hxoj.judge.strategy.DefaultJudgeStrategy;
import com.hexin.hxoj.judge.strategy.JavaLanguageJudgeStrategy;
import com.hexin.hxoj.judge.strategy.JudgeContext;
import com.hexin.hxoj.judge.strategy.JudgeStrategy;
import com.hexin.hxoj.model.dto.questionsubmit.JudgeInfo;
import com.hexin.hxoj.model.entity.QuestionSubmit;
import org.springframework.stereotype.Service;

/**
 * 判题管理（用于简化调用）
 */
@Service
public class JudgeManager {

    /**
     * 执行判题
     *
     * @param judgeContext
     * @return
     */
    public JudgeInfo doJudge(JudgeContext judgeContext) {
        // 调用判题策略
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        String language = questionSubmit.getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if (language.equals("java")) {
            judgeStrategy = new JavaLanguageJudgeStrategy();
        }
        return judgeStrategy.doJudge(judgeContext);
    }
}
