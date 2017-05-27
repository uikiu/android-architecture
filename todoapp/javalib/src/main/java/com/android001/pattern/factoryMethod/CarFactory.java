package com.android001.pattern.factoryMethod;

/**
 * Created by android001 on 2017/5/26.
 */

public class CarFactory {

    //抽象产品
    interface Car {};

    //具体产品Ford福特
    class FordCar implements Car {}

    //具体产品Buick别克
    class BuickCar implements Car{}

    public static Car createCar(Class<? extends Car> c)
    {
        try {
            return c.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
