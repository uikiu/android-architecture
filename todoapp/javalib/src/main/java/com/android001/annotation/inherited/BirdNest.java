package com.android001.annotation.inherited;

/**
* class design：
* @author android001
* created at 2017/5/31 上午10:27
*/

//鸟巢，工厂方法模式
public enum BirdNest {
    SPARROW;

    //鸟类繁殖
    public Bird reproduce() {
        Desc bd = Sparrow.class.getAnnotation(Desc.class);//获取描述信息：有描述信息则使用描述信息指定的颜色，无描述信息则使用默认颜色
        return bd == null ? new Sparrow() : new Sparrow(bd.c());//Sparrow是有继承描述的，所以推断是白色white
    }
}
