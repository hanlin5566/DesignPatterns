package com.hanson.design.behavior;

import java.math.BigDecimal;

/**
 * Created by Hanson on 2020/2/2 21:34
 * Executor 持有算法接口,通过设置算法,变更executor的算法.
 * 其中DefaultInterest是个模板方法.
 */
public class Strategy {

    public static void main(String[] args) {
        long price = 500;//一元 一元商品
        //默认是一级权益
        Executor executor = new Executor(price);
        System.out.println("默认是一级权益");
        executor.offer();
        executor.buy();
        //变更为2级权益,其他不受影响
        Interest l2 = new L2Interest();
        executor.setInterest(l2);
        executor.offer();
        executor.buy();
    }
}

class Executor {
    Interest interest;
    private long price;

    public Executor(long price) {
        this.interest = new L1Interest();
        this.price = price;
        interest.setPrice(price);
    }

    public void setInterest(Interest Interest) {
        this.interest = Interest;
        interest.setPrice(price);
    }
    //报价
    public void offer(){
        System.out.println("该商品:"+interest.getDiscount());
    }
    //消费
    public void buy(){
        //消费N元
        System.out.println("调用账户服务...你消费了"+interest.getDiscount());
        //加积分
        interest.addScore();
    }

}

//权益接口
interface Interest {
    //折扣
    long getDiscount();
    //添加积分
    void addScore();

    void setPrice(long price);
}

//这个其实就是模板方法
abstract class DefaultInterest implements Interest{
    private long price;
    //添加积分默认都是一样的.
    @Override
    public void addScore() {
        long score = calculateUtils.divide(price,"100");//每原价一元,增加1.5积分
        System.out.println("增加"+score+"积分");
    }

    @Override
    public void setPrice(long price) {
        this.price = price;
    }

    public long getPrice() {
        return price;
    }
}

//一级权益
class L1Interest extends  DefaultInterest implements Interest{

    @Override
    public long getDiscount() {
        return calculateUtils.multiply(getPrice(),"0.8");
    }
}


//二级权益
class L2Interest extends  DefaultInterest implements Interest{

    @Override
    public long getDiscount() {
        return calculateUtils.multiply(getPrice(),"0.75");
    }

    @Override
    public void addScore() {
        long score = calculateUtils.divide(getPrice(),"100");//每原价一元,增加2积分
        score = calculateUtils.multiply(score,"2");
        System.out.println("增加"+score+"积分");
    }
}

class calculateUtils{
    public static long multiply(long a,String b){
        BigDecimal aB = new BigDecimal(a);
        BigDecimal  bB = new BigDecimal(b);
        return aB.multiply(bB).longValue();
    }

    public static long divide(long a,String b){
        BigDecimal aB = new BigDecimal(a);
        BigDecimal  bB = new BigDecimal(b);
        return aB.divide(bB).longValue();
    }
}