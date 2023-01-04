package vn.vietmap.vietmapsdk.testapp.style;

import android.graphics.Color;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.mapbox.geojson.Feature;
import com.mapbox.geojson.Point;
import vn.vietmap.vietmapsdk.geometry.LatLng;
import vn.vietmap.vietmapsdk.style.expressions.Expression;
import vn.vietmap.vietmapsdk.style.layers.CircleLayer;
import vn.vietmap.vietmapsdk.style.layers.FillLayer;
import vn.vietmap.vietmapsdk.style.layers.Layer;
import vn.vietmap.vietmapsdk.style.layers.SymbolLayer;
import vn.vietmap.vietmapsdk.style.sources.GeoJsonSource;
import vn.vietmap.vietmapsdk.style.sources.Source;
import vn.vietmap.vietmapsdk.style.types.Formatted;
import vn.vietmap.vietmapsdk.style.types.FormattedSection;
import vn.vietmap.vietmapsdk.testapp.R;
import vn.vietmap.vietmapsdk.testapp.activity.EspressoTest;
import com.mapbox.vietmapsdk.testapp.utils.ResourceUtils;
import com.mapbox.vietmapsdk.testapp.utils.TestingAsyncUtils;
import vn.vietmap.vietmapsdk.utils.ColorUtils;
import vn.vietmap.vietmapsdk.geometry.LatLng;
import vn.vietmap.vietmapsdk.style.expressions.Expression;
import vn.vietmap.vietmapsdk.style.layers.CircleLayer;
import vn.vietmap.vietmapsdk.style.layers.FillLayer;
import vn.vietmap.vietmapsdk.style.layers.Layer;
import vn.vietmap.vietmapsdk.style.layers.PropertyFactory;
import vn.vietmap.vietmapsdk.style.layers.SymbolLayer;
import vn.vietmap.vietmapsdk.style.sources.GeoJsonSource;
import vn.vietmap.vietmapsdk.style.sources.Source;
import vn.vietmap.vietmapsdk.style.types.Formatted;
import vn.vietmap.vietmapsdk.style.types.FormattedSection;
import vn.vietmap.vietmapsdk.testapp.action.MapboxMapAction;
import vn.vietmap.vietmapsdk.testapp.activity.EspressoTest;
import com.vietmap.vietmapsdk.testapp.utils.TestingAsyncUtils;
import vn.vietmap.vietmapsdk.utils.ColorUtils;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.HashMap;

import timber.log.Timber;
import vn.vietmap.vietmapsdk.geometry.LatLng;
import vn.vietmap.vietmapsdk.style.expressions.Expression;
import vn.vietmap.vietmapsdk.style.layers.CircleLayer;
import vn.vietmap.vietmapsdk.style.layers.FillLayer;
import vn.vietmap.vietmapsdk.style.layers.Layer;
import vn.vietmap.vietmapsdk.style.layers.PropertyFactory;
import vn.vietmap.vietmapsdk.style.layers.SymbolLayer;
import vn.vietmap.vietmapsdk.style.sources.GeoJsonSource;
import vn.vietmap.vietmapsdk.style.sources.Source;
import vn.vietmap.vietmapsdk.style.types.Formatted;
import vn.vietmap.vietmapsdk.style.types.FormattedSection;
import vn.vietmap.vietmapsdk.testapp.action.MapboxMapAction;
import vn.vietmap.vietmapsdk.testapp.activity.EspressoTest;
import vn.vietmap.vietmapsdk.testapp.utils.TestingAsyncUtils;
import vn.vietmap.vietmapsdk.utils.ColorUtils;

