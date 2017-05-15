### 流程
* 全局搜索*#06#找到方法：SpecialCharSequenceMgr.handleIMEIDisplay()
```
  static boolean handleIMEIDisplay(Context paramContext, String paramString, boolean paramBoolean)
  {
    if (paramString.equals("*#06#"))
    {
      if (SimFactoryManager.isDualSim())
      {
        showDual_IMEI_Panel(paramContext, paramBoolean);
        return true;
      }
      int i = ((TelephonyManager)paramContext.getSystemService("phone")).getCurrentPhoneType();
      if (i == 1)
      {
        showIMEIPanel(paramContext, paramBoolean);
        return true;
      }
      if (i == 2)
      {
        showMEIDPanel(paramContext, paramBoolean);
        return true;
      }
    }
    return false;
  }

```




### 尚存在的问题或者疑点
通过上面源码可以看出，官方区分双卡，单卡之分。目前全部默认为双卡。以后需要修改。
SimFactoryManager.isDualSim()方法并没有填写代码，默认是双卡。
对mSimTelephonyManager_getCurrentPhoneType反编译没写


### 如何进一步扩展