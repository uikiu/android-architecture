### 几种主要组件
在构建应用时，您应该已经了解一下组件。

* ndk-build:ndk-build脚本用于在构建NDK中心启动构建脚本。这些脚本包含：
  * 自动探测您的开发系统和应用项目文件以确定要构建的内容。
  * 生成二进制文件。
  * 将二进制文件复制到应用的项目路径。


### ndk-build脚本针对Android.mk和Application.mk
下面两个项目仅在使用**ndk-build脚本构建**时以及使用**ndk-gab脚本调试**时才需要。
* **Android.mk配置文件**: 必须在jni文件夹内创建Android.mk配置文件。ndk-build脚本将查看此文件。
文件中定义了模块机器名称、要编译的源文件、版本标识、要链接的库。
* **Application.mk描述文件**: 此文件枚举并描述您的应用需要的模块，这些信息包括
  * 用于针对特定平台进行编译的ABI。
  * 工具链。
  * 要包含的标准库(静态和动态STLport或默认系统)。

---

### 流程

* 设计应用，确定要在Java中实现的部分，以及要在原生代码中实现的部分。
* 创建一个Android项目
* 如果要编写纯原生应用，请在AndroidManifest.xml中声明NativeActivity类。（通常不会出现纯原生应用）
* 在JNI目录中创建一个描述原生库的Android.mk文件，包括：名称、标志、链接库、要编译的源文件。
* 与上一条二选一：创建一个Application.mk文件。对于其中任何您未指明项目，将分别使用以下默认值：
  * ABI: armeabi
  * 工具链：GCC4.8
  * 模式：发行
  * STL：系统
* 将原生来源至于项目的jni目录下。
* 使用ndk-build编译原生(.so、.a)库。
* 构建java组件，生成可执行.dex文件。
* 将所有内封封装到一个APK文件中，包含.so、.dex以及运行所需的其他文件。

### Android.mk
将C和C++源文件粘合至Android NDK的Android.mk构建文件的语法。<br>
Android.mk文件位与项目jni/目录的子目录中，**用于向构建系统描述源文件和共享库**。
它实际上是构建系统解析一次或多次的微小GNU makefile片段。<br>
* **Android.mk定义范围：**
  * 定义Application.mk
  * 构建系统和环境变量所未定义的项目范围设置。
  * 替换特定模块的项目范围设置。

**Android.mk语法作用是将源文件分为不同的模块**：静态库、贡献库、独立可执行文件。<br>
**除了封装库之外，构建系统还可以为您处理各种其他相信信息**。
例如:您无需在Android.mk文件中列出表头文件或生成的文件之间的显示依赖关系。
**NDK构建系统**会自动为您计算这些。因此您应该能够享受到未来NDK版本中新工具链/平台支持的优点，而无需接触Android.mk文件。




### Android.mk之NDK定义的变量
* **定义LOCAL_PATH变量:源文件在开发树中的位置**。
```
LOCAL_PATH:=$(call my-dir)
```
在这里，构建系统提供的宏函数my-dir将返回当前目录(包含Android.mk文本身的目录)的路径。

* **声明CLEAR_VARS变量，其值由构建系统提供**
```
include $(CLEAR_VARS)
```
CLEAR_VARS变量指向特殊GNU Makefile,可为您清除许多LOCAL_XXX变量，
例如：LOCAL_MODULE、LOCAL_SRC_FILES、LOCAL_STATIC_LIBRARIES。请注意，她不会清除LOCAL_PATH.


* **定义模块名称**
```
LOCAL_MODULE:=hello-jni
```
每个模块名称必须唯一，且不含任何空格。构建系统在生成最终共享库文件时，会将正确的前缀和后缀
自动添加到您分配给LOCAL_MODULE的名称。例如实例会导致生成一个名为libhello-jni.so的库。
也就是自动添加了前缀lib和后缀.so。
如果模块名称的开头已是lib，则构建系统不会额外添加前缀lib。而是按照原样采用模块名称，并添加.so扩展名。
因此原来名为libfoo.c源文件仍然会生成名为libfoo.so的共享对象文件。
此行为是为了**支持Android平台源文件从Android.mk文件生成的库；所以这些库的名称都是以lib开头**。

* **枚举源文件，以空格分割多个文件**
```
LOCAL_SRC_FILES:=hello-jni.c
```

* 帮助系统将所有内容链接到一起：
```
include $(BUILD_SHARED_LIBRARY)
```
BUILD_SHARED_LIBRARY变量指向GNU Makefile脚本，用于收集您最近include后在LOCAL_XXX变量中定义的所有信息。
此脚本确定要构建的内容及其操作方法。










