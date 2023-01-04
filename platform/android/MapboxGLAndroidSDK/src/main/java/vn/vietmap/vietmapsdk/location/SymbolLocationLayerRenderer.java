package vn.vietmap.vietmapsdk.location;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.Point;
import vn.vietmap.vietmapsdk.geometry.LatLng;
import vn.vietmap.vietmapsdk.location.modes.RenderMode;
import vn.vietmap.vietmapsdk.maps.Style;
import vn.vietmap.vietmapsdk.style.expressions.Expression;
import vn.vietmap.vietmapsdk.style.layers.Layer;
import vn.vietmap.vietmapsdk.style.layers.SymbolLayer;
import vn.vietmap.vietmapsdk.style.sources.GeoJsonSource;
import vn.vietmap.vietmapsdk.location.modes.RenderMode;

import java.util.Set;

import static vn.vietmap.vietmapsdk.location.LocationComponentConstants.ACCURACY_LAYER;
import static vn.vietmap.vietmapsdk.location.LocationComponentConstants.BACKGROUND_ICON;
import static vn.vietmap.vietmapsdk.location.LocationComponentConstants.BACKGROUND_LAYER;
import static vn.vietmap.vietmapsdk.location.LocationComponentConstants.BACKGROUND_STALE_ICON;
import static vn.vietmap.vietmapsdk.location.LocationComponentConstants.BEARING_ICON;
import static vn.vietmap.vietmapsdk.location.LocationComponentConstants.BEARING_LAYER;
import static vn.vietmap.vietmapsdk.location.LocationComponentConstants.FOREGROUND_ICON;
import static vn.vietmap.vietmapsdk.location.LocationComponentConstants.FOREGROUND_LAYER;
import static vn.vietmap.vietmapsdk.location.LocationComponentConstants.FOREGROUND_STALE_ICON;
import static vn.vietmap.vietmapsdk.location.LocationComponentConstants.LOCATION_SOURCE;
import static vn.vietmap.vietmapsdk.location.LocationComponentConstants.PROPERTY_ACCURACY_ALPHA;
import static vn.vietmap.vietmapsdk.location.LocationComponentConstants.PROPERTY_ACCURACY_COLOR;
import static vn.vietmap.vietmapsdk.location.LocationComponentConstants.PROPERTY_ACCURACY_RADIUS;
import static vn.vietmap.vietmapsdk.location.LocationComponentConstants.PROPERTY_BACKGROUND_ICON;
import static vn.vietmap.vietmapsdk.location.LocationComponentConstants.PROPERTY_BACKGROUND_STALE_ICON;
import static vn.vietmap.vietmapsdk.location.LocationComponentConstants.PROPERTY_BEARING_ICON;
import static vn.vietmap.vietmapsdk.location.LocationComponentConstants.PROPERTY_COMPASS_BEARING;
import static vn.vietmap.vietmapsdk.location.LocationComponentConstants.PROPERTY_FOREGROUND_ICON;
import static vn.vietmap.vietmapsdk.location.LocationComponentConstants.PROPERTY_FOREGROUND_ICON_OFFSET;
import static vn.vietmap.vietmapsdk.location.LocationComponentConstants.PROPERTY_FOREGROUND_STALE_ICON;
import static vn.vietmap.vietmapsdk.location.LocationComponentConstants.PROPERTY_GPS_BEARING;
import static vn.vietmap.vietmapsdk.location.LocationComponentConstants.PROPERTY_LOCATION_STALE;
import static vn.vietmap.vietmapsdk.location.LocationComponentConstants.PROPERTY_PULSING_OPACITY;
import static vn.vietmap.vietmapsdk.location.LocationComponentConstants.PROPERTY_PULSING_RADIUS;
import static vn.vietmap.vietmapsdk.location.LocationComponentConstants.PROPERTY_SHADOW_ICON_OFFSET;
import static vn.vietmap.vietmapsdk.location.LocationComponentConstants.PULSING_CIRCLE_LAYER;
import static vn.vietmap.vietmapsdk.location.LocationComponentConstants.SHADOW_ICON;
import static vn.vietmap.vietmapsdk.location.LocationComponentConstants.SHADOW_LAYER;
import static vn.vietmap.vietmapsdk.style.expressions.Expression.get;
import static vn.vietmap.vietmapsdk.style.layers.Property.NONE;
import static vn.vietmap.vietmapsdk.style.layers.Property.VISIBLE;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.circleColor;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.circleOpacity;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.circleRadius;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.circleStrokeColor;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.iconSize;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.visibility;
import static vn.vietmap.vietmapsdk.utils.ColorUtils.colorToRgbaString;

