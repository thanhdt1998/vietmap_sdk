package vn.vietmap.vietmapsdk.testapp.activity.infowindow

import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import vn.vietmap.vietmapsdk.annotations.Marker
import vn.vietmap.vietmapsdk.annotations.MarkerOptions
import vn.vietmap.vietmapsdk.camera.CameraUpdateFactory
import vn.vietmap.vietmapsdk.geometry.LatLng
import vn.vietmap.vietmapsdk.maps.MapView
import vn.vietmap.vietmapsdk.maps.VietmapMap
import vn.vietmap.vietmapsdk.maps.VietmapMap.InfoWindowAdapter
import vn.vietmap.vietmapsdk.maps.VietmapMap.OnMapClickListener
import vn.vietmap.vietmapsdk.maps.OnMapReadyCallback
import vn.vietmap.vietmapsdk.maps.Style
import vn.vietmap.vietmapsdk.testapp.R
import vn.vietmap.vietmapsdk.testapp.utils.IconUtils
import java.util.*

/**
 * Test activity showcasing how to dynamically update InfoWindow when Using an VietmapMap.InfoWindowAdapter.
 */
class DynamicInfoWindowAdapterActivity : AppCompatActivity(),
    vn.vietmap.vietmapsdk.maps.OnMapReadyCallback {
    private lateinit var mapboxMap: vn.vietmap.vietmapsdk.maps.VietmapMap
    private lateinit var mapView: vn.vietmap.vietmapsdk.maps.MapView
    private var marker: vn.vietmap.vietmapsdk.annotations.Marker? = null
    private val mapClickListener = OnMapClickListener { point ->
        if (marker == null) {
            return@OnMapClickListener false
        }

        // Distance from click to marker
        val distanceKm = marker!!.position.distanceTo(point) / 1000

        // Get the info window
        val infoWindow = marker!!.infoWindow

        // Get the view from the info window
        if (infoWindow != null && infoWindow.view != null) {
            // Set the new text on the text view in the info window
            val textView = infoWindow.view as TextView?
            textView!!.text = String.format(Locale.getDefault(), "%.2fkm", distanceKm)
            // Update the info window position (as the text length changes)
            textView.post { infoWindow.update() }
        }
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_infowindow_adapter)
        mapView = findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
    }

    override fun onMapReady(map: vn.vietmap.vietmapsdk.maps.VietmapMap) {
        mapboxMap = map
        map.setStyle(vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Streets"))

        // Add info window adapter
        addCustomInfoWindowAdapter(mapboxMap!!)

        // Keep info windows open on click
        mapboxMap!!.uiSettings.isDeselectMarkersOnTap = false

        // Add a marker
        marker = addMarker(mapboxMap!!)
        mapboxMap!!.selectMarker(marker!!)

        // On map click, change the info window contents
        mapboxMap!!.addOnMapClickListener(mapClickListener)

        // Focus on Paris
        mapboxMap!!.animateCamera(vn.vietmap.vietmapsdk.camera.CameraUpdateFactory.newLatLng(PARIS))
    }

    private fun addMarker(mapboxMap: vn.vietmap.vietmapsdk.maps.VietmapMap): vn.vietmap.vietmapsdk.annotations.Marker {
        return mapboxMap.addMarker(
            vn.vietmap.vietmapsdk.annotations.MarkerOptions()
                .position(PARIS)
                .icon(
                    vn.vietmap.vietmapsdk.testapp.utils.IconUtils.drawableToIcon(
                        this,
                        R.drawable.ic_location_city,
                        ResourcesCompat.getColor(resources, R.color.mapbox_blue, theme)
                    )
                )
        )
    }

    private fun addCustomInfoWindowAdapter(mapboxMap: vn.vietmap.vietmapsdk.maps.VietmapMap) {
        val padding = resources.getDimension(R.dimen.attr_margin).toInt()
        mapboxMap.infoWindowAdapter = InfoWindowAdapter { marker: vn.vietmap.vietmapsdk.annotations.Marker ->
            val textView = TextView(this@DynamicInfoWindowAdapterActivity)
            textView.text = marker.title
            textView.setBackgroundColor(Color.WHITE)
            textView.setText(R.string.action_calculate_distance)
            textView.setPadding(padding, padding, padding, padding)
            textView
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
        if (mapboxMap != null) {
            mapboxMap!!.removeOnMapClickListener(mapClickListener)
        }
        mapView!!.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView!!.onLowMemory()
    }

    companion object {
        private val PARIS =
            vn.vietmap.vietmapsdk.geometry.LatLng(48.864716, 2.349014)
    }
}
