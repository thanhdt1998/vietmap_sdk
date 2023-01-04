package vn.vietmap.vietmapsdk.maps;

import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.LongSparseArray;

import vn.vietmap.vietmapsdk.Vietmap;
import com.mapbox.vietmapsdk.R;
import vn.vietmap.vietmapsdk.annotations.Annotation;
import vn.vietmap.vietmapsdk.annotations.BaseMarkerOptions;
import vn.vietmap.vietmapsdk.annotations.Marker;
import vn.vietmap.vietmapsdk.annotations.Polygon;
import vn.vietmap.vietmapsdk.annotations.PolygonOptions;
import vn.vietmap.vietmapsdk.annotations.Polyline;
import vn.vietmap.vietmapsdk.annotations.PolylineOptions;
import vn.vietmap.vietmapsdk.log.Logger;
import vn.vietmap.vietmapsdk.annotations.Annotation;
import vn.vietmap.vietmapsdk.annotations.BaseMarkerOptions;
import vn.vietmap.vietmapsdk.annotations.Marker;
import vn.vietmap.vietmapsdk.annotations.Polygon;
import vn.vietmap.vietmapsdk.annotations.PolygonOptions;
import vn.vietmap.vietmapsdk.annotations.Polyline;
import vn.vietmap.vietmapsdk.annotations.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import vn.vietmap.vietmapsdk.annotations.Annotation;
import vn.vietmap.vietmapsdk.annotations.BaseMarkerOptions;
import vn.vietmap.vietmapsdk.annotations.Marker;
import vn.vietmap.vietmapsdk.annotations.Polygon;
import vn.vietmap.vietmapsdk.annotations.PolygonOptions;
import vn.vietmap.vietmapsdk.annotations.Polyline;
import vn.vietmap.vietmapsdk.annotations.PolylineOptions;

/**
 * Responsible for managing and tracking state of Annotations linked to Map. All events related to
 * annotations that occur on {@link VietmapMap} are forwarded to this class.
 * <p>
 * Responsible for referencing {@link InfoWindowManager}.
 * </p>
 * <p>
 * Exposes convenience methods to add/remove/update all subtypes of annotations found in
 * vn.vietmap.vietmapsdk.annotations.
 * </p>
 */
class AnnotationManager {

  private static final String TAG = "Mbgl-AnnotationManager";

  private static final long NO_ANNOTATION_ID = -1;

  @NonNull
  private final MapView mapView;
  private final IconManager iconManager;
  private final InfoWindowManager infoWindowManager = new InfoWindowManager();
  private final LongSparseArray<Annotation> annotationsArray;
  private final List<Marker> selectedMarkers = new ArrayList<>();

  private VietmapMap VietmapMap;
  @Nullable
  private VietmapMap.OnMarkerClickListener onMarkerClickListener;
  @Nullable
  private VietmapMap.OnPolygonClickListener onPolygonClickListener;
  @Nullable
  private VietmapMap.OnPolylineClickListener onPolylineClickListener;

  private Annotations annotations;
  private ShapeAnnotations shapeAnnotations;
  private Markers markers;
  private Polygons polygons;
  private Polylines polylines;

  AnnotationManager(@NonNull MapView mapView, LongSparseArray<Annotation> annotationsArray,
                    IconManager iconManager, Annotations annotations, Markers markers, Polygons polygons,
                    Polylines polylines, ShapeAnnotations shapeAnnotations) {
    this.mapView = mapView;
    this.annotationsArray = annotationsArray;
    this.iconManager = iconManager;
    this.annotations = annotations;
    this.markers = markers;
    this.polygons = polygons;
    this.polylines = polylines;
    this.shapeAnnotations = shapeAnnotations;
  }

  // TODO refactor VietmapMap out for Projection and Transform
  // Requires removing VietmapMap from Annotations by using Peer model from #6912
  @NonNull
  AnnotationManager bind(VietmapMap VietmapMap) {
    this.VietmapMap = VietmapMap;
    return this;
  }

  void update() {
    infoWindowManager.update();
  }

  //
  // Annotations
  //

  Annotation getAnnotation(long id) {
    return annotations.obtainBy(id);
  }

  List<Annotation> getAnnotations() {
    return annotations.obtainAll();
  }

  void removeAnnotation(long id) {
    annotations.removeBy(id);
  }

