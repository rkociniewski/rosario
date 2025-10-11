# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/), and this project adheres
to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.7.1] - 2025-10-11

### ADDED

- Documentation for contributing (CONTRIBUTING.md)

### CHANGED

- Updated README.md
- Change folder localization for documentation
- Change folder localization for hooks scripts

## [1.7.0] - 2025-10-11

### ADDED

- Add CodeQL

## [1.6.8] - 2025-10-11

### CHANGED

- Bumped versions for GA workflows

## [1.6.7] - 2025-10-11

### CHANGED

- Bumped KSP version

## [1.6.6] - 2025-10-11

### ADDED

- Added dependabot

## [1.6.5] - 2025-10-11

### CHANGED

- Change Java folder to Kotlin
- Improve git hooks scripts
- Update InstallGitHooksTask and UninstallGitHooksTask to their own abstract classes

## [1.6.4] - 2025-10-11

### CHANGED

- Update Gradle Wrapper

## [1.6.3] - 2025-10-10

### CHANGED

- Added git hooks auto-install for Gradle

## [1.6.2] - 2025-10-10

### Added

- Created git hooks

## [1.6.1] - 2025-10-10

### Added

- Created CHANGELOG.md

## [1.6.0] - 2025-10-10

### Changed

- Rewrite GitHub Actions workflows

## [1.5.5] - 2025-10-10

### Added

- Created show bead number

### Changed

- Block orientation change
- Change color for `beadColorFor` and add color for previous beads.
- Make BEAD_LARGE bigger
- change BooleanSelector from CheckBox to Switch

## [1.5.4] - 2025-10-01

### Changed

- Update dependencies

### Removed

- Deleted manes plugin
- Removing deprecated parts

## [1.5.3] - 2025-06-12

### Added

- Created PrayerTitle to display actual prayer type
- Created README.md

### Changed

- Update V3 to V4 upload-artifacts in GHA
- Change ui.parts to ui.helper
- Update dependencies

## [1.5.2] - 2025-05-22

### Added

- Created logo
- Created reset button
- Created confirm dialog
- Created change prayer type

### Changed

- Upgrade SDK to 36
- Default language is system language instead of EN

### FIXED

- Fix display rosary
- Fix tap on rosary to next

## [1.5.1] - 2025-05-21

### Removed

- Deleting duplicated entry in strings.xml

## [1.5.0] - 2025-05-20

### Added

- Created Hilt dependency
- Created RosaryBottomAppBar and RosaryTopAppBar @StringRes prayerId instead of prayer (String)
- Created RosaryApplication
- Created localized context
- Created SettingsRepository to inject in RosaryViewModel
- Created CompositionLocalProvider for LocalContext
- Created AppLogger

### Changed

- Using Dimensions object for dimensions
- Add function extension isDarkTheme() for better support
- Update dependencies
- Upgrade Gradle to 8.14

### Removed

- Delete context dependency in viewModel
- Delete localRosaryViewModel
- Delete currentSetting and initialSetting creating settings
- Delete log entries in strings.xml

## [1.4.2] - 2025-05-20

### Changed

- Created language support
- Created LanguageSelector for SettingsScreen
- Created language files

## [1.4.1] - 2025-05-17

### Changed

- Make prayer localization changeable

## [1.4.0] - 2025-05-17

### Changed

- Make bottomBar scrollable

## [1.3.0] - 2025-05-17

### Added

- Created dialog for Settings

## [1.2.3] - 2025-05-17

### Fixed

- Fixed additional prayers

## [1.2.2] - 2025-05-15

### Fixed

- Fixed Rosary generation

## [1.2.1] - 2025-05-15

### Added

- Created Rosary generation

## [1.2.0] - 2025-05-15

### Added

- Created Rosary Canvas drawing

## [1.1.1] - 2025-05-15

### Added

- Created MainLocalView

## [1.1.0] - 2025-05-14

### Added

- Created first components

## [1.0.2] - 2025-05-12

### Changed

- Update Colors
- Update Schemes

## [1.0.1] - 2025-05-12

### Added

- GitHub Actions Workflows

## [1.0.0] - 2025-05-12

### Added

- app first version.
