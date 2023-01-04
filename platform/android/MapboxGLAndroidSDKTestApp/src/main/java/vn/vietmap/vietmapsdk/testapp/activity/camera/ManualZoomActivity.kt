package vn.vietmap.vietmapsdk.testapp.activity.camera

import android.graphics.Point
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import vn.vietmap.vietmapsdk.camera.CameraUpdateFactory
import vn.vietmap.vietmapsdk.maps.MapView
import vn.vietmap.vietmapsdk.maps.VietmapMap
import vn.vietmap.vietmapsdk.maps.Style
import vn.vietmap.vietmapsdk.testapp.R

/**
 * Test activity showcasing the zoom Camera API.
 *
 * This includes zoomIn, zoomOut, zoomTo, zoomBy (center and custom focal point).
 */
class ManualZoomActivity : AppCompatActivity() {
    private lateinit var mapboxMap: vn.vietmap.vietmapsdk.maps.VietmapMap
    private lateinit var mapView: vn.vietmap.vietmapsdk.maps.MapView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manual_zoom)
        mapView = findViewById<View>(R.id.mapView) as vn.vietmap.vietmapsdk.maps.MapView
        mapView!!.onCreate(savedInstanceState)
        mapView!!.getMapAsync { mapboxMap: vn.vietmap.vietmapsdk.maps.VietmapMap ->
            this@ManualZoomActivity.mapboxMap = mapboxMap
            mapboxMap.setStyle(
                vn.vietmap.vietmapsdk.maps.Style.Builder().fromUri(
                    vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Satellite Hybrid"))
            )
            val uiSettings = this@ManualZoomActivity.mapboxMap!!.uiSettings
            uiSettings.setAllGesturesEnabled(false)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_zoom, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_zoom_in -> {
                mapboxMap!!.animateCamera(vn.vietmap.vietmapsdk.camera.CameraUpdateFactory.zoomIn())
                true
            }
            R.id.action_zoom_out -> {
                mapboxMap!!.animateCamera(vn.vietmap.vietmapsdk.camera.CameraUpdateFactory.zoomOut())
                true
            }
            R.id.action_zoom_by -> {
                mapboxMap!!.animateCamera(vn.vietmap.vietmapsdk.camera.CameraUpdateFactory.zoomBy(2.0))
                true
            }
            R.id.action_zoom_to -> {
                mapboxMap!!.animateCamera(vn.vietmap.vietmapsdk.camera.CameraUpdateFactory.zoomTo(2.0))
                true
            }
            R.id.action_zoom_to_point -> {
                val view = window.decorView
                mapboxMap!!.animateCamera(
                    vn.vietmap.vietmapsdk.camera.CameraUpdateFactory.zoomBy(
                        1.0,
                        Point(view.measuredWidth / 4, view.measuredHeight / 4)
                    )
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
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
        mapView!!.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView!!.onLowMemory()
    }
}
