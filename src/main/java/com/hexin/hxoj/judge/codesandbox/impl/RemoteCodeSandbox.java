package com.hexin.hxoj.judge.codesandbox.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.excel.util.StringUtils;
import com.hexin.hxoj.common.ErrorCode;
import com.hexin.hxoj.exception.BusinessException;
import com.hexin.hxoj.judge.codesandbox.CodeSandBox;
import com.hexin.hxoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.hexin.hxoj.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * 远程代码沙箱（实际调用接口的沙箱）
 */
public class RemoteCodeSandbox implements CodeSandBox {
    // 定义鉴权请求头和密钥,实现调用安全性校验
    private static final String AUTH_REQUEST_HEADER = "auth";
    private static final String AUTH_REQUEST_SECRET = "secretKey";

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest excecuteCodeRequest) {
        System.out.println("远程代码沙箱");
        // 调用执行Java原生代码沙箱
        String javaUrl = "http://localhost:8090/javaNativeCodeSandbox";
        // 调用执行Java Docker代码沙箱
        String dockerUrl = "http://localhost:8090/javaDockerCodeSandbox";
        // 使用hutool工具类发送post请求
        String json = JSONUtil.toJsonStr(excecuteCodeRequest);
        String responseStr = HttpUtil.createPost(javaUrl)
                // 设置请求头,实现鉴权
                .header(AUTH_REQUEST_HEADER, AUTH_REQUEST_SECRET)
                .body(json)
                // 执行请求
                .execute()
                // 解析返回结果
                .body();
        if (StringUtils.isBlank(responseStr)) {
            throw new BusinessException(ErrorCode.API_REQUEST_ERROR, "executeCode remoteSandbox error, message = " + responseStr);
        }
        return JSONUtil.toBean(responseStr, ExecuteCodeResponse.class);
    }
}
