package vn.vietmap.vietmapsdk.module.http

import vn.vietmap.vietmapsdk.VietmapInjector
import vn.vietmap.vietmapsdk.utils.ConfigUtils
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import okhttp3.OkHttpClient
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class HttpRequestUtilTest {

    @Test
    fun replaceHttpClient() {
        vn.vietmap.vietmapsdk.VietmapInjector.inject(mockk(relaxed = true), "", ConfigUtils.getMockedOptions())

        assertEquals(vn.vietmap.vietmapsdk.module.http.HttpRequestImpl.DEFAULT_CLIENT, vn.vietmap.vietmapsdk.module.http.HttpRequestImpl.client)

        val httpMock = mockk<OkHttpClient>()
        vn.vietmap.vietmapsdk.module.http.HttpRequestUtil.setOkHttpClient(httpMock)
        assertEquals(
            "Http client should have set to the mocked client",
            httpMock,
            vn.vietmap.vietmapsdk.module.http.HttpRequestImpl.client
        )

        vn.vietmap.vietmapsdk.module.http.HttpRequestUtil.setOkHttpClient(null)
        assertEquals(
            "Http client should have been reset to the default client",
            vn.vietmap.vietmapsdk.module.http.HttpRequestImpl.DEFAULT_CLIENT,
            vn.vietmap.vietmapsdk.module.http.HttpRequestImpl.client
        )

        vn.vietmap.vietmapsdk.VietmapInjector.clear()
    }
}
