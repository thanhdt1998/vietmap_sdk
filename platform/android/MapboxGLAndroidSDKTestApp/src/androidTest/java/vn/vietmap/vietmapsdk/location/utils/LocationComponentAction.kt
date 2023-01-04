package vn.vietmap.vietmapsdk.location.utils

import android.content.Context
import android.view.View
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import vn.vietmap.vietmapsdk.location.LocationComponent
import vn.vietmap.vietmapsdk.maps.MapboxMap
import vn.vietmap.vietmapsdk.maps.Style
import org.hamcrest.Matcher

class LocationComponentAction(
    private val mapboxMap: vn.vietmap.vietmapsdk.maps.MapboxMap,
    private val onPerformLocationComponentAction: OnPerformLocationComponentAction
) : ViewAction {

    override fun getConstraints(): Matcher<View> {
        return isDisplayed()
    }

    override fun getDescription(): String {
        return javaClass.simpleName
    }

    override fun perform(uiController: UiController, view: View) {
        onPerformLocationComponentAction.onLocationComponentAction(
            mapboxMap.locationComponent,
            mapboxMap,
            mapboxMap.style!!,
            uiController,
            view.context
        )
    }

    interface OnPerformLocationComponentAction {
        fun onLocationComponentAction(component: vn.vietmap.vietmapsdk.location.LocationComponent, mapboxMap: vn.vietmap.vietmapsdk.maps.MapboxMap, style: vn.vietmap.vietmapsdk.maps.Style, uiController: UiController, context: Context)
    }
}
