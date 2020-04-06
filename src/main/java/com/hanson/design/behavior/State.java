package com.hanson.design.behavior;

/**
 * Created by Hanson on 2020/2/3 0:39
 * 不同状态影响不同行为,避免大量if else判断不同状态的行为.把行为封装到状态里,更好的新增状态.
 */
public class State {
    public static void main(String[] args) {
        TVController tvController=new TVController();
        tvController.powerOn();
        tvController.nextChannel();
        tvController.turnUp();
        tvController.powerOff();
        //调高音量，此时不会生效
        tvController.turnUp();
        /**
         *  如果不这么做,会有大量的
         *  turnUp(){ if(State.Off)}
         */


    }
}

//遥控器 就是 Context 持有状态
class TVController {
    TVState tvState;

    public void setTvState(TVState tvState){
        this.tvState=tvState;
    }

    //开机 改变状态
    public void powerOn(){
        System.out.println("开机");
        this.tvState = new OnState();
    }

    //关机 改变状态
    public void powerOff(){
        System.out.println("关机");
        this.tvState = new OffState();
    }

    public void nextChannel(){
        tvState.nextChannerl();
    }

    public void prevChannel(){
        tvState.prevChannerl();
    }

    public void turnUp(){
        tvState.turnUp();
    }

    public void turnDown(){
        tvState.turnDown();
    }


}

interface TVState{
    //状态的行为
    public void nextChannerl();
    public void prevChannerl();
    public void turnUp();
    public void turnDown();
}

//开机状态
class OnState implements TVState{

    @Override
    public void nextChannerl() {
        System.out.println("下一个频道");
    }

    @Override
    public void prevChannerl() {
        System.out.println("上一个频道");
    }

    @Override
    public void turnUp() {
        System.out.println("音量加");
    }

    @Override
    public void turnDown() {
        System.out.println("音量减");
    }
}
//关机状态
class OffState implements TVState{

    @Override
    public void nextChannerl() {
        System.out.println("关机中,无响应..");
    }

    @Override
    public void prevChannerl() {
        System.out.println("关机中,无响应..");
    }

    @Override
    public void turnUp() {
        System.out.println("关机中,无响应..");
    }

    @Override
    public void turnDown() {
        //已经关机了,什么也不做.
        System.out.println("关机中,无响应..");
    }
}