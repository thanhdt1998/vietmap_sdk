package vn.vietmap.vietmapsdk.maps

import android.content.Context
import vn.vietmap.vietmapsdk.VietmapInjector
import vn.vietmap.vietmapsdk.camera.CameraPosition
import vn.vietmap.vietmapsdk.camera.CameraUpdateFactory
import vn.vietmap.vietmapsdk.constants.VietmapConstants
import vn.vietmap.vietmapsdk.geometry.LatLng
import vn.vietmap.vietmapsdk.geometry.LatLngBounds
import vn.vietmap.vietmapsdk.style.layers.TransitionOptions
import vn.vietmap.vietmapsdk.utils.ConfigUtils
import io.mockk.*
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class VietmapMapTest {

    private lateinit var VietmapMap: vn.vietmap.vietmapsdk.maps.VietmapMap

    private lateinit var nativeMapView: vn.vietmap.vietmapsdk.maps.NativeMap

    private lateinit var transform: vn.vietmap.vietmapsdk.maps.Transform

    private lateinit var cameraChangeDispatcher: vn.vietmap.vietmapsdk.maps.CameraChangeDispatcher

    private lateinit var developerAnimationListener: vn.vietmap.vietmapsdk.maps.VietmapMap.OnDeveloperAnimationListener

    @Mock
    private lateinit var context: Context
    @Mock
    private lateinit var appContext: Context

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        vn.vietmap.vietmapsdk.VietmapInjector.inject(context, "abcdef", ConfigUtils.getMockedOptions())
        cameraChangeDispatcher = spyk()
        developerAnimationListener = mockk(relaxed = true)
        nativeMapView = mockk(relaxed = true)
        transform = mockk(relaxed = true)
        VietmapMap = vn.vietmap.vietmapsdk.maps.VietmapMap(
            nativeMapView,
            transform,
            mockk(relaxed = true),
            null,
            null,
            cameraChangeDispatcher,
            listOf(developerAnimationListener)
        )
        every { nativeMapView.isDestroyed } returns false
        every { nativeMapView.nativePtr } returns 5
        VietmapMap.injectLocationComponent(spyk())
        VietmapMap.setStyle(vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Streets"))
        VietmapMap.onFinishLoadingStyle()
    }

    @Test
    fun testTransitionOptions() {
        val expected = vn.vietmap.vietmapsdk.style.layers.TransitionOptions(100, 200)
        VietmapMap.style?.transition = expected
        verify { nativeMapView.transitionOptions = expected }
    }

    @Test
    fun testMoveCamera() {
        val callback = mockk<vn.vietmap.vietmapsdk.maps.VietmapMap.CancelableCallback>()
        val target = vn.vietmap.vietmapsdk.geometry.LatLng(1.0, 2.0)
        val expected = vn.vietmap.vietmapsdk.camera.CameraPosition.Builder().target(target).build()
        val update = vn.vietmap.vietmapsdk.camera.CameraUpdateFactory.newCameraPosition(expected)
        VietmapMap.moveCamera(update, callback)
        verify { transform.moveCamera(VietmapMap, update, callback) }
        verify { developerAnimationListener.onDeveloperAnimationStarted() }
    }

    @Test
    fun testEaseCamera() {
        val callback = mockk<vn.vietmap.vietmapsdk.maps.VietmapMap.CancelableCallback>()
        val target = vn.vietmap.vietmapsdk.geometry.LatLng(1.0, 2.0)
        val expected = vn.vietmap.vietmapsdk.camera.CameraPosition.Builder().target(target).build()
        val update = vn.vietmap.vietmapsdk.camera.CameraUpdateFactory.newCameraPosition(expected)
        VietmapMap.easeCamera(update, callback)
        verify { transform.easeCamera(VietmapMap, update, vn.vietmap.vietmapsdk.constants.VietmapConstants.ANIMATION_DURATION, true, callback) }
        verify { developerAnimationListener.onDeveloperAnimationStarted() }
    }

    @Test
    fun testAnimateCamera() {
        val callback = mockk<vn.vietmap.vietmapsdk.maps.VietmapMap.CancelableCallback>()
        val target = vn.vietmap.vietmapsdk.geometry.LatLng(1.0, 2.0)
        val expected = vn.vietmap.vietmapsdk.camera.CameraPosition.Builder().target(target).build()
        val update = vn.vietmap.vietmapsdk.camera.CameraUpdateFactory.newCameraPosition(expected)
        VietmapMap.animateCamera(update, callback)
        verify { transform.animateCamera(VietmapMap, update, vn.vietmap.vietmapsdk.constants.VietmapConstants.ANIMATION_DURATION, callback) }
        verify { developerAnimationListener.onDeveloperAnimationStarted() }
    }

    @Test
    fun testScrollBy() {
        VietmapMap.scrollBy(100f, 200f)
        verify { nativeMapView.moveBy(100.0, 200.0, 0) }
        verify { developerAnimationListener.onDeveloperAnimationStarted() }
    }

    @Test
    fun testResetNorth() {
        VietmapMap.resetNorth()
        verify { transform.resetNorth() }
        verify { developerAnimationListener.onDeveloperAnimationStarted() }
    }

    @Test
    fun testFocalBearing() {
        VietmapMap.setFocalBearing(35.0, 100f, 200f, 1000)
        verify { transform.setBearing(35.0, 100f, 200f, 1000) }
        verify { developerAnimationListener.onDeveloperAnimationStarted() }
    }

    @Test
    fun testMinZoom() {
        VietmapMap.setMinZoomPreference(10.0)
        verify { transform.minZoom = 10.0 }
    }

    @Test
    fun testMaxZoom() {
        VietmapMap.setMaxZoomPreference(10.0)
        verify { transform.maxZoom = 10.0 }
    }

    @Test
    fun testMinPitch() {
        VietmapMap.setMinPitchPreference(10.0)
        verify { transform.minPitch = 10.0 }
    }

    @Test
    fun testMaxPitch() {
        VietmapMap.setMaxPitchPreference(10.0)
        verify { transform.maxPitch = 10.0 }
    }

    @Test
    fun testFpsListener() {
        val fpsChangedListener = mockk<vn.vietmap.vietmapsdk.maps.VietmapMap.OnFpsChangedListener>()
        VietmapMap.onFpsChangedListener = fpsChangedListener
        assertEquals("Listener should match", fpsChangedListener, VietmapMap.onFpsChangedListener)
    }

    @Test
    fun testTilePrefetch() {
        VietmapMap.prefetchesTiles = true
        verify { nativeMapView.prefetchTiles = true }
    }

    @Test
    fun testGetPrefetchZoomDelta() {
        every { nativeMapView.prefetchZoomDelta } answers { 3 }
        assertEquals(3, VietmapMap.prefetchZoomDelta)
    }

    @Test
    fun testSetPrefetchZoomDelta() {
        VietmapMap.prefetchZoomDelta = 2
        verify { nativeMapView.prefetchZoomDelta = 2 }
    }

    @Test
    fun testCameraForLatLngBounds() {
        val bounds = vn.vietmap.vietmapsdk.geometry.LatLngBounds.Builder().include(vn.vietmap.vietmapsdk.geometry.LatLng()).include(
            vn.vietmap.vietmapsdk.geometry.LatLng(1.0, 1.0)
        ).build()
        VietmapMap.setLatLngBoundsForCameraTarget(bounds)
        verify { nativeMapView.setLatLngBounds(bounds) }
    }

    @Test(expected = IllegalArgumentException::class)
    fun testAnimateCameraChecksDurationPositive() {
        VietmapMap.animateCamera(
            vn.vietmap.vietmapsdk.camera.CameraUpdateFactory.newLatLng(
                vn.vietmap.vietmapsdk.geometry.LatLng(
                    30.0,
                    30.0
                )
            ), 0, null)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testEaseCameraChecksDurationPositive() {
        VietmapMap.easeCamera(
            vn.vietmap.vietmapsdk.camera.CameraUpdateFactory.newLatLng(
                vn.vietmap.vietmapsdk.geometry.LatLng(
                    30.0,
                    30.0
                )
            ), 0, null)
    }

    @Test
    fun testGetNativeMapPtr() {
        assertEquals(5, VietmapMap.nativeMapPtr)
    }

    @Test
    fun testNativeMapIsNotCalledOnStateSave() {
        clearMocks(nativeMapView)
        VietmapMap.onSaveInstanceState(mockk(relaxed = true))
        verify { nativeMapView wasNot Called }
    }

    @Test
    fun testCameraChangeDispatcherCleared() {
        VietmapMap.onDestroy()
        verify { cameraChangeDispatcher.onDestroy() }
    }

    @Test
    fun testStyleClearedOnDestroy() {
        val style = mockk<vn.vietmap.vietmapsdk.maps.Style>(relaxed = true)
        val builder = mockk<vn.vietmap.vietmapsdk.maps.Style.Builder>(relaxed = true)
        every { builder.build(nativeMapView) } returns style
        VietmapMap.setStyle(builder)

        VietmapMap.onDestroy()
        verify(exactly = 1) { style.clear() }
    }

    @Test
    fun testStyleCallbackNotCalledWhenPreviousFailed() {
        val style = mockk<vn.vietmap.vietmapsdk.maps.Style>(relaxed = true)
        val builder = mockk<vn.vietmap.vietmapsdk.maps.Style.Builder>(relaxed = true)
        every { builder.build(nativeMapView) } returns style
        val onStyleLoadedListener = mockk<vn.vietmap.vietmapsdk.maps.Style.OnStyleLoaded>(relaxed = true)

        VietmapMap.setStyle(builder, onStyleLoadedListener)
        VietmapMap.onFailLoadingStyle()
        VietmapMap.setStyle(builder, onStyleLoadedListener)
        VietmapMap.onFinishLoadingStyle()
        verify(exactly = 1) { onStyleLoadedListener.onStyleLoaded(style) }
    }

    @Test
    fun testStyleCallbackNotCalledWhenPreviousNotFinished() {
        // regression test for #14337
        val style = mockk<vn.vietmap.vietmapsdk.maps.Style>(relaxed = true)
        val builder = mockk<vn.vietmap.vietmapsdk.maps.Style.Builder>(relaxed = true)
        every { builder.build(nativeMapView) } returns style
        val onStyleLoadedListener = mockk<vn.vietmap.vietmapsdk.maps.Style.OnStyleLoaded>(relaxed = true)

        VietmapMap.setStyle(builder, onStyleLoadedListener)
        VietmapMap.setStyle(builder, onStyleLoadedListener)
        VietmapMap.onFinishLoadingStyle()
        verify(exactly = 1) { onStyleLoadedListener.onStyleLoaded(style) }
    }
}
