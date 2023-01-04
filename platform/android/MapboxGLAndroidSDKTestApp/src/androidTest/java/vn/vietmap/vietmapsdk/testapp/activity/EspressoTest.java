package vn.vietmap.vietmapsdk.testapp.activity;

import androidx.annotation.UiThread;

import vn.vietmap.vietmapsdk.maps.MapboxMap;
import vn.vietmap.vietmapsdk.maps.Style;
import com.mapbox.vietmapsdk.testapp.activity.espresso.EspressoTestActivity;
import vn.vietmap.vietmapsdk.maps.MapboxMap;
import vn.vietmap.vietmapsdk.maps.Style;
import vn.vietmap.vietmapsdk.maps.MapboxMap;
import vn.vietmap.vietmapsdk.maps.Style;


/**
 * Base class for all tests using EspressoTestActivity as wrapper.
 * <p>
 * Loads "assets/streets.json" as style.
 * </p>
 */
public class EspressoTest extends BaseTest {

  @Override
  protected final Class getActivityClass() {
    return EspressoTestActivity.class;
  }

  @UiThread
  @Override
  protected void initMap(MapboxMap mapboxMap) {
    mapboxMap.setStyle(new Style.Builder().fromUri("asset://streets.json"));
    super.initMap(mapboxMap);
  }
}