  void removeAnnotation(@NonNull Annotation annotation) {
    if (annotation instanceof Marker) {
      Marker marker = (Marker) annotation;
      marker.hideInfoWindow();
      if (selectedMarkers.contains(marker)) {
        selectedMarkers.remove(marker);
      }
      // do icon cleanup
      iconManager.iconCleanup(marker.getIcon());
    }
    annotations.removeBy(annotation);
  }

  void removeAnnotations(@NonNull List<? extends Annotation> annotationList) {
    for (Annotation annotation : annotationList) {
      if (annotation instanceof Marker) {
        Marker marker = (Marker) annotation;
        marker.hideInfoWindow();
        if (selectedMarkers.contains(marker)) {
          selectedMarkers.remove(marker);
        }
        iconManager.iconCleanup(marker.getIcon());
      }
    }
    annotations.removeBy(annotationList);
  }

  void removeAnnotations() {
    Annotation annotation;
    int count = annotationsArray.size();
    long[] ids = new long[count];
    selectedMarkers.clear();
    for (int i = 0; i < count; i++) {
      ids[i] = annotationsArray.keyAt(i);
      annotation = annotationsArray.get(ids[i]);
      if (annotation instanceof Marker) {
        Marker marker = (Marker) annotation;
        marker.hideInfoWindow();
        iconManager.iconCleanup(marker.getIcon());
      }
    }
    annotations.removeAll();
  }

  //
  // Markers
  //

  Marker addMarker(@NonNull BaseMarkerOptions markerOptions, @NonNull VietmapMap VietmapMap) {
    return markers.addBy(markerOptions, VietmapMap);
  }

  List<Marker> addMarkers(@NonNull List<? extends BaseMarkerOptions> markerOptionsList, @NonNull VietmapMap VietmapMap) {
    return markers.addBy(markerOptionsList, VietmapMap);
  }

  void updateMarker(@NonNull Marker updatedMarker, @NonNull VietmapMap VietmapMap) {
    if (!isAddedToMap(updatedMarker)) {
      logNonAdded(updatedMarker);
      return;
    }
    markers.update(updatedMarker, VietmapMap);
  }

  List<Marker> getMarkers() {
    return markers.obtainAll();
  }

  @NonNull
  List<Marker> getMarkersInRect(@NonNull RectF rectangle) {
    return markers.obtainAllIn(rectangle);
  }

  void reloadMarkers() {
    markers.reload();
  }

  //
  // Polygons
  //

  Polygon addPolygon(@NonNull PolygonOptions polygonOptions, @NonNull VietmapMap VietmapMap) {
    return polygons.addBy(polygonOptions, VietmapMap);
  }

  List<Polygon> addPolygons(@NonNull List<PolygonOptions> polygonOptionsList, @NonNull VietmapMap VietmapMap) {
    return polygons.addBy(polygonOptionsList, VietmapMap);
  }

  void updatePolygon(@NonNull Polygon polygon) {
    if (!isAddedToMap(polygon)) {
      logNonAdded(polygon);
      return;
    }
    polygons.update(polygon);
  }

  List<Polygon> getPolygons() {
    return polygons.obtainAll();
  }

  //
  // Polylines
  //

  Polyline addPolyline(@NonNull PolylineOptions polylineOptions, @NonNull VietmapMap VietmapMap) {
    return polylines.addBy(polylineOptions, VietmapMap);
  }

  List<Polyline> addPolylines(@NonNull List<PolylineOptions> polylineOptionsList, @NonNull VietmapMap VietmapMap) {
    return polylines.addBy(polylineOptionsList, VietmapMap);
  }

  void updatePolyline(@NonNull Polyline polyline) {
    if (!isAddedToMap(polyline)) {
      logNonAdded(polyline);
      return;
    }
    polylines.update(polyline);
  }

  List<Polyline> getPolylines() {
    return polylines.obtainAll();
  }

  // TODO Refactor from here still in progress
  void setOnMarkerClickListener(@Nullable VietmapMap.OnMarkerClickListener listener) {
    onMarkerClickListener = listener;
  }

  void setOnPolygonClickListener(@Nullable VietmapMap.OnPolygonClickListener listener) {
    onPolygonClickListener = listener;
  }

  void setOnPolylineClickListener(@Nullable VietmapMap.OnPolylineClickListener listener) {
    onPolylineClickListener = listener;
  }

