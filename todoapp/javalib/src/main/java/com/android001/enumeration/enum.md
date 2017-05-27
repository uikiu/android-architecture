##常量定义，首先考虑使用枚举
####枚举特性：
**在编译期间限定类型，不会发生越界情况，但是在switch时需要非空判断** </br>

枚举的排序值默认根据对实例的定义从0开始
####枚举方法：
列出枚举项
对比
排序
方法可自定义
####枚举确定：
枚举没有继承

####注意
在使用枚举的valueOf(String name),必须先判断name是否存在。见EnumDefinition : boolean contains (String name);