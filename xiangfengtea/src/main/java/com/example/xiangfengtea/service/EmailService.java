package com.example.xiangfengtea.service;

public interface EmailService {
    /**
     * 发送邮件
     * @param to 邮件收件人
     * @param subject 邮件主题
     * @param verifyCode 邮件验证码
     */
    public boolean sendHtmlMail(String to, String subject,String doing, String verifyCode);
}
