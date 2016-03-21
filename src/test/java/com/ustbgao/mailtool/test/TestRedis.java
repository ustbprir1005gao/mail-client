package com.ustbgao.mailtool.test;

import com.ustbgao.mailtools.bean.ConnectionBean;
import com.ustbgao.mailtools.constant.RedisConstant;
import com.ustbgao.mailtools.redis.RedisOperation;
import redis.clients.jedis.Jedis;

/**
 * author gaoqi
 * created At 2015/3/21.
 */
public class TestRedis {

    public static void main(String [] args){
        Jedis jedis = RedisOperation.getConnection(RedisConstant.REDIS_HOST, RedisConstant.REDIS_PORT);
        if(jedis == null){
            System.out.println("连接未成功，请检查连接");
        }else {
            ConnectionBean connectionBean = new ConnectionBean();
            connectionBean.setHost("localhost");
            RedisOperation.putValueToRedis(jedis, "test", connectionBean);
            ConnectionBean connectionBean1 = (ConnectionBean)RedisOperation.getValueByKey(jedis,"test".getBytes());
            System.out.println(connectionBean1.getHost());
        }
    }
}
