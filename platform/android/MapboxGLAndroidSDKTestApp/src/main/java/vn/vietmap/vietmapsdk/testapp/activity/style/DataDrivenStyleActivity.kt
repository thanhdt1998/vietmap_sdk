package vn.vietmap.vietmapsdk.testapp.activity.style

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import vn.vietmap.vietmapsdk.camera.CameraUpdateFactory
import vn.vietmap.vietmapsdk.geometry.LatLng
import vn.vietmap.vietmapsdk.maps.MapView
import vn.vietmap.vietmapsdk.maps.VietmapMap
import vn.vietmap.vietmapsdk.maps.OnMapReadyCallback
import vn.vietmap.vietmapsdk.maps.Style
import vn.vietmap.vietmapsdk.style.expressions.Expression
import vn.vietmap.vietmapsdk.style.layers.FillLayer
import vn.vietmap.vietmapsdk.style.layers.PropertyFactory
import vn.vietmap.vietmapsdk.style.sources.GeoJsonSource
import vn.vietmap.vietmapsdk.style.sources.Source
import vn.vietmap.vietmapsdk.testapp.R
import vn.vietmap.vietmapsdk.testapp.utils.IdleZoomListener
import vn.vietmap.vietmapsdk.testapp.utils.ResourceUtils
import timber.log.Timber
import java.io.IOException

/**
 * Test activity showcasing the data driven runtime style API.
 */
