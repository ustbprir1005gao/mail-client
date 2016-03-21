package com.ustbgao.mailtools.impl;

import com.ustbgao.mailtools.bean.ConnectionBean;
import com.ustbgao.mailtools.enumcode.ResponseStatusCode;
import com.ustbgao.mailtools.parse.ParseResponseMessage;
import com.ustbgao.mailtools.service.ConnectionService;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.PasswordAuthentication;
import java.net.Socket;


/**
 * 邮箱服务器连接交互过程中的功能实现类
 *
 * author gaoqi
 * created At 2015/10/21.
 */
public class ConnectionServiceImpl implements ConnectionService {

    //与服务器连接的一些需要保持的信息，由其存储
    private ConnectionBean connectionBean;

    public ConnectionBean getConnectionBean() {
        return connectionBean;
    }

    public void setConnectionBean(ConnectionBean connectionBean) {
        this.connectionBean = connectionBean;
    }

    /**
     * 初始化与邮箱服务器的连接
     *
     * @param serverHost
     * @param serverPort
     * @return
     */
    @Override
    public String initMailServerConnect(String serverHost, int serverPort) {

        Socket socket;
        InputStream inputStream;
        OutputStream outputStream;
        BufferedReader reader;
        BufferedWriter writer;
        String response;
        try {
            socket = new Socket(serverHost , serverPort);
            ConnectionBean connectionBean1 = new ConnectionBean();
            connectionBean1.setSocket(socket);
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            connectionBean1.setInputStream(inputStream);
            connectionBean1.setOutputStream(outputStream);
            reader = new BufferedReader( new InputStreamReader(inputStream));
            connectionBean1.setReader(reader);
            writer = new BufferedWriter(new OutputStreamWriter(outputStream));
            connectionBean1.setWriter(writer);
            response = reader.readLine();
            connectionBean1.setResponseMessage(response);
            this.setConnectionBean(connectionBean1);
            System.out.println("初始化连接时邮箱服务器返回的状态消息是:" + response);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return this.getConnectionBean()==null? this.getConnectionBean().getResponseMessage():null;
    }

    /**
     * 与服务器连接后发送helo 信息进行通信，并返回通信的状态码
     *
     * @param sendHeloMsg
     * @return
     */
    @Override
    public String sendHeloCommand(String sendHeloMsg) {

       String response = null;
        Socket client = null;
        if(this.getConnectionBean() == null){
            System.out.println("连接为空，请确认连接信息");
        }else {
            client = this.getConnectionBean().getSocket();
            String responseMessage = this.getConnectionBean().getResponseMessage();
            BufferedWriter writer;
            BufferedReader reader;
            if (client != null) {
                System.out.println("初始化邮箱服器连接时候返回的状态码是:" + ParseResponseMessage.parseResponseMessageCode(responseMessage));
                if (ParseResponseMessage.parseResponseMessageCode(responseMessage) == ResponseStatusCode.InitResponse.getStatusCode()) {
                    try {
                        writer = this.getConnectionBean().getWriter();
                        writer.write(sendHeloMsg);
                        writer.newLine();
                        writer.flush();
                        reader = this.getConnectionBean().getReader();
                        response = reader.readLine();
                        this.getConnectionBean().setResponseMessage(response);
                        System.out.println("发送helo消息的时候邮箱服务器返回的状态消息是" + response);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return response;
    }

    /**
     * 根据发送helo 字符串后的状态码信息，发送授权登录命令
     *
     * @param authMsg
     * @return
     */
    @Override
    public String sendAuthLoginCommand(String authMsg) {
        String response = null;
        Socket client = null;
        if(this.getConnectionBean() != null) {

            client = this.getConnectionBean().getSocket();
            String responseMessage = this.getConnectionBean().getResponseMessage();
            OutputStream outputStream = null;
            BufferedReader reader = null;
            BufferedWriter writer = null;
            if (client != null) {
                if (ParseResponseMessage.parseResponseMessageCode(responseMessage) == ResponseStatusCode.HeloResponse.getStatusCode()) {
                    try {
                        outputStream = this.getConnectionBean().getOutputStream();
                        writer = new BufferedWriter(new OutputStreamWriter(outputStream));
                        writer.write("auth login");
                        writer.newLine();
                        writer.flush();
                        reader = this.getConnectionBean().getReader();
                        response = reader.readLine();
                        this.getConnectionBean().setResponseMessage(response);
                        System.out.println("发送auth login 命令后邮箱服务器返回的状态消息" + response);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return response;
    }

    /**
     * 向邮箱服务器发送用户名
     *
     * @param userName
     * @return
     */
    @Override
    public String sendUserName(String userName) {

        String response = null;
        if(this.getConnectionBean() != null) {

            Socket client = this.getConnectionBean().getSocket();
            BufferedWriter writer = null;
            BufferedReader reader = null;
            String responseMessage = this.getConnectionBean().getResponseMessage();
            if (client != null) {

                System.out.println("返回的授权状态码是:" + ParseResponseMessage.parseResponseMessageCode(responseMessage));

                if (ParseResponseMessage.parseResponseMessageCode(responseMessage) == ResponseStatusCode.AuthResponse.getStatusCode()) {
                    try {
                        writer = this.getConnectionBean().getWriter();
                        BASE64Encoder encoder = new BASE64Encoder();
                        String name = encoder.encode(userName.getBytes());
                        writer.write(name);
                        writer.newLine();
                        writer.flush();
                        reader = this.getConnectionBean().getReader();
                        response = reader.readLine();
                        this.getConnectionBean().setResponseMessage(response);
                        System.out.println("向邮箱服务器发送用户名后的返回状态消息" + response);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return response;
    }

    /**
     * 向邮箱服务器发送邮箱密码
     *
     * @param passWord
     * @return
     */
    @Override
    public String sendUserPassword(String passWord) {
        String response = null;
        if(this.getConnectionBean() != null) {
            Socket client = this.getConnectionBean().getSocket();
            String responseMessage = this.getConnectionBean().getResponseMessage();
            BufferedWriter writer = null;
            BufferedReader reader = null;
            if (client != null) {
                System.out.println("发送用户名后的返回的状态码是:" + ParseResponseMessage.parseResponseMessageCode(responseMessage));
                if (ParseResponseMessage.parseResponseMessageCode(responseMessage) == ResponseStatusCode.SendUserNameResponse.getStatusCode()) {
                    try {
                        writer = this.getConnectionBean().getWriter();
                        BASE64Encoder encoder = new BASE64Encoder();
                        String name = encoder.encode(passWord.getBytes());
                        writer.write(name);
                        writer.newLine();
                        writer.flush();
                        reader = this.getConnectionBean().getReader();
                        response = reader.readLine();
                        this.getConnectionBean().setResponseMessage(response);
                        System.out.println("向邮箱服务器发送用户密码后的返回状态消息" + response);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return response;
    }

    /**
     * 用户名和密码验证成功后，向邮箱服务器发送发送邮件的源邮箱地址
     *
     * @param mailFrom
     * @return
     */
    @Override
    public String afterAuthenticSendMailFrom(String mailFrom) {

        String response = null;
        if(this.getConnectionBean() != null) {
            Socket client = this.getConnectionBean().getSocket();
            String responseMessage = this.getConnectionBean().getResponseMessage();
            BufferedWriter writer = null;
            BufferedReader reader = null;
            if (client != null) {
                if (ParseResponseMessage.parseResponseMessageCode(responseMessage) == ResponseStatusCode.AuthenticResponse.getStatusCode()) {
                    try {
                        writer = this.getConnectionBean().getWriter();
                        writer.write("mail from:<" + mailFrom + ">");
                        writer.newLine();
                        writer.flush();
                        reader = this.getConnectionBean().getReader();
                        response = reader.readLine();
                        this.getConnectionBean().setResponseMessage(response);
                        System.out.println("发送源邮箱地址命令后邮箱服务器返回的状态消息" + response);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return response;
    }

    /**
     * 发送邮件的目的邮箱，支持群发,当有多个目的邮箱时，以空格分隔
     * 如："123@163.com 12344@qq.com"
     *
     * @param mailTo
     * @return
     */
    @Override
    public String afterAuthenticSendMailTo(String mailTo) {
        String response = null;
        if(this.getConnectionBean() != null) {
            Socket client = this.getConnectionBean().getSocket();
            BufferedReader reader = null;
            BufferedWriter writer = null;
            String responseMessage = this.getConnectionBean().getResponseMessage();
            int destMailCount = 0;
            destMailCount = mailTo.split(" ").length;
            String[] destMailArrays = mailTo.split(" ");
            if (client != null) {
                System.out.println("发送源邮箱地址后邮箱服务器返回的状态码是" + ParseResponseMessage.parseResponseMessageCode(responseMessage));
                if (ParseResponseMessage.parseResponseMessageCode(responseMessage) == ResponseStatusCode.MailFromResponse.getStatusCode()) {
                    try {
                        writer = this.getConnectionBean().getWriter();
                        for (int i = 0; i < destMailCount; i++) {
                            writer.write("rcpt to:<" + destMailArrays[i] + ">");
                            writer.newLine();
                            writer.flush();
                            reader = this.getConnectionBean().getReader();
                            response = reader.readLine();
                        }

                        this.getConnectionBean().setResponseMessage(response);
                        System.out.println("发送目的邮箱地址命令后邮箱服务器返回的状态消息" + response);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return response;
    }

    @Override
    public String sendDataTransferCommitCommand() {
        return null;
    }

    @Override
    public String sendQuitCommand() {
        return null;
    }

    @Override
    public boolean closeConnection() {
        return false;
    }
}
