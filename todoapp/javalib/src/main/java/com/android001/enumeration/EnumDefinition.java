package com.android001.enumeration;

/**
 * Created by android001 on 2017/5/25.
 */

public enum EnumDefinition {

    CONSTANT1,CONSTANT2,CONSTANT3,CONSTANT4,CONSTANT5;

    //根据排序值列出所有实例: EnumDefinition.values():返回一个实例数组，直接输出会不认识，可以使用Arrays.toString(EnumDefinition.values())
    //对比两个枚举实例：EnumDefinition.CONSTANT1.compareTo(EnumDefinition.CONSTANT2)：返回一个负整数，零或正整数，因为该对象小于，等于或大于指定对象。
    //返回排序值---枚举声明中的位置：EnumDefinition.CONSTANT1.ordinal()


    //使用枚举的valueOf方法时必须先调用此方法进行判断
    public static boolean contains (String name)
    {
        //所有的枚举值
        EnumDefinition[] constants = values();
        //遍历查找
        for (EnumDefinition enumDefinition : constants)
        {
            if (enumDefinition.name().equals(name))
                return true;
        }
        return false;
    }


}
