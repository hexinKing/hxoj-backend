package com.hexin.hxoj.judge.codesandbox;

import com.hexin.hxoj.judge.codesandbox.impl.ExampleCodeSandbox;
import com.hexin.hxoj.judge.codesandbox.impl.RemoteCodeSandbox;
import com.hexin.hxoj.judge.codesandbox.impl.ThirdPartyCodeSandbox;

/**
 * 代码沙箱工厂（根据字符串参数创建指定的代码沙箱实例）
 * 静态工厂
 */
public class CodeSandboxFactory {

    public static CodeSandBox newInstance(String type) {
        if (type == null) {
            return new ExampleCodeSandbox();
        }
        // 根据字符串参数创建指定的代码沙箱实例
        switch (type) {
            case "Example":
                return new ExampleCodeSandbox();
            case "Remote":
                return new RemoteCodeSandbox();
            case "ThirdParty":
                return new ThirdPartyCodeSandbox();
            default:
                return new ExampleCodeSandbox();
        }
    }
}
