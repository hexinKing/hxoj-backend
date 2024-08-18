package com.hexin.hxoj.judge.strategy;

import cn.hutool.json.JSONUtil;
import com.hexin.hxoj.model.dto.question.JudgeCase;
import com.hexin.hxoj.model.dto.question.JudgeConfig;
import com.hexin.hxoj.model.dto.questionsubmit.JudgeInfo;
import com.hexin.hxoj.model.entity.Question;
import com.hexin.hxoj.model.enums.JudgeInfoMessageEnum;

import java.util.List;

/**
 * java语言判题策略
 */
public class JavaLanguageJudgeStrategy implements JudgeStrategy {

    /**
     * 执行判题
     *
     * @param judgeContext
     * @return
     */
    @Override
    public JudgeInfo doJudge(JudgeContext judgeContext) {
        JudgeInfo judgeInfo = judgeContext.getJudgeInfo();
        // 如果judgeInfo.getMemory()为空，需要初始化一些数据
        if (judgeInfo.getMemory() == null) {
            judgeInfo.setMemory(0L);
        }
        List<String> inputList = judgeContext.getInputList();
        List<String> outputList = judgeContext.getOutputList();
        List<JudgeCase> judgeCaseList = judgeContext.getJudgeCaseList();
        Question question = judgeContext.getQuestion();
        String judgeConfigStr = question.getJudgeConfig();
        JudgeConfig judgeConfig = JSONUtil.toBean(judgeConfigStr, JudgeConfig.class);

        JudgeInfo judgeInfoResponse = new JudgeInfo();
        judgeInfoResponse.setMessage(JudgeInfoMessageEnum.ACCEPTED.getValue());
        judgeInfoResponse.setMemory(judgeInfo.getMemory());
        judgeInfoResponse.setTime(judgeInfo.getTime());

        // 先判断沙箱执行的结果输出数量是否和预期输出数量相等
        if (outputList.size() != inputList.size()) {
            judgeInfoResponse.setMessage(JudgeInfoMessageEnum.WRONG_ANSWER.getValue());
            return judgeInfoResponse;
        }
        // 依次判断每一项输出和预期输出是否相等
        for (int i = 0; i < outputList.size(); i++) {
            if (!outputList.get(i).equals(judgeCaseList.get(i).getOutput())) {
                judgeInfoResponse.setMessage(JudgeInfoMessageEnum.WRONG_ANSWER.getValue());
                return judgeInfoResponse;
            }
        }
        // 判断题目限制
        // 判断是否超时是则为“失败”
        // 假设Java语言的运行时间计较长，允许比标准时间长5S
        long JAVA_PROGRAM_TIME_COST = 5000L;
        if (judgeConfig.getTimeLimit() < judgeInfo.getTime() - JAVA_PROGRAM_TIME_COST ) {
            judgeInfoResponse.setMessage(JudgeInfoMessageEnum.TIME_LIMIT_EXCEEDED.getValue());
            return judgeInfoResponse;
        }
        // 判断是否超出内存限制是则为“失败”
        if (judgeConfig.getMemoryLimit() < judgeInfo.getMemory()) {
            judgeInfoResponse.setMessage(JudgeInfoMessageEnum.MEMORY_LIMIT_EXCEEDED.getValue());
            return judgeInfoResponse;
        }

        return judgeInfoResponse;
    }
}
