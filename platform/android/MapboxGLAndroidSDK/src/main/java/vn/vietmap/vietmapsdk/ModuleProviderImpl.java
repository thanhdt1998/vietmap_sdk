package vn.vietmap.vietmapsdk;

import androidx.annotation.NonNull;

import vn.vietmap.vietmapsdk.http.HttpRequest;
import vn.vietmap.vietmapsdk.module.http.HttpRequestImpl;
import vn.vietmap.vietmapsdk.module.loader.LibraryLoaderProviderImpl;
import vn.vietmap.vietmapsdk.http.HttpRequest;
import vn.vietmap.vietmapsdk.module.http.HttpRequestImpl;
import vn.vietmap.vietmapsdk.module.loader.LibraryLoaderProviderImpl;
import vn.vietmap.vietmapsdk.http.HttpRequest;
import vn.vietmap.vietmapsdk.module.http.HttpRequestImpl;
import vn.vietmap.vietmapsdk.module.loader.LibraryLoaderProviderImpl;

public class ModuleProviderImpl implements ModuleProvider {

  @Override
  @NonNull
  public HttpRequest createHttpRequest() {
    return new HttpRequestImpl();
  }

  @NonNull
  @Override
  public LibraryLoaderProvider createLibraryLoaderProvider() {
    return new LibraryLoaderProviderImpl();
  }
}
