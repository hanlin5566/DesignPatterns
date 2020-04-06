package com.hanson.design.created;

import java.util.concurrent.TimeoutException;

/**
 * Created by Hanson on 2020/2/1 22:27
 * 抽象工厂
 * 动态添加一个工厂及添加工厂所需要的方法,不需要修改原有类.
 *
 * 工厂方法只考虑一类产品(一个接口)的生产
 * 抽象工厂考虑的是一个产品族,比如有一个电子产品的抽象工厂...
 *      _______
 *
 *        相同的Y坐标,产品等级,扩展简单,只要
 *      _______
 *  |   |     |
 *  |   | 手机 |    路由       电脑
 *  |   |-----|--------------------------
 *  |   |note2|   Mi001      MiAir          小米 |  产品族 | 相同的X坐标,产品族
 *  产  |-----|--------------------------
 *  品  |nova2|   AR001      HWPro          华为 |  产品族 |
 *  族  |     |
 *  |   |     |
 *  |   |     \
 *  --------产品等级-------------------------
 */
public class AbstractFactory {
    public static void main(String[] args) throws Exception,RuntimeException, TimeoutException {
        //我可以方便的添加品牌.但是想添加品类就比较麻烦.
        ElectronicAbstractFactory electronicAbstractFactory = new HuaweiConcreteFactory();
        MobilePhone mobile = electronicAbstractFactory.getMobile();
        mobile.logo();

        Router router = electronicAbstractFactory.getRouter();
        router.iptables();
    }
}

//抽象工厂,产品族工厂(多个产品,多个接口),定义产品族,每个品牌可以生产如下产品
interface ElectronicAbstractFactory{
    public MobilePhone getMobile();
    public Router getRouter();
}

/**
 * 具体工厂,实现抽象工厂
 */

//华为具体工厂
class HuaweiConcreteFactory implements ElectronicAbstractFactory{
    @Override
    public MobilePhone getMobile() {
        return new HuaWeiMobilePhone();
    }

    @Override
    public Router getRouter() {
        return new HuaWeiRouter();
    }
}


//小米具体工厂
class XiaomiConcreteFactory implements ElectronicAbstractFactory{

    @Override
    public MobilePhone getMobile() {
        return new XiaoMiMobilePhone();
    }

    @Override
    public Router getRouter() {
        return new HuaWeiRouter();
    }
}


interface Router{
    public void iptables();
}


class XiaoMiRouter implements Router{

    @Override
    public void iptables() {
        System.out.println("小米路由表");
    }
}

class HuaWeiRouter implements Router{

    @Override
    public void iptables() {
        System.out.println("华为路由表");
    }
}