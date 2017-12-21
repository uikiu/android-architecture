# 定义源文件在开发树中的位置。在这里，固件系统提供的宏函数my-dir将返回当前目录(包含Android.mk文件本身的目录)的路径。
 LOCAL_PATH := $(call my-dir)

# 声明CLEAR_VARS变量
 include $(CLEAR_VARS)

# 定义module名称
 LOCAL_MODULE := JniInteraction

# 枚举源文件，以空格分割多个源文件
 LOCAL_SRC_FILES:=HelloJNI.c

# 生成so文件：将所有内容链接到一起，构建目标共享库。此脚本之前必须为LOCAL_MODULE和LOCAL_SRC_FILES赋值。
 include $(BUILD_SHARED_LIBRARY)








