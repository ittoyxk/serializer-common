package com.xk.nettydemo.util;

import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Description: netty-demo
 * Created by: hengxiaokang
 * on 2018/12/27 14:59
 */
public class SchemaCache {

    //使用单例模式，构建缓存类
    private static class SchemaCacheHolder {
        private static SchemaCache cache = new SchemaCache();
    }

    public static SchemaCache getInstance()
    {
        return SchemaCacheHolder.cache;
    }

    /**
     * 使用cacheBuilder生成器生成缓存
     * maximumSize:缓存如果达到限额，则回收(接近时就会启动回收)
     * expireAfterAccess:缓存在给定时间没有被访问，则回收
     */
    private Cache<Class<?>, Schema<?>> cache = CacheBuilder.newBuilder().maximumSize(1024).expireAfterAccess(1, TimeUnit.HOURS).build();

    /**
     * cache.get(k,Callable)方法返回缓存中的值，或者使用call方法将结果加入到缓存中。
     */
    private Schema<?> get(final Class<?> clazz, Cache<Class<?>, Schema<?>> cache)
    {
        try {
            return cache.get(clazz, new Callable<RuntimeSchema<?>>() {
                public RuntimeSchema<?> call() throws Exception
                {
                    return RuntimeSchema.createFrom(clazz);
                }
            });
        } catch (ExecutionException e) {
            return null;
        }
    }

    public Schema<?> get(final Class<?> clazz)
    {
        return get(clazz, cache);
    }
}
