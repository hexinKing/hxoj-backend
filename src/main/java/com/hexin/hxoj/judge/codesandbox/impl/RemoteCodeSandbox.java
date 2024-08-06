package com.hexin.hxoj.judge.codesandbox.impl;

import com.hexin.hxoj.judge.codesandbox.CodeSandBox;
import com.hexin.hxoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.hexin.hxoj.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * 远程代码沙箱（实际调用接口的沙箱）
 */
public class RemoteCodeSandbox implements CodeSandBox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest excecuteCodeRequest) {
        System.out.println("远程代码沙箱");
        return null;
    }
}
