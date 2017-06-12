[Android闹钟设置的解决方案](http://www.jianshu.com/p/1f919c6eeff6)

---

### android官方关于AlarmManager机制修改
* API19---android4.4 开始AlarmManager的机制修改（引入非准确传递：操作系统会转换闹钟，来最小化唤醒和电池使用）
* API24---android7.0 增加了AlarmManager.OnAlarmListener回调接口