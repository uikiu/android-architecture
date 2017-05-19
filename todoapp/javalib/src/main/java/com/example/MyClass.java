package com.example;

import javax.management.StringValueExp;

public class MyClass {


    public static void main(String...args){
        MyClass myClass = new MyClass();
        myClass.synchronizedTest();
    }

    private void synchronizedTest(){
        final ThreadSynchronized threadSynchronized = new ThreadSynchronized();
        for (int i = 0; i < 100; i++) {
            final int a = i;
            new Thread(){
                @Override
                public void run() {//非synchronized
                    threadSynchronized.print();
                }
            }.start();

        }
    }
}
