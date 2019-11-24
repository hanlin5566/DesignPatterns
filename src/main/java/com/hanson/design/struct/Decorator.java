package com.hanson.design.struct;

/**
 * Created by Hanson on 2019/5/31 11:14
 * 装饰模式
 * 动态扩展一个功能
 * 优点：装饰类和被装饰类可以独立发展，不会相互耦合，装饰模式是继承的一个替代模式，装饰模式可以动态扩展一个实现类的功能。
 * 缺点：多层装饰比较复杂。
 *
 * 比如游戏人物中的装备
 */
public class Decorator {
    public static void main(String[] args) {
        Sabre sabre = new Sabre();
        sabre.attack();

        CaiJue caiJue = new CaiJue();
        caiJue.attack();

        NuZhan nuZhan = new NuZhan();
        nuZhan.attack();

        System.out.println("******黑铁强化*******");
        //黑铁强化攻击力
        new HeiTieIntensify(sabre).attack();
        //黑铁强化攻击力
        HeiTieIntensify heiTieIntensifyCaiJue = new HeiTieIntensify(caiJue);
        heiTieIntensifyCaiJue.attack();
        //黑铁强化攻击力
        new HeiTieIntensify(nuZhan).attack();

        System.out.println("******宝石强化裁决*******");
        //宝石强化
        new GemIntensify(heiTieIntensifyCaiJue).attack();
    }
}

//武器接口
interface Weapon{
    int atk();
    String name();
    default void attack() {
        System.out.println("使用"+this.name()+"砍掉你"+this.atk());
    }
}

//斩马刀
class Sabre implements Weapon{
    @Override
    public int atk() {
        //10-15
        return (int)(10+Math.random()*5);
    }
    @Override
    public String name() {
        return "斩马刀";
    }
}
//裁决
class CaiJue implements Weapon{
    @Override
    public int atk() {
        //30 为了方便观察,默认返回30,并不是随机数
        return 30;
    }

    @Override
    public String name() {
        return "裁决";
    }
}
//怒斩
class NuZhan implements Weapon{
    @Override
    public int atk() {
        //22-28
        return (int)(22+Math.random()*6);
    }

    @Override
    public String name() {
        return "怒斩";
    }
}
//强化装饰器
abstract class IntensifyDecorator implements Weapon {
    protected Weapon weapon;

    public IntensifyDecorator(Weapon weapon) {
        this.weapon = weapon;
    }

    @Override
    public String name() {
        return weapon.name();
    }
}
//黑铁矿石强化,每次攻击力+1
class HeiTieIntensify extends IntensifyDecorator{

    public HeiTieIntensify(Weapon weapon) {
        super(weapon);
    }

    @Override
    public int atk() {
        return weapon.atk()+1;
    }
}

//宝石强化.每次攻击力增加0.1倍
class GemIntensify extends IntensifyDecorator{

    public GemIntensify(Weapon weapon) {
        super(weapon);
    }

    @Override
    public int atk() {
        return (int) (weapon.atk()*1.1);
    }
}


