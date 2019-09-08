/*
    Copyright (c) 2019 Ivan Pekov
    Copyright (c) 2019 Contributors

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.
*/
package com.mrivanplays.dependencydownloader;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.plugin.java.JavaPlugin;

/** Represents a general library (dependency) injector. */
public final class LibraryInjector {

  private static final Method addURL;

  static {
    try {
      addURL = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
      addURL.setAccessible(true);
    } catch (NoSuchMethodException e) {
      throw new RuntimeException(e);
    }
  }

  private final List<Dependency> toDownload;
  private final File dependencyDir;
  private final JavaPlugin plugin;
  private Logger logger;

  /**
   * Creates a new library (dependency) injector
   *
   * @param plugin the plugin to which to inject the libraries
   * @param logDownloads should the injector log downloaded libraries
   */
  public LibraryInjector(JavaPlugin plugin, boolean logDownloads) {
    this.plugin = plugin;
    dependencyDir = new File(plugin.getDataFolder(), "dependencies");
    if (!dependencyDir.exists()) {
      dependencyDir.mkdirs();
    }
    toDownload = new ArrayList<>();
    if (logDownloads) {
      logger = plugin.getLogger();
    }
  }

  public LibraryInjector(JavaPlugin plugin) {
    this(plugin, true);
  }

  /**
   * Searches for the {@link MavenLibrary} annotation in the specified object and adds the found for
   * download.
   *
   * @param clazzObject the object to search for dependency annotations
   */
  public void addToDownload(Object clazzObject) {
    addToDownload(clazzObject.getClass());
  }

  /**
   * Searches for the {@link MavenLibrary} annotation in the specified class object and adds the
   * found for download.
   *
   * @param clazz the class object to search for dependency annotations
   */
  public void addToDownload(Class<?> clazz) {
    MavenLibrary[] libs = clazz.getDeclaredAnnotationsByType(MavenLibrary.class);
    if (libs == null) {
      return;
    }
    for (MavenLibrary library : libs) {
      addToDownload(library.groupId(), library.artifactId(), library.version(), library.repoUrl());
    }
  }

  /**
   * Adds for download the specified dependency parameters.
   *
   * @param groupId the dependency's group id
   * @param artifactId the dependency's artifact id
   * @param version the dependency's version
   */
  public void addToDownload(String groupId, String artifactId, String version) {
    addToDownload(groupId, artifactId, version, "http://repo1.maven.org/maven2");
  }

  /**
   * Adds for download the specified dependency parameters.
   *
   * @param groupId the dependency's group id
   * @param artifactId the dependency's artifact id
   * @param version the dependency's version
   * @param repoUrl the dependency's repository url
   */
  public void addToDownload(String groupId, String artifactId, String version, String repoUrl) {
    addToDownload(new Dependency(groupId, artifactId, version, repoUrl));
  }

  /**
   * Adds to download the specified {@link Dependency}
   *
   * @param dependency the dependency you wish to download
   */
  public void addToDownload(Dependency dependency) {
    if (!toDownload.contains(dependency)) {
      toDownload.add(dependency);
    }
  }

  /**
   * Returns a unmodifiable collection of all dependencies which were downloaded or are to be
   * downloaded.
   *
   * @return dependencies
   */
  public List<Dependency> getDependencies() {
    return Collections.unmodifiableList(toDownload);
  }

  /**
   * Downloads the specified dependencies if they're not present and loads them into the specified
   * plugin's classpath. Make sure to call this after you've added <b>all</b> the dependencies you
   * wish to download.
   */
  public void downloadAndInject() {
    for (Dependency dependency : toDownload) {
      log(
          String.format(
              "Loading dependency %s:%s:%s from %s",
              dependency.getGroupId(),
              dependency.getArtifactId(),
              dependency.getVersion(),
              dependency.getRepoUrl()));
      String name = dependency.getArtifactId() + "-" + dependency.getVersion();
      File saveLocation = new File(dependencyDir, name + ".jar");
      boolean wasExisting = true;
      if (!saveLocation.exists()) {
        wasExisting = false;
        log("Dependency '" + name + "' is not downloaded. Attempting to download...");
        try {
          URL downloadUrl = dependency.getUrl();

          try (InputStream in = downloadUrl.openStream()) {
            Files.copy(in, saveLocation.toPath());
          }

        } catch (Throwable t) {
          t.printStackTrace();
        }
      }

      if (!saveLocation.exists()) {
        throw new RuntimeException("Unable to download dependency: " + dependency.toString());
      }

      if (!wasExisting) {
        log("Successfully downloaded dependency: " + name);
      }

      URLClassLoader classLoader = (URLClassLoader) plugin.getClass().getClassLoader();
      try {
        addURL.invoke(classLoader, saveLocation.toURI().toURL());
      } catch (Throwable t) {
        throw new RuntimeException("Unable to load dependency: " + saveLocation.toString(), t);
      }

      log("Loaded dependency " + name + " successfully");
    }
  }

  private void log(String message) {
    if (logger != null) {
      logger.log(Level.INFO, message);
    }
  }
}
