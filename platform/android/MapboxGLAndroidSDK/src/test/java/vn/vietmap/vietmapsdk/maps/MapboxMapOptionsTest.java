package vn.vietmap.vietmapsdk.maps;

import android.graphics.Color;
import android.view.Gravity;

import vn.vietmap.vietmapsdk.camera.CameraPosition;
import vn.vietmap.vietmapsdk.constants.VietmapConstants;
import vn.vietmap.vietmapsdk.geometry.LatLng;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import java.util.Arrays;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class VietmapMapOptionsTest {

  private static final double DELTA = 1e-15;

  @Test
  public void testSanity() {
    assertNotNull("should not be null", new VietmapMapOptions());
  }

  @Test
  public void testDebugEnabled() {
    assertFalse(new VietmapMapOptions().getDebugActive());
    assertTrue(new VietmapMapOptions().debugActive(true).getDebugActive());
    assertFalse(new VietmapMapOptions().debugActive(false).getDebugActive());
  }

  @Test
  public void testCompassEnabled() {
    assertTrue(new VietmapMapOptions().compassEnabled(true).getCompassEnabled());
    assertFalse(new VietmapMapOptions().compassEnabled(false).getCompassEnabled());
  }

  @Test
  public void testCompassGravity() {
    assertEquals(Gravity.TOP | Gravity.END, new VietmapMapOptions().getCompassGravity());
    assertEquals(Gravity.BOTTOM, new VietmapMapOptions().compassGravity(Gravity.BOTTOM).getCompassGravity());
    assertNotEquals(Gravity.START, new VietmapMapOptions().compassGravity(Gravity.BOTTOM).getCompassGravity());
  }

  @Test
  public void testCompassMargins() {
    assertTrue(Arrays.equals(new int[] {0, 1, 2, 3}, new VietmapMapOptions().compassMargins(
      new int[] {0, 1, 2, 3}).getCompassMargins()));
    assertFalse(Arrays.equals(new int[] {0, 1, 2, 3}, new VietmapMapOptions().compassMargins(
      new int[] {0, 0, 0, 0}).getCompassMargins()));
  }

  @Test
  public void testLogoEnabled() {
    assertTrue(new VietmapMapOptions().logoEnabled(true).getLogoEnabled());
    assertFalse(new VietmapMapOptions().logoEnabled(false).getLogoEnabled());
  }

  @Test
  public void testLogoGravity() {
    assertEquals(Gravity.BOTTOM | Gravity.START, new VietmapMapOptions().getLogoGravity());
    assertEquals(Gravity.BOTTOM, new VietmapMapOptions().logoGravity(Gravity.BOTTOM).getLogoGravity());
    assertNotEquals(Gravity.START, new VietmapMapOptions().logoGravity(Gravity.BOTTOM).getLogoGravity());
  }

  @Test
  public void testLogoMargins() {
    assertTrue(Arrays.equals(new int[] {0, 1, 2, 3}, new VietmapMapOptions().logoMargins(
      new int[] {0, 1, 2, 3}).getLogoMargins()));
    assertFalse(Arrays.equals(new int[] {0, 1, 2, 3}, new VietmapMapOptions().logoMargins(
      new int[] {0, 0, 0, 0}).getLogoMargins()));
  }

  @Test
  public void testAttributionTintColor() {
    assertEquals(-1, new VietmapMapOptions().getAttributionTintColor());
    assertEquals(Color.RED, new VietmapMapOptions().attributionTintColor(Color.RED).getAttributionTintColor());
  }

  @Test
  public void testAttributionEnabled() {
    assertTrue(new VietmapMapOptions().attributionEnabled(true).getAttributionEnabled());
    assertFalse(new VietmapMapOptions().attributionEnabled(false).getAttributionEnabled());
  }

  @Test
  public void testAttributionGravity() {
    assertEquals(Gravity.BOTTOM | Gravity.START, new VietmapMapOptions().getAttributionGravity());
    assertEquals(Gravity.BOTTOM, new VietmapMapOptions().attributionGravity(Gravity.BOTTOM).getAttributionGravity());
    assertNotEquals(Gravity.START, new VietmapMapOptions().attributionGravity(Gravity.BOTTOM).getAttributionGravity());
  }

  @Test
  public void testAttributionMargins() {
    assertTrue(Arrays.equals(new int[] {0, 1, 2, 3}, new VietmapMapOptions().attributionMargins(
      new int[] {0, 1, 2, 3}).getAttributionMargins()));
    assertFalse(Arrays.equals(new int[] {0, 1, 2, 3}, new VietmapMapOptions().attributionMargins(
      new int[] {0, 0, 0, 0}).getAttributionMargins()));
  }

  @Test
  public void testMinZoom() {
    assertEquals(VietmapConstants.MINIMUM_ZOOM, new VietmapMapOptions().getMinZoomPreference(), DELTA);
    assertEquals(5.0f, new VietmapMapOptions().minZoomPreference(5.0f).getMinZoomPreference(), DELTA);
    assertNotEquals(2.0f, new VietmapMapOptions().minZoomPreference(5.0f).getMinZoomPreference(), DELTA);
  }

  @Test
  public void testMaxZoom() {
    assertEquals(VietmapConstants.MAXIMUM_ZOOM, new VietmapMapOptions().getMaxZoomPreference(), DELTA);
    assertEquals(5.0f, new VietmapMapOptions().maxZoomPreference(5.0f).getMaxZoomPreference(), DELTA);
    assertNotEquals(2.0f, new VietmapMapOptions().maxZoomPreference(5.0f).getMaxZoomPreference(), DELTA);
  }

  @Test
  public void testMinPitch() {
    assertEquals(VietmapConstants.MINIMUM_PITCH, new VietmapMapOptions().getMinPitchPreference(), DELTA);
    assertEquals(5.0f, new VietmapMapOptions().minPitchPreference(5.0f).getMinPitchPreference(), DELTA);
    assertNotEquals(2.0f, new VietmapMapOptions().minPitchPreference(5.0f).getMinPitchPreference(), DELTA);
  }

  @Test
  public void testMaxPitch() {
    assertEquals(VietmapConstants.MAXIMUM_PITCH, new VietmapMapOptions().getMaxPitchPreference(), DELTA);
    assertEquals(5.0f, new VietmapMapOptions().maxPitchPreference(5.0f).getMaxPitchPreference(), DELTA);
    assertNotEquals(2.0f, new VietmapMapOptions().maxPitchPreference(5.0f).getMaxPitchPreference(), DELTA);
  }

  @Test
  public void testTiltGesturesEnabled() {
    assertTrue(new VietmapMapOptions().getTiltGesturesEnabled());
    assertTrue(new VietmapMapOptions().tiltGesturesEnabled(true).getTiltGesturesEnabled());
    assertFalse(new VietmapMapOptions().tiltGesturesEnabled(false).getTiltGesturesEnabled());
  }

  @Test
  public void testScrollGesturesEnabled() {
    assertTrue(new VietmapMapOptions().getScrollGesturesEnabled());
    assertTrue(new VietmapMapOptions().scrollGesturesEnabled(true).getScrollGesturesEnabled());
    assertFalse(new VietmapMapOptions().scrollGesturesEnabled(false).getScrollGesturesEnabled());
  }

  @Test
  public void testHorizontalScrollGesturesEnabled() {
    assertTrue(new VietmapMapOptions().getHorizontalScrollGesturesEnabled());
    assertTrue(new VietmapMapOptions().horizontalScrollGesturesEnabled(true).getHorizontalScrollGesturesEnabled());
    assertFalse(new VietmapMapOptions().horizontalScrollGesturesEnabled(false).getHorizontalScrollGesturesEnabled());
  }

  @Test
  public void testZoomGesturesEnabled() {
    assertTrue(new VietmapMapOptions().getZoomGesturesEnabled());
    assertTrue(new VietmapMapOptions().zoomGesturesEnabled(true).getZoomGesturesEnabled());
    assertFalse(new VietmapMapOptions().zoomGesturesEnabled(false).getZoomGesturesEnabled());
  }

  @Test
  public void testRotateGesturesEnabled() {
    assertTrue(new VietmapMapOptions().getRotateGesturesEnabled());
    assertTrue(new VietmapMapOptions().rotateGesturesEnabled(true).getRotateGesturesEnabled());
    assertFalse(new VietmapMapOptions().rotateGesturesEnabled(false).getRotateGesturesEnabled());
  }

  @Test
  public void testCamera() {
    CameraPosition position = new CameraPosition.Builder().build();
    assertEquals(new CameraPosition.Builder(position).build(), new VietmapMapOptions().camera(position).getCamera());
    assertNotEquals(new CameraPosition.Builder().target(new LatLng(1, 1)), new VietmapMapOptions().camera(position));
    assertNull(new VietmapMapOptions().getCamera());
  }

  @Test
  public void testPrefetchesTiles() {
    // Default value
    assertTrue(new VietmapMapOptions().getPrefetchesTiles());

    // Check mutations
    assertTrue(new VietmapMapOptions().setPrefetchesTiles(true).getPrefetchesTiles());
    assertFalse(new VietmapMapOptions().setPrefetchesTiles(false).getPrefetchesTiles());
  }

  @Test
  public void testPrefetchZoomDelta() {
    // Default value
    assertEquals(4, new VietmapMapOptions().getPrefetchZoomDelta());

    // Check mutations
    assertEquals(5, new VietmapMapOptions().setPrefetchZoomDelta(5).getPrefetchZoomDelta());
  }


  @Test
  public void testCrossSourceCollisions() {
    // Default value
    assertTrue(new VietmapMapOptions().getCrossSourceCollisions());

    // check mutations
    assertTrue(new VietmapMapOptions().crossSourceCollisions(true).getCrossSourceCollisions());
    assertFalse(new VietmapMapOptions().crossSourceCollisions(false).getCrossSourceCollisions());
  }

  @Test
  public void testLocalIdeographFontFamily_enabledByDefault() {
    VietmapMapOptions options = VietmapMapOptions.createFromAttributes(RuntimeEnvironment.application, null);
    assertEquals(VietmapConstants.DEFAULT_FONT, options.getLocalIdeographFontFamily());
  }
}

