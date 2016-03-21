package com.ustbgao.mailtools.serialize;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 对象序列化和反序列化工具类
 */
public class SerializeUtil {
    /**
     * 对对象进行序列化
     * @param object
     * @return
     */
    public static byte[] serialize(Object object){
        ByteArrayOutputStream byteArrayOutputStream=null;
        ObjectOutputStream objectOutputStream=null;
        try{
                byteArrayOutputStream=new ByteArrayOutputStream();
                objectOutputStream=new ObjectOutputStream(byteArrayOutputStream);
                objectOutputStream.writeObject(object);
                byte[] bytes=byteArrayOutputStream.toByteArray();
               return bytes;
        }catch(Exception e){

        }
        return null;
    }

    /**
     * 反序列化生成对象
     * @param bytes
     * @return
     */
    public static Object unserialize(byte[] bytes){
        ByteArrayInputStream arrayInputStream=null;
        ObjectInputStream objectInputStream=null;
        try{
            arrayInputStream=new ByteArrayInputStream(bytes);
            objectInputStream=new ObjectInputStream(arrayInputStream);
            return objectInputStream.readObject();
        }catch(Exception e){

        }
        return null;
    }
}
