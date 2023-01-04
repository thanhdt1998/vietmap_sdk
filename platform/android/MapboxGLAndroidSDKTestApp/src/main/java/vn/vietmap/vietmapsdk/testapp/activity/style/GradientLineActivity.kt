package vn.vietmap.vietmapsdk.testapp.activity.style

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import vn.vietmap.vietmapsdk.maps.MapView
import vn.vietmap.vietmapsdk.maps.VietmapMap
import vn.vietmap.vietmapsdk.maps.OnMapReadyCallback
import vn.vietmap.vietmapsdk.maps.Style
import vn.vietmap.vietmapsdk.style.expressions.Expression
import vn.vietmap.vietmapsdk.style.sources.GeoJsonOptions
import vn.vietmap.vietmapsdk.style.sources.GeoJsonSource
import vn.vietmap.vietmapsdk.testapp.R
import vn.vietmap.vietmapsdk.testapp.utils.ResourceUtils
import timber.log.Timber
import java.io.IOException

/**
 * Activity showcasing applying a gradient coloring to a line layer.
 */
class GradientLineActivity : AppCompatActivity(),
    vn.vietmap.vietmapsdk.maps.OnMapReadyCallback {
    private lateinit var mapView: vn.vietmap.vietmapsdk.maps.MapView
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gradient_line)
        mapView = findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
    }

    override fun onMapReady(mapboxMap: vn.vietmap.vietmapsdk.maps.VietmapMap) {
        try {
            val geoJson = ResourceUtils.readRawResource(
                this@GradientLineActivity,
                R.raw.test_line_gradient_feature
            )
            mapboxMap.setStyle(
                vn.vietmap.vietmapsdk.maps.Style.Builder()
                    .withSource(
                        vn.vietmap.vietmapsdk.style.sources.GeoJsonSource(
                            LINE_SOURCE,
                            geoJson,
                            vn.vietmap.vietmapsdk.style.sources.GeoJsonOptions()
                                .withLineMetrics(true)
                        )
                    )
                    .withLayer(
                        vn.vietmap.vietmapsdk.style.layers.LineLayer(
                            "gradient",
                            LINE_SOURCE
                        )
                            .withProperties(
                                vn.vietmap.vietmapsdk.style.layers.PropertyFactory.lineGradient(
                                    vn.vietmap.vietmapsdk.style.expressions.Expression.interpolate(
                                        vn.vietmap.vietmapsdk.style.expressions.Expression.linear(),
                                        vn.vietmap.vietmapsdk.style.expressions.Expression.lineProgress(),
                                        vn.vietmap.vietmapsdk.style.expressions.Expression.stop(0f, vn.vietmap.vietmapsdk.style.expressions.Expression.rgb(0, 0, 255)),
                                        vn.vietmap.vietmapsdk.style.expressions.Expression.stop(0.5f, vn.vietmap.vietmapsdk.style.expressions.Expression.rgb(0, 255, 0)),
                                        vn.vietmap.vietmapsdk.style.expressions.Expression.stop(1f, vn.vietmap.vietmapsdk.style.expressions.Expression.rgb(255, 0, 0))
                                    )
                                ),
                                vn.vietmap.vietmapsdk.style.layers.PropertyFactory.lineColor(Color.RED),
                                vn.vietmap.vietmapsdk.style.layers.PropertyFactory.lineWidth(10.0f),
                                vn.vietmap.vietmapsdk.style.layers.PropertyFactory.lineCap(
                                    vn.vietmap.vietmapsdk.style.layers.Property.LINE_CAP_ROUND),
                                vn.vietmap.vietmapsdk.style.layers.PropertyFactory.lineJoin(
                                    vn.vietmap.vietmapsdk.style.layers.Property.LINE_JOIN_ROUND)
                            )
                    )
            )
        } catch (exception: IOException) {
            Timber.e(exception)
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
        const val LINE_SOURCE = "gradient"
    }
}
