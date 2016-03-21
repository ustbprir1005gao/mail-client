package com.ustbgao.mailtools.bean;

import java.io.*;
import java.net.Socket;

/**
 * 存储与邮箱服务器连接的一些信息
 *
 * author gaoqi
 * created At 2015/10/21.
 */
public class ConnectionBean implements Serializable {

    //与服务器连接的socket
    private Socket socket;
    //邮箱服务器的地址
    private String host;
    //邮箱服务器的通信端口
    private Integer port;
    //连接建立后对应的输入流
    private InputStream inputStream ;
    //连接建立后对应的buf读流
    private BufferedReader reader;
    //连接建立后对应的输出流
    private OutputStream outputStream;
    //连接建立后对应的buf写入流
    private BufferedWriter writer;
    //交互过程中返回的状态码
    private Integer responseCode;
    //交互过程中返回的状态描述信息
    private String responseMessage;

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public BufferedReader getReader() {
        return reader;
    }

    public void setReader(BufferedReader reader) {
        this.reader = reader;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public BufferedWriter getWriter() {
        return writer;
    }

    public void setWriter(BufferedWriter writer) {
        this.writer = writer;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }
}
