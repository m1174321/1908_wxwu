package com.fh.util;

import com.alibaba.fastjson.JSONObject;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;

public class RedisPool {

    //从静态块中初始化
    private static JedisPool jedisPool;

    private RedisPool(){

    }

    //static  静态块
    static {
        //创建redis池的配置
        JedisPoolConfig jedisPoolConfig=new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(3);
        jedisPoolConfig.setMaxIdle(2);
        jedisPoolConfig.setMinIdle(1);
        jedisPoolConfig.setMaxWaitMillis(30000);
        //初始化redis池
        jedisPool=new JedisPool(jedisPoolConfig,"192.168.79.133",6379);
    }
    //从池中拿连接
    public static Jedis getJedis(){
        return jedisPool.getResource();
    }
    //连接用还给池中
    public static void returnJedis(Jedis jedis){
        jedisPool.returnResource(jedis);
    }

    //String 类型 存值
    public static void setRedis(String key,String value){
        Jedis jedis = getJedis();
        jedis.set(key,value);
        returnJedis(jedis);
    }
    //String 类型 取值
    public static String getRedis(String key){
        Jedis jedis = getJedis();
        String value = jedis.get(key);
        returnJedis(jedis);
        return value;
    }
    // String 类型 取值 含有存储时间
    public static String getexRedis(String key){
        Jedis jedis = getJedis();
        String value = jedis.get(key);
        returnJedis(jedis);
        return value;
    }
    //String 类型 存值 含有存储时间
    public static void setexRedis(String key,int seconds,String value){
        Jedis jedis = getJedis();
        jedis.setex(key,seconds,value);
        returnJedis(jedis);
    }
    //hash 类型 取值
    public static String hgetRedis(String key,String filed){
        Jedis jedis = getJedis();
        String value = jedis.hget(key, filed);
        returnJedis(jedis);
        return value;
    }

    //hash 类型 取值
    public static Object hgetRedisObj(String key,String filed,Object obj){
        Jedis jedis = getJedis();
        String value = jedis.hget(key, filed);
        Object object = JSONObject.parseObject(value, obj.getClass());
        returnJedis(jedis);
        return object;
    }

    //hash 类型 存值
    public static void hsetRedis(String key,String filed,Object value){
        String values = JSONObject.toJSONString(value);
        Jedis jedis = getJedis();
        jedis.hset(key,filed,values);
        returnJedis(jedis);
    }

    //hash 类型 获取 hash长度
    public static long hlenRedis(String key) {
        Jedis jedis = getJedis();
        Long hlen = jedis.hlen(key);
        returnJedis(jedis);
        return hlen;
    }

    public static List<String> hvals(String key){
        Jedis jedis = getJedis();
        List<String> hvals = jedis.hvals(key);
        returnJedis(jedis);
        return hvals;
    }

}
