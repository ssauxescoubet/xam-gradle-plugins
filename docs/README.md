Xamarin gradle plugins
==========

# Build status

[![Build Status](https://travis-ci.org/oliviergauthier/xam-gradle-plugins.svg?branch=master)](https://travis-ci.org/oliviergauthier/xam-gradle-plugins)

# Description

Set of [Gradle](https://gradle.org/) plugins to build Xamarin mobile application (iOS/Android) and librairies from cli.

* **[xamarin-application-plugin](#xamarin-application-plugin) :** Build ios/android Application
* **[xamarin-library-plugin](#xamarin-library-plugin) :** Build/Package/Deploy Nuget Library
* **[xamarin-test-plugin](#xamarin-nunit-plugin) :** Build and run nunit test

# Documentation :

See following projects for working samples
- [SampleApp](https://github.com/oliviergauthier/xam-gradle-plugins-sample-app)
- [SampleLib](https://github.com/oliviergauthier/xam-gradle-plugins-sample-lib)

## Xamarin Application Plugin 

This plugin provides the ability to build iOS and Android Xamarin Applications with gradle.

**Android Application:**
* Update versionCode, storeVersion and packageName in AndroidManifest
* Build apk

**Ios Application:**
* Update version, shortVersion and bundleIdentifier in Info.plist
* Build ipa

**Tasks :**
- buildIOS : build iOS application
- buildAndroid : build Android Application

**Notes :**
* By default, use first solution file found in project folder
* By default, use Release configuration

###Minimum requirement

```groovy
buildscript {
   repositories {
       mavenLocal()
   }
   dependencies {
        classpath 'com.betomorrow.gradle:xamarin-application-plugin:1.0-SNAPSHOT'
   }
}

apply plugin: 'xamarin-application-plugin'
```

### Full DSL

```groovy
buildscript {
   repositories {
       mavenLocal()
   }
   dependencies {
        classpath 'com.betomorrow.gradle:xamarin-application-plugin:1.0-SNAPSHOT'
   }
}

apply plugin: 'xamarin-application-plugin'

application {

    // global settings
    solution = "SampleApp.sln"          // default : First solution file found in project folder
    configuration = "Release"           // default : Release
    appName = "SampleApp"               // default : project.name
    appVersion = "42"                   // versionName (Android) / shortVersion (iOS)
    storeVersion = "1.0"                // versionCode (Android) / version (iOS)
    packageName = "com.acme.SampleApp"  // packageName (Android) / bundleIdentifier (iOS)

    android {
        appName = "SampleApp.Droid"                         // default : Droid or first android project in solution 
        output = "dist/SampleApp.apk"                       // default : dist/<appName>-<appVersion>.apk
        manifest = "Droid/Properties/AndroidManifest.xml"   // default : <Android Project Dir>/Properties/AndroidManifest.xml
        projectFile = "Droid/SampleApp.Droid.csproj"        // default : csproj of project name <appName>
        appVersion = 42                                     // (read-only) default : inherited from global settings
        storeVersion = "1.0"                                // (read-only) default : inherited from global settings
        packageName = "com.acme.SampleApp"                  // (read-only) default : inherited from global settings
    } 

    ios {
        appName = "SampleApp.iOS"                           // default : Droid or first android project in solution 
        output = "dist/SampleApp.ipa"                       // default : dist/<appName>-<appVersion>.apk
        infoPlist = "iOS/Properties/Info.plist"             // default : <Android Project Dir>/Properties/AndroidManifest.xml
        projectFile = "iOS/SampleApp.iOS.csproj"                // default : csproj of project name <appName>
        bundleShortVersion = "1.0"                          // (read-only) default : inherited from global settings
        bundleVersion = "1.0"                               // (read-only) default : inherited from global settings
        bundleIdentifier = "com.acme.SampleApp"             // (read-only) default : inherited from global settings
        platform = "iPhone"                                 // default : iphone
    }
}
```


## Xamarin NUnit Plugin 

This plugin provides the ability to build and run NUnit test

**Tasks :**
- compileTest : build test with xbuild
- test : run nunit-console

**Notes :**
* By default, use first solution file found in project folder
* Download and use NunitConsole3.5

```
buildscript {
    repositories {
        mavenLocal()
    }
    dependencies {
        classpath 'com.betomorrow.gradle:xamarin-nunit-plugin:1.0-SNAPSHOT'
    }
}

apply plugin: 'xamarin-nunit-plugin'

nunit {
    assemblies = "path/to/assemblies.Test.dll"  // (optional) array or string, full path of test assemblies
    projects = "CrossLib.Test"                  // (optional) By default, use "${project.name}.Test"
    format = "nunit2"                           // Result test format
}

```

## Install
Install in local repository 
```bash
./gradlew install
```

## Deploy
Update `gradle.properties` file with your own url, user and password 
```bash
./gradlew publish
```
