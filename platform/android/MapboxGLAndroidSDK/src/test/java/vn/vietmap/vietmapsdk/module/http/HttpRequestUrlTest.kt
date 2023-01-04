package vn.vietmap.vietmapsdk.module.http

import vn.vietmap.vietmapsdk.VietmapInjector
import vn.vietmap.vietmapsdk.http.HttpRequestUrl
import vn.vietmap.vietmapsdk.utils.ConfigUtils
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class HttpRequestUrlTest {

    @Before
    fun setUp() {
        vn.vietmap.vietmapsdk.VietmapInjector.inject(mockk(relaxed = true), "pk.foobar", ConfigUtils.getMockedOptions())
    }

    @Test
    fun testOfflineFlagMapboxCom() {
        val expected = "http://mapbox.com/path/of/no/return.pbf?offline=true"
        val actual = vn.vietmap.vietmapsdk.http.HttpRequestUrl.buildResourceUrl("mapbox.com", "http://mapbox.com/path/of/no/return.pbf", 0, true)
        assertEquals(expected, actual)
    }

    @Test
    fun testOfflineFlagMapboxCn() {
        val expected = "http://mapbox.cn/path/of/no/return.pbf?offline=true"
        val actual = vn.vietmap.vietmapsdk.http.HttpRequestUrl.buildResourceUrl("mapbox.cn", "http://mapbox.cn/path/of/no/return.pbf", 0, true)
        assertEquals(expected, actual)
    }

    @Test
    fun testOfflineFlagInvalidHost() {
        val expected = "http://foobar.com/path/of/no/return.pbf"
        val actual = vn.vietmap.vietmapsdk.http.HttpRequestUrl.buildResourceUrl("foobar.com", "http://foobar.com/path/of/no/return.pbf", 0, true)
        assertEquals(expected, actual)
    }

    @Test
    fun testOnlineInvalidHost() {
        val expected = "http://foobar.com/path/of/no/return.pbf"
        val actual = vn.vietmap.vietmapsdk.http.HttpRequestUrl.buildResourceUrl("foobar.com", "http://foobar.com/path/of/no/return.pbf", 0, false)
        assertEquals(expected, actual)
    }

    @After
    fun tearDown() {
        vn.vietmap.vietmapsdk.VietmapInjector.clear()
    }
}
