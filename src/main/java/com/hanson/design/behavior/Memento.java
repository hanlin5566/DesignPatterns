package com.hanson.design.behavior;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by Hanson on 2020/2/5 16:56
 */
public class Memento {
    public static void main(String[] args) {
        Originator originator = new Originator("hanson1",1);
        //设置存储点
        MementoOri mementoOri = originator.saveMemnto();
        Caretaker caretaker = new Caretaker();
        //保存存储点
        caretaker.add(mementoOri);
        originator.setName("hanson2");
        //打印
        System.out.println(originator.toString());
        //恢复存储点1
        originator.recover(caretaker.get(0));
        System.out.println(originator);
    }
}

class Originator{
    private String name;
    private int no;

    public Originator(String name, int no) {
        this.name = name;
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    @Override
    public String toString() {
        return "Originator{" +
                "name='" + name + '\'' +
                ", no=" + no +
                '}';
    }

    public MementoOri saveMemnto(){
        return new MementoOri(this);
    }

    public void recover(MementoOri mementoOri){
        this.name = mementoOri.getName();
        this.no = mementoOri.getNo();
    }
}

class MementoOri {
    private String name;
    private int no;

    public MementoOri(Originator ori){
        this.name = ori.getName();
        this.no = ori.getNo();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }
}

class Caretaker{
    List<MementoOri> mementos = new ArrayList<>();

    public void add(MementoOri mementoOri) {
        mementos.add(mementoOri);
    }

    public MementoOri get(int i) {
        return this.mementos.get(i);
    }
}