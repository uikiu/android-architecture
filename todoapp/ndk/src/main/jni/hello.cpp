#include "com_android001_ndk_Hello.h"

// 根据头文件编写jni代码
JNIEXPORT jstring JNICALL Java_com_android001_ndk_Hello_helloJni
  (JNIEnv *env, jobject) //两个环境：JNI环境，和JAVA环境
  {
        return env->NewStringUTF("Hello from jni");
  }
