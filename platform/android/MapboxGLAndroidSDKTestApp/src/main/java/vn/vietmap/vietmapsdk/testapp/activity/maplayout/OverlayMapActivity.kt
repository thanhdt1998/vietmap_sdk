package vn.vietmap.vietmapsdk.testapp.activity.maplayout

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import vn.vietmap.vietmapsdk.maps.Style
import vn.vietmap.vietmapsdk.testapp.R
import kotlinx.android.synthetic.main.activity_overlay.*

/**
 * Test overlaying a Map with a View that uses a radial gradient shader.
 */
class OverlayMapActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_overlay)
        mapView.onCreate(savedInstanceState)
        parentView.addView(OverlayView(this))
        mapView.getMapAsync {
            it.setStyle(vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Streets"))
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

//    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
//        super.onSaveInstanceState(outState, outPersistentState)
//        outState?.let {
//            mapView.onSaveInstanceState(it)
//        }
//    }

    class OverlayView(context: Context) : View(context) {

        private lateinit var paint: Paint

        @SuppressLint("DrawAllocation") // only happens once
        override fun onDraw(canvas: Canvas?) {
            super.onDraw(canvas)

            canvas?.let {
                if (!::paint.isInitialized) {
                    paint = Paint()
                    paint.color = Color.BLACK
                    paint.strokeWidth = 1.0f
                    paint.style = Paint.Style.FILL_AND_STROKE
                    paint.shader = RadialGradient(
                        width / 2.0f,
                        height / 2.0f,
                        height / 3.0f,
                        Color.TRANSPARENT,
                        Color.BLACK,
                        Shader.TileMode.CLAMP
                    )
                }
                it.drawRect(0.0f, 0.0f, width.toFloat(), height.toFloat(), paint)
            }
        }
    }
}
