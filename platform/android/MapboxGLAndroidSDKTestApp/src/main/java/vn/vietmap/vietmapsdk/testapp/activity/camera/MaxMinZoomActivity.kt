package vn.vietmap.vietmapsdk.testapp.activity.camera

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import vn.vietmap.vietmapsdk.maps.MapView
import vn.vietmap.vietmapsdk.maps.MapView.OnDidFinishLoadingStyleListener
import vn.vietmap.vietmapsdk.maps.VietmapMap
import vn.vietmap.vietmapsdk.maps.VietmapMap.OnMapClickListener
import vn.vietmap.vietmapsdk.maps.OnMapReadyCallback
import vn.vietmap.vietmapsdk.maps.Style
import vn.vietmap.vietmapsdk.testapp.R
import timber.log.Timber

/** Test activity showcasing using maximum and minimum zoom levels to restrict camera movement. */
class MaxMinZoomActivity : AppCompatActivity(),
    vn.vietmap.vietmapsdk.maps.OnMapReadyCallback {
    private lateinit var mapView: vn.vietmap.vietmapsdk.maps.MapView
    private lateinit var mapboxMap: vn.vietmap.vietmapsdk.maps.VietmapMap
    private val clickListener = OnMapClickListener {
        if (mapboxMap != null) {
            mapboxMap!!.setStyle(
                vn.vietmap.vietmapsdk.maps.Style.Builder().fromUri(
                    vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Outdoor")))
        }
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maxmin_zoom)
        mapView = findViewById<vn.vietmap.vietmapsdk.maps.MapView>(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
        mapView.addOnDidFinishLoadingStyleListener(
            OnDidFinishLoadingStyleListener { Timber.d("Style Loaded") }
        )
    }

    override fun onMapReady(map: vn.vietmap.vietmapsdk.maps.VietmapMap) {
        mapboxMap = map
        mapboxMap!!.setStyle(vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Streets"))
        mapboxMap!!.setMinZoomPreference(3.0)
        mapboxMap!!.setMaxZoomPreference(5.0)
        mapboxMap!!.addOnMapClickListener(clickListener)
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
        if (mapboxMap != null) {
            mapboxMap!!.removeOnMapClickListener(clickListener)
        }
        mapView!!.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView!!.onLowMemory()
    }
}
