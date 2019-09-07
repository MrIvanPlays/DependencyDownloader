/*
    Copyright (C) 2019 Ivan Pekov

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as published
    by the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
*/
package com.mrivanplays.dependencydownloader;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

/**
 * Represents general dependency
 */
public final class Dependency {

    private final String groupId;
    private final String artifactId;
    private final String version;
    private final String repoUrl;

    public Dependency(
            String groupId,
            String artifactId,
            String version,
            String repoUrl
    ) {
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
     * unknown protocol is found, or {@code spec} is {@code null}.)
     */
    public URL getUrl() throws MalformedURLException {
        String repo = repoUrl;
        if (!repo.endsWith("/")) {
            repo += "/";
        }
        repo += "%s/%s/%s/%s-%s.jar";

        String url = String.format(
                repo,
                groupId.replace(".", "/"),
                artifactId,
                version,
                artifactId,
                version
        );
        return new URL(url);
    }

    @Override
    public String toString() {
        return "Dependency(" +
                "groupId='" + groupId + '\'' +
                ", artifactId='" + artifactId + '\'' +
                ", version='" + version + '\'' +
                ", repoUrl='" + repoUrl + '\'' +
                ')';
    }
}
