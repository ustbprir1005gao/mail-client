package com.ustbgao.mailtools.bean;

import java.io.Serializable;

/**
 * 定义邮件发送时的数据承载对象
 *
 * author gaoqi
 * created At 2015/10/21.
 */
public class SendMailBean implements Serializable {

    //发送邮件的邮箱
    private String mailFrom;
    //接收邮件的邮箱
    private String mailTo;
    //邮件的主题
    private String subject;
    //邮件的正文内容
    private String content;
    //邮件的附件
    private String attach;

    public String getMailFrom() {
        return mailFrom;
    }

    public void setMailFrom(String mailFrom) {
        this.mailFrom = mailFrom;
    }

    public String getMailTo() {
        return mailTo;
    }

    public void setMailTo(String mailTo) {
        this.mailTo = mailTo;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }
}
