# 📿 Rosario – Android Prayer App

[![version](https://img.shields.io/badge/version-1.7.1-yellow.svg)](https://semver.org)
[![Awesome Kotlin Badge](https://kotlin.link/awesome-kotlin.svg)](https://github.com/KotlinBy/awesome-kotlin)
[![Build](https://github.com/rkociniewski/rosario/actions/workflows/main.yml/badge.svg)](https://github.com/rkociniewski/rosario/actions/workflows/main.yml)
[![CodeQL](https://github.com/rkociniewski/rosario/actions/workflows/codeql.yml/badge.svg)](https://github.com/rkociniewski/rosario/actions/workflows/codeql.yml)
[![Dependabot Status](https://img.shields.io/badge/Dependabot-enabled-success?logo=dependabot)](https://github.com/rkociniewski/rosario/network/updates)
[![codecov](https://codecov.io/gh/rkociniewski/rosario/branch/main/graph/badge.svg)](https://codecov.io/gh/rkociniewski/rosario)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.2.20-blueviolet?logo=kotlin)](https://kotlinlang.org/)
[![Gradle](https://img.shields.io/badge/Gradle-9.1.0-blue?logo=gradle)](https://gradle.org/)
[![License: MIT](https://img.shields.io/badge/License-MIT-green.svg)](https://opensource.org/licenses/MIT)

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
* 🔒 **Security-first**: CodeQL scanning and automated security updates

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

3. Install Git hooks (for commit validation):

   ```bash
   ./scripts/setup-git-hooks.sh
   ```

4. Sync Gradle and run the app on an emulator or physical device

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

## 🔐 Security

This project implements multiple security measures:

* **CodeQL Analysis** - Automated security vulnerability scanning on every PR
* **Dependabot** - Automatic dependency updates and security patches
* **Git Hooks** - Pre-commit checks for secrets, code quality, and conventional commits
* **Secret Scanning** - Prevents accidental credential commits

For security issues, please see [SECURITY.md](docs/SECURITY.md)

## 🤖 CI/CD Pipeline

### Automated Workflows

* **Build & Test** - Runs on every push and PR
* **CodeQL Security Scan** - Weekly security analysis (Mondays 2 AM)
* **Dependabot** - Weekly dependency updates (Mondays 9 AM)
* **UI Tests** - Automated on device emulators (API 28, 33, 34)
* **Release** - Automatic version tagging and GitHub releases

### Git Workflow

This project follows [Git Flow](https://nvie.com/posts/a-successful-git-branching-model/) with automated checks:

```bash
main         # Production releases
  ↑
release/*    # Release candidates
  ↑
develop      # Development branch
  ↑
feature/*    # Feature branches
```

**Commit Message Format**: We use [Conventional Commits](https://www.conventionalcommits.org/)

```bash
feat: Add new prayer type
fix: Resolve crash on rotation
docs: Update README
ci: Update GitHub Actions workflows
```

See [GIT_HOOKS.md](docs/GIT_HOOKS.md) for details.

## 🛠️ Development

### Running Tests

```bash
# Unit tests
./gradlew test

# UI tests (requires emulator/device)
./gradlew connectedCheck

# Code quality
./gradlew detekt
./gradlew ktlintCheck
```

### Code Quality

* **ktlint** - Kotlin code style
* **detekt** - Static code analysis
* **CodeQL** - Security vulnerability detection

### Pre-commit Checks

Git hooks automatically check:
- ✅ Commit message format (Conventional Commits)
- ✅ No debug statements (excluding Logger classes)
- ✅ No secrets in code
- ✅ No large files (>5MB)
- ✅ No merge conflicts

Bypass only in emergencies:
```bash
git commit --no-verify -m "hotfix: Critical fix"
```

## 📦 Dependencies

Major dependencies are automatically updated by Dependabot:

* **Jetpack Compose** - Modern Android UI
* **Hilt** - Dependency injection
* **DataStore** - Settings persistence
* **Coroutines** - Async operations
* **Material 3** - Material Design components

## 📊 Project Status

![Build Status](https://github.com/rkociniewski/rosario/actions/workflows/main.yml/badge.svg)
![Security](https://github.com/rkociniewski/rosario/actions/workflows/codeql.yml/badge.svg)
![Dependencies](https://img.shields.io/badge/dependencies-up%20to%20date-success)

**Development Status**: Active
**Code Coverage**: ![codecov](https://codecov.io/gh/rkociniewski/rosario/branch/main/graph/badge.svg)

## 🤝 Contributing

Contributions are welcome! Please:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Follow commit conventions (`feat: Add amazing feature`)
4. Ensure all tests pass
5. Submit a Pull Request

See [CONTRIBUTING.md](CONTRIBUTING.md) for detailed guidelines.

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🏗️ Built With

* [Kotlin](https://kotlinlang.org/) - Programming language
* [Jetpack Compose](https://developer.android.com/jetpack/compose) - Modern UI toolkit
* [Hilt](https://dagger.dev/hilt/) - Dependency injection
* [Gradle](https://gradle.org/) - Build system
* [GitHub Actions](https://github.com/features/actions) - CI/CD

## 📋 Versioning

We use [Semantic Versioning](http://semver.org/) for versioning.

Version format: `MAJOR.MINOR.PATCH`
- **MAJOR**: Breaking changes
- **MINOR**: New features (backward compatible)
- **PATCH**: Bug fixes

## 👨‍💻 Authors

* **Rafał Kociniewski** - [rkociniewski](https://github.com/rkociniewski)

See also the list of [contributors](https://github.com/rkociniewski/rosario/contributors) who participated in this project.

## 🙏 Acknowledgments

* Prayer texts sourced from traditional Catholic sources
* Icons and design inspired by traditional rosary beads
* Built with modern Android best practices

## 📚 Documentation

* [Git Hooks Guide](docs/GIT_HOOKS.md)
* [CodeQL Security](docs/CODEQL.md)
* [Security Policy](docs/SECURITY.md)

## 📞 Support

* **Issues**: [GitHub Issues](https://github.com/rkociniewski/rosario/issues)
* **Discussions**: [GitHub Discussions](https://github.com/rkociniewski/rosario/discussions)
* **Security**: [Security Policy](docs/SECURITY.md)

---

Made with ❤️ and 🙏 by [Rafał Kociniewski](https://github.com/rkociniewski)
