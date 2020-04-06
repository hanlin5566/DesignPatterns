package com.hanson.design.struct;

/**
 * Created by Hanson on 2019/5/30 10:56
 * 适配器模式
 *
 * 动态转换一个接口
 * 动态的增加新的功能,改变原接口的行为。
 * 核心代码：原接口 ，变更接口 ，适配器
 * 适配器持有变更接口的引用,并实现原接口，在调用处注入适配器。
 *
 * 将一个类的接口转换成客户希望的另外一个接口。适配器模式使得原本由于接口不兼容而不能一起工作的那些类可以一起工作。
 * 主要解决在软件系统中，常常要将一些"现存的对象"放到新的环境中，而新环境要求的接口是现对象不能满足的。
 * 比如说苹果耳机取消了3.5mm接口,改为typec接口,现在只有3.5mm接口的耳机,需要一个转换器.
 * 主要实现方式，就是通过一个类实现原有接口，并使用新的接口实现。
 */
public class Adapter {
    public static void main(String[] args) {
        //如果有typeC耳机插入typeC耳机
        IphoneX iphoneX1 = new IphoneX(new TypecSocketImpl());
        iphoneX1.connectRadioSocket();
        //如果只有3.5mm,那么只能借助转换器.
        IphoneX iphoneX2 = new IphoneX(new ThreeAdapterTypecSocket(new ThreemmSocketImpl()));
        iphoneX2.connectRadioSocket();
    }
}

/**
 * iphonex 只列举了耳机插口,只能兼容typeC接口
 */
class IphoneX{
    private TypecSocket headphoneSocket;

    public IphoneX(TypecSocket headphoneSocket) {
        this.headphoneSocket = headphoneSocket;
    }

    public void connectRadioSocket(){
        headphoneSocket.typecSocket();
    }
}

interface TypecSocket{
    void typecSocket();
}

class TypecSocketImpl implements TypecSocket{
    @Override
    public void typecSocket() {
        System.out.println("插入typeC接口耳机");
    }
}

interface ThreemmSocket{
    void threemmSocket();
}

class ThreemmSocketImpl implements ThreemmSocket{
    @Override
    public void threemmSocket() {
        System.out.println("插入3.5mm接口耳机");
    }
}
//3.5mm转typec接口适配器
class ThreeAdapterTypecSocket implements TypecSocket{
    ThreemmSocket threemmSocket;
    public ThreeAdapterTypecSocket(ThreemmSocket threemmSocket) {
        this.threemmSocket = threemmSocket;
    }

    @Override
    public void typecSocket() {
        System.out.println("通过我的转换,我可以把3.5mm接口耳机插入typeC接口中");
        threemmSocket.threemmSocket();
    }
}