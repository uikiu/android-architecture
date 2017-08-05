# android 打包so库的一种方式ndk-build

### 内部构建
**ndk-build本质是一个文件，是从Android NDK r4中引入的一个shell脚本。其用途是调用正确的NDK构建脚本。**<br>
运行ndk-build脚本相当于运行以下命令：
```
$GNUMAKE -f <ndk>/build/core/build-local.mk
<parameters>
```

* $GNUMAKE指向GNU Make3.81或更高版本。
* <ndk>指向ndk安装目录。

这里说一下ndk的默认路径:
```
/Users/XXX/Library/Android/sdk/ndk-bundle
```
运行ndk-build 其实就是加载的构建文件build-local.mk  而build-local.mk文件位置是：
```
/Users/XXX/Library/Android/sdk/ndk-bundle/build/core/build-local.mk

```


* 通过命令运行build-local.mk文件。











