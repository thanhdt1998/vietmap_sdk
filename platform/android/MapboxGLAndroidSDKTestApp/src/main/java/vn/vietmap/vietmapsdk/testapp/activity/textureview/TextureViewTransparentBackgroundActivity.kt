package vn.vietmap.vietmapsdk.testapp.activity.textureview

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import vn.vietmap.vietmapsdk.camera.CameraPosition
import vn.vietmap.vietmapsdk.geometry.LatLng
// import com.mapbox.vietmapsdk.maps.* //
import vn.vietmap.vietmapsdk.testapp.R
import vn.vietmap.vietmapsdk.testapp.utils.ResourceUtils
import timber.log.Timber
import java.io.IOException

/**
 * Example showcasing how to create a TextureView with a transparent background.
 */
class TextureViewTransparentBackgroundActivity : AppCompatActivity() {
    private var mapView: vn.vietmap.vietmapsdk.maps.MapView? = null
    private val mapboxMap: vn.vietmap.vietmapsdk.maps.VietmapMap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_textureview_transparent)
        setupBackground()
        setupMapView(savedInstanceState)
    }

    private fun setupBackground() {
        val imageView = findViewById<ImageView>(R.id.imageView)
        imageView.setImageResource(R.drawable.water)
        imageView.scaleType = ImageView.ScaleType.FIT_XY
    }

    private fun setupMapView(savedInstanceState: Bundle?) {
        val mapboxMapOptions = vn.vietmap.vietmapsdk.maps.VietMapOptions.createFromAttributes(this, null)
        mapboxMapOptions.translucentTextureSurface(true)
        mapboxMapOptions.textureMode(true)
        mapboxMapOptions.camera(
            vn.vietmap.vietmapsdk.camera.CameraPosition.Builder()
                .zoom(2.0)
                .target(
                    vn.vietmap.vietmapsdk.geometry.LatLng(
                        48.507879,
                        8.363795
                    )
                )
                .build()
        )
        mapView = vn.vietmap.vietmapsdk.maps.MapView(this, mapboxMapOptions)
        mapView!!.onCreate(savedInstanceState)
        mapView!!.getMapAsync { mapboxMap: vn.vietmap.vietmapsdk.maps.VietmapMap -> initMap(mapboxMap) }
        (findViewById<View>(R.id.coordinator_layout) as ViewGroup).addView(mapView)
    }

    private fun initMap(mapboxMap: vn.vietmap.vietmapsdk.maps.VietmapMap) {
        try {
            mapboxMap.setStyle(
                vn.vietmap.vietmapsdk.maps.Style.Builder().fromJson(ResourceUtils.readRawResource(this, R.raw.no_bg_style))
            )
        } catch (exception: IOException) {
            Timber.e(exception)
        }
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
}
