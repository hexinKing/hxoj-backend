package com.hexin.hxoj.judge;

import cn.hutool.json.JSONUtil;
import com.hexin.hxoj.common.ErrorCode;
import com.hexin.hxoj.exception.BusinessException;
import com.hexin.hxoj.judge.codesandbox.CodeSandBox;
import com.hexin.hxoj.judge.codesandbox.CodeSandboxFactory;
import com.hexin.hxoj.judge.codesandbox.CodeSandboxProxy;
import com.hexin.hxoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.hexin.hxoj.judge.codesandbox.model.ExecuteCodeResponse;
import com.hexin.hxoj.judge.strategy.JudgeContext;
import com.hexin.hxoj.model.dto.question.JudgeCase;
import com.hexin.hxoj.model.dto.questionsubmit.JudgeInfo;
import com.hexin.hxoj.model.entity.Question;
import com.hexin.hxoj.model.entity.QuestionSubmit;
import com.hexin.hxoj.model.enums.QuestionSubmitStatusEnum;
import com.hexin.hxoj.service.QuestionService;
import com.hexin.hxoj.service.QuestionSubmitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class JudgeServiceImpl implements JudgeService {

    @Resource
    private QuestionService questionService;
    @Resource
    private QuestionSubmitService questionSubmitService;
    @Resource
    private JudgeManager judgeManager;

    @Value("${codesandbox.type:Remote}")
    private String type;

    @Override
    public QuestionSubmit doJudge(long questionSubmitId) {
        // 1.根据questionSubmitId获取QuestionSubmit对象及获取到对应的题目、提交信息（包含代码、编程语言等）并进行校验
        QuestionSubmit questionSubmit = questionSubmitService.getById(questionSubmitId);
        if (questionSubmit == null) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目提交信息不存在");
        }
        // 2.根据QuestionSubmit对象获取题目提交信息并进行校验
        Question question = questionService.getById(questionSubmit.getQuestionId());
        if (question == null) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目信息不存在");
        }
        // 如果题目提交状态不为等待中，就不用重复执行了
        if (!questionSubmit.getStatus().equals(QuestionSubmitStatusEnum.WAITING.getValue())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目提交状态不为等待中");
        }
        // 更改判题（题目提交）的状态为 “判题中”，防止重复执行，也能让用户即时看到状态
        QuestionSubmit questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        boolean update = questionSubmitService.updateById(questionSubmitUpdate);
        if (!update) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目提交状态更新失败");
        }
        // 3.封装ExecuteCodeRequest;
        String judgeCase = question.getJudgeCase();
        List<JudgeCase> judgeCaseList = JSONUtil.toList(judgeCase, JudgeCase.class);
        List<String> inputList = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .inputList(inputList)
                .code(questionSubmit.getCode())
                .language(questionSubmit.getLanguage())
                .build();
        // 4.调用代码沙盒并获取返回结果
        CodeSandBox codeSandBox = CodeSandboxFactory.newInstance(type);
        CodeSandboxProxy codeSandboxProxy = new CodeSandboxProxy(codeSandBox);
        ExecuteCodeResponse executeCodeResponse = codeSandboxProxy.executeCode(executeCodeRequest);
        // 5.对返回的结果进行校验，更新数据库信息,设置题目的判题状态和信息
        if (executeCodeResponse == null) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "代码沙盒返回结果为空");
        }
        // 封装JudgeContext上下文
        JudgeContext judgeContext = JudgeContext.builder()
                .judgeInfo(executeCodeResponse.getJudgeInfo())
                .outputList(executeCodeResponse.getOutputList())
                .judgeCaseList(judgeCaseList)
                .inputList(inputList)
                .question(question)
                .questionSubmit(questionSubmit)
                .build();
        //策略模式优化
        JudgeInfo judgeInfo = judgeManager.doJudge(judgeContext);
        // 更新题目提交信息，设置题目提交状态为“成功”
        questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        update = questionSubmitService.updateById(questionSubmitUpdate);
        if (!update) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目提交状态更新失败");
        }
        QuestionSubmit submitServiceById = questionSubmitService.getById(questionSubmitId);
        return submitServiceById;
    }
}
