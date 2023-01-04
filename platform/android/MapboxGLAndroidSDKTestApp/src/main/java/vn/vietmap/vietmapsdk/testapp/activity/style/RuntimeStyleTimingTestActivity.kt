package vn.vietmap.vietmapsdk.testapp.activity.style

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
// import com.mapbox.vietmapsdk.maps.* //

import vn.vietmap.vietmapsdk.style.sources.VectorSource
import vn.vietmap.vietmapsdk.testapp.R

/**
 * Test activity for unit test execution
 */
class RuntimeStyleTimingTestActivity : AppCompatActivity() {
    var mapView: vn.vietmap.vietmapsdk.maps.MapView? = null
    var mapboxMap: vn.vietmap.vietmapsdk.maps.VietmapMap? = null
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_runtime_style)

        // Initialize map as normal
        mapView = findViewById<View>(R.id.mapView) as vn.vietmap.vietmapsdk.maps.MapView
        mapView!!.onCreate(savedInstanceState)
        mapView!!.getMapAsync { mapboxMap: vn.vietmap.vietmapsdk.maps.VietmapMap ->
            this@RuntimeStyleTimingTestActivity.mapboxMap = mapboxMap
            val parksLayer = vn.vietmap.vietmapsdk.style.layers.CircleLayer(
                "parks",
                "parks_source"
            )
            parksLayer.sourceLayer = "parks"
            parksLayer.setProperties(
                vn.vietmap.vietmapsdk.style.layers.PropertyFactory.visibility(
                    vn.vietmap.vietmapsdk.style.layers.Property.VISIBLE),
                vn.vietmap.vietmapsdk.style.layers.PropertyFactory.circleRadius(8f),
                vn.vietmap.vietmapsdk.style.layers.PropertyFactory.circleColor(Color.argb(1, 55, 148, 179))
            )
            val parks = vn.vietmap.vietmapsdk.style.sources.VectorSource(
                "parks_source",
                "maptiler://sources/7ac429c7-c96e-46dd-8c3e-13d48988986a"
            )
            mapboxMap.setStyle(
                vn.vietmap.vietmapsdk.maps.Style.Builder()
                    .fromUri(vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Streets"))
                    .withSource(parks)
                    .withLayer(parksLayer)
            )
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
