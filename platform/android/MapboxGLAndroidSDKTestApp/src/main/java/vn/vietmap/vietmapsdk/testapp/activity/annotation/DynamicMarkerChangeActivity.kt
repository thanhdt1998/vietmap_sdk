package vn.vietmap.vietmapsdk.testapp.activity.annotation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import vn.vietmap.vietmapsdk.annotations.Marker
import vn.vietmap.vietmapsdk.annotations.MarkerOptions
import vn.vietmap.vietmapsdk.geometry.LatLng
import vn.vietmap.vietmapsdk.maps.MapView
import vn.vietmap.vietmapsdk.maps.VietmapMap
import vn.vietmap.vietmapsdk.maps.Style
import vn.vietmap.vietmapsdk.testapp.R
import vn.vietmap.vietmapsdk.testapp.utils.IconUtils

/**
 * Test activity showcasing updating a Marker position, title, icon and snippet.
 */
class DynamicMarkerChangeActivity : AppCompatActivity() {
    private lateinit var mapView: vn.vietmap.vietmapsdk.maps.MapView
    private lateinit var mapboxMap: vn.vietmap.vietmapsdk.maps.VietmapMap
    private var marker: vn.vietmap.vietmapsdk.annotations.Marker? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dynamic_marker)
        mapView = findViewById(R.id.mapView)
        mapView.setTag(false)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync { mapboxMap: vn.vietmap.vietmapsdk.maps.VietmapMap ->
            mapboxMap.setStyle(vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Streets"))
            this@DynamicMarkerChangeActivity.mapboxMap = mapboxMap
            // Create marker
            val markerOptions = vn.vietmap.vietmapsdk.annotations.MarkerOptions()
                .position(LAT_LNG_CHELSEA)
                .icon(
                    vn.vietmap.vietmapsdk.testapp.utils.IconUtils.drawableToIcon(
                        this@DynamicMarkerChangeActivity,
                        R.drawable.ic_stars,
                        ResourcesCompat.getColor(resources, R.color.blueAccent, theme)
                    )
                )
                .title(getString(R.string.dynamic_marker_chelsea_title))
                .snippet(getString(R.string.dynamic_marker_chelsea_snippet))
            marker = mapboxMap.addMarker(markerOptions)
//            marker=mapboxMap.addMarkers()
        }
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setColorFilter(ContextCompat.getColor(this, R.color.primary))
        fab.setOnClickListener { view: View? ->
            if (mapboxMap != null) {
                updateMarker()
            }
        }
    }

    private fun updateMarker() {
        // update model
        val first = mapView!!.tag as Boolean
        mapView!!.tag = !first

        // update marker
        marker!!.position =
            if (first) LAT_LNG_CHELSEA else LAT_LNG_ARSENAL
        marker!!.icon = vn.vietmap.vietmapsdk.testapp.utils.IconUtils.drawableToIcon(
            this,
            R.drawable.ic_stars,
            if (first) ResourcesCompat.getColor(
                resources,
                R.color.blueAccent,
                theme
            ) else ResourcesCompat.getColor(
                resources,
                R.color.redAccent,
                theme
            )
        )
        marker!!.title =
            if (first) getString(R.string.dynamic_marker_chelsea_title) else getString(R.string.dynamic_marker_arsenal_title)
        marker!!.snippet =
            if (first) getString(R.string.dynamic_marker_chelsea_snippet) else getString(R.string.dynamic_marker_arsenal_snippet)
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
        private val LAT_LNG_CHELSEA =
            vn.vietmap.vietmapsdk.geometry.LatLng(51.481670, -0.190849)
        private val LAT_LNG_ARSENAL =
            vn.vietmap.vietmapsdk.geometry.LatLng(51.555062, -0.108417)
    }
}
