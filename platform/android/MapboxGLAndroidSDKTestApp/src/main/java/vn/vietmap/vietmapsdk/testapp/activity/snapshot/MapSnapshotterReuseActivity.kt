package vn.vietmap.vietmapsdk.testapp.activity.snapshot

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import vn.vietmap.vietmapsdk.camera.CameraPosition
import vn.vietmap.vietmapsdk.geometry.LatLng
import vn.vietmap.vietmapsdk.geometry.LatLngBounds
import vn.vietmap.vietmapsdk.maps.Style
import vn.vietmap.vietmapsdk.snapshotter.MapSnapshot
import vn.vietmap.vietmapsdk.snapshotter.MapSnapshotter
import vn.vietmap.vietmapsdk.testapp.R
import java.util.*

/**
 * Test activity showing how to use a the [MapSnapshotter]
 */
class MapSnapshotterReuseActivity : AppCompatActivity(), vn.vietmap.vietmapsdk.snapshotter.MapSnapshotter.SnapshotReadyCallback {
    private var mapSnapshotter: vn.vietmap.vietmapsdk.snapshotter.MapSnapshotter? = null
    private lateinit var fab: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_snapshotter_reuse)
        fab = findViewById(R.id.fab)
        fab.setVisibility(View.INVISIBLE)
        fab.setOnClickListener(
            View.OnClickListener { v: View? ->
                fab.setVisibility(View.INVISIBLE)
                mapSnapshotter!!.setStyleUrl(randomStyle)
                if (random.nextInt(2) == 0) {
                    mapSnapshotter!!.setCameraPosition(randomCameraPosition)
                } else {
                    mapSnapshotter!!.setRegion(randomBounds)
                }
                if (random.nextInt(2) == 0) {
                    mapSnapshotter!!.setSize(512, 512)
                } else {
                    mapSnapshotter!!.setSize(256, 256)
                }
                mapSnapshotter!!.start(this@MapSnapshotterReuseActivity)
            }
        )
        mapSnapshotter = vn.vietmap.vietmapsdk.snapshotter.MapSnapshotter(
            applicationContext,
            vn.vietmap.vietmapsdk.snapshotter.MapSnapshotter.Options(512, 512)
                .withStyleBuilder(
                    vn.vietmap.vietmapsdk.maps.Style.Builder().fromUri(
                        randomStyle
                    )
                )
        )
        mapSnapshotter!!.start(this@MapSnapshotterReuseActivity)
    }

    override fun onSnapshotReady(snapshot: vn.vietmap.vietmapsdk.snapshotter.MapSnapshot) {
        fab!!.visibility = View.VISIBLE
        val imageView = findViewById<ImageView>(R.id.snapshot_image)
        imageView.setImageBitmap(snapshot.bitmap)
    }

    private val randomBounds: vn.vietmap.vietmapsdk.geometry.LatLngBounds
        private get() = vn.vietmap.vietmapsdk.geometry.LatLngBounds.Builder()
            .include(
                vn.vietmap.vietmapsdk.geometry.LatLng(
                    randomInRange(5f, 10f).toDouble(),
                    randomInRange(-5f, 5f)
                        .toDouble()
                )
            )
            .include(
                vn.vietmap.vietmapsdk.geometry.LatLng(
                    randomInRange(-5f, 5f).toDouble(),
                    randomInRange(5f, 10f)
                        .toDouble()
                )
            )
            .build()
    private val randomCameraPosition: vn.vietmap.vietmapsdk.camera.CameraPosition
        private get() = vn.vietmap.vietmapsdk.camera.CameraPosition.Builder()
            .target(
                vn.vietmap.vietmapsdk.geometry.LatLng(
                    randomInRange(-80f, 80f).toDouble(),
                    randomInRange(-160f, 160f)
                        .toDouble()
                )
            )
            .zoom(randomInRange(2f, 10f).toDouble())
            .bearing(randomInRange(0f, 90f).toDouble())
            .build()
    val randomStyle: String
        get() = when (random.nextInt(5)) {
            0 -> vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Pastel")
            1 -> vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Bright")
            2 -> vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Streets")
            3 -> vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Outdoor")
            4 -> vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Satellite Hybrid")
            else -> vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Streets")
        }

    companion object {
        private val random = Random()
        fun randomInRange(min: Float, max: Float): Float {
            return random.nextFloat() * (max - min) + min
        }
    }
}
