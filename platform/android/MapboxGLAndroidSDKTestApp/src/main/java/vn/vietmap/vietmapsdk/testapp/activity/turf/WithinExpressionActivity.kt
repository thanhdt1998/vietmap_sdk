package vn.vietmap.vietmapsdk.testapp.activity.turf

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.*
import vn.vietmap.vietmapsdk.camera.CameraPosition
import vn.vietmap.vietmapsdk.camera.CameraUpdateFactory
import vn.vietmap.vietmapsdk.geometry.LatLng
import vn.vietmap.vietmapsdk.maps.MapView
import vn.vietmap.vietmapsdk.maps.Style
import vn.vietmap.vietmapsdk.style.expressions.Expression.within
import vn.vietmap.vietmapsdk.style.layers.CircleLayer
import vn.vietmap.vietmapsdk.style.layers.FillLayer
import vn.vietmap.vietmapsdk.style.layers.LineLayer
import vn.vietmap.vietmapsdk.style.layers.Property.NONE
import vn.vietmap.vietmapsdk.style.layers.PropertyFactory.*
import vn.vietmap.vietmapsdk.style.layers.SymbolLayer
import vn.vietmap.vietmapsdk.style.sources.GeoJsonOptions
import vn.vietmap.vietmapsdk.style.sources.GeoJsonSource
import vn.vietmap.vietmapsdk.testapp.R
import kotlinx.android.synthetic.main.activity_physical_circle.*

/**
 * An Activity that showcases the within expression to filter features outside a geometry
 */
class WithinExpressionActivity : AppCompatActivity() {

