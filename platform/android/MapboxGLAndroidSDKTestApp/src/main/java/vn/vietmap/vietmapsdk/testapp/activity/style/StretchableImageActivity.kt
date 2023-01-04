package vn.vietmap.vietmapsdk.testapp.activity.style

import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
// import com.mapbox.vietmapsdk.maps.* //
import vn.vietmap.vietmapsdk.style.expressions.Expression

import vn.vietmap.vietmapsdk.style.sources.GeoJsonSource
import vn.vietmap.vietmapsdk.testapp.R
import vn.vietmap.vietmapsdk.testapp.utils.GeoParseUtil
import vn.vietmap.vietmapsdk.utils.BitmapUtils
import timber.log.Timber
import java.io.IOException
import java.lang.ref.WeakReference
import java.util.ArrayList

/**
 * Test stretchable image as a background for text..
 */
class StretchableImageActivity : AppCompatActivity(),
    vn.vietmap.vietmapsdk.maps.OnMapReadyCallback {
    private var mapboxMap: vn.vietmap.vietmapsdk.maps.VietmapMap? = null
    private lateinit var mapView: vn.vietmap.vietmapsdk.maps.MapView
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stretchable_image)
        mapView = findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
    }

    override fun onMapReady(mapboxMap: vn.vietmap.vietmapsdk.maps.VietmapMap) {
        this.mapboxMap = mapboxMap
        mapboxMap.setStyle(vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Streets")) { style: vn.vietmap.vietmapsdk.maps.Style ->
            val popup = vn.vietmap.vietmapsdk.utils.BitmapUtils.getBitmapFromDrawable(
                resources.getDrawable(R.drawable.popup)
            )
            val popupDebug =
                vn.vietmap.vietmapsdk.utils.BitmapUtils.getBitmapFromDrawable(resources.getDrawable(R.drawable.popup_debug))

            // The two (blue) columns of pixels that can be stretched horizontally:
            //   - the pixels between x: 25 and x: 55 can be stretched
            //   - the pixels between x: 85 and x: 115 can be stretched.
            val stretchX: MutableList<vn.vietmap.vietmapsdk.maps.ImageStretches> = ArrayList()
            stretchX.add(vn.vietmap.vietmapsdk.maps.ImageStretches(25F, 55F))
            stretchX.add(vn.vietmap.vietmapsdk.maps.ImageStretches(85F, 115F))

            // The one (red) row of pixels that can be stretched vertically:
            //   - the pixels between y: 25 and y: 100 can be stretched
            val stretchY: MutableList<vn.vietmap.vietmapsdk.maps.ImageStretches> = ArrayList()
            stretchY.add(vn.vietmap.vietmapsdk.maps.ImageStretches(25F, 100F))

            // This part of the image that can contain text ([x1, y1, x2, y2]):
            val content =
                vn.vietmap.vietmapsdk.maps.ImageContent(25F, 25F, 115F, 100F)
            style.addImage(NAME_POPUP, popup!!, stretchX, stretchY, content)
            style.addImage(NAME_POPUP_DEBUG, popupDebug!!, stretchX, stretchY, content)
            LoadFeatureTask(this@StretchableImageActivity).execute()
        }
    }

    private fun onFeatureLoaded(json: String?) {
        if (json == null) {
            Timber.e("json is null.")
            return
        }
        val style = mapboxMap!!.style
        if (style != null) {
            val featureCollection = FeatureCollection.fromJson(json)
            val stretchSource =
                vn.vietmap.vietmapsdk.style.sources.GeoJsonSource(
                    STRETCH_SOURCE,
                    featureCollection
                )
            val stretchLayer = vn.vietmap.vietmapsdk.style.layers.SymbolLayer(
                STRETCH_LAYER,
                STRETCH_SOURCE
            )
                .withProperties(
                    vn.vietmap.vietmapsdk.style.layers.PropertyFactory.textField(
                        vn.vietmap.vietmapsdk.style.expressions.Expression.get("name")),
                    vn.vietmap.vietmapsdk.style.layers.PropertyFactory.iconImage(
                        vn.vietmap.vietmapsdk.style.expressions.Expression.get("image-name")),
                    vn.vietmap.vietmapsdk.style.layers.PropertyFactory.iconAllowOverlap(true),
                    vn.vietmap.vietmapsdk.style.layers.PropertyFactory.textAllowOverlap(true),
                    vn.vietmap.vietmapsdk.style.layers.PropertyFactory.iconTextFit(
                        vn.vietmap.vietmapsdk.style.layers.Property.ICON_TEXT_FIT_BOTH)
                )

            // the original, unstretched image for comparison
            val point = Point.fromLngLat(-70.0, 0.0)
            val feature = Feature.fromGeometry(point)
            val originalCollection = FeatureCollection.fromFeature(feature)
            val originalSource =
                vn.vietmap.vietmapsdk.style.sources.GeoJsonSource(
                    ORIGINAL_SOURCE,
                    originalCollection
                )
            val originalLayer = vn.vietmap.vietmapsdk.style.layers.SymbolLayer(
                ORIGINAL_LAYER,
                ORIGINAL_SOURCE
            )
            style.addSource(stretchSource)
            style.addSource(originalSource)
            style.addLayer(stretchLayer)
            style.addLayer(originalLayer)
        }
    }

    private class LoadFeatureTask(activity: StretchableImageActivity) :
        AsyncTask<Void?, Int?, String?>() {
        private val activity: WeakReference<StretchableImageActivity>
        protected override fun doInBackground(vararg p0: Void?): String? {
            val activity = activity.get()
            if (activity != null) {
                var json: String? = null
                try {
                    json = vn.vietmap.vietmapsdk.testapp.utils.GeoParseUtil.loadStringFromAssets(
                        activity.applicationContext,
                        "stretchable_image.geojson"
                    )
                } catch (exception: IOException) {
                    Timber.e(exception, "Could not read feature")
                }
                return json
            }
            return null
        }

        override fun onPostExecute(json: String?) {
            super.onPostExecute(json)
            val activity = activity.get()
            activity?.onFeatureLoaded(json)
        }

        init {
            this.activity = WeakReference(activity)
        }
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

    public override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView!!.onSaveInstanceState(outState)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView!!.onLowMemory()
    }

    public override fun onDestroy() {
        super.onDestroy()
        mapView!!.onDestroy()
    }

    companion object {
        private const val NAME_POPUP = "popup"
        private const val NAME_POPUP_DEBUG = "popup-debug"
        private const val STRETCH_SOURCE = "STRETCH_SOURCE"
        private const val STRETCH_LAYER = "STRETCH_LAYER"
        private const val ORIGINAL_SOURCE = "ORIGINAL_SOURCE"
        private const val ORIGINAL_LAYER = "ORIGINAL_LAYER"
    }
}
