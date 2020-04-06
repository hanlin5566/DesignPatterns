package com.hanson.design.created;

/**
 * Created by Hanson on 2020/2/2 1:12
 * 与简单工厂相比,FactoryMethod工厂方法可以扩展新的产品,而不需要修改 简单工厂类的代码.符合开闭原则.
 * 相当于工厂方法,不再是一个类,而是一个接口.可以通过实现此接口扩展工厂类.
 *
 * 可以相对容易的扩展一个产品族,但扩展产品需要修改代码.
 */
public class FactoryMethod {
    public static void main(String[] args) {
        MobileFactoryMethod mobileFactoryMethod = new OppoMobileFactory();
        MobilePhone mobile = mobileFactoryMethod.getMobile();
        mobile.logo();
    }
}
//工厂接口
interface MobileFactoryMethod{
    public MobilePhone getMobile();
}

class XiaoMiMobileFactory implements MobileFactoryMethod{
    @Override
    public MobilePhone getMobile() {
        return new XiaoMiMobilePhone();
    }
}

class HuaWeiMobileFactory implements MobileFactoryMethod{
    @Override
    public MobilePhone getMobile() {
        return new HuaWeiMobilePhone();
    }
}

/**
 * 相对容易的扩展一个产品族
 */
class OppoMobileFactory implements MobileFactoryMethod{
    @Override
    public MobilePhone getMobile() {
        return new OppoMobilePhone();
    }
}

