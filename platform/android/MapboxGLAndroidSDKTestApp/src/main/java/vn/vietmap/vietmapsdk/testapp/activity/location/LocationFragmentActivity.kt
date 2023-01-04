package vn.vietmap.vietmapsdk.testapp.activity.location

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import vn.vietmap.vietmapsdk.camera.CameraUpdateFactory
import vn.vietmap.vietmapsdk.geometry.LatLng
import vn.vietmap.vietmapsdk.location.LocationComponentActivationOptions
import vn.vietmap.vietmapsdk.location.engine.LocationEngineCallback
import vn.vietmap.vietmapsdk.location.engine.LocationEngineResult
import vn.vietmap.vietmapsdk.location.permissions.PermissionsListener
import vn.vietmap.vietmapsdk.location.permissions.PermissionsManager
import vn.vietmap.vietmapsdk.maps.MapView
import vn.vietmap.vietmapsdk.maps.VietmapMap
import vn.vietmap.vietmapsdk.maps.Style
import vn.vietmap.vietmapsdk.testapp.R
import kotlinx.android.synthetic.main.activity_location_layer_fragment.*

class LocationFragmentActivity : AppCompatActivity() {
    private lateinit var permissionsManager: vn.vietmap.vietmapsdk.location.permissions.PermissionsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_layer_fragment)

        fab.setOnClickListener {
            val fragment = supportFragmentManager.findFragmentByTag(EmptyFragment.TAG)
            if (fragment == null) {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, EmptyFragment.newInstance(), EmptyFragment.TAG)
                    .addToBackStack("transaction2")
                    .commit()
            } else {
                this.onBackPressed()
            }
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (vn.vietmap.vietmapsdk.location.permissions.PermissionsManager.areLocationPermissionsGranted(this)) {
            if (savedInstanceState == null) {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, LocationFragment.newInstance(), LocationFragment.TAG)
                    .commit()
            }
        } else {
            permissionsManager =
                vn.vietmap.vietmapsdk.location.permissions.PermissionsManager(
                    object :
                        vn.vietmap.vietmapsdk.location.permissions.PermissionsListener {
                        override fun onExplanationNeeded(permissionsToExplain: MutableList<String>?) {
                            Toast.makeText(
                                this@LocationFragmentActivity,
                                "You need to accept location permissions.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        override fun onPermissionResult(granted: Boolean) {
                            if (granted) {
                                if (savedInstanceState == null) {
                                    supportFragmentManager
                                        .beginTransaction()
                                        .replace(
                                            R.id.container,
                                            LocationFragment.newInstance(),
                                            LocationFragment.TAG
                                        )
                                        .commit()
                                }
                            } else {
                                finish()
                            }
                        }
                    })
            permissionsManager.requestLocationPermissions(this)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    class LocationFragment : androidx.fragment.app.Fragment(),
        vn.vietmap.vietmapsdk.location.engine.LocationEngineCallback<vn.vietmap.vietmapsdk.location.engine.LocationEngineResult> {
        companion object {
            const val TAG = "LFragment"
            fun newInstance(): LocationFragment {
                return LocationFragment()
            }
        }

        private lateinit var mapView: vn.vietmap.vietmapsdk.maps.MapView
        private lateinit var VietmapMap: vn.vietmap.vietmapsdk.maps.VietmapMap

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            mapView = vn.vietmap.vietmapsdk.maps.MapView(inflater.context)
            return mapView
        }

        @SuppressLint("MissingPermission")
        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            mapView.onCreate(savedInstanceState)
            mapView.getMapAsync {
                VietmapMap = it
                it.setStyle(vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Streets")) { style ->
                    val component = VietmapMap.locationComponent

                    component.activateLocationComponent(
                        vn.vietmap.vietmapsdk.location.LocationComponentActivationOptions
                            .builder(activity!!, style)
                            .useDefaultLocationEngine(true)
                            .build()
                    )

                    component.isLocationComponentEnabled = true
                    component.locationEngine?.getLastLocation(this)
                }
            }
        }

        override fun onSuccess(result: vn.vietmap.vietmapsdk.location.engine.LocationEngineResult?) {
            if (!mapView.isDestroyed) VietmapMap.animateCamera(
                vn.vietmap.vietmapsdk.camera.CameraUpdateFactory.newLatLngZoom(
                    vn.vietmap.vietmapsdk.geometry.LatLng(result?.lastLocation), 12.0))
        }

        override fun onFailure(exception: Exception) {
            // noop
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

        override fun onSaveInstanceState(outState: Bundle) {
            super.onSaveInstanceState(outState)
            mapView.onSaveInstanceState(outState)
        }

        override fun onStop() {
            super.onStop()
            mapView.onStop()
        }

        override fun onLowMemory() {
            super.onLowMemory()
            mapView.onLowMemory()
        }

        override fun onDestroyView() {
            super.onDestroyView()
            mapView.onDestroy()
        }
    }

    class EmptyFragment : androidx.fragment.app.Fragment() {
        companion object {
            const val TAG = "EmptyFragment"
            fun newInstance(): EmptyFragment {
                return EmptyFragment()
            }
        }

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            val textView = TextView(inflater.context)
            textView.text = "This is an empty Fragment"
            return textView
        }
    }
}
