package vn.vietmap.vietmapsdk

import com.mapbox.vietmapsdk.testapp.MapboxApplication

class InstrumentationApplication : MapboxApplication() {
    override fun initializeLeakCanary(): Boolean {
        // do not initialize leak canary during instrumentation tests
        return true
    }
}
