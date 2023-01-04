package vn.vietmap.vietmapsdk.testapp.utils;

import android.content.Context;
import android.widget.TextView;

import vn.vietmap.vietmapsdk.camera.CameraPosition;
import vn.vietmap.vietmapsdk.maps.VietmapMap;
import vn.vietmap.vietmapsdk.testapp.R;

public class IdleZoomListener implements VietmapMap.OnCameraIdleListener {

  private VietmapMap VietmapMap;
  private TextView textView;

  public IdleZoomListener(VietmapMap VietmapMap, TextView textView) {
    this.VietmapMap = VietmapMap;
    this.textView = textView;
  }

  @Override
  public void onCameraIdle() {
    Context context = textView.getContext();
    CameraPosition position = VietmapMap.getCameraPosition();
    textView.setText(String.format(context.getString(R.string.debug_zoom), position.zoom));
  }
}