package vn.vietmap.vietmapsdk.testapp.activity.style

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import vn.vietmap.vietmapsdk.maps.MapView
import vn.vietmap.vietmapsdk.maps.VietmapMap
import vn.vietmap.vietmapsdk.maps.OnMapReadyCallback
import vn.vietmap.vietmapsdk.maps.Style
import vn.vietmap.vietmapsdk.style.expressions.Expression
import vn.vietmap.vietmapsdk.style.layers.FillExtrusionLayer
import vn.vietmap.vietmapsdk.style.layers.Property
import vn.vietmap.vietmapsdk.style.layers.PropertyFactory
import vn.vietmap.vietmapsdk.style.light.Light
import vn.vietmap.vietmapsdk.style.light.Position
import vn.vietmap.vietmapsdk.testapp.R
import vn.vietmap.vietmapsdk.utils.ColorUtils

/**
 * Test activity showing 3D buildings with a FillExtrusion Layer
 */
class BuildingFillExtrusionActivity : AppCompatActivity() {
    private lateinit var mapView: vn.vietmap.vietmapsdk.maps.MapView
    private var mapboxMap: vn.vietmap.vietmapsdk.maps.VietmapMap? = null
    private var light: vn.vietmap.vietmapsdk.style.light.Light? = null
    private var isMapAnchorLight = false
    private var isLowIntensityLight = false
    private var isRedColor = false
    private var isInitPosition = false
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_building_layer)
        mapView = findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(
            vn.vietmap.vietmapsdk.maps.OnMapReadyCallback { map: vn.vietmap.vietmapsdk.maps.VietmapMap? ->
                mapboxMap = map
                mapboxMap!!.setStyle(
                    vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle(
                        "Streets"
                    )
                ) { style: vn.vietmap.vietmapsdk.maps.Style ->
                    setupBuildings(style)
                    setupLight()
                }
            }
        )
    }

    private fun setupBuildings(style: vn.vietmap.vietmapsdk.maps.Style) {
        val fillExtrusionLayer =
            vn.vietmap.vietmapsdk.style.layers.FillExtrusionLayer(
                "3d-buildings",
                "composite"
            )
        fillExtrusionLayer.sourceLayer = "building"
        fillExtrusionLayer.setFilter(
            vn.vietmap.vietmapsdk.style.expressions.Expression.eq(
                vn.vietmap.vietmapsdk.style.expressions.Expression.get("extrude"),
                vn.vietmap.vietmapsdk.style.expressions.Expression.literal("true")
            )
        )
        fillExtrusionLayer.minZoom = 15f
        fillExtrusionLayer.setProperties(
            vn.vietmap.vietmapsdk.style.layers.PropertyFactory.fillExtrusionColor(Color.LTGRAY),
            vn.vietmap.vietmapsdk.style.layers.PropertyFactory.fillExtrusionHeight(
                vn.vietmap.vietmapsdk.style.expressions.Expression.get("height")),
            vn.vietmap.vietmapsdk.style.layers.PropertyFactory.fillExtrusionBase(
                vn.vietmap.vietmapsdk.style.expressions.Expression.get("min_height")),
            vn.vietmap.vietmapsdk.style.layers.PropertyFactory.fillExtrusionOpacity(0.9f)
        )
        style.addLayer(fillExtrusionLayer)
    }

    private fun setupLight() {
        light = mapboxMap!!.style!!.light
        findViewById<View>(R.id.fabLightPosition).setOnClickListener { v: View? ->
            isInitPosition = !isInitPosition
            if (isInitPosition) {
                light!!.position =
                    vn.vietmap.vietmapsdk.style.light.Position(1.5f, 90f, 80f)
            } else {
                light!!.position =
                    vn.vietmap.vietmapsdk.style.light.Position(1.15f, 210f, 30f)
            }
        }
        findViewById<View>(R.id.fabLightColor).setOnClickListener { v: View? ->
            isRedColor = !isRedColor
            light!!.setColor(vn.vietmap.vietmapsdk.utils.ColorUtils.colorToRgbaString(if (isRedColor) Color.RED else Color.BLUE))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_building, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (light != null) {
            val id = item.itemId
            if (id == R.id.menu_action_anchor) {
                isMapAnchorLight = !isMapAnchorLight
                light!!.anchor =
                    if (isMapAnchorLight) vn.vietmap.vietmapsdk.style.layers.Property.ANCHOR_MAP else vn.vietmap.vietmapsdk.style.layers.Property.ANCHOR_VIEWPORT
            } else if (id == R.id.menu_action_intensity) {
                isLowIntensityLight = !isLowIntensityLight
                light!!.intensity = if (isLowIntensityLight) 0.35f else 1.0f
            }
        }
        return super.onOptionsItemSelected(item)
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
