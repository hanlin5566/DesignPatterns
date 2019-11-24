package com.hanson.design.created;

/**
 * Created by Hanson on 2019/5/27 16:44
 * 工厂模式
 * 主要解决接口选择的问题,通过标识选择要创建的具体对象.
 */
public class FactoryMethod {

    public static void main(String[] args) {
        MobileFactory mobileFactory = new MobileFactory();
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

class MobileFactory{
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