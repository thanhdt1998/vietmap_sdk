package vn.vietmap.vietmapsdk.testapp

import android.app.Application
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.os.StrictMode.VmPolicy
import android.text.TextUtils
import vn.vietmap.vietmapsdk.MapStrictMode
import vn.vietmap.vietmapsdk.Vietmap
import vn.vietmap.vietmapsdk.testapp.utils.ApiKeyUtils
import vn.vietmap.vietmapsdk.testapp.utils.TileLoadingMeasurementUtils
import timber.log.Timber
import vn.vietmap.vietmapsdk.WellKnownTileServer
import vn.vietmap.vietmapsdk.testapp.utils.TimberLogger

/**
 * Application class of the test application.
 *
 *
 * Initialises components as LeakCanary, Strictmode, Timber and Vietmap
 *
 */
class MapLibreApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initializeLogger()
        initializeStrictMode()
        initializeVietmap()
    }

    private fun initializeLogger() {
        vn.vietmap.vietmapsdk.log.Logger.setLoggerDefinition(TimberLogger())
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initializeStrictMode() {
        StrictMode.setThreadPolicy(
            ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .build()
        )
        StrictMode.setVmPolicy(
            VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .penaltyLog()
                .penaltyDeath()
                .build()
        )
    }

    private fun initializeVietmap() {
        val apiKey = ApiKeyUtils.getApiKey(applicationContext)
        validateApiKey(apiKey)
        Vietmap.getInstance(applicationContext, apiKey, TILE_SERVER)
        TileLoadingMeasurementUtils.setUpTileLoadingMeasurement()
        MapStrictMode.setStrictModeEnabled(true)
    }

    companion object {
        val TILE_SERVER = WellKnownTileServer.MapTiler
        private const val DEFAULT_API_KEY = "DefaultApiKey"
        private const val API_KEY_NOT_SET_MESSAGE =
            (
                "In order to run the Test App you need to set a valid " +
                    "API key. During development, you can set the MGL_API_KEY environment variable for the SDK to " +
                    "automatically include it in the Test App. Otherwise, you can manually include it in the " +
                    "res/values/developer-config.xml file in the VietmapGLAndroidSDKTestApp folder."
                )

        private fun validateApiKey(apiKey: String) {
            Timber.e(apiKey)
            if (TextUtils.isEmpty(apiKey) || apiKey == DEFAULT_API_KEY) {
                Timber.e(API_KEY_NOT_SET_MESSAGE)
            }
        }
    }
}
