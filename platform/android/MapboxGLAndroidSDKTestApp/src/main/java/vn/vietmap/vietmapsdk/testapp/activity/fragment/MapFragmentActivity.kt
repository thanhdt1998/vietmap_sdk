package vn.vietmap.vietmapsdk.testapp.activity.fragment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import vn.vietmap.vietmapsdk.camera.CameraPosition
import vn.vietmap.vietmapsdk.camera.CameraUpdateFactory
import vn.vietmap.vietmapsdk.geometry.LatLng
// // import com.mapbox.vietmapsdk.maps.* // // ktlint-disable no-wildcard-imports
import vn.vietmap.vietmapsdk.maps.MapFragment.OnMapViewReadyCallback
import vn.vietmap.vietmapsdk.maps.MapView.OnDidFinishRenderingFrameListener
import vn.vietmap.vietmapsdk.testapp.R

/**
 * Test activity showcasing using the MapFragment API using SDK Fragments.
 *
 *
 * Uses VietmapMapOptions to initialise the Fragment.
 *
 */
class MapFragmentActivity :
    AppCompatActivity(),
    OnMapViewReadyCallback,
    vn.vietmap.vietmapsdk.maps.OnMapReadyCallback,
    OnDidFinishRenderingFrameListener {
    private var mapboxMap: vn.vietmap.vietmapsdk.maps.VietmapMap? = null
    private var mapView: vn.vietmap.vietmapsdk.maps.MapView? = null
    private var initialCameraAnimation = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_fragment)
        val mapFragment: vn.vietmap.vietmapsdk.maps.MapFragment
        if (savedInstanceState == null) {
            mapFragment = vn.vietmap.vietmapsdk.maps.MapFragment.newInstance(createFragmentOptions())
            fragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, mapFragment, TAG)
                .commit()
        } else {
            mapFragment = fragmentManager.findFragmentByTag(TAG) as vn.vietmap.vietmapsdk.maps.MapFragment
        }
        mapFragment.getMapAsync(this)
    }

    private fun createFragmentOptions(): vn.vietmap.vietmapsdk.maps.VietMapOptions {
        val options = vn.vietmap.vietmapsdk.maps.VietMapOptions.createFromAttributes(this, null)
        options.scrollGesturesEnabled(false)
        options.zoomGesturesEnabled(false)
        options.tiltGesturesEnabled(false)
        options.rotateGesturesEnabled(false)
        options.debugActive(false)
        val dc = vn.vietmap.vietmapsdk.geometry.LatLng(38.90252, -77.02291)
        options.minZoomPreference(9.0)
        options.maxZoomPreference(11.0)
        options.camera(
            vn.vietmap.vietmapsdk.camera.CameraPosition.Builder()
                .target(dc)
                .zoom(11.0)
                .build()
        )
        return options
    }

    override fun onMapViewReady(map: vn.vietmap.vietmapsdk.maps.MapView) {
        mapView = map
        mapView!!.addOnDidFinishRenderingFrameListener(this)
    }

    override fun onMapReady(map: vn.vietmap.vietmapsdk.maps.VietmapMap) {
        mapboxMap = map
        mapboxMap!!.setStyle(vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Outdoor"))
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mapView != null) {
            mapView!!.removeOnDidFinishRenderingFrameListener(this)
        }
    }

    override fun onDidFinishRenderingFrame(fully: Boolean) {
        if (initialCameraAnimation && fully && mapboxMap != null) {
            mapboxMap!!.animateCamera(
                vn.vietmap.vietmapsdk.camera.CameraUpdateFactory.newCameraPosition(
                    vn.vietmap.vietmapsdk.camera.CameraPosition.Builder().tilt(45.0).build()),
                5000
            )
            initialCameraAnimation = false
        }
    }

    companion object {
        private const val TAG = "com.mapbox.map"
    }
}
