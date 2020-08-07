package com.example.demo.events;

import com.example.demo.utils.MailUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 邮件监听，异步执行
 */
@Component
@Slf4j
public class MailListener implements ApplicationListener<KattleEvent> {
    @Value("${spring.mail.username}")
    private String from;
    @Value("${title}")
    private String title;
    @Autowired
    JavaMailSender javaMailSender;
    @Override
    @Async
    public void onApplicationEvent(KattleEvent kattleEvent) {
        MailUtil.sendSimpleMail(javaMailSender,kattleEvent.getMessage(),from,title,"上传成功");
        log.info("mail成功了哈哈哈");
    }
}
