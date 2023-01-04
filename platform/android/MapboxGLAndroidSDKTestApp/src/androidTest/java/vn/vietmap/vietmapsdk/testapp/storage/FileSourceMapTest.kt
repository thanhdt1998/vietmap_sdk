package vn.vietmap.vietmapsdk.testapp.storage

import androidx.test.annotation.UiThreadTest
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.mapbox.vietmapsdk.AppCenter
import vn.vietmap.vietmapsdk.storage.FileSource
import com.mapbox.vietmapsdk.testapp.activity.espresso.EspressoTestActivity
import junit.framework.Assert
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestName
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch

@RunWith(AndroidJUnit4::class)
open class FileSourceMapTest : AppCenter() {

    private lateinit var fileSourceTestUtils: FileSourceTestUtils

    @get:Rule
    val rule = ActivityTestRule(EspressoTestActivity::class.java)

    @get:Rule
    val testName = TestName()

    @Before
    @UiThreadTest
    fun setup() {
        fileSourceTestUtils = FileSourceTestUtils(rule.activity)
        fileSourceTestUtils.setup()
    }

    @Test
    fun changeResourcesPathWhileMapVisible() {
        val latch = CountDownLatch(1)
        rule.activity.runOnUiThread {
            vn.vietmap.vietmapsdk.storage.FileSource.setResourcesCachePath(
                fileSourceTestUtils.testPath,
                object : vn.vietmap.vietmapsdk.storage.FileSource.ResourcesCachePathChangeCallback {
                    override fun onSuccess(path: String) {
                        latch.countDown()
                        Assert.assertEquals(fileSourceTestUtils.testPath, path)
                    }

                    override fun onError(message: String) {
                        Assert.fail("Resources path can be changed while the map is running")
                    }
                }
            )
        }
        latch.await()
    }

    @After
    fun cleanup() {
        fileSourceTestUtils.cleanup()
    }
}
