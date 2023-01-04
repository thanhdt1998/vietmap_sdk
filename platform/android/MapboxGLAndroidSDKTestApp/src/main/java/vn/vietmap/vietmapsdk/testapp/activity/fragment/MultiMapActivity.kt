package vn.vietmap.vietmapsdk.testapp.activity.fragment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import vn.vietmap.vietmapsdk.maps.VietmapMap
import vn.vietmap.vietmapsdk.maps.OnMapReadyCallback
import vn.vietmap.vietmapsdk.maps.Style
import vn.vietmap.vietmapsdk.maps.SupportMapFragment
import vn.vietmap.vietmapsdk.testapp.R

/**
 * Test Activity showcasing using multiple static map fragments in one layout.
 */
class MultiMapActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multi_map)
        val fragmentManager = supportFragmentManager
        initFragmentStyle(fragmentManager, R.id.map1, vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Streets"))
        initFragmentStyle(fragmentManager, R.id.map2, vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Bright"))
        initFragmentStyle(fragmentManager, R.id.map3, vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Satellite Hybrid"))
        initFragmentStyle(fragmentManager, R.id.map4, vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Pastel"))
    }

    private fun initFragmentStyle(
        fragmentManager: FragmentManager,
        fragmentId: Int,
        styleId: String
    ) {
        (fragmentManager.findFragmentById(fragmentId) as vn.vietmap.vietmapsdk.maps.SupportMapFragment?)
            ?.getMapAsync(vn.vietmap.vietmapsdk.maps.OnMapReadyCallback { mapboxMap: vn.vietmap.vietmapsdk.maps.VietmapMap ->
                mapboxMap.setStyle(
                    styleId
                )
            })
    }
}
