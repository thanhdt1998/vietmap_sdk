package vn.vietmap.vietmapsdk.testapp.activity.snapshot

import android.graphics.Color
import android.os.Bundle
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.GridLayout
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import vn.vietmap.vietmapsdk.camera.CameraPosition
import vn.vietmap.vietmapsdk.constants.VietmapConstants
import vn.vietmap.vietmapsdk.geometry.LatLng
import vn.vietmap.vietmapsdk.geometry.LatLngBounds
import vn.vietmap.vietmapsdk.maps.Style
import vn.vietmap.vietmapsdk.snapshotter.MapSnapshot
import vn.vietmap.vietmapsdk.snapshotter.MapSnapshotter
import vn.vietmap.vietmapsdk.style.expressions.Expression
import vn.vietmap.vietmapsdk.style.sources.GeoJsonSource
import vn.vietmap.vietmapsdk.style.sources.RasterSource
import vn.vietmap.vietmapsdk.style.sources.Source
import vn.vietmap.vietmapsdk.testapp.R
import vn.vietmap.vietmapsdk.utils.BitmapUtils
import timber.log.Timber
import java.util.*

/**
 * Test activity showing how to use a the [com.mapbox.vietmapsdk.snapshotter.MapSnapshotter]
 */
class MapSnapshotterActivity : AppCompatActivity() {
    lateinit var grid: GridLayout
    private val snapshotters: MutableList<vn.vietmap.vietmapsdk.snapshotter.MapSnapshotter> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_snapshotter)

        // Find the grid view and start snapshotting as soon
        // as the view is measured
        grid = findViewById(R.id.snapshot_grid)
        grid.getViewTreeObserver()
            .addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    grid.getViewTreeObserver().removeGlobalOnLayoutListener(this)
                    addSnapshots()
                }
            })
    }

    private fun addSnapshots() {
        Timber.i("Creating snapshotters")
        for (row in 0 until grid!!.rowCount) {
            for (column in 0 until grid!!.columnCount) {
                startSnapShot(row, column)
            }
        }
    }

    private fun startSnapShot(row: Int, column: Int) {
        // Optionally the style
        val builder = vn.vietmap.vietmapsdk.maps.Style.Builder()
            .fromUri(
                if ((column + row) % 2 == 0) vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Streets") else vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle(
                    "Pastel"
                )
            )

        // Define the dimensions
        val options = vn.vietmap.vietmapsdk.snapshotter.MapSnapshotter.Options(
            grid!!.measuredWidth / grid!!.columnCount,
            grid!!.measuredHeight / grid!!.rowCount
        ) // Optionally the pixel ratio
            .withPixelRatio(1f)
            .withLocalIdeographFontFamily(vn.vietmap.vietmapsdk.constants.VietmapConstants.DEFAULT_FONT)

        // Optionally the visible region
        if (row % 2 == 0) {
            options.withRegion(
                vn.vietmap.vietmapsdk.geometry.LatLngBounds.Builder()
                    .include(
                        vn.vietmap.vietmapsdk.geometry.LatLng(
                            randomInRange(-80f, 80f).toDouble(),
                            randomInRange(-160f, 160f)
                                .toDouble()
                        )
                    )
                    .include(
                        vn.vietmap.vietmapsdk.geometry.LatLng(
                            randomInRange(-80f, 80f).toDouble(),
                            randomInRange(-160f, 160f)
                                .toDouble()
                        )
                    )
                    .build()
            )
        }

        // Optionally the camera options
        if (column % 2 == 0) {
            options.withCameraPosition(
                vn.vietmap.vietmapsdk.camera.CameraPosition.Builder()
                    .target(
                        if (options.region != null) options.region!!.center else vn.vietmap.vietmapsdk.geometry.LatLng(
                            randomInRange(-80f, 80f).toDouble(),
                            randomInRange(-160f, 160f)
                                .toDouble()
                        )
                    )
                    .bearing(randomInRange(0f, 360f).toDouble())
                    .tilt(randomInRange(0f, 60f).toDouble())
                    .zoom(randomInRange(0f, 10f).toDouble())
                    .padding(1.0, 1.0, 1.0, 1.0)
                    .build()
            )
        }
        if (row == 0 && column == 0) {
            // Add a source
            val source: vn.vietmap.vietmapsdk.style.sources.Source =
                vn.vietmap.vietmapsdk.style.sources.RasterSource(
                    "my-raster-source",
                    "maptiler://sources/satellite",
                    512
                )
            builder.withLayerAbove(
                vn.vietmap.vietmapsdk.style.layers.RasterLayer(
                    "satellite-layer",
                    "my-raster-source"
                ),
                if ((column + row) % 2 == 0) "country_1" else "country_label"
            )
            builder.withSource(source)
        } else if (row == 0 && column == 2) {
            val carBitmap = vn.vietmap.vietmapsdk.utils.BitmapUtils.getBitmapFromDrawable(
                resources.getDrawable(R.drawable.ic_directions_car_black)
            )

            // marker source
            val markerCollection = FeatureCollection.fromFeatures(
                arrayOf(
                    Feature.fromGeometry(
                        Point.fromLngLat(4.91638, 52.35673),
                        featureProperties("1", "Android")
                    ),
                    Feature.fromGeometry(
                        Point.fromLngLat(4.91638, 12.34673),
                        featureProperties("2", "Car")
                    )
                )
            )
            val markerSource: vn.vietmap.vietmapsdk.style.sources.Source =
                vn.vietmap.vietmapsdk.style.sources.GeoJsonSource(
                    MARKER_SOURCE,
                    markerCollection
                )

            // marker layer
            val markerSymbolLayer = vn.vietmap.vietmapsdk.style.layers.SymbolLayer(
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
                    vn.vietmap.vietmapsdk.style.layers.PropertyFactory.iconColor(Color.BLUE)
                )
            builder.withImage("Car", Objects.requireNonNull(carBitmap)!!, false)
                .withSources(markerSource)
                .withLayers(markerSymbolLayer)
            options
                .withRegion(null)
                .withCameraPosition(
                    vn.vietmap.vietmapsdk.camera.CameraPosition.Builder()
                        .target(
                            vn.vietmap.vietmapsdk.geometry.LatLng(
                                5.537109374999999,
                                52.07950600379697
                            )
                        )
                        .zoom(1.0)
                        .padding(1.0, 1.0, 1.0, 1.0)
                        .build()
                )
        }
        options.withStyleBuilder(builder)
        val snapshotter = vn.vietmap.vietmapsdk.snapshotter.MapSnapshotter(
            this@MapSnapshotterActivity,
            options
        )
        snapshotter.setObserver(object : vn.vietmap.vietmapsdk.snapshotter.MapSnapshotter.Observer {
            override fun onDidFinishLoadingStyle() {
                Timber.i("onDidFinishLoadingStyle")
            }

            override fun onStyleImageMissing(imageName: String) {
                val androidIcon =
                    vn.vietmap.vietmapsdk.utils.BitmapUtils.getBitmapFromDrawable(resources.getDrawable(R.drawable.ic_android_2))
                snapshotter.addImage(imageName, androidIcon!!, false)
            }
        })
        snapshotter.start { snapshot: vn.vietmap.vietmapsdk.snapshotter.MapSnapshot ->
            Timber.i("Got the snapshot")
            val imageView = ImageView(this@MapSnapshotterActivity)
            imageView.setImageBitmap(snapshot.bitmap)
            grid!!.addView(
                imageView,
                GridLayout.LayoutParams(GridLayout.spec(row), GridLayout.spec(column))
            )
        }
        snapshotters.add(snapshotter)
    }

    public override fun onPause() {
        super.onPause()

        // Make sure to stop the snapshotters on pause
        for (snapshotter in snapshotters) {
            snapshotter.cancel()
        }
        snapshotters.clear()
    }

    private fun featureProperties(id: String, title: String): JsonObject {
        val `object` = JsonObject()
        `object`.add(ID_FEATURE_PROPERTY, JsonPrimitive(id))
        `object`.add(TITLE_FEATURE_PROPERTY, JsonPrimitive(title))
        `object`.add(SELECTED_FEATURE_PROPERTY, JsonPrimitive(false))
        return `object`
    }

    companion object {
        private const val ID_FEATURE_PROPERTY = "id"
        private const val SELECTED_FEATURE_PROPERTY = "selected"
        private const val TITLE_FEATURE_PROPERTY = "title"

        // layer & source constants
        private const val MARKER_SOURCE = "marker-source"
        private const val MARKER_LAYER = "marker-layer"
        private val random = Random()
        fun randomInRange(min: Float, max: Float): Float {
            return random.nextFloat() * (max - min) + min
        }
    }
}
