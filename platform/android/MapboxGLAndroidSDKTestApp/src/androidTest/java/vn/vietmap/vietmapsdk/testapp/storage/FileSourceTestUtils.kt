package vn.vietmap.vietmapsdk.testapp.storage

import android.app.Activity
import androidx.annotation.WorkerThread
import com.mapbox.vietmapsdk.AppCenter
import vn.vietmap.vietmapsdk.storage.FileSource
import junit.framework.Assert
import java.io.File
import java.util.concurrent.CountDownLatch

class FileSourceTestUtils(private val activity: Activity) : AppCenter() {
    val originalPath = vn.vietmap.vietmapsdk.storage.FileSource.getResourcesCachePath(activity)
    val testPath = "$originalPath/test"
    val testPath2 = "$originalPath/test2"

    private val paths = listOf(testPath, testPath2)

    fun setup() {
        for (path in paths) {
            val testFile = File(path)
            testFile.mkdirs()
        }
    }

    @WorkerThread
    fun cleanup() {
        val currentPath = vn.vietmap.vietmapsdk.storage.FileSource.getResourcesCachePath(activity)
        if (currentPath != originalPath) {
            changePath(originalPath)
        }

        for (path in paths) {
            val testFile = File(path)
            if (testFile.exists()) {
                testFile.deleteRecursively()
            }
        }
    }

    @WorkerThread
    fun changePath(requestedPath: String) {
        val latch = CountDownLatch(1)
        activity.runOnUiThread {
            vn.vietmap.vietmapsdk.storage.FileSource.setResourcesCachePath(
                requestedPath,
                object : vn.vietmap.vietmapsdk.storage.FileSource.ResourcesCachePathChangeCallback {
                    override fun onSuccess(path: String) {
                        Assert.assertEquals(requestedPath, path)
                        latch.countDown()
                    }

                    override fun onError(message: String) {
                        Assert.fail("Resource path change failed - path: $requestedPath, message: $message")
                    }
                }
            )
        }
        latch.await()
    }
}
