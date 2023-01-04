// This file is generated. Edit scripts/generate-style-code.js, then run `make style-code`.

package vn.vietmap.vietmapsdk.testapp.style;

import android.graphics.Color;

import androidx.test.annotation.UiThreadTest;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.mapbox.geojson.LineString;
import com.mapbox.geojson.MultiLineString;
import com.mapbox.geojson.MultiPoint;
import com.mapbox.geojson.MultiPolygon;
import com.mapbox.geojson.Point;
import com.mapbox.geojson.Polygon;
import com.mapbox.vietmapsdk.maps.BaseLayerTest;
import vn.vietmap.vietmapsdk.style.expressions.Expression;
import vn.vietmap.vietmapsdk.style.layers.CircleLayer;
import vn.vietmap.vietmapsdk.style.layers.TransitionOptions;
import vn.vietmap.vietmapsdk.style.expressions.Expression;
import vn.vietmap.vietmapsdk.style.layers.CircleLayer;
import vn.vietmap.vietmapsdk.style.layers.Property;
import vn.vietmap.vietmapsdk.style.layers.PropertyFactory;
import vn.vietmap.vietmapsdk.style.layers.TransitionOptions;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import timber.log.Timber;
import vn.vietmap.vietmapsdk.style.expressions.Expression;
import vn.vietmap.vietmapsdk.style.layers.CircleLayer;
import vn.vietmap.vietmapsdk.style.layers.Property;
import vn.vietmap.vietmapsdk.style.layers.PropertyFactory;
import vn.vietmap.vietmapsdk.style.layers.TransitionOptions;

import static vn.vietmap.vietmapsdk.style.expressions.Expression.distance;
import static vn.vietmap.vietmapsdk.style.expressions.Expression.eq;
import static vn.vietmap.vietmapsdk.style.expressions.Expression.get;
import static vn.vietmap.vietmapsdk.style.expressions.Expression.literal;
import static vn.vietmap.vietmapsdk.style.expressions.Expression.lt;
import static vn.vietmap.vietmapsdk.style.expressions.Expression.number;
import static vn.vietmap.vietmapsdk.style.expressions.Expression.toColor;
import static vn.vietmap.vietmapsdk.style.expressions.Expression.within;
import static vn.vietmap.vietmapsdk.style.layers.Property.CIRCLE_PITCH_ALIGNMENT_MAP;
import static vn.vietmap.vietmapsdk.style.layers.Property.CIRCLE_PITCH_SCALE_MAP;
import static vn.vietmap.vietmapsdk.style.layers.Property.CIRCLE_TRANSLATE_ANCHOR_MAP;
import static vn.vietmap.vietmapsdk.style.layers.Property.NONE;
import static vn.vietmap.vietmapsdk.style.layers.Property.VISIBLE;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.circleBlur;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.circleColor;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.circleOpacity;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.circlePitchAlignment;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.circlePitchScale;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.circleRadius;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.circleSortKey;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.circleStrokeColor;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.circleStrokeOpacity;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.circleStrokeWidth;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.circleTranslate;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.circleTranslateAnchor;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.visibility;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Basic smoke tests for CircleLayer
 */
@RunWith(AndroidJUnit4.class)
public class CircleLayerTest extends BaseLayerTest {