import static vn.vietmap.vietmapsdk.style.expressions.Expression.FormatOption.formatFontScale;
import static vn.vietmap.vietmapsdk.style.expressions.Expression.FormatOption.formatTextColor;
import static vn.vietmap.vietmapsdk.style.expressions.Expression.FormatOption.formatTextFont;
import static vn.vietmap.vietmapsdk.style.expressions.Expression.NumberFormatOption.currency;
import static vn.vietmap.vietmapsdk.style.expressions.Expression.NumberFormatOption.locale;
import static vn.vietmap.vietmapsdk.style.expressions.Expression.NumberFormatOption.maxFractionDigits;
import static vn.vietmap.vietmapsdk.style.expressions.Expression.NumberFormatOption.minFractionDigits;
import static vn.vietmap.vietmapsdk.style.expressions.Expression.collator;
import static vn.vietmap.vietmapsdk.style.expressions.Expression.color;
import static vn.vietmap.vietmapsdk.style.expressions.Expression.eq;
import static vn.vietmap.vietmapsdk.style.expressions.Expression.exponential;
import static vn.vietmap.vietmapsdk.style.expressions.Expression.format;
import static vn.vietmap.vietmapsdk.style.expressions.Expression.formatEntry;
import static vn.vietmap.vietmapsdk.style.expressions.Expression.get;
import static vn.vietmap.vietmapsdk.style.expressions.Expression.interpolate;
import static vn.vietmap.vietmapsdk.style.expressions.Expression.literal;
import static vn.vietmap.vietmapsdk.style.expressions.Expression.match;
import static vn.vietmap.vietmapsdk.style.expressions.Expression.number;
import static vn.vietmap.vietmapsdk.style.expressions.Expression.numberFormat;
import static vn.vietmap.vietmapsdk.style.expressions.Expression.rgb;
import static vn.vietmap.vietmapsdk.style.expressions.Expression.rgba;
import static vn.vietmap.vietmapsdk.style.expressions.Expression.step;
import static vn.vietmap.vietmapsdk.style.expressions.Expression.stop;
import static vn.vietmap.vietmapsdk.style.expressions.Expression.string;
import static vn.vietmap.vietmapsdk.style.expressions.Expression.switchCase;
import static vn.vietmap.vietmapsdk.style.expressions.Expression.toColor;
import static vn.vietmap.vietmapsdk.style.expressions.Expression.zoom;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.circleColor;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.fillAntialias;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.fillColor;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.fillOutlineColor;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.textColor;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.textField;
import static vn.vietmap.vietmapsdk.testapp.action.MapboxMapAction.invoke;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(AndroidJUnit4.class)
public class ExpressionTest extends EspressoTest {

  private FillLayer layer;

  @Test
  public void testConstantExpressionConversion() {
    validateTestSetup();
    setupStyle();
    Timber.i("camera function");

    MapboxMapAction.invoke(mapboxMap, (uiController, mapboxMap) -> {
      // create color expression
      Expression inputExpression = Expression.rgba(255.0f, 0.0f, 0.0f, 1.0f);

      // set color expression
      layer.setProperties(
        PropertyFactory.fillColor(inputExpression)
      );

      // get color value
      int color = layer.getFillColor().getColorInt();

      // compare
      assertEquals("input expression should match", Color.RED, color);
    });
  }

  @Test
  public void testGetExpressionWrapping() {
    validateTestSetup();
    setupStyle();
    Timber.i("camera function");

    MapboxMapAction.invoke(mapboxMap, (uiController, mapboxMap) -> {
      // create get expression
      Expression inputExpression = Expression.get("fill");

      // set get expression
      layer.setProperties(
        PropertyFactory.fillColor(inputExpression)
      );

      // get actual expression
      Expression actualExpression = layer.getFillColor().getExpression();

      // create wrapped expected expression
      Expression expectedExpression = Expression.toColor(Expression.get("fill"));

      // compare
      assertEquals("input expression should match", expectedExpression, actualExpression);
    });
  }

  @Test
  public void testCameraFunction() {
    validateTestSetup();
    setupStyle();
    Timber.i("camera function");

    MapboxMapAction.invoke(mapboxMap, (uiController, mapboxMap) -> {
      // create camera function expression
      Expression inputExpression = Expression.interpolate(
        Expression.exponential(0.5f), Expression.zoom(),
        Expression.stop(1.0f, Expression.rgba(255.0f, 0.0f, 0.0f, 1.0f)),
        Expression.stop(5.0f, Expression.rgba(0.0f, 0.0f, 255.0f, 1.0f)),
        Expression.stop(10.0f, Expression.rgba(0.0f, 255.0f, 0.0f, 1.0f))
      );

      // set camera function expression
      layer.setProperties(
        PropertyFactory.fillColor(inputExpression)
      );

      // get camera function expression
      Expression outputExpression = layer.getFillColor().getExpression();

      // compare
      assertEquals("input expression should match", inputExpression, outputExpression);
    });
  }

  @Test
  public void testSourceFunction() {
    validateTestSetup();
    setupStyle();
    Timber.i("camera function");

    MapboxMapAction.invoke(mapboxMap, (uiController, mapboxMap) -> {
      // create camera function expression
      Expression inputExpression = Expression.toColor(Expression.get("fill"));

      // set camera function expression
      layer.setProperties(
        PropertyFactory.fillColor(inputExpression)
      );

      // get camera function expression
      Expression outputExpression = layer.getFillColor().getExpression();

      // compare
      assertEquals("input expression should match", inputExpression, outputExpression);
    });
  }

