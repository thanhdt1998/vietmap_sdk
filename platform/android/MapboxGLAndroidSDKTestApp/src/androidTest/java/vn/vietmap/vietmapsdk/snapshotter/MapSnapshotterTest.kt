package vn.vietmap.vietmapsdk.snapshotter

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import vn.vietmap.vietmapsdk.camera.CameraPosition
import vn.vietmap.vietmapsdk.geometry.LatLng
import vn.vietmap.vietmapsdk.maps.Style
import vn.vietmap.vietmapsdk.style.layers.BackgroundLayer
import vn.vietmap.vietmapsdk.style.layers.PropertyFactory
//import vn.vietmap.vietmapsdk.testapp.activity.
import junit.framework.Assert.assertNotNull
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

/**
 * Integration test that validates if a snapshotter creation
 */
@RunWith(AndroidJUnit4::class)
class MapSnapshotterTest {
//
//    @Rule
//    @JvmField
//    var rule = ActivityTestRule(FeatureOverviewActivity::class.java)

    private val countDownLatch = CountDownLatch(1)

    @Test
    fun mapSnapshotter() {
        var mapSnapshotter: vn.vietmap.vietmapsdk.snapshotter.MapSnapshotter?
//        rule.activity.runOnUiThread {
//            val bg = vn.vietmap.vietmapsdk.style.layers.BackgroundLayer("rand_tint")
//            bg.setProperties(vn.vietmap.vietmapsdk.style.layers.PropertyFactory.backgroundColor("rgba(255,128,0,0.7)"))
//            val options = vn.vietmap.vietmapsdk.snapshotter.MapSnapshotter.Options(512, 512)
//                .withPixelRatio(1.0f)
//                .withStyleBuilder(
//                    vn.vietmap.vietmapsdk.maps.Style.Builder().fromUri(vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Satellite Hybrid"))
//                        .withLayerAbove(bg, "country-label")
//                )
//                .withCameraPosition(
//                    vn.vietmap.vietmapsdk.camera.CameraPosition.Builder()
//                        .zoom(12.0)
//                        .target(vn.vietmap.vietmapsdk.geometry.LatLng(51.145495, 5.742234))
//                        .build()
//                )
////            mapSnapshotter =
////                vn.vietmap.vietmapsdk.snapshotter.MapSnapshotter(rule.activity, options)
////            mapSnapshotter!!.start(
////                {
////                    assertNotNull(it)
////                    assertNotNull(it.bitmap)
////                    countDownLatch.countDown()
////                },
////                {
////                    Assert.fail(it)
////                }
////            )
////        }
//        if (!countDownLatch.await(30, TimeUnit.SECONDS)) {
//            throw TimeoutException()
//        }
    }
}
