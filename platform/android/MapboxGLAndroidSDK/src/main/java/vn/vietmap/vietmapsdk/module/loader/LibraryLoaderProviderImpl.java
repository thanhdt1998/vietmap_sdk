package vn.vietmap.vietmapsdk.module.loader;

import vn.vietmap.vietmapsdk.LibraryLoader;
import vn.vietmap.vietmapsdk.LibraryLoaderProvider;

/**
 * Concrete implementation of a native library loader.
 * <p>
 * </p>
 */
public class LibraryLoaderProviderImpl implements LibraryLoaderProvider {

  /**
   * Creates and returns a the default Library Loader.
   *
   * @return the default library loader
   */
  @Override
  public LibraryLoader getDefaultLibraryLoader() {
    return new SystemLibraryLoader();
  }

  /**
   * Concrete implementation of a LibraryLoader using System.loadLibrary.
   */
  private static class SystemLibraryLoader extends LibraryLoader {
    @Override
    public void load(String name) {
      System.loadLibrary(name);
    }
  }
}
