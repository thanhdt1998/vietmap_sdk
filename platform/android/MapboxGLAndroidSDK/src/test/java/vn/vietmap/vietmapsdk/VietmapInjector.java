package vn.vietmap.vietmapsdk;

import android.content.Context;

import androidx.annotation.NonNull;

import vn.vietmap.vietmapsdk.util.TileServerOptions;
import vn.vietmap.vietmapsdk.util.TileServerOptions;

import java.lang.reflect.Field;

import vn.vietmap.vietmapsdk.util.TileServerOptions;

public class VietmapInjector {

  private static final String FIELD_INSTANCE = "INSTANCE";

  public static void inject(@NonNull Context context, @NonNull String apiKey,
                            @NonNull TileServerOptions options) {
    Vietmap mapbox = new Vietmap(context, apiKey, options);
    try {
      Field instance = Vietmap.class.getDeclaredField(FIELD_INSTANCE);
      instance.setAccessible(true);
      instance.set(mapbox, mapbox);
    } catch (Exception exception) {
      throw new AssertionError();
    }
  }

  public static void clear() {
    try {
      Field field = Vietmap.class.getDeclaredField(FIELD_INSTANCE);
      field.setAccessible(true);
      field.set(field, null);
    } catch (Exception exception) {
      throw new AssertionError();
    }
  }
}
