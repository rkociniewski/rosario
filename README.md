# 📿 Rosario – Android Prayer App

[![version](https://img.shields.io/badge/version-1.0.0-yellow.svg)](https://semver.org)
[![Awesome Kotlin Badge](https://kotlin.link/awesome-kotlin.svg)](https://github.com/KotlinBy/awesome-kotlin)
[![Build](https://github.com/rkociniewski/prime-checker/actions/workflows/main.yml/badge.svg)](https://github.com/rkociniewski/prime-checker/actions/workflows/main.yml)
[![codecov](https://codecov.io/gh/rkociniewski/prime-checker/branch/main/graph/badge.svg)](https://codecov.io/gh/rkociniewski/prime-checker)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.1.21-blueviolet?logo=kotlin)](https://kotlinlang.org/)
[![Gradle](https://img.shields.io/badge/Gradle-9.1.0-blue?logo=gradle)](https://gradle.org/)
[![License: MIT](https://img.shields.io/badge/License-MIT-greem.svg)](https://opensource.org/licenses/MIT)

Rosario is a modern Android application built with Kotlin and Jetpack Compose, offering an intuitive and accessible
interface for praying the **Rosary**, **Divine Mercy Chaplet**, and **Jesus Prayer (Chotka)**. It supports multiple
languages and personalization options, while keeping a clean and distraction-free design.

## ✨ Features

* 🔁 **Three prayer types**: Rosary, Divine Mercy, and Jesus Prayer (Chotka)
* 🌐 **Multi-language support**: Automatically detects system language (e.g., EN, PL, FR), with manual override
* 🌗 **Display modes**: Light, Dark, and System settings
* 🎛️ **Prayer navigation**: Tap, Buttons, or Both
* 🧭 **Custom prayer text positioning**: Top or Bottom
* 🧠 **State persistence**: Saves current position and preferences using `DataStore`
* 🌱 **Jetpack Compose** UI and modern Android architecture (ViewModel, Hilt, Coroutines)

## 🧬 Bead Structure

Each prayer type defines a list of `Bead` objects, which form the prayer sequence. Each bead includes:

```kotlin
data class Bead(
    val index: Int,
    val type: BeadType,
    @get:StringRes
    val prayerId: Int = 0
)
```

* `type` – Describes the function and appearance of the bead (e.g. `CROSS`, `BEAD_LARGE`, `BEAD_SMALL`, `TAIL_LARGE`,
  `TAIL_SMALL`)
* `prayerId` – A non-zero ID links the bead to a specific prayer text
* Beads with `prayerId = 0` are skipped in navigation

Beads are generated dynamically in the ViewModel based on the selected `PrayerType`.

## 🚀 Getting Started

### Requirements

* Android Studio Hedgehog or later
* Android 8.0+ (API 26+)
* Kotlin 2.2.20
* Gradle 9.10

### Installation

1. Clone the repository:

   ```bash
   git clone git@github.com:rkociniewski/rosario.git
   cd rosario
   ```

2. Open in Android Studio
3. Sync Gradle and run the app on an emulator or physical device

## 🔧 Architecture

* **MVVM** using `ViewModel`, `StateFlow`, and `DataStore`
* **Hilt** for dependency injection
* **Raw Resources** for localized prayer text (`res/raw`)
* **Custom enum classes** for Prayer types, Bead roles, and UI settings

## 🗂 Project Structure

```
📦pl.rk.rosario
 ┣ 📁data           # Data loading utilities (prayer text loader)
 ┣ 📁enums          # App-specific enums (language, prayer type, etc.)
 ┣ 📁model          # Data models (Bead, Settings)
 ┣ 📁storage        # DataStore access for Settings
 ┣ 📁ui             # UI components and screens (Rosary, Settings)
 ┣ 📁viewModel      # RosaryViewModel (app logic & state)
 ┗ 📜MainActivity.kt
```

## 🌍 Localization

* Prayer texts and UI are fully localized
* To add a new language:
    * Translate strings in `res/values-<lang>/strings.xml`
    * Add corresponding `.json` files in `res/raw/`
    * Add the language to the `Language` enum and map its locale

## License

This project is licensed under the MIT License.

## Built With

* [Gradle](https://gradle.org/) - Dependency Management

## Versioning

We use [SemVer](http://semver.org/) for versioning.

## Authors

* **Rafał Kociniewski**: [PowerMilk](https://github.com/rkociniewski)
