package vn.vietmap.vietmapsdk.testapp.style;

import android.graphics.Color;
import android.view.View;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import vn.vietmap.vietmapsdk.style.expressions.Expression;
import vn.vietmap.vietmapsdk.style.layers.FillExtrusionLayer;
import vn.vietmap.vietmapsdk.style.layers.TransitionOptions;
import vn.vietmap.vietmapsdk.style.light.Light;
import vn.vietmap.vietmapsdk.style.light.Position;
import vn.vietmap.vietmapsdk.testapp.R;
import vn.vietmap.vietmapsdk.testapp.activity.BaseTest;
import com.mapbox.vietmapsdk.testapp.activity.style.FillExtrusionStyleTestActivity;
import vn.vietmap.vietmapsdk.style.expressions.Expression;
import vn.vietmap.vietmapsdk.style.layers.FillExtrusionLayer;
import vn.vietmap.vietmapsdk.style.layers.Property;
import vn.vietmap.vietmapsdk.style.layers.PropertyFactory;
import vn.vietmap.vietmapsdk.style.layers.TransitionOptions;
import vn.vietmap.vietmapsdk.style.light.Light;
import vn.vietmap.vietmapsdk.style.light.Position;
import vn.vietmap.vietmapsdk.testapp.action.MapboxMapAction;
import vn.vietmap.vietmapsdk.testapp.activity.BaseTest;

import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.runner.RunWith;

import timber.log.Timber;
import vn.vietmap.vietmapsdk.style.expressions.Expression;
import vn.vietmap.vietmapsdk.style.layers.FillExtrusionLayer;
import vn.vietmap.vietmapsdk.style.layers.Property;
import vn.vietmap.vietmapsdk.style.layers.PropertyFactory;
import vn.vietmap.vietmapsdk.style.layers.TransitionOptions;
import vn.vietmap.vietmapsdk.style.light.Light;
import vn.vietmap.vietmapsdk.style.light.Position;
import vn.vietmap.vietmapsdk.testapp.action.MapboxMapAction;
import vn.vietmap.vietmapsdk.testapp.activity.BaseTest;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static vn.vietmap.vietmapsdk.style.expressions.Expression.eq;
import static vn.vietmap.vietmapsdk.style.layers.Property.ANCHOR_MAP;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.fillExtrusionBase;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.fillExtrusionColor;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.fillExtrusionHeight;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.fillExtrusionOpacity;
import static vn.vietmap.vietmapsdk.testapp.action.MapboxMapAction.invoke;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

import junit.framework.Assert;

@RunWith(AndroidJUnit4.class)
public class LightTest extends BaseTest {

  private Light light;

  @Test
  public void testAnchor() {
    validateTestSetup();
    setupLight();
    Timber.i("anchor");
    MapboxMapAction.invoke(mapboxMap, (uiController, mapboxMap) -> {
      assertNotNull(light);
      // Set and Get
      light.setAnchor(Property.ANCHOR_MAP);
      Assert.assertEquals("Anchor should match", Property.ANCHOR_MAP, light.getAnchor());
    });
  }

  @Test
  public void testPositionTransition() {
    validateTestSetup();
    setupLight();
    Timber.i("positionTransitionOptions");
    MapboxMapAction.invoke(mapboxMap, (uiController, mapboxMap) -> {
      assertNotNull(light);
      // Set and Get
      TransitionOptions options = new TransitionOptions(300, 100);
      light.setPositionTransition(options);
      Assert.assertEquals("Transition options should match", options, light.getPositionTransition());
    });
  }

  @Test
  public void testPosition() {
    validateTestSetup();
    setupLight();
    Timber.i("position");
    MapboxMapAction.invoke(mapboxMap,(uiController, mapboxMap) -> {
      assertNotNull(light);
      // Set and Get
      Position position = new Position(1, 2, 3);
      light.setPosition(position);
      assertEquals("Position should match", position, light.getPosition());
    });
  }

  @Test
  public void testColorTransition() {
    validateTestSetup();
    setupLight();
    Timber.i("colorTransitionOptions");
    MapboxMapAction.invoke(mapboxMap, (uiController, mapboxMap) -> {
      assertNotNull(light);
      // Set and Get
      TransitionOptions options = new TransitionOptions(300, 100);
      light.setColorTransition(options);
      Assert.assertEquals("Transition options should match", options, light.getColorTransition());
    });
  }

  @Test
  public void testColor() {
    validateTestSetup();
    setupLight();
    Timber.i("color");
    MapboxMapAction.invoke(mapboxMap, (uiController, mapboxMap) -> {
      assertNotNull(light);
      // Set and Get
      light.setColor("rgba(255,128,0,0.7)");
      assertEquals("Color should match", "rgba(255,128,0,0.7)", light.getColor());
    });
  }

  @Test
  public void testIntensityTransition() {
    validateTestSetup();
    setupLight();
    Timber.i("intensityTransitionOptions");
    MapboxMapAction.invoke(mapboxMap, (uiController, mapboxMap) -> {
      assertNotNull(light);
      // Set and Get
      TransitionOptions options = new TransitionOptions(300, 100);
      light.setIntensityTransition(options);
      Assert.assertEquals("Transition options should match", options, light.getIntensityTransition());
    });
  }

  @Test
  public void testIntensity() {
    validateTestSetup();
    setupLight();
    Timber.i("intensity");
    MapboxMapAction.invoke(mapboxMap, (uiController, mapboxMap) -> {
      assertNotNull(light);
      // Set and Get
      light.setIntensity(0.3f);
      assertEquals("Intensity should match", 0.3f, light.getIntensity());
    });
  }

  private void setupLight() {
    onView(withId(R.id.mapView)).perform(new ViewAction() {
      @Override
      public Matcher<View> getConstraints() {
        return isDisplayed();
      }

      @Override
      public String getDescription() {
        return getClass().getSimpleName();
      }

      @Override
      public void perform(UiController uiController, View view) {
        light = mapboxMap.getStyle().getLight();
        FillExtrusionLayer fillExtrusionLayer = new FillExtrusionLayer("3d-buildings", "composite");
        fillExtrusionLayer.setSourceLayer("building");
        fillExtrusionLayer.setFilter(Expression.eq(Expression.get("extrude"), "true"));
        fillExtrusionLayer.setMinZoom(15);
        fillExtrusionLayer.setProperties(
          PropertyFactory.fillExtrusionColor(Color.LTGRAY),
          PropertyFactory.fillExtrusionHeight(Expression.get("height")),
          PropertyFactory.fillExtrusionBase(Expression.get("min_height")),
          PropertyFactory.fillExtrusionOpacity(0.6f)
        );
        mapboxMap.getStyle().addLayer(fillExtrusionLayer);
      }
    });
  }

  @Override
  protected Class getActivityClass() {
    return FillExtrusionStyleTestActivity.class;
  }
}