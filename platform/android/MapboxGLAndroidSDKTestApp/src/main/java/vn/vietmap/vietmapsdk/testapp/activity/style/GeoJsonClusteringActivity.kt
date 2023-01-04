package vn.vietmap.vietmapsdk.testapp.activity.style

import android.graphics.Color
import android.graphics.Point
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.mapbox.geojson.Feature
import vn.vietmap.vietmapsdk.camera.CameraUpdateFactory
import vn.vietmap.vietmapsdk.geometry.LatLng
import vn.vietmap.vietmapsdk.maps.MapView
import vn.vietmap.vietmapsdk.maps.VietmapMap
import vn.vietmap.vietmapsdk.maps.OnMapReadyCallback
import vn.vietmap.vietmapsdk.maps.Style
import vn.vietmap.vietmapsdk.style.expressions.Expression
import vn.vietmap.vietmapsdk.style.sources.GeoJsonOptions
import vn.vietmap.vietmapsdk.style.sources.GeoJsonSource
import vn.vietmap.vietmapsdk.testapp.R
import vn.vietmap.vietmapsdk.utils.BitmapUtils
import timber.log.Timber
import java.net.URI
import java.net.URISyntaxException
import java.util.*

/**
 * Test activity showcasing using a geojson source and visualise that source as a cluster by using filters.
 */
