package vn.vietmap.vietmapsdk.maps;

import androidx.collection.LongSparseArray;

import vn.vietmap.vietmapsdk.annotations.Annotation;
import vn.vietmap.vietmapsdk.annotations.BaseMarkerOptions;
import vn.vietmap.vietmapsdk.annotations.Marker;
import vn.vietmap.vietmapsdk.annotations.MarkerOptions;
import vn.vietmap.vietmapsdk.geometry.LatLng;

import org.junit.Test;
import org.mockito.ArgumentMatchers;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AnnotationManagerTest {

  @Test
  public void checksAddAMarker() throws Exception {
    NativeMap aNativeMapView = mock(NativeMapView.class);
    MapView aMapView = mock(MapView.class);
    LongSparseArray<Annotation> annotationsArray = new LongSparseArray<>();
    IconManager aIconManager = mock(IconManager.class);
    Annotations annotations = new AnnotationContainer(aNativeMapView, annotationsArray);
    Markers markers = new MarkerContainer(aNativeMapView, annotationsArray, aIconManager);
    Polygons polygons = new PolygonContainer(aNativeMapView, annotationsArray);
    Polylines polylines = new PolylineContainer(aNativeMapView, annotationsArray);
    ShapeAnnotations shapeAnnotations = new ShapeAnnotationContainer(aNativeMapView, annotationsArray);
    AnnotationManager annotationManager = new AnnotationManager(aMapView, annotationsArray,
      aIconManager, annotations, markers, polygons, polylines, shapeAnnotations);
    Marker aMarker = mock(Marker.class);
    long aId = 5L;
    when(aNativeMapView.addMarker(aMarker)).thenReturn(aId);
    BaseMarkerOptions aMarkerOptions = mock(BaseMarkerOptions.class);
    VietmapMap aVietmapMap = mock(VietmapMap.class);
    when(aMarkerOptions.getMarker()).thenReturn(aMarker);

    annotationManager.addMarker(aMarkerOptions, aVietmapMap);

    assertEquals(aMarker, annotationManager.getAnnotations().get(0));
    assertEquals(aMarker, annotationManager.getAnnotation(aId));
  }

  @Test
  public void checksAddMarkers() throws Exception {
    NativeMapView aNativeMapView = mock(NativeMapView.class);
    MapView aMapView = mock(MapView.class);
    LongSparseArray<Annotation> annotationsArray = new LongSparseArray<>();
    IconManager aIconManager = mock(IconManager.class);
    Annotations annotations = new AnnotationContainer(aNativeMapView, annotationsArray);
    Markers markers = new MarkerContainer(aNativeMapView, annotationsArray, aIconManager);
    Polygons polygons = new PolygonContainer(aNativeMapView, annotationsArray);
    Polylines polylines = new PolylineContainer(aNativeMapView, annotationsArray);
    ShapeAnnotations shapeAnnotations = new ShapeAnnotationContainer(aNativeMapView, annotationsArray);
    AnnotationManager annotationManager = new AnnotationManager(aMapView, annotationsArray,
      aIconManager, annotations, markers, polygons, polylines, shapeAnnotations);

    long firstId = 1L;
    long secondId = 2L;
    List<BaseMarkerOptions> markerList = new ArrayList<>();
    MarkerOptions firstMarkerOption = new MarkerOptions().position(new LatLng()).title("first");
    MarkerOptions secondMarkerOption = new MarkerOptions().position(new LatLng()).title("second");

    markerList.add(firstMarkerOption);
    markerList.add(secondMarkerOption);
    VietmapMap aVietmapMap = mock(VietmapMap.class);
    when(aNativeMapView.addMarker(any(Marker.class))).thenReturn(firstId, secondId);

    when(aNativeMapView.addMarkers(ArgumentMatchers.<Marker>anyList()))
      .thenReturn(new long[] {firstId, secondId});

    annotationManager.addMarkers(markerList, aVietmapMap);

    assertEquals(2, annotationManager.getAnnotations().size());
    assertEquals("first", ((Marker) annotationManager.getAnnotations().get(0)).getTitle());
    assertEquals("second", ((Marker) annotationManager.getAnnotations().get(1)).getTitle());
    assertEquals("first", ((Marker) annotationManager.getAnnotation(firstId)).getTitle());
    assertEquals("second", ((Marker) annotationManager.getAnnotation(secondId)).getTitle());
  }
}