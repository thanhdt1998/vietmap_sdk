package vn.vietmap.vietmapsdk.utils;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import vn.vietmap.vietmapsdk.constants.VietmapConstants;
import vn.vietmap.vietmapsdk.constants.VietmapConstants;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import vn.vietmap.vietmapsdk.constants.VietmapConstants;

@RunWith(AndroidJUnit4.class)
public class FontUtilsTest {

  @Test
  public void testExtractedFontShouldMatchDefault() {
    String[] fonts = new String[] {"foo", "bar"};
    String actual = FontUtils.extractValidFont(fonts);
    Assert.assertEquals("Selected font should match", VietmapConstants.DEFAULT_FONT, actual);
  }

  @Test
  public void testExtractedFontShouldMatchMonospace() {
    String expected = "monospace";
    String[] fonts = new String[] {"foo", expected};
    String actual = FontUtils.extractValidFont(fonts);
    assertEquals("Selected font should match", expected, actual);
  }

  @Test
  public void testExtractedFontArrayShouldBeNull() {
    String[] fonts = null;
    String actual = FontUtils.extractValidFont(fonts);
    assertNull(actual);
  }

  @Test
  public void testExtractedFontShouldBeNull() {
    String actual = FontUtils.extractValidFont(null);
    assertNull(actual);
  }
}
