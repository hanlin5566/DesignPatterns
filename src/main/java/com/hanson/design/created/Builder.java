package com.hanson.design.created;

/**
 * Created by Hanson on 2019/5/28 14:07
 * 建造者模式
 * 将一个复杂的对象分解为多个简单的对象，然后一步一步构建而成。
 * 它将变与不变相分离，即产品的组成部分是不变的，但每一部分是可以灵活选择的。
 * 以组装电脑为例,包含机箱,内存,主板,cpu,电源等组成,但是品牌与配置是可选的.
 */
public class Builder {
    public static void main(String[] args) {
        ComputerBuilder computer1 = new ComputerBuilder().cpu(new CpuFactory().getCpu("intel")).memory(new MemoryFactory().getMemory("kingSton"));
        ComputerBuilder computer2 = new ComputerBuilder().cpu(new CpuFactory().getCpu("amd")).memory(new MemoryFactory().getMemory("samsung"));
        ComputerBuilder computer3 = new ComputerBuilder().cpu(new CpuFactory().getCpu("intel")).memory(new MemoryFactory().getMemory("samsung"));
        computer1.showInfo();
        System.out.println("----------");
        computer2.showInfo();
        System.out.println("----------");
        computer3.showInfo();
    }
}

/**
 * 计算机构建者
 */
class ComputerBuilder{
    private Cpu cpu;
    private Memory memory;
    public ComputerBuilder(){}

    public ComputerBuilder cpu(Cpu cpu){
        this.cpu = cpu;
        return this;
    }

    public ComputerBuilder memory(Memory memory){
        this.memory = memory;
        return this;
    }

    public void showInfo(){
        System.out.println("cpu:"+cpu.getTrademark()+" info:"+cpu.getInfo());
        System.out.println("memory:"+memory.getTrademark()+" info:"+memory.getInfo());
    }
}

/**
 * cpu
 */
interface Cpu{
    public String getTrademark();
    public String getInfo();
}
class IntelCpu implements Cpu{

    @Override
    public String getTrademark() {
        return "intel";
    }

    @Override
    public String getInfo() {
        return "Intel(R) Core(TM) i7-6600U";
    }
}

class AMDCpu implements Cpu{

    @Override
    public String getTrademark() {
        return "AMD";
    }

    @Override
    public String getInfo() {
        return "AMD Ryzen 5 2600X (r5)";
    }
}

class CpuFactory{
    public Cpu getCpu(String provider){
        switch (provider){
            case "intel":
                return new IntelCpu();
            case "amd":
                return new AMDCpu();
            default:
                throw new IllegalStateException("Unexpected value: " + provider);
        }
    }
}

/**
 * memory
 */
interface Memory{
    public String getTrademark();
    public String getInfo();
}
class KingStonMemory implements Memory{

    @Override
    public String getTrademark() {
        return "KingSton";
    }

    @Override
    public String getInfo() {
        return "KingSton 8g ddr4 2666";
    }
}

class SamsungMemory implements Memory{

    @Override
    public String getTrademark() {
        return "Samsung";
    }

    @Override
    public String getInfo() {
        return "Samsung 8g ddr4 2666";
    }
}

class MemoryFactory{
    public Memory getMemory(String provider){
        switch (provider){
            case "kingSton":
                return new KingStonMemory();
            case "samsung":
                return new SamsungMemory();
            default:
                throw new IllegalStateException("Unexpected value: " + provider);
        }
    }
}