import vn.vietmap.vietmapsdk.location.modes.RenderMode;

final class SymbolLocationLayerRenderer implements LocationLayerRenderer {
  private Style style;
  private final LayerSourceProvider layerSourceProvider;

  private final Set<String> layerSet;
  private Feature locationFeature;
  private GeoJsonSource locationSource;

  SymbolLocationLayerRenderer(LayerSourceProvider layerSourceProvider,
                              LayerFeatureProvider featureProvider,
                              boolean isStale) {
    this.layerSourceProvider = layerSourceProvider;
    this.layerSet = layerSourceProvider.getEmptyLayerSet();
    this.locationFeature = featureProvider.generateLocationFeature(locationFeature, isStale);
  }

  @Override
  public void initializeComponents(Style style) {
    this.style = style;
    addLocationSource();
  }

  @Override
  public void addLayers(LocationComponentPositionManager positionManager) {
    // positions the top-most reference layer
    Layer layer = layerSourceProvider.generateLayer(LocationComponentConstants.BEARING_LAYER);
    positionManager.addLayerToMap(layer);
    layerSet.add(layer.getId());

    // adds remaining layers while keeping the order
    addSymbolLayer(LocationComponentConstants.FOREGROUND_LAYER, LocationComponentConstants.BEARING_LAYER);
    addSymbolLayer(LocationComponentConstants.BACKGROUND_LAYER, LocationComponentConstants.FOREGROUND_LAYER);
    addSymbolLayer(LocationComponentConstants.SHADOW_LAYER, LocationComponentConstants.BACKGROUND_LAYER);
    addAccuracyLayer();
    addPulsingCircleLayerToMap();
  }

  @Override
  public void removeLayers() {
    for (String layerId : layerSet) {
      style.removeLayer(layerId);
    }
    layerSet.clear();
  }

  @Override
  public void hide() {
    for (String layerId : layerSet) {
      setLayerVisibility(layerId, false);
    }
  }

  @Override
  public void cameraTiltUpdated(double tilt) {
    updateForegroundOffset(tilt);
  }

  @Override
  public void cameraBearingUpdated(double bearing) {
    updateForegroundBearing((float) bearing);
  }

  @Override
  public void show(@RenderMode.Mode int renderMode, boolean isStale) {
    switch (renderMode) {
      case RenderMode.NORMAL:
        setLayerVisibility(LocationComponentConstants.SHADOW_LAYER, true);
        setLayerVisibility(LocationComponentConstants.FOREGROUND_LAYER, true);
        setLayerVisibility(LocationComponentConstants.BACKGROUND_LAYER, true);
        setLayerVisibility(LocationComponentConstants.ACCURACY_LAYER, !isStale);
        setLayerVisibility(LocationComponentConstants.BEARING_LAYER, false);
        break;
      case RenderMode.COMPASS:
        setLayerVisibility(LocationComponentConstants.SHADOW_LAYER, true);
        setLayerVisibility(LocationComponentConstants.FOREGROUND_LAYER, true);
        setLayerVisibility(LocationComponentConstants.BACKGROUND_LAYER, true);
        setLayerVisibility(LocationComponentConstants.ACCURACY_LAYER, !isStale);
        setLayerVisibility(LocationComponentConstants.BEARING_LAYER, true);
        break;
      case RenderMode.GPS:
        setLayerVisibility(LocationComponentConstants.SHADOW_LAYER, false);
        setLayerVisibility(LocationComponentConstants.FOREGROUND_LAYER, true);
        setLayerVisibility(LocationComponentConstants.BACKGROUND_LAYER, true);
        setLayerVisibility(LocationComponentConstants.ACCURACY_LAYER, false);
        setLayerVisibility(LocationComponentConstants.BEARING_LAYER, false);
        break;
      default:
        break;
    }
  }

