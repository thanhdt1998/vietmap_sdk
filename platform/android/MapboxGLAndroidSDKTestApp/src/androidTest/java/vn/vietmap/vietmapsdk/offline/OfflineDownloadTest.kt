package vn.vietmap.vietmapsdk.offline

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.mapbox.geojson.Point
import vn.vietmap.vietmapsdk.log.Logger
import vn.vietmap.vietmapsdk.maps.Style
import vn.vietmap.vietmapsdk.testapp.activity.FeatureOverviewActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

/**
 * Integration test that validates downloading an offline region from a point geometry at zoomlevel 17
 */
@RunWith(AndroidJUnit4::class)
class OfflineDownloadTest : vn.vietmap.vietmapsdk.offline.OfflineRegion.OfflineRegionObserver {

    @Rule
    @JvmField
    var rule = ActivityTestRule(FeatureOverviewActivity::class.java)

    private val countDownLatch = CountDownLatch(1)
    private lateinit var offlineRegion: vn.vietmap.vietmapsdk.offline.OfflineRegion

    @Test(timeout = 60000)
    fun offlineDownload() {
        rule.runOnUiThreadActivity {
            vn.vietmap.vietmapsdk.offline.OfflineManager.getInstance(rule.activity).createOfflineRegion(
                createTestRegionDefinition(),
                ByteArray(0),
                object : vn.vietmap.vietmapsdk.offline.OfflineManager.CreateOfflineRegionCallback {
                    override fun onCreate(region: vn.vietmap.vietmapsdk.offline.OfflineRegion) {
                        offlineRegion = region
                        offlineRegion.setDownloadState(vn.vietmap.vietmapsdk.offline.OfflineRegion.STATE_ACTIVE)
                        offlineRegion.setObserver(this@OfflineDownloadTest)
                    }

                    override fun onError(error: String) {
                        vn.vietmap.vietmapsdk.log.Logger.e(TAG, "Error while creating offline region: $error")
                    }
                }
            )
        }

        if (!countDownLatch.await(60, TimeUnit.SECONDS)) {
            throw TimeoutException()
        }
    }

    override fun onStatusChanged(status: vn.vietmap.vietmapsdk.offline.OfflineRegionStatus) {
        vn.vietmap.vietmapsdk.log.Logger.i(TAG, "Download percentage ${100.0 * status.completedResourceCount / status.requiredResourceCount}")
        if (status.isComplete) {
            offlineRegion.setDownloadState(vn.vietmap.vietmapsdk.offline.OfflineRegion.STATE_INACTIVE)
            countDownLatch.countDown()
        }
    }

    override fun onError(error: vn.vietmap.vietmapsdk.offline.OfflineRegionError) {
        vn.vietmap.vietmapsdk.log.Logger.e(TAG, "Error while downloading offline region: $error")
    }

    override fun mapboxTileCountLimitExceeded(limit: Long) {
        vn.vietmap.vietmapsdk.log.Logger.e(TAG, "Tile count limited exceeded: $limit")
    }

    private fun createTestRegionDefinition(): OfflineRegionDefinition {
        return OfflineGeometryRegionDefinition(
            Style.getPredefinedStyle("Streets"),
            Point.fromLngLat(50.847857, 4.360137),
            17.0,
            17.0,
            1.0f,
            false
        )
    }

    companion object {
        const val TAG = "OfflineDownloadTest"
    }
}

fun ActivityTestRule<*>.runOnUiThreadActivity(runnable: () -> Unit) = activity.runOnUiThread(runnable)
