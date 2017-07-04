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

* **生成so文件：将所有内容链接到一起，构建目标共享库。此脚本之前必须为LOCAL_MODULE和LOCAL_SRC_FILES赋值**。
```
 include $(BUILD_SHARED_LIBRARY)
```

* **生成.a文件：用于构建静态库的 BUILD_SHARED_LIBRARY 的变体。**
* **构建系统不会将静态库复制到您的项目/软件包，但可能使用它们构建共享库(请参阅下面的 LOCAL_STATIC_LIBRARIES 和 LOCAL_WHOLE_STATIC_LIBRARIES)**
```
include $(BUILD_STATIC_LIBRARY)
```

* 在这里介绍一下llinux下的相关文件
  * .o 就相当于windows里的obj文件 ，一个.c或.cpp文件对应一个.o文件
  * .a 是好多个.o合在一起,用于静态连接 ，即STATIC mode，多个.a可以链接生成一个exe的可执行文件
  * .so 是shared object,用于动态连接的,和windows的dll差不多，使用时才载入。


* **预编译.so共享库文件:PREBUILT_SHARED_LIBRARY**：为第三方库编写.mk文件。NDK R5支持预编译共享库，也就是支持从其他地方获得源码编译出来的共享库，而不是android自带的
把预编译共享库声明为一个独立模块，例如：libfoo.so。libfoo.so与Android.mk位与同一目录。则Android.mk应该这样写：

针对.so库文件封装为共享模块。实质为：**为第三方.so库编写.mk文件**
```
LOCAL_PATH :=$(call my-dir)
include $(CLEAR_VARS)
LOCAL_MODULE := foo-prebuild # 模块名
LOCAL_SRC_FILES := libfoo.so # 模块的文件路径(相对于LOCAL_PATH)
include $(PREBUILT_SHARED_LIBRARY) #注意这里不是BUILD_SHARED_LIBRARY
```
具体实例:ffmpeg
```
LOCAL_PATH :=$(call my-dir)
include $(CLEAR_VARS)
LOCAL_MODULE := ffmpeg
LOCAL_SRC_FILES := libffmpeg.so
```

* **预编译.a共享库:PREBUILT_STATIC_LIBRARY**: 性质与PREBUILT_SHARED_LIBRARY相同，只不过是引用的第三方库的形式不是.so文件而是.a文件。

* **指定CPU架构名称TARGET_ARCH**
TARGET_ARCH是指定CPU架构名称的，例如:ARM、MIPS、X86。此定义了品牌，但是并没有指定应用程序的二进制接口
```
TARGET_ARCH := arm
```


* **指定分析Android.mk文件的Android平台API版本**
```
TARGET_PLATFORM := android-22
```

* **指定二进制界面TARGET_ARCH_ABI**
不同Android手机使用不同的cpu，因此支持不同的指令集。CPU与指令集的每种组合都有其自己的应用二进制界面（ABI）
ABI可以非常进准的定义应用的机器代码在运行时如何与系统交互，您必须为应用要使用的每个CPU架构指定ABI.
```
TARGET_ARCH_ABI := arm64-v8a
```

* **TARGET_ABI一次性指定硬件版本和软件版本**
指定Android平台的API版本和CPU二进制界面
```
TARGET_ABI := android-22-arm64-v8a
```

---

### 模块描述变量
上面提到了ndk-build构建流程，以及android.mk中使用的一些简单的描述变量。下面专门来说***模块描述变量**
本节中的变量向构建系统描述您的模块。每个模块应遵守以下基本流程：
1.  使用CLEAR_VARS变量初始化或取消定义模块相关的变量。
2.  为用于描述模块的变量赋值。
3.  使用BUILD_XXX变量设置NDK构建系统，以便为模块使用适当的构建脚本。



* **LOCAL_PATH:指定当前文件的路径，必须在Android.mk文件的开头定义它**。
```
LOCAL_PATH := $(call my-dir)
```
CLEAR_VARS指向的脚本不会清除此变量。因此，即使您的Android.mk文件描述了多个模块，您也只需定义它一次。

* **LOCAL_MODULE:存储(指定)模块名称**
  * 所以模块名称必须唯一
  * 不得包含任何空格
  * 必须在包含任何脚本之前定义它(CLEAR_VARS脚本除外)---通常排在CLEAR_VARS之后
  * 无需添加前缀和后缀(.so/.a)----构建系统会自动进行这些修改
```
LOCAL_MODULE := "foo"
```


* LOCAL_MODULE_FILENAME---可选变量：如果希望生成的模块使用lib以外的名称和LOCAL_MODULE以外的值，可以使用LOCAL_MODULE_FILENAME变量为生成的模块指定自己选择的名称。
此可选变量可让您覆盖构建系统默认用于其生成的文件的名称。例如，如果LOCAL_MODULE_FILENAME变量为生成的模块指定自己选择的名称。
```
LOCAL_MODULE := foo
LOCAL_MODULE_FILENAME := libnewfoo
```

对于共享库模块，此实例将生成名为libnewfoo.so的文件。

* LOCAL_SRC_FILES源文件列表：只列出构建系统实际传递到编译器的文件。因为构建系统会自动计算所有关联的依赖关系。
可以使用相对路径，和绝对路径两种方式。但是建议使用相对路径，相对路径的对象是LOCAL_PATH



* 添加由C++编译器编译的文件类型：<br>
通常情况下NDK会自动编译LOCAL_SRC_FILES目录指定的cpp或c文件。如果是cpp会自动用c++编译器，如果是c那么自动用c编译器
mk文件有一个LOCAL_CPP_EXTENSION属性，设置的是用c++编译的文件后缀。我们可以指定.cxx文件也由C++编译器编译。