  private CircleLayer layer;
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
    layer = new CircleLayer("my-layer", "composite");
    layer.setSourceLayer("composite");
    setupLayer(layer);
  }

  @Test
  @UiThreadTest
  public void testSourceId() {
    Timber.i("SourceId");
    assertNotNull(layer);
    assertEquals(layer.getSourceId(), "composite");
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
  public void testSourceLayer() {
    Timber.i("SourceLayer");
    assertNotNull(layer);

    // Get initial
    assertEquals(layer.getSourceLayer(), "composite");

    // Set
    final String sourceLayer = "test";
    layer.setSourceLayer(sourceLayer);
    assertEquals(layer.getSourceLayer(), sourceLayer);
  }

  @Test
  @UiThreadTest
  public void testFilter() {
    Timber.i("Filter");
    assertNotNull(layer);

    // Get initial
    Assert.assertEquals(layer.getFilter(), null);

    // Set
    Expression filter = Expression.eq(Expression.get("undefined"), Expression.literal(1.0));
    layer.setFilter(filter);
    Assert.assertEquals(layer.getFilter().toString(), filter.toString());

    // Set constant
    filter = Expression.literal(true);
    layer.setFilter(filter);
    Assert.assertEquals(layer.getFilter().toString(), filter.toString());
  }

  @Test
  @UiThreadTest
  public void testFilterDistance() {
    Timber.i("FilterDistance");
    assertNotNull(layer);

    // Get initial
    Assert.assertEquals(layer.getFilter(), null);

    // distance with Point
    Expression filter = Expression.lt(Expression.distance(Point.fromLngLat(1.0, 1.0)), 50);
    layer.setFilter(filter);
    Assert.assertEquals(layer.getFilter().toString(), filter.toString());

    // distance with LineString
    filter = Expression.lt(Expression.distance(LineString.fromLngLats(pointsList)), 50);
    layer.setFilter(filter);
    Assert.assertEquals(layer.getFilter().toString(), filter.toString());

    // distance with MultiPoint
    filter = Expression.lt(Expression.distance(MultiPoint.fromLngLats(pointsList)), 50);
    layer.setFilter(filter);
    Assert.assertEquals(layer.getFilter().toString(), filter.toString());

    // distance with MultiPoint
    filter = Expression.lt(Expression.distance(MultiLineString.fromLngLats(Collections.singletonList(pointsList))), 50);
    layer.setFilter(filter);
    Assert.assertEquals(layer.getFilter().toString(), filter.toString());

    // distance with Polygon
    filter = Expression.lt(Expression.distance(Polygon.fromLngLats(Collections.singletonList(pointsList))), 50);
    layer.setFilter(filter);
    Assert.assertEquals(layer.getFilter().toString(), filter.toString());

    // distance with MultiPolygon
    filter = Expression.lt(Expression.distance(MultiPolygon.fromLngLats(Collections
      .singletonList(Collections.singletonList(pointsList)))), 50);
    layer.setFilter(filter);
    Assert.assertEquals(layer.getFilter().toString(), filter.toString());
  }

  @Test
  @UiThreadTest
  public void testFilterWithin() {
    Timber.i("FilterWithin");
    assertNotNull(layer);

    // Get initial
    Assert.assertEquals(layer.getFilter(), null);

    Expression filter = Expression.within(Polygon.fromLngLats(Collections.singletonList(pointsList)));
    layer.setFilter(filter);
    Assert.assertEquals(layer.getFilter().toString(), filter.toString());
  }


  @Test
  @UiThreadTest
  public void testCircleSortKeyAsConstant() {
    Timber.i("circle-sort-key");
    assertNotNull(layer);
    assertNull(layer.getCircleSortKey().getValue());

    // Set and Get
    Float propertyValue = 0.3f;
    layer.setProperties(PropertyFactory.circleSortKey(propertyValue));
    assertEquals(layer.getCircleSortKey().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testCircleSortKeyAsExpression() {
    Timber.i("circle-sort-key-expression");
    assertNotNull(layer);
    assertNull(layer.getCircleSortKey().getExpression());

    // Set and Get
    Expression expression = Expression.number(Expression.get("undefined"));
    layer.setProperties(PropertyFactory.circleSortKey(expression));
    assertEquals(layer.getCircleSortKey().getExpression(), expression);
  }

  @Test
  @UiThreadTest
  public void testCircleRadiusTransition() {
    Timber.i("circle-radiusTransitionOptions");
    assertNotNull(layer);

    // Set and Get
    TransitionOptions options = new TransitionOptions(300, 100);
    layer.setCircleRadiusTransition(options);
    assertEquals(layer.getCircleRadiusTransition(), options);
  }

  @Test
  @UiThreadTest
  public void testCircleRadiusAsConstant() {
    Timber.i("circle-radius");
    assertNotNull(layer);
    assertNull(layer.getCircleRadius().getValue());

    // Set and Get
    Float propertyValue = 0.3f;
    layer.setProperties(PropertyFactory.circleRadius(propertyValue));
    assertEquals(layer.getCircleRadius().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testCircleRadiusAsExpression() {
    Timber.i("circle-radius-expression");
    assertNotNull(layer);
    assertNull(layer.getCircleRadius().getExpression());

    // Set and Get
    Expression expression = Expression.number(Expression.get("undefined"));
    layer.setProperties(PropertyFactory.circleRadius(expression));
    assertEquals(layer.getCircleRadius().getExpression(), expression);
  }

  @Test
  @UiThreadTest
  public void testCircleColorTransition() {
    Timber.i("circle-colorTransitionOptions");
    assertNotNull(layer);

    // Set and Get
    TransitionOptions options = new TransitionOptions(300, 100);
    layer.setCircleColorTransition(options);
    assertEquals(layer.getCircleColorTransition(), options);
  }

  @Test
  @UiThreadTest
  public void testCircleColorAsConstant() {
    Timber.i("circle-color");
    assertNotNull(layer);
    assertNull(layer.getCircleColor().getValue());

    // Set and Get
    String propertyValue = "rgba(255,128,0,0.7)";
    layer.setProperties(PropertyFactory.circleColor(propertyValue));
    assertEquals(layer.getCircleColor().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testCircleColorAsExpression() {
    Timber.i("circle-color-expression");
    assertNotNull(layer);
    assertNull(layer.getCircleColor().getExpression());

    // Set and Get
    Expression expression = Expression.toColor(Expression.get("undefined"));
    layer.setProperties(PropertyFactory.circleColor(expression));
    assertEquals(layer.getCircleColor().getExpression(), expression);
  }

  @Test
  @UiThreadTest
  public void testCircleColorAsIntConstant() {
    Timber.i("circle-color");
    assertNotNull(layer);

    // Set and Get
    layer.setProperties(PropertyFactory.circleColor(Color.argb(127, 255, 127, 0)));
    assertEquals(layer.getCircleColorAsInt(), Color.argb(127, 255, 127, 0));
  }

  @Test
  @UiThreadTest
  public void testCircleBlurTransition() {
    Timber.i("circle-blurTransitionOptions");
    assertNotNull(layer);

    // Set and Get
    TransitionOptions options = new TransitionOptions(300, 100);
    layer.setCircleBlurTransition(options);
    assertEquals(layer.getCircleBlurTransition(), options);
  }

  @Test
  @UiThreadTest
  public void testCircleBlurAsConstant() {
    Timber.i("circle-blur");
    assertNotNull(layer);
    assertNull(layer.getCircleBlur().getValue());

    // Set and Get
    Float propertyValue = 0.3f;
    layer.setProperties(PropertyFactory.circleBlur(propertyValue));
    assertEquals(layer.getCircleBlur().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testCircleBlurAsExpression() {
    Timber.i("circle-blur-expression");
    assertNotNull(layer);
    assertNull(layer.getCircleBlur().getExpression());

    // Set and Get
    Expression expression = Expression.number(Expression.get("undefined"));
    layer.setProperties(PropertyFactory.circleBlur(expression));
    assertEquals(layer.getCircleBlur().getExpression(), expression);
  }

  @Test
  @UiThreadTest
  public void testCircleOpacityTransition() {
    Timber.i("circle-opacityTransitionOptions");
    assertNotNull(layer);

    // Set and Get
    TransitionOptions options = new TransitionOptions(300, 100);
    layer.setCircleOpacityTransition(options);
    assertEquals(layer.getCircleOpacityTransition(), options);
  }

  @Test
  @UiThreadTest
  public void testCircleOpacityAsConstant() {
    Timber.i("circle-opacity");
    assertNotNull(layer);
    assertNull(layer.getCircleOpacity().getValue());

    // Set and Get
    Float propertyValue = 0.3f;
    layer.setProperties(PropertyFactory.circleOpacity(propertyValue));
    assertEquals(layer.getCircleOpacity().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testCircleOpacityAsExpression() {
    Timber.i("circle-opacity-expression");
    assertNotNull(layer);
    assertNull(layer.getCircleOpacity().getExpression());

    // Set and Get
    Expression expression = Expression.number(Expression.get("undefined"));
    layer.setProperties(PropertyFactory.circleOpacity(expression));
    assertEquals(layer.getCircleOpacity().getExpression(), expression);
  }

  @Test
  @UiThreadTest
  public void testCircleTranslateTransition() {
    Timber.i("circle-translateTransitionOptions");
    assertNotNull(layer);

    // Set and Get
    TransitionOptions options = new TransitionOptions(300, 100);
    layer.setCircleTranslateTransition(options);
    assertEquals(layer.getCircleTranslateTransition(), options);
  }

  @Test
  @UiThreadTest
  public void testCircleTranslateAsConstant() {
    Timber.i("circle-translate");
    assertNotNull(layer);
    assertNull(layer.getCircleTranslate().getValue());

    // Set and Get
    Float[] propertyValue = new Float[] {0f, 0f};
    layer.setProperties(PropertyFactory.circleTranslate(propertyValue));
    assertEquals(layer.getCircleTranslate().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testCircleTranslateAnchorAsConstant() {
    Timber.i("circle-translate-anchor");
    assertNotNull(layer);
    assertNull(layer.getCircleTranslateAnchor().getValue());

    // Set and Get
    String propertyValue = Property.CIRCLE_TRANSLATE_ANCHOR_MAP;
    layer.setProperties(PropertyFactory.circleTranslateAnchor(propertyValue));
    assertEquals(layer.getCircleTranslateAnchor().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testCirclePitchScaleAsConstant() {
    Timber.i("circle-pitch-scale");
    assertNotNull(layer);
    assertNull(layer.getCirclePitchScale().getValue());

    // Set and Get
    String propertyValue = Property.CIRCLE_PITCH_SCALE_MAP;
    layer.setProperties(PropertyFactory.circlePitchScale(propertyValue));
    assertEquals(layer.getCirclePitchScale().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testCirclePitchAlignmentAsConstant() {
    Timber.i("circle-pitch-alignment");
    assertNotNull(layer);
    assertNull(layer.getCirclePitchAlignment().getValue());

    // Set and Get
    String propertyValue = Property.CIRCLE_PITCH_ALIGNMENT_MAP;
    layer.setProperties(PropertyFactory.circlePitchAlignment(propertyValue));
    assertEquals(layer.getCirclePitchAlignment().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testCircleStrokeWidthTransition() {
    Timber.i("circle-stroke-widthTransitionOptions");
    assertNotNull(layer);

    // Set and Get
    TransitionOptions options = new TransitionOptions(300, 100);
    layer.setCircleStrokeWidthTransition(options);
    assertEquals(layer.getCircleStrokeWidthTransition(), options);
  }

  @Test
  @UiThreadTest
  public void testCircleStrokeWidthAsConstant() {
    Timber.i("circle-stroke-width");
    assertNotNull(layer);
    assertNull(layer.getCircleStrokeWidth().getValue());

    // Set and Get
    Float propertyValue = 0.3f;
    layer.setProperties(PropertyFactory.circleStrokeWidth(propertyValue));
    assertEquals(layer.getCircleStrokeWidth().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testCircleStrokeWidthAsExpression() {
    Timber.i("circle-stroke-width-expression");
    assertNotNull(layer);
    assertNull(layer.getCircleStrokeWidth().getExpression());

    // Set and Get
    Expression expression = Expression.number(Expression.get("undefined"));
    layer.setProperties(PropertyFactory.circleStrokeWidth(expression));
    assertEquals(layer.getCircleStrokeWidth().getExpression(), expression);
  }

  @Test
  @UiThreadTest
  public void testCircleStrokeColorTransition() {
    Timber.i("circle-stroke-colorTransitionOptions");
    assertNotNull(layer);

    // Set and Get
    TransitionOptions options = new TransitionOptions(300, 100);
    layer.setCircleStrokeColorTransition(options);
    assertEquals(layer.getCircleStrokeColorTransition(), options);
  }

  @Test
  @UiThreadTest
  public void testCircleStrokeColorAsConstant() {
    Timber.i("circle-stroke-color");
    assertNotNull(layer);
    assertNull(layer.getCircleStrokeColor().getValue());

    // Set and Get
    String propertyValue = "rgba(255,128,0,0.7)";
    layer.setProperties(PropertyFactory.circleStrokeColor(propertyValue));
    assertEquals(layer.getCircleStrokeColor().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testCircleStrokeColorAsExpression() {
    Timber.i("circle-stroke-color-expression");
    assertNotNull(layer);
    assertNull(layer.getCircleStrokeColor().getExpression());

    // Set and Get
    Expression expression = Expression.toColor(Expression.get("undefined"));
    layer.setProperties(PropertyFactory.circleStrokeColor(expression));
    assertEquals(layer.getCircleStrokeColor().getExpression(), expression);
  }

  @Test
  @UiThreadTest
  public void testCircleStrokeColorAsIntConstant() {
    Timber.i("circle-stroke-color");
    assertNotNull(layer);

    // Set and Get
    layer.setProperties(PropertyFactory.circleStrokeColor(Color.argb(127, 255, 127, 0)));
    assertEquals(layer.getCircleStrokeColorAsInt(), Color.argb(127, 255, 127, 0));
  }

  @Test
  @UiThreadTest
  public void testCircleStrokeOpacityTransition() {
    Timber.i("circle-stroke-opacityTransitionOptions");
    assertNotNull(layer);

    // Set and Get
    TransitionOptions options = new TransitionOptions(300, 100);
    layer.setCircleStrokeOpacityTransition(options);
    assertEquals(layer.getCircleStrokeOpacityTransition(), options);
  }

  @Test
  @UiThreadTest
  public void testCircleStrokeOpacityAsConstant() {
    Timber.i("circle-stroke-opacity");
    assertNotNull(layer);
    assertNull(layer.getCircleStrokeOpacity().getValue());

    // Set and Get
    Float propertyValue = 0.3f;
    layer.setProperties(PropertyFactory.circleStrokeOpacity(propertyValue));
    assertEquals(layer.getCircleStrokeOpacity().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testCircleStrokeOpacityAsExpression() {
    Timber.i("circle-stroke-opacity-expression");
    assertNotNull(layer);
    assertNull(layer.getCircleStrokeOpacity().getExpression());

    // Set and Get
    Expression expression = Expression.number(Expression.get("undefined"));
    layer.setProperties(PropertyFactory.circleStrokeOpacity(expression));
    assertEquals(layer.getCircleStrokeOpacity().getExpression(), expression);
  }
}
