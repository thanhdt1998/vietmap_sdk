package vn.vietmap.vietmapsdk.testapp.string;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import vn.vietmap.vietmapsdk.testapp.activity.EspressoTest;
import vn.vietmap.vietmapsdk.testapp.activity.EspressoTest;

import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;

import vn.vietmap.vietmapsdk.testapp.activity.EspressoTest;

/**
 * Test verifying if String#toUpperCase and String#toLowerCase produces desired results
 * <p>
 * See core test in https://github.com/mapbox/mapbox-gl-native/blob/master/test/util/text_conversions.test.cpp
 * </p>
 */
@RunWith(AndroidJUnit4.class)
public class UppperLowerCaseTest extends EspressoTest {

  @Test
  public void testToUpperCase() {
    assertEquals("STREET", "strEEt".toUpperCase());  // EN
    assertEquals("ROAD", "rOAd".toUpperCase());      // EN

    assertEquals("STRASSE", "straße".toUpperCase()); // DE
    assertEquals("MASSE", "maße".toUpperCase());     // DE
    assertEquals("WEISSKOPFSEEADLER", "weißkopfseeadler".toUpperCase()); // DE

    assertEquals("BÊNÇÃO", "bênção".toUpperCase()); // PT
    assertEquals("AZƏRBAYCAN", "Azərbaycan".toUpperCase()); // AZ
    assertEquals("ὈΔΥΣΣΕΎΣ", "Ὀδυσσεύς".toUpperCase()); // GR
  }

  @Test
  public void testToLowerCase() {
    assertEquals("street", "strEEt".toLowerCase());  // EN
    assertEquals("road", "rOAd".toLowerCase());      // EN

    assertEquals("straße", "Straße".toLowerCase());   // DE
    assertEquals("strasse", "STRASSE".toLowerCase()); // DE
    assertEquals("masse", "MASSE".toLowerCase());     // DE
    assertEquals("weisskopfseeadler", "weiSSkopfseeadler".toLowerCase()); // DE

    assertEquals("bênção", "BÊNÇÃO".toLowerCase()); // PT
    assertEquals("azərbaycan", "AZƏRBAYCAN".toLowerCase()); //
  }

}
