package vn.vietmap.vietmapsdk.testapp.activity.style

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import vn.vietmap.vietmapsdk.camera.CameraUpdateFactory
import vn.vietmap.vietmapsdk.geometry.LatLng
import vn.vietmap.vietmapsdk.maps.Style
import vn.vietmap.vietmapsdk.style.layers.PropertyFactory.iconImage
import vn.vietmap.vietmapsdk.style.layers.SymbolLayer
import vn.vietmap.vietmapsdk.style.sources.GeoJsonSource
import vn.vietmap.vietmapsdk.testapp.R
import kotlinx.android.synthetic.main.activity_map_simple.*
import java.net.URI

/**
 * Activity showcasing how to load symbols on a map without a Style URI or Style JSON.
 */
class NoStyleActivity : AppCompatActivity() {

    private val imageIcon: Drawable by lazy {
        ResourcesCompat.getDrawable(resources, R.drawable.ic_add_white, theme)!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_simple)
        mapView.getMapAsync { map ->
            map.moveCamera(vn.vietmap.vietmapsdk.camera.CameraUpdateFactory.newLatLngZoom(cameraTarget, cameraZoom))
            map.setStyle(
                vn.vietmap.vietmapsdk.maps.Style.Builder()
                    .withImage(imageId, imageIcon)
                    .withSource(
                        vn.vietmap.vietmapsdk.style.sources.GeoJsonSource(
                            sourceId,
                            URI("asset://points-sf.geojson")
                        )
                    )
                    .withLayer(
                        vn.vietmap.vietmapsdk.style.layers.SymbolLayer(
                            layerId,
                            sourceId
                        ).withProperties(iconImage(imageId)))
            )
        }
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

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState?.let {
            mapView.onSaveInstanceState(it)
        }
    }

    companion object {
        const val layerId = "custom-layer-id"
        const val sourceId = "custom-source-id"
        const val imageId = "image-id"
        const val cameraZoom = 10.0
        val cameraTarget =
            vn.vietmap.vietmapsdk.geometry.LatLng(37.758912, -122.442578)
    }
}
