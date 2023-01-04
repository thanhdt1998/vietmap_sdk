package vn.vietmap.vietmapsdk.testapp.activity.style

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import vn.vietmap.vietmapsdk.maps.MapView
import vn.vietmap.vietmapsdk.maps.VietmapMap
import vn.vietmap.vietmapsdk.maps.OnMapReadyCallback
import vn.vietmap.vietmapsdk.maps.Style
import vn.vietmap.vietmapsdk.style.expressions.Expression

import vn.vietmap.vietmapsdk.style.sources.GeoJsonSource
import vn.vietmap.vietmapsdk.testapp.R
import timber.log.Timber
import java.net.URI
import java.net.URISyntaxException

/**
 * Test activity showcasing the heatmap layer api.
 */
class HeatmapLayerActivity : AppCompatActivity() {
    private lateinit var mapView: vn.vietmap.vietmapsdk.maps.MapView
    private var mapboxMap: vn.vietmap.vietmapsdk.maps.VietmapMap? = null
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_heatmaplayer)
        mapView = findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(
            vn.vietmap.vietmapsdk.maps.OnMapReadyCallback { map: vn.vietmap.vietmapsdk.maps.VietmapMap? ->
                mapboxMap = map
                try {
                    mapboxMap!!.setStyle(
                        vn.vietmap.vietmapsdk.maps.Style.Builder()
                            .fromUri(
                                vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle(
                                    "Pastel"
                                )
                            )
                            .withSource(createEarthquakeSource())
                            .withLayerAbove(createHeatmapLayer(), "country_label")
                            .withLayerBelow(createCircleLayer(), HEATMAP_LAYER_ID)
                    )
                } catch (exception: URISyntaxException) {
                    Timber.e(exception)
                }
            }
        )
    }

    private fun createEarthquakeSource(): vn.vietmap.vietmapsdk.style.sources.GeoJsonSource {
        return vn.vietmap.vietmapsdk.style.sources.GeoJsonSource(
            EARTHQUAKE_SOURCE_ID,
            URI(EARTHQUAKE_SOURCE_URL)
        )
    }

    private fun createHeatmapLayer(): vn.vietmap.vietmapsdk.style.layers.HeatmapLayer {
        val layer = vn.vietmap.vietmapsdk.style.layers.HeatmapLayer(
            HEATMAP_LAYER_ID,
            EARTHQUAKE_SOURCE_ID
        )
        layer.maxZoom = 9f
        layer.setSourceLayer(HEATMAP_LAYER_SOURCE)
        layer.setProperties( // Color ramp for heatmap.  Domain is 0 (low) to 1 (high).
            // Begin color ramp at 0-stop with a 0-transparancy color
            // to create a blur-like effect.
            vn.vietmap.vietmapsdk.style.layers.PropertyFactory.heatmapColor(
                vn.vietmap.vietmapsdk.style.expressions.Expression.interpolate(
                    vn.vietmap.vietmapsdk.style.expressions.Expression.linear(), vn.vietmap.vietmapsdk.style.expressions.Expression.heatmapDensity(),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.literal(0), vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(33, 102, 172, 0),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.literal(0.2), vn.vietmap.vietmapsdk.style.expressions.Expression.rgb(103, 169, 207),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.literal(0.4), vn.vietmap.vietmapsdk.style.expressions.Expression.rgb(209, 229, 240),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.literal(0.6), vn.vietmap.vietmapsdk.style.expressions.Expression.rgb(253, 219, 199),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.literal(0.8), vn.vietmap.vietmapsdk.style.expressions.Expression.rgb(239, 138, 98),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.literal(1), vn.vietmap.vietmapsdk.style.expressions.Expression.rgb(178, 24, 43)
                )
            ), // Increase the heatmap weight based on frequency and property magnitude
            vn.vietmap.vietmapsdk.style.layers.PropertyFactory.heatmapWeight(
                vn.vietmap.vietmapsdk.style.expressions.Expression.interpolate(
                    vn.vietmap.vietmapsdk.style.expressions.Expression.linear(),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.get("mag"),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.stop(0, 0),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.stop(6, 1)
                )
            ), // Increase the heatmap color weight weight by zoom level
            // heatmap-intensity is a multiplier on top of heatmap-weight
            vn.vietmap.vietmapsdk.style.layers.PropertyFactory.heatmapIntensity(
                vn.vietmap.vietmapsdk.style.expressions.Expression.interpolate(
                    vn.vietmap.vietmapsdk.style.expressions.Expression.linear(),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.zoom(),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.stop(0, 1),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.stop(9, 3)
                )
            ), // Adjust the heatmap radius by zoom level
            vn.vietmap.vietmapsdk.style.layers.PropertyFactory.heatmapRadius(
                vn.vietmap.vietmapsdk.style.expressions.Expression.interpolate(
                    vn.vietmap.vietmapsdk.style.expressions.Expression.linear(),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.zoom(),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.stop(0, 2),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.stop(9, 20)
                )
            ), // Transition from heatmap to circle layer by zoom level
            vn.vietmap.vietmapsdk.style.layers.PropertyFactory.heatmapOpacity(
                vn.vietmap.vietmapsdk.style.expressions.Expression.interpolate(
                    vn.vietmap.vietmapsdk.style.expressions.Expression.linear(),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.zoom(),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.stop(7, 1),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.stop(9, 0)
                )
            )
        )
        return layer
    }

    private fun createCircleLayer(): vn.vietmap.vietmapsdk.style.layers.CircleLayer {
        val circleLayer = vn.vietmap.vietmapsdk.style.layers.CircleLayer(
            CIRCLE_LAYER_ID,
            EARTHQUAKE_SOURCE_ID
        )
        circleLayer.setProperties( // Size circle radius by earthquake magnitude and zoom level
            vn.vietmap.vietmapsdk.style.layers.PropertyFactory.circleRadius(
                vn.vietmap.vietmapsdk.style.expressions.Expression.interpolate(
                    vn.vietmap.vietmapsdk.style.expressions.Expression.linear(),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.zoom(),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.literal(7),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.interpolate(
                        vn.vietmap.vietmapsdk.style.expressions.Expression.linear(),
                        vn.vietmap.vietmapsdk.style.expressions.Expression.get("mag"),
                        vn.vietmap.vietmapsdk.style.expressions.Expression.stop(1, 1),
                        vn.vietmap.vietmapsdk.style.expressions.Expression.stop(6, 4)
                    ),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.literal(16),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.interpolate(
                        vn.vietmap.vietmapsdk.style.expressions.Expression.linear(),
                        vn.vietmap.vietmapsdk.style.expressions.Expression.get("mag"),
                        vn.vietmap.vietmapsdk.style.expressions.Expression.stop(1, 5),
                        vn.vietmap.vietmapsdk.style.expressions.Expression.stop(6, 50)
                    )
                )
            ), // Color circle by earthquake magnitude
            vn.vietmap.vietmapsdk.style.layers.PropertyFactory.circleColor(
                vn.vietmap.vietmapsdk.style.expressions.Expression.interpolate(
                    vn.vietmap.vietmapsdk.style.expressions.Expression.linear(), vn.vietmap.vietmapsdk.style.expressions.Expression.get("mag"),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.literal(1), vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(33, 102, 172, 0),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.literal(2), vn.vietmap.vietmapsdk.style.expressions.Expression.rgb(103, 169, 207),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.literal(3), vn.vietmap.vietmapsdk.style.expressions.Expression.rgb(209, 229, 240),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.literal(4), vn.vietmap.vietmapsdk.style.expressions.Expression.rgb(253, 219, 199),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.literal(5), vn.vietmap.vietmapsdk.style.expressions.Expression.rgb(239, 138, 98),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.literal(6), vn.vietmap.vietmapsdk.style.expressions.Expression.rgb(178, 24, 43)
                )
            ), // Transition from heatmap to circle layer by zoom level
            vn.vietmap.vietmapsdk.style.layers.PropertyFactory.circleOpacity(
                vn.vietmap.vietmapsdk.style.expressions.Expression.interpolate(
                    vn.vietmap.vietmapsdk.style.expressions.Expression.linear(),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.zoom(),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.stop(7, 0),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.stop(8, 1)
                )
            ),
            vn.vietmap.vietmapsdk.style.layers.PropertyFactory.circleStrokeColor("white"),
            vn.vietmap.vietmapsdk.style.layers.PropertyFactory.circleStrokeWidth(1.0f)
        )
        return circleLayer
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
        private const val EARTHQUAKE_SOURCE_URL =
            "https://maplibre.org/maplibre-gl-js-docs/assets/earthquakes.geojson"
        private const val EARTHQUAKE_SOURCE_ID = "earthquakes"
        private const val HEATMAP_LAYER_ID = "earthquakes-heat"
        private const val HEATMAP_LAYER_SOURCE = "earthquakes"
        private const val CIRCLE_LAYER_ID = "earthquakes-circle"
    }
}
