package vn.vietmap.vietmapsdk.testapp.activity.textureview

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import vn.vietmap.vietmapsdk.camera.CameraUpdateFactory
import vn.vietmap.vietmapsdk.geometry.LatLng
// import com.mapbox.vietmapsdk.maps.* //
import vn.vietmap.vietmapsdk.maps.VietmapMap.CancelableCallback
import vn.vietmap.vietmapsdk.testapp.R
import java.util.*

/**
 * Test animating a [android.view.TextureView] backed map.
 */
class TextureViewAnimationActivity : AppCompatActivity() {
    private var mapView: vn.vietmap.vietmapsdk.maps.MapView? = null
    private var mapboxMap: vn.vietmap.vietmapsdk.maps.VietmapMap? = null
    private var handler: Handler? = null
    private var delayed: Runnable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_textureview_animate)
        handler = Handler(mainLooper)
        setupToolbar()
        setupMapView(savedInstanceState)
    }

    private fun setupToolbar() {
        val actionBar = supportActionBar
        if (actionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setHomeButtonEnabled(true)
        }
    }

    @SuppressLint("ObjectAnimatorBinding")
    private fun setupMapView(savedInstanceState: Bundle?) {
        mapView = findViewById<View>(R.id.mapView) as vn.vietmap.vietmapsdk.maps.MapView
        mapView!!.getMapAsync { mapboxMap: vn.vietmap.vietmapsdk.maps.VietmapMap ->
            this@TextureViewAnimationActivity.mapboxMap = mapboxMap
            mapboxMap.setStyle(vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Streets"))
            setFpsView(mapboxMap)

            // Animate the map view
            val animation = ObjectAnimator.ofFloat(mapView, "rotationY", 0.0f, 360f)
            animation.duration = 3600
            animation.repeatCount = ObjectAnimator.INFINITE
            animation.start()

            // Start an animation on the map as well
            flyTo(mapboxMap, 0, 14.0)
        }
    }

    private fun flyTo(mapboxMap: vn.vietmap.vietmapsdk.maps.VietmapMap, place: Int, zoom: Double) {
        mapboxMap.animateCamera(
            vn.vietmap.vietmapsdk.camera.CameraUpdateFactory.newLatLngZoom(PLACES[place], zoom),
            10000,
            object : CancelableCallback {
                override fun onCancel() {
                    delayed = Runnable {
                        delayed = null
                        flyTo(mapboxMap, place, zoom)
                    }
                    handler!!.postDelayed(delayed!!, 2000)
                }

                override fun onFinish() {
                    flyTo(mapboxMap, if (place == PLACES.size - 1) 0 else place + 1, zoom)
                }
            }
        )
    }

    private fun setFpsView(mapboxMap: vn.vietmap.vietmapsdk.maps.VietmapMap) {
        val fpsView = findViewById<View>(R.id.fpsView) as TextView
        mapboxMap.setOnFpsChangedListener { fps: Double ->
            fpsView.text = String.format(Locale.US, "FPS: %4.2f", fps)
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
        if (handler != null && delayed != null) {
            handler!!.removeCallbacks(delayed!!)
        }
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

    companion object {
        private val PLACES = arrayOf(
            vn.vietmap.vietmapsdk.geometry.LatLng(37.7749, -122.4194), // SF
            vn.vietmap.vietmapsdk.geometry.LatLng(38.9072, -77.0369), // DC
            vn.vietmap.vietmapsdk.geometry.LatLng(52.3702, 4.8952), // AMS
            vn.vietmap.vietmapsdk.geometry.LatLng(60.1699, 24.9384), // HEL
            vn.vietmap.vietmapsdk.geometry.LatLng(-13.1639, -74.2236), // AYA
            vn.vietmap.vietmapsdk.geometry.LatLng(52.5200, 13.4050), // BER
            vn.vietmap.vietmapsdk.geometry.LatLng(12.9716, 77.5946), // BAN
            vn.vietmap.vietmapsdk.geometry.LatLng(31.2304, 121.4737) // SHA
        )
    }
}
