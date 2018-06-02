package com.bintonto.dao.cache;

import com.bintonto.entity.QiangGou;
import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private JedisPool jedisPool;

    public RedisDao(String ip, int port) {
        jedisPool = new JedisPool(ip, port);
    }

    private RuntimeSchema<QiangGou> schema = RuntimeSchema.createFrom(QiangGou.class);

    /**
     * get from cache
     * if null
     *  get db
     * else
     *   put cache
     * logic
     */
    public QiangGou getQiangGou(long qiangGouId) {

        try {
            //连接数据库连接池
            Jedis jedis = jedisPool.getResource();

            try {
                String key = "qiangGou:"+qiangGouId;
                //Jedis并没有实现内部序列化操作
                // get -> byte[] -> 反序列化 -> Object(QiangGou)
                // protostuff : pojo.
                byte[] bytes = jedis.get(key.getBytes());
                //缓存获取到
                if (bytes != null) {
                    //空对象
                    QiangGou qiangGou = schema.newMessage();
                    ProtostuffIOUtil.mergeFrom(bytes, qiangGou, schema);
                    //seckill 被反序列化
                    return qiangGou;
                }

            } finally {
                jedis.close();
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return null;
    }

    public String putQiangGou(QiangGou qiangGou) {
        // set Object(QiangGou) -> 序列化 -> byte[]
        try {
            Jedis jedis = jedisPool.getResource();

            try {
                String key = "qiangGou:"+qiangGou.getQiangGouId();
                byte[] bytes = ProtostuffIOUtil.toByteArray(qiangGou, schema,
                        LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
                //超时缓存
                int timeout = 60 * 60; // 1小时
                String result = jedis.setex(key.getBytes(), timeout, bytes);
                return result;

            } finally {
                jedis.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return null;
    }
}
