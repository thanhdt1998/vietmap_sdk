package vn.vietmap.vietmapsdk.testapp.activity.location

import android.location.Location
import vn.vietmap.vietmapsdk.geometry.LatLngBounds
import vn.vietmap.vietmapsdk.maps.Style
import timber.log.Timber
import java.util.*

/**
 * Useful utilities used throughout the test app.
 */
object Utils {
    private val STYLES = arrayOf(
        vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Streets"),
        vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Outdoor"),
        vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Bright"),
        vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Pastel"),
        vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Satellite Hybrid")
    )
    private var index = 0

    /**
     * Utility to cycle through map styles. Useful to test if runtime styling source and layers transfer over to new
     * style.
     *
     * @return a string ID representing the map style
     */
    fun nextStyle(): String {
        index++
        if (index == STYLES.size) {
            index = 0
        }
        return STYLES[index]
    }

    /**
     * Utility for getting a random coordinate inside a provided bounds and creates a [Location] from it.
     *
     * @param bounds bounds of the generated location
     * @return a [Location] object using the random coordinate
     */
    fun getRandomLocation(bounds: vn.vietmap.vietmapsdk.geometry.LatLngBounds): Location {
        val random = Random()
        val randomLat = bounds.latSouth + (bounds.latNorth - bounds.latSouth) * random.nextDouble()
        val randomLon = bounds.lonWest + (bounds.lonEast - bounds.lonWest) * random.nextDouble()
        val location = Location("random-loc")
        location.longitude = randomLon
        location.latitude = randomLat
        Timber.d("getRandomLatLng: %s", location.toString())
        return location
    }
}
