package vn.vietmap.vietmapsdk.testapp.activity.style

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
// import com.mapbox.vietmapsdk.maps.* //

import vn.vietmap.vietmapsdk.style.sources.GeoJsonSource
import vn.vietmap.vietmapsdk.testapp.R
import timber.log.Timber
import java.net.URI
import java.net.URISyntaxException

/**
 * Test activity showcasing using realtime GeoJSON to move a symbol on your map
 *
 *
 * GL-native equivalent of https://maplibre.org/maplibre-gl-js-docs/example/live-geojson/
 *
 */
class RealTimeGeoJsonActivity : AppCompatActivity(),
    vn.vietmap.vietmapsdk.maps.OnMapReadyCallback {
    private var mapView: vn.vietmap.vietmapsdk.maps.MapView? = null
    private var mapboxMap: vn.vietmap.vietmapsdk.maps.VietmapMap? = null
    private var handler: Handler? = null
    private var runnable: Runnable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_default)
        mapView = findViewById<View>(R.id.mapView) as vn.vietmap.vietmapsdk.maps.MapView
        mapView!!.onCreate(savedInstanceState)
        mapView!!.getMapAsync(this)
    }

    override fun onMapReady(map: vn.vietmap.vietmapsdk.maps.VietmapMap) {
        mapboxMap = map
        mapboxMap!!.setStyle(vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Streets")) { style -> // add source
            try {
                style.addSource(
                    vn.vietmap.vietmapsdk.style.sources.GeoJsonSource(
                        ID_GEOJSON_SOURCE,
                        URI(URL_GEOJSON_SOURCE)
                    )
                )
            } catch (malformedUriException: URISyntaxException) {
                Timber.e(malformedUriException, "Invalid URL")
            }

            // add layer
            val layer = vn.vietmap.vietmapsdk.style.layers.SymbolLayer(
                ID_GEOJSON_LAYER,
                ID_GEOJSON_SOURCE
            )
            layer.setProperties(vn.vietmap.vietmapsdk.style.layers.PropertyFactory.iconImage("rocket-15"))
            style.addLayer(layer)

            // loop refresh geojson
            handler = Handler()
            runnable = RefreshGeoJsonRunnable(mapboxMap!!, handler!!)
            handler!!.postDelayed(runnable as RefreshGeoJsonRunnable, 2000)
        }
    }

    override fun onStart() {
        super.onStart()
        mapView!!.onStart()
    }

    public override fun onResume() {
        super.onResume()
        mapView!!.onResume()
    }

    public override fun onPause() {
        super.onPause()
        mapView!!.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView!!.onStop()
        runnable?.let { handler!!.removeCallbacks(it) }
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView!!.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView!!.onSaveInstanceState(outState)
    }

    private class RefreshGeoJsonRunnable internal constructor(
        private val mapboxMap: vn.vietmap.vietmapsdk.maps.VietmapMap,
        private val handler: Handler
    ) : Runnable {
        override fun run() {
            (
                mapboxMap.style!!
                    .getSource(ID_GEOJSON_SOURCE) as vn.vietmap.vietmapsdk.style.sources.GeoJsonSource?
                )!!.url =
                URL_GEOJSON_SOURCE
            handler.postDelayed(this, 2000)
        }
    }

    companion object {
        private const val ID_GEOJSON_LAYER = "wanderdrone"
        private const val ID_GEOJSON_SOURCE = ID_GEOJSON_LAYER
        private const val URL_GEOJSON_SOURCE = "https://wanderdrone.appspot.com/"
    }
}