  void selectMarker(@NonNull Marker marker) {
    if (selectedMarkers.contains(marker)) {
      return;
    }

    // Need to deselect any currently selected annotation first
    if (!infoWindowManager.isAllowConcurrentMultipleOpenInfoWindows()) {
      deselectMarkers();
    }

    if (infoWindowManager.isInfoWindowValidForMarker(marker) || infoWindowManager.getInfoWindowAdapter() != null) {
      infoWindowManager.add(marker.showInfoWindow(VietmapMap, mapView));
    }

    // only add to selected markers if user didn't handle the click event themselves #3176
    selectedMarkers.add(marker);
  }

  void deselectMarkers() {
    if (selectedMarkers.isEmpty()) {
      return;
    }

    for (Marker marker : selectedMarkers) {
      if (marker != null) {
        if (marker.isInfoWindowShown()) {
          marker.hideInfoWindow();
        }
      }
    }

    // Removes all selected markers from the list
    selectedMarkers.clear();
  }

  void deselectMarker(@NonNull Marker marker) {
    if (!selectedMarkers.contains(marker)) {
      return;
    }

    if (marker.isInfoWindowShown()) {
      marker.hideInfoWindow();
    }
    selectedMarkers.remove(marker);
  }

  @NonNull
  List<Marker> getSelectedMarkers() {
    return selectedMarkers;
  }

  @NonNull
  InfoWindowManager getInfoWindowManager() {
    return infoWindowManager;
  }

  void adjustTopOffsetPixels(@NonNull VietmapMap VietmapMap) {
    int count = annotationsArray.size();
    for (int i = 0; i < count; i++) {
      Annotation annotation = annotationsArray.get(i);
      if (annotation instanceof Marker) {
        Marker marker = (Marker) annotation;
        marker.setTopOffsetPixels(
          iconManager.getTopOffsetPixelsForIcon(marker.getIcon()));
      }
    }

    for (Marker marker : selectedMarkers) {
      if (marker.isInfoWindowShown()) {
        marker.hideInfoWindow();
        marker.showInfoWindow(VietmapMap, mapView);
      }
    }
  }

  private boolean isAddedToMap(@Nullable Annotation annotation) {
    return annotation != null && annotation.getId() != -1 && annotationsArray.indexOfKey(annotation.getId()) > -1;
  }

  private void logNonAdded(@NonNull Annotation annotation) {
    Logger.w(TAG, String.format(
      "Attempting to update non-added %s with value %s", annotation.getClass().getCanonicalName(), annotation)
    );
  }

  //
  // Click event
  //

  boolean onTap(@NonNull PointF tapPoint) {
    MarkerHit markerHit = getMarkerHitFromTouchArea(tapPoint);
    long markerId = new MarkerHitResolver(VietmapMap).execute(markerHit);
    if (markerId != NO_ANNOTATION_ID) {
      if (isClickHandledForMarker(markerId)) {
        return true;
      }
    }

    ShapeAnnotationHit shapeAnnotationHit = getShapeAnnotationHitFromTap(tapPoint);
    Annotation annotation = new ShapeAnnotationHitResolver(shapeAnnotations).execute(shapeAnnotationHit);
    return annotation != null && handleClickForShapeAnnotation(annotation);
  }

  private ShapeAnnotationHit getShapeAnnotationHitFromTap(PointF tapPoint) {
    float touchTargetSide = Vietmap.getApplicationContext().getResources().getDimension(R.dimen.mapbox_eight_dp);
    RectF tapRect = new RectF(
      tapPoint.x - touchTargetSide,
      tapPoint.y - touchTargetSide,
      tapPoint.x + touchTargetSide,
      tapPoint.y + touchTargetSide
    );
    return new ShapeAnnotationHit(tapRect);
  }

  private boolean handleClickForShapeAnnotation(Annotation annotation) {
    if (annotation instanceof Polygon && onPolygonClickListener != null) {
      onPolygonClickListener.onPolygonClick((Polygon) annotation);
      return true;
    } else if (annotation instanceof Polyline && onPolylineClickListener != null) {
      onPolylineClickListener.onPolylineClick((Polyline) annotation);
      return true;
    }
    return false;
  }

  private MarkerHit getMarkerHitFromTouchArea(PointF tapPoint) {
    int touchSurfaceWidth = (int) (iconManager.getHighestIconHeight() * 1.5);
    int touchSurfaceHeight = (int) (iconManager.getHighestIconWidth() * 1.5);
    final RectF tapRect = new RectF(tapPoint.x - touchSurfaceWidth,
      tapPoint.y - touchSurfaceHeight,
      tapPoint.x + touchSurfaceWidth,
      tapPoint.y + touchSurfaceHeight
    );
    return new MarkerHit(tapRect, getMarkersInRect(tapRect));
  }

