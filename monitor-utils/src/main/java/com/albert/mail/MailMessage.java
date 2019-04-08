package com.albert.mail;

/**
 * @author fujin
 * @version v 1.0
 * @create 2019-03-08 邮件发送信息实体类
 */
public class MailMessage {

    /**
     * 发送邮箱服务器
     */
    private String mailServer;
    /**
     * 发送者登录账号，一般为发送者邮箱
     */
    private String loginAccount;
    /**
     * 发送者授权码
     */
    private String loginAuthCode;
    /**
     * 发送者邮箱
     */
    private String sender;
    /**
     * 接收者邮箱，逗号分隔
     */
    private String[] recipients;
    /**
     * 邮件主题
     */
    private String emailSubject;
    /**
     * 邮件内容
     */
    private String emailContent;
    /**
     * 发送邮件格式
     */
    private String emailContentType;
}
