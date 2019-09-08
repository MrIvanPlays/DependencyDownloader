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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

/** Represents general dependency */
public final class Dependency {

  private final String groupId;
  private final String artifactId;
  private final String version;
  private final String repoUrl;

  public Dependency(String groupId, String artifactId, String version, String repoUrl) {
    this.groupId = Objects.requireNonNull(groupId, "groupId");
    this.artifactId = Objects.requireNonNull(artifactId, "artifactId");
    this.version = Objects.requireNonNull(version, "version");
    this.repoUrl = Objects.requireNonNull(repoUrl, "repoUrl");
  }

  /**
   * Returns the group id of that dependency.
   *
   * @return group id
   */
  public String getGroupId() {
    return groupId;
  }

  /**
   * Returns the artifact id of that dependency.
   *
   * @return artifact id
   */
  public String getArtifactId() {
    return artifactId;
  }

  /**
   * Returns the version of that dependency
   *
   * @return version
   */
  public String getVersion() {
    return version;
  }

  /**
   * Returns the repository, from where the dependency's being downloaded.
   *
   * @return repository url
   */
  public String getRepoUrl() {
    return repoUrl;
  }

  /**
   * Returns the download url of the dependency.
   *
   * @return download url
   * @throws MalformedURLException (taken from {@link URL}: if no protocol is specified, or an
   *     unknown protocol is found, or {@code spec} is {@code null}.)
   */
  public URL getUrl() throws MalformedURLException {
    String repo = repoUrl;
    if (!repo.endsWith("/")) {
      repo += "/";
    }
    repo += "%s/%s/%s/%s-%s.jar";

    String url =
        String.format(repo, groupId.replace(".", "/"), artifactId, version, artifactId, version);
    return new URL(url);
  }

  @Override
  public String toString() {
    return "Dependency("
        + "groupId='"
        + groupId
        + '\''
        + ", artifactId='"
        + artifactId
        + '\''
        + ", version='"
        + version
        + '\''
        + ", repoUrl='"
        + repoUrl
        + '\''
        + ')';
  }
}
