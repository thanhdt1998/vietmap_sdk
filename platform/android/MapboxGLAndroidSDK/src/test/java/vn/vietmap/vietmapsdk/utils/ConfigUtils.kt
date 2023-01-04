package vn.vietmap.vietmapsdk.utils

import vn.vietmap.vietmapsdk.util.DefaultStyle
import vn.vietmap.vietmapsdk.util.TileServerOptions

class ConfigUtils {
    companion object {
        @JvmStatic
        fun getMockedOptions(): vn.vietmap.vietmapsdk.util.TileServerOptions {
            val defaultStyles = arrayOf(
                vn.vietmap.vietmapsdk.util.DefaultStyle("maptiler://maps/streets", "Streets", 1),
                vn.vietmap.vietmapsdk.util.DefaultStyle("maptiler://maps/outdoor", "Outdoor", 1),
                vn.vietmap.vietmapsdk.util.DefaultStyle("maptiler://maps/basic", "Basic", 1),
                vn.vietmap.vietmapsdk.util.DefaultStyle("maptiler://maps/bright", "Bright", 1),
                vn.vietmap.vietmapsdk.util.DefaultStyle("maptiler://maps/pastel", "Pastel", 1),
                vn.vietmap.vietmapsdk.util.DefaultStyle(
                    "maptiler://maps/hybrid",
                    "Satellite Hybrid",
                    1
                ),
                vn.vietmap.vietmapsdk.util.DefaultStyle(
                    "maptiler://maps/topo",
                    "Satellite Topo",
                    1
                )
            )
            val defaultStyle = defaultStyles[0].name

            return vn.vietmap.vietmapsdk.util.TileServerOptions(
                "https://api.maptiler.com",
                "maptiler",
                "{path}",
                "sources",
                null,
                "/maps{path}/style.json",
                "maps",
                null,
                "/maps{path}",
                "sprites",
                null,
                "/fonts{path}",
                "fonts",
                null,
                "{path}",
                "tiles",
                null,
                "key",
                true,
                defaultStyle,
                defaultStyles
            )
        }
    }
}
