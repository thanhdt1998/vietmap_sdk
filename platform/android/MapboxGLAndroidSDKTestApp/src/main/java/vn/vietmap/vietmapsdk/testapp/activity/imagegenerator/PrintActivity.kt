package vn.vietmap.vietmapsdk.testapp.activity.imagegenerator

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.print.PrintHelper
import vn.vietmap.vietmapsdk.maps.MapView
import vn.vietmap.vietmapsdk.maps.VietmapMap
import vn.vietmap.vietmapsdk.maps.OnMapReadyCallback
import vn.vietmap.vietmapsdk.maps.Style
import vn.vietmap.vietmapsdk.testapp.R

/**
 * Test activity showcasing using the Snaphot API to print a Map.
 */
class PrintActivity : AppCompatActivity(), vn.vietmap.vietmapsdk.maps.VietmapMap.SnapshotReadyCallback {
    private lateinit var mapView: vn.vietmap.vietmapsdk.maps.MapView
    private lateinit var mapboxMap: vn.vietmap.vietmapsdk.maps.VietmapMap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_print)
        mapView = findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(vn.vietmap.vietmapsdk.maps.OnMapReadyCallback { mapboxMap: vn.vietmap.vietmapsdk.maps.VietmapMap ->
            initMap(
                mapboxMap
            )
        })
        val fab = findViewById<View>(R.id.fab)
        fab?.setOnClickListener { view: View? ->
            if (mapboxMap != null && mapboxMap!!.style != null) {
                mapboxMap!!.snapshot(this@PrintActivity)
            }
        }
    }

    private fun initMap(mapboxMap: vn.vietmap.vietmapsdk.maps.VietmapMap) {
        this.mapboxMap = mapboxMap
        mapboxMap.setStyle(vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Streets"))
    }

    override fun onSnapshotReady(snapshot: Bitmap) {
        val photoPrinter = PrintHelper(this)
        photoPrinter.scaleMode = PrintHelper.SCALE_MODE_FIT
        photoPrinter.printBitmap("map.jpg - mapbox print job", snapshot)
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
