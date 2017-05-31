package com.android001.annotation;

/**
* class design：
* @author android001
* created at 2017/5/31 上午10:50
*/
public class AnnotationExample {


    public static void main (String... args) {
        Bird bird = BirdNest.SPARROW.reproduce();
        Desc.Color color = bird.getColor();
        System.out.print("Bird's color is : "+color);//White
    }
}
