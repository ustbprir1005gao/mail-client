package com.ustbgao.mailtools.parse;

/**
 * 对于邮箱服务器返回的信息进行解析
 *
 * author gaoqi
 * created At 2015/10/21.
 */
public class ParseResponseMessage {


    public static int parseResponseMessageCode(String response){
        String [] strings = response.split(" ");
        int responseCode = 0;
        if(strings.length > 0){
            responseCode = Integer.parseInt(strings[0]);
        }
        return  responseCode;
    }
}
