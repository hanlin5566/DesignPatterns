package com.hanson.design.struct;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hanson on 2019/5/31 10:45
 * 组合模式 简单来说就是类中持有本类或者本接口的list,
 * 构造一个树形结构.比如员工架构,部门架构等等.用此种方式避免继承或树形继承关系出现,避免耦合性.
 */
public class Composite {
    public static void main(String[] args) {
        Employee CEO = new Employee("王二小","CEO",01);
        Employee CRO = new Employee("王三小","CRO",02);
        Employee CFO = new Employee("王四小","CFO",03);
        Employee CTO = new Employee("王五小","CTO",04);
        Employee PM = new Employee("王六小","PM",05);
        Employee programmer = new Employee("王七小","programmer",06);
        CEO.addSubordinates(CRO).addSubordinates(CFO).addSubordinates(CTO);
        CTO.addSubordinates(PM).addSubordinates(programmer);

        CEO.info();
        CTO.info();
    }
}

class Employee{
    private String name;
    private String title;
    private int no;
    private List<Employee> subordinates = new ArrayList<Employee>();

    public Employee(String name, String title, int no) {
        this.name = name;
        this.title = title;
        this.no = no;
    }

    public Employee addSubordinates(Employee subordinate){
        this.subordinates.add(subordinate);
        return this;
    }

    public void info() {
        String info = String.format("name:%s title:%s no:%s",name,title,no);
        System.out.println(info);
        for (Employee subordinate:subordinates) {
            System.out.print("   ---");
            subordinate.info();
        }
    }
}