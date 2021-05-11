package com.frame.utils;


import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.cache.CacheException;
import org.apache.ibatis.cache.decorators.BlockingCache;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RedisUtils {
    private long timeout;
    private static RedisTemplate<String,Object> redisTemplate;
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final ConcurrentHashMap<Object, ReentrantLock> locks;

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        RedisUtils.redisTemplate = redisTemplate;
    }
    public RedisUtils(){this.locks = new ConcurrentHashMap<Object, ReentrantLock>();}

    private ReentrantLock getLockForKey(Object key) {
        ReentrantLock lock = new ReentrantLock();
        ReentrantLock previous = locks.putIfAbsent(key, lock);
        return previous == null ? lock : previous;
    }
    private void acquireLock(Object key) {
        Lock lock = getLockForKey(key);
        if (timeout > 0) {
            try {
                boolean acquired = lock.tryLock(timeout, TimeUnit.MILLISECONDS);
                if (!acquired) {
                    throw new CacheException("Couldn't get a lock in " + timeout + " for the key " +  key);
                }
            } catch (InterruptedException e) {
                throw new CacheException("Got interrupted while trying to acquire lock for key " + key, e);
            }
        } else {
            lock.lock();
        }
    }

    private void releaseLock(Object key) {
        ReentrantLock lock = locks.get(key);
        if (lock.isHeldByCurrentThread()) {
            lock.unlock();
        }
    }

    public void putObject(String map,Object key, Object value) {
        try{
            if(null!=value)
                redisTemplate.boundHashOps(map).put(key,value);
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            releaseLock(key);
        }
    }


    public Object getObject(String map,Object key) {
        acquireLock(key);
        try{
            if(null!=key)
                return redisTemplate.boundHashOps(map).get(key.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            releaseLock(key);
        }
        return null;
    }


    public Object removeObject(String map) {
        try{
            if(null!=map)
                redisTemplate.delete(map);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    public void clear() {
        Long size=redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redisConnection) throws DataAccessException {
                Long size = redisConnection.dbSize();
                //连接清除数据
                redisConnection.flushDb();
                redisConnection.flushAll();
                return size;
            }
        });
    }


    public int getSize() {
        Long size = redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection)
                    throws DataAccessException {
                return connection.dbSize();
            }
        });
        return size.intValue();
    }


    public ReadWriteLock getReadWriteLock() {
        return this.readWriteLock;
    }
    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }
}

