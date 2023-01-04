package vn.vietmap.vietmapsdk.testapp.activity.maplayout

import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import vn.vietmap.vietmapsdk.annotations.PolygonOptions
import vn.vietmap.vietmapsdk.geometry.LatLng
import vn.vietmap.vietmapsdk.geometry.LatLngBounds
// import com.mapbox.vietmapsdk.maps.* //
import vn.vietmap.vietmapsdk.testapp.R

/**
 * Test activity showcasing restricting user gestures to a bounds around Iceland, almost worldview and IDL.
 */
class LatLngBoundsForCameraActivity : AppCompatActivity(),
    vn.vietmap.vietmapsdk.maps.OnMapReadyCallback {
    private lateinit var mapView: vn.vietmap.vietmapsdk.maps.MapView
    private var mapboxMap: vn.vietmap.vietmapsdk.maps.VietmapMap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restricted_bounds)
        mapView = findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
    }

    override fun onMapReady(mapboxMap: vn.vietmap.vietmapsdk.maps.VietmapMap) {
        this.mapboxMap = mapboxMap
        mapboxMap.setStyle(vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Satellite Hybrid"))
        mapboxMap.setMinZoomPreference(2.0)
        mapboxMap.uiSettings.isFlingVelocityAnimationEnabled = false
        showCrosshair()
        setupBounds(ICELAND_BOUNDS)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_bounds, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_action_allmost_world_bounds -> {
                setupBounds(ALMOST_WORLD_BOUNDS)
                return true
            }
            R.id.menu_action_cross_idl -> {
                setupBounds(CROSS_IDL_BOUNDS)
                return true
            }
            R.id.menu_action_reset -> {
                setupBounds(null)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupBounds(bounds: vn.vietmap.vietmapsdk.geometry.LatLngBounds?) {
        mapboxMap!!.setLatLngBoundsForCameraTarget(bounds)
        showBoundsArea(bounds)
    }

    private fun showBoundsArea(bounds: vn.vietmap.vietmapsdk.geometry.LatLngBounds?) {
        mapboxMap!!.clear()
        if (bounds != null) {
            val boundsArea = vn.vietmap.vietmapsdk.annotations.PolygonOptions()
                .add(bounds.northWest)
                .add(bounds.northEast)
                .add(bounds.southEast)
                .add(bounds.southWest)
            boundsArea.alpha(0.25f)
            boundsArea.fillColor(Color.RED)
            mapboxMap!!.addPolygon(boundsArea)
        }
    }

    private fun showCrosshair() {
        val crosshair = View(this)
        crosshair.layoutParams = FrameLayout.LayoutParams(10, 10, Gravity.CENTER)
        crosshair.setBackgroundColor(Color.BLUE)
        mapView!!.addView(crosshair)
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
        mapView!!.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView!!.onLowMemory()
    }

    companion object {
        private val ICELAND_BOUNDS = vn.vietmap.vietmapsdk.geometry.LatLngBounds.Builder()
            .include(
                vn.vietmap.vietmapsdk.geometry.LatLng(
                    66.852863,
                    -25.985652
                )
            )
            .include(
                vn.vietmap.vietmapsdk.geometry.LatLng(
                    62.985661,
                    -12.626277
                )
            )
            .build()
        private val ALMOST_WORLD_BOUNDS = vn.vietmap.vietmapsdk.geometry.LatLngBounds.Builder()
            .include(vn.vietmap.vietmapsdk.geometry.LatLng(20.0, 170.0))
            .include(
                vn.vietmap.vietmapsdk.geometry.LatLng(
                    (-20).toDouble(),
                    -170.0
                )
            )
            .build()
        private val CROSS_IDL_BOUNDS = vn.vietmap.vietmapsdk.geometry.LatLngBounds.Builder()
            .include(vn.vietmap.vietmapsdk.geometry.LatLng(20.0, 170.0))
            .include(
                vn.vietmap.vietmapsdk.geometry.LatLng(
                    (-20).toDouble(),
                    190.0
                )
            )
            .build()
    }
}
