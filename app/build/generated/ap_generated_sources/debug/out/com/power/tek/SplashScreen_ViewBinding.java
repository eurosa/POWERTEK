// Generated code from Butter Knife. Do not modify!
package com.power.tek;

import android.view.View;
import android.widget.ImageView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SplashScreen_ViewBinding implements Unbinder {
  private SplashScreen target;

  @UiThread
  public SplashScreen_ViewBinding(SplashScreen target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SplashScreen_ViewBinding(SplashScreen target, View source) {
    this.target = target;

    target.mLogo = Utils.findRequiredViewAsType(source, R.id.imageView2, "field 'mLogo'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SplashScreen target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mLogo = null;
  }
}
