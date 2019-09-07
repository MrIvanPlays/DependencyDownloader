# DependencyDownloader
Stop staring at 23 MB flat jars! Download dependencies without making your jar flat!

# Installation
I am going to show you with maven cuz I'm not familiar with gradle.

```html
  <repositories>
    <repository>
      <id>spigotmc-repo</id>
      <url>https://hub.spigotmc.org/nexus/content/groups/public/</url>
    </repository>
    <repository>
      <id>sonatype</id>
      <url>https://oss.sonatype.org/content/groups/public/</url>
    </repository>
    <repository>
      <id>mrivanplays</id>
      <url>https://dl.bintray.com/mrivanplaysbg/mrivanplays/</url>
    </repository>
  </repositories>

  <dependencies>
    <dependency>
      <groupId>org.spigotmc</groupId>
      <artifactId>spigot-api</artifactId>
      <version>1.14.4-R0.1-SNAPSHOT</version>
      <scope>provided</scope>
    </dependency>
    <dependency>
      <groupId>com.mrivanplays</groupId>
      <artifactId>DependencyDownloader</artifactId>
      <version>VERSION</version> <!-- Replace with latest -->
      <scope>compile</scope>
    </dependency>
  </dependencies>
```

Then you should relocate it
```html
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-shade-plugin</artifactId>
        <version>3.1.0</version>
        <executions>
          <execution>
            <phase>package</phase>
            <goals>
              <goal>shade</goal>
            </goals>
            <configuration>
              <createDependencyReducedPom>false</createDependencyReducedPom>
              <relocations>
                <relocation>
                  <pattern>com.mrivanplays.depdencydownloader</pattern>
                  <shadedPattern>[YOUR_PACKAGE].dependencydownloader</shadedPattern>
                </relocation>
              </relocations>
            </configuration>
          </execution>
        </executions>
      </plugin>
```

# Usage

Using the library is pretty easy
```java
@MavenLibrary(groupId = "org.mongodb", artifactId = "mongo-java-driver", version = "3.4.2")
public class MyMainClass extends JavaPlugin {

    @Override
    public void onLoad() {
       LibraryInjector dependencies = new LibraryInjector(this);
       dependencies.addToDownload(this);
       // if you're having in other class the "MavenLibrary" annotation
       // make sure to call "dependencies.addToDownload(yourClassInstance)"
       // else the dependency won't be added for download and won't get downloaded
       dependencies.downloadAndInject();
    }
}
```
There! Now your dependencies will be added to the classpath when the plugin's ran.

## KEEP IN MIND THAT
This library will only add the dependencies to the classpath when the plugin's being ran
on a server. In order to use them, you should depend with `<scope>provided</scope>` on them
into your pom.