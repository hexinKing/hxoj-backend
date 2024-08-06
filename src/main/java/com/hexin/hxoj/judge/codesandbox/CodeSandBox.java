package com.hexin.hxoj.judge.codesandbox;

import com.hexin.hxoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.hexin.hxoj.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * 代码沙箱接口定义
 */
public interface CodeSandBox {

    /**
     * 代码沙箱执行代码接口
     *
     * @param excecuteCodeRequest
     * @return
     */
    ExecuteCodeResponse executeCode(ExecuteCodeRequest excecuteCodeRequest);
}
