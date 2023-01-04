package vn.vietmap.vietmapsdk.testapp.activity.fragment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import vn.vietmap.vietmapsdk.maps.VietmapMap
import vn.vietmap.vietmapsdk.maps.Style
import vn.vietmap.vietmapsdk.maps.SupportMapFragment
import vn.vietmap.vietmapsdk.testapp.R
import vn.vietmap.vietmapsdk.testapp.utils.NavUtils
import kotlinx.android.synthetic.main.activity_backstack_fragment.*

/**
 * Test activity showcasing using the MapFragment API as part of a backstacked fragment.
 */
class FragmentBackStackActivity : AppCompatActivity() {

    companion object {
        private const val FRAGMENT_TAG = "map_fragment"
    }

    private lateinit var mapFragment: vn.vietmap.vietmapsdk.maps.SupportMapFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_backstack_fragment)

        if (savedInstanceState == null) {
            mapFragment = vn.vietmap.vietmapsdk.maps.SupportMapFragment.newInstance()
            mapFragment.getMapAsync { initMap(it) }

            supportFragmentManager.beginTransaction().apply {
                add(R.id.container, mapFragment, FRAGMENT_TAG)
            }.commit()
        } else {
            supportFragmentManager.findFragmentByTag(FRAGMENT_TAG)?.also { fragment ->
                if (fragment is vn.vietmap.vietmapsdk.maps.SupportMapFragment) {
                    fragment.getMapAsync { initMap(it) }
                }
            }
        }

        button.setOnClickListener { handleClick() }
    }

    private fun initMap(VietmapMap: vn.vietmap.vietmapsdk.maps.VietmapMap) {
        VietmapMap.setStyle(vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Satellite Hybrid")) {
            VietmapMap.setPadding(300, 300, 300, 300)
        }
    }

    private fun handleClick() {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.container, NestedViewPagerActivity.ItemAdapter.EmptyFragment())
            addToBackStack("map_empty_fragment")
        }.commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 0) {
            // activity uses singleInstance for testing purposes
            // code below provides a default navigation when using the app
            vn.vietmap.vietmapsdk.testapp.utils.NavUtils.navigateHome(this)
        } else {
            super.onBackPressed()
        }
    }
}
