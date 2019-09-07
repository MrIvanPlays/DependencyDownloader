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

/**
 * Represents a enum with various general dependencies such as
 * database drivers and utility libraries which can be outside
 * the main jar
 */
public enum GeneralDependencies {
    MONGO_JAVA_DRIVER("org.mongodb", "mongo-java-driver", "3.9.1"),
    MONGO_MORPHIA("org.mongodb", "morphia", "1.3.2"),
    MARIADB_JAVA_CLIENT("org.mariadb.jdbc", "mariadb-java-client", "2.4.3"),
    JEDIS("redis.clients", "jedis", "3.1.0");

    private final String groupId;
    private final String artifactId;
    private final String version;

    GeneralDependencies(
            String groupId,
            String artifactId,
            String version
    ) {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
    }

    /**
     * Returns the credentials as a {@link Dependency} object.
     *
     * @return dependency
     */
    public Dependency asDependency() {
        return new Dependency(groupId, artifactId, version, "http://repo1.maven.org/maven2");
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