  @Override
  public void styleAccuracy(float accuracyAlpha, int accuracyColor) {
    locationFeature.addNumberProperty(LocationComponentConstants.PROPERTY_ACCURACY_ALPHA, accuracyAlpha);
    locationFeature.addStringProperty(LocationComponentConstants.PROPERTY_ACCURACY_COLOR, colorToRgbaString(accuracyColor));
    refreshSource();
  }

  @Override
  public void setLatLng(LatLng latLng) {
    Point point = Point.fromLngLat(latLng.getLongitude(), latLng.getLatitude());
    setLocationPoint(point);
  }

  @Override
  public void setGpsBearing(Float gpsBearing) {
    setBearingProperty(LocationComponentConstants.PROPERTY_GPS_BEARING, gpsBearing);
  }

  @Override
  public void setCompassBearing(Float compassBearing) {
    setBearingProperty(LocationComponentConstants.PROPERTY_COMPASS_BEARING, compassBearing);
  }

  @Override
  public void setAccuracyRadius(Float accuracy) {
    updateAccuracyRadius(accuracy);
  }

  @Override
  public void styleScaling(Expression scaleExpression) {
    for (String layerId : layerSet) {
      Layer layer = style.getLayer(layerId);
      if (layer instanceof SymbolLayer) {
        layer.setProperties(
          iconSize(scaleExpression)
        );
      }
    }
  }

  @Override
  public void setLocationStale(boolean isStale, int renderMode) {
    locationFeature.addBooleanProperty(LocationComponentConstants.PROPERTY_LOCATION_STALE, isStale);
    refreshSource();
    if (renderMode != RenderMode.GPS) {
      setLayerVisibility(LocationComponentConstants.ACCURACY_LAYER, !isStale);
    }
  }

  @Override
  public void updateIconIds(String foregroundIconString, String foregroundStaleIconString, String backgroundIconString,
                            String backgroundStaleIconString, String bearingIconString) {
    locationFeature.addStringProperty(LocationComponentConstants.PROPERTY_FOREGROUND_ICON, foregroundIconString);
    locationFeature.addStringProperty(LocationComponentConstants.PROPERTY_BACKGROUND_ICON, backgroundIconString);
    locationFeature.addStringProperty(LocationComponentConstants.PROPERTY_FOREGROUND_STALE_ICON, foregroundStaleIconString);
    locationFeature.addStringProperty(LocationComponentConstants.PROPERTY_BACKGROUND_STALE_ICON, backgroundStaleIconString);
    locationFeature.addStringProperty(LocationComponentConstants.PROPERTY_BEARING_ICON, bearingIconString);
    refreshSource();
  }

  @Override
  public void addBitmaps(@RenderMode.Mode int renderMode, @Nullable Bitmap shadowBitmap, Bitmap backgroundBitmap,
                         Bitmap backgroundStaleBitmap, Bitmap bearingBitmap,
                         Bitmap foregroundBitmap, Bitmap foregroundBitmapStale) {
    if (shadowBitmap != null) {
      style.addImage(LocationComponentConstants.SHADOW_ICON, shadowBitmap);
    } else {
      style.removeImage(LocationComponentConstants.SHADOW_ICON);
    }
    style.addImage(LocationComponentConstants.BACKGROUND_ICON, backgroundBitmap);
    style.addImage(LocationComponentConstants.BACKGROUND_STALE_ICON, backgroundStaleBitmap);
    style.addImage(LocationComponentConstants.BEARING_ICON, bearingBitmap);
    style.addImage(LocationComponentConstants.FOREGROUND_ICON, foregroundBitmap);
    style.addImage(LocationComponentConstants.FOREGROUND_STALE_ICON, foregroundBitmapStale);
  }

  private void updateForegroundOffset(double tilt) {
    JsonArray foregroundJsonArray = new JsonArray();
    foregroundJsonArray.add(0f);
    foregroundJsonArray.add((float) (-0.05 * tilt));
    locationFeature.addProperty(LocationComponentConstants.PROPERTY_FOREGROUND_ICON_OFFSET, foregroundJsonArray);

    JsonArray backgroundJsonArray = new JsonArray();
    backgroundJsonArray.add(0f);
    backgroundJsonArray.add((float) (0.05 * tilt));
    locationFeature.addProperty(LocationComponentConstants.PROPERTY_SHADOW_ICON_OFFSET, backgroundJsonArray);

    refreshSource();
  }