  @Test
  public void testCompositeFunction() {
    validateTestSetup();
    setupStyle();
    Timber.i("camera function");

    MapboxMapAction.invoke(mapboxMap, (uiController, mapboxMap) -> {
      // create camera function expression
      Expression inputExpression = Expression.step(Expression.zoom(),
        Expression.rgba(255.0f, 255.0f, 255.0f, 1.0f),
        Expression.stop(7.0f, Expression.match(
          Expression.string(Expression.get("name")),
          Expression.literal("Westerpark"), Expression.rgba(255.0f, 0.0f, 0.0f, 1.0f),
          Expression.rgba(255.0f, 255.0f, 255.0f, 1.0f)
        )),
        Expression.stop(8.0f, Expression.match(
          Expression.string(Expression.get("name")),
          Expression.literal("Westerpark"), Expression.rgba(0.0f, 0.0f, 255.0f, 1.0f),
          Expression.rgba(255.0f, 255.0f, 255.0f, 1.0f)
        ))
      );

      // set camera function expression
      layer.setProperties(
        PropertyFactory.fillColor(inputExpression)
      );

      // get camera function expression
      Expression outputExpression = layer.getFillColor().getExpression();

      // compare
      assertEquals("input expression should match", inputExpression, outputExpression);
    });
  }

  @Test
  public void testLiteralProperty() {
    validateTestSetup();
    setupStyle();
    MapboxMapAction.invoke(mapboxMap, (uiController, mapboxMap) -> {
      layer.setProperties(
        PropertyFactory.fillColor(Expression.literal("#4286f4"))
      );
    });
  }

  @Test
  public void testLiteralMatchExpression() {
    validateTestSetup();
    setupStyle();
    MapboxMapAction.invoke(mapboxMap, (uiController, mapboxMap) -> {
      Expression expression = Expression.match(Expression.literal("something"), Expression.literal(0f),
        Expression.stop("1", Expression.get("1")),
        Expression.stop("2", Expression.get("2")),
        Expression.stop("3", Expression.get("3")),
        Expression.stop("4", Expression.get("4"))
      );

      layer.setProperties(
        PropertyFactory.fillColor(expression)
      );
      expression.toArray();
    });
  }

  @Test
  public void testCollatorExpression() {
    validateTestSetup();
    MapboxMapAction.invoke(mapboxMap, (uiController, mapboxMap) -> {
      LatLng latLng = new LatLng(51, 17);

      Expression expression1 = Expression.eq(Expression.literal("Łukasz"), Expression.literal("lukasz"), Expression.collator(true, true));
      Expression expression2 = Expression.eq(Expression.literal("Łukasz"), Expression.literal("lukasz"), Expression.collator(Expression.literal(false), Expression.eq(Expression.literal(1),
        Expression.literal(1)), Expression.literal("en")));
      Expression expression3 = Expression.eq(Expression.literal("Łukasz"), Expression.literal("lukasz"), Expression.collator(Expression.literal(false), Expression.eq(Expression.literal(2),
        Expression.literal(1))));

      mapboxMap.getStyle()
        .addSource(new GeoJsonSource("source", Point.fromLngLat(latLng.getLongitude(), latLng.getLatitude())));
      Layer layer = new CircleLayer("layer", "source")
        .withProperties(PropertyFactory.circleColor(
          Expression.switchCase(
            expression1, Expression.literal(ColorUtils.colorToRgbaString(Color.GREEN)),
            Expression.literal(ColorUtils.colorToRgbaString(Color.RED))
          )
        ));
      mapboxMap.getStyle().addLayer(layer);
      TestingAsyncUtils.INSTANCE.waitForLayer(uiController, mapView);
      assertFalse(mapboxMap.queryRenderedFeatures(mapboxMap.getProjection().toScreenLocation(latLng), "layer")
        .isEmpty());

      layer.setProperties(PropertyFactory.circleColor(
        Expression.switchCase(
          expression2, Expression.literal(ColorUtils.colorToRgbaString(Color.GREEN)),
          Expression.literal(ColorUtils.colorToRgbaString(Color.RED))
        )
      ));
      TestingAsyncUtils.INSTANCE.waitForLayer(uiController, mapView);
      assertFalse(mapboxMap.queryRenderedFeatures(mapboxMap.getProjection().toScreenLocation(latLng), "layer")
        .isEmpty());

      layer.setProperties(PropertyFactory.circleColor(
        Expression.switchCase(
          expression3, Expression.literal(ColorUtils.colorToRgbaString(Color.GREEN)),
          Expression.literal(ColorUtils.colorToRgbaString(Color.RED))
        )
      ));
      TestingAsyncUtils.INSTANCE.waitForLayer(uiController, mapView);
      assertFalse(mapboxMap.queryRenderedFeatures(mapboxMap.getProjection().toScreenLocation(latLng), "layer")
        .isEmpty());
    });
  }

