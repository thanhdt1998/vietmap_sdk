package vn.vietmap.vietmapsdk.testapp.activity.storage

import android.os.Bundle
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import vn.vietmap.vietmapsdk.offline.OfflineManager
import vn.vietmap.vietmapsdk.testapp.R
import kotlinx.android.synthetic.main.activity_cache_management.*

/**
 * Test activity showcasing the cache management APIs
 */
class CacheManagementActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cache_management)

        val fileSource = vn.vietmap.vietmapsdk.offline.OfflineManager.getInstance(this)
        resetDatabaseButton.setOnClickListener {
            fileSource.resetDatabase(object : vn.vietmap.vietmapsdk.offline.OfflineManager.FileSourceCallback {
                override fun onSuccess() {
                    showSnackbar("Reset database success")
                }

                override fun onError(message: String) {
                    showSnackbar("Reset database fail: $message")
                }
            })
        }

        invalidateAmbientCacheButton.setOnClickListener {
            fileSource.invalidateAmbientCache(object : vn.vietmap.vietmapsdk.offline.OfflineManager.FileSourceCallback {
                override fun onSuccess() {
                    showSnackbar("Invalidate ambient cache success")
                }

                override fun onError(message: String) {
                    showSnackbar("Invalidate ambient cache fail: $message")
                }
            })
        }

        clearAmbientCacheButton.setOnClickListener {
            fileSource.clearAmbientCache(object : vn.vietmap.vietmapsdk.offline.OfflineManager.FileSourceCallback {
                override fun onSuccess() {
                    showSnackbar("Clear ambient cache success")
                }

                override fun onError(message: String) {
                    showSnackbar("Clear ambient cache fail: $message")
                }
            })
        }

        setMaximumAmbientCacheSizeButton.setOnClickListener {
            fileSource.setMaximumAmbientCacheSize(
                5000000,
                object : vn.vietmap.vietmapsdk.offline.OfflineManager.FileSourceCallback {
                    override fun onSuccess() {
                        showSnackbar("Set maximum ambient cache size success")
                    }

                    override fun onError(message: String) {
                        showSnackbar("Set maximum ambient cache size fail: $message")
                    }
                }
            )
        }
    }

    fun showSnackbar(message: String) {
        // validate that all callbacks occur on main thread
        assert(Looper.myLooper() == Looper.getMainLooper())

        // show snackbar
        Snackbar.make(container, message, Snackbar.LENGTH_SHORT).show()
    }
}
