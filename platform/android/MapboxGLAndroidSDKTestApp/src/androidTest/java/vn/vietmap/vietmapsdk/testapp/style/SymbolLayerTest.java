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
import vn.vietmap.vietmapsdk.style.layers.SymbolLayer;
import vn.vietmap.vietmapsdk.style.layers.TransitionOptions;
import vn.vietmap.vietmapsdk.style.types.Formatted;
import vn.vietmap.vietmapsdk.style.types.FormattedSection;
import vn.vietmap.vietmapsdk.style.expressions.Expression;
import vn.vietmap.vietmapsdk.style.layers.Property;
import vn.vietmap.vietmapsdk.style.layers.PropertyFactory;
import vn.vietmap.vietmapsdk.style.layers.SymbolLayer;
import vn.vietmap.vietmapsdk.style.layers.TransitionOptions;
import vn.vietmap.vietmapsdk.style.types.Formatted;
import vn.vietmap.vietmapsdk.style.types.FormattedSection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import timber.log.Timber;
import vn.vietmap.vietmapsdk.style.expressions.Expression;
import vn.vietmap.vietmapsdk.style.layers.Property;
import vn.vietmap.vietmapsdk.style.layers.PropertyFactory;
import vn.vietmap.vietmapsdk.style.layers.SymbolLayer;
import vn.vietmap.vietmapsdk.style.layers.TransitionOptions;
import vn.vietmap.vietmapsdk.style.types.Formatted;
import vn.vietmap.vietmapsdk.style.types.FormattedSection;

import static vn.vietmap.vietmapsdk.style.expressions.Expression.FormatOption;
import static vn.vietmap.vietmapsdk.style.expressions.Expression.distance;
import static vn.vietmap.vietmapsdk.style.expressions.Expression.eq;
import static vn.vietmap.vietmapsdk.style.expressions.Expression.format;
import static vn.vietmap.vietmapsdk.style.expressions.Expression.get;
import static vn.vietmap.vietmapsdk.style.expressions.Expression.image;
import static vn.vietmap.vietmapsdk.style.expressions.Expression.literal;
import static vn.vietmap.vietmapsdk.style.expressions.Expression.lt;
import static vn.vietmap.vietmapsdk.style.expressions.Expression.number;
import static vn.vietmap.vietmapsdk.style.expressions.Expression.string;
import static vn.vietmap.vietmapsdk.style.expressions.Expression.toColor;
import static vn.vietmap.vietmapsdk.style.expressions.Expression.within;
import static vn.vietmap.vietmapsdk.style.layers.Property.ICON_ANCHOR_CENTER;
import static vn.vietmap.vietmapsdk.style.layers.Property.ICON_PITCH_ALIGNMENT_MAP;
import static vn.vietmap.vietmapsdk.style.layers.Property.ICON_ROTATION_ALIGNMENT_MAP;
import static vn.vietmap.vietmapsdk.style.layers.Property.ICON_TEXT_FIT_NONE;
import static vn.vietmap.vietmapsdk.style.layers.Property.ICON_TRANSLATE_ANCHOR_MAP;
import static vn.vietmap.vietmapsdk.style.layers.Property.NONE;
import static vn.vietmap.vietmapsdk.style.layers.Property.SYMBOL_PLACEMENT_POINT;
import static vn.vietmap.vietmapsdk.style.layers.Property.SYMBOL_Z_ORDER_AUTO;
import static vn.vietmap.vietmapsdk.style.layers.Property.TEXT_ANCHOR_CENTER;
import static vn.vietmap.vietmapsdk.style.layers.Property.TEXT_JUSTIFY_AUTO;
import static vn.vietmap.vietmapsdk.style.layers.Property.TEXT_PITCH_ALIGNMENT_MAP;
import static vn.vietmap.vietmapsdk.style.layers.Property.TEXT_ROTATION_ALIGNMENT_MAP;
import static vn.vietmap.vietmapsdk.style.layers.Property.TEXT_TRANSFORM_NONE;
import static vn.vietmap.vietmapsdk.style.layers.Property.TEXT_TRANSLATE_ANCHOR_MAP;
import static vn.vietmap.vietmapsdk.style.layers.Property.VISIBLE;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.iconAnchor;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.iconColor;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.iconHaloBlur;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.iconHaloColor;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.iconHaloWidth;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.iconImage;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.iconKeepUpright;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.iconOffset;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.iconOpacity;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.iconOptional;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.iconPadding;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.iconPitchAlignment;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.iconRotate;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.iconRotationAlignment;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.iconSize;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.iconTextFit;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.iconTextFitPadding;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.iconTranslate;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.iconTranslateAnchor;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.symbolAvoidEdges;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.symbolPlacement;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.symbolSortKey;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.symbolSpacing;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.symbolZOrder;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.textAllowOverlap;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.textAnchor;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.textColor;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.textField;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.textFont;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.textHaloBlur;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.textHaloColor;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.textHaloWidth;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.textIgnorePlacement;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.textJustify;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.textKeepUpright;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.textLetterSpacing;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.textLineHeight;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.textMaxAngle;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.textMaxWidth;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.textOffset;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.textOpacity;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.textOptional;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.textPadding;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.textPitchAlignment;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.textRadialOffset;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.textRotate;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.textRotationAlignment;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.textSize;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.textTransform;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.textTranslate;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.textTranslateAnchor;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.textVariableAnchor;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.textWritingMode;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.visibility;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Basic smoke tests for SymbolLayer
 */
