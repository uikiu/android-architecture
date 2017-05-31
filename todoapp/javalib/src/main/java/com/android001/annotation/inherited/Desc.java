package com.android001.annotation.inherited;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
* class design：
* @author android001
* created at 2017/5/31 上午11:08
*/

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited()
public @interface Desc {

    enum Color {
        White, Grayish, Yellow;
    }

    Color c() default Color.White;

}
