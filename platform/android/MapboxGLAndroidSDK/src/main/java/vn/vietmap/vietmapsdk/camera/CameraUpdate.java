package vn.vietmap.vietmapsdk.camera;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import vn.vietmap.vietmapsdk.maps.VietmapMap;

/**
 * Interface definition for camera updates.
 */
public interface CameraUpdate {

  /**
   * Get the camera position from the camera update.
   *
   * @param VietmapMap Map object to build the position from
   * @return the camera position from the implementing camera update
   */
  @Nullable
  CameraPosition getCameraPosition(@NonNull VietmapMap VietmapMap);

}

