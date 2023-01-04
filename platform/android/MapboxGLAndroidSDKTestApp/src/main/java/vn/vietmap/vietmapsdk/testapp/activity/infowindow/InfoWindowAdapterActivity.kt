package vn.vietmap.vietmapsdk.testapp.activity.infowindow

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import vn.vietmap.vietmapsdk.annotations.Marker
import vn.vietmap.vietmapsdk.geometry.LatLng
import vn.vietmap.vietmapsdk.maps.MapView
import vn.vietmap.vietmapsdk.maps.VietmapMap
import vn.vietmap.vietmapsdk.maps.VietmapMap.InfoWindowAdapter
import vn.vietmap.vietmapsdk.maps.OnMapReadyCallback
import vn.vietmap.vietmapsdk.maps.Style
import vn.vietmap.vietmapsdk.testapp.R
import vn.vietmap.vietmapsdk.testapp.model.annotations.CityStateMarker
import vn.vietmap.vietmapsdk.testapp.model.annotations.CityStateMarkerOptions
import vn.vietmap.vietmapsdk.testapp.utils.IconUtils

/**
 * Test activity showcasing using an InfoWindowAdapter to provide a custom InfoWindow content.
 */
class InfoWindowAdapterActivity : AppCompatActivity() {
    private lateinit var mapView: vn.vietmap.vietmapsdk.maps.MapView
    private lateinit var mapboxMap: vn.vietmap.vietmapsdk.maps.VietmapMap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_infowindow_adapter)
        mapView = findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(
            vn.vietmap.vietmapsdk.maps.OnMapReadyCallback { map: vn.vietmap.vietmapsdk.maps.VietmapMap ->
                mapboxMap = map
                map.setStyle(
                    vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle(
                        "Streets"
                    )
                ) { style: vn.vietmap.vietmapsdk.maps.Style? ->
                    addMarkers()
                    addCustomInfoWindowAdapter()
                }
            }
        )
    }

    private fun addMarkers() {
        mapboxMap!!.addMarker(generateCityStateMarker("Andorra", 42.505777, 1.52529, "#F44336"))
        mapboxMap!!.addMarker(generateCityStateMarker("Luxembourg", 49.815273, 6.129583, "#3F51B5"))
        mapboxMap!!.addMarker(generateCityStateMarker("Monaco", 43.738418, 7.424616, "#673AB7"))
        mapboxMap!!.addMarker(
            generateCityStateMarker(
                "Vatican City",
                41.902916,
                12.453389,
                "#009688"
            )
        )
        mapboxMap!!.addMarker(
            generateCityStateMarker(
                "San Marino",
                43.942360,
                12.457777,
                "#795548"
            )
        )
        mapboxMap!!.addMarker(
            generateCityStateMarker(
                "Liechtenstein",
                47.166000,
                9.555373,
                "#FF5722"
            )
        )
    }

    private fun generateCityStateMarker(
        title: String,
        lat: Double,
        lng: Double,
        color: String
    ): vn.vietmap.vietmapsdk.testapp.model.annotations.CityStateMarkerOptions {
        val marker =
            vn.vietmap.vietmapsdk.testapp.model.annotations.CityStateMarkerOptions()
        marker.title(title)
        marker.position(vn.vietmap.vietmapsdk.geometry.LatLng(lat, lng))
        marker.infoWindowBackground(color)
        val icon =
            vn.vietmap.vietmapsdk.testapp.utils.IconUtils.drawableToIcon(this, R.drawable.ic_location_city, Color.parseColor(color))
        marker.icon(icon)
        return marker
    }

    private fun addCustomInfoWindowAdapter() {
        mapboxMap!!.infoWindowAdapter = object : InfoWindowAdapter {
            private val tenDp = resources.getDimension(R.dimen.attr_margin).toInt()
            override fun getInfoWindow(marker: vn.vietmap.vietmapsdk.annotations.Marker): View? {
                val textView = TextView(this@InfoWindowAdapterActivity)
                textView.text = marker.title
                textView.setTextColor(Color.WHITE)
                if (marker is vn.vietmap.vietmapsdk.testapp.model.annotations.CityStateMarker) {
                    textView.setBackgroundColor(Color.parseColor(marker.infoWindowBackgroundColor))
                }
                textView.setPadding(tenDp, tenDp, tenDp, tenDp)
                return textView
            }
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
