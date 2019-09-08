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

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** Annotation which defines a maven library in the specified class. */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MavenLibrary {

  /**
   * The group id of the library (ex. "org.spigotmc")
   *
   * @return group id
   */
  String groupId();

  /**
   * The artifact id of the library (ex. "spigot-api")
   *
   * @return artifact id
   */
  String artifactId();

  /**
   * The version of the library (ex. "1.14.4-R0.1-SNAPSHOT")
   *
   * @return version
   */
  String version();

  /**
   * The repository url of where to find the library. By default that's the maven central
   * repository.
   *
   * @return repository url
   */
  String repoUrl() default "http://repo1.maven.org/maven2";
}
