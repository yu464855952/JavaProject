package com.thread.threadTest;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁实现缓存demo
 */
public class CacheDemo {

    private Map<String,Object> cache = new HashMap<String,Object>();
    public static void main(String[] args) {

    }

    private ReadWriteLock rwl = new ReentrantReadWriteLock();
    public Object getData(String key){
        rwl.readLock().lock();
        Object value = null;
        try{
            value  = cache.get(key);
            if (value == null){
                rwl.readLock().unlock();
                rwl.writeLock().lock();
                try {
                    if (value == null){
                        value = "aaa";//查询数据库
                    }
                }finally {
                    rwl.writeLock().unlock();
                }
                rwl.readLock().lock();
            }
        }finally {
            rwl.readLock().unlock();
        }
        return value;
    }
}