  private void updateForegroundBearing(float bearing) {
    setBearingProperty(LocationComponentConstants.PROPERTY_GPS_BEARING, bearing);
  }

  private void setLayerVisibility(@NonNull String layerId, boolean visible) {
    Layer layer = style.getLayer(layerId);
    if (layer != null) {
      String targetVisibility = visible ? VISIBLE : NONE;
      if (!layer.getVisibility().value.equals(targetVisibility)) {
        layer.setProperties(visibility(visible ? VISIBLE : NONE));
      }
    }
  }

  /**
   * Adjust the visibility of the pulsing LocationComponent circle.
   */
  @Override
  public void adjustPulsingCircleLayerVisibility(boolean visible) {
    setLayerVisibility(LocationComponentConstants.PULSING_CIRCLE_LAYER, visible);
  }

  /**
   * Adjust the the pulsing LocationComponent circle based on the set options.
   */
  @Override
  public void stylePulsingCircle(LocationComponentOptions options) {
    if (style.getLayer(LocationComponentConstants.PULSING_CIRCLE_LAYER) != null) {
      setLayerVisibility(LocationComponentConstants.PULSING_CIRCLE_LAYER, true);
      style.getLayer(LocationComponentConstants.PULSING_CIRCLE_LAYER).setProperties(
          circleRadius(get(LocationComponentConstants.PROPERTY_PULSING_RADIUS)),
          circleColor(options.pulseColor()),
          circleStrokeColor(options.pulseColor()),
          circleOpacity(get(LocationComponentConstants.PROPERTY_PULSING_OPACITY))
      );
    }
  }

  /**
   * Adjust the visual appearance of the pulsing LocationComponent circle.
   */
  @Override
  public void updatePulsingUi(float radius, @Nullable Float opacity) {
    locationFeature.addNumberProperty(LocationComponentConstants.PROPERTY_PULSING_RADIUS, radius);
    if (opacity != null) {
      locationFeature.addNumberProperty(LocationComponentConstants.PROPERTY_PULSING_OPACITY, opacity);
    }
    refreshSource();
  }

  private void addSymbolLayer(@NonNull String layerId, @NonNull String beforeLayerId) {
    Layer layer = layerSourceProvider.generateLayer(layerId);
    addLayerToMap(layer, beforeLayerId);
  }

  private void addAccuracyLayer() {
    Layer accuracyLayer = layerSourceProvider.generateAccuracyLayer();
    addLayerToMap(accuracyLayer, LocationComponentConstants.BACKGROUND_LAYER);
  }

  /**
   * Add the pulsing LocationComponent circle to the map for future use, if need be.
   */
  private void addPulsingCircleLayerToMap() {
    Layer pulsingCircleLayer = layerSourceProvider.generatePulsingCircleLayer();
    addLayerToMap(pulsingCircleLayer, LocationComponentConstants.ACCURACY_LAYER);
  }

  private void addLayerToMap(Layer layer, @NonNull String idBelowLayer) {
    style.addLayerBelow(layer, idBelowLayer);
    layerSet.add(layer.getId());
  }

  private void addLocationSource() {
    locationSource = layerSourceProvider.generateSource(locationFeature);
    style.addSource(locationSource);
  }

  private void refreshSource() {
    GeoJsonSource source = style.getSourceAs(LocationComponentConstants.LOCATION_SOURCE);
    if (source != null) {
      locationSource.setGeoJson(locationFeature);
    }
  }

  private void setLocationPoint(Point locationPoint) {
    JsonObject properties = locationFeature.properties();
    if (properties != null) {
      locationFeature = Feature.fromGeometry(locationPoint, properties);
      refreshSource();
    }
  }

  private void setBearingProperty(@NonNull String propertyId, float bearing) {
    locationFeature.addNumberProperty(propertyId, bearing);
    refreshSource();
  }

  private void updateAccuracyRadius(float accuracy) {
    locationFeature.addNumberProperty(LocationComponentConstants.PROPERTY_ACCURACY_RADIUS, accuracy);
    refreshSource();
  }
}
