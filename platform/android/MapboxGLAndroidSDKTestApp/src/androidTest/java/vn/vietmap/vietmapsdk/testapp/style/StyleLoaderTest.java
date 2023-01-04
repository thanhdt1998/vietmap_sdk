package vn.vietmap.vietmapsdk.testapp.style;

import vn.vietmap.vietmapsdk.maps.MapView;
import vn.vietmap.vietmapsdk.maps.Style;
import vn.vietmap.vietmapsdk.testapp.R;
import vn.vietmap.vietmapsdk.testapp.activity.EspressoTest;
import com.mapbox.vietmapsdk.testapp.utils.ResourceUtils;
import vn.vietmap.vietmapsdk.maps.MapView;
import vn.vietmap.vietmapsdk.maps.Style;
import vn.vietmap.vietmapsdk.testapp.action.MapboxMapAction;
import vn.vietmap.vietmapsdk.testapp.activity.EspressoTest;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

import static vn.vietmap.vietmapsdk.testapp.action.MapboxMapAction.invoke;
import static org.junit.Assert.assertEquals;

import vn.vietmap.vietmapsdk.maps.MapView;
import vn.vietmap.vietmapsdk.maps.Style;
import vn.vietmap.vietmapsdk.testapp.action.MapboxMapAction;
import vn.vietmap.vietmapsdk.testapp.activity.EspressoTest;

/**
 * Tests around style loading
 */
public class StyleLoaderTest extends EspressoTest {

  @Test
  public void testSetGetStyleJsonString() {
    validateTestSetup();
    MapboxMapAction.invoke(mapboxMap, (uiController, mapboxMap) -> {
      try {
        String expected = ResourceUtils.readRawResource(rule.getActivity(), R.raw.local_style);
        mapboxMap.setStyle(new Style.Builder().fromJson(expected));
        String actual = mapboxMap.getStyle().getJson();
        assertEquals("Style json should match", expected, actual);
      } catch (IOException exception) {
        exception.printStackTrace();
      }
    });
  }

  @Test
  public void testDefaultStyleLoadWithActivityLifecycleChange() {
    validateTestSetup();
    MapboxMapAction.invoke(mapboxMap, (uiController, mapboxMap) -> {
      try {
        String expected = ResourceUtils.readRawResource(rule.getActivity(), R.raw.local_style);
        mapboxMap.setStyle(new Style.Builder().fromJson(expected));

        // fake activity stop/start
        MapView mapView = (MapView) rule.getActivity().findViewById(R.id.mapView);
        mapView.onPause();
        mapView.onStop();

        mapView.onStart();
        mapView.onResume();

        String actual = mapboxMap.getStyle().getJson();
        Assert.assertEquals("Style URL should be empty", "", mapboxMap.getStyle().getUri());
        assertEquals("Style json should match", expected, actual);
      } catch (IOException exception) {
        exception.printStackTrace();
      }
    });
  }
}