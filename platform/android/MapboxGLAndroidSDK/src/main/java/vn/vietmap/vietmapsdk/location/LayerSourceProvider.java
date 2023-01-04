package vn.vietmap.vietmapsdk.location;

import androidx.annotation.NonNull;

import com.mapbox.geojson.Feature;
import vn.vietmap.vietmapsdk.style.layers.CircleLayer;
import vn.vietmap.vietmapsdk.style.layers.Layer;
import vn.vietmap.vietmapsdk.style.layers.Property;
import vn.vietmap.vietmapsdk.style.layers.SymbolLayer;
import vn.vietmap.vietmapsdk.style.layers.TransitionOptions;
import vn.vietmap.vietmapsdk.style.sources.GeoJsonOptions;
import vn.vietmap.vietmapsdk.style.sources.GeoJsonSource;

import java.util.HashSet;
import java.util.Set;

import static vn.vietmap.vietmapsdk.location.LocationComponentConstants.ACCURACY_LAYER;
import static vn.vietmap.vietmapsdk.location.LocationComponentConstants.BACKGROUND_LAYER;
import static vn.vietmap.vietmapsdk.location.LocationComponentConstants.BEARING_LAYER;
import static vn.vietmap.vietmapsdk.location.LocationComponentConstants.FOREGROUND_LAYER;
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
import static vn.vietmap.vietmapsdk.location.LocationComponentConstants.PROPERTY_SHADOW_ICON_OFFSET;
import static vn.vietmap.vietmapsdk.location.LocationComponentConstants.PULSING_CIRCLE_LAYER;
import static vn.vietmap.vietmapsdk.location.LocationComponentConstants.SHADOW_ICON;
import static vn.vietmap.vietmapsdk.location.LocationComponentConstants.SHADOW_LAYER;
import static vn.vietmap.vietmapsdk.style.expressions.Expression.get;
import static vn.vietmap.vietmapsdk.style.expressions.Expression.literal;
import static vn.vietmap.vietmapsdk.style.expressions.Expression.match;
import static vn.vietmap.vietmapsdk.style.expressions.Expression.stop;
import static vn.vietmap.vietmapsdk.style.expressions.Expression.switchCase;
import static vn.vietmap.vietmapsdk.style.layers.Property.ICON_ROTATION_ALIGNMENT_MAP;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.circleColor;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.circleOpacity;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.circlePitchAlignment;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.circleRadius;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.circleStrokeColor;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.iconImage;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.iconOffset;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.iconRotate;
import static vn.vietmap.vietmapsdk.style.layers.PropertyFactory.iconRotationAlignment;

class LayerSourceProvider {

  private static final String EMPTY_STRING = "";

  @NonNull
  GeoJsonSource generateSource(Feature locationFeature) {
    return new GeoJsonSource(
      LocationComponentConstants.LOCATION_SOURCE,
      locationFeature,
      new GeoJsonOptions().withMaxZoom(16)
    );
  }

  @NonNull
  Layer generateLayer(@NonNull String layerId) {
    SymbolLayer layer = new SymbolLayer(layerId, LocationComponentConstants.LOCATION_SOURCE);
    layer.setProperties(
      iconAllowOverlap(true),
      iconIgnorePlacement(true),
      iconRotationAlignment(ICON_ROTATION_ALIGNMENT_MAP),
      iconRotate(
        match(literal(layerId), literal(0f),
          stop(LocationComponentConstants.FOREGROUND_LAYER, get(LocationComponentConstants.PROPERTY_GPS_BEARING)),
          stop(LocationComponentConstants.BACKGROUND_LAYER, get(LocationComponentConstants.PROPERTY_GPS_BEARING)),
          stop(LocationComponentConstants.SHADOW_LAYER, get(LocationComponentConstants.PROPERTY_GPS_BEARING)),
          stop(LocationComponentConstants.BEARING_LAYER, get(LocationComponentConstants.PROPERTY_COMPASS_BEARING))
        )
      ),
      iconImage(
        match(literal(layerId), literal(EMPTY_STRING),
          stop(LocationComponentConstants.FOREGROUND_LAYER, switchCase(
            get(LocationComponentConstants.PROPERTY_LOCATION_STALE), get(LocationComponentConstants.PROPERTY_FOREGROUND_STALE_ICON),
            get(LocationComponentConstants.PROPERTY_FOREGROUND_ICON))),
          stop(LocationComponentConstants.BACKGROUND_LAYER, switchCase(
            get(LocationComponentConstants.PROPERTY_LOCATION_STALE), get(LocationComponentConstants.PROPERTY_BACKGROUND_STALE_ICON),
            get(LocationComponentConstants.PROPERTY_BACKGROUND_ICON))),
          stop(LocationComponentConstants.SHADOW_LAYER, literal(LocationComponentConstants.SHADOW_ICON)),
          stop(LocationComponentConstants.BEARING_LAYER, get(LocationComponentConstants.PROPERTY_BEARING_ICON))
        )
      ),
      iconOffset(
        match(literal(layerId), literal(new Float[] {0f, 0f}),
          stop(literal(LocationComponentConstants.FOREGROUND_LAYER), get(LocationComponentConstants.PROPERTY_FOREGROUND_ICON_OFFSET)),
          stop(literal(LocationComponentConstants.SHADOW_LAYER), get(LocationComponentConstants.PROPERTY_SHADOW_ICON_OFFSET))
        )
      )
    );
    return layer;
  }

  @NonNull
  Layer generateAccuracyLayer() {
    return new CircleLayer(LocationComponentConstants.ACCURACY_LAYER, LocationComponentConstants.LOCATION_SOURCE)
      .withProperties(
        circleRadius(get(LocationComponentConstants.PROPERTY_ACCURACY_RADIUS)),
        circleColor(get(LocationComponentConstants.PROPERTY_ACCURACY_COLOR)),
        circleOpacity(get(LocationComponentConstants.PROPERTY_ACCURACY_ALPHA)),
        circleStrokeColor(get(LocationComponentConstants.PROPERTY_ACCURACY_COLOR)),
        circlePitchAlignment(Property.CIRCLE_PITCH_ALIGNMENT_MAP)
      );
  }

  Set<String> getEmptyLayerSet() {
    return new HashSet<>();
  }

  LocationLayerRenderer getSymbolLocationLayerRenderer(LayerFeatureProvider featureProvider,
                                                       boolean isStale) {
    return new SymbolLocationLayerRenderer(this, featureProvider, isStale);
  }

  LocationLayerRenderer getIndicatorLocationLayerRenderer() {
    return new IndicatorLocationLayerRenderer(this);
  }

  Layer generateLocationComponentLayer() {
    LocationIndicatorLayer layer = new LocationIndicatorLayer(LocationComponentConstants.FOREGROUND_LAYER);
    layer.setLocationTransition(new TransitionOptions(0, 0));
    layer.setProperties(
        LocationPropertyFactory.perspectiveCompensation(0.9f),
        LocationPropertyFactory.imageTiltDisplacement(4f)
    );
    return layer;
  }

  /**
   * Adds a {@link CircleLayer} to the map to support the {@link LocationComponent} pulsing UI functionality.
   *
   * @return a {@link CircleLayer} with the correct data-driven styling. Tilting the map will keep the pulsing
   * layer aligned with the map plane.
   */
  @NonNull
  Layer generatePulsingCircleLayer() {
    return new CircleLayer(LocationComponentConstants.PULSING_CIRCLE_LAYER, LocationComponentConstants.LOCATION_SOURCE)
        .withProperties(
            circlePitchAlignment(Property.CIRCLE_PITCH_ALIGNMENT_MAP)
        );
  }
}
