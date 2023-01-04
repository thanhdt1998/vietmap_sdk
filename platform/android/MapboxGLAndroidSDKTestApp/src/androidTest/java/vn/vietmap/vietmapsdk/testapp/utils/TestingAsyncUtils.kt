package vn.vietmap.vietmapsdk.testapp.utils

import androidx.test.espresso.UiController
import vn.vietmap.vietmapsdk.maps.MapView

object TestingAsyncUtils {
    private const val DEFAULT_TIMEOUT = 15_000L

    fun waitForLayer(uiController: UiController, mapView: vn.vietmap.vietmapsdk.maps.MapView) {
        val start = System.nanoTime() / 1E6
        var isIdle = false
        mapView.addOnDidBecomeIdleListener { isIdle = true }

        while (!isIdle && System.nanoTime() / 1E6 - start < DEFAULT_TIMEOUT) {
            uiController.loopMainThreadForAtLeast(100)
        }
    }
}
