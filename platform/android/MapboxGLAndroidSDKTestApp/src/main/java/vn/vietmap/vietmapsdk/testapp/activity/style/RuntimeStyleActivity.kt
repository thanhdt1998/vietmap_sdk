package vn.vietmap.vietmapsdk.testapp.activity.style

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import com.mapbox.geojson.Polygon
import vn.vietmap.vietmapsdk.camera.CameraUpdateFactory
import vn.vietmap.vietmapsdk.geometry.LatLng
// import com.mapbox.vietmapsdk.maps.* //
import vn.vietmap.vietmapsdk.maps.VietmapMap.CancelableCallback
import vn.vietmap.vietmapsdk.style.expressions.Expression

import vn.vietmap.vietmapsdk.testapp.R
import vn.vietmap.vietmapsdk.testapp.utils.ResourceUtils
import timber.log.Timber
import vn.vietmap.vietmapsdk.maps.Style
import vn.vietmap.vietmapsdk.style.layers.PropertyFactory
import vn.vietmap.vietmapsdk.style.layers.TransitionOptions
import java.io.IOException
import java.lang.StringBuilder
import java.util.*

/**
 * Test activity showcasing the runtime style API.
 */
class RuntimeStyleActivity : AppCompatActivity() {
    private lateinit var mapView: vn.vietmap.vietmapsdk.maps.MapView
    private var mapboxMap: vn.vietmap.vietmapsdk.maps.VietmapMap? = null
    private var styleLoaded = false
    var lngLats = listOf(
        Arrays.asList(
            Point.fromLngLat(
                -15.468749999999998,
                41.77131167976407
            ),
            Point.fromLngLat(
                15.468749999999998,
                41.77131167976407
            ),
            Point.fromLngLat(
                15.468749999999998,
                58.26328705248601
            ),
            Point.fromLngLat(
                -15.468749999999998,
                58.26328705248601
            ),
            Point.fromLngLat(
                -15.468749999999998,
                41.77131167976407
            )
        )
    )
    var polygon = Polygon.fromLngLats(lngLats)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_runtime_style)

        // Initialize map as normal
        mapView = findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(
            vn.vietmap.vietmapsdk.maps.OnMapReadyCallback { map: vn.vietmap.vietmapsdk.maps.VietmapMap? ->
                // Store for later
                mapboxMap = map

                // Center and Zoom (Amsterdam, zoomed to streets)
                mapboxMap!!.animateCamera(
                    vn.vietmap.vietmapsdk.camera.CameraUpdateFactory.newLatLngZoom(
                        vn.vietmap.vietmapsdk.geometry.LatLng(
                            52.379189,
                            4.899431
                        ),
                        1.0
                    )
                )
                mapboxMap!!.setStyle(
                    Style.Builder()
                        .fromUri(Style.getPredefinedStyle("Streets")) // set custom transition
                        .withTransition(TransitionOptions(250, 50))
                ) { style: vn.vietmap.vietmapsdk.maps.Style ->
                    styleLoaded = true
                    val laber =
                        style.getLayer("country_1") as vn.vietmap.vietmapsdk.style.layers.SymbolLayer?
                    laber!!.setProperties(
                        PropertyFactory.textOpacity(
                            vn.vietmap.vietmapsdk.style.expressions.Expression.switchCase(
                                vn.vietmap.vietmapsdk.style.expressions.Expression.within(
                                    polygon
                                ),
                                vn.vietmap.vietmapsdk.style.expressions.Expression.literal(
                                    1.0f
                                ),
                                vn.vietmap.vietmapsdk.style.expressions.Expression.literal(
                                    0.5f
                                )
                            )
                        )
                    )
                }
            }
        )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_runtime_style, menu)
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
        mapView!!.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView!!.onLowMemory()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (!styleLoaded) {
            false
        } else when (item.itemId) {
            R.id.action_list_layers -> {
                listLayers()
                true
            }
            R.id.action_list_sources -> {
                listSources()
                true
            }
            R.id.action_water_color -> {
                setWaterColor()
                true
            }
            R.id.action_background_opacity -> {
                setBackgroundOpacity()
                true
            }
            R.id.action_road_avoid_edges -> {
                setRoadSymbolPlacement()
                true
            }
            R.id.action_layer_visibility -> {
                setLayerInvisible()
                true
            }
            R.id.action_remove_layer -> {
                removeBuildings()
                true
            }
            R.id.action_add_parks_layer -> {
                addParksLayer()
                true
            }
            R.id.action_add_dynamic_parks_layer -> {
                addDynamicParksLayer()
                true
            }
            R.id.action_add_terrain_layer -> {
                addTerrainLayer()
                true
            }
            R.id.action_add_satellite_layer -> {
                addSatelliteLayer()
                true
            }
            R.id.action_update_water_color_on_zoom -> {
                updateWaterColorOnZoom()
                true
            }
            R.id.action_add_custom_tiles -> {
                addCustomTileSource()
                true
            }
            R.id.action_fill_filter -> {
                styleFillFilterLayer()
                true
            }
            R.id.action_textsize_filter -> {
                styleTextSizeFilterLayer()
                true
            }
            R.id.action_line_filter -> {
                styleLineFilterLayer()
                true
            }
            R.id.action_numeric_filter -> {
                styleNumericFillLayer()
                true
            }
            R.id.action_bring_water_to_front -> {
                bringWaterToFront()
                true
            }
            R.id.action_fill_filter_color -> {
                styleFillColorLayer()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun listLayers() {
        val layers = mapboxMap!!.style!!
            .layers
        val builder = StringBuilder("Layers:")
        for (layer in layers) {
            builder.append("\n")
            builder.append(layer.id)
        }
        Toast.makeText(this, builder.toString(), Toast.LENGTH_LONG).show()
    }

    private fun listSources() {
        val sources = mapboxMap!!.style!!
            .sources
        val builder = StringBuilder("Sources:")
        for (source in sources) {
            builder.append("\n")
            builder.append(source.id)
        }
        Toast.makeText(this, builder.toString(), Toast.LENGTH_LONG).show()
    }

    private fun setLayerInvisible() {
        val roadLayers = arrayOf("water")
        for (roadLayer in roadLayers) {
            val layer = mapboxMap!!.style!!.getLayer(roadLayer)
            layer?.setProperties(
                vn.vietmap.vietmapsdk.style.layers.PropertyFactory.visibility(
                    vn.vietmap.vietmapsdk.style.layers.Property.NONE))
        }
    }

    private fun setRoadSymbolPlacement() {
        // Zoom so that the labels are visible first
        mapboxMap!!.animateCamera(
            vn.vietmap.vietmapsdk.camera.CameraUpdateFactory.zoomTo(14.0),
            object : DefaultCallback() {
                override fun onFinish() {
                    val roadLayers =
                        arrayOf("road-label-small", "road-label-medium", "road-label-large")
                    for (roadLayer in roadLayers) {
                        val layer = mapboxMap!!.style!!
                            .getLayer(roadLayer)
                        layer?.setProperties(
                            vn.vietmap.vietmapsdk.style.layers.PropertyFactory.symbolPlacement(
                                vn.vietmap.vietmapsdk.style.layers.Property.SYMBOL_PLACEMENT_POINT))
                    }
                }
            }
        )
    }

    private fun setBackgroundOpacity() {
        val background = mapboxMap!!.style!!.getLayer("background")
        background?.setProperties(vn.vietmap.vietmapsdk.style.layers.PropertyFactory.backgroundOpacity(0.2f))
    }

    private fun setWaterColor() {
        val water = mapboxMap!!.style!!.getLayerAs<vn.vietmap.vietmapsdk.style.layers.FillLayer>("water")
        if (water != null) {
            water.fillColorTransition =
                vn.vietmap.vietmapsdk.style.layers.TransitionOptions(7500, 1000)
            water.setProperties(
                vn.vietmap.vietmapsdk.style.layers.PropertyFactory.visibility(
                    vn.vietmap.vietmapsdk.style.layers.Property.VISIBLE),
                vn.vietmap.vietmapsdk.style.layers.PropertyFactory.fillColor(Color.RED)
            )
        } else {
            Toast.makeText(
                this@RuntimeStyleActivity,
                "No water layer in this style",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun removeBuildings() {
        // Zoom to see buildings first
        mapboxMap!!.style!!.removeLayer("building")
    }

    private fun addParksLayer() {
        // Add a source
        val source: vn.vietmap.vietmapsdk.style.sources.Source
        source = try {
            vn.vietmap.vietmapsdk.style.sources.GeoJsonSource(
                "amsterdam-spots",
                ResourceUtils.readRawResource(this, R.raw.amsterdam)
            )
        } catch (ioException: IOException) {
            Toast.makeText(
                this@RuntimeStyleActivity,
                "Couldn't add source: " + ioException.message,
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        mapboxMap!!.style!!.addSource(source)
        var layer: vn.vietmap.vietmapsdk.style.layers.FillLayer? =
            vn.vietmap.vietmapsdk.style.layers.FillLayer(
                "parksLayer",
                "amsterdam-spots"
            )
        layer!!.setProperties(
            vn.vietmap.vietmapsdk.style.layers.PropertyFactory.fillColor(Color.RED),
            vn.vietmap.vietmapsdk.style.layers.PropertyFactory.fillOutlineColor(Color.BLUE),
            vn.vietmap.vietmapsdk.style.layers.PropertyFactory.fillOpacity(0.3f),
            vn.vietmap.vietmapsdk.style.layers.PropertyFactory.fillAntialias(true)
        )

        // Only show me parks (except westerpark with stroke-width == 3)
        layer.setFilter(
            vn.vietmap.vietmapsdk.style.expressions.Expression.all(
                vn.vietmap.vietmapsdk.style.expressions.Expression.eq(
                    vn.vietmap.vietmapsdk.style.expressions.Expression.get("type"), vn.vietmap.vietmapsdk.style.expressions.Expression.literal("park")),
                vn.vietmap.vietmapsdk.style.expressions.Expression.eq(
                    vn.vietmap.vietmapsdk.style.expressions.Expression.get("stroke-width"),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.literal(3)
                )
            )
        )
        mapboxMap!!.style!!.addLayerBelow(layer, "building")
        // layer.setPaintProperty(fillColor(Color.RED)); // XXX But not after the object is attached

        // Or get the object later and set it. It's all good.
        mapboxMap!!.style!!.getLayer("parksLayer")!!
            .setProperties(vn.vietmap.vietmapsdk.style.layers.PropertyFactory.fillColor(Color.RED))

        // You can get a typed layer, if you're sure it's of that type. Use with care
        layer = mapboxMap!!.style!!.getLayerAs("parksLayer")
        // And get some properties
        val fillAntialias = layer!!.fillAntialias
        Timber.d("Fill anti alias: %s", fillAntialias.getValue())
        layer.setProperties(
            vn.vietmap.vietmapsdk.style.layers.PropertyFactory.fillTranslateAnchor(
                vn.vietmap.vietmapsdk.style.layers.Property.FILL_TRANSLATE_ANCHOR_MAP))
        val fillTranslateAnchor = layer.fillTranslateAnchor
        Timber.d("Fill translate anchor: %s", fillTranslateAnchor.getValue())
        val visibility = layer.visibility
        Timber.d("Visibility: %s", visibility.getValue())

        // Get a good look at it all
        mapboxMap!!.animateCamera(vn.vietmap.vietmapsdk.camera.CameraUpdateFactory.zoomTo(12.0))
    }

    private fun addDynamicParksLayer() {
        // Load some data
        val parks: FeatureCollection
        parks = try {
            val json = ResourceUtils.readRawResource(this, R.raw.amsterdam)
            FeatureCollection.fromJson(json)
        } catch (ioException: IOException) {
            Toast.makeText(
                this@RuntimeStyleActivity,
                "Couldn't add source: " + ioException.message,
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        // Add an empty source
        mapboxMap!!.style!!.addSource(
            vn.vietmap.vietmapsdk.style.sources.GeoJsonSource(
                "dynamic-park-source"
            )
        )
        val layer = vn.vietmap.vietmapsdk.style.layers.FillLayer(
            "dynamic-parks-layer",
            "dynamic-park-source"
        )
        layer.setProperties(
            vn.vietmap.vietmapsdk.style.layers.PropertyFactory.fillColor(Color.GREEN),
            vn.vietmap.vietmapsdk.style.layers.PropertyFactory.fillOutlineColor(Color.GREEN),
            vn.vietmap.vietmapsdk.style.layers.PropertyFactory.fillOpacity(0.8f),
            vn.vietmap.vietmapsdk.style.layers.PropertyFactory.fillAntialias(true)
        )

        // Only show me parks
        layer.setFilter(
            vn.vietmap.vietmapsdk.style.expressions.Expression.all(
                vn.vietmap.vietmapsdk.style.expressions.Expression.eq(
                    vn.vietmap.vietmapsdk.style.expressions.Expression.get("type"),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.literal("park")
                )
            )
        )
        mapboxMap!!.style!!.addLayer(layer)

        // Get a good look at it all
        mapboxMap!!.animateCamera(vn.vietmap.vietmapsdk.camera.CameraUpdateFactory.zoomTo(12.0))

        // Animate the parks source
        animateParksSource(parks, 0)
    }

    private fun animateParksSource(parks: FeatureCollection, counter: Int) {
        val handler = Handler(mainLooper)
        handler.postDelayed(
            {
                if (mapboxMap == null) {
                    return@postDelayed
                }
                Timber.d("Updating parks source")
                // change the source
                val park = if (counter < parks.features()!!.size - 1) counter else 0
                val source = mapboxMap!!.style!!.getSourceAs<vn.vietmap.vietmapsdk.style.sources.GeoJsonSource>("dynamic-park-source")
                if (source == null) {
                    Timber.e("Source not found")
                    Toast.makeText(this@RuntimeStyleActivity, "Source not found", Toast.LENGTH_SHORT)
                        .show()
                    return@postDelayed
                }
                val features: MutableList<Feature> = ArrayList()
                features.add(parks.features()!![park])
                source.setGeoJson(FeatureCollection.fromFeatures(features))

                // Re-post
                animateParksSource(parks, park + 1)
            },
            if (counter == 0) 100 else 1000.toLong()
        )
    }

    private fun addTerrainLayer() {
        // Add a source
        val source: vn.vietmap.vietmapsdk.style.sources.Source =
            vn.vietmap.vietmapsdk.style.sources.VectorSource(
                "my-terrain-source",
                "maptiler://sources/hillshades"
            )
        mapboxMap!!.style!!.addSource(source)
        var layer: vn.vietmap.vietmapsdk.style.layers.LineLayer? =
            vn.vietmap.vietmapsdk.style.layers.LineLayer(
                "terrainLayer",
                "my-terrain-source"
            )
        layer!!.sourceLayer = "contour"
        layer.setProperties(
            vn.vietmap.vietmapsdk.style.layers.PropertyFactory.lineJoin(
                vn.vietmap.vietmapsdk.style.layers.Property.LINE_JOIN_ROUND),
            vn.vietmap.vietmapsdk.style.layers.PropertyFactory.lineCap(
                vn.vietmap.vietmapsdk.style.layers.Property.LINE_CAP_ROUND),
            vn.vietmap.vietmapsdk.style.layers.PropertyFactory.lineColor(Color.RED),
            vn.vietmap.vietmapsdk.style.layers.PropertyFactory.lineWidth(20f)
        )

        // adding layers below "road" layers
        val layers = mapboxMap!!.style!!
            .layers
        var latestLayer: vn.vietmap.vietmapsdk.style.layers.Layer? = null
        Collections.reverse(layers)
        for (currentLayer in layers) {
            if (currentLayer is vn.vietmap.vietmapsdk.style.layers.FillLayer && currentLayer.sourceLayer == "road") {
                latestLayer = currentLayer
            } else if (currentLayer is vn.vietmap.vietmapsdk.style.layers.CircleLayer && currentLayer.sourceLayer == "road") {
                latestLayer = currentLayer
            } else if (currentLayer is vn.vietmap.vietmapsdk.style.layers.SymbolLayer && currentLayer.sourceLayer == "road") {
                latestLayer = currentLayer
            } else if (currentLayer is vn.vietmap.vietmapsdk.style.layers.LineLayer && currentLayer.sourceLayer == "road") {
                latestLayer = currentLayer
            }
        }
        if (latestLayer != null) {
            mapboxMap!!.style!!.addLayerBelow(layer, latestLayer.id)
        }

        // Need to get a fresh handle
        layer = mapboxMap!!.style!!.getLayerAs("terrainLayer")

        // Make sure it's also applied after the fact
        layer!!.minZoom = 10f
        layer.maxZoom = 15f
        layer = mapboxMap!!.style!!.getLayer("terrainLayer") as vn.vietmap.vietmapsdk.style.layers.LineLayer?
        Toast.makeText(
            this,
            String.format(
                "Set min/max zoom to %s - %s",
                layer!!.minZoom,
                layer.maxZoom
            ),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun addSatelliteLayer() {
        // Add a source
        val source: vn.vietmap.vietmapsdk.style.sources.Source =
            vn.vietmap.vietmapsdk.style.sources.RasterSource(
                "my-raster-source",
                "maptiler://sources/satellite",
                512
            )
        mapboxMap!!.style!!.addSource(source)

        // Add a layer
        mapboxMap!!.style!!.addLayer(
            vn.vietmap.vietmapsdk.style.layers.RasterLayer(
                "satellite-layer",
                "my-raster-source"
            )
        )
    }

    private fun updateWaterColorOnZoom() {
        val layer = mapboxMap!!.style!!.getLayerAs<vn.vietmap.vietmapsdk.style.layers.FillLayer>("water") ?: return

        // Set a zoom function to update the color of the water
        layer.setProperties(
            vn.vietmap.vietmapsdk.style.layers.PropertyFactory.fillColor(
                vn.vietmap.vietmapsdk.style.expressions.Expression.interpolate(
                    vn.vietmap.vietmapsdk.style.expressions.Expression.exponential(0.8f),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.zoom(),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.stop(1, vn.vietmap.vietmapsdk.style.expressions.Expression.color(Color.GREEN)),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.stop(4, vn.vietmap.vietmapsdk.style.expressions.Expression.color(Color.BLUE)),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.stop(12, vn.vietmap.vietmapsdk.style.expressions.Expression.color(Color.RED)),
                    vn.vietmap.vietmapsdk.style.expressions.Expression.stop(20, vn.vietmap.vietmapsdk.style.expressions.Expression.color(Color.BLACK))
                )
            )
        )

        // do some animations to show it off properly
        mapboxMap!!.animateCamera(vn.vietmap.vietmapsdk.camera.CameraUpdateFactory.zoomTo(1.0), 1500)
    }

    private fun addCustomTileSource() {
        // TODO: migrate
        // Add a source
        val tileSet = vn.vietmap.vietmapsdk.style.sources.TileSet(
            "2.1.0",
            "https://d25uarhxywzl1j.cloudfront.net/v0.1/{z}/{x}/{y}.mvt"
        )
        tileSet.minZoom = 0f
        tileSet.maxZoom = 14f
        val source: vn.vietmap.vietmapsdk.style.sources.Source =
            vn.vietmap.vietmapsdk.style.sources.VectorSource(
                "custom-tile-source",
                tileSet
            )
        mapboxMap!!.style!!.addSource(source)

        // Add a layer
        val lineLayer = vn.vietmap.vietmapsdk.style.layers.LineLayer(
            "custom-tile-layers",
            "custom-tile-source"
        )
        lineLayer.sourceLayer = "mapillary-sequences"
        lineLayer.setProperties(
            vn.vietmap.vietmapsdk.style.layers.PropertyFactory.lineCap(
                vn.vietmap.vietmapsdk.style.layers.Property.LINE_CAP_ROUND),
            vn.vietmap.vietmapsdk.style.layers.PropertyFactory.lineJoin(
                vn.vietmap.vietmapsdk.style.layers.Property.LINE_JOIN_ROUND),
            vn.vietmap.vietmapsdk.style.layers.PropertyFactory.lineOpacity(0.6f),
            vn.vietmap.vietmapsdk.style.layers.PropertyFactory.lineWidth(2.0f),
            vn.vietmap.vietmapsdk.style.layers.PropertyFactory.lineColor(Color.GREEN)
        )
        mapboxMap!!.style!!.addLayer(lineLayer)
    }

    private fun styleFillColorLayer() {
        mapboxMap!!.setStyle(vn.vietmap.vietmapsdk.maps.Style.Builder().fromUri("asset://fill_color_style.json"))
        mapboxMap!!.moveCamera(
            vn.vietmap.vietmapsdk.camera.CameraUpdateFactory.newLatLngZoom(
                vn.vietmap.vietmapsdk.geometry.LatLng(31.0, (-100).toDouble()),
                3.0
            )
        )
    }

    private fun styleFillFilterLayer() {
        mapboxMap!!.setStyle(vn.vietmap.vietmapsdk.maps.Style.Builder().fromUri("asset://fill_filter_style.json"))
        mapboxMap!!.moveCamera(
            vn.vietmap.vietmapsdk.camera.CameraUpdateFactory.newLatLngZoom(
                vn.vietmap.vietmapsdk.geometry.LatLng(31.0, (-100).toDouble()),
                3.0
            )
        )
        val handler = Handler(mainLooper)
        handler.postDelayed(
            {
                if (mapboxMap == null) {
                    return@postDelayed
                }
                Timber.d("Styling filtered fill layer")
                val states = mapboxMap!!.style!!.getLayer("states") as vn.vietmap.vietmapsdk.style.layers.FillLayer?
                if (states != null) {
                    states.setFilter(
                        vn.vietmap.vietmapsdk.style.expressions.Expression.eq(
                            vn.vietmap.vietmapsdk.style.expressions.Expression.get("name"), vn.vietmap.vietmapsdk.style.expressions.Expression.literal("Texas")))
                    states.fillOpacityTransition =
                        vn.vietmap.vietmapsdk.style.layers.TransitionOptions(
                            2500,
                            0
                        )
                    states.fillColorTransition =
                        vn.vietmap.vietmapsdk.style.layers.TransitionOptions(
                            2500,
                            0
                        )
                    states.setProperties(
                        vn.vietmap.vietmapsdk.style.layers.PropertyFactory.fillColor(Color.RED),
                        vn.vietmap.vietmapsdk.style.layers.PropertyFactory.fillOpacity(0.25f)
                    )
                } else {
                    Toast.makeText(
                        this@RuntimeStyleActivity,
                        "No states layer in this style",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            2000
        )
    }

    private fun styleTextSizeFilterLayer() {
        mapboxMap!!.setStyle(vn.vietmap.vietmapsdk.maps.Style.Builder().fromUri("asset://fill_filter_style.json"))
        mapboxMap!!.moveCamera(
            vn.vietmap.vietmapsdk.camera.CameraUpdateFactory.newLatLngZoom(
                vn.vietmap.vietmapsdk.geometry.LatLng(31.0, (-100).toDouble()),
                3.0
            )
        )
        val handler = Handler(mainLooper)
        handler.postDelayed(
            {
                if (mapboxMap == null) {
                    return@postDelayed
                }
                Timber.d("Styling text size fill layer")
                val states = mapboxMap!!.style!!.getLayer("state-label-lg") as vn.vietmap.vietmapsdk.style.layers.SymbolLayer?
                if (states != null) {
                    states.setProperties(
                        vn.vietmap.vietmapsdk.style.layers.PropertyFactory.textSize(
                            vn.vietmap.vietmapsdk.style.expressions.Expression.switchCase(
                                vn.vietmap.vietmapsdk.style.expressions.Expression.`in`(
                                    vn.vietmap.vietmapsdk.style.expressions.Expression.get("name"), vn.vietmap.vietmapsdk.style.expressions.Expression.literal("Texas")),
                                vn.vietmap.vietmapsdk.style.expressions.Expression.literal(25.0f),
                                vn.vietmap.vietmapsdk.style.expressions.Expression.`in`(
                                    vn.vietmap.vietmapsdk.style.expressions.Expression.get("name"),
                                    vn.vietmap.vietmapsdk.style.expressions.Expression.literal(arrayOf<Any>("California", "Illinois"))
                                ),
                                vn.vietmap.vietmapsdk.style.expressions.Expression.literal(25.0f),
                                vn.vietmap.vietmapsdk.style.expressions.Expression.literal(6.0f) // default value
                            )
                        )
                    )
                } else {
                    Toast.makeText(
                        this@RuntimeStyleActivity,
                        "No states layer in this style",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            2000
        )
    }

    private fun styleLineFilterLayer() {
        mapboxMap!!.setStyle(vn.vietmap.vietmapsdk.maps.Style.Builder().fromUri("asset://line_filter_style.json"))
        mapboxMap!!.moveCamera(
            vn.vietmap.vietmapsdk.camera.CameraUpdateFactory.newLatLngZoom(
                vn.vietmap.vietmapsdk.geometry.LatLng(40.0, (-97).toDouble()), 5.0))
        val handler = Handler(mainLooper)
        handler.postDelayed(
            {
                if (mapboxMap == null) {
                    return@postDelayed
                }
                Timber.d("Styling filtered line layer")
                val counties = mapboxMap!!.style!!.getLayer("counties") as vn.vietmap.vietmapsdk.style.layers.LineLayer?
                if (counties != null) {
                    counties.setFilter(
                        vn.vietmap.vietmapsdk.style.expressions.Expression.eq(
                            vn.vietmap.vietmapsdk.style.expressions.Expression.get("NAME10"), "Washington"))
                    counties.setProperties(
                        vn.vietmap.vietmapsdk.style.layers.PropertyFactory.lineColor(Color.RED),
                        vn.vietmap.vietmapsdk.style.layers.PropertyFactory.lineOpacity(0.75f),
                        vn.vietmap.vietmapsdk.style.layers.PropertyFactory.lineWidth(5f)
                    )
                } else {
                    Toast.makeText(
                        this@RuntimeStyleActivity,
                        "No counties layer in this style",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            2000
        )
    }

    private fun styleNumericFillLayer() {
        mapboxMap!!.setStyle(vn.vietmap.vietmapsdk.maps.Style.Builder().fromUri("asset://numeric_filter_style.json"))
        mapboxMap!!.moveCamera(
            vn.vietmap.vietmapsdk.camera.CameraUpdateFactory.newLatLngZoom(
                vn.vietmap.vietmapsdk.geometry.LatLng(40.0, (-97).toDouble()), 5.0))
        val handler = Handler(mainLooper)
        handler.postDelayed(
            {
                if (mapboxMap == null) {
                    return@postDelayed
                }
                Timber.d("Styling numeric fill layer")
                val regions = mapboxMap!!.style!!.getLayer("regions") as vn.vietmap.vietmapsdk.style.layers.FillLayer?
                if (regions != null) {
                    regions.setFilter(
                        vn.vietmap.vietmapsdk.style.expressions.Expression.all(
                            vn.vietmap.vietmapsdk.style.expressions.Expression.gte(
                                vn.vietmap.vietmapsdk.style.expressions.Expression.toNumber(
                                    vn.vietmap.vietmapsdk.style.expressions.Expression.get("HRRNUM")),
                                vn.vietmap.vietmapsdk.style.expressions.Expression.literal(200)
                            ),
                            vn.vietmap.vietmapsdk.style.expressions.Expression.lt(
                                vn.vietmap.vietmapsdk.style.expressions.Expression.toNumber(
                                    vn.vietmap.vietmapsdk.style.expressions.Expression.get("HRRNUM")),
                                vn.vietmap.vietmapsdk.style.expressions.Expression.literal(300)
                            )
                        )
                    )
                    regions.setProperties(
                        vn.vietmap.vietmapsdk.style.layers.PropertyFactory.fillColor(Color.BLUE),
                        vn.vietmap.vietmapsdk.style.layers.PropertyFactory.fillOpacity(0.5f)
                    )
                } else {
                    Toast.makeText(
                        this@RuntimeStyleActivity,
                        "No regions layer in this style",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            2000
        )
    }

    private fun bringWaterToFront() {
        val water = mapboxMap!!.style!!.getLayer("water")
        if (water != null) {
            mapboxMap!!.style!!.removeLayer(water)
            mapboxMap!!.style!!.addLayerAt(water, mapboxMap!!.style!!.layers.size - 1)
        } else {
            Toast.makeText(this, "No water layer in this style", Toast.LENGTH_SHORT).show()
        }
    }

    private open class DefaultCallback : CancelableCallback {
        override fun onCancel() {
            // noop
        }

        override fun onFinish() {
            // noop
        }
    }
}
