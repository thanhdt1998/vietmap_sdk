package vn.vietmap.vietmapsdk.maps

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.ShapeDrawable
import vn.vietmap.vietmapsdk.VietmapInjector
import vn.vietmap.vietmapsdk.constants.VietmapConstants
import vn.vietmap.vietmapsdk.style.layers.CannotAddLayerException
import vn.vietmap.vietmapsdk.style.layers.SymbolLayer
import vn.vietmap.vietmapsdk.style.layers.TransitionOptions
import vn.vietmap.vietmapsdk.style.sources.CannotAddSourceException
import vn.vietmap.vietmapsdk.style.sources.GeoJsonSource
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import vn.vietmap.vietmapsdk.utils.ConfigUtils

@RunWith(RobolectricTestRunner::class)
class StyleTest {

    private lateinit var VietmapMap: vn.vietmap.vietmapsdk.maps.VietmapMap

    private lateinit var nativeMapView: vn.vietmap.vietmapsdk.maps.NativeMap

    @Mock
    private lateinit var context: Context

    @Mock
    private lateinit var appContext: Context

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        vn.vietmap.vietmapsdk.VietmapInjector.inject(context, "abcdef", ConfigUtils.getMockedOptions())
        nativeMapView = mockk(relaxed = true)
        VietmapMap =
            vn.vietmap.vietmapsdk.maps.VietmapMap(nativeMapView, null, null, null, null, null, null)
        every { nativeMapView.isDestroyed } returns false
        VietmapMap.injectLocationComponent(spyk())
    }

    @Test
    fun testFromUrl() {
        val builder = vn.vietmap.vietmapsdk.maps.Style.Builder().fromUrl(vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Streets"))
        VietmapMap.setStyle(builder)
        verify(exactly = 1) { nativeMapView.styleUri = vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Streets") }
    }

    @Test
    fun testFromJson() {
        val builder = vn.vietmap.vietmapsdk.maps.Style.Builder().fromJson("{}")
        VietmapMap.setStyle(builder)
        verify(exactly = 1) { nativeMapView.styleJson = "{}" }
    }

    @Test
    fun testEmptyBuilder() {
        val builder = vn.vietmap.vietmapsdk.maps.Style.Builder()
        VietmapMap.setStyle(builder)
        verify(exactly = 1) { nativeMapView.styleJson = vn.vietmap.vietmapsdk.maps.Style.EMPTY_JSON }
    }

    @Test
    fun testWithLayer() {
        val layer = mockk<vn.vietmap.vietmapsdk.style.layers.SymbolLayer>()
        every { layer.id } returns "1"
        val builder = vn.vietmap.vietmapsdk.maps.Style.Builder().withLayer(layer)
        VietmapMap.setStyle(builder)
        VietmapMap.onFinishLoadingStyle()
        verify(exactly = 1) {
            nativeMapView.addLayerBelow(
                layer,
                vn.vietmap.vietmapsdk.constants.VietmapConstants.LAYER_ID_ANNOTATIONS
            )
        }
    }

    @Test
    fun testWithLayerAbove() {
        val layer = mockk<vn.vietmap.vietmapsdk.style.layers.SymbolLayer>()
        every { layer.id } returns "1"
        val builder = vn.vietmap.vietmapsdk.maps.Style.Builder().withLayerAbove(layer, "id")
        VietmapMap.setStyle(builder)
        VietmapMap.onFinishLoadingStyle()
        verify(exactly = 1) { nativeMapView.addLayerAbove(layer, "id") }
    }

    @Test
    fun testWithLayerBelow() {
        val layer = mockk<vn.vietmap.vietmapsdk.style.layers.SymbolLayer>()
        every { layer.id } returns "1"
        val builder = vn.vietmap.vietmapsdk.maps.Style.Builder().withLayerBelow(layer, "id")
        VietmapMap.setStyle(builder)
        VietmapMap.onFinishLoadingStyle()
        verify(exactly = 1) { nativeMapView.addLayerBelow(layer, "id") }
    }

    @Test
    fun testWithLayerAt() {
        val layer = mockk<vn.vietmap.vietmapsdk.style.layers.SymbolLayer>()
        every { layer.id } returns "1"
        val builder = vn.vietmap.vietmapsdk.maps.Style.Builder().withLayerAt(layer, 1)
        VietmapMap.setStyle(builder)
        VietmapMap.onFinishLoadingStyle()
        verify(exactly = 1) { nativeMapView.addLayerAt(layer, 1) }
    }

    @Test
    fun testWithSource() {
        val source = mockk<vn.vietmap.vietmapsdk.style.sources.GeoJsonSource>()
        every { source.id } returns "1"
        val builder = vn.vietmap.vietmapsdk.maps.Style.Builder().withSource(source)
        VietmapMap.setStyle(builder)
        VietmapMap.onFinishLoadingStyle()
        verify(exactly = 1) { nativeMapView.addSource(source) }
    }

    @Test
    fun testWithTransitionOptions() {
        val transitionOptions = vn.vietmap.vietmapsdk.style.layers.TransitionOptions(100, 200)
        val builder = vn.vietmap.vietmapsdk.maps.Style.Builder().withTransition(transitionOptions)
        VietmapMap.setStyle(builder)
        VietmapMap.onFinishLoadingStyle()
        verify(exactly = 1) { nativeMapView.transitionOptions = transitionOptions }
    }

    @Test
    fun testWithFromLoadingSource() {
        val source = mockk<vn.vietmap.vietmapsdk.style.sources.GeoJsonSource>()
        every { source.id } returns "1"
        val builder =
            vn.vietmap.vietmapsdk.maps.Style.Builder().fromUrl(vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Streets")).withSource(source)
        VietmapMap.setStyle(builder)
        verify(exactly = 1) { nativeMapView.styleUri = vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Streets") }
        VietmapMap.notifyStyleLoaded()
        verify(exactly = 1) { nativeMapView.addSource(source) }
    }

    @Test
    fun testWithFromLoadingLayer() {
        val layer = mockk<vn.vietmap.vietmapsdk.style.layers.SymbolLayer>()
        every { layer.id } returns "1"
        val builder = vn.vietmap.vietmapsdk.maps.Style.Builder().fromUrl(vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Streets")).withLayer(layer)
        VietmapMap.setStyle(builder)
        verify(exactly = 1) { nativeMapView.styleUri = vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Streets") }
        VietmapMap.notifyStyleLoaded()
        verify(exactly = 1) {
            nativeMapView.addLayerBelow(
                layer,
                vn.vietmap.vietmapsdk.constants.VietmapConstants.LAYER_ID_ANNOTATIONS
            )
        }
    }

    @Test
    fun testWithFromLoadingLayerAt() {
        val layer = mockk<vn.vietmap.vietmapsdk.style.layers.SymbolLayer>()
        every { layer.id } returns "1"
        val builder =
            vn.vietmap.vietmapsdk.maps.Style.Builder().fromUrl(vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Streets")).withLayerAt(layer, 1)
        VietmapMap.setStyle(builder)
        verify(exactly = 1) { nativeMapView.styleUri = vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Streets") }
        VietmapMap.notifyStyleLoaded()
        verify(exactly = 1) { nativeMapView.addLayerAt(layer, 1) }
    }

    @Test
    fun testWithFromLoadingLayerBelow() {
        val layer = mockk<vn.vietmap.vietmapsdk.style.layers.SymbolLayer>()
        every { layer.id } returns "1"
        val builder = vn.vietmap.vietmapsdk.maps.Style.Builder().fromUrl(vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Streets"))
            .withLayerBelow(layer, "below")
        VietmapMap.setStyle(builder)
        verify(exactly = 1) { nativeMapView.styleUri = vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Streets") }
        VietmapMap.notifyStyleLoaded()
        verify(exactly = 1) { nativeMapView.addLayerBelow(layer, "below") }
    }

    @Test
    fun testWithFromLoadingLayerAbove() {
        val layer = mockk<vn.vietmap.vietmapsdk.style.layers.SymbolLayer>()
        every { layer.id } returns "1"
        val builder = vn.vietmap.vietmapsdk.maps.Style.Builder().fromUrl(vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Streets"))
            .withLayerBelow(layer, "below")
        VietmapMap.setStyle(builder)
        verify(exactly = 1) { nativeMapView.styleUri = vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Streets") }
        VietmapMap.notifyStyleLoaded()
        verify(exactly = 1) { nativeMapView.addLayerBelow(layer, "below") }
    }

    @Test
    fun testWithFromLoadingTransitionOptions() {
        val transitionOptions = vn.vietmap.vietmapsdk.style.layers.TransitionOptions(100, 200)
        val builder = vn.vietmap.vietmapsdk.maps.Style.Builder().fromUrl(vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Streets"))
            .withTransition(transitionOptions)
        VietmapMap.setStyle(builder)
        verify(exactly = 1) { nativeMapView.styleUri = vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Streets") }
        VietmapMap.notifyStyleLoaded()
        verify(exactly = 1) { nativeMapView.transitionOptions = transitionOptions }
    }

    @Test
    fun testFromCallback() {
        val callback = mockk<vn.vietmap.vietmapsdk.maps.Style.OnStyleLoaded>()
        every { callback.onStyleLoaded(any()) } answers {}
        val builder = vn.vietmap.vietmapsdk.maps.Style.Builder().fromUrl(vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Streets"))
        VietmapMap.setStyle(builder, callback)
        verify(exactly = 1) { nativeMapView.styleUri = vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Streets") }
        VietmapMap.notifyStyleLoaded()
        verify(exactly = 1) { callback.onStyleLoaded(any()) }
    }

    @Test
    fun testWithCallback() {
        val callback = mockk<vn.vietmap.vietmapsdk.maps.Style.OnStyleLoaded>()
        every { callback.onStyleLoaded(any()) } answers {}
        val source = mockk<vn.vietmap.vietmapsdk.style.sources.GeoJsonSource>()
        every { source.id } returns "1"
        val builder = vn.vietmap.vietmapsdk.maps.Style.Builder().withSource(source)
        VietmapMap.setStyle(builder, callback)
        VietmapMap.onFinishLoadingStyle()
        verify(exactly = 1) { nativeMapView.addSource(source) }
        verify(exactly = 1) { callback.onStyleLoaded(any()) }
    }

    @Test
    fun testGetAsyncWith() {
        val callback = mockk<vn.vietmap.vietmapsdk.maps.Style.OnStyleLoaded>()
        every { callback.onStyleLoaded(any()) } answers {}
        VietmapMap.getStyle(callback)
        val source = mockk<vn.vietmap.vietmapsdk.style.sources.GeoJsonSource>()
        every { source.id } returns "1"
        val builder = vn.vietmap.vietmapsdk.maps.Style.Builder().withSource(source)
        VietmapMap.setStyle(builder)
        VietmapMap.onFinishLoadingStyle()
        verify(exactly = 1) { nativeMapView.addSource(source) }
        verify(exactly = 1) { callback.onStyleLoaded(any()) }
    }

    @Test
    fun testGetAsyncFrom() {
        val callback = mockk<vn.vietmap.vietmapsdk.maps.Style.OnStyleLoaded>()
        every { callback.onStyleLoaded(any()) } answers {}
        VietmapMap.getStyle(callback)
        val source = mockk<vn.vietmap.vietmapsdk.style.sources.GeoJsonSource>()
        every { source.id } returns "1"
        val builder = vn.vietmap.vietmapsdk.maps.Style.Builder().fromJson("{}")
        VietmapMap.setStyle(builder)
        verify(exactly = 1) { nativeMapView.styleJson = "{}" }
        VietmapMap.notifyStyleLoaded()
        verify(exactly = 1) { callback.onStyleLoaded(any()) }
    }

    @Test
    fun testGetAsyncWithFrom() {
        val callback = mockk<vn.vietmap.vietmapsdk.maps.Style.OnStyleLoaded>()
        every { callback.onStyleLoaded(any()) } answers {}
        VietmapMap.getStyle(callback)
        val source = mockk<vn.vietmap.vietmapsdk.style.sources.GeoJsonSource>()
        every { source.id } returns "1"
        val builder =
            vn.vietmap.vietmapsdk.maps.Style.Builder().fromUrl(vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Streets")).withSource(source)
        VietmapMap.setStyle(builder)
        verify(exactly = 1) { nativeMapView.styleUri = vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Streets") }
        VietmapMap.notifyStyleLoaded()
        verify(exactly = 1) { nativeMapView.addSource(source) }
        verify(exactly = 1) { callback.onStyleLoaded(any()) }
    }

    @Test
    fun testGetNullStyle() {
        Assert.assertNull(VietmapMap.style)
    }

    @Test
    fun testGetNullWhileLoading() {
        val transitionOptions = vn.vietmap.vietmapsdk.style.layers.TransitionOptions(100, 200)
        val builder = vn.vietmap.vietmapsdk.maps.Style.Builder().fromUrl(vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Streets"))
            .withTransition(transitionOptions)
        VietmapMap.setStyle(builder)
        Assert.assertNull(VietmapMap.style)
        VietmapMap.notifyStyleLoaded()
        Assert.assertNotNull(VietmapMap.style)
    }

    @Test
    fun testNotReinvokeSameListener() {
        val callback = mockk<vn.vietmap.vietmapsdk.maps.Style.OnStyleLoaded>()
        every { callback.onStyleLoaded(any()) } answers {}
        VietmapMap.getStyle(callback)
        val source = mockk<vn.vietmap.vietmapsdk.style.sources.GeoJsonSource>()
        every { source.id } returns "1"
        val builder = vn.vietmap.vietmapsdk.maps.Style.Builder().fromJson("{}")
        VietmapMap.setStyle(builder)
        verify(exactly = 1) { nativeMapView.styleJson = "{}" }
        VietmapMap.notifyStyleLoaded()
        VietmapMap.setStyle(vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Streets"))
        verify(exactly = 1) { callback.onStyleLoaded(any()) }
    }

    @Test(expected = IllegalStateException::class)
    fun testIllegalStateExceptionWithStyleReload() {
        val builder = vn.vietmap.vietmapsdk.maps.Style.Builder().fromUrl(vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Streets"))
        VietmapMap.setStyle(builder)
        VietmapMap.notifyStyleLoaded()
        val style = VietmapMap.style
        VietmapMap.setStyle(vn.vietmap.vietmapsdk.maps.Style.Builder().fromUrl(vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Bright")))
        style!!.addLayer(mockk<vn.vietmap.vietmapsdk.style.layers.SymbolLayer>())
    }

    @Test
    fun testAddImage() {
        val bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
        val builder =
            vn.vietmap.vietmapsdk.maps.Style.Builder().fromUrl(vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Satellite Hybrid")).withImage("id", bitmap)
        VietmapMap.setStyle(builder)
        verify(exactly = 1) { nativeMapView.styleUri = vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Satellite Hybrid") }
        verify(exactly = 0) { nativeMapView.addImages(any()) }
        VietmapMap.notifyStyleLoaded()
        verify(exactly = 1) { nativeMapView.addImages(any()) }
    }

    @Test
    fun testAddDrawable() {
        val drawable = ShapeDrawable()
        drawable.intrinsicHeight = 10
        drawable.intrinsicWidth = 10
        val builder =
            vn.vietmap.vietmapsdk.maps.Style.Builder().fromUrl(vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Satellite Hybrid")).withImage("id", drawable)
        VietmapMap.setStyle(builder)
        verify(exactly = 1) { nativeMapView.styleUri = vn.vietmap.vietmapsdk.maps.Style.getPredefinedStyle("Satellite Hybrid") }
        verify(exactly = 0) { nativeMapView.addImages(any()) }
        VietmapMap.notifyStyleLoaded()
        verify(exactly = 1) { nativeMapView.addImages(any()) }
    }

    @Test
    fun testSourceSkippedIfAdditionFails() {
        val source1 = mockk<vn.vietmap.vietmapsdk.style.sources.GeoJsonSource>(relaxed = true)
        every { source1.id } returns "source1"
        val source2 = mockk<vn.vietmap.vietmapsdk.style.sources.GeoJsonSource>(relaxed = true)
        every { source2.id } returns "source1" // same ID

        val builder = vn.vietmap.vietmapsdk.maps.Style.Builder().withSource(source1)
        VietmapMap.setStyle(builder)
        VietmapMap.notifyStyleLoaded()

        every { nativeMapView.addSource(any()) } throws vn.vietmap.vietmapsdk.style.sources.CannotAddSourceException(
            "Duplicate ID"
        )

        try {
            VietmapMap.style!!.addSource(source2)
        } catch (ex: Exception) {
            Assert.assertEquals(
                "Source that failed to be added shouldn't be cached",
                source1,
                VietmapMap.style!!.getSource("source1")
            )
        }
    }

    @Test
    fun testLayerSkippedIfAdditionFails() {
        val layer1 = mockk<vn.vietmap.vietmapsdk.style.layers.SymbolLayer>(relaxed = true)
        every { layer1.id } returns "layer1"
        val layer2 = mockk<vn.vietmap.vietmapsdk.style.layers.SymbolLayer>(relaxed = true)
        every { layer2.id } returns "layer1" // same ID

        val builder = vn.vietmap.vietmapsdk.maps.Style.Builder().withLayer(layer1)
        VietmapMap.setStyle(builder)
        VietmapMap.notifyStyleLoaded()

        every { nativeMapView.addLayer(any()) } throws vn.vietmap.vietmapsdk.style.layers.CannotAddLayerException(
            "Duplicate ID"
        )

        try {
            VietmapMap.style!!.addLayer(layer2)
        } catch (ex: Exception) {
            Assert.assertEquals(
                "Layer that failed to be added shouldn't be cached",
                layer1,
                VietmapMap.style!!.getLayer("layer1")
            )
        }
    }

    @Test
    fun testLayerSkippedIfAdditionBelowFails() {
        val layer1 = mockk<vn.vietmap.vietmapsdk.style.layers.SymbolLayer>(relaxed = true)
        every { layer1.id } returns "layer1"
        val layer2 = mockk<vn.vietmap.vietmapsdk.style.layers.SymbolLayer>(relaxed = true)
        every { layer2.id } returns "layer1" // same ID

        val builder = vn.vietmap.vietmapsdk.maps.Style.Builder().withLayer(layer1)
        VietmapMap.setStyle(builder)
        VietmapMap.notifyStyleLoaded()

        every {
            nativeMapView.addLayerBelow(
                any(),
                ""
            )
        } throws vn.vietmap.vietmapsdk.style.layers.CannotAddLayerException("Duplicate ID")

        try {
            VietmapMap.style!!.addLayerBelow(layer2, "")
        } catch (ex: Exception) {
            Assert.assertEquals(
                "Layer that failed to be added shouldn't be cached",
                layer1,
                VietmapMap.style!!.getLayer("layer1")
            )
        }
    }

    @Test
    fun testLayerSkippedIfAdditionAboveFails() {
        val layer1 = mockk<vn.vietmap.vietmapsdk.style.layers.SymbolLayer>(relaxed = true)
        every { layer1.id } returns "layer1"
        val layer2 = mockk<vn.vietmap.vietmapsdk.style.layers.SymbolLayer>(relaxed = true)
        every { layer2.id } returns "layer1" // same ID

        val builder = vn.vietmap.vietmapsdk.maps.Style.Builder().withLayer(layer1)
        VietmapMap.setStyle(builder)
        VietmapMap.notifyStyleLoaded()

        every {
            nativeMapView.addLayerAbove(
                any(),
                ""
            )
        } throws vn.vietmap.vietmapsdk.style.layers.CannotAddLayerException("Duplicate ID")

        try {
            VietmapMap.style!!.addLayerAbove(layer2, "")
        } catch (ex: Exception) {
            Assert.assertEquals(
                "Layer that failed to be added shouldn't be cached",
                layer1,
                VietmapMap.style!!.getLayer("layer1")
            )
        }
    }

    @Test
    fun testLayerSkippedIfAdditionAtFails() {
        val layer1 = mockk<vn.vietmap.vietmapsdk.style.layers.SymbolLayer>(relaxed = true)
        every { layer1.id } returns "layer1"
        val layer2 = mockk<vn.vietmap.vietmapsdk.style.layers.SymbolLayer>(relaxed = true)
        every { layer2.id } returns "layer1" // same ID

        val builder = vn.vietmap.vietmapsdk.maps.Style.Builder().withLayer(layer1)
        VietmapMap.setStyle(builder)
        VietmapMap.notifyStyleLoaded()

        every { nativeMapView.addLayerAt(any(), 5) } throws vn.vietmap.vietmapsdk.style.layers.CannotAddLayerException(
            "Duplicate ID"
        )

        try {
            VietmapMap.style!!.addLayerAt(layer2, 5)
        } catch (ex: Exception) {
            Assert.assertEquals(
                "Layer that failed to be added shouldn't be cached",
                layer1,
                VietmapMap.style!!.getLayer("layer1")
            )
        }
    }
}
