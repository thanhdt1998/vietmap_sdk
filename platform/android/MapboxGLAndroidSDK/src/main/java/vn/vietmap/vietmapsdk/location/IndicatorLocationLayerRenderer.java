package vn.vietmap.vietmapsdk.location;

import android.graphics.Bitmap;

import androidx.annotation.Nullable;

import vn.vietmap.vietmapsdk.geometry.LatLng;
import vn.vietmap.vietmapsdk.location.modes.RenderMode;
import vn.vietmap.vietmapsdk.maps.Style;
import vn.vietmap.vietmapsdk.style.expressions.Expression;
import vn.vietmap.vietmapsdk.style.layers.Layer;
import vn.vietmap.vietmapsdk.utils.BitmapUtils;
import vn.vietmap.vietmapsdk.utils.ColorUtils;
import vn.vietmap.vietmapsdk.location.modes.RenderMode;

import static vn.vietmap.vietmapsdk.location.LocationComponentConstants.BACKGROUND_ICON;
import static vn.vietmap.vietmapsdk.location.LocationComponentConstants.BACKGROUND_STALE_ICON;
import static vn.vietmap.vietmapsdk.location.LocationComponentConstants.BEARING_ICON;
import static vn.vietmap.vietmapsdk.location.LocationComponentConstants.BEARING_STALE_ICON;
import static vn.vietmap.vietmapsdk.location.LocationComponentConstants.FOREGROUND_ICON;
import static vn.vietmap.vietmapsdk.location.LocationComponentConstants.FOREGROUND_STALE_ICON;
import static vn.vietmap.vietmapsdk.location.LocationComponentConstants.SHADOW_ICON;
import static vn.vietmap.vietmapsdk.style.expressions.Expression.rgba;
import static vn.vietmap.vietmapsdk.style.layers.Property.NONE;
import static vn.vietmap.vietmapsdk.style.layers.Property.VISIBLE;

import vn.vietmap.vietmapsdk.location.modes.RenderMode;

class IndicatorLocationLayerRenderer implements LocationLayerRenderer {

  private Style style;
  private final LayerSourceProvider layerSourceProvider;
  private Layer layer;

  @Nullable
  private LatLng lastLatLng;
  private double lastBearing = 0;
  private float lastAccuracy = 0;

  IndicatorLocationLayerRenderer(LayerSourceProvider layerSourceProvider) {
    this.layerSourceProvider = layerSourceProvider;
  }

  @Override
  public void initializeComponents(Style style) {
    this.style = style;
    layer = layerSourceProvider.generateLocationComponentLayer();
    if (lastLatLng != null) {
      setLatLng(lastLatLng);
    }
    setLayerBearing(lastBearing);
    setAccuracyRadius(lastAccuracy);
  }

  @Override
  public void addLayers(LocationComponentPositionManager positionManager) {
    positionManager.addLayerToMap(layer);
  }

  @Override
  public void removeLayers() {
    style.removeLayer(layer);
  }

  @Override
  public void hide() {
    setLayerVisibility(false);
  }

  @Override
  public void cameraTiltUpdated(double tilt) {
    // ignored
  }

  @Override
  public void cameraBearingUpdated(double bearing) {
    // ignored
  }

  @Override
  public void show(@RenderMode.Mode int renderMode, boolean isStale) {
    setImages(renderMode, isStale);
    setLayerVisibility(true);
  }

  @Override
  public void styleAccuracy(float accuracyAlpha, int accuracyColor) {
    float[] colorArray = ColorUtils.colorToRgbaArray(accuracyColor);
    colorArray[3] = accuracyAlpha;
    Expression rgbaExpression = rgba(colorArray[0], colorArray[1], colorArray[2], colorArray[3]);
    layer.setProperties(
      LocationPropertyFactory.accuracyRadiusColor(rgbaExpression),
      LocationPropertyFactory.accuracyRadiusBorderColor(rgbaExpression)
    );
  }

  @Override
  public void setLatLng(LatLng latLng) {
    setLayerLocation(latLng);
  }

  @Override
  public void setGpsBearing(Float gpsBearing) {
    setLayerBearing(gpsBearing);
  }

  @Override
  public void setCompassBearing(Float compassBearing) {
    setLayerBearing(compassBearing);
  }

  @Override
  public void setAccuracyRadius(Float accuracy) {
    layer.setProperties(
      LocationPropertyFactory.accuracyRadius(accuracy)
    );
    lastAccuracy = accuracy;
  }

  @Override
  public void styleScaling(Expression scaleExpression) {
    layer.setProperties(
      LocationPropertyFactory.shadowImageSize(scaleExpression),
      LocationPropertyFactory.bearingImageSize(scaleExpression),
      LocationPropertyFactory.topImageSize(scaleExpression)
    );
  }

  @Override
  public void setLocationStale(boolean isStale, int renderMode) {
    setImages(renderMode, isStale);
  }

