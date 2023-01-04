package vn.vietmap.vietmapsdk.location

import android.graphics.Bitmap
import android.graphics.Color
import vn.vietmap.vietmapsdk.geometry.LatLng
import vn.vietmap.vietmapsdk.location.LocationComponentConstants.*
import vn.vietmap.vietmapsdk.location.modes.RenderMode
import vn.vietmap.vietmapsdk.maps.Style
import vn.vietmap.vietmapsdk.style.expressions.Expression
import vn.vietmap.vietmapsdk.style.layers.Layer
import vn.vietmap.vietmapsdk.style.layers.Property
import vn.vietmap.vietmapsdk.utils.BitmapUtils
import vn.vietmap.vietmapsdk.utils.ColorUtils
import io.mockk.*
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class IndicatorLocationLayerRendererTest {

    private val style: vn.vietmap.vietmapsdk.maps.Style = mockk(relaxUnitFun = true)
    private val layerSourceProvider: vn.vietmap.vietmapsdk.location.LayerSourceProvider = mockk(relaxUnitFun = true)
    private val layer: vn.vietmap.vietmapsdk.style.layers.Layer = mockk(relaxUnitFun = true)

    private lateinit var locationLayerRenderer: vn.vietmap.vietmapsdk.location.IndicatorLocationLayerRenderer

    @Before
    fun setup() {
        every { style.removeLayer(any<vn.vietmap.vietmapsdk.style.layers.Layer>()) } returns true
        locationLayerRenderer =
            vn.vietmap.vietmapsdk.location.IndicatorLocationLayerRenderer(layerSourceProvider)
        every { layerSourceProvider.generateLocationComponentLayer() } returns layer
        locationLayerRenderer.initializeComponents(style)
    }

    @Test
    fun sanity() {
        Assert.assertNotNull(locationLayerRenderer)
    }

    @Test
    fun initializeComponents_withLocation() {
        val newLayer: vn.vietmap.vietmapsdk.style.layers.Layer = mockk(relaxUnitFun = true)
        every { layerSourceProvider.generateLocationComponentLayer() } returns newLayer
        val latLng = vn.vietmap.vietmapsdk.geometry.LatLng(10.0, 20.0)
        val bearing = 11f
        val accuracy = 65f
        locationLayerRenderer.setLatLng(latLng)
        locationLayerRenderer.setGpsBearing(bearing)
        locationLayerRenderer.setAccuracyRadius(accuracy)

        locationLayerRenderer.initializeComponents(mockk())

        verify { newLayer.setProperties(
            vn.vietmap.vietmapsdk.location.LocationPropertyFactory.location(
                latLng.toLocationArray()
            )
        ) }
        verify { newLayer.setProperties(
            vn.vietmap.vietmapsdk.location.LocationPropertyFactory.bearing(
                bearing.toDouble()
            )
        ) }
        verify { newLayer.setProperties(
            vn.vietmap.vietmapsdk.location.LocationPropertyFactory.accuracyRadius(
                accuracy
            )
        ) }
    }

    @Test
    fun addLayers() {
        val positionManager: vn.vietmap.vietmapsdk.location.LocationComponentPositionManager = mockk(relaxUnitFun = true)

        locationLayerRenderer.addLayers(positionManager)

        verify { positionManager.addLayerToMap(layer) }
    }

    @Test
    fun removeLayers() {
        locationLayerRenderer.removeLayers()

        verify { style.removeLayer(layer) }
    }

    @Test
    fun hide() {
        locationLayerRenderer.hide()

        verify { layer.setProperties(
            vn.vietmap.vietmapsdk.location.LocationPropertyFactory.visibility(
                vn.vietmap.vietmapsdk.style.layers.Property.NONE
            )
        ) }
    }

    @Test
    fun show() {
        locationLayerRenderer.show(vn.vietmap.vietmapsdk.location.modes.RenderMode.NORMAL, false)

        verify { layer.setProperties(
            vn.vietmap.vietmapsdk.location.LocationPropertyFactory.visibility(
                vn.vietmap.vietmapsdk.style.layers.Property.VISIBLE
            )
        ) }
    }

    @Test
    fun show_normal_notStale() {
        locationLayerRenderer.show(vn.vietmap.vietmapsdk.location.modes.RenderMode.NORMAL, false)

        verify {
            layer.setProperties(
                vn.vietmap.vietmapsdk.location.LocationPropertyFactory.topImage(FOREGROUND_ICON),
                vn.vietmap.vietmapsdk.location.LocationPropertyFactory.bearingImage(BACKGROUND_ICON),
                vn.vietmap.vietmapsdk.location.LocationPropertyFactory.shadowImage(SHADOW_ICON)
            )
        }
    }

    @Test
    fun show_compass_notStale() {
        locationLayerRenderer.show(vn.vietmap.vietmapsdk.location.modes.RenderMode.COMPASS, false)

        verify {
            layer.setProperties(
                vn.vietmap.vietmapsdk.location.LocationPropertyFactory.topImage(FOREGROUND_ICON),
                vn.vietmap.vietmapsdk.location.LocationPropertyFactory.bearingImage(BEARING_ICON),
                vn.vietmap.vietmapsdk.location.LocationPropertyFactory.shadowImage(SHADOW_ICON)
            )
        }
    }

    @Test
    fun show_gps_notStale() {
        locationLayerRenderer.show(vn.vietmap.vietmapsdk.location.modes.RenderMode.GPS, false)

        verify {
            layer.setProperties(
                vn.vietmap.vietmapsdk.location.LocationPropertyFactory.topImage(""),
                vn.vietmap.vietmapsdk.location.LocationPropertyFactory.bearingImage(FOREGROUND_ICON),
                vn.vietmap.vietmapsdk.location.LocationPropertyFactory.shadowImage(BACKGROUND_ICON)
            )
        }
        verify { layer.setProperties(
            vn.vietmap.vietmapsdk.location.LocationPropertyFactory.accuracyRadius(
                0f
            )
        ) }
    }

    @Test
    fun show_normal_stale() {
        locationLayerRenderer.show(vn.vietmap.vietmapsdk.location.modes.RenderMode.NORMAL, true)

        verify {
            layer.setProperties(
                vn.vietmap.vietmapsdk.location.LocationPropertyFactory.topImage(
                    FOREGROUND_STALE_ICON
                ),
                vn.vietmap.vietmapsdk.location.LocationPropertyFactory.bearingImage(
                    BACKGROUND_STALE_ICON
                ),
                vn.vietmap.vietmapsdk.location.LocationPropertyFactory.shadowImage(SHADOW_ICON)
            )
        }
    }

    @Test
    fun show_compass_stale() {
        locationLayerRenderer.show(vn.vietmap.vietmapsdk.location.modes.RenderMode.COMPASS, true)

        verify {
            layer.setProperties(
                vn.vietmap.vietmapsdk.location.LocationPropertyFactory.topImage(
                    FOREGROUND_STALE_ICON
                ),
                vn.vietmap.vietmapsdk.location.LocationPropertyFactory.bearingImage(
                    BEARING_STALE_ICON
                ),
                vn.vietmap.vietmapsdk.location.LocationPropertyFactory.shadowImage(SHADOW_ICON)
            )
        }
    }

    @Test
    fun show_gps_stale() {
        locationLayerRenderer.show(vn.vietmap.vietmapsdk.location.modes.RenderMode.GPS, true)

        verify {
            layer.setProperties(
                vn.vietmap.vietmapsdk.location.LocationPropertyFactory.topImage(""),
                vn.vietmap.vietmapsdk.location.LocationPropertyFactory.bearingImage(
                    FOREGROUND_STALE_ICON
                ),
                vn.vietmap.vietmapsdk.location.LocationPropertyFactory.shadowImage(
                    BACKGROUND_STALE_ICON
                )
            )
        }
        verify { layer.setProperties(
            vn.vietmap.vietmapsdk.location.LocationPropertyFactory.accuracyRadius(
                0f
            )
        ) }
    }

    @Test
    fun styleAccuracy() {
        val colorArray = vn.vietmap.vietmapsdk.utils.ColorUtils.colorToRgbaArray(Color.RED)
        val exp = vn.vietmap.vietmapsdk.style.expressions.Expression.rgba(colorArray[0], colorArray[1], colorArray[2], 0.7f)

        locationLayerRenderer.styleAccuracy(0.7f, Color.RED)

        verify {
            layer.setProperties(
                vn.vietmap.vietmapsdk.location.LocationPropertyFactory.accuracyRadiusColor(exp),
                vn.vietmap.vietmapsdk.location.LocationPropertyFactory.accuracyRadiusBorderColor(
                    exp
                )
            )
        }
    }

    @Test
    fun setLatLng() {
        val latLng = vn.vietmap.vietmapsdk.geometry.LatLng(10.0, 20.0)
        locationLayerRenderer.setLatLng(latLng)

        verify { layer.setProperties(
            vn.vietmap.vietmapsdk.location.LocationPropertyFactory.location(
                latLng.toLocationArray()
            )
        ) }
    }

    @Test
    fun setGpsBearing() {
        val bearing = 30.0
        locationLayerRenderer.setGpsBearing(bearing.toFloat())

        verify { layer.setProperties(
            vn.vietmap.vietmapsdk.location.LocationPropertyFactory.bearing(
                bearing
            )
        ) }
    }

    @Test
    fun setCompassBearing() {
        val bearing = 30.0
        locationLayerRenderer.setCompassBearing(bearing.toFloat())

        verify { layer.setProperties(
            vn.vietmap.vietmapsdk.location.LocationPropertyFactory.bearing(
                bearing
            )
        ) }
    }

    @Test
    fun setAccuracyRadius() {
        val radius = 40f
        locationLayerRenderer.setAccuracyRadius(radius)

        verify { layer.setProperties(
            vn.vietmap.vietmapsdk.location.LocationPropertyFactory.accuracyRadius(
                radius
            )
        ) }
    }

    @Test
    fun styleScaling() {
        val exp = vn.vietmap.vietmapsdk.style.expressions.Expression.literal("")
        locationLayerRenderer.styleScaling(exp)

        verify {
            layer.setProperties(
                vn.vietmap.vietmapsdk.location.LocationPropertyFactory.shadowImageSize(exp),
                vn.vietmap.vietmapsdk.location.LocationPropertyFactory.bearingImageSize(exp),
                vn.vietmap.vietmapsdk.location.LocationPropertyFactory.topImageSize(exp)
            )
        }
    }

    @Test
    fun setLocationStale() {
        locationLayerRenderer.setLocationStale(true, vn.vietmap.vietmapsdk.location.modes.RenderMode.NORMAL)

        verify {
            layer.setProperties(
                vn.vietmap.vietmapsdk.location.LocationPropertyFactory.topImage(
                    FOREGROUND_STALE_ICON
                ),
                vn.vietmap.vietmapsdk.location.LocationPropertyFactory.bearingImage(
                    BACKGROUND_STALE_ICON
                ),
                vn.vietmap.vietmapsdk.location.LocationPropertyFactory.shadowImage(SHADOW_ICON)
            )
        }
    }

    @Test
    fun addBitmaps_shadow() {
        addBitmaps(withShadow = true, renderMode = vn.vietmap.vietmapsdk.location.modes.RenderMode.NORMAL)

        verify { style.addImage(SHADOW_ICON, shadowBitmap) }
    }

    @Test
    fun addBitmaps_noShadow() {
        addBitmaps(withShadow = false, renderMode = vn.vietmap.vietmapsdk.location.modes.RenderMode.NORMAL)

        verify { style.removeImage(SHADOW_ICON) }
    }

    @Test
    fun addBitmaps_normal() {
        addBitmaps(withShadow = true, renderMode = vn.vietmap.vietmapsdk.location.modes.RenderMode.NORMAL)

        verify { style.addImage(FOREGROUND_ICON, foregroundBitmap) }
        verify { style.addImage(FOREGROUND_STALE_ICON, foregroundStaleBitmap) }
        verify { style.addImage(BACKGROUND_ICON, backgroundBitmap) }
        verify { style.addImage(BACKGROUND_STALE_ICON, backgroundStaleBitmap) }
        verify { style.addImage(BEARING_ICON, bearingBitmap) }
    }

    @Test
    fun addBitmaps_compass() {
        every { bearingBitmap.width } returns 40
        every { bearingBitmap.height } returns 40
        every { bearingBitmap.config } returns mockk()
        every { backgroundBitmap.width } returns 20
        every { backgroundBitmap.height } returns 10

        val mergedBitmap = mockk<Bitmap>()
        mockkStatic(vn.vietmap.vietmapsdk.utils.BitmapUtils::class)
        every { vn.vietmap.vietmapsdk.utils.BitmapUtils.mergeBitmap(bearingBitmap, backgroundBitmap, 10f, 15f) } returns mergedBitmap

        every { bearingBitmap.width } returns 40
        every { bearingBitmap.height } returns 40
        every { bearingBitmap.config } returns mockk()
        every { backgroundStaleBitmap.width } returns 30
        every { backgroundStaleBitmap.height } returns 10

        val mergedStaleBitmap = mockk<Bitmap>()
        every { vn.vietmap.vietmapsdk.utils.BitmapUtils.mergeBitmap(bearingBitmap, backgroundStaleBitmap, 5f, 15f) } returns mergedStaleBitmap

        addBitmaps(withShadow = true, renderMode = vn.vietmap.vietmapsdk.location.modes.RenderMode.COMPASS)

        verify { style.addImage(BEARING_ICON, mergedBitmap) }
        verify { style.addImage(BEARING_STALE_ICON, mergedStaleBitmap) }

        unmockkStatic(vn.vietmap.vietmapsdk.utils.BitmapUtils::class)
    }

    @Test
    fun addBitmaps_gps() {
        addBitmaps(withShadow = true, renderMode = vn.vietmap.vietmapsdk.location.modes.RenderMode.GPS)

        verify { style.addImage(FOREGROUND_ICON, foregroundBitmap) }
        verify { style.addImage(FOREGROUND_STALE_ICON, foregroundStaleBitmap) }
        verify { style.addImage(BACKGROUND_ICON, backgroundBitmap) }
        verify { style.addImage(BACKGROUND_STALE_ICON, backgroundStaleBitmap) }
        verify { style.addImage(BEARING_ICON, bearingBitmap) }
    }

    private val shadowBitmap = mockk<Bitmap>()
    private val backgroundBitmap = mockk<Bitmap>()
    private val backgroundStaleBitmap = mockk<Bitmap>()
    private val bearingBitmap = mockk<Bitmap>()
    private val foregroundBitmap = mockk<Bitmap>()
    private val foregroundStaleBitmap = mockk<Bitmap>()

    private fun addBitmaps(withShadow: Boolean, @vn.vietmap.vietmapsdk.location.modes.RenderMode.Mode renderMode: Int) {
        locationLayerRenderer.addBitmaps(
            renderMode,
            if (withShadow) shadowBitmap else null,
            backgroundBitmap,
            backgroundStaleBitmap,
            bearingBitmap,
            foregroundBitmap,
            foregroundStaleBitmap
        )
    }

    private fun vn.vietmap.vietmapsdk.geometry.LatLng.toLocationArray() = arrayOf(latitude, longitude, 0.0)
}
