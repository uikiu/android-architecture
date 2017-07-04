本文档介绍Application.mk构建文件，此文件用于描述应用需要的原生模块。模块可以是静态库、共享库(动态库)、可执行文件。<br>
**application.mk 描述应用程序需要的原生模块**

* linux下对库的一些介绍
  * 什么是库？
  本质上来说，库是一种可执行代码的二进制形式，可以被操作系统载入内存执行。
  * 库的种类
  linux下的库有两种：静态库和共享库(动态库)<br>
  静态库和共享库不同点在于代码被载入的时刻不同。<br>
  静态库的代码在**编译过程中已经被载入可执行程序**，因此体积较大。
  共享库的代码在**可执行程序运行时才载入内容的**，在编译过程中仅简单的引用，因此代码体积较小。
  * 库存在的意义
  库是别人写好的现有的、成熟的、可以复用的代码、你可以使用但要记得遵守许可协议。
  现实中每个程序都要依赖很多基础的底层库，不可能每个人的代码都从零开始，因此库的存在意义非同寻常。
  共享库的好处是，**不同的应用程序如果调用相同的库，那么在内存里只需要有一份该共享库的实例**。
  * linux下的库文件是如何产生的
    * 静态库的后缀是.a ,它的产生分两步：1> 由源文件编译生成一堆.o，每个.o里包含这个编译单元的符号表；2>ar命令将很多.o转换为.a,成为静态库。
    * 动态库的周追是.so,它是由gcc加特定参数编译产生。例如：
    ```
    $ gcc -fPIC -c *.c $ gcc -shared -Wl,-soname, libfoo.so.1 -o libfoo.so.1.0 *.
    ```

