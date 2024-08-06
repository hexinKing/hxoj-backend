package com.hexin.hxoj.judge;

import com.hexin.hxoj.judge.codesandbox.CodeSandBox;
import com.hexin.hxoj.judge.codesandbox.CodeSandboxFactory;
import com.hexin.hxoj.judge.codesandbox.CodeSandboxProxy;
import com.hexin.hxoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.hexin.hxoj.judge.codesandbox.model.ExecuteCodeResponse;
import com.hexin.hxoj.model.enums.QuestionSubmitLanguageEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class CodeSandBoxTest {

    @Value("${codesandbox.type:Remote}")
    private String type;

    @Bean
    @Test
    public void CodeSandBox() {

        // 参数配置化 // 工厂模式
        CodeSandBox codeSandBox = CodeSandboxFactory.newInstance(type);
        // 代理模式,本质是对需要代理对象方法的重写
        CodeSandboxProxy codeSandboxProxy = new CodeSandboxProxy(codeSandBox);
        String code = "int main() { }";
        String language = QuestionSubmitLanguageEnum.JAVA.getValue();
        List<String> inputList = Arrays.asList("1 2", "3 4");
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandboxProxy.executeCode(executeCodeRequest);
        Assertions.assertNotNull(executeCodeResponse);

    }


}