  @Test
  public void testConstFormatExpression() {
    validateTestSetup();
    MapboxMapAction.invoke(mapboxMap, (uiController, mapboxMap) -> {
      LatLng latLng = new LatLng(51, 17);
      mapboxMap.getStyle()
        .addSource(new GeoJsonSource("source", Point.fromLngLat(latLng.getLongitude(), latLng.getLatitude())));
      SymbolLayer layer = new SymbolLayer("layer", "source");
      mapboxMap.getStyle().addLayer(layer);

      Expression expression = Expression.format(
        Expression.formatEntry("test")
      );
      layer.setProperties(PropertyFactory.textField(expression));
      TestingAsyncUtils.INSTANCE.waitForLayer(uiController, mapView);

      assertFalse(mapboxMap.queryRenderedFeatures(mapboxMap.getProjection().toScreenLocation(latLng), "layer")
        .isEmpty());
      assertNull(layer.getTextField().getExpression());
      Assert.assertEquals(new Formatted(new FormattedSection("test")), layer.getTextField().getValue());
    });
  }

  @Test
  public void testConstFormatExpressionFontScaleParam() {
    validateTestSetup();
    MapboxMapAction.invoke(mapboxMap, (uiController, mapboxMap) -> {
      LatLng latLng = new LatLng(51, 17);
      mapboxMap.getStyle()
        .addSource(new GeoJsonSource("source", Point.fromLngLat(latLng.getLongitude(), latLng.getLatitude())));
      SymbolLayer layer = new SymbolLayer("layer", "source");
      mapboxMap.getStyle().addLayer(layer);

      Expression expression = Expression.format(
        Expression.formatEntry("test", Expression.FormatOption.formatFontScale(1.75))
      );
      layer.setProperties(PropertyFactory.textField(expression));
      TestingAsyncUtils.INSTANCE.waitForLayer(uiController, mapView);

      assertFalse(mapboxMap.queryRenderedFeatures(mapboxMap.getProjection().toScreenLocation(latLng), "layer")
        .isEmpty());
      assertNull(layer.getTextField().getExpression());
      Assert.assertEquals(new Formatted(new FormattedSection("test", 1.75)), layer.getTextField().getValue());
    });
  }

  @Test
  public void testConstFormatExpressionTextFontParam() {
    validateTestSetup();
    MapboxMapAction.invoke(mapboxMap, (uiController, mapboxMap) -> {
      LatLng latLng = new LatLng(51, 17);
      mapboxMap.getStyle()
        .addSource(new GeoJsonSource("source", Point.fromLngLat(latLng.getLongitude(), latLng.getLatitude())));
      SymbolLayer layer = new SymbolLayer("layer", "source");
      mapboxMap.getStyle().addLayer(layer);

      Expression expression = Expression.format(
        Expression.formatEntry(
          Expression.literal("test"),
          Expression.FormatOption.formatTextFont(new String[] {"DIN Offc Pro Regular", "Arial Unicode MS Regular"})
        )
      );
      layer.setProperties(PropertyFactory.textField(expression));
      TestingAsyncUtils.INSTANCE.waitForLayer(uiController, mapView);

      assertFalse(
        mapboxMap.queryRenderedFeatures(mapboxMap.getProjection().toScreenLocation(latLng), "layer").isEmpty()
      );
      assertNull(layer.getTextField().getExpression());
      Assert.assertEquals(new Formatted(
        new FormattedSection("test",
          new String[] {"DIN Offc Pro Regular", "Arial Unicode MS Regular"})
      ), layer.getTextField().getValue());
    });
  }

  @Test
  public void testConstFormatExpressionTextColorParam() {
    validateTestSetup();
    MapboxMapAction.invoke(mapboxMap, (uiController, mapboxMap) -> {
      LatLng latLng = new LatLng(51, 17);
      mapboxMap.getStyle()
        .addSource(new GeoJsonSource("source", Point.fromLngLat(latLng.getLongitude(), latLng.getLatitude())));
      SymbolLayer layer = new SymbolLayer("layer", "source");
      mapboxMap.getStyle().addLayer(layer);

      Expression expression = Expression.format(
        Expression.formatEntry(
          Expression.literal("test"),
          Expression.FormatOption.formatTextColor(Expression.literal("yellow"))
        )
      );
      layer.setProperties(PropertyFactory.textField(expression));
      TestingAsyncUtils.INSTANCE.waitForLayer(uiController, mapView);

      assertFalse(
        mapboxMap.queryRenderedFeatures(mapboxMap.getProjection().toScreenLocation(latLng), "layer").isEmpty()
      );
      assertNull(layer.getTextField().getExpression());
      Assert.assertEquals(new Formatted(
        new FormattedSection("test", null, null, "rgba(255,255,0,1)")
      ), layer.getTextField().getValue());
    });
  }

