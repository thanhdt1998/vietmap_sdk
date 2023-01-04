package vn.vietmap.vietmapsdk.testapp.activity.storage

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import vn.vietmap.vietmapsdk.maps.MapView
import vn.vietmap.vietmapsdk.maps.VietmapMap
import vn.vietmap.vietmapsdk.maps.Style
import vn.vietmap.vietmapsdk.storage.FileSource
import vn.vietmap.vietmapsdk.storage.FileSource.ResourceTransformCallback
import vn.vietmap.vietmapsdk.storage.Resource
import vn.vietmap.vietmapsdk.testapp.R
import timber.log.Timber

/**
 * Test activity showcasing the url transform
 */
class UrlTransformActivity : AppCompatActivity() {
    private var mapView: vn.vietmap.vietmapsdk.maps.MapView? = null

    /**
     * Be sure to use an isolated class so the activity is not leaked when
     * the activity goes out of scope (static class in this case).
     *
     *
     * Alternatively, unregister the callback in [Activity.onDestroy]
     */
    private class Transform : ResourceTransformCallback {
        override fun onURL(@vn.vietmap.vietmapsdk.storage.Resource.Kind kind: Int, url: String): String {
            Timber.i("[%s] Could be rewriting %s", Thread.currentThread().name, url)
            return url
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_driven_style)

        // Initialize map as normal
        mapView = findViewById<View>(R.id.mapView) as vn.vietmap.vietmapsdk.maps.MapView
        mapView!!.onCreate(savedInstanceState)

        // Get a handle to the file source and set the resource transform
        vn.vietmap.vietmapsdk.storage.FileSource.getInstance(this@UrlTransformActivity).setResourceTransform(Transform())
        mapView!!.getMapAsync { map: vn.vietmap.vietmapsdk.maps.VietmapMap ->
            Timber.i("Map loaded")
            map.setStyle(vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Streets"))
        }
    }

    override fun onStart() {
        super.onStart()
        mapView!!.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView!!.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView!!.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView!!.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView!!.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()

        // Example of how to reset the transform callback
        vn.vietmap.vietmapsdk.storage.FileSource.getInstance(this@UrlTransformActivity).setResourceTransform(null)
        mapView!!.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView!!.onLowMemory()
    }
}
