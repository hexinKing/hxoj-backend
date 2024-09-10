package com.hexin.hxoj.mq;

import com.hexin.hxoj.common.ErrorCode;
import com.hexin.hxoj.exception.BusinessException;
import com.hexin.hxoj.model.entity.QuestionSubmit;
import com.hexin.hxoj.model.enums.QuestionSubmitStatusEnum;
import com.hexin.hxoj.service.QuestionSubmitService;
import com.rabbitmq.client.Channel;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static com.hexin.hxoj.constant.MqConstant.CODE_DLX_QUEUE;


/**
 * 判题死信队列
 *
 */
@Component
@Slf4j
public class CodeMqFailConsumer {

    @Resource
    private QuestionSubmitService questionSubmitService;


    /**
     * 监听死信队列
     *
     * @param message
     * @param channel
     * @param dekivery
     */
    @SneakyThrows
    @RabbitListener(queues = {CODE_DLX_QUEUE}, ackMode = "MANUAL")
    public void receiveMessage(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long dekivery) {
        // 接收到失败的信息
        log.info("死信队列接受到的消息：{}", message);
        if (StringUtils.isBlank(message)) {
            channel.basicNack(dekivery, false, false);
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "消息为空");
        }
        long questionSubmitId = Long.parseLong(message);
        QuestionSubmit questionSubmit = questionSubmitService.getById(questionSubmitId);
        if (questionSubmit == null) {
            channel.basicNack(dekivery, false, false);
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "提交的题目信息不存在");
        }
        // 把提交题目标为失败
        questionSubmit.setStatus(QuestionSubmitStatusEnum.FAILED.getValue());

        boolean update = questionSubmitService.updateById(questionSubmit);
        if (!update) {
            log.info("处理死信队列消息失败,对应提交的题目id为:{}", questionSubmit.getId());
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "处理死信队列消息失败");
        }
        // 确认消息
        channel.basicAck(dekivery, false);
    }
}
