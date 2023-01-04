package vn.vietmap.vietmapsdk.utils;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import vn.vietmap.vietmapsdk.constants.VietmapConstants;
import vn.vietmap.vietmapsdk.maps.MapFragment;
import vn.vietmap.vietmapsdk.maps.VietMapOptions;
import vn.vietmap.vietmapsdk.constants.VietmapConstants;
import vn.vietmap.vietmapsdk.maps.SupportMapFragment;
import vn.vietmap.vietmapsdk.constants.VietmapConstants;

/**
 * MapFragment utility class.
 * <p>
 * Used to extract duplicate code between {@link MapFragment} and
 * {@link SupportMapFragment}.
 * </p>
 */
public class MapFragmentUtils {

  /**
   * Convert VietmapMapOptions to a bundle of fragment arguments.
   *
   * @param options The VietmapMapOptions to convert
   * @return a bundle of converted fragment arguments
   */
  @NonNull
  public static Bundle createFragmentArgs(VietMapOptions options) {
    Bundle bundle = new Bundle();
    bundle.putParcelable(VietmapConstants.FRAG_ARG_VietmapMapOPTIONS, options);
    return bundle;
  }

  /**
   * Convert a bundle of fragment arguments to VietMapOptions.
   *
   * @param context The context of the activity hosting the fragment
   * @param args    The fragment arguments
   * @return converted VietMapOptions
   */
  @Nullable
  public static VietMapOptions resolveArgs(@NonNull Context context, @Nullable Bundle args) {
    VietMapOptions options;
    if (args != null && args.containsKey(VietmapConstants.FRAG_ARG_VietmapMapOPTIONS)) {
      options = args.getParcelable(VietmapConstants.FRAG_ARG_VietmapMapOPTIONS);
    } else {
      // load default options
      options = VietMapOptions.createFromAttributes(context);
    }
    return options;
  }
}
