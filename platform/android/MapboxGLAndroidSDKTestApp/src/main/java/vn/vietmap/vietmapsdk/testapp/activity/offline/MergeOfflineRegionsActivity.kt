package vn.vietmap.vietmapsdk.testapp.activity.offline

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import vn.vietmap.vietmapsdk.Vietmap
import vn.vietmap.vietmapsdk.log.Logger
import vn.vietmap.vietmapsdk.maps.Style
import vn.vietmap.vietmapsdk.offline.OfflineManager
import vn.vietmap.vietmapsdk.offline.OfflineRegion
import vn.vietmap.vietmapsdk.storage.FileSource
import vn.vietmap.vietmapsdk.testapp.R
import vn.vietmap.vietmapsdk.testapp.utils.FileUtils
import kotlinx.android.synthetic.main.activity_merge_offline_regions.*

class MergeOfflineRegionsActivity : AppCompatActivity() {
    companion object {
        private const val LOG_TAG = "Mbgl-MergeOfflineRegionsActivity"
        private const val TEST_DB_FILE_NAME = "offline_test.db"
        private var TEST_STYLE = vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Satellite Hybrid")
    }

    private val onFileCopiedListener = object : FileUtils.OnFileCopiedFromAssetsListener {
        override fun onFileCopiedFromAssets() {
            Toast.makeText(
                this@MergeOfflineRegionsActivity,
                String.format("OnFileCopied."),
                Toast.LENGTH_LONG
            ).show()
            mergeDb()
        }

        override fun onError() {
            Toast.makeText(
                this@MergeOfflineRegionsActivity,
                String.format("Error copying DB file."),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private val onRegionMergedListener = object : vn.vietmap.vietmapsdk.offline.OfflineManager.MergeOfflineRegionsCallback {
        override fun onMerge(offlineRegions: Array<vn.vietmap.vietmapsdk.offline.OfflineRegion>) {
            mapView.getMapAsync {
                it.setStyle(vn.vietmap.vietmapsdk.maps.Style.Builder().fromUri(TEST_STYLE))
            }
            Toast.makeText(
                this@MergeOfflineRegionsActivity,
                String.format("Merged %d regions.", offlineRegions.size),
                Toast.LENGTH_LONG
            ).show()
        }

        override fun onError(error: String) {
            Toast.makeText(
                this@MergeOfflineRegionsActivity,
                String.format("Offline DB merge error."),
                Toast.LENGTH_LONG
            ).show()
            vn.vietmap.vietmapsdk.log.Logger.e(LOG_TAG, error)
        }
    }

    /**
     * Since we expect from the results of the offline merge callback to interact with the hosting activity,
     * we need to ensure that we are not interacting with a destroyed activity.
     */
    private class MergeCallback(private var activityCallback: vn.vietmap.vietmapsdk.offline.OfflineManager.MergeOfflineRegionsCallback?) : vn.vietmap.vietmapsdk.offline.OfflineManager.MergeOfflineRegionsCallback {

        override fun onMerge(offlineRegions: Array<out vn.vietmap.vietmapsdk.offline.OfflineRegion>?) {
            activityCallback?.onMerge(offlineRegions)
        }

        override fun onError(error: String?) {
            activityCallback?.onError(error)
        }

        fun onActivityDestroy() {
            activityCallback = null
        }
    }

    private val mergeCallback = MergeCallback(onRegionMergedListener)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_merge_offline_regions)

        // forcing offline state
        vn.vietmap.vietmapsdk.Vietmap.setConnected(false)

        mapView.onCreate(savedInstanceState)
        load_region_btn.setOnClickListener {
            copyAsset()
        }
        mapView.getMapAsync {
            it.isDebugActive = true
            it.setStyle(vn.vietmap.vietmapsdk.maps.Style.Builder().fromUri(TEST_STYLE))
        }
    }

    private fun copyAsset() {
        // copy db asset to internal memory
        FileUtils.CopyFileFromAssetsTask(this, onFileCopiedListener).execute(TEST_DB_FILE_NAME, vn.vietmap.vietmapsdk.storage.FileSource.getResourcesCachePath(this))
    }

    private fun mergeDb() {
        vn.vietmap.vietmapsdk.offline.OfflineManager.getInstance(this).mergeOfflineRegions(
            vn.vietmap.vietmapsdk.storage.FileSource.getResourcesCachePath(this) + "/" + TEST_DB_FILE_NAME,
            mergeCallback
        )
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
        mergeCallback.onActivityDestroy()
        mapView.onDestroy()

        // restoring connectivity state
        vn.vietmap.vietmapsdk.Vietmap.setConnected(null)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }
}
