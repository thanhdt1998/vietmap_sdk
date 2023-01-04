package vn.vietmap.vietmapsdk.maps;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

@RunWith(RobolectricTestRunner.class)
public class AttributionDialogManagerTest {
  @InjectMocks
  Context context = mock(Context.class);

  @InjectMocks
  VietmapMap VietmapMap = mock(VietmapMap.class);

  @InjectMocks
  Style style = mock(Style.class);

  private AttributionDialogManager attributionDialogManager;

  @Before
  public void beforeTest() {
    attributionDialogManager = new AttributionDialogManager(context, VietmapMap);
  }

  @Test
  public void testSanity() {
    assertNotNull("AttributionDialogManager should not be null", attributionDialogManager);
  }
}
