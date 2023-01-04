package vn.vietmap.vietmapsdk.testapp.activity.feature

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.JsonObject
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import vn.vietmap.vietmapsdk.geometry.LatLng
import vn.vietmap.vietmapsdk.maps.MapView
import vn.vietmap.vietmapsdk.maps.VietmapMap
import vn.vietmap.vietmapsdk.maps.Style
import vn.vietmap.vietmapsdk.style.expressions.Expression
import vn.vietmap.vietmapsdk.style.layers.CircleLayer
import vn.vietmap.vietmapsdk.style.sources.GeoJsonSource
import vn.vietmap.vietmapsdk.testapp.R

/**
 * Test activity showcasing using the query source features API to query feature counts
 */
class QuerySourceFeaturesActivity : AppCompatActivity() {
    var mapView: vn.vietmap.vietmapsdk.maps.MapView? = null
    private var mapboxMap: vn.vietmap.vietmapsdk.maps.VietmapMap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_query_source_features)

        // Initialize map as normal
        mapView = findViewById<View>(R.id.mapView) as vn.vietmap.vietmapsdk.maps.MapView
        mapView!!.onCreate(savedInstanceState)
        mapView!!.getMapAsync { map: vn.vietmap.vietmapsdk.maps.VietmapMap? ->
            mapboxMap = map
            mapboxMap!!.getStyle { style: vn.vietmap.vietmapsdk.maps.Style -> initStyle(style) }
            mapboxMap!!.setStyle(vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Streets"))
        }
    }

    private fun initStyle(style: vn.vietmap.vietmapsdk.maps.Style) {
        val properties = JsonObject()
        properties.addProperty("key1", "value1")
        val source = vn.vietmap.vietmapsdk.style.sources.GeoJsonSource(
            "test-source",
            FeatureCollection.fromFeatures(
                arrayOf(
                    Feature.fromGeometry(Point.fromLngLat(17.1, 51.0), properties),
                    Feature.fromGeometry(Point.fromLngLat(17.2, 51.0), properties),
                    Feature.fromGeometry(Point.fromLngLat(17.3, 51.0), properties),
                    Feature.fromGeometry(Point.fromLngLat(17.4, 51.0), properties)
                )
            )
        )
        style.addSource(source)
        val visible = vn.vietmap.vietmapsdk.style.expressions.Expression.eq(
            vn.vietmap.vietmapsdk.style.expressions.Expression.get("key1"), vn.vietmap.vietmapsdk.style.expressions.Expression.literal("value1"))
        val invisible = vn.vietmap.vietmapsdk.style.expressions.Expression.neq(
            vn.vietmap.vietmapsdk.style.expressions.Expression.get("key1"), vn.vietmap.vietmapsdk.style.expressions.Expression.literal("value1"))
        val layer = vn.vietmap.vietmapsdk.style.layers.CircleLayer(
            "test-layer",
            source.id
        )
            .withFilter(visible)
        style.addLayer(layer)

        // Add a click listener
        mapboxMap!!.addOnMapClickListener { point: vn.vietmap.vietmapsdk.geometry.LatLng? ->
            // Query
            val features = source.querySourceFeatures(
                vn.vietmap.vietmapsdk.style.expressions.Expression.eq(
                    vn.vietmap.vietmapsdk.style.expressions.Expression.get("key1"),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.literal("value1")
                )
            )
            Toast.makeText(
                this@QuerySourceFeaturesActivity,
                String.format(
                    "Found %s features",
                    features.size
                ),
                Toast.LENGTH_SHORT
            ).show()
            false
        }
        val fab = findViewById<View>(R.id.fab) as FloatingActionButton
        fab.setColorFilter(ContextCompat.getColor(this, R.color.primary))
        fab.setOnClickListener { view: View? ->
            val visibility = layer.filter
            if (visibility != null && visibility == visible) {
                layer.setFilter(invisible)
                fab.setImageResource(R.drawable.ic_layers_clear)
            } else {
                layer.setFilter(visible)
                fab.setImageResource(R.drawable.ic_layers)
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
