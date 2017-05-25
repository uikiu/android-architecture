package com.android001.common.hardware.sim.gione;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import java.util.List;

public abstract interface IDialPadExtension
{
  public abstract void buildOptionsMenu(Activity paramActivity, Menu paramMenu);
  
  public abstract void constructPopupMenu(PopupMenu paramPopupMenu, View paramView, Menu paramMenu);
  
  public abstract List<String> getSingleIMEI(List<String> paramList);
  
  public abstract boolean handleChars(Context paramContext, String paramString);
  
  public abstract String handleSecretCode(String paramString);
  
//  public abstract void onCreate(Context paramContext, Fragment paramFragment, DialpadExtensionAction paramDialpadExtensionAction);
  
  public abstract View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle, View paramView);
  
  public abstract void onDestroy();
  
  public abstract void onHiddenChanged(boolean paramBoolean, int paramInt);
  
  public abstract void onViewCreated(Activity paramActivity, View paramView);
  
  public abstract void showDialpadChooser(boolean paramBoolean);
}