  private boolean isClickHandledForMarker(long markerId) {
    Marker marker = (Marker) getAnnotation(markerId);
    boolean handledDefaultClick = onClickMarker(marker);
    if (!handledDefaultClick) {
      toggleMarkerSelectionState(marker);
    }
    return true;
  }

  private boolean onClickMarker(@NonNull Marker marker) {
    return onMarkerClickListener != null && onMarkerClickListener.onMarkerClick(marker);
  }

  private void toggleMarkerSelectionState(@NonNull Marker marker) {
    if (!selectedMarkers.contains(marker)) {
      selectMarker(marker);
    } else {
      deselectMarker(marker);
    }
  }

  private static class ShapeAnnotationHitResolver {

    private ShapeAnnotations shapeAnnotations;

    ShapeAnnotationHitResolver(ShapeAnnotations shapeAnnotations) {
      this.shapeAnnotations = shapeAnnotations;
    }

    @Nullable
    public Annotation execute(@NonNull ShapeAnnotationHit shapeHit) {
      Annotation foundAnnotation = null;
      List<Annotation> annotations = shapeAnnotations.obtainAllIn(shapeHit.tapPoint);
      if (annotations.size() > 0) {
        foundAnnotation = annotations.get(0);
      }
      return foundAnnotation;
    }
  }

  private static class MarkerHitResolver {

    @NonNull
    private final Projection projection;
    private final int minimalTouchSize;

    @Nullable
    private View view;

    private Bitmap bitmap;
    private int bitmapWidth;
    private int bitmapHeight;
    private PointF markerLocation;

    @NonNull
    private Rect hitRectView = new Rect();
    @NonNull
    private RectF hitRectMarker = new RectF();
    @NonNull
    private RectF highestSurfaceIntersection = new RectF();

    private long closestMarkerId = NO_ANNOTATION_ID;

    MarkerHitResolver(@NonNull VietmapMap VietmapMap) {
      this.projection = VietmapMap.getProjection();
      this.minimalTouchSize = (int) (32 * Vietmap.getApplicationContext().getResources().getDisplayMetrics().density);
    }

    public long execute(@NonNull MarkerHit markerHit) {
      resolveForMarkers(markerHit);
      return closestMarkerId;
    }

    private void resolveForMarkers(MarkerHit markerHit) {
      for (Marker marker : markerHit.markers) {
        resolveForMarker(markerHit, marker);
      }
    }

    private void resolveForMarker(@NonNull MarkerHit markerHit, Marker marker) {
      markerLocation = projection.toScreenLocation(marker.getPosition());
      bitmap = marker.getIcon().getBitmap();

      bitmapHeight = bitmap.getHeight();
      if (bitmapHeight < minimalTouchSize) {
        bitmapHeight = minimalTouchSize;
      }

      bitmapWidth = bitmap.getWidth();
      if (bitmapWidth < minimalTouchSize) {
        bitmapWidth = minimalTouchSize;
      }

      hitRectMarker.set(0, 0, bitmapWidth, bitmapHeight);
      hitRectMarker.offsetTo(
        markerLocation.x - bitmapWidth / 2,
        markerLocation.y - bitmapHeight / 2
      );
      hitTestMarker(markerHit, marker, hitRectMarker);
    }

    private void hitTestMarker(MarkerHit markerHit, @NonNull Marker marker, RectF hitRectMarker) {
      if (hitRectMarker.contains(markerHit.getTapPointX(), markerHit.getTapPointY())) {
        hitRectMarker.intersect(markerHit.tapRect);
        if (isRectangleHighestSurfaceIntersection(hitRectMarker)) {
          highestSurfaceIntersection = new RectF(hitRectMarker);
          closestMarkerId = marker.getId();
        }
      }
    }

    private boolean isRectangleHighestSurfaceIntersection(RectF rectF) {
      return rectF.width() * rectF.height() > highestSurfaceIntersection.width() * highestSurfaceIntersection.height();
    }
  }

  private static class ShapeAnnotationHit {
    private final RectF tapPoint;

    ShapeAnnotationHit(RectF tapPoint) {
      this.tapPoint = tapPoint;
    }
  }

  private static class MarkerHit {
    private final RectF tapRect;
    private final List<Marker> markers;

    MarkerHit(RectF tapRect, List<Marker> markers) {
      this.tapRect = tapRect;
      this.markers = markers;
    }

    float getTapPointX() {
      return tapRect.centerX();
    }

    float getTapPointY() {
      return tapRect.centerY();
    }
  }
}
