### JNI (Java Native Interface)
JNI 是Java调用Native机制，是Java需要自己的特性。所以说JNI和google的android没有关系。

### NDK (Native Development Kit)
**NDK 是一组允许你将C或者C++("原生代码")嵌入到Android应用中的工具**。
能够在Android应用中使用原生代码对于想要执行以下一项或者多想操作的开发者特别有用。

* 在平台之间移植其应用。
* 重复使用现有库，或者提供其自己的库空重复使用。
* 在某些情况下提供性能，特别是像游戏这种计算密集型应用。


NDK其实多了一个把so和apk打包的工具。而JNI并没有打包，只是把.so文件放到文件系统的特定位置。


---


* 编写含有Native方法的java类。例如，本module的Hello类。
* 生成头文件。在当前模块目录下追加一个shell文件，该文件用于生成头文件。
* 编写JNI代码。根据头文件，在jni目录下创建对应的.C/.cpp文件，完成代码。


---
[官方文档](https://developer.android.com/ndk/guides/concepts.html)<br>
[官方sample](https://github.com/googlesamples/android-ndk)<br>
[如何开发jni应用](http://git.bookislife.com/post/2015/how-to-develop-jni-app/)<br>
[JNI与NDK编程](https://tom510230.gitbooks.io/android_ka_fa_yi_shu_tan_suo/content/chapter14.html)<br>
[ReLinker项目](https://github.com/KeepSafe/ReLinker)

[Mastering Android NDK Build System-Part 1: Techniques with ndk-build](http://web.guohuiwang.com/technical-notes/androidndk1)<br>
[Mastering Android NDK Build System - Part 2: Standalone toolchain](http://web.guohuiwang.com/technical-notes/androidndk2)<br>
















