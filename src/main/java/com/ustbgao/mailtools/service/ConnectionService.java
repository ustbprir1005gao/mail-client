package com.ustbgao.mailtools.service;

/**
 * 连接到邮箱服务器的相关接口定义
 *
 * author gaoqi
 * created At 2015/10/21.
 */
public interface ConnectionService {
    /**
     * 初始化与邮箱服务器连接的相关设置
     *
     * @return
     */
    public String initMailServerConnect(String serverHost, int serverPort);

    /**
     * 发送helo 字符串与邮箱服务器获得连接
     *
     * @return
     */
    public String sendHeloCommand(String sendHeloMsg);

    /**
     * helo 字符串发送邮箱服务器成功后，开始进行授权登录命令发送
     * @return
     */
    public String sendAuthLoginCommand(String authMsg);

    /**
     * 向邮箱服务器发送用户的用户名(需要BASE64加密)
     *
     * @param userName
     * @return
     */
    public String sendUserName(String userName);

    /**
     * 向邮箱服务器发送用户邮箱的密码(需要BASE64加密)
     * @param passWord
     * @return
     */
    public String sendUserPassword(String passWord);

    /**
     * 用户名和密码验证成功后，发送邮件发送的源邮箱地址
     * @param mailFrom
     * @return
     */
    public String afterAuthenticSendMailFrom(String mailFrom);

    /**
     * 当认证完成后发送目的邮箱的地址
     * @param mailTo
     * @return
     */
    public String afterAuthenticSendMailTo(String mailTo);

    /**
     * 全部准备工作完成后，发送进行数据发送的确认命令
     * @return
     */
    public String sendDataTransferCommitCommand();
    /**
     * 向邮箱服务器发送断开连接命令
     * @return
     */
    public String sendQuitCommand();

    /**
     * 关闭与邮箱服务器的链接，并且释放相应的资源
     *
     * @return
     */
    public boolean closeConnection();
}
