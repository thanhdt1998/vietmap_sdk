package vn.vietmap.vietmapsdk.testapp.activity.fragment

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import vn.vietmap.vietmapsdk.camera.CameraPosition
import vn.vietmap.vietmapsdk.geometry.LatLng
import vn.vietmap.vietmapsdk.maps.VietMapOptions
import vn.vietmap.vietmapsdk.maps.Style
import vn.vietmap.vietmapsdk.maps.SupportMapFragment
import vn.vietmap.vietmapsdk.testapp.R
import kotlinx.android.synthetic.main.activity_viewpager.*

/**
 * Test activity showcasing using the Android SDK ViewPager API to show MapFragments.
 */
class ViewPagerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewpager)
//        viewPager.adapter = MapFragmentAdapter(this, supportFragmentManager)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
//        val currentPosition = viewPager.currentItem
//        val offscreenLimit = viewPager.offscreenPageLimit
//        for (i in currentPosition - offscreenLimit..currentPosition + offscreenLimit) {
//            if (i < 0 || i > viewPager.adapter?.count ?: 0) {
//                continue
//            }
//            val mapFragment = viewPager.adapter?.instantiateItem(viewPager, i) as vn.vietmap.vietmapsdk.maps.SupportMapFragment
//            mapFragment.getMapAsync(i)
//        }
    }

    internal class MapFragmentAdapter(private val context: Context, fragmentManager: androidx.fragment.app.FragmentManager) : androidx.fragment.app.FragmentStatePagerAdapter(fragmentManager) {

        override fun getCount(): Int {
            return NUM_ITEMS
        }

        override fun getItem(position: Int): androidx.fragment.app.Fragment? {
            val options = vn.vietmap.vietmapsdk.maps.VietMapOptions.createFromAttributes(context)
            options.textureMode(true)
            options.camera(
                vn.vietmap.vietmapsdk.camera.CameraPosition.Builder()
                    .zoom(3.0)
                    .target(
                        when (position) {
                            0 -> {
                                vn.vietmap.vietmapsdk.geometry.LatLng(
                                    34.920526,
                                    102.634774
                                )
                            }
                            1 -> {
                                vn.vietmap.vietmapsdk.geometry.LatLng(
                                    62.326440,
                                    92.764913
                                )
                            }
                            2 -> {
                                vn.vietmap.vietmapsdk.geometry.LatLng(
                                    -25.007786,
                                    133.623852
                                )
                            }
                            3 -> {
                                vn.vietmap.vietmapsdk.geometry.LatLng(
                                    62.326440,
                                    92.764913
                                )
                            }
                            else -> {
                                vn.vietmap.vietmapsdk.geometry.LatLng(
                                    34.920526,
                                    102.634774
                                )
                            }
                        }
                    )
                    .build()
            )

            val fragment = vn.vietmap.vietmapsdk.maps.SupportMapFragment.newInstance(options)
            fragment.getMapAsync(position)
            return fragment
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return "Page $position"
        }

        companion object {
            private const val NUM_ITEMS = 5
        }
    }
}

fun vn.vietmap.vietmapsdk.maps.SupportMapFragment.getMapAsync(index: Int) {
    this.getMapAsync {
        it.setStyle(
            when (index) {
                0 -> vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Streets")
                1 -> vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Pastel")
                2 -> vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Satellite Hybrid")
                3 -> vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Bright")
                else -> vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Streets")
            }
        )
    }
}
