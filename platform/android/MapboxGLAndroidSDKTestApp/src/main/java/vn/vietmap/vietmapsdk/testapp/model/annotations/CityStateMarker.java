package vn.vietmap.vietmapsdk.testapp.model.annotations;

import vn.vietmap.vietmapsdk.annotations.Marker;

public class CityStateMarker extends Marker {

  private String infoWindowBackgroundColor;

  public CityStateMarker(CityStateMarkerOptions cityStateOptions, String color) {
    super(cityStateOptions);
    infoWindowBackgroundColor = color;
  }

  public String getInfoWindowBackgroundColor() {
    return infoWindowBackgroundColor;
  }

}
