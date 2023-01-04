package vn.vietmap.vietmapsdk.testapp.activity.feature

import android.graphics.Color
import android.graphics.RectF
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.FeatureCollection
import vn.vietmap.vietmapsdk.maps.MapView
import vn.vietmap.vietmapsdk.maps.VietmapMap
import vn.vietmap.vietmapsdk.maps.Style
import vn.vietmap.vietmapsdk.style.expressions.Expression
import vn.vietmap.vietmapsdk.style.layers.FillLayer
import vn.vietmap.vietmapsdk.style.layers.Layer
import vn.vietmap.vietmapsdk.style.layers.PropertyFactory
import vn.vietmap.vietmapsdk.style.sources.GeoJsonSource
import vn.vietmap.vietmapsdk.testapp.R
import timber.log.Timber

/**
 * Demo's query rendered features
 */
class QueryRenderedFeaturesBoxHighlightActivity : AppCompatActivity() {
    var mapView: vn.vietmap.vietmapsdk.maps.MapView? = null
    var mapboxMap: vn.vietmap.vietmapsdk.maps.VietmapMap? = null
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_query_features_box)
        val selectionBox = findViewById<View>(R.id.selection_box)

        // Initialize map as normal
        mapView = findViewById<View>(R.id.mapView) as vn.vietmap.vietmapsdk.maps.MapView
        mapView!!.onCreate(savedInstanceState)
        mapView!!.getMapAsync { mapboxMap: vn.vietmap.vietmapsdk.maps.VietmapMap ->
            this@QueryRenderedFeaturesBoxHighlightActivity.mapboxMap = mapboxMap

            // Add layer / source
            val source =
                vn.vietmap.vietmapsdk.style.sources.GeoJsonSource("highlighted-shapes-source")
            val layer: vn.vietmap.vietmapsdk.style.layers.Layer = vn.vietmap.vietmapsdk.style.layers.FillLayer(
                "highlighted-shapes-layer",
                "highlighted-shapes-source"
            )
                .withProperties(vn.vietmap.vietmapsdk.style.layers.PropertyFactory.fillColor(Color.RED))
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
                Timber.i("Querying box %s for buildings", box)
                val filter = vn.vietmap.vietmapsdk.style.expressions.Expression.lt(
                    vn.vietmap.vietmapsdk.style.expressions.Expression.toNumber(
                        vn.vietmap.vietmapsdk.style.expressions.Expression.get("height")),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.literal(10)
                )
                val features = mapboxMap.queryRenderedFeatures(box, filter, "building")

                // Show count
                Toast.makeText(
                    this@QueryRenderedFeaturesBoxHighlightActivity,
                    String.format("%s features in box", features.size),
                    Toast.LENGTH_SHORT
                ).show()

                // Update source data
                source.setGeoJson(FeatureCollection.fromFeatures(features))
            }
            mapboxMap.setStyle(
                vn.vietmap.vietmapsdk.maps.Style.Builder()
                    .fromUri(vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Streets"))
                    .withSource(source)
                    .withLayer(layer)
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
