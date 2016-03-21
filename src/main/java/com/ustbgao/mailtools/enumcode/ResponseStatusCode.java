package com.ustbgao.mailtools.enumcode;

/**
 * 邮件连接，发送，接收过程中的状态码枚举接口
 *
 * Created by ustbgao on 15-10-30.
 */
public enum ResponseStatusCode {


    InitResponse("初始返回状态码" , 220),
    HeloResponse("helo命令返回状态吗" , 250),
    AuthResponse("auth认证返回状态码" , 334) ,
    SendUserNameResponse("发送用户名后返回的状态码" , 334),
    SendUserPassword("发送用户密码后返回状态码" , 334),
    AuthenticResponse("认证后返回的状态码" , 235),
    MailFromResponse("源邮箱地址发送后返回的状态码" , 250),
    MailToResponse("目的邮箱地址发送后返回的状态吗" , 250);


    private String statusCodeDes;
    private Integer statusCode;

    ResponseStatusCode(String statusCodeDes, Integer statusCode) {
        this.statusCodeDes = statusCodeDes;
        this.statusCode = statusCode;
    }

    public String getStatusCodeDes() {
        return statusCodeDes;
    }

    public void setStatusCodeDes(String statusCodeDes) {
        this.statusCodeDes = statusCodeDes;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public static void main(String []args){
        System.out.println(ResponseStatusCode.InitResponse.getStatusCode());
        for(ResponseStatusCode responseStatucCode : ResponseStatusCode.values()){
            System.out.println(responseStatucCode.getStatusCode());
        }
        System.out.println(ResponseStatusCode.AuthResponse.getStatusCode());
        System.out.println(ResponseStatusCode.SendUserNameResponse.getStatusCode());
    }
}
