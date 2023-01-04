package vn.vietmap.vietmapsdk.testapp.activity.textureview

import vn.vietmap.vietmapsdk.maps.VietMapOptions
import vn.vietmap.vietmapsdk.maps.OnMapReadyCallback
import vn.vietmap.vietmapsdk.testapp.activity.maplayout.DebugModeActivity
import vn.vietmap.vietmapsdk.testapp.utils.NavUtils

/**
 * Test activity showcasing the different debug modes and allows to cycle between the default map styles.
 */
class TextureViewDebugModeActivity : DebugModeActivity(),
    vn.vietmap.vietmapsdk.maps.OnMapReadyCallback {
    override fun setupVietmapMapOptions(): vn.vietmap.vietmapsdk.maps.VietMapOptions {
        val mapboxMapOptions = super.setupVietmapMapOptions()
        mapboxMapOptions.textureMode(true)
        return mapboxMapOptions
    }

    override fun onBackPressed() {
        // activity uses singleInstance for testing purposes
        // code below provides a default navigation when using the app
        vn.vietmap.vietmapsdk.testapp.utils.NavUtils.navigateHome(this)
    }
}
