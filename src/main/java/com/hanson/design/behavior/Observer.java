package com.hanson.design.behavior;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hanson on 2020/2/2 23:25
 * 发布订阅模式,当被观察者发生变化时,则通知他所有的订阅者. 被观察者与订阅者是1:N.
 */
public class Observer {

    public static void main(String[] args) {
        Subject subject = new Subject();
        Subscriber subscriber1 = new Subscriber("hanson");
        Subscriber subscriber2 = new Subscriber("daianna");
        subject.register(subscriber1);
        subject.register(subscriber2);

        Publish publish = new Publish(subject);
        publish.publish("message one");
        //取消订阅
        subject.cancel(subscriber2);

        publish.publish("message two");

    }
}

//被观察者 Observer,也是发布者 Publish 持有主题
class Publish{
    private Subject subject;

    public Publish(Subject subject) {
        this.subject = subject;
    }

    public void publish(String msg){
        subject.notify(msg);
    }
}


class Subscriber{
    String name;

    public Subscriber(String name) {
        this.name = name;
    }

    public void recive(String msg){
        System.out.println(name+"收到了发布者的消息"+msg);
    }
}


//主题,持有所有订阅者
class Subject {
    List<Subscriber> subscribers = new ArrayList<>();

    public void register(Subscriber subscriber){
        subscribers.add(subscriber);
    }

    public void cancel(Subscriber subscriber){
        subscribers.remove(subscriber);
    }

    //通知所有的订阅者
    public void notify(String msg){
        for (Subscriber sub : subscribers) {
            sub.recive(msg);
        }
    }
}