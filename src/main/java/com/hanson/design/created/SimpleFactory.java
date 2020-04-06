package com.hanson.design.created;

/**
 * Created by Hanson on 2019/5/27 16:44
 * 一、一句话概括工厂模式
 *
 *  简单工厂：一个工厂类，一个产品抽象类。
 *  工厂方法：多个工厂类，一个产品抽象类。
 *  抽象工厂：多个工厂类，多个产品抽象类。
 * 二、生活中的工厂模式
 *
 *  简单工厂类：一个麦当劳店，可以生产多种汉堡。
 *  工厂方法类：一个麦当劳店，可以生产多种汉堡。一个肯德基店，也可以生产多种汉堡。
 *  抽象工厂类：百胜餐饮集团下有肯德基和百事公司，肯德基生产汉堡，百事公司生成百事可乐。
 *
 * – 简单工厂模式
 * • 用来生产同一等级结构中的任意产品。（对于增加新的产品，需要修改已有代码）
 * – 工厂方法模式
 * • 用来生产同一等级结构中的固定产品。（支持增加任意产品）
 * – 抽象工厂模式
 * • 用来生产不同产品族的全部产品。（对于增加新的产品，无能为力；支持增加产品族）
 *
 * 简单工厂模式
 * 主要解决接口选择的问题,通过标识选择要创建的具体对象.让调用者不去关心具体的实现类.
 *
 *
 */
public class SimpleFactory {

    public static void main(String[] args) {
        MobileFactory mobileFactory = new MobileFactory();
        //如果新增产品(比如欧派手机) 需要修改工厂类,不符合开闭原则,解决办法是使用工厂方法.FactoryMethod
        MobilePhone mobile = mobileFactory.getMobile("huawei");
        mobile.logo();
    }
}



interface MobilePhone{
    public void logo();
}

class HuaWeiMobilePhone implements MobilePhone{

    @Override
    public void logo() {
        System.out.println("华为logo");
    }
}

class XiaoMiMobilePhone implements MobilePhone{

    @Override
    public void logo() {
        System.out.println("小米logo");
    }
}

class OppoMobilePhone implements MobilePhone{

    @Override
    public void logo() {
        System.out.println("oppo logo");
    }
}

class MobileFactory{
    //如果新增产品(比如欧派手机) 需要修改工厂类,不符合开闭原则,解决办法是使用工厂方法.FactoryMethod
    public MobilePhone getMobile(String provide){
        switch (provide){
            case "xiaomi":
                return new XiaoMiMobilePhone();
            case "huawei":
                return new HuaWeiMobilePhone();
            default:
                throw new IllegalStateException("Unexpected value: " + provide);
        }
    }
}