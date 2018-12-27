package com.xk.nettydemo.util;

/**
 * Description: 使用protostuff进行序列化和反序列化
 * Created by: hengxiaokang
 * on 2018/12/27 14:59
 */

import com.dyuproject.protostuff.LinkedBuffer;

import com.dyuproject.protostuff.ProtostuffIOUtil;

import com.dyuproject.protostuff.Schema;

import org.objenesis.Objenesis;

import org.objenesis.ObjenesisStd;

public class SerializationUtils {

    private static SchemaCache schemaCache = SchemaCache.getInstance();
    private static Objenesis objenesis = new ObjenesisStd(true);

    private SerializationUtils()
    {
    }


    //对象 转 字节数组   序列化
    public static <T> byte[] serialize(T obj)
    {
        Class<T> clazz = (Class<T>) obj.getClass();//获得对象的类

        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);//使用LinkedBuffer分配一块默认大小的buffer空间

        try {//通过对象的类构建对应的schema
            Schema<T> schema = getSchma(clazz);
            return ProtostuffIOUtil.toByteArray(obj, schema, buffer);//使用给定的schema将对象序列化为一个byte数组，并返回
        } finally {
            buffer.clear();
        }
    }


    private static <T> Schema<T> getSchma(Class<T> clazz)
    {
        Schema<T> schema = (Schema<T>) schemaCache.get(clazz);
        return schema;
    }


    //字节数组 转 对象   反序列化
    public static <T> T deserialize(byte[] data, Class<T> clazz)
    {
        T message = objenesis.newInstance(clazz);//使用objenesis实例化一个类的对象
        Schema<T> schma = getSchma(clazz);//通过对象的类构建对应的schema
        ProtostuffIOUtil.mergeFrom(data, message, schma);//使用给定的schema将byte数组和对象合并
        return message;
    }

}

