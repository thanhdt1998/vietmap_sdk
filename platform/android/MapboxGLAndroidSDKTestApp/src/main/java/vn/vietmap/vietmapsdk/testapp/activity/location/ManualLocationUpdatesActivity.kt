package vn.vietmap.vietmapsdk.testapp.activity.location

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import vn.vietmap.vietmapsdk.geometry.LatLngBounds
import vn.vietmap.vietmapsdk.location.LocationComponent
import vn.vietmap.vietmapsdk.location.LocationComponentActivationOptions
import vn.vietmap.vietmapsdk.location.engine.LocationEngine
import vn.vietmap.vietmapsdk.location.engine.LocationEngineProvider
import vn.vietmap.vietmapsdk.location.engine.LocationEngineRequest
import vn.vietmap.vietmapsdk.location.modes.RenderMode
import vn.vietmap.vietmapsdk.location.permissions.PermissionsListener
import vn.vietmap.vietmapsdk.location.permissions.PermissionsManager
import vn.vietmap.vietmapsdk.maps.MapView
import vn.vietmap.vietmapsdk.maps.VietmapMap
import vn.vietmap.vietmapsdk.maps.OnMapReadyCallback
import vn.vietmap.vietmapsdk.maps.Style
import vn.vietmap.vietmapsdk.testapp.R

class ManualLocationUpdatesActivity : AppCompatActivity(),
    vn.vietmap.vietmapsdk.maps.OnMapReadyCallback {
    private lateinit var mapView: vn.vietmap.vietmapsdk.maps.MapView
    private var locationComponent: vn.vietmap.vietmapsdk.location.LocationComponent? = null
    private var locationEngine: vn.vietmap.vietmapsdk.location.engine.LocationEngine? = null
    private var permissionsManager: vn.vietmap.vietmapsdk.location.permissions.PermissionsManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_manual_update)
        locationEngine = vn.vietmap.vietmapsdk.location.engine.LocationEngineProvider.getBestLocationEngine(this, false)
        val fabManualUpdate = findViewById<FloatingActionButton>(R.id.fabManualLocationChange)
        fabManualUpdate.setOnClickListener { v: View? ->
            if (locationComponent != null && locationComponent!!.locationEngine == null) {
                locationComponent!!.forceLocationUpdate(
                    Utils.getRandomLocation(vn.vietmap.vietmapsdk.geometry.LatLngBounds.from(60.0, 25.0, 40.0, -5.0))
                )
            }
        }
        fabManualUpdate.isEnabled = false
        val fabToggle = findViewById<FloatingActionButton>(R.id.fabToggleManualLocation)
        fabToggle.setOnClickListener { v: View? ->
            if (locationComponent != null) {
                locationComponent!!.locationEngine =
                    if (locationComponent!!.locationEngine == null) locationEngine else null
                if (locationComponent!!.locationEngine == null) {
                    fabToggle.setImageResource(R.drawable.ic_layers_clear)
                    fabManualUpdate.isEnabled = true
                    fabManualUpdate.alpha = 1f
                    Toast.makeText(
                        this@ManualLocationUpdatesActivity.applicationContext,
                        "LocationEngine disabled, use manual updates",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    fabToggle.setImageResource(R.drawable.ic_layers)
                    fabManualUpdate.isEnabled = false
                    fabManualUpdate.alpha = 0.5f
                    Toast.makeText(
                        this@ManualLocationUpdatesActivity.applicationContext,
                        "LocationEngine enabled",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        mapView = findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        if (vn.vietmap.vietmapsdk.location.permissions.PermissionsManager.areLocationPermissionsGranted(this)) {
            mapView.getMapAsync(this)
        } else {
            permissionsManager =
                vn.vietmap.vietmapsdk.location.permissions.PermissionsManager(
                    object :
                        vn.vietmap.vietmapsdk.location.permissions.PermissionsListener {
                        override fun onExplanationNeeded(permissionsToExplain: List<String>) {
                            Toast.makeText(
                                this@ManualLocationUpdatesActivity.applicationContext,
                                "You need to accept location permissions.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        override fun onPermissionResult(granted: Boolean) {
                            if (granted) {
                                mapView.getMapAsync(this@ManualLocationUpdatesActivity)
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

    @SuppressLint("MissingPermission")
    override fun onMapReady(mapboxMap: vn.vietmap.vietmapsdk.maps.VietmapMap) {
        mapboxMap.setStyle(
            vn.vietmap.vietmapsdk.maps.Style.Builder().fromUri(
                vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Streets"))
        ) { style: vn.vietmap.vietmapsdk.maps.Style? ->
            locationComponent = mapboxMap.locationComponent
            locationComponent!!.activateLocationComponent(
                vn.vietmap.vietmapsdk.location.LocationComponentActivationOptions
                    .builder(this, style!!)
                    .locationEngine(locationEngine)
                    .locationEngineRequest(
                        vn.vietmap.vietmapsdk.location.engine.LocationEngineRequest.Builder(500)
                            .setFastestInterval(500)
                            .setPriority(vn.vietmap.vietmapsdk.location.engine.LocationEngineRequest.PRIORITY_HIGH_ACCURACY).build()
                    )
                    .build()
            )
            locationComponent!!.isLocationComponentEnabled = true
            locationComponent!!.renderMode = vn.vietmap.vietmapsdk.location.modes.RenderMode.COMPASS
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
