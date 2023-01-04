package vn.vietmap.vietmapsdk.testapp.offline

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.mapbox.vietmapsdk.AppCenter
import vn.vietmap.vietmapsdk.offline.OfflineManager
import vn.vietmap.vietmapsdk.offline.OfflineRegion
import vn.vietmap.vietmapsdk.storage.FileSource
import com.mapbox.vietmapsdk.testapp.activity.FeatureOverviewActivity
import com.mapbox.vietmapsdk.testapp.utils.FileUtils
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import java.io.IOException
import java.util.concurrent.CountDownLatch

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4::class)
class OfflineManagerTest : AppCenter() {

    companion object {
        private const val TEST_DB_FILE_NAME = "offline_test.db"
        private lateinit var mergedRegion: vn.vietmap.vietmapsdk.offline.OfflineRegion
    }

    @Rule
    @JvmField
    var rule = ActivityTestRule(FeatureOverviewActivity::class.java)

    private val context: Context by lazy { rule.activity }

    @Test(timeout = 30_000)
    fun a_copyFileFromAssets() {
        val latch = CountDownLatch(1)
        rule.activity.runOnUiThread {
            FileUtils.CopyFileFromAssetsTask(
                rule.activity,
                object : FileUtils.OnFileCopiedFromAssetsListener {
                    override fun onFileCopiedFromAssets() {
                        latch.countDown()
                    }

                    override fun onError() {
                        throw IOException("Unable to copy DB file.")
                    }
                }
            ).execute(TEST_DB_FILE_NAME, vn.vietmap.vietmapsdk.storage.FileSource.getResourcesCachePath(rule.activity))
        }
        latch.await()
    }

    @Test(timeout = 30_000)
    fun b_mergeRegion() {
        val latch = CountDownLatch(1)
        rule.activity.runOnUiThread {
            vn.vietmap.vietmapsdk.offline.OfflineManager.getInstance(context).mergeOfflineRegions(
                vn.vietmap.vietmapsdk.storage.FileSource.getResourcesCachePath(rule.activity) + "/" + TEST_DB_FILE_NAME,
                object : vn.vietmap.vietmapsdk.offline.OfflineManager.MergeOfflineRegionsCallback {
                    override fun onMerge(offlineRegions: Array<out vn.vietmap.vietmapsdk.offline.OfflineRegion>?) {
                        assert(offlineRegions?.size == 1)
                        latch.countDown()
                    }

                    override fun onError(error: String?) {
                        throw RuntimeException("Unable to merge external offline database. $error")
                    }
                }
            )
        }
        latch.await()
    }

    @Test(timeout = 30_000)
    fun c_listRegion() {
        val latch = CountDownLatch(1)
        rule.activity.runOnUiThread {
            vn.vietmap.vietmapsdk.offline.OfflineManager.getInstance(context).listOfflineRegions(object : vn.vietmap.vietmapsdk.offline.OfflineManager.ListOfflineRegionsCallback {
                override fun onList(offlineRegions: Array<out vn.vietmap.vietmapsdk.offline.OfflineRegion>?) {
                    assert(offlineRegions?.size == 1)
                    mergedRegion = offlineRegions!![0]
                    latch.countDown()
                }

                override fun onError(error: String?) {
                    throw RuntimeException("Unable to merge external offline database. $error")
                }
            })
        }
        latch.await()
    }

    @Test(timeout = 30_000)
    fun d_invalidateRegion() {
        val latch = CountDownLatch(1)
        rule.activity.runOnUiThread {
            mergedRegion.invalidate(object : vn.vietmap.vietmapsdk.offline.OfflineRegion.OfflineRegionInvalidateCallback {
                override fun onInvalidate() {
                    latch.countDown()
                }

                override fun onError(error: String?) {
                    throw RuntimeException("Unable to delete region")
                }
            })
        }
        latch.await()
    }

    @Test(timeout = 30_000)
    fun e_deleteRegion() {
        val latch = CountDownLatch(1)
        rule.activity.runOnUiThread {
            mergedRegion.delete(object : vn.vietmap.vietmapsdk.offline.OfflineRegion.OfflineRegionDeleteCallback {
                override fun onDelete() {
                    latch.countDown()
                }

                override fun onError(error: String?) {
                    throw RuntimeException("Unable to delete region")
                }
            })
        }
        latch.await()
    }
}
