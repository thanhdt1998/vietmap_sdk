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
import vn.vietmap.vietmapsdk.style.layers.FillLayer;
import vn.vietmap.vietmapsdk.style.layers.TransitionOptions;
import vn.vietmap.vietmapsdk.style.expressions.Expression;
import vn.vietmap.vietmapsdk.style.layers.FillLayer;
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
import vn.vietmap.vietmapsdk.style.layers.FillLayer;
import vn.vietmap.vietmapsdk.style.layers.Property;
import vn.vietmap.vietmapsdk.style.layers.PropertyFactory;
import vn.vietmap.vietmapsdk.style.layers.TransitionOptions;

import static vn.vietmap.vietmapsdk.style.expressions.Expression.distance;
import static vn.vietmap.vietmapsdk.style.expressions.Expression.eq;
import static vn.vietmap.vietmapsdk.style.expressions.Expression.get;
import static vn.vietmap.vietmapsdk.style.expressions.Expression.image;
import static vn.vietmap.vietmapsdk.style.expressions.Expression.literal;
import static vn.vietmap.vietmapsdk.style.expressions.Expression.lt;
import static vn.vietmap.vietmapsdk.style.expressions.Expression.number;
import static vn.vietmap.vietmapsdk.style.expressions.Expression.string;
import static vn.vietmap.vietmapsdk.style.expressions.Expression.toColor;
import static vn.vietmap.vietmapsdk.style.expressions.Expression.within;
import static vn.vietmap.vietmapsdk.style.layers.Property.FILL_TRANSLATE_ANCHOR_MAP;
import static vn.vietmap.vietmapsdk.style.layers.Property.NONE;
import static vn.vietmap.vietmapsdk.style.layers.Property.VISIBLE;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.fillAntialias;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.fillColor;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.fillOpacity;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.fillOutlineColor;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.fillPattern;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.fillSortKey;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.fillTranslate;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.fillTranslateAnchor;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.visibility;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Basic smoke tests for FillLayer
 */
@RunWith(AndroidJUnit4.class)
public class FillLayerTest extends BaseLayerTest {

