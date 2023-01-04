package vn.vietmap.vietmapsdk.testapp.activity.style

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import vn.vietmap.vietmapsdk.camera.CameraUpdateFactory
import vn.vietmap.vietmapsdk.geometry.LatLng
import vn.vietmap.vietmapsdk.maps.MapView
import vn.vietmap.vietmapsdk.maps.VietmapMap
import vn.vietmap.vietmapsdk.maps.OnMapReadyCallback
import vn.vietmap.vietmapsdk.maps.Style
import vn.vietmap.vietmapsdk.style.layers.Layer
import vn.vietmap.vietmapsdk.style.layers.PropertyFactory
import vn.vietmap.vietmapsdk.style.layers.SymbolLayer
import vn.vietmap.vietmapsdk.style.sources.GeoJsonSource
import vn.vietmap.vietmapsdk.testapp.R
import timber.log.Timber

/**
 * Test activity showcasing adding a sprite image and use it in a Symbol Layer
 */
class CustomSpriteActivity : AppCompatActivity() {
    private var source: vn.vietmap.vietmapsdk.style.sources.GeoJsonSource? = null
    private var mapboxMap: vn.vietmap.vietmapsdk.maps.VietmapMap? = null
    private lateinit var mapView: vn.vietmap.vietmapsdk.maps.MapView
    private lateinit var layer: vn.vietmap.vietmapsdk.style.layers.Layer
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_sprite)
        mapView = findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(
            vn.vietmap.vietmapsdk.maps.OnMapReadyCallback { map: vn.vietmap.vietmapsdk.maps.VietmapMap ->
                mapboxMap = map
                map.setStyle(
                    vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle(
                        "Streets"
                    )
                ) { style: vn.vietmap.vietmapsdk.maps.Style ->
                    val fab = findViewById<FloatingActionButton>(R.id.fab)
                    fab.setColorFilter(
                        ContextCompat.getColor(
                            this@CustomSpriteActivity,
                            R.color.primary
                        )
                    )
                    fab.setOnClickListener(object : View.OnClickListener {
                        private lateinit var point: Point
                        override fun onClick(view: View) {
                            if (point == null) {
                                Timber.i("First click -> Car")
                                // Add an icon to reference later
                                style.addImage(
                                    CUSTOM_ICON,
                                    BitmapFactory.decodeResource(
                                        resources,
                                        R.drawable.ic_car_top
                                    )
                                )

                                // Add a source with a geojson point
                                point = Point.fromLngLat(13.400972, 52.519003)
                                source =
                                    vn.vietmap.vietmapsdk.style.sources.GeoJsonSource(
                                        "point",
                                        FeatureCollection.fromFeatures(
                                            arrayOf(
                                                Feature.fromGeometry(
                                                    point
                                                )
                                            )
                                        )
                                    )
                                mapboxMap!!.style!!.addSource(source!!)

                                // Add a symbol layer that references that point source
                                layer =
                                    vn.vietmap.vietmapsdk.style.layers.SymbolLayer(
                                        "layer",
                                        "point"
                                    )
                                layer.setProperties( // Set the id of the sprite to use
                                    vn.vietmap.vietmapsdk.style.layers.PropertyFactory.iconImage(
                                        CUSTOM_ICON
                                    ),
                                    vn.vietmap.vietmapsdk.style.layers.PropertyFactory.iconAllowOverlap(
                                        true
                                    ),
                                    vn.vietmap.vietmapsdk.style.layers.PropertyFactory.iconIgnorePlacement(
                                        true
                                    )
                                )

                                // lets add a circle below labels!
                                mapboxMap!!.style!!.addLayerBelow(layer, "water_intermittent")
                                fab.setImageResource(R.drawable.ic_directions_car_black)
                            } else {
                                // Update point
                                point = Point.fromLngLat(
                                    point!!.longitude() + 0.001,
                                    point!!.latitude() + 0.001
                                )
                                source!!.setGeoJson(
                                    FeatureCollection.fromFeatures(
                                        arrayOf(
                                            Feature.fromGeometry(
                                                point
                                            )
                                        )
                                    )
                                )

                                // Move the camera as well
                                mapboxMap!!.moveCamera(
                                    vn.vietmap.vietmapsdk.camera.CameraUpdateFactory.newLatLng(
                                        vn.vietmap.vietmapsdk.geometry.LatLng(
                                            point.latitude(),
                                            point.longitude()
                                        )
                                    )
                                )
                            }
                        }
                    })
                }
            }
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
        private const val CUSTOM_ICON = "custom-icon"
    }
}
