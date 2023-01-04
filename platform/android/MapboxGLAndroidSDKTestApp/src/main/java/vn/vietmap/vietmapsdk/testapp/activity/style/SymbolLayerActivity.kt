package vn.vietmap.vietmapsdk.testapp.activity.style

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import vn.vietmap.vietmapsdk.camera.CameraPosition
import vn.vietmap.vietmapsdk.geometry.LatLng
// import com.mapbox.vietmapsdk.maps.* //
import vn.vietmap.vietmapsdk.maps.VietmapMap.OnMapClickListener
import vn.vietmap.vietmapsdk.style.expressions.Expression
import vn.vietmap.vietmapsdk.style.expressions.Expression.FormatEntry
import vn.vietmap.vietmapsdk.style.expressions.Expression.FormatOption
import vn.vietmap.vietmapsdk.style.expressions.Expression.NumberFormatOption
import vn.vietmap.vietmapsdk.style.layers.Property
import vn.vietmap.vietmapsdk.style.layers.PropertyFactory
import vn.vietmap.vietmapsdk.style.layers.SymbolLayer
import vn.vietmap.vietmapsdk.style.sources.GeoJsonSource
import vn.vietmap.vietmapsdk.style.sources.Source
import vn.vietmap.vietmapsdk.testapp.R
import vn.vietmap.vietmapsdk.utils.BitmapUtils
import timber.log.Timber
import java.util.*

/**
 * Test activity showcasing runtime manipulation of symbol layers.
 *
 *
 * Showcases the ability to offline render a symbol layer by using a packaged style and fonts from the assets folder.
 *
 */