  @Override
  public void updateIconIds(String foregroundIconString, String foregroundStaleIconString, String backgroundIconString,
                            String backgroundStaleIconString, String bearingIconString) {
    // not supported
  }

  @Override
  public void addBitmaps(@RenderMode.Mode int renderMode, @Nullable Bitmap shadowBitmap, Bitmap backgroundBitmap,
                         Bitmap backgroundStaleBitmap, Bitmap bearingBitmap,
                         Bitmap foregroundBitmap, Bitmap foregroundStaleBitmap) {
    if (shadowBitmap != null) {
      style.addImage(LocationComponentConstants.SHADOW_ICON, shadowBitmap);
    } else {
      style.removeImage(LocationComponentConstants.SHADOW_ICON);
    }
    style.addImage(LocationComponentConstants.FOREGROUND_ICON, foregroundBitmap);
    style.addImage(LocationComponentConstants.FOREGROUND_STALE_ICON, foregroundStaleBitmap);

    if (renderMode == RenderMode.COMPASS) {
      float leftOffset = (bearingBitmap.getWidth() - backgroundBitmap.getWidth()) / 2f;
      float topOffset = (bearingBitmap.getHeight() - backgroundBitmap.getHeight()) / 2f;
      style.addImage(LocationComponentConstants.BEARING_ICON, BitmapUtils.mergeBitmap(bearingBitmap, backgroundBitmap, leftOffset, topOffset));

      float staleLeftOffset = (bearingBitmap.getWidth() - backgroundStaleBitmap.getWidth()) / 2f;
      float staleTopOffset = (bearingBitmap.getHeight() - backgroundStaleBitmap.getHeight()) / 2f;
      style.addImage(LocationComponentConstants.BEARING_STALE_ICON,
        BitmapUtils.mergeBitmap(bearingBitmap, backgroundStaleBitmap, staleLeftOffset, staleTopOffset));
    } else {
      style.addImage(LocationComponentConstants.BACKGROUND_ICON, backgroundBitmap);
      style.addImage(LocationComponentConstants.BACKGROUND_STALE_ICON, backgroundStaleBitmap);
      style.addImage(LocationComponentConstants.BEARING_ICON, bearingBitmap);
    }
  }

  private void setLayerVisibility(boolean visible) {
    layer.setProperties(LocationPropertyFactory.visibility(visible ? VISIBLE : NONE));
  }

  private void setLayerLocation(LatLng latLng) {
    Double[] values = new Double[] {latLng.getLatitude(), latLng.getLongitude(), 0d};
    layer.setProperties(
      LocationPropertyFactory.location(values)
    );
    lastLatLng = latLng;
  }

  private void setLayerBearing(double bearing) {
    layer.setProperties(
      LocationPropertyFactory.bearing(bearing)
    );
    lastBearing = bearing;
  }

  /**
   * Adjust the visibility of the pulsing LocationComponent circle.
   */
  @Override
  public void adjustPulsingCircleLayerVisibility(boolean visible) {
    // not supported at this time
  }

  /**
   * Adjust the the pulsing LocationComponent circle based on the set options.
   */
  @Override
  public void stylePulsingCircle(LocationComponentOptions options) {
    // not supported at this time
  }

  /**
   * Adjust the visual appearance of the pulsing LocationComponent circle.
   */
  @Override
  public void updatePulsingUi(float radius, @Nullable Float opacity) {
    // not supported at this time
  }

  private void setImages(@RenderMode.Mode int renderMode, boolean isStale) {
    String topImage = "";
    String bearingImage = "";
    String shadowImage = "";

    switch (renderMode) {
      case RenderMode.COMPASS:
        topImage = isStale ? LocationComponentConstants.FOREGROUND_STALE_ICON : LocationComponentConstants.FOREGROUND_ICON;
        bearingImage = isStale ? LocationComponentConstants.BEARING_STALE_ICON : LocationComponentConstants.BEARING_ICON;
        shadowImage = LocationComponentConstants.SHADOW_ICON;
        break;
      case RenderMode.GPS:
        topImage = "";
        bearingImage = isStale ? LocationComponentConstants.FOREGROUND_STALE_ICON : LocationComponentConstants.FOREGROUND_ICON;
        shadowImage = isStale ? LocationComponentConstants.BACKGROUND_STALE_ICON : LocationComponentConstants.BACKGROUND_ICON;
        setAccuracyRadius(0f);
        break;
      case RenderMode.NORMAL:
        topImage = isStale ? LocationComponentConstants.FOREGROUND_STALE_ICON : LocationComponentConstants.FOREGROUND_ICON;
        bearingImage = isStale ? LocationComponentConstants.BACKGROUND_STALE_ICON : LocationComponentConstants.BACKGROUND_ICON;
        shadowImage = LocationComponentConstants.SHADOW_ICON;
        break;
    }
    layer.setProperties(
      LocationPropertyFactory.topImage(topImage),
      LocationPropertyFactory.bearingImage(bearingImage),
      LocationPropertyFactory.shadowImage(shadowImage)
    );
  }
}
