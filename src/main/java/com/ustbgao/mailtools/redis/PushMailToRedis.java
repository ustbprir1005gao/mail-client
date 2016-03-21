package com.ustbgao.mailtools.redis;

import redis.clients.jedis.Jedis;

/**
 * 将定时邮件推送到resis以便到达定时时间的时候进行发送
 *
 * author gaoqi
 * created At 2015/11/21.
 */
public class PushMailToRedis {

    /**
     * 将单发定时邮件推送到redis
     * @param jedis
     * @param key
     * @param o
     */
    public static void pushSingleMailToRedis(Jedis jedis, String key,Object o){

        RedisOperation.putValueToRedis(jedis, key, o);

    }

    /**
     * 将群发邮件推送到redis
     * @param key
     * @param o
     */
    public static void pushGroupMailToRedis(Jedis jedis, String key, Object o){

        RedisOperation.putValueToRedis(jedis, key, o);
    }
}
