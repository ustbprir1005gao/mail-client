package com.ustbgao.mailtools.utils;

import com.ustbgao.mailtools.constant.MailServerConfiguration;
import com.ustbgao.mailtools.impl.ConnectionServiceImpl;

/**
 * author gaoqi
 * created At 2015/10/21.
 */
public class TestSendMail {
    public static void main(String [] args){
        ConnectionServiceImpl connectionService = new ConnectionServiceImpl();
        connectionService.initMailServerConnect(MailServerConfiguration.NET_EASE_HOST, MailServerConfiguration.NET_EASE_PORT);
        connectionService.sendHeloCommand("helo ustbgao");
        connectionService.sendAuthLoginCommand("auth login");
    }
}
