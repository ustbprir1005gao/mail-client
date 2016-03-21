package com.ustbgao.mailtools.utils;

import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.ustbgao.mailtools.enumcode.ResponseStatusCode;

/**
 * 自定义底层协议邮件发送框架
 * Created by ustbgao on 15-10-30.
 */
public class MailUtil {
    private Socket socket;
    private String host;
    private Integer port;
    private InputStream inputStream ;
    private BufferedReader reader;
    private OutputStream outputStream;
    private BufferedWriter writer;
    private Integer responseCode;
    private String responseMessage;

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public MailUtil(String host, Integer port) {
        this.host = host;
        this.port = port;
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

    /**
     * 获得邮箱服务器的连接
     * @return
     */
    public Socket getSocket() {
        return socket;
    }

    /**
     * 设置邮箱服务器的连接
     * @param socket
     */
    public void setSocket(Socket socket) {
        this.socket = socket;
    }
    public Integer parseResponseMessageCode(String response){
        String [] strings = response.split(" ");
        Integer responseCode = 0;
        if(strings.length > 0){
            responseCode = Integer.parseInt(strings[0]);
        }
        return  responseCode;
    }

    /**
     * 验证邮箱地址是否合法
     * @param mailName
     * @return
     */
    public boolean verifyMailIsLegal(String mailName){

        Pattern pattern = Pattern.compile(".{0,30}@163\\.com");
        Matcher matcher = pattern.matcher(mailName);
        if(matcher.matches()){
            return true;
        }
        return true;
    }
    /**
     * 初始化邮箱服务器连接
     * @return
     */
    public String initMailServerConnect(){
        Socket socket = null;
        InputStream inputStream = null;
        BufferedReader reader = null;
        BufferedWriter writer1 = null;
        String response = null;//邮箱服务器返回的信息，其中包含状态码
        try {
            socket = new Socket(this.host , this.port);
            this.setSocket(socket);
            inputStream = socket.getInputStream();
            this.inputStream = inputStream;
            reader = new BufferedReader( new InputStreamReader(inputStream));
            this.reader = reader;
            response = reader.readLine();
            this.responseMessage = response;
            System.out.println("初始化连接时邮箱服务器返回的状态消息是:" + response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseMessage;
    }

    /**
     * 发送helo握手消息
     * @return
     */
    public String sendHeloCommand(){
        String response = null;
        Socket client = this.getSocket();
        if(client != null){
            System.out.println("初始化邮箱服器连接时候返回的状态码是:" + parseResponseMessageCode(this.responseMessage));
            if(parseResponseMessageCode(this.responseMessage).equals(ResponseStatusCode.InitResponse.getStatusCode())){
                try{
                    this.outputStream = client.getOutputStream();
                    writer = new BufferedWriter(new OutputStreamWriter(this.outputStream));
                    writer.write("helo ustbgao");
                    writer.newLine();
                    writer.flush();
                    response = reader.readLine();
                    this.responseMessage = response;
                    System.out.println("发送helo消息的时候邮箱服务器返回的状态消息是" + response);
                }catch (IOException e){
                    e.printStackTrace();
                }finally {
                    /*try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/
                }
            }
        }
        return response;
    }

    /**
     * 向邮箱服务器发送授权登录命令
     * @return
     */
    public String sendAuthLoginCommand(){
        String response = null;
        Socket client = this.getSocket();
        if(client != null){
            if(parseResponseMessageCode(this.responseMessage).equals(ResponseStatusCode.HeloResponse.getStatusCode())){
                try{
                    this.outputStream = client.getOutputStream();
                    writer = new BufferedWriter(new OutputStreamWriter(this.outputStream));
                    writer.write("auth login");
                    writer.newLine();
                    writer.flush();
                    response = reader.readLine();
                    this.responseMessage = response;
                    System.out.println("发送auth login 命令后邮箱服务器返回的状态消息" + response);
                }catch (IOException e){
                    e.printStackTrace();
                }finally{
                    /*try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/
                }
            }
        }
        return response;
    }

    /**
     * 向邮箱服务器发送用户名，需要BASE64加密
     * @param userName
     * @return
     */
    public String sendUserName(String userName){
        String response = null;
        Socket client = this.getSocket();
        if(client != null){
            System.out.println("返回的授权状态码是:" + parseResponseMessageCode(this.responseMessage));
            if(parseResponseMessageCode(this.responseMessage).equals(ResponseStatusCode.AuthResponse.getStatusCode())){
                try{
                    this.outputStream = client.getOutputStream();
                    writer = new BufferedWriter(new OutputStreamWriter(this.outputStream));
                    BASE64Encoder encoder = new BASE64Encoder();
                    String name = encoder.encode(userName.getBytes());
                    writer.write(name);
                    writer.newLine();
                    writer.flush();
                    response = reader.readLine();
                    this.responseMessage = response;
                    System.out.println("向邮箱服务器发送用户名后的返回状态消息" + response);
                }catch (IOException e){
                    e.printStackTrace();
                }finally{
                   /* try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/
                }
            }
        }
        return response;
    }

    /**
     * 向邮箱服务器发送用户的密码， 需要BASE64加密
     * @param password
     * @return
     */
    public String sendUserPassword(String password){
        String response = null;
        Socket client = this.getSocket();
        if(client != null){
            System.out.println("发送用户名后的返回的状态码是:" + parseResponseMessageCode(this.responseMessage));
            if(parseResponseMessageCode(this.responseMessage).equals(ResponseStatusCode.SendUserNameResponse.getStatusCode())){
                try{
                    this.outputStream = client.getOutputStream();
                    writer = new BufferedWriter(new OutputStreamWriter(this.outputStream));
                    BASE64Encoder encoder = new BASE64Encoder();
                    String name = encoder.encode(password.getBytes());
                    writer.write(name);
                    writer.newLine();
                    writer.flush();
                    response = reader.readLine();
                    this.responseMessage = response ;
                    System.out.println("向邮箱服务器发送用户密码后的返回状态消息" + response);
                }catch (IOException e){
                    e.printStackTrace();
                }finally {
                    /*try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/
                }
            }
        }
        return response;
    }

    /**
     * 用户民和密码验证成功后，发送邮件发送的源邮箱地址
     * @param mailFrom
     * @return
     */
    public String afterAuthenticSendMailFrom(String mailFrom){
        String response = null;
        Socket client = this.getSocket();
        if(client != null){
            if(parseResponseMessageCode(this.responseMessage).equals(ResponseStatusCode.AuthenticResponse.getStatusCode())){
                try{
                    this.outputStream = client.getOutputStream();
                    writer = new BufferedWriter(new OutputStreamWriter(this.outputStream));
                    writer.write("mail from:<" + mailFrom + ">");
                    writer.newLine();
                    writer.flush();
                    response = reader.readLine();
                    this.responseMessage = response;
                    System.out.println("发送源邮箱地址命令后邮箱服务器返回的状态消息" + response);
                }catch (IOException e){
                    e.printStackTrace();
                }finally {
                    /*try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/
                }
            }
        }
        return response;
    }

    /**
     * 本函数是授权验证通过后，进行单封邮件的发送，不支持多封邮件的发送
     * @param mailTo
     * @return
     */
    public String afterAuthenicSendMailToSingleDestMail(String mailTo){
        String response = null;
        Socket client = this.getSocket();
        if(client != null){
            System.out.println("发送源邮箱地址后邮箱服务器返回的状态码是" + parseResponseMessageCode(this.responseMessage));
            if(parseResponseMessageCode(this.responseMessage).equals(ResponseStatusCode.MailFromResponse.getStatusCode())){
                try{
                    this.outputStream = client.getOutputStream();
                    writer = new BufferedWriter(new OutputStreamWriter(this.outputStream));
                    writer.write("rcpt to:<" + mailTo + ">");
                    writer.newLine();
                    writer.flush();
                    response = reader.readLine();
                    this.responseMessage = response;
                    System.out.println("发送目的邮箱地址命令后邮箱服务器返回的状态消息" + response);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return response;
    }

    /**
     * 当认证完成后发送目的邮箱的地址
     * @param mailTo
     * @return
     */
    public String afterAuthenticSendMailTo(String mailTo){
        String response = null;
        Socket client = this.getSocket();
        int destMailCount = 0;
        destMailCount = mailTo.split(" ").length;
        String [] destMailArrays = mailTo.split(" ");
        if(client != null){
            System.out.println("发送源邮箱地址后邮箱服务器返回的状态码是" + parseResponseMessageCode(this.responseMessage));
            if(parseResponseMessageCode(this.responseMessage).equals(ResponseStatusCode.MailFromResponse.getStatusCode())){
                try{
                    this.outputStream = client.getOutputStream();
                    writer = new BufferedWriter(new OutputStreamWriter(this.outputStream));
                    for(int i = 0 ; i < destMailCount ; i++){
                        writer.write("rcpt to:<" + destMailArrays[i] + ">");
                        writer.newLine();
                        writer.flush();
                        response = reader.readLine();
                    }
                    this.responseMessage = response;
                    System.out.println("发送目的邮箱地址命令后邮箱服务器返回的状态消息" + response);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return response;
    }

    /**
     * 全部准备工作完成后，发送进行数据发送的确认命令
     * @return
     */
    public String sendDataTransferCommitCommand(){
        String response = null;
        Socket client = this.getSocket();
        if(client != null){
            System.out.println("发送邮箱目的地址后邮箱服务器返回的状态码是" + parseResponseMessageCode(this.responseMessage));
            if(parseResponseMessageCode(this.responseMessage).equals(ResponseStatusCode.MailToResponse.getStatusCode())){
                try{
                    this.outputStream = client.getOutputStream();
                    writer = new BufferedWriter(new OutputStreamWriter(this.outputStream));
                    writer.write("data");
                    writer.newLine();
                    writer.flush();
                    response = reader.readLine();
                    this.responseMessage = response;
                    System.out.println("发送data 确认命令后邮箱服务器返回的状态消息" + response);
                }catch (IOException e){
                    e.printStackTrace();
                }finally{
                   /* try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/
                }
            }
        }
        return response;
    }

    /**
     * 发送邮箱正文内容包括源邮箱地址 目的邮箱地址 邮件主题 邮件的正文内容
     * @param mailFrom
     * @param mailTo
     * @param subject
     * @param content
     * @return
     */
    public String sendMailContent(String mailFrom , String mailTo , String subject ,String content){
        String response = null;
        Socket client = this.getSocket();
        if(client != null){
            if(parseResponseMessageCode(this.responseMessage) != 0){
                try{
                    this.outputStream = client.getOutputStream();
                    writer = new BufferedWriter(new OutputStreamWriter(this.outputStream));
                    writer.write("From:" + mailFrom );
                    writer.newLine();
                    writer.write("To:" + mailTo);
                    writer.newLine();
                    writer.write("Subject:" + subject);
                    writer.newLine();
                    writer.newLine();
                    writer.write(content);
                    writer.newLine();
                    writer.write(".");
                    writer.newLine();
                    writer.flush();
                    response = reader.readLine();
                    this.responseMessage = response;
                    System.out.println("发送邮件正文内容后邮箱服务器返回的状态消息" + response);
                }catch (IOException e){
                    e.printStackTrace();
                }finally{
                    /*try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/
                }
            }
        }
        return response;
    }

    /**
     * 向邮箱服务器发送退出命令
     * @return
     */
    public String sendQuitCommand(){
        String response = null;
        Socket client = this.getSocket();
        if(client != null){
            if(parseResponseMessageCode(this.responseMessage) != 0){
                try{
                    this.outputStream = client.getOutputStream();
                    writer = new BufferedWriter(new OutputStreamWriter(this.outputStream));
                    writer.write("quit");
                    writer.newLine();
                    writer.flush();
                    response = reader.readLine();
                    this.responseMessage = response;
                    System.out.println("发送退出命令后邮箱服务器返回的状态消息" + response);
                }catch (IOException e){
                    e.printStackTrace();
                }finally{
                    try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return response;
    }
    /**
     * 关闭与邮箱服务器的连接，释放掉资源
     * @return
     */
    public boolean closeConnection(){
        try {
            if(this.getSocket() != null){
                this.getSocket().close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("与邮箱服务器连接的socket关闭出现异常");
            return false;
        }
        return true;
    }

    /**
     * 释放掉连接的流的信息
     * @return
     */
    public boolean closeConnectStream(){
        try{
            if(getReader() != null){
                this.reader.close();
            }
            if(getWriter() != null){
                this.writer.close();
            }
        }catch(IOException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public static void main(String [] args){
        StringBuffer sf=new StringBuffer();
        sf.append("北京科技大学计通学院http://10.22.13.172:8080/ustbgao/activateEmail.action?email=");
        sf.append("1234");
        sf.append("&validateCode=");
        sf.append("1234");
        sf.append("点击这里");
        sf.append("激活账号，24小时生效，否则重新验证，请尽快激活!!!");
        MailUtil mailUtil = new MailUtil("smtp.163.com" ,25);
        mailUtil.initMailServerConnect();
        System.out.println(mailUtil.getSocket().getInetAddress().toString());
        System.out.println(mailUtil.getSocket().getLocalAddress().toString());
        System.out.println(mailUtil.getSocket().isConnected());
        mailUtil.sendHeloCommand();
        mailUtil.sendAuthLoginCommand();
        mailUtil.sendUserName("18954182574@163.com");
        mailUtil.sendUserPassword("1245669680");
        mailUtil.afterAuthenticSendMailFrom("18954182574@163.com");
        mailUtil.afterAuthenticSendMailTo("1245669680@qq.com");
        mailUtil.sendDataTransferCommitCommand();
        mailUtil.sendMailContent("18954182574@163.com","1245669680@qq.com","自定义邮件发送框架测试" ,sf.toString());
        mailUtil.sendQuitCommand();
        mailUtil.closeConnection();
        mailUtil.closeConnectStream();
        String destMail = "1245669680@qq.com 18954182574@163.com";
        String destMailAnother = "1245669680@qq.com";
        int destMailCount = destMail.split(" ").length;
        int destMailCountAnotherCount  = destMailAnother.split(" ").length;
        System.out.println("the number of destMail is " + destMailCount);
        System.out.println("the number of destMailAnother is" + destMailCountAnotherCount);
    }
}

