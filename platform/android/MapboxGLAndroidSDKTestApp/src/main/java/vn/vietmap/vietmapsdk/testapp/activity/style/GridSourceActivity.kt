package vn.vietmap.vietmapsdk.testapp.activity.style

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.MultiLineString
import com.mapbox.geojson.Point
import vn.vietmap.vietmapsdk.geometry.LatLngBounds
import vn.vietmap.vietmapsdk.maps.MapView
import vn.vietmap.vietmapsdk.maps.VietmapMap
import vn.vietmap.vietmapsdk.maps.OnMapReadyCallback
import vn.vietmap.vietmapsdk.maps.Style

import vn.vietmap.vietmapsdk.style.sources.CustomGeometrySource
import vn.vietmap.vietmapsdk.style.sources.GeometryTileProvider
import vn.vietmap.vietmapsdk.testapp.R
import java.util.*

/**
 * Test activity showcasing using CustomGeometrySource to create a grid overlay on the map.
 */
class GridSourceActivity : AppCompatActivity(),
    vn.vietmap.vietmapsdk.maps.OnMapReadyCallback {
    private lateinit var mapView: vn.vietmap.vietmapsdk.maps.MapView

    // public for testing purposes
    var source: vn.vietmap.vietmapsdk.style.sources.CustomGeometrySource? = null
    var layer: vn.vietmap.vietmapsdk.style.layers.LineLayer? = null

    /**
     * Implementation of GeometryTileProvider that returns features representing a zoom-dependent
     * grid.
     */
    internal class GridProvider :
        vn.vietmap.vietmapsdk.style.sources.GeometryTileProvider {
        override fun getFeaturesForBounds(bounds: vn.vietmap.vietmapsdk.geometry.LatLngBounds, zoom: Int): FeatureCollection {
            val features: MutableList<Feature> = ArrayList()
            val gridSpacing: Double
            gridSpacing = if (zoom >= 13) {
                0.01
            } else if (zoom >= 11) {
                0.05
            } else if (zoom == 10) {
                .1
            } else if (zoom == 9) {
                0.25
            } else if (zoom == 8) {
                0.5
            } else if (zoom >= 6) {
                1.0
            } else if (zoom == 5) {
                2.0
            } else if (zoom >= 4) {
                5.0
            } else if (zoom == 2) {
                10.0
            } else {
                20.0
            }
            var gridLines: MutableList<Any?> = ArrayList<Any?>()
            var y = Math.ceil(bounds.latNorth / gridSpacing) * gridSpacing
            while (y >= Math.floor(bounds.latSouth / gridSpacing) * gridSpacing) {
                gridLines.add(
                    Arrays.asList(
                        Point.fromLngLat(bounds.lonWest, y),
                        Point.fromLngLat(bounds.lonEast, y)
                    )
                )
                y -= gridSpacing
            }
            features.add(Feature.fromGeometry(MultiLineString.fromLngLats(gridLines as MutableList<MutableList<Point>>)))
            gridLines = ArrayList<Any?>()
            var x = Math.floor(bounds.lonWest / gridSpacing) * gridSpacing
            while (x <= Math.ceil(bounds.lonEast / gridSpacing) * gridSpacing) {
                gridLines.add(
                    Arrays.asList(
                        Point.fromLngLat(x, bounds.latSouth),
                        Point.fromLngLat(x, bounds.latNorth)
                    )
                )
                x += gridSpacing
            }
            features.add(Feature.fromGeometry(MultiLineString.fromLngLats(gridLines as MutableList<MutableList<Point>>)))
            return FeatureCollection.fromFeatures(features)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grid_source)
        mapView = findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
    }

    override fun onMapReady(map: vn.vietmap.vietmapsdk.maps.VietmapMap) {
        source = vn.vietmap.vietmapsdk.style.sources.CustomGeometrySource(
            ID_GRID_SOURCE,
            GridProvider()
        )
        layer = vn.vietmap.vietmapsdk.style.layers.LineLayer(
            ID_GRID_LAYER,
            ID_GRID_SOURCE
        )
        layer!!.setProperties(
            vn.vietmap.vietmapsdk.style.layers.PropertyFactory.lineColor(Color.parseColor("#000000"))
        )
        map.setStyle(
            vn.vietmap.vietmapsdk.maps.Style.Builder()
                .fromUri(vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Streets"))
                .withLayer(layer!!)
                .withSource(source!!)
        )
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
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView!!.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView!!.onSaveInstanceState(outState)
    }

    companion object {
        const val ID_GRID_SOURCE = "grid_source"
        const val ID_GRID_LAYER = "grid_layer"
    }
}
