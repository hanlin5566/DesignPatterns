package com.hanson.design.created;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Hanson on 2019/5/27 15:37
 * 单例模式
 * 某个类只能生成一个实例，该类提供了一个全局访问点供外部获取该实例，其拓展是有限多例模式。
 * 1.StarvingSingleton 饿汉模式(静态变量引用,跟随类一起加载)
 * 2.懒汉(线程非安全)
 * 3.懒汉(线程安全)
 * 4.懒汉(双重检验)
 * 5.静态内部类(懒汉,无锁,调用时通过JVM加载类时创建)
 */
public class Singleton {
    public static void main(String[] args) {
        int threadCount = 20;
        final Map<Object,Object> map = new HashMap<Object, Object>();
        final CountDownLatch latch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //多线程模拟创建对象
                    StarvingSingleton starving = StarvingSingleton.getInstance();//饿汉
                    LazySingletonUnSafe lazyUnSafe = LazySingletonUnSafe.getInstance();//饿汉线程非安全
                    lazyUnSafe.hashCode();
                    LazySingletonSynchronized lazySingletonSynchronized = LazySingletonSynchronized.getInstance();//线程安全,锁粒度大.
                    LazySingletonDoubleCheck lazySingletonDoubleCheck = LazySingletonDoubleCheck.getInstance();//线程安全,双重校验
                    SingletonInnerClass innerClass = SingletonInnerClass.getInstance();//静态内部类
                    map.put(starving,starving);
                    map.put(lazyUnSafe,lazyUnSafe);
                    map.put(lazySingletonSynchronized,lazySingletonSynchronized);
                    map.put(lazySingletonDoubleCheck,lazySingletonDoubleCheck);
                    map.put(innerClass,innerClass);
                    latch.countDown();
                }
            }).start();
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //执行完打印看是否有重复
        for (HashMap.Entry entry :map.entrySet()) {
            System.out.println(entry.getKey()+" v:"+entry.getValue());
        }

    }

}

class StarvingSingleton{
    private static final StarvingSingleton instance;
    static {
        instance = new StarvingSingleton();
    }

    private StarvingSingleton(){}

    public static StarvingSingleton getInstance(){
        return instance;
    }
}

class LazySingletonUnSafe{
    private static LazySingletonUnSafe instance;

    private LazySingletonUnSafe(){}

    /**
     * 线程不安全
     * @return
     */
    public static LazySingletonUnSafe getInstance(){
        if(instance == null){
            instance = new LazySingletonUnSafe();
        }
        return instance;
    }
}

class LazySingletonSynchronized{
    private static LazySingletonSynchronized instance;

    private LazySingletonSynchronized(){}

    /**
     * 线程安全,锁粒度大
     * @return
     */
    public synchronized static LazySingletonSynchronized getInstance(){
        if(instance == null){
            synchronized (LazySingletonSynchronized.class){
                instance = new LazySingletonSynchronized();
            }
        }
        return instance;
    }
}

class LazySingletonDoubleCheck{
    //1. 声明为volatile的本实例
    private static volatile LazySingletonDoubleCheck instance;

    private LazySingletonDoubleCheck(){}

    public static LazySingletonDoubleCheck getInstance(){
        //2. 判断为空则第一次尝试进行加载
        if(instance == null){
            //3. 对对象加锁
            synchronized (LazySingletonDoubleCheck.class){
                //4. 获取到锁,但此时有可能其他对象已经初始化完成.
                if(instance == null){
                    //5. 如果还是为null则,生成对象.
                    instance = new LazySingletonDoubleCheck();
                }
            }
        }
        return instance;
    }
}

class SingletonInnerClass {

    private SingletonInnerClass() {}

    private static class SingletonStaticInnerClass {
        private static final SingletonInnerClass INSTANCE = new SingletonInnerClass();
    }

    public static SingletonInnerClass getInstance() {
        return SingletonStaticInnerClass.INSTANCE;
    }
}