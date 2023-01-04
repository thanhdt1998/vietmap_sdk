package vn.vietmap.vietmapsdk.maps;


import androidx.annotation.NonNull;

import vn.vietmap.vietmapsdk.annotations.Annotation;
import vn.vietmap.vietmapsdk.annotations.Annotation;

import java.util.List;

import vn.vietmap.vietmapsdk.annotations.Annotation;

/**
 * Interface that defines convenient methods for working with a {@link Annotation}'s collection.
 */
interface Annotations {
  Annotation obtainBy(long id);

  List<Annotation> obtainAll();

  void removeBy(long id);

  void removeBy(@NonNull Annotation annotation);

  void removeBy(@NonNull List<? extends Annotation> annotationList);

  void removeAll();
}
