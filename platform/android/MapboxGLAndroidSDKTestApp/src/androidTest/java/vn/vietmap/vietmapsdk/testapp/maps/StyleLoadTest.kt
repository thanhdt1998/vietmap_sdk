package vn.vietmap.vietmapsdk.testapp.maps

import androidx.test.espresso.UiController
import androidx.test.ext.junit.runners.AndroidJUnit4
import vn.vietmap.vietmapsdk.maps.MapboxMap
import vn.vietmap.vietmapsdk.maps.Style
import vn.vietmap.vietmapsdk.style.layers.SymbolLayer
import vn.vietmap.vietmapsdk.style.sources.GeoJsonSource
import vn.vietmap.vietmapsdk.testapp.action.MapboxMapAction
import vn.vietmap.vietmapsdk.testapp.activity.EspressoTest
import com.mapbox.vietmapsdk.testapp.utils.TestingAsyncUtils
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class StyleLoadTest : vn.vietmap.vietmapsdk.testapp.activity.EspressoTest() {

    @Test
    fun updateSourceAfterStyleLoad() {
        validateTestSetup()
        vn.vietmap.vietmapsdk.testapp.action.MapboxMapAction.invoke(mapboxMap) { uiController: UiController, mapboxMap: vn.vietmap.vietmapsdk.maps.MapboxMap ->
            val source = vn.vietmap.vietmapsdk.style.sources.GeoJsonSource("id")
            val layer = vn.vietmap.vietmapsdk.style.layers.SymbolLayer("id", "id")
            mapboxMap.setStyle(vn.vietmap.vietmapsdk.maps.Style.Builder().withSource(source).withLayer(layer))
            TestingAsyncUtils.waitForLayer(uiController, mapView)
            mapboxMap.setStyle(vn.vietmap.vietmapsdk.maps.Style.Builder().fromUrl(vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Streets")))
            TestingAsyncUtils.waitForLayer(uiController, mapView)
            source.setGeoJson("{}")
        }
    }
}
