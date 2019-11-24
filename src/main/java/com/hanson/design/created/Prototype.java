package com.hanson.design.created;

import java.io.*;

/**
 * Created by Hanson on 2019/5/28 15:21
 * 原型模式,通过复制的形式创建对象.当创建对象比较耗费资源时,比如查询数据库,可以使用此方式.
 * java中分为深拷贝和浅拷贝
 * 对于对象中的引用类型采用复制引用则认为是浅拷贝,对引用类型使用重新创建对象则为深拷贝.
 * Cloneable接口为浅拷贝,如果需要深拷贝,可以对其引用类型的字段实现Cloneable接口,并在clone实现中重新调用成员变量的clone方法,创建新对象,如果是数组需要新建一个数组,否则数组的引用也是指向原有对象的地址的..
 */
public class Prototype {
    public static void main(String[] args) throws Exception{
        long sTime = System.currentTimeMillis();
        System.out.println("构建对象@"+sTime);
        Dictory dictory = new Dictory();
        System.out.println("构建对象耗时"+(System.currentTimeMillis()-sTime));
        //第一次打印原始对象
        System.out.println("-----------第一次打印原始对象----------");
        dictory.print();
        //复制构建对象
        sTime = System.currentTimeMillis();
        System.out.println("构建复制对象@"+sTime);
        Dictory dictoryCopy = dictory.clone();
        System.out.println("构建复制对象耗时"+(System.currentTimeMillis()-sTime));
        System.out.println("-----------第一次打印复制对象----------");
        dictoryCopy.print();
        //由于对象的引用类型字段未使用深复制,只是复制了引用,所以操作对象中的引用类型对象会改变其值.基本类型不会发生此情况.
        //可以使引用类型的字段也实现克隆接口,即可实现深复制,创建新的引用,并不会互相干扰.
        dictoryCopy.setCount(5);
        dictoryCopy.setRecords();
        //打印原对象
        System.out.println("-----------再次打印原对象----------");
        dictory.print();
        //打印复制对象
        System.out.println("-----------再次打印复制对象----------");
        dictoryCopy.print();
    }


    public static <T extends Serializable> T deepCloneObject(T object) {
        T deepClone = null;
        ByteArrayOutputStream baos = null;
        ObjectOutputStream oos = null;
        ByteArrayInputStream bais = null;
        ObjectInputStream ois = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            bais = new ByteArrayInputStream(baos
                    .toByteArray());
            ois = new ObjectInputStream(bais);
            deepClone = (T)ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                baos.close();
                oos.close();
                bais.close();
                ois.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return deepClone;
    }
}

class Dictory implements Cloneable,Serializable{
    private int count = 1;
    private Record record = new Record("OOOOOO","OOOOOO","OOOOOO");
    private DeepCopyRecord deepCopyRecord = new DeepCopyRecord("DeepOOOOOO","DeepOOOOOO","DeepOOOOOO");
    private Record [] records = new Record [count];
    private DeepCopyRecord [] deepRecords = new DeepCopyRecord [count];
    public Dictory(){
        for (int i = 0; i < count; i++) {
            //模拟耗时延时
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Record record = new Record("name"+i,"phone"+i,"address"+i);
            records[i] = record;

            DeepCopyRecord deepRecord = new DeepCopyRecord("name"+i,"phone"+i,"address"+i);
            deepRecords[i] = deepRecord;
        }
    }

    public void setRecords() {
        records[0].setName("Shallow Copy");
        deepRecords[0].setName("Deep Copy");
        record.setName("field Shallow CopyXXXXXXXXXXXXXXXXX");
        deepCopyRecord.setName("field Deep CopyXXXXXXXXXXXX");
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Dictory clone() throws CloneNotSupportedException
    {
        Dictory clone = (Dictory) super.clone();
        if(deepRecords.length > 0){
            //循环深复制,成员变量
            clone.deepRecords = new DeepCopyRecord [deepRecords.length];
            for (int i = 0; i < deepRecords.length; i++) {
                clone.deepRecords[i] = deepRecords[i].clone();
            }
        }
        //深复制,对象成员变量
        clone.deepCopyRecord = deepCopyRecord.clone();
        return clone;
    }

    public void print(){
        System.out.println("total record:"+ count);
        for (Record record : records) {
            record.show();
        }

        for (DeepCopyRecord record : deepRecords) {
            record.show();
        }
        record.show();
        deepCopyRecord.show();
    }




}

class Record{
    private String name;
    private String phone;
    private String address;

    Record(String name, String phone, String address) {
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void show(){
        System.out.println(String.format("name:%s,phone:%s,address:%s,hash:%s", name, phone, address, this));
    }
}

class DeepCopyRecord implements Cloneable{
    private String name;
    private String phone;
    private String address;

    DeepCopyRecord(String name, String phone, String address) {
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void show(){
        System.out.println(String.format("name:%s,phone:%s,address:%s,hash:%s", name, phone, address, this));
    }

    public DeepCopyRecord clone() throws CloneNotSupportedException{
        //实现深度克隆
        DeepCopyRecord clone = (DeepCopyRecord)super.clone();
        return clone;
    }
}