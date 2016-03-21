package com.ustbgao.mailtools.redis;

import com.ustbgao.mailtools.bean.SendMailBean;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * 拉取存储在redis服务器的邮件
 *
 * author gaoqi
 * created At 2015/9/21.
 */
public class PullMailFromRedis {

    public static List<SendMailBean> pullSingleMailFromRedis(Jedis jedis, String key){
        RedisOperation.getValueByKey(jedis, key.getBytes());

        return null;
    }
}
