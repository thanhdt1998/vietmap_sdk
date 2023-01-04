package vn.vietmap.vietmapsdk.testapp.activity.snapshot

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import vn.vietmap.vietmapsdk.camera.CameraPosition
import vn.vietmap.vietmapsdk.geometry.LatLng
import vn.vietmap.vietmapsdk.maps.Style
import vn.vietmap.vietmapsdk.snapshotter.MapSnapshot
import vn.vietmap.vietmapsdk.snapshotter.MapSnapshotter
import vn.vietmap.vietmapsdk.style.expressions.Expression
import vn.vietmap.vietmapsdk.style.layers.HeatmapLayer
import vn.vietmap.vietmapsdk.style.layers.PropertyFactory
import vn.vietmap.vietmapsdk.style.sources.GeoJsonSource
import vn.vietmap.vietmapsdk.style.sources.Source
import vn.vietmap.vietmapsdk.testapp.R
import timber.log.Timber
import java.net.URI
import java.net.URISyntaxException

/**
 * Test activity showing how to use a the [MapSnapshotter] and heatmap layer on it.
 */
class MapSnapshotterHeatMapActivity : AppCompatActivity(), vn.vietmap.vietmapsdk.snapshotter.MapSnapshotter.SnapshotReadyCallback {
    private var mapSnapshotter: vn.vietmap.vietmapsdk.snapshotter.MapSnapshotter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_snapshotter_marker)
        val container = findViewById<View>(R.id.container)
        container.viewTreeObserver
            .addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    container.viewTreeObserver.removeGlobalOnLayoutListener(this)
                    Timber.i("Starting snapshot")
                    val builder = vn.vietmap.vietmapsdk.maps.Style.Builder().fromUri(
                        vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Outdoor"))
                        .withSource(earthquakeSource!!)
                        .withLayerAbove(heatmapLayer, "water_intermittent")
                    mapSnapshotter =
                        vn.vietmap.vietmapsdk.snapshotter.MapSnapshotter(
                            applicationContext,
                            vn.vietmap.vietmapsdk.snapshotter.MapSnapshotter.Options(
                                container.measuredWidth,
                                container.measuredHeight
                            )
                                .withStyleBuilder(builder)
                                .withCameraPosition(
                                    vn.vietmap.vietmapsdk.camera.CameraPosition.Builder()
                                        .target(LatLng(15.0, (-94).toDouble()))
                                        .zoom(5.0)
                                        .padding(1.0, 1.0, 1.0, 1.0)
                                        .build()
                                )
                        )
                    mapSnapshotter!!.start(this@MapSnapshotterHeatMapActivity)
                }
            })
    }

    // Color ramp for heatmap.  Domain is 0 (low) to 1 (high).
    // Begin color ramp at 0-stop with a 0-transparency color
    // to create a blur-like effect.
    // Increase the heatmap weight based on frequency and property magnitude
    // Increase the heatmap color weight weight by zoom level
    // heatmap-intensity is a multiplier on top of heatmap-weight
    // Adjust the heatmap radius by zoom level
    // Transition from heatmap to circle layer by zoom level
    private val heatmapLayer: vn.vietmap.vietmapsdk.style.layers.HeatmapLayer
        private get() {
            val layer = vn.vietmap.vietmapsdk.style.layers.HeatmapLayer(
                HEATMAP_LAYER_ID,
                EARTHQUAKE_SOURCE_ID
            )
            layer.maxZoom = 9f
            layer.sourceLayer = HEATMAP_LAYER_SOURCE
            layer.setProperties( // Color ramp for heatmap.  Domain is 0 (low) to 1 (high).
                // Begin color ramp at 0-stop with a 0-transparency color
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

    override fun onStop() {
        super.onStop()
        mapSnapshotter!!.cancel()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onSnapshotReady(snapshot: vn.vietmap.vietmapsdk.snapshotter.MapSnapshot) {
        Timber.i("Snapshot ready")
        val imageView = findViewById<ImageView>(R.id.snapshot_image)
        imageView.setImageBitmap(snapshot.bitmap)
    }

    private val earthquakeSource: vn.vietmap.vietmapsdk.style.sources.Source?
        private get() {
            var source: vn.vietmap.vietmapsdk.style.sources.Source? = null
            try {
                source = vn.vietmap.vietmapsdk.style.sources.GeoJsonSource(
                    EARTHQUAKE_SOURCE_ID,
                    URI(EARTHQUAKE_SOURCE_URL)
                )
            } catch (uriSyntaxException: URISyntaxException) {
                Timber.e(uriSyntaxException, "That's not an url... ")
            }
            return source
        }

    companion object {
        private const val EARTHQUAKE_SOURCE_URL =
            "https://www.mapbox.com/mapbox-gl-js/assets/earthquakes.geojson"
        private const val EARTHQUAKE_SOURCE_ID = "earthquakes"
        private const val HEATMAP_LAYER_ID = "earthquakes-heat"
        private const val HEATMAP_LAYER_SOURCE = "earthquakes"
    }
}