  @Test
  public void testConstFormatExpressionAllParams() {
    validateTestSetup();
    MapboxMapAction.invoke(mapboxMap, (uiController, mapboxMap) -> {
      LatLng latLng = new LatLng(51, 17);
      mapboxMap.getStyle()
        .addSource(new GeoJsonSource("source", Point.fromLngLat(latLng.getLongitude(), latLng.getLatitude())));
      SymbolLayer layer = new SymbolLayer("layer", "source");
      mapboxMap.getStyle().addLayer(layer);

      Expression expression = Expression.format(
        Expression.formatEntry(
          "test",
          Expression.FormatOption.formatFontScale(0.5),
          Expression.FormatOption.formatTextFont(new String[] {"DIN Offc Pro Regular", "Arial Unicode MS Regular"}),
          Expression.FormatOption.formatTextColor(Expression.rgb(126, 0, 0))
        )
      );
      layer.setProperties(PropertyFactory.textField(expression));
      TestingAsyncUtils.INSTANCE.waitForLayer(uiController, mapView);

      assertFalse(
        mapboxMap.queryRenderedFeatures(mapboxMap.getProjection().toScreenLocation(latLng), "layer").isEmpty()
      );
      assertNull(layer.getTextField().getExpression());
      Assert.assertEquals(new Formatted(
        new FormattedSection("test",
          0.5,
          new String[] {"DIN Offc Pro Regular", "Arial Unicode MS Regular"},
          "rgba(126,0,0,1)")
      ), layer.getTextField().getValue());
    });
  }

  @Test
  public void testConstFormatExpressionMultipleInputs() {
    validateTestSetup();
    MapboxMapAction.invoke(mapboxMap, (uiController, mapboxMap) -> {
      LatLng latLng = new LatLng(51, 17);
      mapboxMap.getStyle()
        .addSource(new GeoJsonSource("source", Point.fromLngLat(latLng.getLongitude(), latLng.getLatitude())));
      SymbolLayer layer = new SymbolLayer("layer", "source");
      mapboxMap.getStyle().addLayer(layer);

      Expression expression = Expression.format(
        Expression.formatEntry(
          "test",
          Expression.FormatOption.formatFontScale(1.5),
          Expression.FormatOption.formatTextFont(new String[] {"DIN Offc Pro Regular", "Arial Unicode MS Regular"})
        ),
        Expression.formatEntry("\ntest2", Expression.FormatOption.formatFontScale(2), Expression.FormatOption.formatTextColor(Color.BLUE)),
        Expression.formatEntry("\ntest3", Expression.FormatOption.formatFontScale(2.5), Expression.FormatOption.formatTextColor(Expression.toColor(Expression.literal("rgba(0, 128, 255, 0.5)"))))
      );
      layer.setProperties(PropertyFactory.textField(expression));
      TestingAsyncUtils.INSTANCE.waitForLayer(uiController, mapView);

      assertFalse(
        mapboxMap.queryRenderedFeatures(mapboxMap.getProjection().toScreenLocation(latLng), "layer").isEmpty()
      );
      assertNull(layer.getTextField().getExpression());
      Assert.assertEquals(new Formatted(
        new FormattedSection("test", 1.5,
          new String[] {"DIN Offc Pro Regular", "Arial Unicode MS Regular"}),
        new FormattedSection("\ntest2", 2.0, null, "rgba(0,0,255,1)"),
        new FormattedSection("\ntest3", 2.5, null, "rgba(0,128,255,0.5)")
      ), layer.getTextField().getValue());
    });
  }

  @Test
  public void testVariableFormatExpression() {
    validateTestSetup();
    MapboxMapAction.invoke(mapboxMap, (uiController, mapboxMap) -> {
      LatLng latLng = new LatLng(51, 17);
      Feature feature = Feature.fromGeometry(Point.fromLngLat(latLng.getLongitude(), latLng.getLatitude()));
      feature.addStringProperty("test_property", "test");
      feature.addNumberProperty("test_property_number", 1.5);
      feature.addStringProperty("test_property_color", "green");
      mapboxMap.getStyle().addSource(new GeoJsonSource("source", feature));
      SymbolLayer layer = new SymbolLayer("layer", "source");
      mapboxMap.getStyle().addLayer(layer);

      Expression expression = Expression.format(
        Expression.formatEntry(
          Expression.get("test_property"),
          Expression.FormatOption.formatFontScale(Expression.number(Expression.get("test_property_number"))),
          Expression.FormatOption.formatTextFont(new String[] {"Arial Unicode MS Regular", "DIN Offc Pro Regular"}),
          Expression.FormatOption.formatTextColor(Expression.toColor(Expression.get("test_property_color")))
        )
      );
      layer.setProperties(PropertyFactory.textField(expression));
      TestingAsyncUtils.INSTANCE.waitForLayer(uiController, mapView);

      assertFalse(mapboxMap.queryRenderedFeatures(mapboxMap.getProjection().toScreenLocation(latLng), "layer")
        .isEmpty());
      Assert.assertEquals(expression, layer.getTextField().getExpression());
      assertNull(layer.getTextField().getValue());
    });
  }

