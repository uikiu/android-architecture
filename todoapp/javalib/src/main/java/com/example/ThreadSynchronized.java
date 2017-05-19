package com.example;

import java.util.logging.Logger;

/**
 * Created by xixionghui on 2017/5/19.
 */

public class ThreadSynchronized {

    private int couter = 0;

    public synchronized void print(){

//        for (;couter>=0;couter--) {
//            System.out.print(couter+"\n");
//        }

        for (;couter<=100;couter++) {
            System.out.print(couter+"\n");
        }

    }
}
