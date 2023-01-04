package vn.vietmap.vietmapsdk.testapp.activity.annotation

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import vn.vietmap.vietmapsdk.annotations.Polygon
import vn.vietmap.vietmapsdk.annotations.PolygonOptions
import vn.vietmap.vietmapsdk.camera.CameraPosition
import vn.vietmap.vietmapsdk.geometry.LatLng
// // import com.mapbox.vietmapsdk.maps.* // // ktlint-disable no-wildcard-imports
import vn.vietmap.vietmapsdk.testapp.R
import java.util.ArrayList

/**
 * Test activity to showcase the Polygon annotation API & programmatically creating a MapView.
 *
 *
 * Shows how to change Polygon features as visibility, alpha, color and points.
 *
 */
class PolygonActivity : AppCompatActivity(),
    vn.vietmap.vietmapsdk.maps.OnMapReadyCallback {
    private var mapView: vn.vietmap.vietmapsdk.maps.MapView? = null
    private var mapboxMap: vn.vietmap.vietmapsdk.maps.VietmapMap? = null
    private var polygon: vn.vietmap.vietmapsdk.annotations.Polygon? = null
    private var fullAlpha = true
    private var polygonIsVisible = true
    private var color = true
    private var allPoints = true
    private var holes = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // configure inital map state
        val options = vn.vietmap.vietmapsdk.maps.VietMapOptions.createFromAttributes(this, null)
            .attributionTintColor(Config.RED_COLOR)
            .compassFadesWhenFacingNorth(false)
            .camera(
                vn.vietmap.vietmapsdk.camera.CameraPosition.Builder()
                    .target(
                        vn.vietmap.vietmapsdk.geometry.LatLng(
                            45.520486,
                            -122.673541
                        )
                    )
                    .zoom(12.0)
                    .tilt(40.0)
                    .build()
            )

        // create map
        mapView = vn.vietmap.vietmapsdk.maps.MapView(this, options)
        mapView!!.id = R.id.mapView
        mapView!!.onCreate(savedInstanceState)
        mapView!!.getMapAsync(this)
        setContentView(mapView)
    }

    override fun onMapReady(map: vn.vietmap.vietmapsdk.maps.VietmapMap) {
        mapboxMap = map
        map.setStyle(vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Streets"))
        map.setOnPolygonClickListener { polygon: vn.vietmap.vietmapsdk.annotations.Polygon ->
            Toast.makeText(
                this@PolygonActivity,
                "You clicked on polygon with id = " + polygon.id,
                Toast.LENGTH_SHORT
            ).show()
        }
        polygon = mapboxMap!!.addPolygon(
            vn.vietmap.vietmapsdk.annotations.PolygonOptions()
                .addAll(Config.STAR_SHAPE_POINTS)
                .fillColor(Config.BLUE_COLOR)
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
            R.id.action_id_alpha -> {
                fullAlpha = !fullAlpha
                polygon!!.alpha =
                    if (fullAlpha) Config.FULL_ALPHA else Config.PARTIAL_ALPHA
                true
            }
            R.id.action_id_visible -> {
                polygonIsVisible = !polygonIsVisible
                polygon!!.alpha =
                    if (polygonIsVisible) if (fullAlpha) Config.FULL_ALPHA else Config.PARTIAL_ALPHA else Config.NO_ALPHA
                true
            }
            R.id.action_id_points -> {
                allPoints = !allPoints
                polygon!!.points =
                    if (allPoints) Config.STAR_SHAPE_POINTS else Config.BROKEN_SHAPE_POINTS
                true
            }
            R.id.action_id_color -> {
                color = !color
                polygon!!.fillColor =
                    if (color) Config.BLUE_COLOR else Config.RED_COLOR
                true
            }
            R.id.action_id_holes -> {
                holes = !holes
                polygon!!.holes =
                    if (holes) Config.STAR_SHAPE_HOLES else emptyList()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_polygon, menu)
        return true
    }

    internal object Config {
        val BLUE_COLOR = Color.parseColor("#3bb2d0")
        val RED_COLOR = Color.parseColor("#AF0000")
        const val FULL_ALPHA = 1.0f
        const val PARTIAL_ALPHA = 0.5f
        const val NO_ALPHA = 0.0f
        val STAR_SHAPE_POINTS: ArrayList<vn.vietmap.vietmapsdk.geometry.LatLng?> = object : ArrayList<vn.vietmap.vietmapsdk.geometry.LatLng?>() {
            init {
                add(
                    vn.vietmap.vietmapsdk.geometry.LatLng(
                        45.522585,
                        -122.685699
                    )
                )
                add(
                    vn.vietmap.vietmapsdk.geometry.LatLng(
                        45.534611,
                        -122.708873
                    )
                )
                add(
                    vn.vietmap.vietmapsdk.geometry.LatLng(
                        45.530883,
                        -122.678833
                    )
                )
                add(
                    vn.vietmap.vietmapsdk.geometry.LatLng(
                        45.547115,
                        -122.667503
                    )
                )
                add(
                    vn.vietmap.vietmapsdk.geometry.LatLng(
                        45.530643,
                        -122.660121
                    )
                )
                add(
                    vn.vietmap.vietmapsdk.geometry.LatLng(
                        45.533529,
                        -122.636260
                    )
                )
                add(
                    vn.vietmap.vietmapsdk.geometry.LatLng(
                        45.521743,
                        -122.659091
                    )
                )
                add(
                    vn.vietmap.vietmapsdk.geometry.LatLng(
                        45.510677,
                        -122.648792
                    )
                )
                add(
                    vn.vietmap.vietmapsdk.geometry.LatLng(
                        45.515008,
                        -122.664070
                    )
                )
                add(
                    vn.vietmap.vietmapsdk.geometry.LatLng(
                        45.502496,
                        -122.669048
                    )
                )
                add(
                    vn.vietmap.vietmapsdk.geometry.LatLng(
                        45.515369,
                        -122.678489
                    )
                )
                add(
                    vn.vietmap.vietmapsdk.geometry.LatLng(
                        45.506346,
                        -122.702007
                    )
                )
                add(
                    vn.vietmap.vietmapsdk.geometry.LatLng(
                        45.522585,
                        -122.685699
                    )
                )
            }
        }
        val BROKEN_SHAPE_POINTS = STAR_SHAPE_POINTS.subList(0, STAR_SHAPE_POINTS.size - 3)
        val STAR_SHAPE_HOLES: ArrayList<List<vn.vietmap.vietmapsdk.geometry.LatLng?>?> = object : ArrayList<List<vn.vietmap.vietmapsdk.geometry.LatLng?>?>() {
            init {
                add(
                    ArrayList<vn.vietmap.vietmapsdk.geometry.LatLng>(object : ArrayList<vn.vietmap.vietmapsdk.geometry.LatLng?>() {
                        init {
                            add(
                                vn.vietmap.vietmapsdk.geometry.LatLng(
                                    45.521743,
                                    -122.669091
                                )
                            )
                            add(
                                vn.vietmap.vietmapsdk.geometry.LatLng(
                                    45.530483,
                                    -122.676833
                                )
                            )
                            add(
                                vn.vietmap.vietmapsdk.geometry.LatLng(
                                    45.520483,
                                    -122.676833
                                )
                            )
                            add(
                                vn.vietmap.vietmapsdk.geometry.LatLng(
                                    45.521743,
                                    -122.669091
                                )
                            )
                        }
                    })
                )
                add(
                    ArrayList<vn.vietmap.vietmapsdk.geometry.LatLng>(object : ArrayList<vn.vietmap.vietmapsdk.geometry.LatLng?>() {
                        init {
                            add(
                                vn.vietmap.vietmapsdk.geometry.LatLng(
                                    45.529743,
                                    -122.662791
                                )
                            )
                            add(
                                vn.vietmap.vietmapsdk.geometry.LatLng(
                                    45.525543,
                                    -122.662791
                                )
                            )
                            add(
                                vn.vietmap.vietmapsdk.geometry.LatLng(
                                    45.525543,
                                    -122.660
                                )
                            )
                            add(
                                vn.vietmap.vietmapsdk.geometry.LatLng(
                                    45.527743,
                                    -122.660
                                )
                            )
                            add(
                                vn.vietmap.vietmapsdk.geometry.LatLng(
                                    45.529743,
                                    -122.662791
                                )
                            )
                        }
                    })
                )
            }
        }
    }
}
