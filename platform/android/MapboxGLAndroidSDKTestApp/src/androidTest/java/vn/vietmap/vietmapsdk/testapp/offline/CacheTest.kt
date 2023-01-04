package vn.vietmap.vietmapsdk.testapp.offline

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import vn.vietmap.vietmapsdk.offline.OfflineManager
import com.mapbox.vietmapsdk.testapp.activity.FeatureOverviewActivity
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch

@RunWith(AndroidJUnit4::class)
class CacheTest {

    @Rule
    @JvmField
    var rule = ActivityTestRule(FeatureOverviewActivity::class.java)

    private val context: Context by lazy { rule.activity }

    private val countDownLatch = CountDownLatch(1)

    @Test
    fun testSetMaximumAmbientCacheSize() {
        rule.activity.runOnUiThread {
            vn.vietmap.vietmapsdk.offline.OfflineManager.getInstance(context).setMaximumAmbientCacheSize(
                10000000,
                object : vn.vietmap.vietmapsdk.offline.OfflineManager.FileSourceCallback {
                    override fun onSuccess() {
                        countDownLatch.countDown()
                    }

                    override fun onError(message: String) {
                        Assert.assertNull("onError should not be called", message)
                    }
                }
            )
        }
        countDownLatch.await()
    }

    @Test
    fun testSetClearAmbientCache() {
        rule.activity.runOnUiThread {
            vn.vietmap.vietmapsdk.offline.OfflineManager.getInstance(context).clearAmbientCache(object : vn.vietmap.vietmapsdk.offline.OfflineManager.FileSourceCallback {
                override fun onSuccess() {
                    countDownLatch.countDown()
                }

                override fun onError(message: String) {
                    Assert.assertNull("onError should not be called", message)
                }
            })
        }
        countDownLatch.await()
    }

    @Test
    fun testSetInvalidateAmbientCache() {
        rule.activity.runOnUiThread {
            vn.vietmap.vietmapsdk.offline.OfflineManager.getInstance(context).invalidateAmbientCache(object : vn.vietmap.vietmapsdk.offline.OfflineManager.FileSourceCallback {
                override fun onSuccess() {
                    countDownLatch.countDown()
                }

                override fun onError(message: String) {
                    Assert.assertNull("onError should not be called", message)
                }
            })
        }
        countDownLatch.await()
    }

    @Test
    fun testSetResetDatabase() {
        rule.activity.runOnUiThread {
            vn.vietmap.vietmapsdk.offline.OfflineManager.getInstance(context).resetDatabase(object : vn.vietmap.vietmapsdk.offline.OfflineManager.FileSourceCallback {
                override fun onSuccess() {
                    countDownLatch.countDown()
                }

                override fun onError(message: String) {
                    Assert.assertNull("onError should not be called", message)
                }
            })
        }
        countDownLatch.await()
    }

    @Test
    fun testSetPackDatabase() {
        rule.activity.runOnUiThread {
            vn.vietmap.vietmapsdk.offline.OfflineManager.getInstance(context).packDatabase(object : vn.vietmap.vietmapsdk.offline.OfflineManager.FileSourceCallback {
                override fun onSuccess() {
                    countDownLatch.countDown()
                }

                override fun onError(message: String) {
                    Assert.assertNull("onError should not be called", message)
                }
            })
        }
        countDownLatch.await()
    }
}