    private lateinit var VietmapMap: vn.vietmap.vietmapsdk.maps.VietmapMap
    private val handler: Handler = Handler()
    private val runnable: Runnable = Runnable {
        optimizeStyle()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_within_expression)

        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync { map ->
            VietmapMap = map

            // Setup camera position above Georgetown
            VietmapMap.cameraPosition = vn.vietmap.vietmapsdk.camera.CameraPosition.Builder()
                .target(
                    vn.vietmap.vietmapsdk.geometry.LatLng(
                        38.90628988399711,
                        -77.06574689337494
                    )
                )
                .zoom(15.5)
                .build()

            // Wait for the map to become idle before manipulating the style and camera of the map
            mapView.addOnDidBecomeIdleListener(object : vn.vietmap.vietmapsdk.maps.MapView.OnDidBecomeIdleListener {
                override fun onDidBecomeIdle() {
                    handler.postDelayed(runnable, 2500)
                    mapView.removeOnDidBecomeIdleListener(this)
                }
            })

            // Load mapbox streets and add lines and circles
            setupStyle()
        }
    }

    private fun setupStyle() {
        // Assume the route is represented by an array of coordinates.
        val coordinates = listOf<Point>(
            Point.fromLngLat(
                -77.06866264343262,
                38.90506061276737
            ),
            Point.fromLngLat(
                -77.06283688545227,
                38.905194197410545
            ),
            Point.fromLngLat(
                -77.06285834312439,
                38.906429843444094
            ),
            Point.fromLngLat(
                -77.0630407333374,
                38.90680554236621
            )
        )

        // Convert to a LineString
        val routeLineString = LineString.fromLngLats(coordinates)

        // Create buffer around linestring
        val bufferedRouteGeometry = bufferLineStringGeometry(routeLineString)

        // Setup style with additional layers,
        // using streets as a base style
        VietmapMap.setStyle(
            vn.vietmap.vietmapsdk.maps.Style.Builder()
                .fromUri(vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Streets"))
                .withSources(
                    vn.vietmap.vietmapsdk.style.sources.GeoJsonSource(
                        POINT_ID,
                        LineString.fromLngLats(coordinates)
                    ),
                    vn.vietmap.vietmapsdk.style.sources.GeoJsonSource(
                        FILL_ID,
                        FeatureCollection.fromFeature(Feature.fromGeometry(bufferedRouteGeometry)),
                        vn.vietmap.vietmapsdk.style.sources.GeoJsonOptions()
                            .withBuffer(0).withTolerance(0.0f)
                    )
                )
                .withLayerBelow(
                    vn.vietmap.vietmapsdk.style.layers.LineLayer(
                        LINE_ID,
                        POINT_ID
                    )
                        .withProperties(lineWidth(7.5f), lineColor(Color.LTGRAY)),
                    "poi-label"
                )
                .withLayerBelow(
                    vn.vietmap.vietmapsdk.style.layers.CircleLayer(
                        POINT_ID,
                        POINT_ID
                    )
                        .withProperties(
                            circleRadius(7.5f),
                            circleColor(Color.DKGRAY),
                            circleOpacity(0.75f)
                        ),
                    "poi-label"
                )
        )
    }

    private fun optimizeStyle() {
        val style = VietmapMap.style!!

        // Add fill layer to represent buffered LineString
        VietmapMap.style!!.addLayerBelow(
            vn.vietmap.vietmapsdk.style.layers.FillLayer(FILL_ID, FILL_ID)
                .withProperties(
                    fillOpacity(0.12f),
                    fillColor(Color.YELLOW)
                ),
            LINE_ID
        )

        // Move to a new camera position
        VietmapMap.easeCamera(
            vn.vietmap.vietmapsdk.camera.CameraUpdateFactory.newCameraPosition(
                vn.vietmap.vietmapsdk.camera.CameraPosition.Builder()
                    .zoom(16.0)
                    .target(
                        vn.vietmap.vietmapsdk.geometry.LatLng(
                            38.905156245642814,
                            -77.06535338052844
                        )
                    )
                    .bearing(80.68015859462369)
                    .tilt(55.0)
                    .build()
            ),
            1750
        )

        // Show only POI labels inside geometry using within expression
        val symbolLayer = style.getLayer("poi_z16") as vn.vietmap.vietmapsdk.style.layers.SymbolLayer
        symbolLayer.setFilter(
            within(
                bufferLineStringGeometry()
            )
        )

        // Hide other types of labels to highlight POI labels
        (style.getLayer("road_label") as vn.vietmap.vietmapsdk.style.layers.SymbolLayer).setProperties(visibility(NONE))
        (style.getLayer("airport-label-major") as vn.vietmap.vietmapsdk.style.layers.SymbolLayer).setProperties(visibility(NONE))
        (style.getLayer("poi_transit") as vn.vietmap.vietmapsdk.style.layers.SymbolLayer).setProperties(visibility(NONE))
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
        handler.removeCallbacks(runnable)
        mapView.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState?.let {
            mapView.onSaveInstanceState(it)
        }
    }

    private fun bufferLineStringGeometry(lineString: LineString? = null): Polygon {
        // TODO replace static data by Turf#Buffer: mapbox-java/issues/987
        return FeatureCollection.fromJson(
            """
            {
              "type": "FeatureCollection",
              "features": [
                {
                  "type": "Feature",
                  "properties": {},
                  "geometry": {
                    "type": "Polygon",
                    "coordinates": [
                      [
                        [
                          -77.06867337226866,
                          38.90467655551809
                        ],
                        [
                          -77.06233263015747,
                          38.90479344272695
                        ],
                        [
                          -77.06234335899353,
                          38.906463238984344
                        ],
                        [
                          -77.06290125846863,
                          38.907206285691615
                        ],
                        [
                          -77.06364154815674,
                          38.90684728656818
                        ],
                        [
                          -77.06326603889465,
                          38.90637140121084
                        ],
                        [
                          -77.06321239471436,
                          38.905561553883246
                        ],
                        [
                          -77.0691454410553,
                          38.905436318935635
                        ],
                        [
                          -77.06912398338318,
                          38.90466820642439
                        ],
                        [
                          -77.06867337226866,
                          38.90467655551809
                        ]
                      ]
                    ]
                  }
                }
              ]
            }
            """.trimIndent()
        ).features()!![0].geometry() as Polygon
    }

    companion object {
        const val POINT_ID = "point"
        const val FILL_ID = "fill"
        const val LINE_ID = "line"
    }
}
