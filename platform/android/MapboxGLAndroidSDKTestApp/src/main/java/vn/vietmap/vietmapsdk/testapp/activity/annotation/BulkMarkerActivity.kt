package vn.vietmap.vietmapsdk.testapp.activity.annotation

import android.app.ProgressDialog
import android.os.AsyncTask
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuItemCompat
import vn.vietmap.vietmapsdk.annotations.MarkerOptions
import vn.vietmap.vietmapsdk.geometry.LatLng
import vn.vietmap.vietmapsdk.maps.MapView
import vn.vietmap.vietmapsdk.maps.VietmapMap
import vn.vietmap.vietmapsdk.maps.OnMapReadyCallback
import vn.vietmap.vietmapsdk.maps.Style
import vn.vietmap.vietmapsdk.testapp.R
import vn.vietmap.vietmapsdk.testapp.utils.GeoParseUtil
import timber.log.Timber
import java.io.IOException
import java.lang.ref.WeakReference
import java.text.DecimalFormat
import java.util.*

/**
 * Test activity showcasing adding a large amount of Markers.
 */
class BulkMarkerActivity : AppCompatActivity(), OnItemSelectedListener {
    private lateinit var mapboxMap: vn.vietmap.vietmapsdk.maps.VietmapMap
    private lateinit var mapView: vn.vietmap.vietmapsdk.maps.MapView
    private var locations: List<vn.vietmap.vietmapsdk.geometry.LatLng>? = null
    private var progressDialog: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marker_bulk)
        mapView = findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(vn.vietmap.vietmapsdk.maps.OnMapReadyCallback { mapboxMap: vn.vietmap.vietmapsdk.maps.VietmapMap ->
            initMap(
                mapboxMap
            )
        })
    }

    private fun initMap(mapboxMap: vn.vietmap.vietmapsdk.maps.VietmapMap) {
        this.mapboxMap = mapboxMap
        mapboxMap.setStyle(vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Streets"))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val spinnerAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.bulk_marker_list,
            android.R.layout.simple_spinner_item
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        menuInflater.inflate(R.menu.menu_bulk_marker, menu)
        val item = menu.findItem(R.id.spinner)
        val spinner = MenuItemCompat.getActionView(item) as Spinner
        spinner.adapter = spinnerAdapter
        spinner.onItemSelectedListener = this@BulkMarkerActivity
        return true
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
        val amount = Integer.valueOf(resources.getStringArray(R.array.bulk_marker_list)[position])
        if (locations == null) {
            progressDialog = ProgressDialog.show(this, "Loading", "Fetching markers", false)
            LoadLocationTask(this, amount).execute()
        } else {
            showMarkers(amount)
        }
    }

    private fun onLatLngListLoaded(latLngs: List<vn.vietmap.vietmapsdk.geometry.LatLng>?, amount: Int) {
        progressDialog!!.hide()
        locations = latLngs
        showMarkers(amount)
    }

    private fun showMarkers(amount: Int) {
        var amount = amount
        if (mapboxMap == null || locations == null || mapView!!.isDestroyed) {
            return
        }
        mapboxMap!!.clear()
        if (locations!!.size < amount) {
            amount = locations!!.size
        }
        showGlMarkers(amount)
    }

    private fun showGlMarkers(amount: Int) {
        val markerOptionsList: MutableList<vn.vietmap.vietmapsdk.annotations.MarkerOptions> = ArrayList()
        val formatter = DecimalFormat("#.#####")
        val random = Random()
        var randomIndex: Int
        for (i in 0 until amount) {
            randomIndex = random.nextInt(locations!!.size)
            val latLng = locations!![randomIndex]
            markerOptionsList.add(
                vn.vietmap.vietmapsdk.annotations.MarkerOptions()
                    .position(latLng)
                    .title(i.toString())
                    .snippet(formatter.format(latLng.latitude) + ", " + formatter.format(latLng.longitude))
            )
        }
        mapboxMap!!.addMarkers(markerOptionsList)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        // nothing selected, nothing to do!
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
        if (progressDialog != null) {
            progressDialog!!.dismiss()
        }
        mapView!!.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView!!.onLowMemory()
    }

    private class LoadLocationTask constructor(
        activity: BulkMarkerActivity,
        private val amount: Int
    ) : AsyncTask<Void?, Int?, List<vn.vietmap.vietmapsdk.geometry.LatLng>?>() {
        private val activity: WeakReference<BulkMarkerActivity>
        override fun doInBackground(vararg p0: Void?): List<vn.vietmap.vietmapsdk.geometry.LatLng>? {
            val activity = activity.get()
            if (activity != null) {
                var json: String? = null
                try {
                    json = vn.vietmap.vietmapsdk.testapp.utils.GeoParseUtil.loadStringFromAssets(
                        activity.applicationContext,
                        "points.geojson"
                    )
                } catch (exception: IOException) {
                    Timber.e(exception, "Could not add markers")
                }
                if (json != null) {
                    return vn.vietmap.vietmapsdk.testapp.utils.GeoParseUtil.parseGeoJsonCoordinates(json)
                }
            }
            return null
        }

        override fun onPostExecute(locations: List<vn.vietmap.vietmapsdk.geometry.LatLng>?) {
            super.onPostExecute(locations)
            val activity = activity.get()
            activity?.onLatLngListLoaded(locations, amount)
        }

        init {
            this.activity = WeakReference(activity)
        }
    }
}
