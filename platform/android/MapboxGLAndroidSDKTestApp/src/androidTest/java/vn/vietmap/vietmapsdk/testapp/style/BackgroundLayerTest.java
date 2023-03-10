// This file is generated. Edit scripts/generate-style-code.js, then run `make style-code`.

package vn.vietmap.vietmapsdk.testapp.style;

import android.graphics.Color;

import androidx.test.annotation.UiThreadTest;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.mapbox.geojson.Point;
import com.mapbox.vietmapsdk.maps.BaseLayerTest;
import vn.vietmap.vietmapsdk.style.layers.BackgroundLayer;
import vn.vietmap.vietmapsdk.style.layers.TransitionOptions;
import vn.vietmap.vietmapsdk.style.layers.BackgroundLayer;
import vn.vietmap.vietmapsdk.style.layers.Property;
import vn.vietmap.vietmapsdk.style.layers.PropertyFactory;
import vn.vietmap.vietmapsdk.style.layers.TransitionOptions;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;
import vn.vietmap.vietmapsdk.style.layers.BackgroundLayer;
import vn.vietmap.vietmapsdk.style.layers.Property;
import vn.vietmap.vietmapsdk.style.layers.PropertyFactory;
import vn.vietmap.vietmapsdk.style.layers.TransitionOptions;

import static vn.vietmap.vietmapsdk.style.layers.Property.NONE;
import static vn.vietmap.vietmapsdk.style.layers.Property.VISIBLE;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.backgroundColor;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.backgroundOpacity;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.backgroundPattern;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.visibility;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Basic smoke tests for BackgroundLayer
 */
@RunWith(AndroidJUnit4.class)
public class BackgroundLayerTest extends BaseLayerTest {

  private BackgroundLayer layer;
  private final List<Point> pointsList = new ArrayList<Point>() {
    {
      add(Point.fromLngLat(55.30122473231012, 25.26476622289597));
      add(Point.fromLngLat(55.29743486255916, 25.25827212207261));
      add(Point.fromLngLat(55.28978863411328, 25.251356725509737));
      add(Point.fromLngLat(55.300027931336984, 25.246425506635504));
      add(Point.fromLngLat(55.307474692951274, 25.244200378933655));
      add(Point.fromLngLat(55.31212891895635, 25.256408010450187));
      add(Point.fromLngLat(55.30774064871093, 25.26266169122738));
      add(Point.fromLngLat(55.301357710197806, 25.264946609615492));
      add(Point.fromLngLat(55.30122473231012, 25.26476622289597));
    }
  };

  @Before
  @UiThreadTest
  public void beforeTest(){
    super.before();
    layer = new BackgroundLayer("my-layer");
    setupLayer(layer);
  }

  @Test
  @UiThreadTest
  public void testSetVisibility() {
    Timber.i("Visibility");
    assertNotNull(layer);

    // Get initial
    Assert.assertEquals(layer.getVisibility().getValue(), Property.VISIBLE);

    // Set
    layer.setProperties(PropertyFactory.visibility(Property.NONE));
    Assert.assertEquals(layer.getVisibility().getValue(), Property.NONE);
  }

  @Test
  @UiThreadTest
  public void testBackgroundColorTransition() {
    Timber.i("background-colorTransitionOptions");
    assertNotNull(layer);

    // Set and Get
    TransitionOptions options = new TransitionOptions(300, 100);
    layer.setBackgroundColorTransition(options);
    assertEquals(layer.getBackgroundColorTransition(), options);
  }

  @Test
  @UiThreadTest
  public void testBackgroundColorAsConstant() {
    Timber.i("background-color");
    assertNotNull(layer);
    assertNull(layer.getBackgroundColor().getValue());

    // Set and Get
    String propertyValue = "rgba(255,128,0,0.7)";
    layer.setProperties(PropertyFactory.backgroundColor(propertyValue));
    assertEquals(layer.getBackgroundColor().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testBackgroundColorAsIntConstant() {
    Timber.i("background-color");
    assertNotNull(layer);

    // Set and Get
    layer.setProperties(PropertyFactory.backgroundColor(Color.argb(127, 255, 127, 0)));
    assertEquals(layer.getBackgroundColorAsInt(), Color.argb(127, 255, 127, 0));
  }

  @Test
  @UiThreadTest
  public void testBackgroundPatternTransition() {
    Timber.i("background-patternTransitionOptions");
    assertNotNull(layer);

    // Set and Get
    TransitionOptions options = new TransitionOptions(300, 100);
    layer.setBackgroundPatternTransition(options);
    assertEquals(layer.getBackgroundPatternTransition(), options);
  }

  @Test
  @UiThreadTest
  public void testBackgroundPatternAsConstant() {
    Timber.i("background-pattern");
    assertNotNull(layer);
    assertNull(layer.getBackgroundPattern().getValue());

    // Set and Get
    String propertyValue = "pedestrian-polygon";
    layer.setProperties(PropertyFactory.backgroundPattern(propertyValue));
    assertEquals(layer.getBackgroundPattern().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testBackgroundOpacityTransition() {
    Timber.i("background-opacityTransitionOptions");
    assertNotNull(layer);

    // Set and Get
    TransitionOptions options = new TransitionOptions(300, 100);
    layer.setBackgroundOpacityTransition(options);
    assertEquals(layer.getBackgroundOpacityTransition(), options);
  }

  @Test
  @UiThreadTest
  public void testBackgroundOpacityAsConstant() {
    Timber.i("background-opacity");
    assertNotNull(layer);
    assertNull(layer.getBackgroundOpacity().getValue());

    // Set and Get
    Float propertyValue = 0.3f;
    layer.setProperties(PropertyFactory.backgroundOpacity(propertyValue));
    assertEquals(layer.getBackgroundOpacity().getValue(), propertyValue);
  }
}
