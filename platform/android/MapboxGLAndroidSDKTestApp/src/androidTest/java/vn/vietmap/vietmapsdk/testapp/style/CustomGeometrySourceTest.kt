package vn.vietmap.vietmapsdk.testapp.style

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import vn.vietmap.vietmapsdk.style.sources.CustomGeometrySource.THREAD_POOL_LIMIT
import vn.vietmap.vietmapsdk.style.sources.CustomGeometrySource.THREAD_PREFIX
import vn.vietmap.vietmapsdk.testapp.action.MapboxMapAction.invoke
import vn.vietmap.vietmapsdk.testapp.action.OrientationAction.orientationLandscape
import vn.vietmap.vietmapsdk.testapp.action.OrientationAction.orientationPortrait
import vn.vietmap.vietmapsdk.testapp.action.WaitAction
import vn.vietmap.vietmapsdk.testapp.activity.BaseTest
import com.mapbox.vietmapsdk.testapp.activity.style.GridSourceActivity
import com.mapbox.vietmapsdk.testapp.activity.style.GridSourceActivity.ID_GRID_LAYER
import com.mapbox.vietmapsdk.testapp.activity.style.GridSourceActivity.ID_GRID_SOURCE
import com.mapbox.vietmapsdk.testapp.utils.TestingAsyncUtils
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test

class CustomGeometrySourceTest : vn.vietmap.vietmapsdk.testapp.activity.BaseTest() {

    override fun getActivityClass(): Class<*> = GridSourceActivity::class.java

    @Test
    fun sourceNotLeakingThreadsTest() {
        validateTestSetup()
        vn.vietmap.vietmapsdk.testapp.action.WaitAction.invoke(4000)
        onView(isRoot()).perform(orientationLandscape())
        vn.vietmap.vietmapsdk.testapp.action.WaitAction.invoke(2000)
        onView(isRoot()).perform(orientationPortrait())
        vn.vietmap.vietmapsdk.testapp.action.WaitAction.invoke(2000)
        Assert.assertFalse(
            "Threads should be shutdown when the source is destroyed.",
            Thread.getAllStackTraces().keys.filter {
                it.name.startsWith(THREAD_PREFIX)
            }.count() > THREAD_POOL_LIMIT
        )
    }

    @Test
    fun threadsShutdownWhenSourceRemovedTest() {
        validateTestSetup()
        invoke(mapboxMap) { uiController, mapboxMap ->
            mapboxMap.style!!.removeLayer(ID_GRID_LAYER)
            TestingAsyncUtils.waitForLayer(uiController, mapView)
            mapboxMap.style!!.removeSource(ID_GRID_SOURCE)
            TestingAsyncUtils.waitForLayer(uiController, mapView)
            Assert.assertTrue(
                "There should be no threads running when the source is removed.",
                Thread.getAllStackTraces().keys.filter {
                    it.name.startsWith(THREAD_PREFIX)
                }.count() == 0
            )
        }
    }

    @Test
    fun threadsRestartedWhenSourceReAddedTest() {
        validateTestSetup()
        invoke(mapboxMap) { uiController, mapboxMap ->
            mapboxMap.style!!.removeLayer((rule.activity as GridSourceActivity).layer)
            TestingAsyncUtils.waitForLayer(uiController, mapView)
            mapboxMap.style!!.removeSource(ID_GRID_SOURCE)
            TestingAsyncUtils.waitForLayer(uiController, mapView)
            mapboxMap.style!!.addSource((rule.activity as GridSourceActivity).source)
            mapboxMap.style!!.addLayer((rule.activity as GridSourceActivity).layer)
            TestingAsyncUtils.waitForLayer(uiController, mapView)
            Assert.assertTrue(
                "Threads should be restarted when the source is re-added to the map.",
                Thread.getAllStackTraces().keys.filter {
                    it.name.startsWith(THREAD_PREFIX)
                }.count() == THREAD_POOL_LIMIT
            )
        }
    }

    @Test
    fun sourceZoomDeltaTest() {
        validateTestSetup()
        invoke(mapboxMap) { uiController, mapboxMap ->
            mapboxMap.prefetchZoomDelta = 3
            mapboxMap.style!!.getSource(ID_GRID_SOURCE)!!.let {
                assertNull(it.prefetchZoomDelta)
                it.prefetchZoomDelta = 5
                assertNotNull(it.prefetchZoomDelta)
                assertEquals(5, it.prefetchZoomDelta!!)
                it.prefetchZoomDelta = null
                assertNull(it.prefetchZoomDelta)
            }
        }
    }

    @Test
    fun isVolatileTest() {
        validateTestSetup()
        invoke(mapboxMap) { uiController, mapboxMap ->
            mapboxMap.style!!.getSource(ID_GRID_SOURCE)!!.let {
                assertFalse(it.isVolatile)
                it.isVolatile = true
                assertTrue(it.isVolatile)
            }
        }
    }

    @Test
    fun minimumTileUpdateIntervalTest() {
        validateTestSetup()
        invoke(mapboxMap) { uiController, mapboxMap ->
            mapboxMap.style!!.getSource(ID_GRID_SOURCE)!!.let {
                assertEquals(0, it.minimumTileUpdateInterval)
                it.minimumTileUpdateInterval = 1000
                assertEquals(1000, it.minimumTileUpdateInterval)
            }
        }
    }
}
