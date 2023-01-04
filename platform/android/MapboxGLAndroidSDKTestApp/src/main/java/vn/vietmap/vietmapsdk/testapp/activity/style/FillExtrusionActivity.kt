package vn.vietmap.vietmapsdk.testapp.activity.style

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Point
import com.mapbox.geojson.Polygon
import vn.vietmap.vietmapsdk.camera.CameraPosition
import vn.vietmap.vietmapsdk.camera.CameraUpdateFactory
import vn.vietmap.vietmapsdk.geometry.LatLng
import vn.vietmap.vietmapsdk.maps.MapView
import vn.vietmap.vietmapsdk.maps.VietmapMap
import vn.vietmap.vietmapsdk.maps.OnMapReadyCallback
import vn.vietmap.vietmapsdk.maps.Style
import vn.vietmap.vietmapsdk.style.sources.GeoJsonSource
import vn.vietmap.vietmapsdk.testapp.R
import vn.vietmap.vietmapsdk.style.layers.PropertyFactory
import java.util.*

/**
 * Test activity showcasing fill extrusions
 */
class FillExtrusionActivity : AppCompatActivity() {
    private lateinit var mapView: vn.vietmap.vietmapsdk.maps.MapView
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_extrusion_layer)
        mapView = findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(
            vn.vietmap.vietmapsdk.maps.OnMapReadyCallback { mapboxMap: vn.vietmap.vietmapsdk.maps.VietmapMap ->
                mapboxMap.setStyle(
                    vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle(
                        "Streets"
                    )
                ) { style: vn.vietmap.vietmapsdk.maps.Style ->
                    val lngLats = listOf(
                        Arrays.asList(
                            Point.fromLngLat(5.12112557888031, 52.09071040847704),
                            Point.fromLngLat(5.121227502822875, 52.09053901776669),
                            Point.fromLngLat(5.121484994888306, 52.090601641371805),
                            Point.fromLngLat(5.1213884353637695, 52.090766439912635),
                            Point.fromLngLat(5.12112557888031, 52.09071040847704)
                        )
                    )
                    val domTower = Polygon.fromLngLats(lngLats)
                    val source =
                        vn.vietmap.vietmapsdk.style.sources.GeoJsonSource(
                            "extrusion-source",
                            domTower
                        )
                    style.addSource(source)
                    style.addLayer(
                        vn.vietmap.vietmapsdk.style.layers.FillExtrusionLayer(
                            "extrusion-layer",
                            source.id
                        )
                            .withProperties(
                                PropertyFactory.fillExtrusionHeight(40f),
                                vn.vietmap.vietmapsdk.style.layers.PropertyFactory.fillExtrusionOpacity(
                                    0.5f
                                ),
                                vn.vietmap.vietmapsdk.style.layers.PropertyFactory.fillExtrusionColor(
                                    Color.RED
                                )
                            )
                    )
                    mapboxMap.animateCamera(
                        vn.vietmap.vietmapsdk.camera.CameraUpdateFactory.newCameraPosition(
                            vn.vietmap.vietmapsdk.camera.CameraPosition.Builder()
                                .target(
                                    vn.vietmap.vietmapsdk.geometry.LatLng(
                                        52.09071040847704,
                                        5.12112557888031
                                    )
                                )
                                .tilt(45.0)
                                .zoom(18.0)
                                .build()
                        ),
                        10000
                    )
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
}