class DataDrivenStyleActivity : AppCompatActivity() {
    private lateinit var mapView: vn.vietmap.vietmapsdk.maps.MapView
    private var mapboxMap: vn.vietmap.vietmapsdk.maps.VietmapMap? = null
    private var idleListener: vn.vietmap.vietmapsdk.testapp.utils.IdleZoomListener? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_driven_style)

        // Initialize map as normal
        mapView = findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(
            vn.vietmap.vietmapsdk.maps.OnMapReadyCallback { map: vn.vietmap.vietmapsdk.maps.VietmapMap? ->
                // Store for later
                mapboxMap = map
                mapboxMap!!.setStyle(
                    vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle(
                        "Streets"
                    )
                ) { style: vn.vietmap.vietmapsdk.maps.Style? ->
                    // Add a parks layer
                    addParksLayer()

                    // Add debug overlay
                    setupDebugZoomView()
                }

                // Center and Zoom (Amsterdam, zoomed to streets)
                mapboxMap!!.animateCamera(
                    vn.vietmap.vietmapsdk.camera.CameraUpdateFactory.newLatLngZoom(
                        vn.vietmap.vietmapsdk.geometry.LatLng(
                            52.379189,
                            4.899431
                        ),
                        14.0
                    )
                )
            }
        )
    }

    private fun setupDebugZoomView() {
        val textView = findViewById<View>(R.id.textZoom) as TextView
        mapboxMap!!.addOnCameraIdleListener(
            vn.vietmap.vietmapsdk.testapp.utils.IdleZoomListener(
                mapboxMap,
                textView
            ).also { idleListener = it }
        )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_data_driven_style, menu)
        return true
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
        if (mapboxMap != null && idleListener != null) {
            mapboxMap!!.removeOnCameraIdleListener(idleListener!!)
        }
        mapView!!.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView!!.onLowMemory()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add_exponential_zoom_function -> {
                addExponentialZoomFunction()
                true
            }
            R.id.action_add_interval_zoom_function -> {
                addIntervalZoomFunction()
                true
            }
            R.id.action_add_categorical_source_function -> {
                addCategoricalSourceFunction()
                true
            }
            R.id.action_add_exponential_source_function -> {
                addExponentialSourceFunction()
                true
            }
            R.id.action_add_identity_source_function -> {
                addIdentitySourceFunction()
                true
            }
            R.id.action_add_interval_source_function -> {
                addIntervalSourceFunction()
                true
            }
            R.id.action_add_composite_categorical_function -> {
                addCompositeCategoricalFunction()
                true
            }
            R.id.action_add_composite_exponential_function -> {
                addCompositeExponentialFunction()
                true
            }
            R.id.action_add_composite_interval_function -> {
                addCompositeIntervalFunction()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addExponentialZoomFunction() {
        Timber.i("Add exponential zoom function")
        val layer = mapboxMap!!.style!!.getLayerAs<vn.vietmap.vietmapsdk.style.layers.FillLayer>("water")!!
        layer.setProperties(
            vn.vietmap.vietmapsdk.style.layers.PropertyFactory.fillColor(
                vn.vietmap.vietmapsdk.style.expressions.Expression.interpolate(
                    vn.vietmap.vietmapsdk.style.expressions.Expression.exponential(0.5f),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.zoom(),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.stop(1, vn.vietmap.vietmapsdk.style.expressions.Expression.color(Color.RED)),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.stop(5, vn.vietmap.vietmapsdk.style.expressions.Expression.color(Color.BLUE)),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.stop(10, vn.vietmap.vietmapsdk.style.expressions.Expression.color(Color.GREEN))
                )
            )
        )
        Timber.i("Fill color: %s", layer.fillColor)
    }

    private fun addIntervalZoomFunction() {
        Timber.i("Add interval zoom function")
        val layer = mapboxMap!!.style!!.getLayerAs<vn.vietmap.vietmapsdk.style.layers.FillLayer>("water")!!
        layer.setProperties(
            vn.vietmap.vietmapsdk.style.layers.PropertyFactory.fillColor(
                vn.vietmap.vietmapsdk.style.expressions.Expression.step(
                    vn.vietmap.vietmapsdk.style.expressions.Expression.zoom(),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(0.0f, 255.0f, 255.0f, 1.0f),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.stop(1, vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(255.0f, 0.0f, 0.0f, 1.0f)),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.stop(5, vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(0.0f, 0.0f, 255.0f, 1.0f)),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.stop(10, vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(0.0f, 255.0f, 0.0f, 1.0f))
                )
            )
        )
        Timber.i("Fill color: %s", layer.fillColor)
    }

    private fun addExponentialSourceFunction() {
        Timber.i("Add exponential source function")
        val layer = mapboxMap!!.style!!.getLayerAs<vn.vietmap.vietmapsdk.style.layers.FillLayer>(AMSTERDAM_PARKS_LAYER)!!
        layer.setProperties(
            vn.vietmap.vietmapsdk.style.layers.PropertyFactory.fillColor(
                vn.vietmap.vietmapsdk.style.expressions.Expression.interpolate(
                    vn.vietmap.vietmapsdk.style.expressions.Expression.exponential(0.5f),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.get("stroke-width"),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.stop(1f, vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(255.0f, 0.0f, 0.0f, 1.0f)),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.stop(5f, vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(0.0f, 0.0f, 255.0f, 1.0f)),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.stop(10f, vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(0.0f, 255.0f, 0.0f, 1.0f))
                )
            )
        )
        Timber.i("Fill color: %s", layer.fillColor)
    }

    private fun addCategoricalSourceFunction() {
        Timber.i("Add categorical source function")
        val layer = mapboxMap!!.style!!.getLayerAs<vn.vietmap.vietmapsdk.style.layers.FillLayer>(AMSTERDAM_PARKS_LAYER)!!
        layer.setProperties(
            vn.vietmap.vietmapsdk.style.layers.PropertyFactory.fillColor(
                vn.vietmap.vietmapsdk.style.expressions.Expression.match(
                    vn.vietmap.vietmapsdk.style.expressions.Expression.get("name"),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.literal("Westerpark"),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(255.0f, 0.0f, 0.0f, 1.0f),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.literal("Jordaan"),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(0.0f, 0.0f, 255.0f, 1.0f),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.literal("Prinseneiland"),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(0.0f, 255.0f, 0.0f, 1.0f),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(0.0f, 255.0f, 255.0f, 1.0f)
                )
            )
        )
        Timber.i("Fill color: %s", layer.fillColor)
    }

    private fun addIdentitySourceFunction() {
        Timber.i("Add identity source function")
        val layer = mapboxMap!!.style!!.getLayerAs<vn.vietmap.vietmapsdk.style.layers.FillLayer>(AMSTERDAM_PARKS_LAYER)!!
        layer.setProperties(
            vn.vietmap.vietmapsdk.style.layers.PropertyFactory.fillOpacity(
                vn.vietmap.vietmapsdk.style.expressions.Expression.get("fill-opacity")
            )
        )
        Timber.i("Fill opacity: %s", layer.fillOpacity)
    }

    private fun addIntervalSourceFunction() {
        Timber.i("Add interval source function")
        val layer = mapboxMap!!.style!!.getLayerAs<vn.vietmap.vietmapsdk.style.layers.FillLayer>(AMSTERDAM_PARKS_LAYER)!!
        layer.setProperties(
            vn.vietmap.vietmapsdk.style.layers.PropertyFactory.fillColor(
                vn.vietmap.vietmapsdk.style.expressions.Expression.step(
                    vn.vietmap.vietmapsdk.style.expressions.Expression.get("stroke-width"),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(0.0f, 255.0f, 255.0f, 1.0f),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.stop(1f, vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(255.0f, 0.0f, 0.0f, 1.0f)),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.stop(2f, vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(0.0f, 0.0f, 255.0f, 1.0f)),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.stop(3f, vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(0.0f, 255.0f, 0.0f, 1.0f))
                )
            )
        )
        Timber.i("Fill color: %s", layer.fillColor)
    }

    private fun addCompositeExponentialFunction() {
        Timber.i("Add composite exponential function")
        val layer = mapboxMap!!.style!!.getLayerAs<vn.vietmap.vietmapsdk.style.layers.FillLayer>(AMSTERDAM_PARKS_LAYER)!!
        layer.setProperties(
            vn.vietmap.vietmapsdk.style.layers.PropertyFactory.fillColor(
                vn.vietmap.vietmapsdk.style.expressions.Expression.interpolate(
                    vn.vietmap.vietmapsdk.style.expressions.Expression.exponential(1f),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.zoom(),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.stop(
                        12,
                        vn.vietmap.vietmapsdk.style.expressions.Expression.step(
                            vn.vietmap.vietmapsdk.style.expressions.Expression.get("stroke-width"),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(255.0f, 255.0f, 255.0f, 1.0f),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.stop(1f, vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(255.0f, 0.0f, 0.0f, 1.0f)),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.stop(2f, vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(0.0f, 0.0f, 0.0f, 1.0f)),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.stop(3f, vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(0.0f, 0.0f, 255.0f, 1.0f))
                        )
                    ),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.stop(
                        15,
                        vn.vietmap.vietmapsdk.style.expressions.Expression.step(
                            vn.vietmap.vietmapsdk.style.expressions.Expression.get("stroke-width"),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(255.0f, 255.0f, 255.0f, 1.0f),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.stop(1f, vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(255.0f, 255.0f, 0.0f, 1.0f)),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.stop(2f, vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(211.0f, 211.0f, 211.0f, 1.0f)),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.stop(3f, vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(0.0f, 255.0f, 255.0f, 1.0f))
                        )
                    ),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.stop(
                        18,
                        vn.vietmap.vietmapsdk.style.expressions.Expression.step(
                            vn.vietmap.vietmapsdk.style.expressions.Expression.get("stroke-width"),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(255.0f, 255.0f, 255.0f, 1.0f),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.stop(1f, vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(0.0f, 0.0f, 0.0f, 1.0f)),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.stop(2f, vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(128.0f, 128.0f, 128.0f, 1.0f)),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.stop(3f, vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(0.0f, 255.0f, 0.0f, 1.0f))
                        )
                    )
                )
            )
        )
        Timber.i("Fill color: %s", layer.fillColor)
    }

    private fun addCompositeIntervalFunction() {
        Timber.i("Add composite interval function")
        val layer = mapboxMap!!.style!!.getLayerAs<vn.vietmap.vietmapsdk.style.layers.FillLayer>(AMSTERDAM_PARKS_LAYER)!!
        layer.setProperties(
            vn.vietmap.vietmapsdk.style.layers.PropertyFactory.fillColor(
                vn.vietmap.vietmapsdk.style.expressions.Expression.interpolate(
                    vn.vietmap.vietmapsdk.style.expressions.Expression.linear(),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.zoom(),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.stop(
                        12,
                        vn.vietmap.vietmapsdk.style.expressions.Expression.step(
                            vn.vietmap.vietmapsdk.style.expressions.Expression.get("stroke-width"),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(255.0f, 255.0f, 255.0f, 1.0f),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.stop(1f, vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(255.0f, 0.0f, 0.0f, 1.0f)),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.stop(2f, vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(0.0f, 0.0f, 0.0f, 1.0f)),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.stop(3f, vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(0.0f, 0.0f, 255.0f, 1.0f))
                        )
                    ),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.stop(
                        15,
                        vn.vietmap.vietmapsdk.style.expressions.Expression.step(
                            vn.vietmap.vietmapsdk.style.expressions.Expression.get("stroke-width"),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(255.0f, 255.0f, 255.0f, 1.0f),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.stop(1f, vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(255.0f, 255.0f, 0.0f, 1.0f)),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.stop(2f, vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(211.0f, 211.0f, 211.0f, 1.0f)),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.stop(3f, vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(0.0f, 255.0f, 255.0f, 1.0f))
                        )
                    ),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.stop(
                        18,
                        vn.vietmap.vietmapsdk.style.expressions.Expression.step(
                            vn.vietmap.vietmapsdk.style.expressions.Expression.get("stroke-width"),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(255.0f, 255.0f, 255.0f, 1.0f),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.stop(1f, vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(0.0f, 0.0f, 0.0f, 1.0f)),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.stop(2f, vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(128.0f, 128.0f, 128.0f, 1.0f)),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.stop(3f, vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(0.0f, 255.0f, 0.0f, 1.0f))
                        )
                    )
                )
            )
        )
        Timber.i("Fill color: %s", layer.fillColor)
    }

    private fun addCompositeCategoricalFunction() {
        Timber.i("Add composite categorical function")
        val layer = mapboxMap!!.style!!.getLayerAs<vn.vietmap.vietmapsdk.style.layers.FillLayer>(AMSTERDAM_PARKS_LAYER)!!
        layer.setProperties(
            vn.vietmap.vietmapsdk.style.layers.PropertyFactory.fillColor(
                vn.vietmap.vietmapsdk.style.expressions.Expression.step(
                    vn.vietmap.vietmapsdk.style.expressions.Expression.zoom(),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(255.0f, 255.0f, 255.0f, 1.0f),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.stop(
                        7f,
                        vn.vietmap.vietmapsdk.style.expressions.Expression.match(
                            vn.vietmap.vietmapsdk.style.expressions.Expression.get("name"),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.literal("Westerpark"),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(255.0f, 0.0f, 0.0f, 1.0f),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(255.0f, 255.0f, 255.0f, 1.0f)
                        )
                    ),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.stop(
                        8f,
                        vn.vietmap.vietmapsdk.style.expressions.Expression.match(
                            vn.vietmap.vietmapsdk.style.expressions.Expression.get("name"),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.literal("Westerpark"),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(0.0f, 0.0f, 255.0f, 1.0f),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(255.0f, 255.0f, 255.0f, 1.0f)
                        )
                    ),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.stop(
                        9f,
                        vn.vietmap.vietmapsdk.style.expressions.Expression.match(
                            vn.vietmap.vietmapsdk.style.expressions.Expression.get("name"),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.literal("Westerpark"),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(255.0f, 0.0f, 0.0f, 1.0f),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(255.0f, 255.0f, 255.0f, 1.0f)
                        )
                    ),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.stop(
                        10f,
                        vn.vietmap.vietmapsdk.style.expressions.Expression.match(
                            vn.vietmap.vietmapsdk.style.expressions.Expression.get("name"),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.literal("Westerpark"),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(0.0f, 0.0f, 255.0f, 1.0f),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(255.0f, 255.0f, 255.0f, 1.0f)
                        )
                    ),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.stop(
                        11f,
                        vn.vietmap.vietmapsdk.style.expressions.Expression.match(
                            vn.vietmap.vietmapsdk.style.expressions.Expression.get("name"),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.literal("Westerpark"),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(255.0f, 0.0f, 0.0f, 1.0f),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(255.0f, 255.0f, 255.0f, 1.0f)
                        )
                    ),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.stop(
                        12f,
                        vn.vietmap.vietmapsdk.style.expressions.Expression.match(
                            vn.vietmap.vietmapsdk.style.expressions.Expression.get("name"),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.literal("Westerpark"),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(0.0f, 0.0f, 255.0f, 1.0f),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(255.0f, 255.0f, 255.0f, 1.0f)
                        )
                    ),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.stop(
                        13f,
                        vn.vietmap.vietmapsdk.style.expressions.Expression.match(
                            vn.vietmap.vietmapsdk.style.expressions.Expression.get("name"),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.literal("Westerpark"),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(255.0f, 0.0f, 0.0f, 1.0f),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(255.0f, 255.0f, 255.0f, 1.0f)
                        )
                    ),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.stop(
                        14f,
                        vn.vietmap.vietmapsdk.style.expressions.Expression.match(
                            vn.vietmap.vietmapsdk.style.expressions.Expression.get("name"),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.literal("Westerpark"),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(0.0f, 0.0f, 255.0f, 1.0f),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.literal("Jordaan"),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(0.0f, 255.0f, 0.0f, 1.0f),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.literal("PrinsenEiland"),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(0.0f, 0.0f, 0.0f, 1.0f),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(255.0f, 255.0f, 255.0f, 1.0f)
                        )
                    ),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.stop(
                        15f,
                        vn.vietmap.vietmapsdk.style.expressions.Expression.match(
                            vn.vietmap.vietmapsdk.style.expressions.Expression.get("name"),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.literal("Westerpark"),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(255.0f, 0.0f, 0.0f, 1.0f),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(255.0f, 255.0f, 255.0f, 1.0f)
                        )
                    ),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.stop(
                        16f,
                        vn.vietmap.vietmapsdk.style.expressions.Expression.match(
                            vn.vietmap.vietmapsdk.style.expressions.Expression.get("name"),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.literal("Westerpark"),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(0.0f, 0.0f, 255.0f, 1.0f),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(255.0f, 255.0f, 255.0f, 1.0f)
                        )
                    ),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.stop(
                        17f,
                        vn.vietmap.vietmapsdk.style.expressions.Expression.match(
                            vn.vietmap.vietmapsdk.style.expressions.Expression.get("name"),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.literal("Westerpark"),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(255.0f, 0.0f, 0.0f, 1.0f),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(255.0f, 255.0f, 255.0f, 1.0f)
                        )
                    ),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.stop(
                        18f,
                        vn.vietmap.vietmapsdk.style.expressions.Expression.match(
                            vn.vietmap.vietmapsdk.style.expressions.Expression.get("name"),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.literal("Westerpark"),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(0.0f, 0.0f, 255.0f, 1.0f),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.literal("Jordaan"),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(0.0f, 255.0f, 255.0f, 1.0f),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(255.0f, 255.0f, 255.0f, 1.0f)
                        )
                    ),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.stop(
                        19f,
                        vn.vietmap.vietmapsdk.style.expressions.Expression.match(
                            vn.vietmap.vietmapsdk.style.expressions.Expression.get("name"),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.literal("Westerpark"),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(255.0f, 0.0f, 0.0f, 1.0f),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(255.0f, 255.0f, 255.0f, 1.0f)
                        )
                    ),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.stop(
                        20f,
                        vn.vietmap.vietmapsdk.style.expressions.Expression.match(
                            vn.vietmap.vietmapsdk.style.expressions.Expression.get("name"),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.literal("Westerpark"),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(0.0f, 0.0f, 255.0f, 1.0f),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(255.0f, 255.0f, 255.0f, 1.0f)
                        )
                    ),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.stop(
                        21f,
                        vn.vietmap.vietmapsdk.style.expressions.Expression.match(
                            vn.vietmap.vietmapsdk.style.expressions.Expression.get("name"),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.literal("Westerpark"),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(255.0f, 0.0f, 0.0f, 1.0f),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(255.0f, 255.0f, 255.0f, 1.0f)
                        )
                    ),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.stop(
                        22f,
                        vn.vietmap.vietmapsdk.style.expressions.Expression.match(
                            vn.vietmap.vietmapsdk.style.expressions.Expression.get("name"),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.literal("Westerpark"),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(0.0f, 0.0f, 255.0f, 1.0f),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(255.0f, 255.0f, 255.0f, 1.0f)
                        )
                    )
                )
            )
        )
        Timber.i("Fill color: %s", layer.fillColor)
    }

    private fun addParksLayer() {
        // Add a source
        val source: vn.vietmap.vietmapsdk.style.sources.Source
        try {
            source = vn.vietmap.vietmapsdk.style.sources.GeoJsonSource(
                "amsterdam-parks-source",
                ResourceUtils.readRawResource(this, R.raw.amsterdam)
            )
            mapboxMap!!.style!!.addSource(source)
        } catch (ioException: IOException) {
            Toast.makeText(
                this@DataDrivenStyleActivity,
                "Couldn't add source: " + ioException.message,
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        // Add a fill layer
        mapboxMap!!.style!!
            .addLayer(
                vn.vietmap.vietmapsdk.style.layers.FillLayer(
                    AMSTERDAM_PARKS_LAYER,
                    source.getId()
                )
                    .withProperties(
                        vn.vietmap.vietmapsdk.style.layers.PropertyFactory.fillColor(
                            vn.vietmap.vietmapsdk.style.expressions.Expression.color(Color.GREEN)),
                        vn.vietmap.vietmapsdk.style.layers.PropertyFactory.fillOutlineColor(
                            vn.vietmap.vietmapsdk.style.expressions.Expression.rgb(0, 0, 255)),
                        vn.vietmap.vietmapsdk.style.layers.PropertyFactory.fillAntialias(true)
                    )
            )
    }

    companion object {
        const val AMSTERDAM_PARKS_LAYER = "amsterdam-parks-layer"
    }
}
