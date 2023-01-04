package vn.vietmap.vietmapsdk.testapp.activity.snapshot

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.PointF
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.ImageView
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import vn.vietmap.vietmapsdk.camera.CameraPosition
import vn.vietmap.vietmapsdk.geometry.LatLng
import vn.vietmap.vietmapsdk.maps.Style
import vn.vietmap.vietmapsdk.snapshotter.MapSnapshot
import vn.vietmap.vietmapsdk.snapshotter.MapSnapshotter
import vn.vietmap.vietmapsdk.testapp.R
import timber.log.Timber

/**
 * Test activity showing how to use a the [MapSnapshotter] and overlay
 * [android.graphics.Bitmap]s on top.
 */
class MapSnapshotterBitMapOverlayActivity :
    AppCompatActivity(),
    vn.vietmap.vietmapsdk.snapshotter.MapSnapshotter.SnapshotReadyCallback {
    private var mapSnapshotter: vn.vietmap.vietmapsdk.snapshotter.MapSnapshotter? = null

    @get:VisibleForTesting
    var mapSnapshot: vn.vietmap.vietmapsdk.snapshotter.MapSnapshot? = null
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_snapshotter_marker)
        val container = findViewById<View>(R.id.container)
        container.viewTreeObserver
            .addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    container.viewTreeObserver.removeGlobalOnLayoutListener(this)
                    Timber.i("Starting snapshot")
                    mapSnapshotter =
                        vn.vietmap.vietmapsdk.snapshotter.MapSnapshotter(
                            applicationContext,
                            vn.vietmap.vietmapsdk.snapshotter.MapSnapshotter.Options(
                                Math.min(container.measuredWidth, 1024),
                                Math.min(container.measuredHeight, 1024)
                            )
                                .withStyleBuilder(
                                    vn.vietmap.vietmapsdk.maps.Style.Builder()
                                        .fromUri(
                                            vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle(
                                                "Outdoor"
                                            )
                                        )
                                )
                                .withCameraPosition(
                                    vn.vietmap.vietmapsdk.camera.CameraPosition.Builder()
                                        .target(LatLng(52.090737, 5.121420))
                                        .zoom(15.0).build()
                                )
                        )
                    mapSnapshotter!!.start(this@MapSnapshotterBitMapOverlayActivity)
                }
            })
    }

    override fun onStop() {
        super.onStop()
        mapSnapshotter!!.cancel()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onSnapshotReady(snapshot: vn.vietmap.vietmapsdk.snapshotter.MapSnapshot) {
        mapSnapshot = snapshot
        Timber.i("Snapshot ready")
        val imageView = findViewById<View>(R.id.snapshot_image) as ImageView
        val image = addMarker(snapshot)
        imageView.setImageBitmap(image)
        imageView.setOnTouchListener { v: View?, event: MotionEvent ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                val latLng = snapshot.latLngForPixel(PointF(event.x, event.y))
                Timber.e("Clicked LatLng is %s", latLng)
                return@setOnTouchListener true
            }
            false
        }
    }

    private fun addMarker(snapshot: vn.vietmap.vietmapsdk.snapshotter.MapSnapshot): Bitmap {
        val canvas = Canvas(snapshot.bitmap)
        val marker =
            BitmapFactory.decodeResource(resources, R.drawable.mapbox_marker_icon_default, null)
        // Dom toren
        val markerLocation = snapshot.pixelForLatLng(
            vn.vietmap.vietmapsdk.geometry.LatLng(
                52.090649433011315,
                5.121310651302338
            )
        )
        canvas.drawBitmap(
            marker, /* Subtract half of the width so we center the bitmap correctly */
            markerLocation.x - marker.width / 2, /* Subtract half of the height so we align the bitmap bottom correctly */
            markerLocation.y - marker.height / 2,
            null
        )
        return snapshot.bitmap
    }
}
