package vn.vietmap.vietmapsdk.testapp.activity.maplayout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import vn.vietmap.vietmapsdk.camera.CameraUpdateFactory
import vn.vietmap.vietmapsdk.geometry.LatLng
// import com.mapbox.vietmapsdk.maps.* //
import vn.vietmap.vietmapsdk.maps.MapView.OnCameraDidChangeListener
import vn.vietmap.vietmapsdk.maps.MapView.OnCameraIsChangingListener
import vn.vietmap.vietmapsdk.maps.MapView.OnCameraWillChangeListener
import vn.vietmap.vietmapsdk.maps.MapView.OnDidBecomeIdleListener
import vn.vietmap.vietmapsdk.maps.MapView.OnDidFailLoadingMapListener
import vn.vietmap.vietmapsdk.maps.MapView.OnDidFinishLoadingMapListener
import vn.vietmap.vietmapsdk.maps.MapView.OnDidFinishLoadingStyleListener
import vn.vietmap.vietmapsdk.maps.MapView.OnDidFinishRenderingFrameListener
import vn.vietmap.vietmapsdk.maps.MapView.OnDidFinishRenderingMapListener
import vn.vietmap.vietmapsdk.maps.MapView.OnSourceChangedListener
import vn.vietmap.vietmapsdk.maps.MapView.OnWillStartLoadingMapListener
import vn.vietmap.vietmapsdk.maps.MapView.OnWillStartRenderingFrameListener
import vn.vietmap.vietmapsdk.maps.MapView.OnWillStartRenderingMapListener
import vn.vietmap.vietmapsdk.testapp.R
import timber.log.Timber
import vn.vietmap.vietmapsdk.maps.Style

/**
 * Test activity showcasing how to listen to map change events.
 */
class MapChangeActivity : AppCompatActivity() {
    private lateinit var mapView: vn.vietmap.vietmapsdk.maps.MapView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_simple)
        mapView = findViewById(R.id.mapView)
        mapView.addOnCameraIsChangingListener(OnCameraIsChangingListener { Timber.v("OnCameraIsChanging") })
        mapView.addOnCameraDidChangeListener(
            OnCameraDidChangeListener { animated: Boolean ->
                Timber.v(
                    "OnCamaraDidChange: animated: %s",
                    animated
                )
            }
        )
        mapView.addOnCameraWillChangeListener(
            OnCameraWillChangeListener { animated: Boolean ->
                Timber.v(
                    "OnCameraWilChange: animated: %s",
                    animated
                )
            }
        )
        mapView.addOnDidFailLoadingMapListener(
            OnDidFailLoadingMapListener { errorMessage: String? ->
                Timber.v(
                    "OnDidFailLoadingMap: %s",
                    errorMessage
                )
            }
        )
        mapView.addOnDidFinishLoadingMapListener(OnDidFinishLoadingMapListener { Timber.v("OnDidFinishLoadingMap") })
        mapView.addOnDidFinishLoadingStyleListener(OnDidFinishLoadingStyleListener { Timber.v("OnDidFinishLoadingStyle") })
        mapView.addOnDidFinishRenderingFrameListener(
            OnDidFinishRenderingFrameListener { fully: Boolean ->
                Timber.v(
                    "OnDidFinishRenderingFrame: fully: %s",
                    fully
                )
            }
        )
        mapView.addOnDidFinishRenderingMapListener(
            OnDidFinishRenderingMapListener { fully: Boolean ->
                Timber.v(
                    "OnDidFinishRenderingMap: fully: %s",
                    fully
                )
            }
        )
        mapView.addOnDidBecomeIdleListener(OnDidBecomeIdleListener { Timber.v("OnDidBecomeIdle") })
        mapView.addOnSourceChangedListener(
            OnSourceChangedListener { sourceId: String? ->
                Timber.v(
                    "OnSourceChangedListener: source with id: %s",
                    sourceId
                )
            }
        )
        mapView.addOnWillStartLoadingMapListener(OnWillStartLoadingMapListener { Timber.v("OnWillStartLoadingMap") })
        mapView.addOnWillStartRenderingFrameListener(OnWillStartRenderingFrameListener { Timber.v("OnWillStartRenderingFrame") })
        mapView.addOnWillStartRenderingMapListener(OnWillStartRenderingMapListener { Timber.v("OnWillStartRenderingMap") })
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(
            vn.vietmap.vietmapsdk.maps.OnMapReadyCallback { mapboxMap: vn.vietmap.vietmapsdk.maps.VietmapMap ->
                mapboxMap.setStyle(Style.getPredefinedStyle("Streets"))
                mapboxMap.animateCamera(
                    vn.vietmap.vietmapsdk.camera.CameraUpdateFactory.newLatLngZoom(
                        vn.vietmap.vietmapsdk.geometry.LatLng(
                            55.754020,
                            37.620948
                        ),
                        12.0
                    ),
                    9000
                )
            }
        )
    }

    override fun onStart() {
        super.onStart()
        mapView!!.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView!!.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView!!.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView!!.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView!!.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView!!.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView!!.onSaveInstanceState(outState)
    }
}
