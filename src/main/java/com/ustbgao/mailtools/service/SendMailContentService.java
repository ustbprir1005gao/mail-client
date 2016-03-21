package com.ustbgao.mailtools.service;

/**
 * 与邮箱服务器的连接成功后，发送邮件的相关接口定义
 *
 * author gaoqi
 * created At 2015/9/21.
 */
public interface SendMailContentService {

    /**
     * 发送邮件内容的接口(不包含附件方式发送)
     * @param mailFrom
     * @param mailTo
     * @param subject
     * @param content
     * @return
     */
    public String sendMailContent(String mailFrom , String mailTo , String subject ,String content);
}