[linux下的共享库(动态库)和静态库](https://my.oschina.net/alphajay/blog/3858)


---

### 概览
application.mk 文件实际上是**定义要编译的多个变量的微小GUN Makefile片段**。
两种存放位置：
1>:它通常位于$PROJECT/jni/下，其中$PROJECT指向应用的项目目录。2>:另一种方式是将其放在顶级$NDK/apps/目录的子目录下。例如:
```
$NDK/apps/<myapp>/Application.mk
```
这里的<myapp>是用于向NDK构建系统描述应用的短名称。它不会实际进入生成的共享库或最终软件包。


### 变量

* **APP_PROJECT_PATH 此变量用于存储应用项目根目录的绝对路径**。<br>
构建系统使用此信息将生成的JNI共享库的简缩版放入APK生成工具已知的特定位置。
如果正如上面提到的第二种存放位置，将Application.mk文件放在$NDK/apps/<myapp>下，则必须定义此变量。
如果将其存放在$PROJECT/jni/下，则此变量可选。

* APP_OPTIM
修改native代码的两种模式：release 或 debug。 在构建应用的模块时可使用它来更改优化级别。
  * release发行模式是默认模式:可生成高度优化的二进制文件。
  * debug调试模式:会生成未优化的二进制文件，更容易调试。

在应用清单的<application>标记声明android:debuggable 将导致此变量默认使用debug而非release。将APP_OPTIM设置为release可替换此默认值。
```
APP_OPTIM := release
APP_OPTIM := debug
```

* APP_CFLAGS 编译选项，可以在Application.mk文件中修改此编译选项参数，此参数可以覆盖Android.mk文件中的相同定义，
从而不必去Android.mk中修改。需要主要的是，这个选项里的路径必须是以顶层NDK目录为相对了路径，例如存在如下两个文件：
```
sources/foo/Android.mk
sources/bar/Android.mk
```

如果在foo/Android.mk文件中想要添加bar目录，那么得这样便用：
```
APP_CFLAGS += -Isources/bar
```
或者
```
APP_CFLAGS += -I$(LOCAL_PATH)/../bar
```
-I../bar在其等于-I$NDK_ROOT/.../bar后不会运行。


* APP_CPPFLAGS **当只编译C++源文件的时候，可以通过这个C++编译器来设置。建议使用APP_CFLAGS**<br>

在 android-ndk-1.5_r1 中，此变量适用于C和 C++ 源文件.在 NDK 的所有后续版本中，APP_CPPFLAGS 现在匹配整个 Android 构建系统。
对于适用于 C 和 C++ 源文件的标志，请使用 APP_CFLAGS.

* APP_LDFLAGS
构建系统在连接应用时传递的一组链接器标志。此变量仅在构建系统构建共享库和可执行文件时才相关。
当构建系统构建静态库时，会忽略这些标志。


* **APP_BUILD_SCRIPT：修改Android.mk的默认路径**<br>
默认情况下，NDK构建系统在jni/下查找名称为Android.mk的文件。
如果要改写此行为，可以定义APP_BUILD_SCRIPT指向替代构建脚本。构建系统始终将非绝对路径解释为NDK顶级目录的相对路径。


* **APP_ABI:指向不同的ABI**<br>
默认情况下，NDK 构建系统为 armeabi ABI 生成机器代码。 此机器代码对应于基于 ARMv5TE、采用软件浮点运算的 CPU。
您可以使用 APP_ABI 选择不同的 ABI<br>

|指令集|值|
|:----:|:----:|
|基于 ARMv7 的设备上的硬件 FPU 指令 | APP_ABI := armeabi-v7a|
|ARMv8 AArch64 | APP_ABI := arm64-v8a|
|IA-32 | APP_ABI := x86|
|Intel64 | APP_ABI := x86_64|
|MIPS32 | APP_ABI := mips|
|MIPS64 | (r6)	APP_ABI := mips64|
|所有支持的指令集 | APP_ABI := all|

您也可以指定多个值，将它们放在同一行上，中间用空格分隔。例如：
```
APP_ABI := armeabi armeabi-v7a x86 mips
```

* APP_PLATFORM 目标android平台的名称，与Android.mk中的TARGET_PLATFORM相同。例如：
```
TARGET_PLATFORM := android-22
```

* **APP_STL：指定application里要链接的标准c++库。如果不指定，默认情况下，使用最小化的c++运行时系统库。**

```
APP_STL := stlport_shared
APP_STL := stlport_static
```

* APP_SHORT_COMMANDS  与Android.mk中APP_SHORT_COMMANDS作用相同<br>

当您的模块有很多源文件和/或相依的静态或共享库时，将此变量设置为 true。 这样会强制构建系统对包含中间对象文件或链接库的存档使用 @ 语法。
此功能在 Windows 上可能很有用，其中命令行最多只接受 8191 个字符，这对于复杂的项目可能太少。 它还会影响个别源文件的编译，而且将几乎所有编译器标志放在列表文件内。
请注意，true 以外的任何值都将恢复到默认行为。 您也可在 Application.mk 文件中定义 APP_SHORT_COMMANDS，以强制对项目中的所有模块实施此行为。
不建议默认启用此功能，因为它会减慢构建的速度


* NDK_TOOLCHAIN_VERSION ndk工具链的版本<br>
将此变量定义为4.9或4.8以选择GC 编译器的版本。64位ABI默认使用版本4.9 ,32位ABI默认使用版本 4.8。
要选择 Clang 的版本，请将此变量定义为 clang3.4、clang3.5 或 clang。 指定 clang 会选择 Clang 的最新版本
```
NDK_TOOLCHAIN_VERSION := 4.9
```


* APP_PIE 设置是否支持PIE，这个变量受到APP_PLATFORM变量的影响，是从APP_PLATFORM := android-16开始支持的<br>
从Android4.1（API 级别 16）开始，Android 的动态链接器支持位置独立的可执行文件 (PIE)。
从 Android 5.0（API 级别 21）开始，可执行文件需要 PIE。要使用 PIE 构建可执行文件，请设置 -fPIE 标志。
此标志增大了通过随机化代码位置来利用内存损坏缺陷的难度。 默认情况下，如果项目针对 android-16 或更高版本，ndk-build 会自动将此值设置为 true。您可以手动将其设置为 true 或 false



* LOCAL_THIN_ARCHIVE 与android.mk中的LOCAL_THIN_ARCHIVE相同<br>
构建静态库时将此变量设置为 true。这样会生成一个瘦存档 ，即一个库文件，其中不含对象文件，而只包含它通常要包含的实际对象的文件路径。
这对于减小构建输出的大小非常有用。缺点是：这样的库无法移至不同的位置（其中的所有路径都是相对的）。
有效值为 true、false 或空白。可通过 APP_THIN_ARCHIVE 变量在 Application.mk 文件中设置默认值。
注：对于非静态库模块或预构建的静态库模块会忽略此变量。
















































































