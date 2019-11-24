package com.hanson.design.struct;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

/**
 * Created by Hanson on 2019/6/11 13:00
 * 代理模式
 *  通过代理方式,对被代理的类添加行为.spring的aop就是通过cglib实现的动态代理.也有jdk自带的动态代理.此列采用InvocationHandler实现动态代理方式.
 *  代理模式主要由三个元素共同构成：
 * 　　1）一个接口，接口中的方法是要真正去实现的。
 * 　　2）被代理类，实现上述接口，这是真正去执行接口中方法的类。
 * 　　3）代理类，同样实现上述接口，同时封装被代理类对象，帮助被代理类去实现方法。
 * 优点：
 * 代理模式在客户端与目标对象之间起到一个中介作用和保护目标对象的作用；
 * 代理对象可以扩展目标对象的功能；
 * 代理模式能将客户端与目标对象分离，在一定程度上降低了系统的耦合度；
 *
 * 缺点：
 * 在客户端和目标对象之间增加一个代理对象，会造成请求处理速度变慢；
 * 增加了系统的复杂度；
 */
public class Proxy {
    synchronized  public static void main(String[] args) throws Exception{
        //真实对象
        HttpHandler httpHandler =  new HttpHandlerImpl();
        InvocationHandler  httpHandlerProxy = new HttpHandlerProxy(httpHandler);
        //代理对象
        HttpHandler proxyClass = (HttpHandler) java.lang.reflect.Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), new Class[]{HttpHandler.class}, httpHandlerProxy);
        String param = "我是一枚😊的表情参数";
        //调用参数加密
        param = httpHandler.encrypt(param, HttpHandler.secretKey);

        //代理调用
        String result = proxyClass.handler(param);

        //解密接收参数
        System.out.println("main-->回执:"+result);
        System.out.println("main-->回执解密:"+httpHandler.decrypt(result, HttpHandler.secretKey));
    }
}

interface HttpHandler{
    //密钥需要为16位,并没有补码操作.否则会报Wrong IV length: must be 16 bytes long异常
    String secretKey = "mhfjykMzk3YGBgdh";
    public String handler(String req);

    default String encrypt(String data,String key) throws Exception{
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");//"算法/模式/补码方式"
            //由于使用NoPadding模式,所以不足16位采用补码的方式.否则会报Input length not multiple of 16 bytes异常
            int blockSize = cipher.getBlockSize();
            byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }
            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(key.getBytes(StandardCharsets.UTF_8));
            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            byte[] encrypted = cipher.doFinal(plaintext);
            return new BASE64Encoder().encode(encrypted).trim();
        } catch (Exception e) {
            throw e;
        }
    }

    default String decrypt(String data,String key) throws Exception{
        try {
            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(data);
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(key.getBytes(StandardCharsets.UTF_8));
            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original,StandardCharsets.UTF_8);
            return originalString.trim();
        } catch (Exception e) {
            throw e;
        }
    }
}

class HttpHandlerImpl implements  HttpHandler{
    @Override
    public String handler(String req) {
        String res = "Server rec:"+req;
        System.out.println("接收["+req+"]参数,处理http请求");
        return res;
    }
}

class HttpHandlerProxy implements InvocationHandler {
    HttpHandler httpHandler;
    public HttpHandlerProxy(HttpHandler httpHandler) {
        this.httpHandler = httpHandler;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //所有的代理对象都应该实现InvocationHandler接口,此接口通过动态编译一个被代理对象的子类,并调用Proxy$0的父类invoke相应方法.
//        System.out.println(proxy.getClass().getName());
//        System.out.println(this.httpHandler.getClass().getName());
        String param = args[0].toString();
        //解密
        System.out.println("proxy before-->参数:"+param);
        param = httpHandler.decrypt(param,HttpHandler.secretKey);
        System.out.println("参数 解密后:"+param);
        String result = httpHandler.handler(param);
        System.out.println("回执:"+result);
        //回执加密
        result = httpHandler.encrypt(result, HttpHandler.secretKey);
        System.out.println("回执 加密后:"+result);
        return result;
    }
}