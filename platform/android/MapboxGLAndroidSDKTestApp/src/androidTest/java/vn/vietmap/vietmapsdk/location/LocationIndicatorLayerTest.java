// This file is generated. Edit scripts/generate-style-code.js, then run `make style-code`.

package vn.vietmap.vietmapsdk.location;

import android.graphics.Color;

import androidx.test.annotation.UiThreadTest;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.mapbox.vietmapsdk.maps.BaseLayerTest;
import vn.vietmap.vietmapsdk.style.layers.TransitionOptions;
import vn.vietmap.vietmapsdk.style.layers.Property;
import vn.vietmap.vietmapsdk.style.layers.TransitionOptions;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import timber.log.Timber;
import vn.vietmap.vietmapsdk.style.layers.Property;
import vn.vietmap.vietmapsdk.style.layers.TransitionOptions;

import static vn.vietmap.vietmapsdk.location.LocationPropertyFactory.accuracyRadius;
import static vn.vietmap.vietmapsdk.location.LocationPropertyFactory.accuracyRadiusBorderColor;
import static vn.vietmap.vietmapsdk.location.LocationPropertyFactory.accuracyRadiusColor;
import static vn.vietmap.vietmapsdk.location.LocationPropertyFactory.bearing;
import static vn.vietmap.vietmapsdk.location.LocationPropertyFactory.bearingImage;
import static vn.vietmap.vietmapsdk.location.LocationPropertyFactory.bearingImageSize;
import static vn.vietmap.vietmapsdk.location.LocationPropertyFactory.imageTiltDisplacement;
import static vn.vietmap.vietmapsdk.location.LocationPropertyFactory.location;
import static vn.vietmap.vietmapsdk.location.LocationPropertyFactory.perspectiveCompensation;
import static vn.vietmap.vietmapsdk.location.LocationPropertyFactory.shadowImage;
import static vn.vietmap.vietmapsdk.location.LocationPropertyFactory.shadowImageSize;
import static vn.vietmap.vietmapsdk.location.LocationPropertyFactory.topImage;
import static vn.vietmap.vietmapsdk.location.LocationPropertyFactory.topImageSize;
import static vn.vietmap.vietmapsdk.location.LocationPropertyFactory.visibility;
import static vn.vietmap.vietmapsdk.style.layers.Property.NONE;
import static vn.vietmap.vietmapsdk.style.layers.Property.VISIBLE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Basic smoke tests for LocationIndicatorLayer
 */
@RunWith(AndroidJUnit4.class)
public class LocationIndicatorLayerTest extends BaseLayerTest {

  private LocationIndicatorLayer layer;

  @Before
  @UiThreadTest
  public void beforeTest(){
    super.before();
    layer = new LocationIndicatorLayer("my-layer");
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
    layer.setProperties(LocationPropertyFactory.visibility(Property.NONE));
    Assert.assertEquals(layer.getVisibility().getValue(), Property.NONE);
  }