  @Test
  public void testVariableFormatExpressionMultipleInputs() {
    validateTestSetup();
    MapboxMapAction.invoke(mapboxMap, (uiController, mapboxMap) -> {
      LatLng latLng = new LatLng(51, 17);
      Feature feature = Feature.fromGeometry(Point.fromLngLat(latLng.getLongitude(), latLng.getLatitude()));
      feature.addStringProperty("test_property", "test");
      feature.addNumberProperty("test_property_number", 1.5);
      feature.addStringProperty("test_property_color", "rgba(0, 255, 0, 1)");
      mapboxMap.getStyle().addSource(new GeoJsonSource("source", feature));
      SymbolLayer layer = new SymbolLayer("layer", "source");
      mapboxMap.getStyle().addLayer(layer);

      Expression expression = Expression.format(
        Expression.formatEntry(
          Expression.get("test_property"),
          Expression.FormatOption.formatFontScale(1.25),
          Expression.FormatOption.formatTextFont(new String[] {"Arial Unicode MS Regular", "DIN Offc Pro Regular"}),
          Expression.FormatOption.formatTextColor(Expression.toColor(Expression.get("test_property_color")))
        ),
        Expression.formatEntry("\ntest2", Expression.FormatOption.formatFontScale(2))
      );
      layer.setProperties(PropertyFactory.textField(expression), PropertyFactory.textColor("rgba(128, 0, 0, 1)"));
      TestingAsyncUtils.INSTANCE.waitForLayer(uiController, mapView);

      assertFalse(mapboxMap.queryRenderedFeatures(mapboxMap.getProjection().toScreenLocation(latLng), "layer")
        .isEmpty());
      Assert.assertEquals(expression, layer.getTextField().getExpression());
      assertNull(layer.getTextField().getValue());
    });
  }

  @Test
  public void testFormatExpressionPlainTextCoercion() {
    validateTestSetup();
    MapboxMapAction.invoke(mapboxMap, (uiController, mapboxMap) -> {
      LatLng latLng = new LatLng(51, 17);
      mapboxMap.getStyle()
        .addSource(new GeoJsonSource("source", Point.fromLngLat(latLng.getLongitude(), latLng.getLatitude())));
      SymbolLayer layer = new SymbolLayer("layer", "source");
      mapboxMap.getStyle().addLayer(layer);

      layer.setProperties(PropertyFactory.textField("test"));
      TestingAsyncUtils.INSTANCE.waitForLayer(uiController, mapView);

      assertFalse(mapboxMap.queryRenderedFeatures(mapboxMap.getProjection().toScreenLocation(latLng), "layer")
        .isEmpty());
      assertNull(layer.getTextField().getExpression());
      Assert.assertEquals(new Formatted(
        new FormattedSection("test")), layer.getTextField().getValue());
    });
  }

  @Test
  public void testTextFieldFormattedArgument() {
    validateTestSetup();
    MapboxMapAction.invoke(mapboxMap, (uiController, mapboxMap) -> {
      LatLng latLng = new LatLng(51, 17);
      mapboxMap.getStyle()
        .addSource(new GeoJsonSource("source", Point.fromLngLat(latLng.getLongitude(), latLng.getLatitude())));
      SymbolLayer layer = new SymbolLayer("layer", "source");
      mapboxMap.getStyle().addLayer(layer);

      Formatted formatted = new Formatted(
        new FormattedSection("test", 1.5),
        new FormattedSection("\ntest", 0.5, new String[] {"Arial Unicode MS Regular", "DIN Offc Pro Regular"}),
        new FormattedSection("test", null, null, "rgba(0,255,0,1)")
      );
      layer.setProperties(PropertyFactory.textField(formatted), PropertyFactory.textColor("rgba(128,0,0,1)"));
      TestingAsyncUtils.INSTANCE.waitForLayer(uiController, mapView);

      assertFalse(mapboxMap.queryRenderedFeatures(mapboxMap.getProjection().toScreenLocation(latLng), "layer")
        .isEmpty());
      assertNull(layer.getTextField().getExpression());
      Assert.assertEquals(formatted, layer.getTextField().getValue());
    });
  }

