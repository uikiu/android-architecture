#!/usr/bin/env bash

# 定义项目路径
export ProjectPath=$(cd ".";pwd)
# 打印验证：项目路径
echo "ProjectPath = "$ProjectPath

# 定义目标类、源文件、目标路径
export TargetClassName="com.android001.ndk.Hello"
export SourceFile="${ProjectPath}/src/main/java"
export TargetPath="${ProjectPath}/src/main/jni"
# 打印验证：源文件
echo "SourceFile = "$SourceFile


# 进入源文件
cd "${SourceFile}"

# 执行
javah -d ${TargetPath} -classpath "${SourceFile}" "${TargetClassName}"
# 打印验证：目标文件
echo "TargetPath = " ${TargetPath}




# TargetClassName 为含有native方法的类
# sourceFile 为java源代码目录
# TargetPath 为jni源代码目录
# 运行本shell：sh hello.sh



