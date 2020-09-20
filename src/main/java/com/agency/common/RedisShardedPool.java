package com.BluerMall.common;

import com.BluerMall.util.*;
import redis.clients.jedis.*;
import redis.clients.util.*;

import java.util.*;

/**
 * @author ch33o
 */
public class RedisShardedPool {

    //ShardedJedisPool连接池
    private static ShardedJedisPool pool;
    //最大连接数
    private static Integer maxTotal = Integer.parseInt(PropertiesUtil.getProperty("redis.max.total", "20"));
    //Jedis中最大的idle(空闲)状态jedis实例个数
    private static Integer maxIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.max.idle", "10"));
    //Jedis中最小的idle(空闲)状态jedis实例个数
    private static Integer minIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.min.idle", "2"));
    //redis1的ip
    private static String redis1Ip = PropertiesUtil.getProperty("redis1.ip");
    //redis1的port
    private static int redis1Port = Integer.parseInt(PropertiesUtil.getProperty("redis1.port"));
    //redis2的ip
    private static String redis2Ip = PropertiesUtil.getProperty("redis2.ip");
    //redis2的port
    private static int redis2Port = Integer.parseInt(PropertiesUtil.getProperty("redis2.port"));
    //redis的password
    private static String redisPassword1 = PropertiesUtil.getProperty("redis1.password");
    //redis的password
    private static String redisPassword2 = PropertiesUtil.getProperty("redis2.password");

    //在borrow一个jedis实例时,是否要进行验证操作,如果赋值为true,则得到的jedis实例肯定是可以用的
    private static Boolean testOnBorrow = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.borrow",
            "true"));

    //在return一个jedis实例时,是否要进行验证操作,如果赋值为true,则放回JKedisPool的jedis实例肯定是可以用的
    private static Boolean testOnReturn = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.return",
            "false"));

    static {
        initPool();
    }

    private static void initPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);

        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(testOnReturn);
        //连接耗尽时,是否阻塞,false会抛出异常,true阻塞知道超时,默认为true
        config.setBlockWhenExhausted(true);

        JedisShardInfo info1 = new JedisShardInfo(redis1Ip,redis1Port,2*1000);
        info1.setPassword(redisPassword1);
        JedisShardInfo info2 = new JedisShardInfo(redis2Ip,redis2Port,2*1000);
        info2.setPassword(redisPassword2);
        List<JedisShardInfo> jedisShardInfoArrayList = new ArrayList<JedisShardInfo>(2);
        jedisShardInfoArrayList.add(info1);
        jedisShardInfoArrayList.add(info2);

        pool = new ShardedJedisPool(config,jedisShardInfoArrayList, Hashing.MURMUR_HASH,
                Sharded.DEFAULT_KEY_TAG_PATTERN);
    }

    public static ShardedJedis getJedis() {
        return pool.getResource();
    }

    public static void returnBrokenResource(ShardedJedis jedis) {
        pool.returnBrokenResource(jedis);
    }

    public static void returnResource(ShardedJedis jedis) {
        pool.returnResource(jedis);
    }

//    public static void main(String[] args) {
//        ShardedJedis jedis = pool.getResource();
//        for (int i=0;i<10;i++){
//            jedis.set("key"+i,"value"+i);
//        }
//        returnResource(jedis);
//
////        pool.destroy();//临时调用,销毁连接池里的所有连接
//        System.out.println("end!");
//    }

}
