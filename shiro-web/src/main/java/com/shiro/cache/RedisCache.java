package com.shiro.cache;

import com.shiro.util.JedisUtil;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Set;

@Component
public class RedisCache<K,V>  implements Cache<K,V> {

    private final String CACHE_PREFIX = "shiro_cache:";

    private byte[] getKeys(K k){
        if (k instanceof  String){
            return (CACHE_PREFIX + k).getBytes();
        }
        return SerializationUtils.serialize(k);
    }

    @Resource
    private JedisUtil jedisUtil;

    @Override
    public V get(K k) throws CacheException {
        System.out.println("从缓存中获取授权数据...");
        byte[] value = jedisUtil.get(getKeys(k));
        if (value != null){
            return (V) SerializationUtils.deserialize(value);
        }
        return null;
    }

    @Override
    public V put(K k, V v) throws CacheException {
        byte[] key = getKeys(k);
        byte[] value = SerializationUtils.serialize(v);
        jedisUtil.set(key,value);
        jedisUtil.expire(key,600);
        return v;
    }

    @Override
    public V remove(K k) throws CacheException {
        byte[] key = getKeys(k);
        byte[] value = jedisUtil.get(key);
        jedisUtil.delete(key);
        if (value != null){
            return (V) SerializationUtils.deserialize(value);
        }
        return null;
    }

    @Override
    public void clear() throws CacheException {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Set<K> keys() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }
}
