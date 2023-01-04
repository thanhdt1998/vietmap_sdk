package vn.vietmap.vietmapsdk.log

import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class LoggerTest {

    private val logger: vn.vietmap.vietmapsdk.log.LoggerDefinition = mockk(relaxed = true)

    @Before
    fun setUp() {
        vn.vietmap.vietmapsdk.log.Logger.setLoggerDefinition(logger)
    }

    @Test
    fun verbosityLogLevel() {
        vn.vietmap.vietmapsdk.log.Logger.setVerbosity(vn.vietmap.vietmapsdk.log.Logger.VERBOSE)
        vn.vietmap.vietmapsdk.log.Logger.v(TAG, MESSAGE)
        verify { logger.v(TAG, MESSAGE) }
    }

    @Test
    fun verbosityLogLevelIgnore() {
        vn.vietmap.vietmapsdk.log.Logger.setVerbosity(vn.vietmap.vietmapsdk.log.Logger.DEBUG)
        vn.vietmap.vietmapsdk.log.Logger.v(TAG, MESSAGE)
        verify(exactly = 0) { logger.v(TAG, MESSAGE) }
    }

    @Test
    fun debugLogLevel() {
        vn.vietmap.vietmapsdk.log.Logger.setVerbosity(vn.vietmap.vietmapsdk.log.Logger.VERBOSE)
        vn.vietmap.vietmapsdk.log.Logger.d(TAG, MESSAGE)
        verify { logger.d(TAG, MESSAGE) }
    }

    @Test
    fun debugLogLevelIgnore() {
        vn.vietmap.vietmapsdk.log.Logger.setVerbosity(vn.vietmap.vietmapsdk.log.Logger.WARN)
        vn.vietmap.vietmapsdk.log.Logger.d(TAG, MESSAGE)
        verify(exactly = 0) { logger.d(TAG, MESSAGE) }
    }

    @Test
    fun warnLogLevel() {
        vn.vietmap.vietmapsdk.log.Logger.setVerbosity(vn.vietmap.vietmapsdk.log.Logger.WARN)
        vn.vietmap.vietmapsdk.log.Logger.w(TAG, MESSAGE)
        verify { logger.w(TAG, MESSAGE) }
    }

    @Test
    fun warnLogLevelIgnore() {
        vn.vietmap.vietmapsdk.log.Logger.setVerbosity(vn.vietmap.vietmapsdk.log.Logger.ERROR)
        vn.vietmap.vietmapsdk.log.Logger.w(TAG, MESSAGE)
        verify(exactly = 0) { logger.w(TAG, MESSAGE) }
    }

    @Test
    fun errorLogLevel() {
        vn.vietmap.vietmapsdk.log.Logger.setVerbosity(vn.vietmap.vietmapsdk.log.Logger.ERROR)
        vn.vietmap.vietmapsdk.log.Logger.e(TAG, MESSAGE)
        verify { logger.e(TAG, MESSAGE) }
    }

    @Test
    fun errorLogLevelIgnore() {
        vn.vietmap.vietmapsdk.log.Logger.setVerbosity(vn.vietmap.vietmapsdk.log.Logger.NONE)
        vn.vietmap.vietmapsdk.log.Logger.e(TAG, MESSAGE)
        verify(exactly = 0) { logger.e(TAG, MESSAGE) }
    }

    @Test
    fun noneLogLevelIgnore() {
        vn.vietmap.vietmapsdk.log.Logger.setVerbosity(vn.vietmap.vietmapsdk.log.Logger.NONE)
        vn.vietmap.vietmapsdk.log.Logger.v(TAG, MESSAGE)
        vn.vietmap.vietmapsdk.log.Logger.d(TAG, MESSAGE)
        vn.vietmap.vietmapsdk.log.Logger.w(TAG, MESSAGE)
        vn.vietmap.vietmapsdk.log.Logger.e(TAG, MESSAGE)
        verify(exactly = 0) { logger.v(TAG, MESSAGE) }
        verify(exactly = 0) { logger.d(TAG, MESSAGE) }
        verify(exactly = 0) { logger.w(TAG, MESSAGE) }
        verify(exactly = 0) { logger.e(TAG, MESSAGE) }
    }

    companion object {
        const val TAG: String = "TAG"
        const val MESSAGE: String = "message"
    }
}
