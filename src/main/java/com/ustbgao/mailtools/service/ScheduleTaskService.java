package com.ustbgao.mailtools.service;

import com.ustbgao.mailtools.bean.SendMailBean;
import com.ustbgao.mailtools.bean.SendMailToGroupBean;

/**
 * 定时发送邮件的接口定义
 * author gaoqi
 * created At 2015/10/21.
 */
public interface ScheduleTaskService {

    /**
     * 将需要定时发送的单发邮件推送到redis服务器
     * @param sendMailBean
     */
    public void pushSingleMailToRedis(SendMailBean sendMailBean);

    /**
     * 将需要定时发送的群发邮件推送到redis服务器
     * @param sendMailToGroupBean
     */
    public void pushGroupMailToRedis(SendMailToGroupBean sendMailToGroupBean);

}
