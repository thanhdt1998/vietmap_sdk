package vn.vietmap.vietmapsdk.testapp.activity.feature

import android.graphics.BitmapFactory
import android.graphics.RectF
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import vn.vietmap.vietmapsdk.maps.MapView
import vn.vietmap.vietmapsdk.maps.VietmapMap
import vn.vietmap.vietmapsdk.maps.Style
import vn.vietmap.vietmapsdk.style.expressions.Expression
import vn.vietmap.vietmapsdk.style.layers.BackgroundLayer
import vn.vietmap.vietmapsdk.style.layers.PropertyFactory
import vn.vietmap.vietmapsdk.style.layers.SymbolLayer
import vn.vietmap.vietmapsdk.style.sources.GeoJsonSource
import vn.vietmap.vietmapsdk.testapp.R
import timber.log.Timber
import vn.vietmap.vietmapsdk.testapp.utils.ResourceUtils
import java.io.IOException

/**
 * Test activity showcasing using the query rendered features API to count Symbols in a rectangle.
 */
class QueryRenderedFeaturesBoxSymbolCountActivity : AppCompatActivity() {
    var mapView: vn.vietmap.vietmapsdk.maps.MapView? = null
    var mapboxMap: vn.vietmap.vietmapsdk.maps.VietmapMap? = null
        private set
    private lateinit var toast: Toast
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_query_features_box)
        val selectionBox = findViewById<View>(R.id.selection_box)

        // Initialize map as normal
        mapView = findViewById<View>(R.id.mapView) as vn.vietmap.vietmapsdk.maps.MapView
        mapView!!.onCreate(savedInstanceState)
        mapView!!.getMapAsync { mapboxMap: vn.vietmap.vietmapsdk.maps.VietmapMap ->
            this@QueryRenderedFeaturesBoxSymbolCountActivity.mapboxMap = mapboxMap
            try {
                val testPoints = ResourceUtils.readRawResource(
                    mapView!!.context,
                    R.raw.test_points_utrecht
                )
                val markerImage =
                    BitmapFactory.decodeResource(resources, R.drawable.mapbox_marker_icon_default)
                mapboxMap.setStyle(
                    vn.vietmap.vietmapsdk.maps.Style.Builder()
                        .withLayer(
                            vn.vietmap.vietmapsdk.style.layers.BackgroundLayer("bg")
                                .withProperties(
                                    vn.vietmap.vietmapsdk.style.layers.PropertyFactory.backgroundColor(
                                        vn.vietmap.vietmapsdk.style.expressions.Expression.rgb(120, 161, 226))
                                )
                        )
                        .withLayer(
                            vn.vietmap.vietmapsdk.style.layers.SymbolLayer(
                                "symbols-layer",
                                "symbols-source"
                            )
                                .withProperties(
                                    vn.vietmap.vietmapsdk.style.layers.PropertyFactory.iconImage("test-icon")
                                )
                        )
                        .withSource(
                            vn.vietmap.vietmapsdk.style.sources.GeoJsonSource(
                                "symbols-source",
                                testPoints
                            )
                        )
                        .withImage("test-icon", markerImage)
                )
            } catch (exception: IOException) {
                exception.printStackTrace()
            }
            selectionBox.setOnClickListener { view: View? ->
                // Query
                val top = selectionBox.top - mapView!!.top
                val left = selectionBox.left - mapView!!.left
                val box = RectF(
                    left.toFloat(),
                    top.toFloat(),
                    (left + selectionBox.width).toFloat(),
                    (top + selectionBox.height).toFloat()
                )
                Timber.i("Querying box %s", box)
                val features = mapboxMap.queryRenderedFeatures(box, "symbols-layer")

                // Show count
                if (toast != null) {
                    toast!!.cancel()
                }
                toast = Toast.makeText(
                    this@QueryRenderedFeaturesBoxSymbolCountActivity,
                    String.format("%s features in box", features.size),
                    Toast.LENGTH_SHORT
                )
                toast.show()
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
