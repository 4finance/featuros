[![Build Status](https://travis-ci.org/4finance/featuros.svg)](https://travis-ci.org/4finance/featuros)

# featuros

Overview application features across multiple deployments. Within a blink of an eye, identify deployment dates, versions and available features in dozens or even hundreds of deployed applications.

`featuros` is a young and a work-in-progress project. It will be released publicly as soon as it is production-ready!


## Install

`$ ./gradlew build`

## Use

First step is to start featuros backend dashboard:

`$ ./gradlew featuros:start`

Then you can choose a way to provide metadata:

### Option 1: plugin (recommended)

- Apply and configure [featuros-gradle-plugin] in `gradle` build file
- Add dependency on [featuros-plugin]:

```
repositories {
    mavenLocal()
}
dependencies {
    compile 'io.fourfinanceit.featuros:featuros-plugin:+'
}
```

- Call this **once** from your application code:

```
Featuros.notification();
```

### Option 2: curl

- Post from deployment or startup script using `curl`:

```
$ curl -d name=my-cool-microservice \
    -d product=cool-microservice \
    -d group=my \
    -d version=1.0 \
    http://localhost:31113/api/notifications
```

## Run the sample project

- Run featuros backend:

`$ ./gradlew featuros:run`

- Open featuros dashboard in your browser: [http://localhost:31113](http://localhost:31113)
- Run sample application:

`$ ./gradlew featuros-example:run`

- Refresh the dashboard and see the information of a new deployment

## How it works

During an application build, `featuros-gradle-plugin` prepares metadata and bundles it within the application JAR/WAR. When the application starts, `featuros-plugin` posts this metadata to `featuros` backend so that it knows about the new deployment and its features.

Featuros metadata is as follows:

| Field     | Description |
| --------- | ----------- |
| name      | Final name of a deployable, usually including product and group, e.g. `xx-backend-europe` |
| product   | Product name, e.g. `xx-backend` |
| group     | Group of deployment, e.g. `europe` |
| version   | Version |

## Understanding the modules

| Module                   | Description                                                          |
| ------------------------ | -------------------------------------------------------------------- |
| featuros                 | Backend application with deployment overview                         |
| [featuros-gradle-plugin] | Hooks into your gradle build process and prepares featuros metadata  |
| featuros-plugin          | Plugin for your application to publish metadata to featuros backend  |
| featuros-example         | Sample application integrated with featuros                          |

## Project goals

- Easy to use overview interface: filter, sort and analyze like a boss
- Zero integration effort: pluggable with the most popular frameworks and platforms
- Lightweight

## Contributing

TBD

## Licensing

featuros is licensed under MIT.

[featuros-gradle-plugin]: https://github.com/4finance/featuros/tree/master/featuros-gradle-plugin  "featuros-gradle-plugin"
[featuros-plugin]: https://github.com/4finance/featuros/tree/master/featuros-plugin  "featuros-plugin"
