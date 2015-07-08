# Featuros Gradle plugin

This plugin will prepare "featuros.properties" metadata file, that is used by
featuros application plugin for registering application to featuros server.

The generated properties file will be added to application jar.

## Usage

Build plugin locally:

```
./gradlew :featuros-gradle-plugin:publishToMavenLocal
```

Add buildscript dependency:

```
buildscript {
    repositories {
        mavenLocal()
    }
    dependencies {
        classpath 'io.fourfinanceit.featuros:featuros-gradle-plugin:0.1'
    }
}
```

In your project configuration closure, apply the plugin:

```
apply plugin: 'io.fourfinanceit.featuros'
```

Configure the featuros metadata metadata:

```
    featuros {
        name = 'my-cool-microservice'
        product = 'cool-microservice'
        group = 'my'
        version = project.version // or just static string if project version is not set
    }
```