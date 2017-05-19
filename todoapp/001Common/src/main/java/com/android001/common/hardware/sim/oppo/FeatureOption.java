package com.android001.common.hardware.sim.oppo;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.provider.Settings.Global;
import android.provider.Settings.SettingNotFoundException;
import android.provider.Settings.System;
import android.text.TextUtils;
import android.util.Log;

import com.android001.common.CommonUtilMethods;
import com.android001.common.os.android.SystemPropertiesAccessor;

public final class FeatureOption
{
  private static final boolean DEBUG = false;
  private static final String DIALER_LOG_SWITCH_DB = "dialer.log.switch";
  public static boolean LOG_SWITCH = false;
  private static final String NUMBERRECOGNITOIN_SWITCH = "numberrecognition_switch";
  private static final String TAG = "FeatureOption";
  public static boolean VERSION_US = false;
  private static final String YELLOWPAGE_SWITCH = "yellowpage_switch";
  private static boolean mDisplayContactsPhoto;
  private static boolean mIsAllClient;
  private static boolean mIsAllClientPromoted;
  private static boolean mIsMtkGeminiSupport;
  private static boolean mIsNotYellowPageVersion;
  private static boolean mIsNumberRecognitionOn = true;
  private static boolean mIsOppoCmccOptr;
  private static boolean mIsOppoHwMtk;
  private static boolean mIsOppoHwQualComm;
  private static boolean mIsQualcommGeminiSupport;
  private static volatile int mIsShowYellowpage;
  private static boolean mIsSwitchChanged = false;
  private static boolean mVoLTEChecked;
  
  static
  {
    mIsShowYellowpage = -1;
  }
  
  public FeatureOption() {}
  
  public static void init(Context paramContext)
  {
    boolean bool2 = true;
    int i = Settings.System.getInt(paramContext.getContentResolver(), "dialer.log.switch", 0);
    boolean bool1 = SystemPropertiesAccessor.getBoolean("persist.sys.assert.panic", false);
    if ((i == 1) || (bool1))
    {
      bool1 = true;
      LOG_SWITCH = bool1;
      mIsMtkGeminiSupport = paramContext.getPackageManager().hasSystemFeature("mtk.gemini.support");
      mIsQualcommGeminiSupport = paramContext.getPackageManager().hasSystemFeature("oppo.qualcomm.gemini.support");
      mIsOppoHwMtk = paramContext.getPackageManager().hasSystemFeature("oppo.hw.manufacturer.mtk");
      mIsOppoHwQualComm = paramContext.getPackageManager().hasSystemFeature("oppo.hw.manufacturer.qualcomm");
      mIsNotYellowPageVersion = paramContext.getPackageManager().hasSystemFeature("oppo.cmcc.test");
      mIsOppoCmccOptr = paramContext.getPackageManager().hasSystemFeature("oppo.cmcc.test");
      mIsAllClientPromoted = paramContext.getPackageManager().hasSystemFeature("oppo.all.client_7_5");
      mIsAllClient = paramContext.getPackageManager().hasSystemFeature("oppo.all.client");
      mDisplayContactsPhoto = TextUtils.equals(Settings.System.getString(paramContext.getContentResolver(), "oppo_display_contacts_photo"), "oppo_display_contacts_photo");
      voLTEChecked(paramContext);

//      mIsShowYellowpage = Settings.Global.getInt(paramContext.getContentResolver(), "yellowpage_switch", 1);
//      if (Settings.Global.getInt(paramContext.getContentResolver(), "numberrecognition_switch", 1) != 1) {
//        break label206;
//      }
    }
//    label206:
//    for (bool1 = bool2;; bool1 = false)
//    {
//      mIsNumberRecognitionOn = bool1;
//      VERSION_US = SystemPropertiesAccessor.get("ro.oppo.version", "CN").equalsIgnoreCase("US");
//      return;
//      bool1 = false;
//      break;
//    }

  }
  
  public static boolean isAllClient()
  {
    return mIsAllClient;
  }
  
  public static boolean isAllClientPromoted()
  {
    return mIsAllClientPromoted;
  }
  
  public static boolean isCtaSupport(Context paramContext)
  {
    return paramContext.getPackageManager().hasSystemFeature("oppo.cta.support");
  }
  
  public static boolean isDisplayContactsPhoto()
  {
    return mDisplayContactsPhoto;
  }
  
  public static boolean isGeminiSupported(Context paramContext)
  {
    return (mIsMtkGeminiSupport) || (mIsQualcommGeminiSupport);
  }
  
  public static boolean isMtkGeminiSupport(Context paramContext)
  {
    return mIsMtkGeminiSupport;
  }
  
  public static boolean isNotYellowPageVersion(Context paramContext)
  {
    if (mIsShowYellowpage == -1) {
      mIsShowYellowpage = Settings.Global.getInt(paramContext.getContentResolver(), "yellowpage_switch", 1);
    }
    if (mIsShowYellowpage == 0) {
      return true;
    }
    return mIsNotYellowPageVersion;
  }
  
  public static boolean isNumberRecognitionEnable(Context paramContext)
  {
    return !mIsNotYellowPageVersion;
  }
  
  public static boolean isOppoCmccOptr(Context paramContext)
  {
    return mIsOppoCmccOptr;
  }
  
  public static boolean isOppoCmccTest(Context paramContext)
  {
    return mIsNotYellowPageVersion;
  }
  
  public static boolean isOppoCtOptr(Context paramContext)
  {
//    return paramContext.getPackageManager().hasSystemFeature("oppo.ct.optr");
    return CommonUtilMethods.getPackageManager().hasSystemFeature("oppo.ct.optr");
  }
  
  public static boolean isOppoCuOptr(Context paramContext)
  {
    return paramContext.getPackageManager().hasSystemFeature("oppo.cu.optr");
  }
  
  public static boolean isOppoHwMtk(Context paramContext)
  {
    return mIsOppoHwMtk;
  }
  
  public static boolean isOppoHwQualComm(Context paramContext)
  {
    return mIsOppoHwQualComm;
  }
  
  public static boolean isOppoMobile()
  {
    return Build.MANUFACTURER.equalsIgnoreCase("OPPO");
  }
  
  public static boolean isQualcommGeminiSupport(Context paramContext)
  {
    return mIsQualcommGeminiSupport;
  }
  
  public static boolean isRechargeSwitchOn()
  {
    return mIsNumberRecognitionOn;
  }
  
  public static boolean isSwitchChanged()
  {
    return mIsSwitchChanged;
  }
  
  public static boolean isVideoCallEnable(Context paramContext)
  {
    if (paramContext == null) {
      return false;
    }
    return paramContext.getPackageManager().hasSystemFeature("oppo.phone.video.call");
  }
  
  public static boolean isVoLTEChecked()
  {
    return mVoLTEChecked;
  }
  
  public static void setDisplayContactsPhoto(boolean paramBoolean)
  {
    mIsSwitchChanged = true;
    mDisplayContactsPhoto = paramBoolean;
  }
  
  public static void voLTEChecked(Context paramContext)
  {
    boolean bool = true;
    if (paramContext == null) {
      return;
    }
    mVoLTEChecked = false;
    for (;;)
    {
      try
      {
        if (Settings.Global.getInt(paramContext.getContentResolver(), "volte_vt_enabled") == 1)
        {
          mVoLTEChecked = bool;
          return;
        }
      }
      catch (Settings.SettingNotFoundException exception)
      {
        Log.e("FeatureOption", "SettingNotFoundException:", exception);
        return;
      }
      bool = false;
    }
  }
}
