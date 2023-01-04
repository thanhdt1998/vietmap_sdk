package vn.vietmap.vietmapsdk.maps;

import android.graphics.RectF;

import androidx.annotation.NonNull;

import vn.vietmap.vietmapsdk.annotations.BaseMarkerOptions;
import vn.vietmap.vietmapsdk.annotations.Marker;
import vn.vietmap.vietmapsdk.annotations.BaseMarkerOptions;
import vn.vietmap.vietmapsdk.annotations.Marker;

import java.util.List;

import vn.vietmap.vietmapsdk.annotations.BaseMarkerOptions;
import vn.vietmap.vietmapsdk.annotations.Marker;

/**
 * Interface that defines convenient methods for working with a {@link Marker}'s collection.
 */
interface Markers {
  Marker addBy(@NonNull BaseMarkerOptions markerOptions, @NonNull VietmapMap VietmapMap);

  List<Marker> addBy(@NonNull List<? extends BaseMarkerOptions> markerOptionsList, @NonNull VietmapMap VietmapMap);

  void update(@NonNull Marker updatedMarker, @NonNull VietmapMap VietmapMap);

  List<Marker> obtainAll();

  @NonNull
  List<Marker> obtainAllIn(@NonNull RectF rectangle);

  void reload();
}
