package vn.vietmap.vietmapsdk.location

import android.content.Context
import android.content.res.Resources
import android.content.res.TypedArray
import android.location.Location
import android.os.Looper
import com.mapbox.vietmapsdk.R
import vn.vietmap.vietmapsdk.camera.CameraPosition
import vn.vietmap.vietmapsdk.location.LocationComponentConstants.TRANSITION_ANIMATION_DURATION_MS
import vn.vietmap.vietmapsdk.location.engine.LocationEngine
import vn.vietmap.vietmapsdk.location.engine.LocationEngineRequest
import vn.vietmap.vietmapsdk.location.modes.CameraMode
import vn.vietmap.vietmapsdk.location.modes.RenderMode
import vn.vietmap.vietmapsdk.maps.VietmapMap
import vn.vietmap.vietmapsdk.maps.Projection
import vn.vietmap.vietmapsdk.maps.Style
import vn.vietmap.vietmapsdk.maps.Transform
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class LocationComponentTest {
    private lateinit var locationComponent: vn.vietmap.vietmapsdk.location.LocationComponent

    @Mock
    private lateinit var locationComponentOptions: vn.vietmap.vietmapsdk.location.LocationComponentOptions

    @Mock
    private lateinit var VietmapMap: vn.vietmap.vietmapsdk.maps.VietmapMap

    @Mock
    private lateinit var transform: vn.vietmap.vietmapsdk.maps.Transform

    @Mock
    private lateinit var context: Context

    @Mock
    private lateinit var locationEngine: vn.vietmap.vietmapsdk.location.engine.LocationEngine

    @Mock
    private lateinit var locationEngineRequest: vn.vietmap.vietmapsdk.location.engine.LocationEngineRequest

    @Mock
    private lateinit var currentListener: vn.vietmap.vietmapsdk.location.LocationComponent.CurrentLocationEngineCallback

    @Mock
    private lateinit var lastListener: vn.vietmap.vietmapsdk.location.LocationComponent.LastLocationEngineCallback

    @Mock
    private lateinit var compassEngine: vn.vietmap.vietmapsdk.location.CompassEngine

    @Mock
    private lateinit var locationLayerController: vn.vietmap.vietmapsdk.location.LocationLayerController

    @Mock
    private lateinit var locationCameraController: vn.vietmap.vietmapsdk.location.LocationCameraController

    @Mock
    private lateinit var locationAnimatorCoordinator: vn.vietmap.vietmapsdk.location.LocationAnimatorCoordinator

    @Mock
    private lateinit var staleStateManager: vn.vietmap.vietmapsdk.location.StaleStateManager

    @Mock
    private lateinit var locationEngineProvider: vn.vietmap.vietmapsdk.location.LocationComponent.InternalLocationEngineProvider

    @Mock
    private lateinit var style: vn.vietmap.vietmapsdk.maps.Style

    private lateinit var developerAnimationListeners: List<vn.vietmap.vietmapsdk.maps.VietmapMap.OnDeveloperAnimationListener>

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)
        developerAnimationListeners = mutableListOf()
        locationComponent = vn.vietmap.vietmapsdk.location.LocationComponent(
            VietmapMap,
            transform,
            developerAnimationListeners,
            currentListener,
            lastListener,
            locationLayerController,
            locationCameraController,
            locationAnimatorCoordinator,
            staleStateManager,
            compassEngine,
            locationEngineProvider,
            false
        )
        doReturn(locationEngine).`when`(locationEngineProvider).getBestLocationEngine(context, false)
        doReturn(style).`when`(VietmapMap).style
    }

    @Test
    fun activateWithRequestTest() {
        locationComponent.activateLocationComponent(context, mock(vn.vietmap.vietmapsdk.maps.Style::class.java), locationEngine, locationEngineRequest, locationComponentOptions)

        Assert.assertEquals(locationEngineRequest, locationComponent.locationEngineRequest)

        doReturn(mock(TypedArray::class.java)).`when`(context)
            .obtainStyledAttributes(R.style.mapbox_LocationComponent, R.styleable.mapbox_LocationComponent)

        val resources = mock(Resources::class.java)

        doReturn(resources).`when`(context).resources
        doReturn(0f).`when`(resources)
            .getDimension(R.dimen.mapbox_locationComponentTrackingMultiFingerMoveThreshold)
        doReturn(0f).`when`(resources)
            .getDimension(R.dimen.mapbox_locationComponentTrackingMultiFingerMoveThreshold)
        locationComponent.activateLocationComponent(context, mock(vn.vietmap.vietmapsdk.maps.Style::class.java), true, locationEngineRequest)
        Assert.assertEquals(locationEngineRequest, locationComponent.locationEngineRequest)
    }

    @Test
    fun activateWithDefaultLocationEngineRequestAndOptionsTestDefaultLocationEngine() {
        locationComponent.activateLocationComponent(context, mock(vn.vietmap.vietmapsdk.maps.Style::class.java), true, locationEngineRequest, locationComponentOptions)
        Assert.assertEquals(locationEngineRequest, locationComponent.locationEngineRequest)
        Assert.assertNotNull(locationComponent.locationEngine)
    }

    @Test
    fun activateWithDefaultLocationEngineRequestAndOptionsTestCustomLocationEngine() {
        locationComponent.activateLocationComponent(context, mock(vn.vietmap.vietmapsdk.maps.Style::class.java), false, locationEngineRequest, locationComponentOptions)
        Assert.assertEquals(locationEngineRequest, locationComponent.locationEngineRequest)
        Assert.assertNull(locationComponent.locationEngine)
    }

    @Test
    fun locationUpdatesWhenEnabledDisableTest() {
        locationComponent.activateLocationComponent(context, mock(vn.vietmap.vietmapsdk.maps.Style::class.java), locationEngine, locationEngineRequest, locationComponentOptions)
        verify(locationEngine, times(0)).removeLocationUpdates(currentListener)
        verify(locationEngine, times(0)).requestLocationUpdates(eq(locationEngineRequest), eq(currentListener), any(Looper::class.java))

        locationComponent.onStart()
        verify(locationEngine, times(0)).removeLocationUpdates(currentListener)
        verify(locationEngine, times(0)).requestLocationUpdates(eq(locationEngineRequest), eq(currentListener), any(Looper::class.java))

        locationComponent.isLocationComponentEnabled = true
        verify(locationEngine).requestLocationUpdates(eq(locationEngineRequest), eq(currentListener), any(Looper::class.java))

        locationComponent.isLocationComponentEnabled = false
        verify(locationEngine).requestLocationUpdates(eq(locationEngineRequest), eq(currentListener), any(Looper::class.java))
        verify(locationEngine).removeLocationUpdates(currentListener)
    }

    @Test
    fun locationUpdatesWhenStartedStoppedTest() {
        locationComponent.activateLocationComponent(context, mock(vn.vietmap.vietmapsdk.maps.Style::class.java), locationEngine, locationEngineRequest, locationComponentOptions)
        locationComponent.onStart()
        locationComponent.isLocationComponentEnabled = true

        locationComponent.onStop()
        verify(locationEngine).removeLocationUpdates(currentListener)

        locationComponent.onStart()
        verify(locationEngine, times(2)).requestLocationUpdates(eq(locationEngineRequest), eq(currentListener), any(Looper::class.java))
    }

    @Test
    fun locationUpdatesWhenNewRequestTest() {
        locationComponent.activateLocationComponent(context, mock(vn.vietmap.vietmapsdk.maps.Style::class.java), locationEngine, locationEngineRequest, locationComponentOptions)
        locationComponent.onStart()
        locationComponent.isLocationComponentEnabled = true

        val newRequest = mock(vn.vietmap.vietmapsdk.location.engine.LocationEngineRequest::class.java)
        locationComponent.locationEngineRequest = newRequest
        verify(locationEngine).removeLocationUpdates(currentListener)
        verify(locationEngine).requestLocationUpdates(eq(newRequest), eq(currentListener), any(Looper::class.java))
    }

    @Test
    fun lastLocationUpdateOnStartTest() {
        locationComponent.activateLocationComponent(context, mock(vn.vietmap.vietmapsdk.maps.Style::class.java), locationEngine, locationEngineRequest, locationComponentOptions)
        locationComponent.onStart()
        locationComponent.isLocationComponentEnabled = true

        verify(locationEngine).getLastLocation(lastListener)
    }

    @Test
    fun transitionCallbackFinishedTest() {
        locationComponent.activateLocationComponent(context, mock(vn.vietmap.vietmapsdk.maps.Style::class.java), locationEngine, locationEngineRequest, locationComponentOptions)
        locationComponent.onStart()
        locationComponent.isLocationComponentEnabled = true
        `when`(VietmapMap.cameraPosition).thenReturn(vn.vietmap.vietmapsdk.camera.CameraPosition.DEFAULT)

        val listener = mock(vn.vietmap.vietmapsdk.location.OnLocationCameraTransitionListener::class.java)

        val callback = ArgumentCaptor.forClass(vn.vietmap.vietmapsdk.location.OnLocationCameraTransitionListener::class.java)
        locationComponent.setCameraMode(vn.vietmap.vietmapsdk.location.modes.CameraMode.TRACKING, listener)
        verify(locationCameraController).setCameraMode(eq(vn.vietmap.vietmapsdk.location.modes.CameraMode.TRACKING), any(), eq(TRANSITION_ANIMATION_DURATION_MS), isNull(), isNull(), isNull(), callback.capture())
        callback.value.onLocationCameraTransitionFinished(vn.vietmap.vietmapsdk.location.modes.CameraMode.TRACKING)

        verify(listener).onLocationCameraTransitionFinished(vn.vietmap.vietmapsdk.location.modes.CameraMode.TRACKING)
        verify(locationAnimatorCoordinator).resetAllCameraAnimations(vn.vietmap.vietmapsdk.camera.CameraPosition.DEFAULT, false)
    }

    @Test
    fun transitionCallbackCanceledTest() {
        locationComponent.activateLocationComponent(context, mock(vn.vietmap.vietmapsdk.maps.Style::class.java), locationEngine, locationEngineRequest, locationComponentOptions)
        locationComponent.onStart()
        locationComponent.isLocationComponentEnabled = true
        `when`(VietmapMap.cameraPosition).thenReturn(vn.vietmap.vietmapsdk.camera.CameraPosition.DEFAULT)

        val listener = mock(vn.vietmap.vietmapsdk.location.OnLocationCameraTransitionListener::class.java)

        val callback = ArgumentCaptor.forClass(vn.vietmap.vietmapsdk.location.OnLocationCameraTransitionListener::class.java)
        locationComponent.setCameraMode(vn.vietmap.vietmapsdk.location.modes.CameraMode.TRACKING, listener)
        verify(locationCameraController).setCameraMode(eq(vn.vietmap.vietmapsdk.location.modes.CameraMode.TRACKING), any(), eq(TRANSITION_ANIMATION_DURATION_MS), isNull(), isNull(), isNull(), callback.capture())
        callback.value.onLocationCameraTransitionCanceled(vn.vietmap.vietmapsdk.location.modes.CameraMode.TRACKING)

        verify(listener).onLocationCameraTransitionCanceled(vn.vietmap.vietmapsdk.location.modes.CameraMode.TRACKING)
        verify(locationAnimatorCoordinator).resetAllCameraAnimations(vn.vietmap.vietmapsdk.camera.CameraPosition.DEFAULT, false)
    }

    @Test
    fun transitionCustomFinishedTest() {
        locationComponent.activateLocationComponent(context, mock(vn.vietmap.vietmapsdk.maps.Style::class.java), locationEngine, locationEngineRequest, locationComponentOptions)
        locationComponent.onStart()
        locationComponent.isLocationComponentEnabled = true
        `when`(VietmapMap.cameraPosition).thenReturn(vn.vietmap.vietmapsdk.camera.CameraPosition.DEFAULT)

        val listener = mock(vn.vietmap.vietmapsdk.location.OnLocationCameraTransitionListener::class.java)

        val callback = ArgumentCaptor.forClass(vn.vietmap.vietmapsdk.location.OnLocationCameraTransitionListener::class.java)
        locationComponent.setCameraMode(vn.vietmap.vietmapsdk.location.modes.CameraMode.TRACKING, 1200, 14.0, 13.0, 45.0, listener)
        verify(locationCameraController).setCameraMode(eq(vn.vietmap.vietmapsdk.location.modes.CameraMode.TRACKING), any(), eq(1200L), eq(14.0), eq(13.0), eq(45.0), callback.capture())
        callback.value.onLocationCameraTransitionFinished(vn.vietmap.vietmapsdk.location.modes.CameraMode.TRACKING)

        verify(listener).onLocationCameraTransitionFinished(vn.vietmap.vietmapsdk.location.modes.CameraMode.TRACKING)
        verify(locationAnimatorCoordinator).resetAllCameraAnimations(vn.vietmap.vietmapsdk.camera.CameraPosition.DEFAULT, false)
    }

    @Test
    fun compass_listenWhenConsumedByNoneCamera() {
        locationComponent.activateLocationComponent(context, mock(vn.vietmap.vietmapsdk.maps.Style::class.java), locationEngine, locationEngineRequest, locationComponentOptions)
        locationComponent.onStart()
        locationComponent.isLocationComponentEnabled = true
        `when`(VietmapMap.cameraPosition).thenReturn(vn.vietmap.vietmapsdk.camera.CameraPosition.DEFAULT)

        `when`(locationCameraController.isConsumingCompass).thenReturn(true)
        locationComponent.cameraMode = vn.vietmap.vietmapsdk.location.modes.CameraMode.NONE_COMPASS
        verify(compassEngine).addCompassListener(any(vn.vietmap.vietmapsdk.location.CompassListener::class.java))
    }

    @Test
    fun compass_listenWhenConsumedByTrackingCamera() {
        locationComponent.activateLocationComponent(context, mock(vn.vietmap.vietmapsdk.maps.Style::class.java), locationEngine, locationEngineRequest, locationComponentOptions)
        locationComponent.onStart()
        locationComponent.isLocationComponentEnabled = true
        `when`(VietmapMap.cameraPosition).thenReturn(vn.vietmap.vietmapsdk.camera.CameraPosition.DEFAULT)

        `when`(locationCameraController.isConsumingCompass).thenReturn(true)
        locationComponent.cameraMode = vn.vietmap.vietmapsdk.location.modes.CameraMode.TRACKING_COMPASS
        verify(compassEngine).addCompassListener(any(vn.vietmap.vietmapsdk.location.CompassListener::class.java))
    }

    @Test
    fun compass_listenWhenConsumedByLayer() {
        locationComponent.activateLocationComponent(context, mock(vn.vietmap.vietmapsdk.maps.Style::class.java), locationEngine, locationEngineRequest, locationComponentOptions)
        locationComponent.onStart()
        locationComponent.isLocationComponentEnabled = true
        `when`(VietmapMap.cameraPosition).thenReturn(vn.vietmap.vietmapsdk.camera.CameraPosition.DEFAULT)

        `when`(locationLayerController.isConsumingCompass).thenReturn(true)
        locationComponent.renderMode = vn.vietmap.vietmapsdk.location.modes.RenderMode.COMPASS
        verify(compassEngine).addCompassListener(any(vn.vietmap.vietmapsdk.location.CompassListener::class.java))
    }

    @Test
    fun compass_notListenWhenNotConsumed() {
        locationComponent.activateLocationComponent(context, mock(vn.vietmap.vietmapsdk.maps.Style::class.java), locationEngine, locationEngineRequest, locationComponentOptions)
        locationComponent.onStart()
        locationComponent.isLocationComponentEnabled = true
        `when`(VietmapMap.cameraPosition).thenReturn(vn.vietmap.vietmapsdk.camera.CameraPosition.DEFAULT)

        `when`(locationLayerController.isConsumingCompass).thenReturn(false)
        `when`(locationCameraController.isConsumingCompass).thenReturn(false)
        locationComponent.renderMode = vn.vietmap.vietmapsdk.location.modes.RenderMode.GPS
        locationComponent.renderMode = vn.vietmap.vietmapsdk.location.modes.RenderMode.NORMAL
        locationComponent.cameraMode = vn.vietmap.vietmapsdk.location.modes.CameraMode.TRACKING
        locationComponent.cameraMode = vn.vietmap.vietmapsdk.location.modes.CameraMode.NONE
        locationComponent.cameraMode = vn.vietmap.vietmapsdk.location.modes.CameraMode.NONE_GPS
        locationComponent.cameraMode = vn.vietmap.vietmapsdk.location.modes.CameraMode.TRACKING_GPS
        locationComponent.cameraMode = vn.vietmap.vietmapsdk.location.modes.CameraMode.TRACKING_GPS_NORTH
        verify(compassEngine, never()).addCompassListener(any(vn.vietmap.vietmapsdk.location.CompassListener::class.java))
    }

    @Test
    fun compass_removeListenerOnChange() {
        locationComponent.activateLocationComponent(context, mock(vn.vietmap.vietmapsdk.maps.Style::class.java), locationEngine, locationEngineRequest, locationComponentOptions)
        locationComponent.onStart()
        locationComponent.isLocationComponentEnabled = true
        `when`(VietmapMap.cameraPosition).thenReturn(vn.vietmap.vietmapsdk.camera.CameraPosition.DEFAULT)

        `when`(locationLayerController.isConsumingCompass).thenReturn(true)
        locationComponent.renderMode = vn.vietmap.vietmapsdk.location.modes.RenderMode.COMPASS
        `when`(locationLayerController.isConsumingCompass).thenReturn(false)
        locationComponent.renderMode = vn.vietmap.vietmapsdk.location.modes.RenderMode.NORMAL
        verify(compassEngine).removeCompassListener(any(vn.vietmap.vietmapsdk.location.CompassListener::class.java))
    }

    @Test
    fun compass_removeListenerOnStop() {
        locationComponent.activateLocationComponent(context, mock(vn.vietmap.vietmapsdk.maps.Style::class.java), locationEngine, locationEngineRequest, locationComponentOptions)
        locationComponent.onStart()
        locationComponent.isLocationComponentEnabled = true
        `when`(VietmapMap.cameraPosition).thenReturn(vn.vietmap.vietmapsdk.camera.CameraPosition.DEFAULT)

        `when`(locationLayerController.isConsumingCompass).thenReturn(true)
        locationComponent.renderMode = vn.vietmap.vietmapsdk.location.modes.RenderMode.COMPASS
        locationComponent.onStop()
        verify(compassEngine).removeCompassListener(any(vn.vietmap.vietmapsdk.location.CompassListener::class.java))
    }

    @Test
    fun compass_reAddListenerOnStart() {
        locationComponent.activateLocationComponent(context, mock(vn.vietmap.vietmapsdk.maps.Style::class.java), locationEngine, locationEngineRequest, locationComponentOptions)
        locationComponent.onStart()
        locationComponent.isLocationComponentEnabled = true
        `when`(VietmapMap.cameraPosition).thenReturn(vn.vietmap.vietmapsdk.camera.CameraPosition.DEFAULT)

        `when`(locationLayerController.isConsumingCompass).thenReturn(true)
        locationComponent.renderMode = vn.vietmap.vietmapsdk.location.modes.RenderMode.COMPASS
        locationComponent.onStop()
        locationComponent.onStart()
        verify(compassEngine, times(2)).addCompassListener(any(vn.vietmap.vietmapsdk.location.CompassListener::class.java))
    }

    @Test
    fun compass_removeListenerOnStyleStartLoad() {
        locationComponent.activateLocationComponent(context, mock(vn.vietmap.vietmapsdk.maps.Style::class.java), locationEngine, locationEngineRequest, locationComponentOptions)
        locationComponent.onStart()
        locationComponent.isLocationComponentEnabled = true
        `when`(VietmapMap.cameraPosition).thenReturn(vn.vietmap.vietmapsdk.camera.CameraPosition.DEFAULT)

        `when`(locationLayerController.isConsumingCompass).thenReturn(true)
        locationComponent.renderMode = vn.vietmap.vietmapsdk.location.modes.RenderMode.COMPASS
        locationComponent.onStartLoadingMap()
        verify(compassEngine).removeCompassListener(any(vn.vietmap.vietmapsdk.location.CompassListener::class.java))
    }

    @Test
    fun compass_reAddListenerOnStyleLoadFinished() {
        locationComponent.activateLocationComponent(context, mock(vn.vietmap.vietmapsdk.maps.Style::class.java), locationEngine, locationEngineRequest, locationComponentOptions)
        locationComponent.onStart()
        locationComponent.isLocationComponentEnabled = true
        `when`(VietmapMap.cameraPosition).thenReturn(vn.vietmap.vietmapsdk.camera.CameraPosition.DEFAULT)

        `when`(locationLayerController.isConsumingCompass).thenReturn(true)
        locationComponent.renderMode = vn.vietmap.vietmapsdk.location.modes.RenderMode.COMPASS
        locationComponent.onStartLoadingMap()
        locationComponent.onFinishLoadingStyle()
        verify(compassEngine, times(2)).addCompassListener(any(vn.vietmap.vietmapsdk.location.CompassListener::class.java))
    }

    @Test
    fun compass_reAddListenerOnlyWhenEnabled() {
        locationComponent.activateLocationComponent(context, mock(vn.vietmap.vietmapsdk.maps.Style::class.java), locationEngine, locationEngineRequest, locationComponentOptions)
        locationComponent.onStart()
        locationComponent.isLocationComponentEnabled = true
        `when`(VietmapMap.cameraPosition).thenReturn(vn.vietmap.vietmapsdk.camera.CameraPosition.DEFAULT)

        `when`(locationLayerController.isConsumingCompass).thenReturn(true)
        locationComponent.renderMode = vn.vietmap.vietmapsdk.location.modes.RenderMode.COMPASS
        locationComponent.isLocationComponentEnabled = false

        locationComponent.onStartLoadingMap()
        locationComponent.onFinishLoadingStyle()
        verify(compassEngine).addCompassListener(any(vn.vietmap.vietmapsdk.location.CompassListener::class.java))
    }

    @Test
    fun compass_notAdListenerWhenDisabled() {
        locationComponent.activateLocationComponent(context, mock(vn.vietmap.vietmapsdk.maps.Style::class.java), locationEngine, locationEngineRequest, locationComponentOptions)
        locationComponent.onStart()
        `when`(VietmapMap.cameraPosition).thenReturn(vn.vietmap.vietmapsdk.camera.CameraPosition.DEFAULT)

        `when`(locationLayerController.isConsumingCompass).thenReturn(true)
        locationComponent.renderMode = vn.vietmap.vietmapsdk.location.modes.RenderMode.COMPASS
        verify(compassEngine, never()).addCompassListener(any(vn.vietmap.vietmapsdk.location.CompassListener::class.java))
    }

    @Test
    fun compass_notAdListenerWhenStopped() {
        locationComponent.activateLocationComponent(context, mock(vn.vietmap.vietmapsdk.maps.Style::class.java), locationEngine, locationEngineRequest, locationComponentOptions)
        locationComponent.isLocationComponentEnabled = true
        `when`(VietmapMap.cameraPosition).thenReturn(vn.vietmap.vietmapsdk.camera.CameraPosition.DEFAULT)

        `when`(locationLayerController.isConsumingCompass).thenReturn(true)
        locationComponent.renderMode = vn.vietmap.vietmapsdk.location.modes.RenderMode.COMPASS
        verify(compassEngine, never()).addCompassListener(any(vn.vietmap.vietmapsdk.location.CompassListener::class.java))
    }

    @Test
    fun compass_notAddListenerWhenLayerNotReady() {
        locationComponent.activateLocationComponent(context, mock(vn.vietmap.vietmapsdk.maps.Style::class.java), locationEngine, locationEngineRequest, locationComponentOptions)
        locationComponent.onStart()
        locationComponent.isLocationComponentEnabled = true
        `when`(VietmapMap.cameraPosition).thenReturn(vn.vietmap.vietmapsdk.camera.CameraPosition.DEFAULT)
        `when`(locationLayerController.isConsumingCompass).thenReturn(true)
        locationComponent.renderMode = vn.vietmap.vietmapsdk.location.modes.RenderMode.COMPASS

        verify(compassEngine, times(1)).addCompassListener(any(vn.vietmap.vietmapsdk.location.CompassListener::class.java))

        locationComponent.onStartLoadingMap()
        // Layer should be disabled at this point
        locationComponent.setCameraMode(vn.vietmap.vietmapsdk.location.modes.CameraMode.TRACKING_COMPASS)
        verify(compassEngine, times(1)).addCompassListener(any(vn.vietmap.vietmapsdk.location.CompassListener::class.java))
    }

    @Test
    fun developerAnimationCalled() {
        locationComponent.activateLocationComponent(context, mock(vn.vietmap.vietmapsdk.maps.Style::class.java), locationEngine, locationEngineRequest, locationComponentOptions)
        locationComponent.isLocationComponentEnabled = true
        for (listener in developerAnimationListeners) {
            listener.onDeveloperAnimationStarted()
        }
        verify(locationCameraController).setCameraMode(eq(vn.vietmap.vietmapsdk.location.modes.CameraMode.NONE), isNull<Location>(), eq(TRANSITION_ANIMATION_DURATION_MS), isNull<Double>(), isNull<Double>(), isNull<Double>(), any())
    }

    @Test
    fun internal_cameraTrackingChangedListener_onCameraTrackingDismissed() {
        locationComponent.activateLocationComponent(context, mock(vn.vietmap.vietmapsdk.maps.Style::class.java), locationEngine, locationEngineRequest, locationComponentOptions)
        locationComponent.isLocationComponentEnabled = true

        val cameraChangeListener: vn.vietmap.vietmapsdk.location.OnCameraTrackingChangedListener = mock(
            vn.vietmap.vietmapsdk.location.OnCameraTrackingChangedListener::class.java)
        locationComponent.addOnCameraTrackingChangedListener(cameraChangeListener)

        locationComponent.cameraTrackingChangedListener.onCameraTrackingDismissed()

        verify(cameraChangeListener).onCameraTrackingDismissed()
    }

    @Test
    fun internal_cameraTrackingChangedListener_onCameraTrackingChanged() {
        locationComponent.activateLocationComponent(context, mock(vn.vietmap.vietmapsdk.maps.Style::class.java), locationEngine, locationEngineRequest, locationComponentOptions)
        locationComponent.isLocationComponentEnabled = true

        val cameraValueListener: vn.vietmap.vietmapsdk.location.AnimatorListenerHolder = mock(vn.vietmap.vietmapsdk.location.AnimatorListenerHolder::class.java)
        val layerValueListener: vn.vietmap.vietmapsdk.location.AnimatorListenerHolder = mock(vn.vietmap.vietmapsdk.location.AnimatorListenerHolder::class.java)
        `when`(locationCameraController.animationListeners).thenReturn(setOf(cameraValueListener))
        `when`(locationLayerController.animationListeners).thenReturn(setOf(layerValueListener))
        val cameraChangeListener: vn.vietmap.vietmapsdk.location.OnCameraTrackingChangedListener = mock(
            vn.vietmap.vietmapsdk.location.OnCameraTrackingChangedListener::class.java)
        locationComponent.addOnCameraTrackingChangedListener(cameraChangeListener)

        locationComponent.cameraTrackingChangedListener.onCameraTrackingChanged(vn.vietmap.vietmapsdk.location.modes.CameraMode.TRACKING_GPS)

        verify(locationAnimatorCoordinator).cancelZoomAnimation()
        verify(locationAnimatorCoordinator).cancelTiltAnimation()
        verify(locationAnimatorCoordinator).updateAnimatorListenerHolders(eq(setOf(cameraValueListener, layerValueListener)))
        verify(locationAnimatorCoordinator).resetAllCameraAnimations(any(), anyBoolean())
        verify(locationAnimatorCoordinator).resetAllLayerAnimations()
        verify(cameraChangeListener).onCameraTrackingChanged(vn.vietmap.vietmapsdk.location.modes.CameraMode.TRACKING_GPS)
    }

    @Test
    fun internal_renderModeChangedListener_onRenderModeChanged() {
        locationComponent.activateLocationComponent(context, mock(vn.vietmap.vietmapsdk.maps.Style::class.java), locationEngine, locationEngineRequest, locationComponentOptions)
        locationComponent.isLocationComponentEnabled = true

        val cameraListener: vn.vietmap.vietmapsdk.location.AnimatorListenerHolder = mock(vn.vietmap.vietmapsdk.location.AnimatorListenerHolder::class.java)
        val layerListener: vn.vietmap.vietmapsdk.location.AnimatorListenerHolder = mock(vn.vietmap.vietmapsdk.location.AnimatorListenerHolder::class.java)
        `when`(locationCameraController.animationListeners).thenReturn(setOf(cameraListener))
        `when`(locationLayerController.animationListeners).thenReturn(setOf(layerListener))
        val renderChangeListener: vn.vietmap.vietmapsdk.location.OnRenderModeChangedListener = mock(
            vn.vietmap.vietmapsdk.location.OnRenderModeChangedListener::class.java)
        locationComponent.addOnRenderModeChangedListener(renderChangeListener)

        locationComponent.renderModeChangedListener.onRenderModeChanged(vn.vietmap.vietmapsdk.location.modes.RenderMode.NORMAL)

        verify(locationAnimatorCoordinator).updateAnimatorListenerHolders(eq(setOf(cameraListener, layerListener)))
        verify(locationAnimatorCoordinator).resetAllCameraAnimations(any(), anyBoolean())
        verify(locationAnimatorCoordinator).resetAllLayerAnimations()
        verify(renderChangeListener).onRenderModeChanged(vn.vietmap.vietmapsdk.location.modes.RenderMode.NORMAL)
    }

    @Test
    fun change_to_gps_mode_symbolLayerBearingValue() {
        val location = Location("test")
        location.bearing = 50f
        val projection: vn.vietmap.vietmapsdk.maps.Projection = mock(vn.vietmap.vietmapsdk.maps.Projection::class.java)
        `when`(projection.getMetersPerPixelAtLatitude(location.latitude)).thenReturn(10.0)
        `when`(VietmapMap.projection).thenReturn(projection)
        `when`(style.isFullyLoaded).thenReturn(true)
        `when`(VietmapMap.cameraPosition).thenReturn(vn.vietmap.vietmapsdk.camera.CameraPosition.DEFAULT)

        locationComponent.activateLocationComponent(
            vn.vietmap.vietmapsdk.location.LocationComponentActivationOptions.builder(context, style)
                .locationComponentOptions(locationComponentOptions)
                .useDefaultLocationEngine(false)
                .build()
        )
        locationComponent.isLocationComponentEnabled = true
        locationComponent.onStart()
        locationComponent.renderMode = vn.vietmap.vietmapsdk.location.modes.RenderMode.NORMAL
        locationComponent.forceLocationUpdate(location)

        verify(locationLayerController, times(0)).setGpsBearing(50f)

        locationComponent.renderMode = vn.vietmap.vietmapsdk.location.modes.RenderMode.GPS
        verify(locationLayerController, times(1)).setGpsBearing(50f)
        verify(locationAnimatorCoordinator).cancelAndRemoveGpsBearingAnimation()
    }

    @Test
    fun tiltWhileTracking_notReady() {
        `when`(VietmapMap.cameraPosition).thenReturn(vn.vietmap.vietmapsdk.camera.CameraPosition.DEFAULT)
        locationComponent.activateLocationComponent(context, mock(vn.vietmap.vietmapsdk.maps.Style::class.java), locationEngine, locationEngineRequest, locationComponentOptions)
        locationComponent.isLocationComponentEnabled = true

        val callback = mock(vn.vietmap.vietmapsdk.maps.VietmapMap.CancelableCallback::class.java)

        locationComponent.tiltWhileTracking(30.0, 500L, callback)
        verify(callback).onCancel()
        verify(locationAnimatorCoordinator, times(0)).feedNewTilt(anyDouble(), any(), anyLong(), any())
    }

    @Test
    fun tiltWhileTracking_notTracking() {
        `when`(VietmapMap.cameraPosition).thenReturn(vn.vietmap.vietmapsdk.camera.CameraPosition.DEFAULT)
        `when`(locationCameraController.cameraMode).thenReturn(vn.vietmap.vietmapsdk.location.modes.CameraMode.NONE)
        locationComponent.activateLocationComponent(context, mock(vn.vietmap.vietmapsdk.maps.Style::class.java), locationEngine, locationEngineRequest, locationComponentOptions)
        locationComponent.isLocationComponentEnabled = true
        locationComponent.onStart()

        val callback = mock(vn.vietmap.vietmapsdk.maps.VietmapMap.CancelableCallback::class.java)

        locationComponent.tiltWhileTracking(30.0, 500L, callback)
        verify(callback).onCancel()
        verify(locationAnimatorCoordinator, times(0)).feedNewTilt(anyDouble(), any(), anyLong(), any())
    }

    @Test
    fun tiltWhileTracking_transitioning() {
        `when`(VietmapMap.cameraPosition).thenReturn(vn.vietmap.vietmapsdk.camera.CameraPosition.DEFAULT)
        `when`(locationCameraController.cameraMode).thenReturn(vn.vietmap.vietmapsdk.location.modes.CameraMode.TRACKING)
        `when`(locationCameraController.isTransitioning).thenReturn(true)
        locationComponent.activateLocationComponent(context, mock(vn.vietmap.vietmapsdk.maps.Style::class.java), locationEngine, locationEngineRequest, locationComponentOptions)
        locationComponent.isLocationComponentEnabled = true
        locationComponent.onStart()

        val callback = mock(vn.vietmap.vietmapsdk.maps.VietmapMap.CancelableCallback::class.java)

        locationComponent.tiltWhileTracking(30.0, 500L, callback)
        verify(callback).onCancel()
        verify(locationAnimatorCoordinator, times(0)).feedNewTilt(anyDouble(), any(), anyLong(), any())
    }

    @Test
    fun tiltWhileTracking_sucessful() {
        `when`(VietmapMap.cameraPosition).thenReturn(vn.vietmap.vietmapsdk.camera.CameraPosition.DEFAULT)
        `when`(locationCameraController.cameraMode).thenReturn(vn.vietmap.vietmapsdk.location.modes.CameraMode.TRACKING)
        `when`(locationCameraController.isTransitioning).thenReturn(false)
        locationComponent.activateLocationComponent(context, mock(vn.vietmap.vietmapsdk.maps.Style::class.java), locationEngine, locationEngineRequest, locationComponentOptions)
        locationComponent.isLocationComponentEnabled = true
        locationComponent.onStart()

        val callback = mock(vn.vietmap.vietmapsdk.maps.VietmapMap.CancelableCallback::class.java)

        locationComponent.tiltWhileTracking(30.0, 500L, callback)
        verify(callback, times(0)).onCancel()
        verify(locationAnimatorCoordinator).feedNewTilt(30.0, vn.vietmap.vietmapsdk.camera.CameraPosition.DEFAULT, 500L, callback)
    }

    @Test
    fun zoomWhileTracking_notReady() {
        `when`(VietmapMap.cameraPosition).thenReturn(vn.vietmap.vietmapsdk.camera.CameraPosition.DEFAULT)
        locationComponent.activateLocationComponent(context, mock(vn.vietmap.vietmapsdk.maps.Style::class.java), locationEngine, locationEngineRequest, locationComponentOptions)
        locationComponent.isLocationComponentEnabled = true

        val callback = mock(vn.vietmap.vietmapsdk.maps.VietmapMap.CancelableCallback::class.java)

        locationComponent.zoomWhileTracking(14.0, 500L, callback)
        verify(callback).onCancel()
        verify(locationAnimatorCoordinator, times(0)).feedNewZoomLevel(anyDouble(), any(), anyLong(), any())
    }

    @Test
    fun zoomWhileTracking_notTracking() {
        `when`(VietmapMap.cameraPosition).thenReturn(vn.vietmap.vietmapsdk.camera.CameraPosition.DEFAULT)
        `when`(locationCameraController.cameraMode).thenReturn(vn.vietmap.vietmapsdk.location.modes.CameraMode.NONE)
        locationComponent.activateLocationComponent(context, mock(vn.vietmap.vietmapsdk.maps.Style::class.java), locationEngine, locationEngineRequest, locationComponentOptions)
        locationComponent.isLocationComponentEnabled = true
        locationComponent.onStart()

        val callback = mock(vn.vietmap.vietmapsdk.maps.VietmapMap.CancelableCallback::class.java)

        locationComponent.zoomWhileTracking(14.0, 500L, callback)
        verify(callback).onCancel()
        verify(locationAnimatorCoordinator, times(0)).feedNewZoomLevel(anyDouble(), any(), anyLong(), any())
    }

    @Test
    fun zoomWhileTracking_transitioning() {
        `when`(VietmapMap.cameraPosition).thenReturn(vn.vietmap.vietmapsdk.camera.CameraPosition.DEFAULT)
        `when`(locationCameraController.cameraMode).thenReturn(vn.vietmap.vietmapsdk.location.modes.CameraMode.TRACKING)
        `when`(locationCameraController.isTransitioning).thenReturn(true)
        locationComponent.activateLocationComponent(context, mock(vn.vietmap.vietmapsdk.maps.Style::class.java), locationEngine, locationEngineRequest, locationComponentOptions)
        locationComponent.isLocationComponentEnabled = true
        locationComponent.onStart()

        val callback = mock(vn.vietmap.vietmapsdk.maps.VietmapMap.CancelableCallback::class.java)

        locationComponent.zoomWhileTracking(14.0, 500L, callback)
        verify(callback).onCancel()
        verify(locationAnimatorCoordinator, times(0)).feedNewZoomLevel(anyDouble(), any(), anyLong(), any())
    }

    @Test
    fun zoomWhileTracking_successful() {
        `when`(VietmapMap.cameraPosition).thenReturn(vn.vietmap.vietmapsdk.camera.CameraPosition.DEFAULT)
        `when`(locationCameraController.cameraMode).thenReturn(vn.vietmap.vietmapsdk.location.modes.CameraMode.TRACKING)
        `when`(locationCameraController.isTransitioning).thenReturn(false)
        locationComponent.activateLocationComponent(context, mock(vn.vietmap.vietmapsdk.maps.Style::class.java), locationEngine, locationEngineRequest, locationComponentOptions)
        locationComponent.isLocationComponentEnabled = true
        locationComponent.onStart()

        val callback = mock(vn.vietmap.vietmapsdk.maps.VietmapMap.CancelableCallback::class.java)

        locationComponent.zoomWhileTracking(14.0, 500L, callback)
        verify(callback, times(0)).onCancel()
        verify(locationAnimatorCoordinator).feedNewZoomLevel(14.0, vn.vietmap.vietmapsdk.camera.CameraPosition.DEFAULT, 500L, callback)
    }

    @Test
    fun newLocation_accuracy_symbolLayerRadiusValue() {
        val location = Location("test")
        location.accuracy = 50f
        val projection: vn.vietmap.vietmapsdk.maps.Projection = mock(vn.vietmap.vietmapsdk.maps.Projection::class.java)
        `when`(projection.getMetersPerPixelAtLatitude(location.latitude)).thenReturn(10.0)
        `when`(VietmapMap.projection).thenReturn(projection)
        `when`(style.isFullyLoaded).thenReturn(true)
        locationComponent.activateLocationComponent(
            vn.vietmap.vietmapsdk.location.LocationComponentActivationOptions.builder(context, style)
                .locationComponentOptions(locationComponentOptions)
                .useDefaultLocationEngine(false)
                .build()
        )
        locationComponent.isLocationComponentEnabled = true
        locationComponent.onStart()
        locationComponent.forceLocationUpdate(location)

        val radius = (location.accuracy * (1 / 10.0)).toFloat()
        verify(locationAnimatorCoordinator).feedNewAccuracyRadius(radius, false)
    }

    @Test
    fun newLocation_accuracy_indicatorLayerRadiusValue() {
        val location = Location("test")
        location.accuracy = 50f
        `when`(style.isFullyLoaded).thenReturn(true)
        locationComponent = vn.vietmap.vietmapsdk.location.LocationComponent(
            VietmapMap,
            transform,
            developerAnimationListeners,
            currentListener,
            lastListener,
            locationLayerController,
            locationCameraController,
            locationAnimatorCoordinator,
            staleStateManager,
            compassEngine,
            locationEngineProvider,
            true
        )
        locationComponent.activateLocationComponent(
            vn.vietmap.vietmapsdk.location.LocationComponentActivationOptions.builder(context, style)
                .locationComponentOptions(locationComponentOptions)
                .useSpecializedLocationLayer(true)
                .useDefaultLocationEngine(false)
                .build()
        )
        locationComponent.isLocationComponentEnabled = true
        locationComponent.onStart()
        locationComponent.forceLocationUpdate(location)

        verify(locationAnimatorCoordinator).feedNewAccuracyRadius(location.accuracy, false)
    }
}