@RunWith(AndroidJUnit4.class)
public class SymbolLayerTest extends BaseLayerTest {

  private SymbolLayer layer;
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
    layer = new SymbolLayer("my-layer", "composite");
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
  public void testSymbolPlacementAsConstant() {
    Timber.i("symbol-placement");
    assertNotNull(layer);
    assertNull(layer.getSymbolPlacement().getValue());

    // Set and Get
    String propertyValue = Property.SYMBOL_PLACEMENT_POINT;
    layer.setProperties(PropertyFactory.symbolPlacement(propertyValue));
    assertEquals(layer.getSymbolPlacement().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testSymbolSpacingAsConstant() {
    Timber.i("symbol-spacing");
    assertNotNull(layer);
    assertNull(layer.getSymbolSpacing().getValue());

    // Set and Get
    Float propertyValue = 0.3f;
    layer.setProperties(PropertyFactory.symbolSpacing(propertyValue));
    assertEquals(layer.getSymbolSpacing().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testSymbolAvoidEdgesAsConstant() {
    Timber.i("symbol-avoid-edges");
    assertNotNull(layer);
    assertNull(layer.getSymbolAvoidEdges().getValue());

    // Set and Get
    Boolean propertyValue = true;
    layer.setProperties(PropertyFactory.symbolAvoidEdges(propertyValue));
    assertEquals(layer.getSymbolAvoidEdges().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testSymbolSortKeyAsConstant() {
    Timber.i("symbol-sort-key");
    assertNotNull(layer);
    assertNull(layer.getSymbolSortKey().getValue());

    // Set and Get
    Float propertyValue = 0.3f;
    layer.setProperties(PropertyFactory.symbolSortKey(propertyValue));
    assertEquals(layer.getSymbolSortKey().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testSymbolSortKeyAsExpression() {
    Timber.i("symbol-sort-key-expression");
    assertNotNull(layer);
    assertNull(layer.getSymbolSortKey().getExpression());

    // Set and Get
    Expression expression = Expression.number(Expression.get("undefined"));
    layer.setProperties(PropertyFactory.symbolSortKey(expression));
    assertEquals(layer.getSymbolSortKey().getExpression(), expression);
  }

  @Test
  @UiThreadTest
  public void testSymbolZOrderAsConstant() {
    Timber.i("symbol-z-order");
    assertNotNull(layer);
    assertNull(layer.getSymbolZOrder().getValue());

    // Set and Get
    String propertyValue = Property.SYMBOL_Z_ORDER_AUTO;
    layer.setProperties(PropertyFactory.symbolZOrder(propertyValue));
    assertEquals(layer.getSymbolZOrder().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testIconAllowOverlapAsConstant() {
    Timber.i("icon-allow-overlap");
    assertNotNull(layer);
    assertNull(layer.getIconAllowOverlap().getValue());

    // Set and Get
    Boolean propertyValue = true;
    layer.setProperties(PropertyFactory.iconAllowOverlap(propertyValue));
    assertEquals(layer.getIconAllowOverlap().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testIconIgnorePlacementAsConstant() {
    Timber.i("icon-ignore-placement");
    assertNotNull(layer);
    assertNull(layer.getIconIgnorePlacement().getValue());

    // Set and Get
    Boolean propertyValue = true;
    layer.setProperties(PropertyFactory.iconIgnorePlacement(propertyValue));
    assertEquals(layer.getIconIgnorePlacement().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testIconOptionalAsConstant() {
    Timber.i("icon-optional");
    assertNotNull(layer);
    assertNull(layer.getIconOptional().getValue());

    // Set and Get
    Boolean propertyValue = true;
    layer.setProperties(PropertyFactory.iconOptional(propertyValue));
    assertEquals(layer.getIconOptional().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testIconRotationAlignmentAsConstant() {
    Timber.i("icon-rotation-alignment");
    assertNotNull(layer);
    assertNull(layer.getIconRotationAlignment().getValue());

    // Set and Get
    String propertyValue = Property.ICON_ROTATION_ALIGNMENT_MAP;
    layer.setProperties(PropertyFactory.iconRotationAlignment(propertyValue));
    assertEquals(layer.getIconRotationAlignment().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testIconSizeAsConstant() {
    Timber.i("icon-size");
    assertNotNull(layer);
    assertNull(layer.getIconSize().getValue());

    // Set and Get
    Float propertyValue = 0.3f;
    layer.setProperties(PropertyFactory.iconSize(propertyValue));
    assertEquals(layer.getIconSize().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testIconSizeAsExpression() {
    Timber.i("icon-size-expression");
    assertNotNull(layer);
    assertNull(layer.getIconSize().getExpression());

    // Set and Get
    Expression expression = Expression.number(Expression.get("undefined"));
    layer.setProperties(PropertyFactory.iconSize(expression));
    assertEquals(layer.getIconSize().getExpression(), expression);
  }

  @Test
  @UiThreadTest
  public void testIconTextFitAsConstant() {
    Timber.i("icon-text-fit");
    assertNotNull(layer);
    assertNull(layer.getIconTextFit().getValue());

    // Set and Get
    String propertyValue = Property.ICON_TEXT_FIT_NONE;
    layer.setProperties(PropertyFactory.iconTextFit(propertyValue));
    assertEquals(layer.getIconTextFit().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testIconTextFitPaddingAsConstant() {
    Timber.i("icon-text-fit-padding");
    assertNotNull(layer);
    assertNull(layer.getIconTextFitPadding().getValue());

    // Set and Get
    Float[] propertyValue = new Float[] {0f, 0f, 0f, 0f};
    layer.setProperties(PropertyFactory.iconTextFitPadding(propertyValue));
    assertEquals(layer.getIconTextFitPadding().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testIconImageAsConstant() {
    Timber.i("icon-image");
    assertNotNull(layer);
    assertNull(layer.getIconImage().getValue());

    // Set and Get
    String propertyValue = "undefined";
    layer.setProperties(PropertyFactory.iconImage(propertyValue));
    assertEquals(layer.getIconImage().getValue(), propertyValue);

    layer.setProperties(PropertyFactory.iconImage("{token}"));
    Assert.assertEquals(layer.getIconImage().getExpression(), Expression.image(Expression.toString(Expression.get("token"))));
  }

  @Test
  @UiThreadTest
  public void testIconImageAsExpression() {
    Timber.i("icon-image-expression");
    assertNotNull(layer);
    assertNull(layer.getIconImage().getExpression());

    // Set and Get
    Expression expression = Expression.image(Expression.string(Expression.get("undefined")));
    layer.setProperties(PropertyFactory.iconImage(expression));
    assertEquals(layer.getIconImage().getExpression(), expression);
  }

  @Test
  @UiThreadTest
  public void testIconRotateAsConstant() {
    Timber.i("icon-rotate");
    assertNotNull(layer);
    assertNull(layer.getIconRotate().getValue());

    // Set and Get
    Float propertyValue = 0.3f;
    layer.setProperties(PropertyFactory.iconRotate(propertyValue));
    assertEquals(layer.getIconRotate().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testIconRotateAsExpression() {
    Timber.i("icon-rotate-expression");
    assertNotNull(layer);
    assertNull(layer.getIconRotate().getExpression());

    // Set and Get
    Expression expression = Expression.number(Expression.get("undefined"));
    layer.setProperties(PropertyFactory.iconRotate(expression));
    assertEquals(layer.getIconRotate().getExpression(), expression);
  }

  @Test
  @UiThreadTest
  public void testIconPaddingAsConstant() {
    Timber.i("icon-padding");
    assertNotNull(layer);
    assertNull(layer.getIconPadding().getValue());

    // Set and Get
    Float propertyValue = 0.3f;
    layer.setProperties(PropertyFactory.iconPadding(propertyValue));
    assertEquals(layer.getIconPadding().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testIconKeepUprightAsConstant() {
    Timber.i("icon-keep-upright");
    assertNotNull(layer);
    assertNull(layer.getIconKeepUpright().getValue());

    // Set and Get
    Boolean propertyValue = true;
    layer.setProperties(PropertyFactory.iconKeepUpright(propertyValue));
    assertEquals(layer.getIconKeepUpright().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testIconOffsetAsConstant() {
    Timber.i("icon-offset");
    assertNotNull(layer);
    assertNull(layer.getIconOffset().getValue());

    // Set and Get
    Float[] propertyValue = new Float[] {0f, 0f};
    layer.setProperties(PropertyFactory.iconOffset(propertyValue));
    assertEquals(layer.getIconOffset().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testIconAnchorAsConstant() {
    Timber.i("icon-anchor");
    assertNotNull(layer);
    assertNull(layer.getIconAnchor().getValue());

    // Set and Get
    String propertyValue = Property.ICON_ANCHOR_CENTER;
    layer.setProperties(PropertyFactory.iconAnchor(propertyValue));
    assertEquals(layer.getIconAnchor().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testIconAnchorAsExpression() {
    Timber.i("icon-anchor-expression");
    assertNotNull(layer);
    assertNull(layer.getIconAnchor().getExpression());

    // Set and Get
    Expression expression = Expression.string(Expression.get("undefined"));
    layer.setProperties(PropertyFactory.iconAnchor(expression));
    assertEquals(layer.getIconAnchor().getExpression(), expression);
  }

  @Test
  @UiThreadTest
  public void testIconPitchAlignmentAsConstant() {
    Timber.i("icon-pitch-alignment");
    assertNotNull(layer);
    assertNull(layer.getIconPitchAlignment().getValue());

    // Set and Get
    String propertyValue = Property.ICON_PITCH_ALIGNMENT_MAP;
    layer.setProperties(PropertyFactory.iconPitchAlignment(propertyValue));
    assertEquals(layer.getIconPitchAlignment().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testTextPitchAlignmentAsConstant() {
    Timber.i("text-pitch-alignment");
    assertNotNull(layer);
    assertNull(layer.getTextPitchAlignment().getValue());

    // Set and Get
    String propertyValue = Property.TEXT_PITCH_ALIGNMENT_MAP;
    layer.setProperties(PropertyFactory.textPitchAlignment(propertyValue));
    assertEquals(layer.getTextPitchAlignment().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testTextRotationAlignmentAsConstant() {
    Timber.i("text-rotation-alignment");
    assertNotNull(layer);
    assertNull(layer.getTextRotationAlignment().getValue());

    // Set and Get
    String propertyValue = Property.TEXT_ROTATION_ALIGNMENT_MAP;
    layer.setProperties(PropertyFactory.textRotationAlignment(propertyValue));
    assertEquals(layer.getTextRotationAlignment().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testTextFieldAsConstant() {
    Timber.i("text-field");
    assertNotNull(layer);
    assertNull(layer.getTextField().getValue());

    // Set and Get
    Formatted propertyValue = new Formatted(new FormattedSection("default"));
    layer.setProperties(PropertyFactory.textField("default"));
    Assert.assertEquals(layer.getTextField().getValue(), propertyValue);
    layer.setProperties(PropertyFactory.textField(propertyValue));
    Assert.assertEquals(layer.getTextField().getValue(), propertyValue);

    layer.setProperties(PropertyFactory.textField("{token}"));
    Assert.assertEquals(layer.getTextField().getExpression(), Expression.format(Expression.formatEntry(Expression.toString(Expression.get("token")))));
  }

  @Test
  @UiThreadTest
  public void testTextFieldAsExpression() {
    Timber.i("text-field-expression");
    assertNotNull(layer);
    assertNull(layer.getTextField().getExpression());

    // Set and Get
    Expression expression = Expression.format(Expression.formatEntry(Expression.get("undefined"), Expression.FormatOption.formatFontScale(2.0), Expression.FormatOption.formatTextFont(new String[]{"Open Sans Regular", "Arial Unicode MS Regular"})));
    layer.setProperties(PropertyFactory.textField(expression));
    assertEquals(layer.getTextField().getExpression(), expression);
  }

  @Test
  @UiThreadTest
  public void testTextFontAsConstant() {
    Timber.i("text-font");
    assertNotNull(layer);
    assertNull(layer.getTextFont().getValue());

    // Set and Get
    String[] propertyValue = new String[]{"Open Sans Regular", "Arial Unicode MS Regular"};
    layer.setProperties(PropertyFactory.textFont(propertyValue));
    assertEquals(layer.getTextFont().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testTextSizeAsConstant() {
    Timber.i("text-size");
    assertNotNull(layer);
    assertNull(layer.getTextSize().getValue());

    // Set and Get
    Float propertyValue = 0.3f;
    layer.setProperties(PropertyFactory.textSize(propertyValue));
    assertEquals(layer.getTextSize().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testTextSizeAsExpression() {
    Timber.i("text-size-expression");
    assertNotNull(layer);
    assertNull(layer.getTextSize().getExpression());

    // Set and Get
    Expression expression = Expression.number(Expression.get("undefined"));
    layer.setProperties(PropertyFactory.textSize(expression));
    assertEquals(layer.getTextSize().getExpression(), expression);
  }

  @Test
  @UiThreadTest
  public void testTextMaxWidthAsConstant() {
    Timber.i("text-max-width");
    assertNotNull(layer);
    assertNull(layer.getTextMaxWidth().getValue());

    // Set and Get
    Float propertyValue = 0.3f;
    layer.setProperties(PropertyFactory.textMaxWidth(propertyValue));
    assertEquals(layer.getTextMaxWidth().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testTextMaxWidthAsExpression() {
    Timber.i("text-max-width-expression");
    assertNotNull(layer);
    assertNull(layer.getTextMaxWidth().getExpression());

    // Set and Get
    Expression expression = Expression.number(Expression.get("undefined"));
    layer.setProperties(PropertyFactory.textMaxWidth(expression));
    assertEquals(layer.getTextMaxWidth().getExpression(), expression);
  }

  @Test
  @UiThreadTest
  public void testTextLineHeightAsConstant() {
    Timber.i("text-line-height");
    assertNotNull(layer);
    assertNull(layer.getTextLineHeight().getValue());

    // Set and Get
    Float propertyValue = 0.3f;
    layer.setProperties(PropertyFactory.textLineHeight(propertyValue));
    assertEquals(layer.getTextLineHeight().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testTextLetterSpacingAsConstant() {
    Timber.i("text-letter-spacing");
    assertNotNull(layer);
    assertNull(layer.getTextLetterSpacing().getValue());

    // Set and Get
    Float propertyValue = 0.3f;
    layer.setProperties(PropertyFactory.textLetterSpacing(propertyValue));
    assertEquals(layer.getTextLetterSpacing().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testTextLetterSpacingAsExpression() {
    Timber.i("text-letter-spacing-expression");
    assertNotNull(layer);
    assertNull(layer.getTextLetterSpacing().getExpression());

    // Set and Get
    Expression expression = Expression.number(Expression.get("undefined"));
    layer.setProperties(PropertyFactory.textLetterSpacing(expression));
    assertEquals(layer.getTextLetterSpacing().getExpression(), expression);
  }

  @Test
  @UiThreadTest
  public void testTextJustifyAsConstant() {
    Timber.i("text-justify");
    assertNotNull(layer);
    assertNull(layer.getTextJustify().getValue());

    // Set and Get
    String propertyValue = Property.TEXT_JUSTIFY_AUTO;
    layer.setProperties(PropertyFactory.textJustify(propertyValue));
    assertEquals(layer.getTextJustify().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testTextJustifyAsExpression() {
    Timber.i("text-justify-expression");
    assertNotNull(layer);
    assertNull(layer.getTextJustify().getExpression());

    // Set and Get
    Expression expression = Expression.string(Expression.get("undefined"));
    layer.setProperties(PropertyFactory.textJustify(expression));
    assertEquals(layer.getTextJustify().getExpression(), expression);
  }

  @Test
  @UiThreadTest
  public void testTextRadialOffsetAsConstant() {
    Timber.i("text-radial-offset");
    assertNotNull(layer);
    assertNull(layer.getTextRadialOffset().getValue());

    // Set and Get
    Float propertyValue = 0.3f;
    layer.setProperties(PropertyFactory.textRadialOffset(propertyValue));
    assertEquals(layer.getTextRadialOffset().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testTextVariableAnchorAsConstant() {
    Timber.i("text-variable-anchor");
    assertNotNull(layer);
    assertNull(layer.getTextVariableAnchor().getValue());

    // Set and Get
    String[] propertyValue = new String[0];
    layer.setProperties(PropertyFactory.textVariableAnchor(propertyValue));
    assertEquals(layer.getTextVariableAnchor().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testTextAnchorAsConstant() {
    Timber.i("text-anchor");
    assertNotNull(layer);
    assertNull(layer.getTextAnchor().getValue());

    // Set and Get
    String propertyValue = Property.TEXT_ANCHOR_CENTER;
    layer.setProperties(PropertyFactory.textAnchor(propertyValue));
    assertEquals(layer.getTextAnchor().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testTextAnchorAsExpression() {
    Timber.i("text-anchor-expression");
    assertNotNull(layer);
    assertNull(layer.getTextAnchor().getExpression());

    // Set and Get
    Expression expression = Expression.string(Expression.get("undefined"));
    layer.setProperties(PropertyFactory.textAnchor(expression));
    assertEquals(layer.getTextAnchor().getExpression(), expression);
  }

  @Test
  @UiThreadTest
  public void testTextMaxAngleAsConstant() {
    Timber.i("text-max-angle");
    assertNotNull(layer);
    assertNull(layer.getTextMaxAngle().getValue());

    // Set and Get
    Float propertyValue = 0.3f;
    layer.setProperties(PropertyFactory.textMaxAngle(propertyValue));
    assertEquals(layer.getTextMaxAngle().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testTextWritingModeAsConstant() {
    Timber.i("text-writing-mode");
    assertNotNull(layer);
    assertNull(layer.getTextWritingMode().getValue());

    // Set and Get
    String[] propertyValue = new String[0];
    layer.setProperties(PropertyFactory.textWritingMode(propertyValue));
    assertEquals(layer.getTextWritingMode().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testTextRotateAsConstant() {
    Timber.i("text-rotate");
    assertNotNull(layer);
    assertNull(layer.getTextRotate().getValue());

    // Set and Get
    Float propertyValue = 0.3f;
    layer.setProperties(PropertyFactory.textRotate(propertyValue));
    assertEquals(layer.getTextRotate().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testTextRotateAsExpression() {
    Timber.i("text-rotate-expression");
    assertNotNull(layer);
    assertNull(layer.getTextRotate().getExpression());

    // Set and Get
    Expression expression = Expression.number(Expression.get("undefined"));
    layer.setProperties(PropertyFactory.textRotate(expression));
    assertEquals(layer.getTextRotate().getExpression(), expression);
  }

  @Test
  @UiThreadTest
  public void testTextPaddingAsConstant() {
    Timber.i("text-padding");
    assertNotNull(layer);
    assertNull(layer.getTextPadding().getValue());

    // Set and Get
    Float propertyValue = 0.3f;
    layer.setProperties(PropertyFactory.textPadding(propertyValue));
    assertEquals(layer.getTextPadding().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testTextKeepUprightAsConstant() {
    Timber.i("text-keep-upright");
    assertNotNull(layer);
    assertNull(layer.getTextKeepUpright().getValue());

    // Set and Get
    Boolean propertyValue = true;
    layer.setProperties(PropertyFactory.textKeepUpright(propertyValue));
    assertEquals(layer.getTextKeepUpright().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testTextTransformAsConstant() {
    Timber.i("text-transform");
    assertNotNull(layer);
    assertNull(layer.getTextTransform().getValue());

    // Set and Get
    String propertyValue = Property.TEXT_TRANSFORM_NONE;
    layer.setProperties(PropertyFactory.textTransform(propertyValue));
    assertEquals(layer.getTextTransform().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testTextTransformAsExpression() {
    Timber.i("text-transform-expression");
    assertNotNull(layer);
    assertNull(layer.getTextTransform().getExpression());

    // Set and Get
    Expression expression = Expression.string(Expression.get("undefined"));
    layer.setProperties(PropertyFactory.textTransform(expression));
    assertEquals(layer.getTextTransform().getExpression(), expression);
  }

  @Test
  @UiThreadTest
  public void testTextOffsetAsConstant() {
    Timber.i("text-offset");
    assertNotNull(layer);
    assertNull(layer.getTextOffset().getValue());

    // Set and Get
    Float[] propertyValue = new Float[] {0f, 0f};
    layer.setProperties(PropertyFactory.textOffset(propertyValue));
    assertEquals(layer.getTextOffset().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testTextAllowOverlapAsConstant() {
    Timber.i("text-allow-overlap");
    assertNotNull(layer);
    assertNull(layer.getTextAllowOverlap().getValue());

    // Set and Get
    Boolean propertyValue = true;
    layer.setProperties(PropertyFactory.textAllowOverlap(propertyValue));
    assertEquals(layer.getTextAllowOverlap().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testTextIgnorePlacementAsConstant() {
    Timber.i("text-ignore-placement");
    assertNotNull(layer);
    assertNull(layer.getTextIgnorePlacement().getValue());

    // Set and Get
    Boolean propertyValue = true;
    layer.setProperties(PropertyFactory.textIgnorePlacement(propertyValue));
    assertEquals(layer.getTextIgnorePlacement().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testTextOptionalAsConstant() {
    Timber.i("text-optional");
    assertNotNull(layer);
    assertNull(layer.getTextOptional().getValue());

    // Set and Get
    Boolean propertyValue = true;
    layer.setProperties(PropertyFactory.textOptional(propertyValue));
    assertEquals(layer.getTextOptional().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testIconOpacityTransition() {
    Timber.i("icon-opacityTransitionOptions");
    assertNotNull(layer);

    // Set and Get
    TransitionOptions options = new TransitionOptions(300, 100);
    layer.setIconOpacityTransition(options);
    assertEquals(layer.getIconOpacityTransition(), options);
  }

  @Test
  @UiThreadTest
  public void testIconOpacityAsConstant() {
    Timber.i("icon-opacity");
    assertNotNull(layer);
    assertNull(layer.getIconOpacity().getValue());

    // Set and Get
    Float propertyValue = 0.3f;
    layer.setProperties(PropertyFactory.iconOpacity(propertyValue));
    assertEquals(layer.getIconOpacity().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testIconOpacityAsExpression() {
    Timber.i("icon-opacity-expression");
    assertNotNull(layer);
    assertNull(layer.getIconOpacity().getExpression());

    // Set and Get
    Expression expression = Expression.number(Expression.get("undefined"));
    layer.setProperties(PropertyFactory.iconOpacity(expression));
    assertEquals(layer.getIconOpacity().getExpression(), expression);
  }

  @Test
  @UiThreadTest
  public void testIconColorTransition() {
    Timber.i("icon-colorTransitionOptions");
    assertNotNull(layer);

    // Set and Get
    TransitionOptions options = new TransitionOptions(300, 100);
    layer.setIconColorTransition(options);
    assertEquals(layer.getIconColorTransition(), options);
  }

  @Test
  @UiThreadTest
  public void testIconColorAsConstant() {
    Timber.i("icon-color");
    assertNotNull(layer);
    assertNull(layer.getIconColor().getValue());

    // Set and Get
    String propertyValue = "rgba(255,128,0,0.7)";
    layer.setProperties(PropertyFactory.iconColor(propertyValue));
    assertEquals(layer.getIconColor().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testIconColorAsExpression() {
    Timber.i("icon-color-expression");
    assertNotNull(layer);
    assertNull(layer.getIconColor().getExpression());

    // Set and Get
    Expression expression = Expression.toColor(Expression.get("undefined"));
    layer.setProperties(PropertyFactory.iconColor(expression));
    assertEquals(layer.getIconColor().getExpression(), expression);
  }

  @Test
  @UiThreadTest
  public void testIconColorAsIntConstant() {
    Timber.i("icon-color");
    assertNotNull(layer);

    // Set and Get
    layer.setProperties(PropertyFactory.iconColor(Color.argb(127, 255, 127, 0)));
    assertEquals(layer.getIconColorAsInt(), Color.argb(127, 255, 127, 0));
  }

  @Test
  @UiThreadTest
  public void testIconHaloColorTransition() {
    Timber.i("icon-halo-colorTransitionOptions");
    assertNotNull(layer);

    // Set and Get
    TransitionOptions options = new TransitionOptions(300, 100);
    layer.setIconHaloColorTransition(options);
    assertEquals(layer.getIconHaloColorTransition(), options);
  }

  @Test
  @UiThreadTest
  public void testIconHaloColorAsConstant() {
    Timber.i("icon-halo-color");
    assertNotNull(layer);
    assertNull(layer.getIconHaloColor().getValue());

    // Set and Get
    String propertyValue = "rgba(255,128,0,0.7)";
    layer.setProperties(PropertyFactory.iconHaloColor(propertyValue));
    assertEquals(layer.getIconHaloColor().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testIconHaloColorAsExpression() {
    Timber.i("icon-halo-color-expression");
    assertNotNull(layer);
    assertNull(layer.getIconHaloColor().getExpression());

    // Set and Get
    Expression expression = Expression.toColor(Expression.get("undefined"));
    layer.setProperties(PropertyFactory.iconHaloColor(expression));
    assertEquals(layer.getIconHaloColor().getExpression(), expression);
  }

  @Test
  @UiThreadTest
  public void testIconHaloColorAsIntConstant() {
    Timber.i("icon-halo-color");
    assertNotNull(layer);

    // Set and Get
    layer.setProperties(PropertyFactory.iconHaloColor(Color.argb(127, 255, 127, 0)));
    assertEquals(layer.getIconHaloColorAsInt(), Color.argb(127, 255, 127, 0));
  }

  @Test
  @UiThreadTest
  public void testIconHaloWidthTransition() {
    Timber.i("icon-halo-widthTransitionOptions");
    assertNotNull(layer);

    // Set and Get
    TransitionOptions options = new TransitionOptions(300, 100);
    layer.setIconHaloWidthTransition(options);
    assertEquals(layer.getIconHaloWidthTransition(), options);
  }

  @Test
  @UiThreadTest
  public void testIconHaloWidthAsConstant() {
    Timber.i("icon-halo-width");
    assertNotNull(layer);
    assertNull(layer.getIconHaloWidth().getValue());

    // Set and Get
    Float propertyValue = 0.3f;
    layer.setProperties(PropertyFactory.iconHaloWidth(propertyValue));
    assertEquals(layer.getIconHaloWidth().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testIconHaloWidthAsExpression() {
    Timber.i("icon-halo-width-expression");
    assertNotNull(layer);
    assertNull(layer.getIconHaloWidth().getExpression());

    // Set and Get
    Expression expression = Expression.number(Expression.get("undefined"));
    layer.setProperties(PropertyFactory.iconHaloWidth(expression));
    assertEquals(layer.getIconHaloWidth().getExpression(), expression);
  }

  @Test
  @UiThreadTest
  public void testIconHaloBlurTransition() {
    Timber.i("icon-halo-blurTransitionOptions");
    assertNotNull(layer);

    // Set and Get
    TransitionOptions options = new TransitionOptions(300, 100);
    layer.setIconHaloBlurTransition(options);
    assertEquals(layer.getIconHaloBlurTransition(), options);
  }

  @Test
  @UiThreadTest
  public void testIconHaloBlurAsConstant() {
    Timber.i("icon-halo-blur");
    assertNotNull(layer);
    assertNull(layer.getIconHaloBlur().getValue());

    // Set and Get
    Float propertyValue = 0.3f;
    layer.setProperties(PropertyFactory.iconHaloBlur(propertyValue));
    assertEquals(layer.getIconHaloBlur().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testIconHaloBlurAsExpression() {
    Timber.i("icon-halo-blur-expression");
    assertNotNull(layer);
    assertNull(layer.getIconHaloBlur().getExpression());

    // Set and Get
    Expression expression = Expression.number(Expression.get("undefined"));
    layer.setProperties(PropertyFactory.iconHaloBlur(expression));
    assertEquals(layer.getIconHaloBlur().getExpression(), expression);
  }

  @Test
  @UiThreadTest
  public void testIconTranslateTransition() {
    Timber.i("icon-translateTransitionOptions");
    assertNotNull(layer);

    // Set and Get
    TransitionOptions options = new TransitionOptions(300, 100);
    layer.setIconTranslateTransition(options);
    assertEquals(layer.getIconTranslateTransition(), options);
  }

  @Test
  @UiThreadTest
  public void testIconTranslateAsConstant() {
    Timber.i("icon-translate");
    assertNotNull(layer);
    assertNull(layer.getIconTranslate().getValue());

    // Set and Get
    Float[] propertyValue = new Float[] {0f, 0f};
    layer.setProperties(PropertyFactory.iconTranslate(propertyValue));
    assertEquals(layer.getIconTranslate().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testIconTranslateAnchorAsConstant() {
    Timber.i("icon-translate-anchor");
    assertNotNull(layer);
    assertNull(layer.getIconTranslateAnchor().getValue());

    // Set and Get
    String propertyValue = Property.ICON_TRANSLATE_ANCHOR_MAP;
    layer.setProperties(PropertyFactory.iconTranslateAnchor(propertyValue));
    assertEquals(layer.getIconTranslateAnchor().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testTextOpacityTransition() {
    Timber.i("text-opacityTransitionOptions");
    assertNotNull(layer);

    // Set and Get
    TransitionOptions options = new TransitionOptions(300, 100);
    layer.setTextOpacityTransition(options);
    assertEquals(layer.getTextOpacityTransition(), options);
  }

  @Test
  @UiThreadTest
  public void testTextOpacityAsConstant() {
    Timber.i("text-opacity");
    assertNotNull(layer);
    assertNull(layer.getTextOpacity().getValue());

    // Set and Get
    Float propertyValue = 0.3f;
    layer.setProperties(PropertyFactory.textOpacity(propertyValue));
    assertEquals(layer.getTextOpacity().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testTextOpacityAsExpression() {
    Timber.i("text-opacity-expression");
    assertNotNull(layer);
    assertNull(layer.getTextOpacity().getExpression());

    // Set and Get
    Expression expression = Expression.number(Expression.get("undefined"));
    layer.setProperties(PropertyFactory.textOpacity(expression));
    assertEquals(layer.getTextOpacity().getExpression(), expression);
  }

  @Test
  @UiThreadTest
  public void testTextColorTransition() {
    Timber.i("text-colorTransitionOptions");
    assertNotNull(layer);

    // Set and Get
    TransitionOptions options = new TransitionOptions(300, 100);
    layer.setTextColorTransition(options);
    assertEquals(layer.getTextColorTransition(), options);
  }

  @Test
  @UiThreadTest
  public void testTextColorAsConstant() {
    Timber.i("text-color");
    assertNotNull(layer);
    assertNull(layer.getTextColor().getValue());

    // Set and Get
    String propertyValue = "rgba(255,128,0,0.7)";
    layer.setProperties(PropertyFactory.textColor(propertyValue));
    assertEquals(layer.getTextColor().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testTextColorAsExpression() {
    Timber.i("text-color-expression");
    assertNotNull(layer);
    assertNull(layer.getTextColor().getExpression());

    // Set and Get
    Expression expression = Expression.toColor(Expression.get("undefined"));
    layer.setProperties(PropertyFactory.textColor(expression));
    assertEquals(layer.getTextColor().getExpression(), expression);
  }

  @Test
  @UiThreadTest
  public void testTextColorAsIntConstant() {
    Timber.i("text-color");
    assertNotNull(layer);

    // Set and Get
    layer.setProperties(PropertyFactory.textColor(Color.argb(127, 255, 127, 0)));
    assertEquals(layer.getTextColorAsInt(), Color.argb(127, 255, 127, 0));
  }

  @Test
  @UiThreadTest
  public void testTextHaloColorTransition() {
    Timber.i("text-halo-colorTransitionOptions");
    assertNotNull(layer);

    // Set and Get
    TransitionOptions options = new TransitionOptions(300, 100);
    layer.setTextHaloColorTransition(options);
    assertEquals(layer.getTextHaloColorTransition(), options);
  }

  @Test
  @UiThreadTest
  public void testTextHaloColorAsConstant() {
    Timber.i("text-halo-color");
    assertNotNull(layer);
    assertNull(layer.getTextHaloColor().getValue());

    // Set and Get
    String propertyValue = "rgba(255,128,0,0.7)";
    layer.setProperties(PropertyFactory.textHaloColor(propertyValue));
    assertEquals(layer.getTextHaloColor().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testTextHaloColorAsExpression() {
    Timber.i("text-halo-color-expression");
    assertNotNull(layer);
    assertNull(layer.getTextHaloColor().getExpression());

    // Set and Get
    Expression expression = Expression.toColor(Expression.get("undefined"));
    layer.setProperties(PropertyFactory.textHaloColor(expression));
    assertEquals(layer.getTextHaloColor().getExpression(), expression);
  }

  @Test
  @UiThreadTest
  public void testTextHaloColorAsIntConstant() {
    Timber.i("text-halo-color");
    assertNotNull(layer);

    // Set and Get
    layer.setProperties(PropertyFactory.textHaloColor(Color.argb(127, 255, 127, 0)));
    assertEquals(layer.getTextHaloColorAsInt(), Color.argb(127, 255, 127, 0));
  }

  @Test
  @UiThreadTest
  public void testTextHaloWidthTransition() {
    Timber.i("text-halo-widthTransitionOptions");
    assertNotNull(layer);

    // Set and Get
    TransitionOptions options = new TransitionOptions(300, 100);
    layer.setTextHaloWidthTransition(options);
    assertEquals(layer.getTextHaloWidthTransition(), options);
  }

  @Test
  @UiThreadTest
  public void testTextHaloWidthAsConstant() {
    Timber.i("text-halo-width");
    assertNotNull(layer);
    assertNull(layer.getTextHaloWidth().getValue());

    // Set and Get
    Float propertyValue = 0.3f;
    layer.setProperties(PropertyFactory.textHaloWidth(propertyValue));
    assertEquals(layer.getTextHaloWidth().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testTextHaloWidthAsExpression() {
    Timber.i("text-halo-width-expression");
    assertNotNull(layer);
    assertNull(layer.getTextHaloWidth().getExpression());

    // Set and Get
    Expression expression = Expression.number(Expression.get("undefined"));
    layer.setProperties(PropertyFactory.textHaloWidth(expression));
    assertEquals(layer.getTextHaloWidth().getExpression(), expression);
  }

  @Test
  @UiThreadTest
  public void testTextHaloBlurTransition() {
    Timber.i("text-halo-blurTransitionOptions");
    assertNotNull(layer);

    // Set and Get
    TransitionOptions options = new TransitionOptions(300, 100);
    layer.setTextHaloBlurTransition(options);
    assertEquals(layer.getTextHaloBlurTransition(), options);
  }

  @Test
  @UiThreadTest
  public void testTextHaloBlurAsConstant() {
    Timber.i("text-halo-blur");
    assertNotNull(layer);
    assertNull(layer.getTextHaloBlur().getValue());

    // Set and Get
    Float propertyValue = 0.3f;
    layer.setProperties(PropertyFactory.textHaloBlur(propertyValue));
    assertEquals(layer.getTextHaloBlur().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testTextHaloBlurAsExpression() {
    Timber.i("text-halo-blur-expression");
    assertNotNull(layer);
    assertNull(layer.getTextHaloBlur().getExpression());

    // Set and Get
    Expression expression = Expression.number(Expression.get("undefined"));
    layer.setProperties(PropertyFactory.textHaloBlur(expression));
    assertEquals(layer.getTextHaloBlur().getExpression(), expression);
  }

  @Test
  @UiThreadTest
  public void testTextTranslateTransition() {
    Timber.i("text-translateTransitionOptions");
    assertNotNull(layer);

    // Set and Get
    TransitionOptions options = new TransitionOptions(300, 100);
    layer.setTextTranslateTransition(options);
    assertEquals(layer.getTextTranslateTransition(), options);
  }

  @Test
  @UiThreadTest
  public void testTextTranslateAsConstant() {
    Timber.i("text-translate");
    assertNotNull(layer);
    assertNull(layer.getTextTranslate().getValue());

    // Set and Get
    Float[] propertyValue = new Float[] {0f, 0f};
    layer.setProperties(PropertyFactory.textTranslate(propertyValue));
    assertEquals(layer.getTextTranslate().getValue(), propertyValue);
  }

  @Test
  @UiThreadTest
  public void testTextTranslateAnchorAsConstant() {
    Timber.i("text-translate-anchor");
    assertNotNull(layer);
    assertNull(layer.getTextTranslateAnchor().getValue());

    // Set and Get
    String propertyValue = Property.TEXT_TRANSLATE_ANCHOR_MAP;
    layer.setProperties(PropertyFactory.textTranslateAnchor(propertyValue));
    assertEquals(layer.getTextTranslateAnchor().getValue(), propertyValue);
  }
}
