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
    assertNotNull("should not be null", new VietMapOptions());
  }

  @Test
  public void testDebugEnabled() {
    assertFalse(new VietMapOptions().getDebugActive());
    assertTrue(new VietMapOptions().debugActive(true).getDebugActive());
    assertFalse(new VietMapOptions().debugActive(false).getDebugActive());
  }

  @Test
  public void testCompassEnabled() {
    assertTrue(new VietMapOptions().compassEnabled(true).getCompassEnabled());
    assertFalse(new VietMapOptions().compassEnabled(false).getCompassEnabled());
  }

  @Test
  public void testCompassGravity() {
    assertEquals(Gravity.TOP | Gravity.END, new VietMapOptions().getCompassGravity());
    assertEquals(Gravity.BOTTOM, new VietMapOptions().compassGravity(Gravity.BOTTOM).getCompassGravity());
    assertNotEquals(Gravity.START, new VietMapOptions().compassGravity(Gravity.BOTTOM).getCompassGravity());
  }

  @Test
  public void testCompassMargins() {
    assertTrue(Arrays.equals(new int[] {0, 1, 2, 3}, new VietMapOptions().compassMargins(
      new int[] {0, 1, 2, 3}).getCompassMargins()));
    assertFalse(Arrays.equals(new int[] {0, 1, 2, 3}, new VietMapOptions().compassMargins(
      new int[] {0, 0, 0, 0}).getCompassMargins()));
  }

  @Test
  public void testLogoEnabled() {
    assertTrue(new VietMapOptions().logoEnabled(true).getLogoEnabled());
    assertFalse(new VietMapOptions().logoEnabled(false).getLogoEnabled());
  }

  @Test
  public void testLogoGravity() {
    assertEquals(Gravity.BOTTOM | Gravity.START, new VietMapOptions().getLogoGravity());
    assertEquals(Gravity.BOTTOM, new VietMapOptions().logoGravity(Gravity.BOTTOM).getLogoGravity());
    assertNotEquals(Gravity.START, new VietMapOptions().logoGravity(Gravity.BOTTOM).getLogoGravity());
  }

  @Test
  public void testLogoMargins() {
    assertTrue(Arrays.equals(new int[] {0, 1, 2, 3}, new VietMapOptions().logoMargins(
      new int[] {0, 1, 2, 3}).getLogoMargins()));
    assertFalse(Arrays.equals(new int[] {0, 1, 2, 3}, new VietMapOptions().logoMargins(
      new int[] {0, 0, 0, 0}).getLogoMargins()));
  }

  @Test
  public void testAttributionTintColor() {
    assertEquals(-1, new VietMapOptions().getAttributionTintColor());
    assertEquals(Color.RED, new VietMapOptions().attributionTintColor(Color.RED).getAttributionTintColor());
  }

  @Test
  public void testAttributionEnabled() {
    assertTrue(new VietMapOptions().attributionEnabled(true).getAttributionEnabled());
    assertFalse(new VietMapOptions().attributionEnabled(false).getAttributionEnabled());
  }

  @Test
  public void testAttributionGravity() {
    assertEquals(Gravity.BOTTOM | Gravity.START, new VietMapOptions().getAttributionGravity());
    assertEquals(Gravity.BOTTOM, new VietMapOptions().attributionGravity(Gravity.BOTTOM).getAttributionGravity());
    assertNotEquals(Gravity.START, new VietMapOptions().attributionGravity(Gravity.BOTTOM).getAttributionGravity());
  }

  @Test
  public void testAttributionMargins() {
    assertTrue(Arrays.equals(new int[] {0, 1, 2, 3}, new VietMapOptions().attributionMargins(
      new int[] {0, 1, 2, 3}).getAttributionMargins()));
    assertFalse(Arrays.equals(new int[] {0, 1, 2, 3}, new VietMapOptions().attributionMargins(
      new int[] {0, 0, 0, 0}).getAttributionMargins()));
  }

  @Test
  public void testMinZoom() {
    assertEquals(VietmapConstants.MINIMUM_ZOOM, new VietMapOptions().getMinZoomPreference(), DELTA);
    assertEquals(5.0f, new VietMapOptions().minZoomPreference(5.0f).getMinZoomPreference(), DELTA);
    assertNotEquals(2.0f, new VietMapOptions().minZoomPreference(5.0f).getMinZoomPreference(), DELTA);
  }

  @Test
  public void testMaxZoom() {
    assertEquals(VietmapConstants.MAXIMUM_ZOOM, new VietMapOptions().getMaxZoomPreference(), DELTA);
    assertEquals(5.0f, new VietMapOptions().maxZoomPreference(5.0f).getMaxZoomPreference(), DELTA);
    assertNotEquals(2.0f, new VietMapOptions().maxZoomPreference(5.0f).getMaxZoomPreference(), DELTA);
  }

  @Test
  public void testMinPitch() {
    assertEquals(VietmapConstants.MINIMUM_PITCH, new VietMapOptions().getMinPitchPreference(), DELTA);
    assertEquals(5.0f, new VietMapOptions().minPitchPreference(5.0f).getMinPitchPreference(), DELTA);
    assertNotEquals(2.0f, new VietMapOptions().minPitchPreference(5.0f).getMinPitchPreference(), DELTA);
  }

  @Test
  public void testMaxPitch() {
    assertEquals(VietmapConstants.MAXIMUM_PITCH, new VietMapOptions().getMaxPitchPreference(), DELTA);
    assertEquals(5.0f, new VietMapOptions().maxPitchPreference(5.0f).getMaxPitchPreference(), DELTA);
    assertNotEquals(2.0f, new VietMapOptions().maxPitchPreference(5.0f).getMaxPitchPreference(), DELTA);
  }

  @Test
  public void testTiltGesturesEnabled() {
    assertTrue(new VietMapOptions().getTiltGesturesEnabled());
    assertTrue(new VietMapOptions().tiltGesturesEnabled(true).getTiltGesturesEnabled());
    assertFalse(new VietMapOptions().tiltGesturesEnabled(false).getTiltGesturesEnabled());
  }

  @Test
  public void testScrollGesturesEnabled() {
    assertTrue(new VietMapOptions().getScrollGesturesEnabled());
    assertTrue(new VietMapOptions().scrollGesturesEnabled(true).getScrollGesturesEnabled());
    assertFalse(new VietMapOptions().scrollGesturesEnabled(false).getScrollGesturesEnabled());
  }

  @Test
  public void testHorizontalScrollGesturesEnabled() {
    assertTrue(new VietMapOptions().getHorizontalScrollGesturesEnabled());
    assertTrue(new VietMapOptions().horizontalScrollGesturesEnabled(true).getHorizontalScrollGesturesEnabled());
    assertFalse(new VietMapOptions().horizontalScrollGesturesEnabled(false).getHorizontalScrollGesturesEnabled());
  }

  @Test
  public void testZoomGesturesEnabled() {
    assertTrue(new VietMapOptions().getZoomGesturesEnabled());
    assertTrue(new VietMapOptions().zoomGesturesEnabled(true).getZoomGesturesEnabled());
    assertFalse(new VietMapOptions().zoomGesturesEnabled(false).getZoomGesturesEnabled());
  }

  @Test
  public void testRotateGesturesEnabled() {
    assertTrue(new VietMapOptions().getRotateGesturesEnabled());
    assertTrue(new VietMapOptions().rotateGesturesEnabled(true).getRotateGesturesEnabled());
    assertFalse(new VietMapOptions().rotateGesturesEnabled(false).getRotateGesturesEnabled());
  }

  @Test
  public void testCamera() {
    CameraPosition position = new CameraPosition.Builder().build();
    assertEquals(new CameraPosition.Builder(position).build(), new VietMapOptions().camera(position).getCamera());
    assertNotEquals(new CameraPosition.Builder().target(new LatLng(1, 1)), new VietMapOptions().camera(position));
    assertNull(new VietMapOptions().getCamera());
  }

  @Test
  public void testPrefetchesTiles() {
    // Default value
    assertTrue(new VietMapOptions().getPrefetchesTiles());

    // Check mutations
    assertTrue(new VietMapOptions().setPrefetchesTiles(true).getPrefetchesTiles());
    assertFalse(new VietMapOptions().setPrefetchesTiles(false).getPrefetchesTiles());
  }

  @Test
  public void testPrefetchZoomDelta() {
    // Default value
    assertEquals(4, new VietMapOptions().getPrefetchZoomDelta());

    // Check mutations
    assertEquals(5, new VietMapOptions().setPrefetchZoomDelta(5).getPrefetchZoomDelta());
  }


  @Test
  public void testCrossSourceCollisions() {
    // Default value
    assertTrue(new VietMapOptions().getCrossSourceCollisions());

    // check mutations
    assertTrue(new VietMapOptions().crossSourceCollisions(true).getCrossSourceCollisions());
    assertFalse(new VietMapOptions().crossSourceCollisions(false).getCrossSourceCollisions());
  }

  @Test
  public void testLocalIdeographFontFamily_enabledByDefault() {
    VietMapOptions options = VietMapOptions.createFromAttributes(RuntimeEnvironment.application, null);
    assertEquals(VietmapConstants.DEFAULT_FONT, options.getLocalIdeographFontFamily());
  }
}

