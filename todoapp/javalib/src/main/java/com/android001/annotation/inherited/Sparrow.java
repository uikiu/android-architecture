package com.android001.annotation.inherited;

/**
* class design：
* @author android001
* created at 2017/5/31 上午10:37
*/

//麻雀
class Sparrow extends Bird {
    private Desc.Color color;

    @Override
    public Desc.Color getColor() {
        return color;
    }

    public Sparrow () {//默认构造函数：是浅灰色
        color = Desc.Color.Grayish;
    }

    public Sparrow (Desc.Color sparrowColor) {//指定颜色的构造函数
        color = sparrowColor;
    }
}
