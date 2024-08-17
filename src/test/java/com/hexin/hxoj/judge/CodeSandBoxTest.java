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

        // 参数配置化 + 工厂模式
        CodeSandBox codeSandBox = CodeSandboxFactory.newInstance(type);
        // 代理模式,本质是对需要代理对象方法的重写
        CodeSandboxProxy codeSandboxProxy = new CodeSandboxProxy(codeSandBox);
        String code = "public class Main {\n" +
                "    public static void main(String[] args) {\n" +
                "        int a = Integer.parseInt(args[0]);\n" +
                "        int b = Integer.parseInt(args[1]);\n" +
                "        System.out.println(\"结果:\" + (a + b));\n" +
                "    }\n" +
                "}\n";
        String language = QuestionSubmitLanguageEnum.JAVA.getValue();
        List<String> inputList = Arrays.asList("1 2", "3 4");
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandboxProxy.executeCode(executeCodeRequest);
        // 断言,用于检查一个对象是否为null,如果 executeCodeResponse 为 null，测试将失败，并且通常会抛出一个异常
        Assertions.assertNotNull(executeCodeResponse);
        System.out.println(executeCodeResponse);

    }


}
