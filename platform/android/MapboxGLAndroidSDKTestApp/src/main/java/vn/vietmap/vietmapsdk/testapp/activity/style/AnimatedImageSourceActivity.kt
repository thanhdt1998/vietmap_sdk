package vn.vietmap.vietmapsdk.testapp.activity.style

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import vn.vietmap.vietmapsdk.Vietmap
import vn.vietmap.vietmapsdk.geometry.LatLng
import vn.vietmap.vietmapsdk.geometry.LatLngQuad
import vn.vietmap.vietmapsdk.maps.MapView
import vn.vietmap.vietmapsdk.maps.VietmapMap
import vn.vietmap.vietmapsdk.maps.OnMapReadyCallback
import vn.vietmap.vietmapsdk.maps.Style
import vn.vietmap.vietmapsdk.style.layers.RasterLayer
import vn.vietmap.vietmapsdk.style.sources.ImageSource
import vn.vietmap.vietmapsdk.testapp.R
import vn.vietmap.vietmapsdk.utils.BitmapUtils

/**
 * Test activity showing how to use a series of images to create an animation
 * with an ImageSource
 *
 *
 * GL-native equivalent of https://maplibre.org/maplibre-gl-js-docs/example/animate-images/
 *
 */
class AnimatedImageSourceActivity : AppCompatActivity(),
    vn.vietmap.vietmapsdk.maps.OnMapReadyCallback {
    private lateinit var mapView: vn.vietmap.vietmapsdk.maps.MapView
    private val handler = Handler()
    private var runnable: Runnable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animated_image_source)
        mapView = findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
    }

    override fun onMapReady(map: vn.vietmap.vietmapsdk.maps.VietmapMap) {
        val quad = vn.vietmap.vietmapsdk.geometry.LatLngQuad(
            vn.vietmap.vietmapsdk.geometry.LatLng(46.437, -80.425),
            vn.vietmap.vietmapsdk.geometry.LatLng(46.437, -71.516),
            vn.vietmap.vietmapsdk.geometry.LatLng(37.936, -71.516),
            vn.vietmap.vietmapsdk.geometry.LatLng(37.936, -80.425)
        )
        val imageSource = vn.vietmap.vietmapsdk.style.sources.ImageSource(
            ID_IMAGE_SOURCE,
            quad,
            R.drawable.southeast_radar_0
        )
        val layer = vn.vietmap.vietmapsdk.style.layers.RasterLayer(
            ID_IMAGE_LAYER,
            ID_IMAGE_SOURCE
        )
        map.setStyle(
            vn.vietmap.vietmapsdk.maps.Style.Builder()
                .fromUri(vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Streets"))
                .withSource(imageSource)
                .withLayer(layer)
        ) { style: vn.vietmap.vietmapsdk.maps.Style? ->
            runnable = RefreshImageRunnable(imageSource, handler)
            handler.postDelayed(runnable as RefreshImageRunnable, 100)
        }
    }

    override fun onStart() {
        super.onStart()
        mapView!!.onStart()
    }

    public override fun onResume() {
        super.onResume()
        mapView!!.onResume()
    }

    public override fun onPause() {
        super.onPause()
        mapView!!.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView!!.onStop()
        runnable?.let { handler.removeCallbacks(it) }
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView!!.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView!!.onSaveInstanceState(outState)
    }

    private class RefreshImageRunnable internal constructor(
        private val imageSource: vn.vietmap.vietmapsdk.style.sources.ImageSource,
        private val handler: Handler
    ) : Runnable {
        private val drawables: Array<Bitmap?>
        private var drawableIndex: Int
        fun getBitmap(resourceId: Int): Bitmap? {
            val context = vn.vietmap.vietmapsdk.Vietmap.getApplicationContext()
            val drawable = vn.vietmap.vietmapsdk.utils.BitmapUtils.getDrawableFromRes(context, resourceId)
            if (drawable is BitmapDrawable) {
                return drawable.bitmap
            }
            return null
        }

        override fun run() {
            imageSource.setImage(drawables[drawableIndex++]!!)
            if (drawableIndex > 3) {
                drawableIndex = 0
            }
            handler.postDelayed(this, 1000)
        }

        init {
            drawables = arrayOfNulls(4)
            drawables[0] = getBitmap(R.drawable.southeast_radar_0)
            drawables[1] = getBitmap(R.drawable.southeast_radar_1)
            drawables[2] = getBitmap(R.drawable.southeast_radar_2)
            drawables[3] = getBitmap(R.drawable.southeast_radar_3)
            drawableIndex = 1
        }
    }

    companion object {
        private const val ID_IMAGE_SOURCE = "animated_image_source"
        private const val ID_IMAGE_LAYER = "animated_image_layer"
    }
}
