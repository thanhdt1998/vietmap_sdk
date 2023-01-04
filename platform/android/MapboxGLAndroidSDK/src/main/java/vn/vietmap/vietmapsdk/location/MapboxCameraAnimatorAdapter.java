package vn.vietmap.vietmapsdk.location;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Size;

import vn.vietmap.vietmapsdk.maps.VietmapMap;

class MapboxCameraAnimatorAdapter extends MapboxFloatAnimator {
  @Nullable
  private final VietmapMap.CancelableCallback cancelableCallback;

  MapboxCameraAnimatorAdapter(@NonNull @Size(min = 2) Float[] values,
                              AnimationsValueChangeListener updateListener,
                              @Nullable VietmapMap.CancelableCallback cancelableCallback) {
    super(values, updateListener, Integer.MAX_VALUE);
    this.cancelableCallback = cancelableCallback;
    addListener(new MapboxAnimatorListener());
  }

  private final class MapboxAnimatorListener extends AnimatorListenerAdapter {
    @Override
    public void onAnimationCancel(Animator animation) {
      if (cancelableCallback != null) {
        cancelableCallback.onCancel();
      }
    }

    @Override
    public void onAnimationEnd(Animator animation) {
      if (cancelableCallback != null) {
        cancelableCallback.onFinish();
      }
    }
  }
}