```
LOCAL_CPP_EXTENSION := .cxx
```

从NDK r7开始，您可以使用此变量指定多个扩展名。

```
LOCAL_CPP_EXTENSION := .cxx .cpp .cc
```

* LOCAL_CPP_FEATURES指明您的代码依赖于特定的C++功能。----可选变量
它在构建过程中启用正确编译器和链接器标志。对于预构建的库，此变量还可以声明二进制文件依赖哪些功能，从而帮助确保最终关联正确工作。
**建议启用此变量，而不要直接在LOCAL_CPPFLAGS定义中启用-frtti和fexceptions**
使用此变量可让构建系统对每个模块使用适当的标识。使用LOCAL_CPPFLAGS会导致编译器对所有模块使用所有指定的标志，而不管实际需求如何。
例如，要指示您的代码使用RTTI(运行时类型信息),请编写：
```
LCAL_CPP_FEATURES := rtti
```

要指示您的代码使用C++异常，请编写：
```
LOCAL_CPP_FEATURES :=  exceptions
```

LOCAL_CPP_FEATURES 可有有多个值，并且不限定顺序，中间以空格分开即可
```
LOCAL_CPP_FEATURES := rtti features
```


* LOCAL_C_INCLUDES 设置**头文件的include目录**。是相对于NDK root目录的路径列表。例如：
```
LOCAL_C_INCLUDES := sources/foo
```

甚至：
```
LOCAL_C_INCLUDES := $(LOCAL_PATH)//foo
```

指定全路径:一个目录写一行。
```
LOCAL_C_INCLUDES := $(LOCAL_PATH)/../../Classes \
                     $(LOCAL_PATH)/../../Classes/game \
                     $(LOCAL_PATH)/../../Classes/logic \
                     $(LOCAL_PATH)/../../Classes/view
```

把源码目录下的所有子目录都引入：-----把classes目录和子目录全部包含进来
```
LOCAL_C_INCLUDES := $(LOCAL_PATH)/../../Classes
LOCAL_C_INCLUDES += $(shell ls -FR $(LOCAL_C_INCLUDES) | grep $(LOCAL_PATH)/$ )
LOCAL_C_INCLUDES := $(LOCAL_C_INCLUDES:$(LOCAL_PATH)/%:=$(LOCAL_PATH)/%)
```


把源码目录下的所有子目录都引入：------使用sed命令
```
LOCAL_C_INCLUDES := $(LOCAL_PATH)/../../Classes
LOCAL_C_INCLUDES += $(shell ls -FR $(LOCAL_C_INCLUDES) | grep $(LOCAL_PATH)/$ | sed "s/:/ /g" )
```

**LOCAL_C_INCLUDES要在通过 LOCAL_CFLAGS 或 LOCAL_CPPFLAGS 设置任何对应的 include 标志之前定义此变量**



* LOCAL_CFLAGS
构建系统设置在构建C/C++源文件时要传递编译器标志。此功能对于指定额外的宏定义或编译选项可能很有用。相当于在所有源文件增加一个宏定义# define XXX
可通过编写以下代码指定其他include路径
尽量不要更改Android.mk文件中的优化/调试级别。构建系统可使用Application.mk文件中相关信息自动为您处理此设置。
```
LOCAL_CFLAGS += -I<path>
```

* 更多模块描述变量...

### NDK提供的函数宏
本节说明**NDK提供的GUN Make函数宏**。**使用$(call <function>)对它们估值；返回文本信息**。
注意格式：
```
$(call <function>)
```
通常在宏函数前面有定义：
```
XXX := $(call <function>)
```

* my-dir 宏
此宏返回最后包含的makefile的路径，通常是当前Android.mk的目录。my-dir可用于Android.mk文件的开头定义LOCALPATH
```
LOCAL_PATH := $(call my-dir)
```

* all-subdir-makefiles返回位于当前my-dir路径所有子目录中的Android.mk文件列表。
可以使用此函数为**构建系统提供深入嵌套的源目录层次结构**。默认情况系，NDK只在包含Android.mk文件的目录中查找文件。

* this-makefile 返回**当前makefile**(构建系统从中调用函数)的路径。

* parent-makefile 返回包含树中**父makefile的路径---makefile上一级目录**(包含当前makefile的)

* grand-parent-makefile 返回包含树中**makefile上两级目录**

* import-module 导入外部模块的.mk文件，和include基本一样。
定义上区分：include是导入由我们自己写的.mk文件。二 import-module导入的是外部库、外部模块提供的.mk文件。<br>
用法上区分：include的路径是.mk文件的绝对路径。而import是设置的路径指定到模块的.mk所在目录，是相对于NDK_MODULE_PATH中的路径列表的相对路径。
```
$(call import-module,相对路径)
```
[import-module详细信息：import-module的注意事项与NDK_MODULE_PATH的配置](http://blog.sina.com.cn/s/blog_4057ab62010197z8.html)









### 总和示例
```
#当前文件路径---固定在第一步
LOCAL_PATH := $(call my-dir)

# 声明脚本变量----固定在第二步
include $(CLEAR_VARS)

# 指定module名称----固定在第三步
LOCAL_MODULE := "foo"



```






### 名词解释
共享库：指的是.so文件
预编译共享库：从其他地方获得源码编译出来的贡献库
静态库：指定LOCAL_STATIC_LIBRARIES
动态库：指定LOCAL_SHARED_LIBRARIES
ABI:应用二进制界面









