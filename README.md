# eggeum-android

[![Kotlin](https://img.shields.io/badge/Kotlin-1.9.23-blue.svg)](https://kotlinlang.org)
[![Gradle](https://img.shields.io/badge/gradle-8.7-green.svg)](https://gradle.org/)
[![Android Studio](https://img.shields.io/badge/Android%20Studio-2023.2.1%20%28Iguana%29-green)](https://developer.android.com/studio)
[![minSdkVersion](https://img.shields.io/badge/minSdkVersion-24-red)](https://developer.android.com/distribute/best-practices/develop/target-sdk)
[![targetSdkVersion](https://img.shields.io/badge/targetSdkVersion-34-orange)](https://developer.android.com/distribute/best-practices/develop/target-sdk)
<br/>

이끔 - 나에게 맞는 카페찾기 [PlayStore](https://play.google.com/store/apps/details?id=us.wedemy.eggeum.android)

![eggeum_graphic_image](https://github.com/Wedemy/eggeum-android/assets/51016231/94422e61-10a6-426a-9829-fc81cd827321)

<p align="center">
<img src="https://github.com/Wedemy/eggeum-android/assets/51016231/ca54c3ac-cf85-4df8-9d3d-adc0369708bc" width="30%"/>
<img src="https://github.com/Wedemy/eggeum-android/assets/51016231/6862943e-e235-4f4b-b0f6-a58d08b98cef" width="30%"/>
<img src="https://github.com/Wedemy/eggeum-android/assets/51016231/03c0dffd-f4ac-427e-a50b-08c2a4818bd4" width="30%"/>
</p>

## Features

## Development

### Required

- IDE : Android Studio Iguana
- JDK : Java 17을 실행할 수 있는 JDK
- Kotlin Language : 1.9.23

### Language

- Kotlin

### Libraries

- AndroidX
  - Activity
  - Core
  - Lifecycle & ViewModel
  - Navigation
  - DataStore
  - Room
  - StartUp
  - Splash
  - Paging3

- Kotlin Libraries (Coroutine, Serialization)

- Dagger Hilt
- Retrofit, OkHttp
- Timber
- Coil
- [Naver-Map](https://github.com/fornewid/naver-map-compose)
- [PhotoView](https://github.com/Baseflow/PhotoView)
- Lottie
- Firebase(Analytics, Crashlytics)

#### Test & Code analysis

- Ktlint
- Detekt

#### Gradle Dependency

- Gradle Version Catalog

## Architecture
Clean Architecture 

## Package Structure
```
├── app
│   └── application
├── build-logic
├── buildSrc
├── common
├── data
├── design-resource
├── domain
├── gradle
│   └── libs.versions.toml
├── login
├── main
├── navigator
├── onboard
├── register-cafe
└── update-cafe
```
<br/>
