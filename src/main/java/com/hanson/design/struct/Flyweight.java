package com.hanson.design.struct;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hanson on 2019/5/31 17:13
 * 简单来说就是使用本地缓存,避免重复创建大对象.
 */
public class Flyweight {
    Map<String,BigObject> map = new HashMap<String,BigObject>();

    public static void main(String[] args) {

        Flyweight flyweight = new Flyweight();
        BigObject b1 = flyweight.getInstance("b1");
        BigObject b2 = flyweight.getInstance("b2");
        BigObject b3 = flyweight.getInstance("b1");
        System.out.println("b1 == b2 "+(b1 == b2));
        System.out.println("b1 == b3 "+(b1 == b3));
    }

    public BigObject getInstance(String key){
        BigObject bigObject;
        if(map.containsKey(key)){
            bigObject = map.get(key);
        }else{
            bigObject = new BigObject(key);
            map.put(key,bigObject);
        }
        return bigObject;
    }
}

class BigObject{
    byte bigMemory [] = new byte [1024*1024*100];
    String key;

    public BigObject(String key) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        this.key = key;
    }

    public byte[] getBigMemory() {
        return bigMemory;
    }
}