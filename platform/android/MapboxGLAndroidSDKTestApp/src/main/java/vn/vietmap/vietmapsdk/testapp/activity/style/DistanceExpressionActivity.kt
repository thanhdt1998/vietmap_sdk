package vn.vietmap.vietmapsdk.testapp.activity.style

import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Point
import vn.vietmap.vietmapsdk.camera.CameraPosition
import vn.vietmap.vietmapsdk.geometry.LatLng
import vn.vietmap.vietmapsdk.maps.VietmapMap
import vn.vietmap.vietmapsdk.maps.Style
import vn.vietmap.vietmapsdk.style.expressions.Expression.distance
import vn.vietmap.vietmapsdk.style.expressions.Expression.lt
import vn.vietmap.vietmapsdk.style.layers.FillLayer
import vn.vietmap.vietmapsdk.style.layers.Property.NONE
import vn.vietmap.vietmapsdk.style.layers.PropertyFactory.*
import vn.vietmap.vietmapsdk.style.layers.SymbolLayer
import vn.vietmap.vietmapsdk.style.sources.GeoJsonSource
import vn.vietmap.vietmapsdk.testapp.R
import com.mapbox.turf.TurfConstants
import com.mapbox.turf.TurfTransformation
import kotlinx.android.synthetic.main.activity_physical_circle.*

/**
 * An Activity that showcases the within expression to filter features outside a geometry
 */
class DistanceExpressionActivity : AppCompatActivity() {

    private lateinit var VietmapMap: vn.vietmap.vietmapsdk.maps.VietmapMap

    private val lat = 37.78794572301525
    private val lon = -122.40752220153807

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_within_expression)

        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync { map ->
            VietmapMap = map

            // Setup camera position above Georgetown
            VietmapMap.cameraPosition = vn.vietmap.vietmapsdk.camera.CameraPosition.Builder()
                .target(vn.vietmap.vietmapsdk.geometry.LatLng(lat, lon))
                .zoom(16.0)
                .build()
            setupStyle()
        }
    }

    private fun setupStyle() {
        val center = Point.fromLngLat(lon, lat)
        val circle = TurfTransformation.circle(center, 150.0, TurfConstants.UNIT_METRES)
        // Setup style with additional layers,
        // using Streets as a base style
        VietmapMap.setStyle(
            vn.vietmap.vietmapsdk.maps.Style.Builder()
                .fromUri(vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Streets"))
                .withSources(
                    vn.vietmap.vietmapsdk.style.sources.GeoJsonSource(
                        POINT_ID,
                        Point.fromLngLat(lon, lat)
                    ),
                    vn.vietmap.vietmapsdk.style.sources.GeoJsonSource(
                        CIRCLE_ID,
                        circle
                    )
                )
                .withLayerBelow(
                    vn.vietmap.vietmapsdk.style.layers.FillLayer(
                        CIRCLE_ID,
                        CIRCLE_ID
                    )
                        .withProperties(
                            fillOpacity(0.5f),
                            fillColor(Color.parseColor("#3bb2d0"))
                        ),
                    "poi-label"
                )
        ) { style ->
            // Show only POI labels inside circle radius using distance expression
            val symbolLayer = style.getLayer("poi_z16") as vn.vietmap.vietmapsdk.style.layers.SymbolLayer
            symbolLayer.setFilter(
                lt(
                    distance(
                        Point.fromLngLat(lon, lat)
                    ),
                    150
                )
            )

            // Hide other types of labels to highlight POI labels
            (style.getLayer("road_label") as vn.vietmap.vietmapsdk.style.layers.SymbolLayer).setProperties(visibility(NONE))
            (style.getLayer("airport-label-major") as vn.vietmap.vietmapsdk.style.layers.SymbolLayer).setProperties(visibility(NONE))
            (style.getLayer("poi_transit") as vn.vietmap.vietmapsdk.style.layers.SymbolLayer).setProperties(visibility(NONE))
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

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState?.let {
            mapView.onSaveInstanceState(it)
        }
    }

    companion object {
        const val POINT_ID = "point"
        const val CIRCLE_ID = "circle"
    }
}
