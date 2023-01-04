package vn.vietmap.vietmapsdk.testapp.activity.snapshot

import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import vn.vietmap.vietmapsdk.camera.CameraPosition
import vn.vietmap.vietmapsdk.geometry.LatLng
import vn.vietmap.vietmapsdk.maps.Style
import vn.vietmap.vietmapsdk.snapshotter.MapSnapshot
import vn.vietmap.vietmapsdk.snapshotter.MapSnapshotter
import vn.vietmap.vietmapsdk.testapp.R
import vn.vietmap.vietmapsdk.testapp.utils.ResourceUtils
import timber.log.Timber
import java.io.IOException
import java.lang.RuntimeException

/**
 * Test activity showing how to use a the MapSnapshotter with a local style
 */
class MapSnapshotterLocalStyleActivity : AppCompatActivity(), vn.vietmap.vietmapsdk.snapshotter.MapSnapshotter.SnapshotReadyCallback {
    private var mapSnapshotter: vn.vietmap.vietmapsdk.snapshotter.MapSnapshotter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_snapshotter_marker)
        val container = findViewById<View>(R.id.container)
        container.viewTreeObserver
            .addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    container.viewTreeObserver.removeGlobalOnLayoutListener(this)
                    val styleJson: String
                    styleJson = try {
                        ResourceUtils.readRawResource(
                            this@MapSnapshotterLocalStyleActivity,
                            R.raw.sat_style
                        )
                    } catch (exception: IOException) {
                        throw RuntimeException(exception)
                    }
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
                                        .fromJson(styleJson)
                                )
                                .withCameraPosition(
                                    vn.vietmap.vietmapsdk.camera.CameraPosition.Builder()
                                        .target(LatLng(52.090737, 5.121420))
                                        .zoom(18.0).build()
                                )
                        )
                    mapSnapshotter!!.start(
                        this@MapSnapshotterLocalStyleActivity,
                        vn.vietmap.vietmapsdk.snapshotter.MapSnapshotter.ErrorHandler { error: String? -> Timber.e(error) }
                    )
                }
            })
    }

    override fun onStop() {
        super.onStop()
        mapSnapshotter!!.cancel()
    }

    override fun onSnapshotReady(snapshot: vn.vietmap.vietmapsdk.snapshotter.MapSnapshot) {
        Timber.i("Snapshot ready")
        val imageView = findViewById<View>(R.id.snapshot_image) as ImageView
        imageView.setImageBitmap(snapshot.bitmap)
    }
}
