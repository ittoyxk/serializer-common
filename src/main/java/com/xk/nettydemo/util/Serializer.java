package com.xk.nettydemo.util;

/**
 * Description: 序列化工具（程序调用该接口来实现obj<->byte[]之间的序列化/反序列化）
 * Created by: hengxiaokang
 * on 2018/12/27 15:52
 */
public interface Serializer {

    /**
     * 序列化
     *
     * @param obj
     */

    public  <T> byte[] serialize(T obj);

    /**
     * 序列化
     *
     * @param obj
     * @param bytes
     * @param offset
     * @param count
     */
    public void serialize(Object obj, byte[] bytes, int offset, int count);

    /**
     * 反序列化
     *
     * @param bytes -字节数组
     * @return T<T>
     */
    public <T> T deserialize(byte[] bytes);

    /**
     * 反序列化
     *
     * @param bytes
     * @param offset
     * @param count
     * @return
     */
    public <T> T deserialize(byte[] bytes, int offset, int count);
}