  private FillLayer layer;
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
    layer = new FillLayer("my-layer", "composite");
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
  public void testFillSortKeyAsConstant() {
    Timber.i("fill-sort-key");
    assertNotNull(layer);
    assertNull(layer.getFillSortKey().getValue());

    // Set and Get
    Float propertyValue = 0.3f;
    layer.setProperties(PropertyFactory.fillSortKey(propertyValue));
    assertEquals(layer.getFillSortKey().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testFillSortKeyAsExpression() {
    Timber.i("fill-sort-key-expression");
    assertNotNull(layer);
    assertNull(layer.getFillSortKey().getExpression());

    // Set and Get
    Expression expression = Expression.number(Expression.get("undefined"));
    layer.setProperties(PropertyFactory.fillSortKey(expression));
    assertEquals(layer.getFillSortKey().getExpression(), expression);
  }

  @Test
  @UiThreadTest
  public void testFillAntialiasAsConstant() {
    Timber.i("fill-antialias");
    assertNotNull(layer);
    assertNull(layer.getFillAntialias().getValue());

    // Set and Get
    Boolean propertyValue = true;
    layer.setProperties(PropertyFactory.fillAntialias(propertyValue));
    assertEquals(layer.getFillAntialias().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testFillOpacityTransition() {
    Timber.i("fill-opacityTransitionOptions");
    assertNotNull(layer);

    // Set and Get
    TransitionOptions options = new TransitionOptions(300, 100);
    layer.setFillOpacityTransition(options);
    assertEquals(layer.getFillOpacityTransition(), options);
  }

  @Test
  @UiThreadTest
  public void testFillOpacityAsConstant() {
    Timber.i("fill-opacity");
    assertNotNull(layer);
    assertNull(layer.getFillOpacity().getValue());

    // Set and Get
    Float propertyValue = 0.3f;
    layer.setProperties(PropertyFactory.fillOpacity(propertyValue));
    assertEquals(layer.getFillOpacity().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testFillOpacityAsExpression() {
    Timber.i("fill-opacity-expression");
    assertNotNull(layer);
    assertNull(layer.getFillOpacity().getExpression());

    // Set and Get
    Expression expression = Expression.number(Expression.get("undefined"));
    layer.setProperties(PropertyFactory.fillOpacity(expression));
    assertEquals(layer.getFillOpacity().getExpression(), expression);
  }

  @Test
  @UiThreadTest
  public void testFillColorTransition() {
    Timber.i("fill-colorTransitionOptions");
    assertNotNull(layer);

    // Set and Get
    TransitionOptions options = new TransitionOptions(300, 100);
    layer.setFillColorTransition(options);
    assertEquals(layer.getFillColorTransition(), options);
  }

  @Test
  @UiThreadTest
  public void testFillColorAsConstant() {
    Timber.i("fill-color");
    assertNotNull(layer);
    assertNull(layer.getFillColor().getValue());

    // Set and Get
    String propertyValue = "rgba(255,128,0,0.7)";
    layer.setProperties(PropertyFactory.fillColor(propertyValue));
    assertEquals(layer.getFillColor().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testFillColorAsExpression() {
    Timber.i("fill-color-expression");
    assertNotNull(layer);
    assertNull(layer.getFillColor().getExpression());

    // Set and Get
    Expression expression = Expression.toColor(Expression.get("undefined"));
    layer.setProperties(PropertyFactory.fillColor(expression));
    assertEquals(layer.getFillColor().getExpression(), expression);
  }

  @Test
  @UiThreadTest
  public void testFillColorAsIntConstant() {
    Timber.i("fill-color");
    assertNotNull(layer);

    // Set and Get
    layer.setProperties(PropertyFactory.fillColor(Color.argb(127, 255, 127, 0)));
    assertEquals(layer.getFillColorAsInt(), Color.argb(127, 255, 127, 0));
  }

  @Test
  @UiThreadTest
  public void testFillOutlineColorTransition() {
    Timber.i("fill-outline-colorTransitionOptions");
    assertNotNull(layer);

    // Set and Get
    TransitionOptions options = new TransitionOptions(300, 100);
    layer.setFillOutlineColorTransition(options);
    assertEquals(layer.getFillOutlineColorTransition(), options);
  }

  @Test
  @UiThreadTest
  public void testFillOutlineColorAsConstant() {
    Timber.i("fill-outline-color");
    assertNotNull(layer);
    assertNull(layer.getFillOutlineColor().getValue());

    // Set and Get
    String propertyValue = "rgba(255,128,0,0.7)";
    layer.setProperties(PropertyFactory.fillOutlineColor(propertyValue));
    assertEquals(layer.getFillOutlineColor().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testFillOutlineColorAsExpression() {
    Timber.i("fill-outline-color-expression");
    assertNotNull(layer);
    assertNull(layer.getFillOutlineColor().getExpression());

    // Set and Get
    Expression expression = Expression.toColor(Expression.get("undefined"));
    layer.setProperties(PropertyFactory.fillOutlineColor(expression));
    assertEquals(layer.getFillOutlineColor().getExpression(), expression);
  }

  @Test
  @UiThreadTest
  public void testFillOutlineColorAsIntConstant() {
    Timber.i("fill-outline-color");
    assertNotNull(layer);

    // Set and Get
    layer.setProperties(PropertyFactory.fillOutlineColor(Color.argb(127, 255, 127, 0)));
    assertEquals(layer.getFillOutlineColorAsInt(), Color.argb(127, 255, 127, 0));
  }

  @Test
  @UiThreadTest
  public void testFillTranslateTransition() {
    Timber.i("fill-translateTransitionOptions");
    assertNotNull(layer);

    // Set and Get
    TransitionOptions options = new TransitionOptions(300, 100);
    layer.setFillTranslateTransition(options);
    assertEquals(layer.getFillTranslateTransition(), options);
  }

  @Test
  @UiThreadTest
  public void testFillTranslateAsConstant() {
    Timber.i("fill-translate");
    assertNotNull(layer);
    assertNull(layer.getFillTranslate().getValue());

    // Set and Get
    Float[] propertyValue = new Float[] {0f, 0f};
    layer.setProperties(PropertyFactory.fillTranslate(propertyValue));
    assertEquals(layer.getFillTranslate().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testFillTranslateAnchorAsConstant() {
    Timber.i("fill-translate-anchor");
    assertNotNull(layer);
    assertNull(layer.getFillTranslateAnchor().getValue());

    // Set and Get
    String propertyValue = Property.FILL_TRANSLATE_ANCHOR_MAP;
    layer.setProperties(PropertyFactory.fillTranslateAnchor(propertyValue));
    assertEquals(layer.getFillTranslateAnchor().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testFillPatternTransition() {
    Timber.i("fill-patternTransitionOptions");
    assertNotNull(layer);

    // Set and Get
    TransitionOptions options = new TransitionOptions(300, 100);
    layer.setFillPatternTransition(options);
    assertEquals(layer.getFillPatternTransition(), options);
  }

  @Test
  @UiThreadTest
  public void testFillPatternAsConstant() {
    Timber.i("fill-pattern");
    assertNotNull(layer);
    assertNull(layer.getFillPattern().getValue());

    // Set and Get
    String propertyValue = "pedestrian-polygon";
    layer.setProperties(PropertyFactory.fillPattern(propertyValue));
    assertEquals(layer.getFillPattern().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testFillPatternAsExpression() {
    Timber.i("fill-pattern-expression");
    assertNotNull(layer);
    assertNull(layer.getFillPattern().getExpression());

    // Set and Get
    Expression expression = Expression.image(Expression.string(Expression.get("undefined")));
    layer.setProperties(PropertyFactory.fillPattern(expression));
    assertEquals(layer.getFillPattern().getExpression(), expression);
  }
}
