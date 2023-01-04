package vn.vietmap.vietmapsdk.testapp.activity.style

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import vn.vietmap.vietmapsdk.maps.MapView
import vn.vietmap.vietmapsdk.maps.MapView.OnDidFinishLoadingStyleListener
import vn.vietmap.vietmapsdk.maps.VietmapMap
import vn.vietmap.vietmapsdk.maps.OnMapReadyCallback
import vn.vietmap.vietmapsdk.maps.Style
import vn.vietmap.vietmapsdk.style.expressions.Expression
import vn.vietmap.vietmapsdk.style.layers.CircleLayer
import vn.vietmap.vietmapsdk.style.layers.PropertyFactory
import vn.vietmap.vietmapsdk.style.layers.SymbolLayer
import vn.vietmap.vietmapsdk.style.sources.GeoJsonOptions
import vn.vietmap.vietmapsdk.style.sources.GeoJsonSource
import vn.vietmap.vietmapsdk.testapp.R
import timber.log.Timber
import java.net.URI
import java.net.URISyntaxException

/**
 * Test activity showcasing adding a Circle Layer to the Map
 *
 *
 * Uses bus stop data from Singapore as a source and allows to filter into 1 specific route with a line layer.
 *
 */
class CircleLayerActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var mapView: vn.vietmap.vietmapsdk.maps.MapView
    private var mapboxMap: vn.vietmap.vietmapsdk.maps.VietmapMap? = null
    private lateinit var styleFab: FloatingActionButton
    private lateinit var routeFab: FloatingActionButton
    private var layer: vn.vietmap.vietmapsdk.style.layers.CircleLayer? = null
    private var source: vn.vietmap.vietmapsdk.style.sources.GeoJsonSource? = null
    private var currentStyleIndex = 0
    private var isLoadingStyle = true
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_circle_layer)
        mapView = findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(
            vn.vietmap.vietmapsdk.maps.OnMapReadyCallback { map: vn.vietmap.vietmapsdk.maps.VietmapMap? ->
                mapboxMap = map
                mapboxMap!!.setStyle(
                    vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle(
                        "Satellite Hybrid"
                    )
                )
                mapView.addOnDidFinishLoadingStyleListener(
                    OnDidFinishLoadingStyleListener {
                        val style = mapboxMap!!.style
                        addBusStopSource(style)
                        addBusStopCircleLayer(style)
                        initFloatingActionButtons()
                        isLoadingStyle = false
                    }
                )
            }
        )
    }

    private fun addBusStopSource(style: vn.vietmap.vietmapsdk.maps.Style?) {
        try {
            source = vn.vietmap.vietmapsdk.style.sources.GeoJsonSource(
                SOURCE_ID,
                URI(URL_BUS_ROUTES)
            )
        } catch (exception: URISyntaxException) {
            Timber.e(exception, "That's not an url... ")
        }
        style!!.addSource(source!!)
    }

    private fun addBusStopCircleLayer(style: vn.vietmap.vietmapsdk.maps.Style?) {
        layer =
            vn.vietmap.vietmapsdk.style.layers.CircleLayer(LAYER_ID, SOURCE_ID)
        layer!!.setProperties(
            vn.vietmap.vietmapsdk.style.layers.PropertyFactory.circleColor(Color.parseColor("#FF9800")),
            vn.vietmap.vietmapsdk.style.layers.PropertyFactory.circleRadius(2.0f)
        )
        style!!.addLayerBelow(layer!!, "water_intermittent")
    }

    private fun initFloatingActionButtons() {
        routeFab = findViewById(R.id.fab_route)
        routeFab.setColorFilter(ContextCompat.getColor(this@CircleLayerActivity, R.color.primary))
        routeFab.setOnClickListener(this@CircleLayerActivity)
        styleFab = findViewById(R.id.fab_style)
        styleFab.setOnClickListener(this@CircleLayerActivity)
    }

    override fun onClick(view: View) {
        if (isLoadingStyle) {
            return
        }
        if (view.id == R.id.fab_route) {
            showBusCluster()
        } else if (view.id == R.id.fab_style) {
            changeMapStyle()
        }
    }

    private fun showBusCluster() {
        removeFabs()
        removeOldSource()
        addClusteredSource()
    }

    private fun removeOldSource() {
        mapboxMap!!.style!!.removeSource(SOURCE_ID)
        mapboxMap!!.style!!.removeLayer(LAYER_ID)
    }

    private fun addClusteredSource() {
        try {
            mapboxMap!!.style!!.addSource(
                vn.vietmap.vietmapsdk.style.sources.GeoJsonSource(
                    SOURCE_ID_CLUSTER,
                    URI(URL_BUS_ROUTES),
                    vn.vietmap.vietmapsdk.style.sources.GeoJsonOptions()
                        .withCluster(true)
                        .withClusterMaxZoom(14)
                        .withClusterRadius(50)
                )
            )
        } catch (malformedUrlException: URISyntaxException) {
            Timber.e(malformedUrlException, "That's not an url... ")
        }

        // Add unclustered layer
        val layers = arrayOf(
            intArrayOf(
                150,
                ResourcesCompat.getColor(
                    resources,
                    R.color.redAccent,
                    theme
                )
            ),
            intArrayOf(20, ResourcesCompat.getColor(resources, R.color.greenAccent, theme)),
            intArrayOf(
                0,
                ResourcesCompat.getColor(
                    resources,
                    R.color.blueAccent,
                    theme
                )
            )
        )
        val unclustered = vn.vietmap.vietmapsdk.style.layers.SymbolLayer(
            "unclustered-points",
            SOURCE_ID_CLUSTER
        )
        unclustered.setProperties(
            vn.vietmap.vietmapsdk.style.layers.PropertyFactory.iconImage("bus-15")
        )
        mapboxMap!!.style!!.addLayer(unclustered)
        for (i in layers.indices) {
            // Add some nice circles
            val circles = vn.vietmap.vietmapsdk.style.layers.CircleLayer(
                "cluster-$i",
                SOURCE_ID_CLUSTER
            )
            circles.setProperties(
                vn.vietmap.vietmapsdk.style.layers.PropertyFactory.circleColor(layers[i][1]),
                vn.vietmap.vietmapsdk.style.layers.PropertyFactory.circleRadius(18f)
            )
            val pointCount = vn.vietmap.vietmapsdk.style.expressions.Expression.toNumber(
                vn.vietmap.vietmapsdk.style.expressions.Expression.get("point_count"))
            circles.setFilter(
                if (i == 0) vn.vietmap.vietmapsdk.style.expressions.Expression.all(
                    vn.vietmap.vietmapsdk.style.expressions.Expression.has("point_count"),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.gte(
                        pointCount,
                        vn.vietmap.vietmapsdk.style.expressions.Expression.literal(
                            layers[i][0]
                        )
                    )
                ) else vn.vietmap.vietmapsdk.style.expressions.Expression.all(
                    vn.vietmap.vietmapsdk.style.expressions.Expression.has("point_count"),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.gt(
                        pointCount,
                        vn.vietmap.vietmapsdk.style.expressions.Expression.literal(
                            layers[i][0]
                        )
                    ),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.lt(
                        pointCount,
                        vn.vietmap.vietmapsdk.style.expressions.Expression.literal(
                            layers[i - 1][0]
                        )
                    )
                )
            )
            mapboxMap!!.style!!.addLayer(circles)
        }

        // Add the count labels
        val count = vn.vietmap.vietmapsdk.style.layers.SymbolLayer(
            "count",
            SOURCE_ID_CLUSTER
        )
        count.setProperties(
            vn.vietmap.vietmapsdk.style.layers.PropertyFactory.textField(
                vn.vietmap.vietmapsdk.style.expressions.Expression.toString(
                    vn.vietmap.vietmapsdk.style.expressions.Expression.get("point_count"))),
            vn.vietmap.vietmapsdk.style.layers.PropertyFactory.textSize(12f),
            vn.vietmap.vietmapsdk.style.layers.PropertyFactory.textColor(Color.WHITE),
            vn.vietmap.vietmapsdk.style.layers.PropertyFactory.textIgnorePlacement(true),
            vn.vietmap.vietmapsdk.style.layers.PropertyFactory.textAllowOverlap(true)
        )
        mapboxMap!!.style!!.addLayer(count)
    }

    @SuppressLint("RestrictedApi")
    private fun removeFabs() {
        routeFab!!.visibility = View.GONE
        styleFab!!.visibility = View.GONE
    }

    private fun changeMapStyle() {
        isLoadingStyle = true
        removeBusStop()
        loadNewStyle()
    }

    private fun removeBusStop() {
        mapboxMap!!.style!!.removeLayer(layer!!)
        mapboxMap!!.style!!.removeSource(source!!)
    }

    private fun loadNewStyle() {
        mapboxMap!!.setStyle(vn.vietmap.vietmapsdk.maps.Style.Builder().fromUri(nextStyle))
    }

    private fun addBusStop() {
        mapboxMap!!.style!!.addLayer(layer!!)
        mapboxMap!!.style!!.addSource(source!!)
    }

    private val nextStyle: String
        private get() {
            currentStyleIndex++
            if (currentStyleIndex == Data.STYLES.size) {
                currentStyleIndex = 0
            }
            return Data.STYLES[currentStyleIndex]
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

    private object Data {
        val STYLES = arrayOf(
            vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Streets"),
            vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Outdoor"),
            vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Bright"),
            vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Pastel"),
            vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Satellite Hybrid"),
            vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Satellite Hybrid")
        )
    }

    companion object {
        const val SOURCE_ID = "bus_stop"
        const val SOURCE_ID_CLUSTER = "bus_stop_cluster"
        const val URL_BUS_ROUTES =
            "https://raw.githubusercontent.com/cheeaun/busrouter-sg/master/data/2/bus-stops.geojson"
        const val LAYER_ID = "stops_layer"
    }
}
