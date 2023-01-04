package vn.vietmap.vietmapsdk.testapp.utils;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import timber.log.Timber;

import static vn.vietmap.vietmapsdk.testapp.activity.offline.OfflineActivity.JSON_CHARSET;
import static vn.vietmap.vietmapsdk.testapp.activity.offline.OfflineActivity.JSON_FIELD_REGION_NAME;

public class OfflineUtils {

  public static String convertRegionName(@NonNull byte[] metadata) {
    try {
      String json = new String(metadata, JSON_CHARSET);
      JsonObject jsonObject = new Gson().fromJson(json, JsonObject.class);
      String name = jsonObject.get(JSON_FIELD_REGION_NAME).getAsString();
      return name != null ? name : "";
    } catch (Exception exception) {
      return "";
    }
  }

  public static byte[] convertRegionName(String regionName) {
    try {
      JsonObject jsonObject = new JsonObject();
      jsonObject.addProperty(JSON_FIELD_REGION_NAME, regionName);
      return jsonObject.toString().getBytes(JSON_CHARSET);
    } catch (Exception exception) {
      Timber.e(exception, "Failed to encode metadata: ");
    }
    return null;
  }

}
