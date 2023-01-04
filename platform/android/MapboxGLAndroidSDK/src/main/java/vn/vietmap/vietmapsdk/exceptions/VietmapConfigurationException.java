package vn.vietmap.vietmapsdk.exceptions;

import android.content.Context;

import androidx.annotation.NonNull;

import vn.vietmap.vietmapsdk.Vietmap;

/**
 * A VietmapConfigurationException is thrown by VietmapMap when the SDK hasn't been properly initialised.
 * <p>
 * This occurs either when {@link Vietmap} is not correctly initialised or the provided apiKey
 * through {@link Vietmap#getInstance(Context, String)} isn't valid.
 * </p>
 *
 * @see Vietmap#getInstance(Context, String)
 */
public class VietmapConfigurationException extends RuntimeException {

  /**
   * Creates a Vietmap configuration exception thrown by VietmapMap when the SDK hasn't been properly initialised.
   */
  public VietmapConfigurationException() {
    super("\nUsing MapView requires calling Vietmap.getInstance(Context context, String apiKey) before "
      + "inflating or creating the view.");
  }

  /**
   * Creates a Vietmap configuration exception thrown by VietmapMap when the SDK hasn't been properly initialised.
   */
  public VietmapConfigurationException(@NonNull String message) {
    super(message);
  }
}
