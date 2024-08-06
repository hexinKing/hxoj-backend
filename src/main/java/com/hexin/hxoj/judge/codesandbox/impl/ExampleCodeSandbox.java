package com.hexin.hxoj.judge.codesandbox.impl;

import com.hexin.hxoj.judge.codesandbox.CodeSandBox;
import com.hexin.hxoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.hexin.hxoj.judge.codesandbox.model.ExecuteCodeResponse;
import com.hexin.hxoj.model.dto.questionsubmit.JudgeInfo;
import com.hexin.hxoj.model.enums.JudgeInfoMessageEnum;
import com.hexin.hxoj.model.enums.QuestionSubmitStatusEnum;

import java.util.List;

/**
 * 示例代码沙箱（仅为了跑通业务流程）
 */
public class ExampleCodeSandbox implements CodeSandBox {

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest excecuteCodeRequest) {
        List<String> inputList = excecuteCodeRequest.getInputList();
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        executeCodeResponse.setOutputList(inputList);
        executeCodeResponse.setMessage("测试执行成功");
        executeCodeResponse.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setMessage(JudgeInfoMessageEnum.ACCEPTED.getText());
        judgeInfo.setMemory(100L);
        judgeInfo.setTime(100L);
        executeCodeResponse.setJudgeInfo(judgeInfo);
        return executeCodeResponse;
    }
}
