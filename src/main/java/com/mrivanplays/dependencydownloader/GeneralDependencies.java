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

/**
 * Represents a enum with various general dependencies such as database drivers and utility
 * libraries which can be outside the main jar
 */
public enum GeneralDependencies {
  // database drivers
  MONGO_JAVA_DRIVER("org.mongodb", "mongo-java-driver", "3.9.1"),
  MONGO_MORPHIA("org.mongodb", "morphia", "1.3.2"),
  MARIADB_JAVA_CLIENT("org.mariadb.jdbc", "mariadb-java-client", "2.4.3"),
  JEDIS("redis.clients", "jedis", "3.1.0"),
  // discord bot stuff
  JDA("net.dv8tion", "jda", "4.0.0_46", "http://jcenter.bintray.com/"),
  JAVACORD("org.javacord", "javacord", "3.0.4"),
  DISCORDJ4(
      "com.discord4j",
      "discord4j-core",
      "3.0.8"), // discord4j really sucks. better using jda or javacord
  // google stuff
  GUICE("com.google.inject", "guice", "4.0.0"),
  GUAVA("com.google.guava", "guava", "28.1-jre"), // if you don't want to use the spigot one
  // asm stuff
  ASM("org.ow2.asm", "asm", "7.0"),
  ASM_ANALYSIS("org.ow2.asm", "asm-analysis", "7.0"),
  ASM_COMMONS("org.ow2.asm", "asm-commons", "7.0"),
  ASM_TREE("org.ow2.asm", "asm-tree", "7.0"),
  ASM_UTIL("org.ow2.asm", "asm-util", "7.0"),
  // specifically minecraft related stuff
  COMMODORE("me.lucko", "commodore", "1.3"),
  ADVANCEMENT_CREATOR("com.github.Trigary", "AdvancementCreator", "v2.0", "https://jitpack.io"),
  LIGHTNING_STORAGE("com.github.JavaFactoryDev", "LightningStorage", "2.3.8", "https://jitpack.io");

  private final String groupId;
  private final String artifactId;
  private final String version;
  private final String repositoryUrl;

  GeneralDependencies(String groupId, String artifactId, String version) {
    this(groupId, artifactId, version, "http://repo1.maven.org/maven2");
  }

  GeneralDependencies(String groupId, String artifactId, String version, String repositoryUrl) {
    this.groupId = groupId;
    this.artifactId = artifactId;
    this.version = version;
    this.repositoryUrl = repositoryUrl;
  }

  /**
   * Returns the credentials as a {@link Dependency} object.
   *
   * @return dependency
   */
  public Dependency asDependency() {
    return new Dependency(groupId, artifactId, version, repositoryUrl);
  }

  /**
   * Adds to download the specified dependency in the specified {@link LibraryInjector}
   *
   * @param injector the injector you want the dependency to be added
   */
  public void append(LibraryInjector injector) {
    injector.addToDownload(asDependency());
  }
}
