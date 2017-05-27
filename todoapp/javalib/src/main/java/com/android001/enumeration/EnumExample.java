package com.android001.enumeration;

import java.util.Arrays;

/**
 * Created by android001 on 2017/5/25.
 */

public class EnumExample {


    public static void main(String... args){
        System.out.print("\n列出所有枚举实例："+ Arrays.toString(EnumDefinition.values()));//[CONSTANT1, CONSTANT2, CONSTANT3, CONSTANT4, CONSTANT5]
        System.out.print("\n对比两个枚举实例："+ EnumDefinition.CONSTANT1.compareTo(EnumDefinition.CONSTANT2));//-1
        System.out.print("\n返回枚举实例排序值："+ EnumDefinition.CONSTANT2.ordinal());//1

        if(EnumDefinition.contains("CONSTANT0"))//使用枚举的valueOf之前必须先判断是否存在此定义
            System.out.print("\n找到指定名称的枚举对象："+ EnumDefinition.valueOf("CONSTANT0").name());//1



    }


}
