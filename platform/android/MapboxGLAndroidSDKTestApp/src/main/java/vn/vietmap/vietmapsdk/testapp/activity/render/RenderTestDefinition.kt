package vn.vietmap.vietmapsdk.testapp.activity.render

import vn.vietmap.vietmapsdk.maps.Style
import vn.vietmap.vietmapsdk.snapshotter.MapSnapshotter

class RenderTestDefinition internal constructor( // eg. background-color
    val category: String?, // eg. colorSpace-hcl
    val name: String,
    val styleJson: String?,
    private val definition: RenderTestStyleDefinition
) {
    val width: Int
        get() {
            val test = test
            if (test != null) {
                val testWidth = test.width
                if (testWidth != null && testWidth > 0) {
                    return testWidth
                }
            }
            return DEFAULT_WIDTH
        }
    val height: Int
        get() {
            val test = test
            if (test != null) {
                val testHeight = test.height
                if (testHeight != null && testHeight > 0) {
                    return testHeight
                }
            }
            return DEFAULT_HEIGHT
        }
    val pixelRatio: Float
        get() {
            val test = test
            if (test != null) {
                val pixelRatio = test.pixelRatio
                if (pixelRatio != null && pixelRatio > 0) {
                    return pixelRatio
                }
            }
            return 1F
        }

    fun hasOperations(): Boolean {
        return test?.operations != null
    }

    val test: RenderTestStyleDefinition.Test?
        get() = definition.metadata?.test

    fun toOptions(): vn.vietmap.vietmapsdk.snapshotter.MapSnapshotter.Options {
        return vn.vietmap.vietmapsdk.snapshotter.MapSnapshotter.Options(width, height)
            .withStyleBuilder(vn.vietmap.vietmapsdk.maps.Style.Builder().fromJson(styleJson!!))
            .withPixelRatio(pixelRatio)
            .withLogo(false)
    }

    override fun toString(): String {
        return (
            "RenderTestDefinition{" +
                "category='" + category + '\'' +
                ", name='" + name + '\'' +
                ", styleJson='" + styleJson + '\'' +
                '}'
            )
    }

    companion object {
        private const val DEFAULT_WIDTH = 512
        private const val DEFAULT_HEIGHT = 512
    }
}
