package com.example;

import com.android001.enumeration.EnumDefinition;

public class MyClass {


    public static void main(String...args)
    {
         int i= 200;
        System.out.print("main.i = "+i+"\n");
        change(i);
    }

    static void change(int i){
        i = 300;
        System.out.print("change.i = "+i);
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
