package vn.vietmap.vietmapsdk.location;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import com.mapbox.vietmapsdk.R;
import vn.vietmap.vietmapsdk.utils.BitmapUtils;

import static vn.vietmap.vietmapsdk.location.Utils.generateShadow;

class LayerBitmapProvider {

  private final Context context;

  LayerBitmapProvider(Context context) {
    this.context = context;
  }

  Bitmap generateBitmap(@DrawableRes int drawableRes, @ColorInt Integer tintColor) {
    Drawable drawable = BitmapUtils.getDrawableFromRes(context, drawableRes, tintColor);
    return BitmapUtils.getBitmapFromDrawable(drawable);
  }

  Bitmap generateShadowBitmap(@NonNull LocationComponentOptions options) {
    Drawable shadowDrawable = BitmapUtils.getDrawableFromRes(context, R.drawable.mapbox_user_icon_shadow);
    return generateShadow(shadowDrawable, options.elevation());
  }
}