  @Test
  public void testNumberFormatCurrencyExpression() {
    validateTestSetup();
    MapboxMapAction.invoke(mapboxMap, (uiController, mapboxMap) -> {
      LatLng latLng = new LatLng(51, 17);
      mapboxMap.getStyle()
        .addSource(new GeoJsonSource("source", Point.fromLngLat(latLng.getLongitude(), latLng.getLatitude())));
      SymbolLayer layer = new SymbolLayer("layer", "source");
      mapboxMap.getStyle().addLayer(layer);

      layer.setProperties(
        PropertyFactory.textField(
          Expression.numberFormat(12.345, Expression.NumberFormatOption.locale("en-US"), Expression.NumberFormatOption.currency("USD"))
        )
      );
      TestingAsyncUtils.INSTANCE.waitForLayer(uiController, mapView);

      assertFalse(mapboxMap.queryRenderedFeatures(
        mapboxMap.getProjection().toScreenLocation(latLng), "layer").isEmpty()
      );
      assertNull(layer.getTextField().getExpression());
      Assert.assertEquals("$12.35", layer.getTextField().getValue().getFormattedSections()[0].getText());
    });
  }

  @Test
  public void testNumberFormatMaxExpression() {
    validateTestSetup();
    MapboxMapAction.invoke(mapboxMap, (uiController, mapboxMap) -> {
      LatLng latLng = new LatLng(51, 17);
      mapboxMap.getStyle()
        .addSource(new GeoJsonSource("source", Point.fromLngLat(latLng.getLongitude(), latLng.getLatitude())));
      SymbolLayer layer = new SymbolLayer("layer", "source");
      mapboxMap.getStyle().addLayer(layer);

      layer.setProperties(
        PropertyFactory.textField(
          Expression.numberFormat(12.34567890, Expression.NumberFormatOption.maxFractionDigits(5), Expression.NumberFormatOption.minFractionDigits(0))
        )
      );
      TestingAsyncUtils.INSTANCE.waitForLayer(uiController, mapView);

      assertFalse(mapboxMap.queryRenderedFeatures(
        mapboxMap.getProjection().toScreenLocation(latLng), "layer").isEmpty()
      );
      assertNull(layer.getTextField().getExpression());
      Assert.assertEquals("12.34568", layer.getTextField().getValue().getFormattedSections()[0].getText());
    });
  }

  @Test
  public void testNumberFormatMinExpression() {
    validateTestSetup();
    MapboxMapAction.invoke(mapboxMap, (uiController, mapboxMap) -> {
      LatLng latLng = new LatLng(51, 17);
      mapboxMap.getStyle()
        .addSource(new GeoJsonSource("source", Point.fromLngLat(latLng.getLongitude(), latLng.getLatitude())));
      SymbolLayer layer = new SymbolLayer("layer", "source");
      mapboxMap.getStyle().addLayer(layer);

      layer.setProperties(
        PropertyFactory.textField(
          Expression.numberFormat(12.0000001, Expression.NumberFormatOption.maxFractionDigits(5), Expression.NumberFormatOption.minFractionDigits(0))
        )
      );
      TestingAsyncUtils.INSTANCE.waitForLayer(uiController, mapView);

      assertFalse(mapboxMap.queryRenderedFeatures(
        mapboxMap.getProjection().toScreenLocation(latLng), "layer").isEmpty()
      );
      assertNull(layer.getTextField().getExpression());
      Assert.assertEquals("12", layer.getTextField().getValue().getFormattedSections()[0].getText());
    });
  }

  @Test
  public void testNumberFormatLocaleExpression() {
    validateTestSetup();
    MapboxMapAction.invoke(mapboxMap, (uiController, mapboxMap) -> {
      LatLng latLng = new LatLng(51, 17);
      mapboxMap.getStyle()
        .addSource(new GeoJsonSource("source", Point.fromLngLat(latLng.getLongitude(), latLng.getLatitude())));
      SymbolLayer layer = new SymbolLayer("layer", "source");
      mapboxMap.getStyle().addLayer(layer);

      layer.setProperties(
        PropertyFactory.textField(
          Expression.numberFormat(12.0000001, Expression.NumberFormatOption.locale("nl-BE"), Expression.NumberFormatOption.maxFractionDigits(5), Expression.NumberFormatOption.minFractionDigits(1))
        )
      );
      TestingAsyncUtils.INSTANCE.waitForLayer(uiController, mapView);

      assertFalse(mapboxMap.queryRenderedFeatures(
        mapboxMap.getProjection().toScreenLocation(latLng), "layer").isEmpty()
      );
      assertNull(layer.getTextField().getExpression());
      Assert.assertEquals("12,0", layer.getTextField().getValue().getFormattedSections()[0].getText());
    });
  }

