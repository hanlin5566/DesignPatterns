package com.hanson.design.behavior;

/**
 * Created by Hanson on 2020/2/2 22:53
 * 预留方法类似于一个钩子,对外暴露变更,隐藏实现步骤.
 * 模板方法可以与策略模式组合使用,模板规定步骤,策略变更算法.
 */
public class TemplateMethod {
    public static void main(String[] args) {
        DbOperationTemplateMethod insert = new InsertOperation();
        insert.exec();

        DbOperationTemplateMethod update = new UpdateOperation();
        update.exec();
    }
}

//模拟操作数据库
abstract class DbOperationTemplateMethod{
    private void connect(){
        System.out.println("连接数据库");
    }
    //这个通常都是放策略模式
    abstract void operation();

    private void release(){
        System.out.println("断开连接数据库");
    }

    public void exec(){
        this.connect();
        this.operation();
        this.release();
    }
}

//插入操作,无需关心连接,专注操作即可.
class InsertOperation extends DbOperationTemplateMethod{

    @Override
    void operation() {
        System.out.println("插入");
    }
}

//更新操作,无需关心连接,专注操作即可.
class UpdateOperation extends DbOperationTemplateMethod{

    @Override
    void operation() {
        System.out.println("更新");
    }
}