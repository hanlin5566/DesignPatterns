package com.hanson.design.struct;

/**
 * Created by Hanson on 2019/5/30 13:38
 * 桥接模式
 * 意图：将抽象部分与抽象(Abstraction)与行为实现(Implementation)，使它们都可以独立的变化。
 * 主要解决：在有多种可能会变化的情况下，用继承会造成类爆炸问题，桥接模式可以取代多层继承的方式，多使用组合而非继承的形式。
 * 实际项目中可能应用的场景
 * 桥接模式实际开发中应用场景
 * -JDBC驱动程序（API与Driver分离)
 *    ・API分类:CURD
 *    ・距离分类：Mysql，Oracle，SQLServer,h2，impala
 * -银行日志管理：
 *    ・格式分类:操作日志、交易日志、异常日志
 *    ・距离分类：本地记录日志、异地记录日志
 * -人力资源系统中的奖金计算模块：
 *    ・奖金分类:个人奖金、团体奖金、激励奖金。
 *    ・部门分类：人事部门、销售部门、研发部门。
 * -OA系统中的消息处理：
 *    ・业务类型:普通消息、加急消息、特急消息
 *    ・发送消息方式:系统内消息、手机短信、邮件

 * 举个列子，移动电子产品，类别分为笔记本电脑，手机，pad等，品牌分为苹果,华为等。
 *
 * 如果采用继承的方式可能是如下结构
 *
 * electronics
 *      -- notebook
 *          -- apple
 *          -- xiaomi
 *          -- huawei
 *      -- mobile
 *          -- apple
 *          -- xiaomi
 *          -- huawei
 *      -- pad
 *          -- apple
 *          -- xiaomi
 *          -- huawei
 *
 * 如果此时新增了一种产品比如watch或者新增了一个小米品牌都会导致类膨胀，现在就使用桥接模式解决此问题。
 *
 */
public class Bridge {
    public static void main(String[] args) {
        //苹果厂家测试一台macBook,
        AppleProvider macBook = new AppleProvider(new NoteBook());
        macBook.makeTest();
        //苹果厂家测试一台pad
        AppleProvider iPad =new AppleProvider(new Pad());
        iPad.makeTest();
        //添加产品很容易，苹果厂家测试一款watch
        AppleProvider iWatch = new AppleProvider(new Watch());
        iWatch.makeTest();
        /**
         * 如果使用桥接模式，而使用继承，则新增电子产品种类或者品牌都需要新增*m（品牌）或*n（种类）个类。
         */
        //相应的添加厂家也很容易
        XiaomiProvider xiaomiWatch = new XiaomiProvider(new Watch());
        xiaomiWatch.makeTest();

        HuaweiProvider huaweiWatch = new HuaweiProvider(new Watch());
        huaweiWatch.makeTest();


    }
}
//产品的接口
interface ElectronicProduct{
    //开机
    public void bootstrap();
    public void shutdown();
}

/**
 * 笔记本实现类
 */
class NoteBook implements ElectronicProduct{
    @Override
    public void bootstrap() {
        System.out.println("我是一台笔记本，我在进行自检..开机操作");
    }

    @Override
    public void shutdown() {
        System.out.println("我是一台笔记本，我在进行保存..关机操作");
    }
}


/**
 * 手机实现类
 */
class Mobile implements ElectronicProduct{
    @Override
    public void bootstrap() {
        System.out.println("我是一台手机，我在网络注册，自检等..开机操作");
    }

    @Override
    public void shutdown() {
        System.out.println("我是一台手机，我在网络注销，清除缓存等..开机操作");
    }
}
/**
 * Pad实现类
 */
class Pad implements ElectronicProduct{
    @Override
    public void bootstrap() {
        System.out.println("我是一台Pad，我在进行自检..开机操作");
    }

    @Override
    public void shutdown() {
        System.out.println("我是一台Pad，我在进行保存..关机操作");
    }
}

/**
 * TODO:很容易的添加一个watch产品
 */
class Watch implements ElectronicProduct{
    @Override
    public void bootstrap() {
        System.out.println("我是一款手表，我在进行自检，激活时钟以及健康检测..开机操作");
    }

    @Override
    public void shutdown() {
        System.out.println("我是一款手表，我在进行保存，关闭健康检测..关机操作");
    }
}

//厂家的抽象类
abstract class AbstractProvider{
    /**
     * 持有一个产品的引用
     */
    ElectronicProduct product;

    public AbstractProvider(ElectronicProduct product) {
        this.product = product;
    }

    public abstract void printBanner();

    //厂家测试产品是否可用,开机打印banner
    public void makeTest(){
        //开机打印banner
        this.printBanner();
        product.bootstrap();
        product.shutdown();
    }


}


class HuaweiProvider extends AbstractProvider{
    public HuaweiProvider(ElectronicProduct product) {
        super(product);
    }
    @Override
    public void printBanner() {
        System.out.println("华为LOGO");
    }
}

class AppleProvider extends AbstractProvider{
    public AppleProvider(ElectronicProduct product) {
        super(product);
    }
    @Override
    public void printBanner() {
        System.out.println("苹果LOGO");
    }
}

/**
 * 添加小米品牌也很容易
 */

class XiaomiProvider extends AbstractProvider{
    public XiaomiProvider(ElectronicProduct product) {
        super(product);
    }
    @Override
    public void printBanner() {
        System.out.println("小米LOGO");
    }
}