class GeoJsonClusteringActivity : AppCompatActivity() {
    private lateinit var mapView: vn.vietmap.vietmapsdk.maps.MapView
    private var mapboxMap: vn.vietmap.vietmapsdk.maps.VietmapMap? = null
    private var clusterSource: vn.vietmap.vietmapsdk.style.sources.GeoJsonSource? = null
    private var clickOptionCounter = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_geojson_clustering)

        // Initialize map as normal
        mapView = findViewById(R.id.mapView)
        // noinspection ConstantConditions
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(
            vn.vietmap.vietmapsdk.maps.OnMapReadyCallback { map: vn.vietmap.vietmapsdk.maps.VietmapMap? ->
                mapboxMap = map
                mapboxMap!!.animateCamera(
                    vn.vietmap.vietmapsdk.camera.CameraUpdateFactory.newLatLngZoom(
                        vn.vietmap.vietmapsdk.geometry.LatLng(
                            37.7749,
                            122.4194
                        ),
                        0.0
                    )
                )
                val clusterLayers = arrayOf(
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
                try {
                    mapboxMap!!.setStyle(
                        vn.vietmap.vietmapsdk.maps.Style.Builder()
                            .fromUri(
                                vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle(
                                    "Bright"
                                )
                            )
                            .withSource(createClusterSource().also { clusterSource = it })
                            .withLayer(createSymbolLayer())
                            .withLayer(createClusterLevelLayer(0, clusterLayers))
                            .withLayer(createClusterLevelLayer(1, clusterLayers))
                            .withLayer(createClusterLevelLayer(2, clusterLayers))
                            .withLayer(createClusterTextLayer())
                            .withImage(
                                "icon-id",
                                Objects.requireNonNull(
                                    vn.vietmap.vietmapsdk.utils.BitmapUtils.getBitmapFromDrawable(
                                        resources.getDrawable(R.drawable.ic_hearing_black_24dp)
                                    )
                                )!!,
                                true
                            )
                    )
                } catch (exception: URISyntaxException) {
                    Timber.e(exception)
                }
                mapboxMap!!.addOnMapClickListener { latLng: vn.vietmap.vietmapsdk.geometry.LatLng? ->
                    val point = mapboxMap!!.projection.toScreenLocation(latLng!!)
                    val features =
                        mapboxMap!!.queryRenderedFeatures(
                            point,
                            "cluster-0",
                            "cluster-1",
                            "cluster-2"
                        )
                    if (!features.isEmpty()) {
                        onClusterClick(features[0], Point(point.x.toInt(), point.y.toInt()))
                    }
                    true
                }
            }
        )
        findViewById<View>(R.id.fab).setOnClickListener { v: View? ->
            updateClickOptionCounter()
            notifyClickOptionUpdate()
        }
    }

    private fun onClusterClick(cluster: Feature, clickPoint: Point) {
        if (clickOptionCounter == 0) {
            val nextZoomLevel = clusterSource!!.getClusterExpansionZoom(cluster).toDouble()
            val zoomDelta = nextZoomLevel - mapboxMap!!.cameraPosition.zoom
            mapboxMap!!.animateCamera(
                vn.vietmap.vietmapsdk.camera.CameraUpdateFactory.zoomBy(
                    zoomDelta + CAMERA_ZOOM_DELTA,
                    clickPoint
                )
            )
            Toast.makeText(this, "Zooming to $nextZoomLevel", Toast.LENGTH_SHORT).show()
        } else if (clickOptionCounter == 1) {
            val collection = clusterSource!!.getClusterChildren(cluster)
            Toast.makeText(this, "Children: " + collection.toJson(), Toast.LENGTH_SHORT).show()
        } else {
            val collection = clusterSource!!.getClusterLeaves(cluster, 2, 1)
            Toast.makeText(this, "Leaves: " + collection.toJson(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun createClusterSource(): vn.vietmap.vietmapsdk.style.sources.GeoJsonSource {
        return vn.vietmap.vietmapsdk.style.sources.GeoJsonSource(
            "earthquakes",
            URI("asset://earthquakes.geojson"),
            vn.vietmap.vietmapsdk.style.sources.GeoJsonOptions()
                .withCluster(true)
                .withClusterMaxZoom(14)
                .withClusterRadius(50)
                .withClusterProperty(
                    "max",
                    vn.vietmap.vietmapsdk.style.expressions.Expression.max(
                        Expression.accumulated(),
                        vn.vietmap.vietmapsdk.style.expressions.Expression.get("max")
                    ),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.get("mag")
                )
                .withClusterProperty(
                    "sum",
                    Expression.literal("+"),
                    Expression.get("mag")
                )
                .withClusterProperty(
                    "felt",Expression.literal("any"),
                    Expression.neq(
                        vn.vietmap.vietmapsdk.style.expressions.Expression.get("felt"),
                       Expression.literal(
                            "null"
                        )
                    )
                )
        )
    }

    private fun createSymbolLayer(): vn.vietmap.vietmapsdk.style.layers.SymbolLayer {
        return vn.vietmap.vietmapsdk.style.layers.SymbolLayer(
            "unclustered-points",
            "earthquakes"
        )
            .withProperties(
                vn.vietmap.vietmapsdk.style.layers.PropertyFactory.iconImage("icon-id"),
                vn.vietmap.vietmapsdk.style.layers.PropertyFactory.iconSize(
                    vn.vietmap.vietmapsdk.style.expressions.Expression.division(
                        vn.vietmap.vietmapsdk.style.expressions.Expression.get("mag"),
                        vn.vietmap.vietmapsdk.style.expressions.Expression.literal(4.0f)
                    )
                ),
                vn.vietmap.vietmapsdk.style.layers.PropertyFactory.iconColor(
                    vn.vietmap.vietmapsdk.style.expressions.Expression.interpolate(
                        vn.vietmap.vietmapsdk.style.expressions.Expression.exponential(1),
                        vn.vietmap.vietmapsdk.style.expressions.Expression.get("mag"),
                        vn.vietmap.vietmapsdk.style.expressions.Expression.stop(2.0, vn.vietmap.vietmapsdk.style.expressions.Expression.rgb(0, 255, 0)),
                        vn.vietmap.vietmapsdk.style.expressions.Expression.stop(4.5, vn.vietmap.vietmapsdk.style.expressions.Expression.rgb(0, 0, 255)),
                        vn.vietmap.vietmapsdk.style.expressions.Expression.stop(7.0, vn.vietmap.vietmapsdk.style.expressions.Expression.rgb(255, 0, 0))
                    )
                )
            )
            .withFilter(vn.vietmap.vietmapsdk.style.expressions.Expression.has("mag"))
    }

    private fun createClusterLevelLayer(level: Int, layerColors: Array<IntArray>): vn.vietmap.vietmapsdk.style.layers.CircleLayer {
        val circles = vn.vietmap.vietmapsdk.style.layers.CircleLayer(
            "cluster-$level",
            "earthquakes"
        )
        circles.setProperties(
            vn.vietmap.vietmapsdk.style.layers.PropertyFactory.circleColor(layerColors[level][1]),
            vn.vietmap.vietmapsdk.style.layers.PropertyFactory.circleRadius(18f)
        )
        val pointCount = vn.vietmap.vietmapsdk.style.expressions.Expression.toNumber(
            vn.vietmap.vietmapsdk.style.expressions.Expression.get("point_count"))
        circles.setFilter(
            if (level == 0) vn.vietmap.vietmapsdk.style.expressions.Expression.all(
                vn.vietmap.vietmapsdk.style.expressions.Expression.has("point_count"),
                vn.vietmap.vietmapsdk.style.expressions.Expression.gte(
                    pointCount,
                    vn.vietmap.vietmapsdk.style.expressions.Expression.literal(
                        layerColors[level][0]
                    )
                )
            ) else vn.vietmap.vietmapsdk.style.expressions.Expression.all(
                vn.vietmap.vietmapsdk.style.expressions.Expression.has("point_count"),
                vn.vietmap.vietmapsdk.style.expressions.Expression.gt(
                    pointCount,
                    vn.vietmap.vietmapsdk.style.expressions.Expression.literal(
                        layerColors[level][0]
                    )
                ),
                vn.vietmap.vietmapsdk.style.expressions.Expression.lt(
                    pointCount,
                    vn.vietmap.vietmapsdk.style.expressions.Expression.literal(
                        layerColors[level - 1][0]
                    )
                )
            )
        )
        return circles
    }

    private fun createClusterTextLayer(): vn.vietmap.vietmapsdk.style.layers.SymbolLayer {
        return vn.vietmap.vietmapsdk.style.layers.SymbolLayer(
            "property",
            "earthquakes"
        )
            .withProperties(
                vn.vietmap.vietmapsdk.style.layers.PropertyFactory.textField(
                    vn.vietmap.vietmapsdk.style.expressions.Expression.concat(
                        vn.vietmap.vietmapsdk.style.expressions.Expression.get("point_count"),
                        vn.vietmap.vietmapsdk.style.expressions.Expression.literal(", "),
                        vn.vietmap.vietmapsdk.style.expressions.Expression.get("max")
                    )
                ),
                vn.vietmap.vietmapsdk.style.layers.PropertyFactory.textSize(12f),
                vn.vietmap.vietmapsdk.style.layers.PropertyFactory.textColor(Color.WHITE),
                vn.vietmap.vietmapsdk.style.layers.PropertyFactory.textIgnorePlacement(true),
                vn.vietmap.vietmapsdk.style.layers.PropertyFactory.textAllowOverlap(true)
            )
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun updateClickOptionCounter() {
        if (clickOptionCounter == 2) {
            clickOptionCounter = 0
        } else {
            clickOptionCounter++
        }
    }

    private fun notifyClickOptionUpdate() {
        if (clickOptionCounter == 0) {
            Toast.makeText(
                this@GeoJsonClusteringActivity,
                "Clicking a cluster will zoom to the level where it dissolves",
                Toast.LENGTH_SHORT
            ).show()
        } else if (clickOptionCounter == 1) {
            Toast.makeText(
                this@GeoJsonClusteringActivity,
                "Clicking a cluster will show the details of the cluster children",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(
                this@GeoJsonClusteringActivity,
                "Clicking a cluster will show the details of the cluster leaves with an offset and limit",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    companion object {
        private const val CAMERA_ZOOM_DELTA = 0.01
    }
}