  @Test
  public void testNumberFormatNonConstantExpression() {
    validateTestSetup();
    MapboxMapAction.invoke(mapboxMap, (uiController, mapboxMap) -> {
      LatLng latLng = new LatLng(51, 17);
      Feature feature = Feature.fromGeometry(Point.fromLngLat(latLng.getLongitude(), latLng.getLatitude()));
      feature.addNumberProperty("number_value", 12.345678);
      feature.addStringProperty("locale_value", "nl-BE");
      feature.addNumberProperty("max_value", 5);
      feature.addNumberProperty("min_value", 1);


      mapboxMap.getStyle().addSource(new GeoJsonSource("source", feature));
      SymbolLayer layer = new SymbolLayer("layer", "source");
      mapboxMap.getStyle().addLayer(layer);

      Expression numberFormatExpression = Expression.numberFormat(
        Expression.number(Expression.number(Expression.get("number_value"))),
        Expression.NumberFormatOption.locale(Expression.string(Expression.get("locale_value"))),
        Expression.NumberFormatOption.maxFractionDigits(Expression.number(Expression.get("max_value"))),
        Expression.NumberFormatOption.minFractionDigits(Expression.number(Expression.get("min_value")))
      );

      layer.setProperties(PropertyFactory.textField(numberFormatExpression));
      TestingAsyncUtils.INSTANCE.waitForLayer(uiController, mapView);

      assertFalse(mapboxMap.queryRenderedFeatures(
        mapboxMap.getProjection().toScreenLocation(latLng), "layer").isEmpty()
      );

      assertNotNull(layer.getTextField().getExpression());

      // Expressions evaluated to string are wrapped by a format expression, take array index 1 to get original
      Object[] returnExpression = (Object[]) layer.getTextField().getExpression().toArray()[1];
      Object[] setExpression = numberFormatExpression.toArray();
      assertEquals("Number format should match",returnExpression[0], setExpression[0]);
      assertArrayEquals("Get value expression should match",
        (Object[]) returnExpression[1],
        (Object[]) setExpression[1]
      );

      // number format objects
      HashMap<String, Object> returnMap = (HashMap<String, Object>) returnExpression[2];
      HashMap<String, Object> setMap = (HashMap<String, Object>) returnExpression[2];

      assertArrayEquals("Number format min fraction digits should match ",
        (Object[]) returnMap.get("min-fraction-digits"),
        (Object[]) setMap.get("min-fraction-digits")
      );

      assertArrayEquals("Number format max fraction digits should match ",
        (Object[]) returnMap.get("max-fraction-digits"),
        (Object[]) setMap.get("max-fraction-digits")
      );

      assertArrayEquals("Number format min fraction digits should match ",
        (Object[]) returnMap.get("locale"),
        (Object[]) setMap.get("locale")
      );
    });

  }

  /**
   * Regression test for #15532
   */
  @Test
  public void testDoubleConversion() {
    validateTestSetup();
    MapboxMapAction.invoke(mapboxMap, (uiController, mapboxMap) -> {
      LatLng latLng = new LatLng(51, 17);
      mapboxMap.getStyle().addSource(
        new GeoJsonSource("source", Point.fromLngLat(latLng.getLongitude(), latLng.getLatitude()))
      );

      CircleLayer layer = new CircleLayer("layer", "source");
      mapboxMap.getStyle().addLayer(layer);

      Expression input =  Expression.interpolate(
        Expression.exponential(0.5f), Expression.zoom(),
        Expression.stop(-0.1, Expression.color(Color.RED)),
        Expression.stop(0, Expression.color(Color.BLUE))
      );

      layer.setProperties(PropertyFactory.circleColor(input));

      Expression output = layer.getCircleColor().getExpression();
      assertArrayEquals("Expression should match", input.toArray(), output.toArray());
    });
  }

  private void setupStyle() {
    MapboxMapAction.invoke(mapboxMap, (uiController, mapboxMap) -> {
      // Add a source
      Source source;
      try {
        source = new GeoJsonSource("amsterdam-parks-source",
          ResourceUtils.readRawResource(rule.getActivity(), R.raw.amsterdam));
        mapboxMap.getStyle().addSource(source);
      } catch (IOException ioException) {
        return;
      }

      // Add a fill layer
      mapboxMap.getStyle().addLayer(layer = new FillLayer("amsterdam-parks-layer", source.getId())
        .withProperties(
          PropertyFactory.fillColor(Expression.rgba(0.0f, 0.0f, 0.0f, 0.5f)),
          PropertyFactory.fillOutlineColor(Expression.rgb(0, 0, 255)),
          PropertyFactory.fillAntialias(true)
        )
      );
    });
  }
}
