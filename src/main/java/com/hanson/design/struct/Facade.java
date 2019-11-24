package com.hanson.design.struct;

/**
 * Created by Hanson on 2019/5/31 16:48
 * 外观模式
 * 外观模式（Facade Pattern）隐藏系统的复杂性，并向客户端提供了一个客户端可以访问系统的接口。
 * 这种类型的设计模式属于结构型模式，它向现有的系统添加一个接口，来隐藏系统的复杂性。
 * 比如电脑 开关机,里面有复杂的逻辑,对其进行隐藏.
 */
public class Facade {
    public static void main(String[] args) {
        Computer pc = new PC();
        pc.startup();
        pc.shutdown();
    }
}

interface Computer{
    void startup();
    void shutdown();
}

class PC implements Computer{
    Cpu cpu = new AMDCpu();
    Memory memory = new KinstonMemory();
    Disk disk = new SeagateDisk();
    @Override
    public void startup() {
        cpu.startup();
        memory.startup();
        disk.startup();
    }

    @Override
    public void shutdown() {
        cpu.shutdown();
        memory.shutdown();
        disk.shutdown();
    }
}

interface Cpu{
    void startup();
    void shutdown();
}
interface Memory{
    void startup();
    void shutdown();
}
interface Disk {
    void startup();
    void shutdown();
}

class AMDCpu implements Cpu{
    @Override
    public void startup() {
        System.out.println("cpu startup..");
    }

    @Override
    public void shutdown() {
        System.out.println("cpu shutdown..");
    }
}
class KinstonMemory implements Memory{
    @Override
    public void startup() {
        System.out.println("memory startup..");
    }

    @Override
    public void shutdown() {
        System.out.println("memory shutdown..");
    }
}
class SeagateDisk implements Disk{
    @Override
    public void startup() {
        System.out.println("disk startup..");
    }

    @Override
    public void shutdown() {
        System.out.println("disk shutdown..");
    }
}

