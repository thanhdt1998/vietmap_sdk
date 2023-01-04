package vn.vietmap.vietmapsdk.testapp.activity.style

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.LineString
import com.mapbox.geojson.Point
import vn.vietmap.vietmapsdk.geometry.LatLng
import vn.vietmap.vietmapsdk.geometry.LatLngBounds
import vn.vietmap.vietmapsdk.maps.VietmapMap
import vn.vietmap.vietmapsdk.maps.OnMapReadyCallback
import vn.vietmap.vietmapsdk.maps.Style
import vn.vietmap.vietmapsdk.style.layers.LineLayer
import vn.vietmap.vietmapsdk.style.layers.PropertyFactory
import vn.vietmap.vietmapsdk.style.sources.GeoJsonSource
import vn.vietmap.vietmapsdk.testapp.R
import kotlinx.android.synthetic.main.activity_collection_update_on_style_change.*
import java.util.*

/**
 * Test activity that verifies whether the GeoJsonSource transition over style changes can be smooth.
 */
class CollectionUpdateOnStyleChange : AppCompatActivity(),
    vn.vietmap.vietmapsdk.maps.OnMapReadyCallback, vn.vietmap.vietmapsdk.maps.Style.OnStyleLoaded {

    private lateinit var VietmapMap: vn.vietmap.vietmapsdk.maps.VietmapMap
    private var currentStyleIndex: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collection_update_on_style_change)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
        setupStyleChangeView()
        Toast.makeText(
            this,
            "Make sure that the collection doesn't blink on style change",
            Toast.LENGTH_LONG
        )
            .show()
    }

    override fun onMapReady(map: vn.vietmap.vietmapsdk.maps.VietmapMap) {
        VietmapMap = map
        VietmapMap.setStyle(vn.vietmap.vietmapsdk.maps.Style.Builder().fromUri(STYLES[currentStyleIndex]), this)
    }

    override fun onStyleLoaded(style: vn.vietmap.vietmapsdk.maps.Style) {
        setupLayer(style)
    }

    private fun setupLayer(style: vn.vietmap.vietmapsdk.maps.Style) {
        val source = vn.vietmap.vietmapsdk.style.sources.GeoJsonSource(
            "source",
            featureCollection
        )
        val lineLayer = vn.vietmap.vietmapsdk.style.layers.LineLayer(
            "layer",
            "source"
        )
            .withProperties(
                vn.vietmap.vietmapsdk.style.layers.PropertyFactory.lineColor(Color.RED),
                vn.vietmap.vietmapsdk.style.layers.PropertyFactory.lineWidth(10f)
            )

        style.addSource(source)
        style.addLayer(lineLayer)
    }

    private fun setupStyleChangeView() {
        val fabStyles = findViewById<FloatingActionButton>(R.id.fabStyles)
        fabStyles.setOnClickListener {
            currentStyleIndex++
            if (currentStyleIndex == STYLES.size) {
                currentStyleIndex = 0
            }
            VietmapMap.setStyle(vn.vietmap.vietmapsdk.maps.Style.Builder().fromUri(STYLES[currentStyleIndex]), this)
        }
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    companion object {

        private val STYLES = arrayOf(vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Streets"), vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Outdoor"), vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Bright"), vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Pastel"), vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Satellite Hybrid"), vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Satellite Hybrid"))

        private val featureCollection: FeatureCollection

        init {
            val bounds = vn.vietmap.vietmapsdk.geometry.LatLngBounds.from(60.0, 100.0, -60.0, -100.0)
            val points = ArrayList<Point>()
            for (i in 0 until 1000) {
                val latLng = getLatLngInBounds(bounds)
                points.add(Point.fromLngLat(latLng.longitude, latLng.latitude))
            }
            featureCollection = FeatureCollection.fromFeature(Feature.fromGeometry(LineString.fromLngLats(points)))
        }

        private fun getLatLngInBounds(bounds: vn.vietmap.vietmapsdk.geometry.LatLngBounds): vn.vietmap.vietmapsdk.geometry.LatLng {
            val generator = Random()
            val randomLat = bounds.latSouth + generator.nextDouble() * (bounds.latNorth - bounds.latSouth)
            val randomLon = bounds.lonWest + generator.nextDouble() * (bounds.lonEast - bounds.lonWest)
            return vn.vietmap.vietmapsdk.geometry.LatLng(randomLat, randomLon)
        }
    }
}
