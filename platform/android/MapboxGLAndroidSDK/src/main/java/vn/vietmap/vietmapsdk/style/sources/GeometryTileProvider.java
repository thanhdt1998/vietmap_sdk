package vn.vietmap.vietmapsdk.style.sources;

import androidx.annotation.WorkerThread;

import com.mapbox.geojson.FeatureCollection;
import vn.vietmap.vietmapsdk.geometry.LatLngBounds;
import vn.vietmap.vietmapsdk.geometry.LatLngBounds;
import vn.vietmap.vietmapsdk.geometry.LatLngBounds;

/**
 * Interface that defines methods for working with {@link CustomGeometrySource}.
 */
public interface GeometryTileProvider {

  /***
   * Interface method called by {@link CustomGeometrySource} to request features for a tile.
   *
   * @param bounds {@link LatLngBounds} of the tile.
   * @param zoomLevel Tile zoom level.
   * @return Return a @{link FeatureCollection} to be displayed in the requested tile.
   */
  @WorkerThread
  FeatureCollection getFeaturesForBounds(LatLngBounds bounds, int zoomLevel);
}
