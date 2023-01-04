package vn.vietmap.vietmapsdk.testapp.activity.location

import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import vn.vietmap.vietmapsdk.location.LocationComponent
import vn.vietmap.vietmapsdk.location.LocationComponentActivationOptions
import vn.vietmap.vietmapsdk.location.LocationComponentOptions
import vn.vietmap.vietmapsdk.location.engine.LocationEngineRequest
import vn.vietmap.vietmapsdk.location.modes.CameraMode
import vn.vietmap.vietmapsdk.location.permissions.PermissionsListener
import vn.vietmap.vietmapsdk.location.permissions.PermissionsManager
import vn.vietmap.vietmapsdk.maps.MapView
import vn.vietmap.vietmapsdk.maps.VietmapMap
import vn.vietmap.vietmapsdk.maps.OnMapReadyCallback
import vn.vietmap.vietmapsdk.maps.Style
import vn.vietmap.vietmapsdk.testapp.R

/**
 * This activity shows a basic usage of the LocationComponent's pulsing circle. There's no
 * customization of the pulsing circle's color, radius, speed, etc.
 */
class BasicLocationPulsingCircleActivity : AppCompatActivity(),
    vn.vietmap.vietmapsdk.maps.OnMapReadyCallback {
    private var lastLocation: Location? = null
    private lateinit var mapView: vn.vietmap.vietmapsdk.maps.MapView
    private var permissionsManager: vn.vietmap.vietmapsdk.location.permissions.PermissionsManager? = null
    private var locationComponent: vn.vietmap.vietmapsdk.location.LocationComponent? = null
    private var mapboxMap: vn.vietmap.vietmapsdk.maps.VietmapMap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_layer_basic_pulsing_circle)
        mapView = findViewById(R.id.mapView)
        if (savedInstanceState != null) {
            lastLocation = savedInstanceState.getParcelable(SAVED_STATE_LOCATION)
        }
        mapView.onCreate(savedInstanceState)
        checkPermissions()
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(mapboxMap: vn.vietmap.vietmapsdk.maps.VietmapMap) {
        this.mapboxMap = mapboxMap
        mapboxMap.setStyle(vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Streets")) { style: vn.vietmap.vietmapsdk.maps.Style ->
            locationComponent = mapboxMap.locationComponent
            val locationComponentOptions =
                vn.vietmap.vietmapsdk.location.LocationComponentOptions.builder(this@BasicLocationPulsingCircleActivity)
                    .pulseEnabled(true)
                    .build()
            val locationComponentActivationOptions =
                buildLocationComponentActivationOptions(style, locationComponentOptions)
            locationComponent!!.activateLocationComponent(locationComponentActivationOptions)
            locationComponent!!.isLocationComponentEnabled = true
            locationComponent!!.cameraMode = vn.vietmap.vietmapsdk.location.modes.CameraMode.TRACKING
            locationComponent!!.forceLocationUpdate(lastLocation)
        }
    }

    private fun buildLocationComponentActivationOptions(
        style: vn.vietmap.vietmapsdk.maps.Style,
        locationComponentOptions: vn.vietmap.vietmapsdk.location.LocationComponentOptions
    ): vn.vietmap.vietmapsdk.location.LocationComponentActivationOptions {
        return vn.vietmap.vietmapsdk.location.LocationComponentActivationOptions
            .builder(this, style)
            .locationComponentOptions(locationComponentOptions)
            .useDefaultLocationEngine(true)
            .locationEngineRequest(
                vn.vietmap.vietmapsdk.location.engine.LocationEngineRequest.Builder(750)
                    .setFastestInterval(750)
                    .setPriority(vn.vietmap.vietmapsdk.location.engine.LocationEngineRequest.PRIORITY_HIGH_ACCURACY)
                    .build()
            )
            .build()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_pulsing_location_mode, menu)
        return true
    }

    @SuppressLint("MissingPermission")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (locationComponent == null) {
            return super.onOptionsItemSelected(item)
        }
        val id = item.itemId
        if (id == R.id.action_map_style_change) {
            loadNewStyle()
            return true
        } else if (id == R.id.action_component_disable) {
            locationComponent!!.isLocationComponentEnabled = false
            return true
        } else if (id == R.id.action_component_enabled) {
            locationComponent!!.isLocationComponentEnabled = true
            return true
        } else if (id == R.id.action_stop_pulsing) {
            locationComponent!!.applyStyle(
                vn.vietmap.vietmapsdk.location.LocationComponentOptions.builder(
                    this@BasicLocationPulsingCircleActivity
                )
                    .pulseEnabled(false)
                    .build()
            )
            return true
        } else if (id == R.id.action_start_pulsing) {
            locationComponent!!.applyStyle(
                vn.vietmap.vietmapsdk.location.LocationComponentOptions.builder(
                    this@BasicLocationPulsingCircleActivity
                )
                    .pulseEnabled(true)
                    .build()
            )
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadNewStyle() {
        mapboxMap!!.setStyle(vn.vietmap.vietmapsdk.maps.Style.Builder().fromUri(Utils.nextStyle()))
    }

    private fun checkPermissions() {
        if (vn.vietmap.vietmapsdk.location.permissions.PermissionsManager.areLocationPermissionsGranted(this)) {
            mapView!!.getMapAsync(this)
        } else {
            permissionsManager =
                vn.vietmap.vietmapsdk.location.permissions.PermissionsManager(
                    object :
                        vn.vietmap.vietmapsdk.location.permissions.PermissionsListener {
                        override fun onExplanationNeeded(permissionsToExplain: List<String>) {
                            Toast.makeText(
                                this@BasicLocationPulsingCircleActivity,
                                "You need to accept location permissions.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        override fun onPermissionResult(granted: Boolean) {
                            if (granted) {
                                mapView!!.getMapAsync(this@BasicLocationPulsingCircleActivity)
                            } else {
                                finish()
                            }
                        }
                    })
            permissionsManager!!.requestLocationPermissions(this)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionsManager!!.onRequestPermissionsResult(requestCode, permissions, grantResults)
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

    @SuppressLint("MissingPermission")
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView!!.onSaveInstanceState(outState)
        if (locationComponent != null) {
            outState.putParcelable(SAVED_STATE_LOCATION, locationComponent!!.lastKnownLocation)
        }
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
        private const val SAVED_STATE_LOCATION = "saved_state_location"
        private const val TAG = "Mbgl-BasicLocationPulsingCircleActivity"
    }
}
