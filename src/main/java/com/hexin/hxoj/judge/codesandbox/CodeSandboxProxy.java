package com.hexin.hxoj.judge.codesandbox;

import com.hexin.hxoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.hexin.hxoj.judge.codesandbox.model.ExecuteCodeResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 代码沙箱代理
 */
@Slf4j
@AllArgsConstructor
public class CodeSandboxProxy implements CodeSandBox {

    private final CodeSandBox codeSandBox;

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest excecuteCodeRequest) {
        log.info("代码沙箱请求信息：" + excecuteCodeRequest.toString());
        ExecuteCodeResponse executeCodeResponse = codeSandBox.executeCode(excecuteCodeRequest);
        log.info("代码沙箱响应信息：" + executeCodeResponse.toString());
        return executeCodeResponse;
    }
}