class SymbolLayerActivity : AppCompatActivity(), OnMapClickListener,
    vn.vietmap.vietmapsdk.maps.OnMapReadyCallback {
    private val random = Random()
    private var markerSource: vn.vietmap.vietmapsdk.style.sources.GeoJsonSource? = null
    private var markerCollection: FeatureCollection? = null
    private var markerSymbolLayer: vn.vietmap.vietmapsdk.style.layers.SymbolLayer? = null
    private var mapboxSignSymbolLayer: vn.vietmap.vietmapsdk.style.layers.SymbolLayer? = null
    private var numberFormatSymbolLayer: vn.vietmap.vietmapsdk.style.layers.SymbolLayer? = null
    private var mapboxMap: vn.vietmap.vietmapsdk.maps.VietmapMap? = null
    private var mapView: vn.vietmap.vietmapsdk.maps.MapView? = null
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_symbollayer)

        // Create map configuration
        val mapboxMapOptions = vn.vietmap.vietmapsdk.maps.VietMapOptions.createFromAttributes(this)
        mapboxMapOptions.camera(
            vn.vietmap.vietmapsdk.camera.CameraPosition.Builder().target(
                vn.vietmap.vietmapsdk.geometry.LatLng(52.35273, 4.91638)
            )
                .zoom(13.0)
                .build()
        )

        // Create map programmatically, add to view hierarchy
        mapView = vn.vietmap.vietmapsdk.maps.MapView(this, mapboxMapOptions)
        mapView!!.getMapAsync(this)
        mapView!!.onCreate(savedInstanceState)
        (findViewById<View>(R.id.container) as ViewGroup).addView(mapView)

        // Use OnStyleImageMissing API to lazily load an icon
        mapView!!.addOnStyleImageMissingListener { id: String? ->
            val style = mapboxMap!!.style
            if (style != null) {
                Timber.e("Adding image with id: %s", id)
                val androidIcon =
                    vn.vietmap.vietmapsdk.utils.BitmapUtils.getBitmapFromDrawable(resources.getDrawable(R.drawable.ic_android_2))
                style.addImage(id!!, Objects.requireNonNull(androidIcon)!!)
            }
        }
    }

    override fun onMapReady(mapboxMap: vn.vietmap.vietmapsdk.maps.VietmapMap) {
        this.mapboxMap = mapboxMap
        val carBitmap = vn.vietmap.vietmapsdk.utils.BitmapUtils.getBitmapFromDrawable(
            resources.getDrawable(R.drawable.ic_directions_car_black)
        )

        // marker source
        markerCollection = FeatureCollection.fromFeatures(
            arrayOf(
                Feature.fromGeometry(
                    Point.fromLngLat(4.91638, 52.35673),
                    featureProperties("1", "Android")
                ),
                Feature.fromGeometry(
                    Point.fromLngLat(4.91638, 52.34673),
                    featureProperties("2", "Car")
                )
            )
        )
        markerSource = vn.vietmap.vietmapsdk.style.sources.GeoJsonSource(
            MARKER_SOURCE,
            markerCollection
        )

        // marker layer
        markerSymbolLayer = vn.vietmap.vietmapsdk.style.layers.SymbolLayer(
            MARKER_LAYER,
            MARKER_SOURCE
        )
            .withProperties(
                vn.vietmap.vietmapsdk.style.layers.PropertyFactory.iconImage(
                    vn.vietmap.vietmapsdk.style.expressions.Expression.get(TITLE_FEATURE_PROPERTY)),
                vn.vietmap.vietmapsdk.style.layers.PropertyFactory.iconIgnorePlacement(true),
                vn.vietmap.vietmapsdk.style.layers.PropertyFactory.iconAllowOverlap(true),
                vn.vietmap.vietmapsdk.style.layers.PropertyFactory.iconSize(
                    vn.vietmap.vietmapsdk.style.expressions.Expression.switchCase(
                        vn.vietmap.vietmapsdk.style.expressions.Expression.toBool(
                            vn.vietmap.vietmapsdk.style.expressions.Expression.get(
                                SELECTED_FEATURE_PROPERTY
                            )
                        ),
                        vn.vietmap.vietmapsdk.style.expressions.Expression.literal(1.5f),
                        vn.vietmap.vietmapsdk.style.expressions.Expression.literal(1.0f)
                    )
                ),
                vn.vietmap.vietmapsdk.style.layers.PropertyFactory.iconAnchor(
                    vn.vietmap.vietmapsdk.style.layers.Property.ICON_ANCHOR_BOTTOM),
                vn.vietmap.vietmapsdk.style.layers.PropertyFactory.iconColor(Color.BLUE),
                vn.vietmap.vietmapsdk.style.layers.PropertyFactory.textField(TEXT_FIELD_EXPRESSION),
                vn.vietmap.vietmapsdk.style.layers.PropertyFactory.textFont(NORMAL_FONT_STACK),
                vn.vietmap.vietmapsdk.style.layers.PropertyFactory.textColor(Color.BLUE),
                vn.vietmap.vietmapsdk.style.layers.PropertyFactory.textAllowOverlap(true),
                vn.vietmap.vietmapsdk.style.layers.PropertyFactory.textIgnorePlacement(true),
                vn.vietmap.vietmapsdk.style.layers.PropertyFactory.textAnchor(
                    vn.vietmap.vietmapsdk.style.layers.Property.TEXT_ANCHOR_TOP),
                vn.vietmap.vietmapsdk.style.layers.PropertyFactory.textSize(10f)
            )

        // mapbox sign layer
        val mapboxSignSource: vn.vietmap.vietmapsdk.style.sources.Source =
            vn.vietmap.vietmapsdk.style.sources.GeoJsonSource(
                MAPBOX_SIGN_SOURCE,
                Point.fromLngLat(4.91638, 52.3510)
            )
        mapboxSignSymbolLayer = vn.vietmap.vietmapsdk.style.layers.SymbolLayer(
            MAPBOX_SIGN_LAYER,
            MAPBOX_SIGN_SOURCE
        )
        shuffleVietmapSign()

        // number format layer
        val numberFormatSource: vn.vietmap.vietmapsdk.style.sources.Source =
            vn.vietmap.vietmapsdk.style.sources.GeoJsonSource(
                NUMBER_FORMAT_SOURCE,
                Point.fromLngLat(4.92756, 52.3516)
            )
        numberFormatSymbolLayer =
            vn.vietmap.vietmapsdk.style.layers.SymbolLayer(
                NUMBER_FORMAT_LAYER,
                NUMBER_FORMAT_SOURCE
            )
        numberFormatSymbolLayer!!.setProperties(
            vn.vietmap.vietmapsdk.style.layers.PropertyFactory.textField(
                vn.vietmap.vietmapsdk.style.expressions.Expression.numberFormat(
                    123.456789,
                    NumberFormatOption.locale("nl-NL"),
                    NumberFormatOption.currency("EUR")
                )
            )
        )
        mapboxMap.setStyle(
            vn.vietmap.vietmapsdk.maps.Style.Builder()
                .fromUri("asset://streets.json")
                .withImage("Car", Objects.requireNonNull(carBitmap)!!, false)
                .withSources(markerSource, mapboxSignSource, numberFormatSource)
                .withLayers(markerSymbolLayer, mapboxSignSymbolLayer, numberFormatSymbolLayer)
        )

        // Set a click-listener so we can manipulate the map
        mapboxMap.addOnMapClickListener(this@SymbolLayerActivity)
    }

    override fun onMapClick(point: vn.vietmap.vietmapsdk.geometry.LatLng): Boolean {
        // Query which features are clicked
        val screenLoc = mapboxMap!!.projection.toScreenLocation(point)
        val markerFeatures = mapboxMap!!.queryRenderedFeatures(screenLoc, MARKER_LAYER)
        if (!markerFeatures.isEmpty()) {
            for (feature in Objects.requireNonNull(markerCollection!!.features())!!) {
                if (feature.getStringProperty(ID_FEATURE_PROPERTY)
                    == markerFeatures[0].getStringProperty(ID_FEATURE_PROPERTY)
                ) {
                    // use DDS
                    val selected = feature.getBooleanProperty(SELECTED_FEATURE_PROPERTY)
                    feature.addBooleanProperty(SELECTED_FEATURE_PROPERTY, !selected)

                    // validate symbol flicker regression for #13407
                    markerSymbolLayer!!.setProperties(
                        vn.vietmap.vietmapsdk.style.layers.PropertyFactory.iconOpacity(
                            vn.vietmap.vietmapsdk.style.expressions.Expression.match(
                                vn.vietmap.vietmapsdk.style.expressions.Expression.get(ID_FEATURE_PROPERTY),
                                vn.vietmap.vietmapsdk.style.expressions.Expression.literal(1.0f),
                                vn.vietmap.vietmapsdk.style.expressions.Expression.stop(
                                    feature.getStringProperty("id"),
                                    if (selected) 0.3f else 1.0f
                                )
                            )
                        )
                    )
                }
            }
            markerSource!!.setGeoJson(markerCollection)
        } else {
            val mapboxSignFeatures = mapboxMap!!.queryRenderedFeatures(screenLoc, MAPBOX_SIGN_LAYER)
            if (!mapboxSignFeatures.isEmpty()) {
                shuffleVietmapSign()
            }
        }
        return false
    }

    private fun toggleTextSize() {
        if (markerSymbolLayer != null) {
            val size: Number? = markerSymbolLayer!!.textSize.getValue()
            if (size != null) {
                markerSymbolLayer!!.setProperties(
                    if (size as Float > 10) vn.vietmap.vietmapsdk.style.layers.PropertyFactory.textSize(
                        10f
                    ) else vn.vietmap.vietmapsdk.style.layers.PropertyFactory.textSize(20f)
                )
            }
        }
    }

    private fun toggleTextField() {
        if (markerSymbolLayer != null) {
            if (TEXT_FIELD_EXPRESSION == markerSymbolLayer!!.textField.expression) {
                markerSymbolLayer!!.setProperties(vn.vietmap.vietmapsdk.style.layers.PropertyFactory.textField("āA"))
            } else {
                markerSymbolLayer!!.setProperties(vn.vietmap.vietmapsdk.style.layers.PropertyFactory.textField(TEXT_FIELD_EXPRESSION))
            }
        }
    }

    private fun toggleTextFont() {
        if (markerSymbolLayer != null) {
            if (Arrays.equals(markerSymbolLayer!!.textFont.getValue(), NORMAL_FONT_STACK)) {
                markerSymbolLayer!!.setProperties(vn.vietmap.vietmapsdk.style.layers.PropertyFactory.textFont(BOLD_FONT_STACK))
            } else {
                markerSymbolLayer!!.setProperties(vn.vietmap.vietmapsdk.style.layers.PropertyFactory.textFont(NORMAL_FONT_STACK))
            }
        }
    }

    private fun shuffleVietmapSign() {
        if (mapboxSignSymbolLayer != null) {
            mapboxSignSymbolLayer!!.setProperties(
                vn.vietmap.vietmapsdk.style.layers.PropertyFactory.textField(
                    vn.vietmap.vietmapsdk.style.expressions.Expression.format(
                        vn.vietmap.vietmapsdk.style.expressions.Expression.formatEntry("M", FormatOption.formatFontScale(2.0)),
                        getRandomColorEntryForString("a"),
                        getRandomColorEntryForString("p"),
                        getRandomColorEntryForString("b"),
                        getRandomColorEntryForString("o"),
                        getRandomColorEntryForString("x")
                    )
                ),
                vn.vietmap.vietmapsdk.style.layers.PropertyFactory.textColor(Color.BLACK),
                vn.vietmap.vietmapsdk.style.layers.PropertyFactory.textFont(BOLD_FONT_STACK),
                vn.vietmap.vietmapsdk.style.layers.PropertyFactory.textSize(25f),
                vn.vietmap.vietmapsdk.style.layers.PropertyFactory.textRotationAlignment(
                    vn.vietmap.vietmapsdk.style.layers.Property.TEXT_ROTATION_ALIGNMENT_MAP)
            )
        }
    }

    private fun getRandomColorEntryForString(string: String): FormatEntry {
        return vn.vietmap.vietmapsdk.style.expressions.Expression.formatEntry(
            string,
            FormatOption.formatTextColor(
                vn.vietmap.vietmapsdk.style.expressions.Expression.rgb(
                    random.nextInt(256),
                    random.nextInt(256),
                    random.nextInt(256)
                )
            )
        )
    }

    private fun featureProperties(id: String, title: String): JsonObject {
        val `object` = JsonObject()
        `object`.add(ID_FEATURE_PROPERTY, JsonPrimitive(id))
        `object`.add(TITLE_FEATURE_PROPERTY, JsonPrimitive(title))
        `object`.add(SELECTED_FEATURE_PROPERTY, JsonPrimitive(false))
        return `object`
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
        if (mapboxMap != null) {
            mapboxMap!!.removeOnMapClickListener(this)
        }
        mapView!!.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_symbol_layer, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_toggle_text_size -> {
                toggleTextSize()
                true
            }
            R.id.action_toggle_text_field -> {
                toggleTextField()
                true
            }
            R.id.action_toggle_text_font -> {
                toggleTextFont()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        private const val ID_FEATURE_PROPERTY = "id"
        private const val SELECTED_FEATURE_PROPERTY = "selected"
        private const val TITLE_FEATURE_PROPERTY = "title"
        private val NORMAL_FONT_STACK = arrayOf("DIN Offc Pro Regular", "Arial Unicode MS Regular")
        private val BOLD_FONT_STACK = arrayOf("DIN Offc Pro Bold", "Arial Unicode MS Regular")

        // layer & source constants
        private const val MARKER_SOURCE = "marker-source"
        private const val MARKER_LAYER = "marker-layer"
        private const val MAPBOX_SIGN_SOURCE = "mapbox-sign-source"
        private const val MAPBOX_SIGN_LAYER = "mapbox-sign-layer"
        private const val NUMBER_FORMAT_SOURCE = "mapbox-number-source"
        private const val NUMBER_FORMAT_LAYER = "mapbox-number-layer"
        private val TEXT_FIELD_EXPRESSION = vn.vietmap.vietmapsdk.style.expressions.Expression.switchCase(
            vn.vietmap.vietmapsdk.style.expressions.Expression.toBool(
                vn.vietmap.vietmapsdk.style.expressions.Expression.get(SELECTED_FEATURE_PROPERTY)),
            vn.vietmap.vietmapsdk.style.expressions.Expression.format(
                vn.vietmap.vietmapsdk.style.expressions.Expression.formatEntry(
                    vn.vietmap.vietmapsdk.style.expressions.Expression.get(TITLE_FEATURE_PROPERTY),
                    FormatOption.formatTextFont(BOLD_FONT_STACK)
                ),
                vn.vietmap.vietmapsdk.style.expressions.Expression.formatEntry("\nis fun!", FormatOption.formatFontScale(0.75))
            ),
            vn.vietmap.vietmapsdk.style.expressions.Expression.format(
                vn.vietmap.vietmapsdk.style.expressions.Expression.formatEntry("This is", FormatOption.formatFontScale(0.75)),
                vn.vietmap.vietmapsdk.style.expressions.Expression.formatEntry(
                    vn.vietmap.vietmapsdk.style.expressions.Expression.concat(
                        vn.vietmap.vietmapsdk.style.expressions.Expression.literal("\n"),
                        vn.vietmap.vietmapsdk.style.expressions.Expression.get(TITLE_FEATURE_PROPERTY)
                    ),
                    FormatOption.formatFontScale(1.25),
                    FormatOption.formatTextFont(BOLD_FONT_STACK)
                )
            )
        )
    }
}
