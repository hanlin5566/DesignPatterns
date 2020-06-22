package com.hanson.design.behavior;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Hanson on 2020/2/4 21:00
 * 调用者角色与接收者角色之间没有任何依赖关系，调用者实现功能时只需调用Command抽象类的execute方法就可以，不需要了解到底是哪个接收者执行。
 */
public class Command {
    public static void main(String[] args) throws Exception{
//        Connection connection = DriverManager.getConnection("");
//        connection.setAutoCommit(false);
//        Statement statement = connection.createStatement();
//        statement.execute("");
//        statement.execute("");
//        connection.rollback();
        Executeor invoker = new Executeor();
        //命令...
        ConcreteCommand command1 = new ConcreteCommand(new Receiver().sql("test1"));
        ConcreteCommand command2 = new ConcreteCommand(new Receiver().sql("test2"));
        invoker.addCommand(command1);
        invoker.addCommand(command2);
        invoker.commint();


    }
}

//reciver 该角色就是干活的角色，命令传递到这里应该被执行的，具体到上面的三个实现类：
class Receiver{
    public String sql;
    public int count;

    public Receiver sql(String sql){
        this.sql = sql;
        return this;
    }
    public Receiver count(int count){
        this.count = count;
        return this;
    }


    public String getSql() {
        return sql;
    }

    public int getCount() {
        return count;
    }
}

//Command 需要执行的所有命令都在这里声明。
interface ICommand {
    public boolean redo() throws Exception;
    public boolean call() throws Exception;
    public void undo();
}
abstract class AbstractCommand implements ICommand{
    AtomicInteger count = new AtomicInteger();
    @Override
    public boolean redo() throws Exception{
        //重试N次
        if(count.getAndIncrement() == 3){
            new Exception("redo error");
        }
        return this.call();
    }
}

class ConcreteCommand extends AbstractCommand implements ICommand{


    private Receiver rec;
    public ConcreteCommand(Receiver rec) {
        this.rec = rec;
    }

    @Override
    public boolean call() throws Exception {
        System.out.println(rec.getSql()+"执行");
        return true;
    }

    @Override
    public void undo() {
        System.out.println(this.rec.getSql()+"执行撤销");
    }
}

//invoker 接收到命令，并执行命令。
class Executeor{
    private List<ICommand> commands = new ArrayList<ICommand>();//组合模式,进行批处理。数据库底层的事务管理就是类似的结构！

    public void addCommand(ICommand command){
        commands.add(command);
    }

    public void commint(){
        for (ICommand command:commands) {
            try {
                //命令会重试3次,3次不成功则到抛出异常
                boolean ret = command.call();
                while (!ret){
                    ret = command.redo();
                    Thread.sleep(100);
                }
            } catch (Exception e) {
                //异常则重置
                command.undo();
            }

        }
    }
}