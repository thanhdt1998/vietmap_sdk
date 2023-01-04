package vn.vietmap.vietmapsdk.testapp.activity.location

import android.annotation.SuppressLint
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.view.animation.Interpolator
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.ListPopupWindow
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
import java.util.ArrayList

/**
 * This activity shows how to customize the LocationComponent's pulsing circle.
 */
class CustomizedLocationPulsingCircleActivity : AppCompatActivity(),
    vn.vietmap.vietmapsdk.maps.OnMapReadyCallback {
    private var lastLocation: Location? = null
    private lateinit var mapView: vn.vietmap.vietmapsdk.maps.MapView
    private lateinit var pulsingCircleDurationButton: Button
    private lateinit var pulsingCircleColorButton: Button
    private var permissionsManager: vn.vietmap.vietmapsdk.location.permissions.PermissionsManager? = null
    private var locationComponent: vn.vietmap.vietmapsdk.location.LocationComponent? = null
    private var mapboxMap: vn.vietmap.vietmapsdk.maps.VietmapMap? = null
    private var currentPulseDuration = 0f

    //endregion
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_layer_customized_pulsing_circle)
        LOCATION_CIRCLE_PULSE_COLOR = Color.BLUE
        mapView = findViewById(R.id.mapView)
        if (savedInstanceState != null) {
            lastLocation = savedInstanceState.getParcelable(SAVED_STATE_LOCATION)
            LOCATION_CIRCLE_PULSE_COLOR = savedInstanceState.getInt(
                SAVED_STATE_LOCATION_CIRCLE_PULSE_COLOR
            )
            LOCATION_CIRCLE_PULSE_DURATION = savedInstanceState.getFloat(
                SAVED_STATE_LOCATION_CIRCLE_PULSE_DURATION
            )
        }
        pulsingCircleDurationButton = findViewById(R.id.button_location_circle_duration)
        pulsingCircleDurationButton.setText(
            String.format(
                "%sms",
                LOCATION_CIRCLE_PULSE_DURATION.toString()
            )
        )
        pulsingCircleDurationButton.setOnClickListener(
            View.OnClickListener setOnClickListener@{ v: View? ->
                if (locationComponent == null) {
                    return@setOnClickListener
                }
                showDurationListDialog()
            }
        )
        pulsingCircleColorButton = findViewById(R.id.button_location_circle_color)
        pulsingCircleColorButton.setOnClickListener(
            View.OnClickListener setOnClickListener@{ v: View? ->
                if (locationComponent == null) {
                    return@setOnClickListener
                }
                showColorListDialog()
            }
        )
        mapView.onCreate(savedInstanceState)
        checkPermissions()
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(mapboxMap: vn.vietmap.vietmapsdk.maps.VietmapMap) {
        this.mapboxMap = mapboxMap
        mapboxMap.setStyle(vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Streets")) { style: vn.vietmap.vietmapsdk.maps.Style ->
            locationComponent = mapboxMap.locationComponent
            val locationComponentOptions = buildLocationComponentOptions(
                LOCATION_CIRCLE_PULSE_COLOR,
                LOCATION_CIRCLE_PULSE_DURATION
            )
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

    private fun buildLocationComponentOptions(
        pulsingCircleColor: Int,
        pulsingCircleDuration: Float
    ): vn.vietmap.vietmapsdk.location.LocationComponentOptions.Builder {
        currentPulseDuration = pulsingCircleDuration
        return vn.vietmap.vietmapsdk.location.LocationComponentOptions.builder(this)
            .layerBelow(LAYER_BELOW_ID)
            .pulseFadeEnabled(DEFAULT_LOCATION_CIRCLE_PULSE_FADE_MODE)
            .pulseInterpolator(DEFAULT_LOCATION_CIRCLE_INTERPOLATOR_PULSE_MODE)
            .pulseColor(pulsingCircleColor)
            .pulseAlpha(DEFAULT_LOCATION_CIRCLE_PULSE_ALPHA)
            .pulseSingleDuration(pulsingCircleDuration)
            .pulseMaxRadius(DEFAULT_LOCATION_CIRCLE_PULSE_RADIUS)
    }

    @SuppressLint("MissingPermission")
    private fun setNewLocationComponentOptions(
        newPulsingDuration: Float,
        newPulsingColor: Int
    ) {
        mapboxMap!!.getStyle { style: vn.vietmap.vietmapsdk.maps.Style? ->
            locationComponent!!.applyStyle(
                buildLocationComponentOptions(
                    newPulsingColor,
                    newPulsingDuration
                )
                    .pulseEnabled(true)
                    .build()
            )
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
                    this@CustomizedLocationPulsingCircleActivity
                )
                    .pulseEnabled(false)
                    .build()
            )
            return true
        } else if (id == R.id.action_start_pulsing) {
            locationComponent!!.applyStyle(
                buildLocationComponentOptions(
                    LOCATION_CIRCLE_PULSE_COLOR,
                    LOCATION_CIRCLE_PULSE_DURATION
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
                                this@CustomizedLocationPulsingCircleActivity,
                                "You need to accept location permissions.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        override fun onPermissionResult(granted: Boolean) {
                            if (granted) {
                                mapView!!.getMapAsync(this@CustomizedLocationPulsingCircleActivity)
                            } else {
                                finish()
                            }
                        }
                    })
            permissionsManager!!.requestLocationPermissions(this)
        }
    }

    private fun showDurationListDialog() {
        val modes: MutableList<String> = ArrayList()
        modes.add(String.format("%sms", DEFAULT_LOCATION_CIRCLE_PULSE_DURATION_MS.toString()))
        modes.add(String.format("%sms", SECOND_LOCATION_CIRCLE_PULSE_DURATION_MS.toString()))
        modes.add(String.format("%sms", THIRD_LOCATION_CIRCLE_PULSE_DURATION_MS.toString()))
        val profileAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            modes
        )
        val listPopup = ListPopupWindow(this)
        listPopup.setAdapter(profileAdapter)
        listPopup.anchorView = pulsingCircleDurationButton
        listPopup.setOnItemClickListener { parent: AdapterView<*>?, itemView: View?, position: Int, id: Long ->
            val selectedMode = modes[position]
            pulsingCircleDurationButton!!.text = selectedMode
            if (selectedMode.contentEquals(
                    String.format(
                            "%sms",
                            DEFAULT_LOCATION_CIRCLE_PULSE_DURATION_MS.toString()
                        )
                )
            ) {
                LOCATION_CIRCLE_PULSE_DURATION = DEFAULT_LOCATION_CIRCLE_PULSE_DURATION_MS
                setNewLocationComponentOptions(
                    DEFAULT_LOCATION_CIRCLE_PULSE_DURATION_MS,
                    LOCATION_CIRCLE_PULSE_COLOR
                )
            } else if (selectedMode.contentEquals(
                    String.format(
                            "%sms",
                            SECOND_LOCATION_CIRCLE_PULSE_DURATION_MS.toString()
                        )
                )
            ) {
                LOCATION_CIRCLE_PULSE_DURATION = SECOND_LOCATION_CIRCLE_PULSE_DURATION_MS
                setNewLocationComponentOptions(
                    SECOND_LOCATION_CIRCLE_PULSE_DURATION_MS,
                    LOCATION_CIRCLE_PULSE_COLOR
                )
            } else if (selectedMode.contentEquals(
                    String.format(
                            "%sms",
                            THIRD_LOCATION_CIRCLE_PULSE_DURATION_MS.toString()
                        )
                )
            ) {
                LOCATION_CIRCLE_PULSE_DURATION = THIRD_LOCATION_CIRCLE_PULSE_DURATION_MS
                setNewLocationComponentOptions(
                    THIRD_LOCATION_CIRCLE_PULSE_DURATION_MS,
                    LOCATION_CIRCLE_PULSE_COLOR
                )
            }
            listPopup.dismiss()
        }
        listPopup.show()
    }

    private fun showColorListDialog() {
        val trackingTypes: MutableList<String> = ArrayList()
        trackingTypes.add("Blue")
        trackingTypes.add("Red")
        trackingTypes.add("Green")
        trackingTypes.add("Gray")
        val profileAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            trackingTypes
        )
        val listPopup = ListPopupWindow(this)
        listPopup.setAdapter(profileAdapter)
        listPopup.anchorView = pulsingCircleColorButton
        listPopup.setOnItemClickListener { parent: AdapterView<*>?, itemView: View?, position: Int, id: Long ->
            val selectedTrackingType = trackingTypes[position]
            pulsingCircleColorButton!!.text = selectedTrackingType
            if (selectedTrackingType.contentEquals("Blue")) {
                LOCATION_CIRCLE_PULSE_COLOR = Color.BLUE
                setNewLocationComponentOptions(currentPulseDuration, Color.BLUE)
            } else if (selectedTrackingType.contentEquals("Red")) {
                LOCATION_CIRCLE_PULSE_COLOR = Color.RED
                setNewLocationComponentOptions(currentPulseDuration, Color.RED)
            } else if (selectedTrackingType.contentEquals("Green")) {
                LOCATION_CIRCLE_PULSE_COLOR = Color.GREEN
                setNewLocationComponentOptions(currentPulseDuration, Color.GREEN)
            } else if (selectedTrackingType.contentEquals("Gray")) {
                LOCATION_CIRCLE_PULSE_COLOR = Color.parseColor("#4a4a4a")
                setNewLocationComponentOptions(currentPulseDuration, Color.parseColor("#4a4a4a"))
            }
            listPopup.dismiss()
        }
        listPopup.show()
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
            outState.putInt(SAVED_STATE_LOCATION_CIRCLE_PULSE_COLOR, LOCATION_CIRCLE_PULSE_COLOR)
            outState.putFloat(
                SAVED_STATE_LOCATION_CIRCLE_PULSE_DURATION,
                LOCATION_CIRCLE_PULSE_DURATION
            )
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
        //region
        // Adjust these variables to customize the example's pulsing circle UI
        private const val DEFAULT_LOCATION_CIRCLE_PULSE_DURATION_MS = 2300f
        private const val SECOND_LOCATION_CIRCLE_PULSE_DURATION_MS = 800f
        private const val THIRD_LOCATION_CIRCLE_PULSE_DURATION_MS = 8000f
        private const val DEFAULT_LOCATION_CIRCLE_PULSE_RADIUS = 35f
        private const val DEFAULT_LOCATION_CIRCLE_PULSE_ALPHA = .55f
        private val DEFAULT_LOCATION_CIRCLE_INTERPOLATOR_PULSE_MODE: Interpolator =
            DecelerateInterpolator()
        private const val DEFAULT_LOCATION_CIRCLE_PULSE_FADE_MODE = true

        //endregion
        //region
        private var LOCATION_CIRCLE_PULSE_COLOR = 0
        private var LOCATION_CIRCLE_PULSE_DURATION = DEFAULT_LOCATION_CIRCLE_PULSE_DURATION_MS
        private const val SAVED_STATE_LOCATION = "saved_state_location"
        private const val SAVED_STATE_LOCATION_CIRCLE_PULSE_COLOR = "saved_state_color"
        private const val SAVED_STATE_LOCATION_CIRCLE_PULSE_DURATION = "saved_state_duration"
        private const val LAYER_BELOW_ID = "poi_transit"
    }
}
