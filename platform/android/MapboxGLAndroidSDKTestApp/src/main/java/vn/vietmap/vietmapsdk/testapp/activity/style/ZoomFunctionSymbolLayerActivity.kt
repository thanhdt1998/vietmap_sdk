package vn.vietmap.vietmapsdk.testapp.activity.style

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.JsonObject
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
// import com.mapbox.vietmapsdk.maps.* //
import vn.vietmap.vietmapsdk.maps.VietmapMap.OnMapClickListener
import vn.vietmap.vietmapsdk.style.expressions.Expression
import vn.vietmap.vietmapsdk.style.layers.Property
import vn.vietmap.vietmapsdk.style.layers.PropertyFactory
import vn.vietmap.vietmapsdk.style.layers.SymbolLayer
import vn.vietmap.vietmapsdk.style.sources.GeoJsonSource
import vn.vietmap.vietmapsdk.testapp.R
import timber.log.Timber
import vn.vietmap.vietmapsdk.maps.Style

/**
 * Test activity showcasing changing the icon with a zoom function and adding selection state to a SymbolLayer.
 */
class ZoomFunctionSymbolLayerActivity : AppCompatActivity() {
    private lateinit var mapView: vn.vietmap.vietmapsdk.maps.MapView
    private var mapboxMap: vn.vietmap.vietmapsdk.maps.VietmapMap? = null
    private var source: vn.vietmap.vietmapsdk.style.sources.GeoJsonSource? = null
    private var layer: vn.vietmap.vietmapsdk.style.layers.SymbolLayer? = null
    private var isInitialPosition = true
    private var isSelected = false
    private var isShowingSymbolLayer = true
    private val mapClickListener = OnMapClickListener { point ->
        val screenPoint = mapboxMap!!.projection.toScreenLocation(point)
        val featureList = mapboxMap!!.queryRenderedFeatures(screenPoint, LAYER_ID)
        if (!featureList.isEmpty()) {
            val feature = featureList[0]
            val selectedNow = feature.getBooleanProperty(KEY_PROPERTY_SELECTED)
            isSelected = !selectedNow
            updateSource(mapboxMap!!.style)
        } else {
            Timber.e("No features found")
        }
        true
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zoom_symbol_layer)
        mapView = findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(
            vn.vietmap.vietmapsdk.maps.OnMapReadyCallback { map: vn.vietmap.vietmapsdk.maps.VietmapMap ->
                mapboxMap = map
                map.setStyle(Style.getPredefinedStyle("Streets")) { style: vn.vietmap.vietmapsdk.maps.Style ->
                    updateSource(style)
                    addLayer(style)
                    map.addOnMapClickListener(mapClickListener)
                }
            }
        )
    }

    private fun updateSource(style: vn.vietmap.vietmapsdk.maps.Style?) {
        val featureCollection = createFeatureCollection()
        if (source != null) {
            source!!.setGeoJson(featureCollection)
        } else {
            source = vn.vietmap.vietmapsdk.style.sources.GeoJsonSource(
                SOURCE_ID,
                featureCollection
            )
            style!!.addSource(source!!)
        }
    }

    private fun toggleSymbolLayerVisibility() {
        layer!!.setProperties(
            vn.vietmap.vietmapsdk.style.layers.PropertyFactory.visibility(if (isShowingSymbolLayer) vn.vietmap.vietmapsdk.style.layers.Property.NONE else vn.vietmap.vietmapsdk.style.layers.Property.VISIBLE)
        )
        isShowingSymbolLayer = !isShowingSymbolLayer
    }

    private fun createFeatureCollection(): FeatureCollection {
        val point = if (isInitialPosition) Point.fromLngLat(
            -74.01618140,
            40.701745
        ) else Point.fromLngLat(-73.988097, 40.749864)
        val properties = JsonObject()
        properties.addProperty(KEY_PROPERTY_SELECTED, isSelected)
        val feature = Feature.fromGeometry(point, properties)
        return FeatureCollection.fromFeatures(arrayOf(feature))
    }

    private fun addLayer(style: vn.vietmap.vietmapsdk.maps.Style) {
        layer =
            vn.vietmap.vietmapsdk.style.layers.SymbolLayer(LAYER_ID, SOURCE_ID)
        layer!!.setProperties(
            vn.vietmap.vietmapsdk.style.layers.PropertyFactory.iconImage(
                vn.vietmap.vietmapsdk.style.expressions.Expression.step(
                    vn.vietmap.vietmapsdk.style.expressions.Expression.zoom(),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.literal(BUS_MAKI_ICON_ID),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.stop(ZOOM_STOP_MAX_VALUE, CAFE_MAKI_ICON_ID)
                )
            ),
            vn.vietmap.vietmapsdk.style.layers.PropertyFactory.iconSize(
                vn.vietmap.vietmapsdk.style.expressions.Expression.switchCase(
                    vn.vietmap.vietmapsdk.style.expressions.Expression.get(KEY_PROPERTY_SELECTED),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.literal(3.0f),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.literal(1.0f)
                )
            ),
            vn.vietmap.vietmapsdk.style.layers.PropertyFactory.iconAllowOverlap(true)
        )
        style.addLayer(layer!!)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_symbols, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (mapboxMap != null) {
            if (item.itemId == R.id.menu_action_change_location) {
                isInitialPosition = !isInitialPosition
                updateSource(mapboxMap!!.style)
            } else if (item.itemId == R.id.menu_action_toggle_source) {
                toggleSymbolLayerVisibility()
            }
        }
        return super.onOptionsItemSelected(item)
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
        private const val LAYER_ID = "symbolLayer"
        private const val SOURCE_ID = "poiSource"
        private const val BUS_MAKI_ICON_ID = "bus"
        private const val CAFE_MAKI_ICON_ID = "cafe-11"
        private const val KEY_PROPERTY_SELECTED = "selected"
        private const val ZOOM_STOP_MAX_VALUE = 12.0f
    }
}
