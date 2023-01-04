package vn.vietmap.vietmapsdk.testapp.activity.imagegenerator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import vn.vietmap.vietmapsdk.log.Logger
import vn.vietmap.vietmapsdk.maps.MapView
import vn.vietmap.vietmapsdk.maps.VietmapMap
import vn.vietmap.vietmapsdk.maps.OnMapReadyCallback
import vn.vietmap.vietmapsdk.maps.Style
import vn.vietmap.vietmapsdk.testapp.R
import kotlinx.android.synthetic.main.activity_snapshot.*
import timber.log.Timber

/**
 * Test activity showcasing the Snapshot API to create and display a bitmap of the current shown Map.
 */
class SnapshotActivity : AppCompatActivity(),
    vn.vietmap.vietmapsdk.maps.OnMapReadyCallback {

    private lateinit var VietmapMap: vn.vietmap.vietmapsdk.maps.VietmapMap

    private val idleListener = object : vn.vietmap.vietmapsdk.maps.MapView.OnDidFinishRenderingFrameListener {
        override fun onDidFinishRenderingFrame(fully: Boolean) {
            if (fully) {
                mapView.removeOnDidFinishRenderingFrameListener(this)
                vn.vietmap.vietmapsdk.log.Logger.v(TAG, LOG_MESSAGE)
                VietmapMap.snapshot { snapshot ->
                    imageView.setImageBitmap(snapshot)
                    mapView.addOnDidFinishRenderingFrameListener(this)
                }
            }
        }
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_snapshot)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
    }

    override fun onMapReady(map: vn.vietmap.vietmapsdk.maps.VietmapMap) {
        VietmapMap = map
        VietmapMap.setStyle(
            vn.vietmap.vietmapsdk.maps.Style.Builder().fromUri(
                vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Outdoor"))) { mapView.addOnDidFinishRenderingFrameListener(idleListener) }
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        VietmapMap.snapshot {
            Timber.e("Regression test for https://github.com/mapbox/mapbox-gl-native/pull/11358")
        }
        mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    public override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    public override fun onDestroy() {
        super.onDestroy()
        mapView.removeOnDidFinishRenderingFrameListener(idleListener)
        mapView.onDestroy()
    }

    companion object {
        const val TAG = "Mbgl-SnapshotActivity"
        const val LOG_MESSAGE = "OnSnapshot"
    }
}
