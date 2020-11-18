package com.example.xiangfengtea.service.serviceimpl;

import com.example.xiangfengtea.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {
    @Value("${mail.fromMail.addr}")
    private String from;

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public EmailServiceImpl(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    @Override
    public boolean sendHtmlMail(String to, String subject,String doing, String verifyCode) {
        //创建邮件正文
        Context context = new Context();
        context.setVariable("doing",doing);
        context.setVariable("verifyCode", verifyCode);
        //将模块引擎内容解析成html字符串
        String emailContent = templateEngine.process("verifyCodeEmail", context);

        MimeMessage message=mailSender.createMimeMessage();
        try{
            MimeMessageHelper messageHelper=new MimeMessageHelper(message,true);
            messageHelper.setFrom(from);
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(emailContent,true);
            mailSender.send(message);
        }catch (Exception e){
            System.out.println("EmailService: 验证码发送错误");
            return false;
        }
        return true;
    }
}
