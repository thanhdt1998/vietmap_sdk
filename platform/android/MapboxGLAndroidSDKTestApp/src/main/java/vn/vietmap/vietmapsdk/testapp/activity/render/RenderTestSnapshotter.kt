package vn.vietmap.vietmapsdk.testapp.activity.render

import android.content.Context
import vn.vietmap.vietmapsdk.snapshotter.MapSnapshot
import vn.vietmap.vietmapsdk.snapshotter.MapSnapshotter

class RenderTestSnapshotter internal constructor(context: Context, options: Options) :
    vn.vietmap.vietmapsdk.snapshotter.MapSnapshotter(context, options) {
    override fun addOverlay(mapSnapshot: vn.vietmap.vietmapsdk.snapshotter.MapSnapshot) {
        // don't add an overlay
    }
}
