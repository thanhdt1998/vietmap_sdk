package vn.vietmap.vietmapsdk.annotations;

import android.graphics.PointF;

import vn.vietmap.vietmapsdk.geometry.LatLng;
import vn.vietmap.vietmapsdk.maps.MapView;
import vn.vietmap.vietmapsdk.maps.VietmapMap;
import vn.vietmap.vietmapsdk.maps.Projection;

import org.junit.Test;
import org.mockito.InjectMocks;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class InfoWindowTest {

  @InjectMocks
  MapView mMapView = mock(MapView.class);

  @InjectMocks
  VietmapMap mVietmapMap = mock(VietmapMap.class);

  @Test
  public void testSanity() {
    InfoWindow infoWindow = new InfoWindow(mMapView, mVietmapMap);
    assertNotNull("infoWindow should exist", infoWindow);
  }

  @Test
  public void testBoundMarker() {
    MarkerOptions markerOptions = new MarkerOptions();
    Marker marker = markerOptions.position(new LatLng()).getMarker();
    InfoWindow infoWindow = new InfoWindow(mMapView, mVietmapMap).setBoundMarker(marker);
    assertEquals("marker should match", marker, infoWindow.getBoundMarker());
  }

  @Test
  public void testClose() {
    InfoWindow infoWindow = new InfoWindow(mMapView, mVietmapMap);
    infoWindow.close();
    assertEquals("infowindow should not be visible", false, infoWindow.isVisible());
  }


  @Test
  public void testOpen() {
    LatLng latLng = new LatLng(0, 0);
    Projection projection = mock(Projection.class);
    when(mVietmapMap.getProjection()).thenReturn(projection);
    when(projection.toScreenLocation(latLng)).thenReturn(new PointF(0, 0));

    InfoWindow infoWindow = new InfoWindow(mMapView, mVietmapMap);
    infoWindow.open(mMapView, new MarkerOptions().position(new LatLng()).getMarker(), latLng, 0, 0);
    assertEquals("infowindow should not be visible", true, infoWindow.isVisible());
  }

  @Test
  public void testOpenClose() {
    LatLng latLng = new LatLng(0, 0);
    Projection projection = mock(Projection.class);
    when(mVietmapMap.getProjection()).thenReturn(projection);
    when(projection.toScreenLocation(latLng)).thenReturn(new PointF(0, 0));

    InfoWindow infoWindow = new InfoWindow(mMapView, mVietmapMap);
    infoWindow.open(mMapView, new MarkerOptions().position(new LatLng()).getMarker(), latLng, 0, 0);
    infoWindow.close();
    assertEquals("infowindow should not be visible", false, infoWindow.isVisible());
  }


  @Test
  public void testUpdate() {
    LatLng latLng = new LatLng(0, 0);
    Projection projection = mock(Projection.class);
    when(mVietmapMap.getProjection()).thenReturn(projection);
    when(projection.toScreenLocation(latLng)).thenReturn(new PointF(0, 0));

    InfoWindow infoWindow = new InfoWindow(mMapView, mVietmapMap);
    infoWindow.open(mMapView, new MarkerOptions().position(latLng).getMarker(), latLng, 0, 0);
    infoWindow.update();
  }

}
