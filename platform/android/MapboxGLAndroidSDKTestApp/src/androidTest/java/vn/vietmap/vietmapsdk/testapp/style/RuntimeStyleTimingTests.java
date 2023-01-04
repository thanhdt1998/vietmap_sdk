package vn.vietmap.vietmapsdk.testapp.style;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import vn.vietmap.vietmapsdk.testapp.activity.BaseTest;
import com.mapbox.vietmapsdk.testapp.activity.style.RuntimeStyleTimingTestActivity;
import vn.vietmap.vietmapsdk.testapp.activity.BaseTest;

import org.junit.Test;
import org.junit.runner.RunWith;

import vn.vietmap.vietmapsdk.testapp.activity.BaseTest;

/**
 * Basic smoke tests for adding Layer and Source as early as possible (in onCreate)
 */
@RunWith(AndroidJUnit4.class)
public class RuntimeStyleTimingTests extends BaseTest {

  @Override
  protected Class getActivityClass() {
    return RuntimeStyleTimingTestActivity.class;
  }

  @Test
  public void testGetAddRemoveLayer() {
    validateTestSetup();
    // We're good if it didn't crash
  }
}
