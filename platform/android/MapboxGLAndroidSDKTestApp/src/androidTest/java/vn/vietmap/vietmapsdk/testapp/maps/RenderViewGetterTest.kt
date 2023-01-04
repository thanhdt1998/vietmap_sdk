package vn.vietmap.vietmapsdk.testapp.maps

import android.view.TextureView
import android.view.ViewGroup
import androidx.test.annotation.UiThreadTest
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.mapbox.vietmapsdk.AppCenter
import vn.vietmap.vietmapsdk.maps.MapView
import vn.vietmap.vietmapsdk.maps.MapboxMapOptions
import vn.vietmap.vietmapsdk.maps.renderer.glsurfaceview.MapboxGLSurfaceView
import com.mapbox.vietmapsdk.testapp.activity.FeatureOverviewActivity
import junit.framework.Assert.assertNotNull
import junit.framework.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch

@RunWith(AndroidJUnit4::class)
class RenderViewGetterTest : AppCenter() {

    @Rule
    @JvmField
    var rule = ActivityTestRule(FeatureOverviewActivity::class.java)

    private lateinit var rootView: ViewGroup
    private lateinit var mapView: vn.vietmap.vietmapsdk.maps.MapView
    private val latch: CountDownLatch = CountDownLatch(1)

    @Test
    @UiThreadTest
    fun testGLSurfaceView() {
        rootView = rule.activity.findViewById(android.R.id.content)
        mapView = vn.vietmap.vietmapsdk.maps.MapView(rule.activity)
        assertNotNull(mapView.renderView)
        assertTrue(mapView.renderView is vn.vietmap.vietmapsdk.maps.renderer.glsurfaceview.MapboxGLSurfaceView)
    }

    @Test
    @UiThreadTest
    fun testTextureView() {
        rootView = rule.activity.findViewById(android.R.id.content)
        mapView = vn.vietmap.vietmapsdk.maps.MapView(
            rule.activity,
            vn.vietmap.vietmapsdk.maps.MapboxMapOptions.createFromAttributes(rule.activity, null)
                .textureMode(true)
        )
        assertNotNull(mapView.renderView)
        assertTrue(mapView.renderView is TextureView)
    }
}
