package vn.vietmap.vietmapsdk.location.utils

import androidx.test.espresso.IdlingResource
import vn.vietmap.vietmapsdk.maps.MapboxMap
import vn.vietmap.vietmapsdk.maps.Style

/**
 * Resource, that's idling until the provided style is loaded.
 * Remember to add any espresso action (like view assertion) after the [waitForStyle] call
 * for the test to keep running.
 */
class StyleChangeIdlingResource : IdlingResource {

    private var callback: IdlingResource.ResourceCallback? = null
    private var isIdle = true

    override fun getName(): String {
        return javaClass.simpleName
    }

    override fun isIdleNow(): Boolean {
        return isIdle
    }

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
        this.callback = callback
    }

    private fun setIdle() {
        isIdle = true
        callback?.onTransitionToIdle()
    }

    fun waitForStyle(mapboxMap: vn.vietmap.vietmapsdk.maps.MapboxMap, styleUrl: String) {
        isIdle = false
        mapboxMap.setStyle(vn.vietmap.vietmapsdk.maps.Style.Builder().fromUri(styleUrl)) {
            setIdle()
        }
    }
}
