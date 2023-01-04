package vn.vietmap.vietmapsdk.testapp.activity.style

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
// import com.mapbox.vietmapsdk.maps.* //
import vn.vietmap.vietmapsdk.style.layers.HillshadeLayer
import vn.vietmap.vietmapsdk.style.sources.RasterDemSource
import vn.vietmap.vietmapsdk.testapp.R

/**
 * Test activity showcasing using HillshadeLayer.
 */
class HillshadeLayerActivity : AppCompatActivity() {
    private lateinit var mapView: vn.vietmap.vietmapsdk.maps.MapView
    private var mapboxMap: vn.vietmap.vietmapsdk.maps.VietmapMap? = null
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_extrusion_layer)
        mapView = findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(
            vn.vietmap.vietmapsdk.maps.OnMapReadyCallback { map: vn.vietmap.vietmapsdk.maps.VietmapMap? ->
                mapboxMap = map
                val rasterDemSource =
                    vn.vietmap.vietmapsdk.style.sources.RasterDemSource(
                        SOURCE_ID,
                        SOURCE_URL
                    )
                val hillshadeLayer =
                    vn.vietmap.vietmapsdk.style.layers.HillshadeLayer(
                        LAYER_ID,
                        SOURCE_ID
                    )
                mapboxMap!!.setStyle(
                    vn.vietmap.vietmapsdk.maps.Style.Builder()
                        .fromUri(
                            vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle(
                                "Streets"
                            )
                        )
                        .withLayerBelow(hillshadeLayer, LAYER_BELOW_ID)
                        .withSource(rasterDemSource)
                )
            }
        )
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

    public override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView!!.onSaveInstanceState(outState)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView!!.onLowMemory()
    }

    public override fun onDestroy() {
        super.onDestroy()
        mapView!!.onDestroy()
    }

    companion object {
        private const val LAYER_ID = "hillshade"
        private const val LAYER_BELOW_ID = "water_intermittent"
        private const val SOURCE_ID = "terrain-rgb"
        private const val SOURCE_URL = "maptiler://sources/terrain-rgb"
    }
}