  @Test
  @UiThreadTest
  public void testTopImageAsConstant() {
    Timber.i("top-image");
    assertNotNull(layer);
    assertNull(layer.getTopImage().getValue());

    // Set and Get
    String propertyValue = "undefined";
    layer.setProperties(LocationPropertyFactory.topImage(propertyValue));
    Assert.assertEquals(layer.getTopImage().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testBearingImageAsConstant() {
    Timber.i("bearing-image");
    assertNotNull(layer);
    assertNull(layer.getBearingImage().getValue());

    // Set and Get
    String propertyValue = "undefined";
    layer.setProperties(LocationPropertyFactory.bearingImage(propertyValue));
    Assert.assertEquals(layer.getBearingImage().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testShadowImageAsConstant() {
    Timber.i("shadow-image");
    assertNotNull(layer);
    assertNull(layer.getShadowImage().getValue());

    // Set and Get
    String propertyValue = "undefined";
    layer.setProperties(LocationPropertyFactory.shadowImage(propertyValue));
    Assert.assertEquals(layer.getShadowImage().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testPerspectiveCompensationAsConstant() {
    Timber.i("perspective-compensation");
    assertNotNull(layer);
    assertNull(layer.getPerspectiveCompensation().getValue());

    // Set and Get
    Float propertyValue = 0.3f;
    layer.setProperties(LocationPropertyFactory.perspectiveCompensation(propertyValue));
    Assert.assertEquals(layer.getPerspectiveCompensation().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testImageTiltDisplacementAsConstant() {
    Timber.i("image-tilt-displacement");
    assertNotNull(layer);
    assertNull(layer.getImageTiltDisplacement().getValue());

    // Set and Get
    Float propertyValue = 0.3f;
    layer.setProperties(LocationPropertyFactory.imageTiltDisplacement(propertyValue));
    Assert.assertEquals(layer.getImageTiltDisplacement().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testBearingAsConstant() {
    Timber.i("bearing");
    assertNotNull(layer);
    assertNull(layer.getBearing().getValue());

    // Set and Get
    Double propertyValue = 0.3;
    layer.setProperties(LocationPropertyFactory.bearing(propertyValue));
    Assert.assertEquals(layer.getBearing().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testLocationTransition() {
    Timber.i("locationTransitionOptions");
    assertNotNull(layer);

    // Set and Get
    TransitionOptions options = new TransitionOptions(300, 100);
    layer.setLocationTransition(options);
    Assert.assertEquals(layer.getLocationTransition(), options);
  }

  @Test
  @UiThreadTest
  public void testLocationAsConstant() {
    Timber.i("location");
    assertNotNull(layer);
    assertNull(layer.getLocation().getValue());

    // Set and Get
    Double[] propertyValue = new Double[] {0.0, 0.0, 0.0};
    layer.setProperties(LocationPropertyFactory.location(propertyValue));
    Assert.assertEquals(layer.getLocation().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testAccuracyRadiusTransition() {
    Timber.i("accuracy-radiusTransitionOptions");
    assertNotNull(layer);

    // Set and Get
    TransitionOptions options = new TransitionOptions(300, 100);
    layer.setAccuracyRadiusTransition(options);
    Assert.assertEquals(layer.getAccuracyRadiusTransition(), options);
  }

  @Test
  @UiThreadTest
  public void testAccuracyRadiusAsConstant() {
    Timber.i("accuracy-radius");
    assertNotNull(layer);
    assertNull(layer.getAccuracyRadius().getValue());

    // Set and Get
    Float propertyValue = 0.3f;
    layer.setProperties(LocationPropertyFactory.accuracyRadius(propertyValue));
    Assert.assertEquals(layer.getAccuracyRadius().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testTopImageSizeTransition() {
    Timber.i("top-image-sizeTransitionOptions");
    assertNotNull(layer);

    // Set and Get
    TransitionOptions options = new TransitionOptions(300, 100);
    layer.setTopImageSizeTransition(options);
    Assert.assertEquals(layer.getTopImageSizeTransition(), options);
  }

  @Test
  @UiThreadTest
  public void testTopImageSizeAsConstant() {
    Timber.i("top-image-size");
    assertNotNull(layer);
    assertNull(layer.getTopImageSize().getValue());

    // Set and Get
    Float propertyValue = 0.3f;
    layer.setProperties(LocationPropertyFactory.topImageSize(propertyValue));
    Assert.assertEquals(layer.getTopImageSize().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testBearingImageSizeTransition() {
    Timber.i("bearing-image-sizeTransitionOptions");
    assertNotNull(layer);

    // Set and Get
    TransitionOptions options = new TransitionOptions(300, 100);
    layer.setBearingImageSizeTransition(options);
    Assert.assertEquals(layer.getBearingImageSizeTransition(), options);
  }

  @Test
  @UiThreadTest
  public void testBearingImageSizeAsConstant() {
    Timber.i("bearing-image-size");
    assertNotNull(layer);
    assertNull(layer.getBearingImageSize().getValue());

    // Set and Get
    Float propertyValue = 0.3f;
    layer.setProperties(LocationPropertyFactory.bearingImageSize(propertyValue));
    Assert.assertEquals(layer.getBearingImageSize().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testShadowImageSizeTransition() {
    Timber.i("shadow-image-sizeTransitionOptions");
    assertNotNull(layer);

    // Set and Get
    TransitionOptions options = new TransitionOptions(300, 100);
    layer.setShadowImageSizeTransition(options);
    Assert.assertEquals(layer.getShadowImageSizeTransition(), options);
  }

  @Test
  @UiThreadTest
  public void testShadowImageSizeAsConstant() {
    Timber.i("shadow-image-size");
    assertNotNull(layer);
    assertNull(layer.getShadowImageSize().getValue());

    // Set and Get
    Float propertyValue = 0.3f;
    layer.setProperties(LocationPropertyFactory.shadowImageSize(propertyValue));
    Assert.assertEquals(layer.getShadowImageSize().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testAccuracyRadiusColorTransition() {
    Timber.i("accuracy-radius-colorTransitionOptions");
    assertNotNull(layer);

    // Set and Get
    TransitionOptions options = new TransitionOptions(300, 100);
    layer.setAccuracyRadiusColorTransition(options);
    Assert.assertEquals(layer.getAccuracyRadiusColorTransition(), options);
  }

  @Test
  @UiThreadTest
  public void testAccuracyRadiusColorAsConstant() {
    Timber.i("accuracy-radius-color");
    assertNotNull(layer);
    assertNull(layer.getAccuracyRadiusColor().getValue());

    // Set and Get
    String propertyValue = "rgba(255,128,0,0.7)";
    layer.setProperties(LocationPropertyFactory.accuracyRadiusColor(propertyValue));
    Assert.assertEquals(layer.getAccuracyRadiusColor().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testAccuracyRadiusColorAsIntConstant() {
    Timber.i("accuracy-radius-color");
    assertNotNull(layer);

    // Set and Get
    layer.setProperties(LocationPropertyFactory.accuracyRadiusColor(Color.argb(127, 255, 127, 0)));
    assertEquals(layer.getAccuracyRadiusColorAsInt(), Color.argb(127, 255, 127, 0));
  }

  @Test
  @UiThreadTest
  public void testAccuracyRadiusBorderColorTransition() {
    Timber.i("accuracy-radius-border-colorTransitionOptions");
    assertNotNull(layer);

    // Set and Get
    TransitionOptions options = new TransitionOptions(300, 100);
    layer.setAccuracyRadiusBorderColorTransition(options);
    Assert.assertEquals(layer.getAccuracyRadiusBorderColorTransition(), options);
  }

  @Test
  @UiThreadTest
  public void testAccuracyRadiusBorderColorAsConstant() {
    Timber.i("accuracy-radius-border-color");
    assertNotNull(layer);
    assertNull(layer.getAccuracyRadiusBorderColor().getValue());

    // Set and Get
    String propertyValue = "rgba(255,128,0,0.7)";
    layer.setProperties(LocationPropertyFactory.accuracyRadiusBorderColor(propertyValue));
    Assert.assertEquals(layer.getAccuracyRadiusBorderColor().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testAccuracyRadiusBorderColorAsIntConstant() {
    Timber.i("accuracy-radius-border-color");
    assertNotNull(layer);

    // Set and Get
    layer.setProperties(LocationPropertyFactory.accuracyRadiusBorderColor(Color.argb(127, 255, 127, 0)));
    assertEquals(layer.getAccuracyRadiusBorderColorAsInt(), Color.argb(127, 255, 127, 0));
  }
}
