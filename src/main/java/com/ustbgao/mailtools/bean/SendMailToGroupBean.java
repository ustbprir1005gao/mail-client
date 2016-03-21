package com.ustbgao.mailtools.bean;

import java.util.List;

/**
 * 邮件的群发数据内存对象
 * author gaoqi
 * created At 2015/10/21.
 */
public class SendMailToGroupBean {
    //发送邮件的邮箱
    private String mailFrom;
    //接收邮件的邮箱,群发
    private List<String> mailTo;
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

    public List<String> getMailTo() {
        return mailTo;
    }

    public void setMailTo(List<String> mailTo) {
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
