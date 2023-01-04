package vn.vietmap.vietmapsdk.testapp.activity.camera

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.FeatureCollection.fromJson
import com.mapbox.geojson.Point
import vn.vietmap.vietmapsdk.geometry.LatLng
import vn.vietmap.vietmapsdk.geometry.LatLngBounds
import vn.vietmap.vietmapsdk.maps.VietmapMap
import vn.vietmap.vietmapsdk.maps.Style
import vn.vietmap.vietmapsdk.style.layers.Property.ICON_ANCHOR_CENTER
import vn.vietmap.vietmapsdk.style.layers.PropertyFactory.*
import vn.vietmap.vietmapsdk.style.layers.SymbolLayer
import vn.vietmap.vietmapsdk.style.sources.GeoJsonSource
import vn.vietmap.vietmapsdk.testapp.R
import vn.vietmap.vietmapsdk.testapp.utils.GeoParseUtil.loadStringFromAssets
import vn.vietmap.vietmapsdk.utils.BitmapUtils
import kotlinx.android.synthetic.main.activity_latlngbounds.*
import java.net.URISyntaxException

/** Test activity showcasing using the LatLngBounds camera API. */
class LatLngBoundsActivity : AppCompatActivity() {

    private lateinit var VietmapMap: vn.vietmap.vietmapsdk.maps.VietmapMap
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<*>
    private lateinit var bounds: vn.vietmap.vietmapsdk.geometry.LatLngBounds

    private val peekHeight by lazy {
        375.toPx(this) // 375dp
    }

    private val additionalPadding by lazy {
        32.toPx(this) // 32dp
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_latlngbounds)
        initMapView(savedInstanceState)
    }

    private fun initMapView(savedInstanceState: Bundle?) {
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync { map ->
            VietmapMap = map

            val featureCollection: FeatureCollection =
                fromJson(loadStringFromAssets(this, "points-sf.geojson"))
            bounds = createBounds(featureCollection)

            map.getCameraForLatLngBounds(bounds, createPadding(peekHeight))?.let {
                map.cameraPosition = it
            }

            try {
                loadStyle(featureCollection)
            } catch (e: URISyntaxException) {
                e.printStackTrace()
            }
        }
    }

    private fun loadStyle(featureCollection: FeatureCollection) {
        VietmapMap.setStyle(
            vn.vietmap.vietmapsdk.maps.Style.Builder()
                .fromUri(vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Streets"))
                .withLayer(
                    vn.vietmap.vietmapsdk.style.layers.SymbolLayer(
                        "symbol",
                        "symbol"
                    )
                        .withProperties(
                            iconAllowOverlap(true),
                            iconIgnorePlacement(true),
                            iconImage("icon"),
                            iconAnchor(ICON_ANCHOR_CENTER)
                        )
                )
                .withSource(
                    vn.vietmap.vietmapsdk.style.sources.GeoJsonSource(
                        "symbol",
                        featureCollection
                    )
                )
                .withImage(
                    "icon",
                    vn.vietmap.vietmapsdk.utils.BitmapUtils.getDrawableFromRes(
                        this@LatLngBoundsActivity,
                        R.drawable.ic_android
                    )!!
                )
        ) {
            initBottomSheet()
            fab.setOnClickListener {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
    }

    private fun initBottomSheet() {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.setBottomSheetCallback(
            object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    val offset = convertSlideOffset(slideOffset)
                    val bottomPadding = (peekHeight * offset).toInt()

                    VietmapMap.getCameraForLatLngBounds(bounds, createPadding(bottomPadding))
                        ?.let { VietmapMap.cameraPosition = it }
                }

                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    // no-op
                }
            }
        )
    }

    // slideOffset ranges from NaN to -1.0, range from 1.0 to 0 instead
    fun convertSlideOffset(slideOffset: Float): Float {
        return if (slideOffset.equals(Float.NaN)) {
            1.0f
        } else {
            1 + slideOffset
        }
    }

    fun createPadding(bottomPadding: Int): IntArray {
        return intArrayOf(additionalPadding, additionalPadding, additionalPadding, bottomPadding)
    }

    private fun createBounds(featureCollection: FeatureCollection): vn.vietmap.vietmapsdk.geometry.LatLngBounds {
        val boundsBuilder = vn.vietmap.vietmapsdk.geometry.LatLngBounds.Builder()
        featureCollection.features()?.let {
            for (feature in it) {
                val point = feature.geometry() as Point
                boundsBuilder.include(
                    vn.vietmap.vietmapsdk.geometry.LatLng(
                        point.latitude(),
                        point.longitude()
                    )
                )
            }
        }
        return boundsBuilder.build()
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
        mapView.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }
}

fun Int.toPx(context: Context): Int = (this * context.resources.displayMetrics.density).toInt()
