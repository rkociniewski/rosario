# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/), and this project adheres
to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.6.5] - 2025-10-11

### CHANGED
- change Java folder to Kotlin
- improve git hooks scripts
- update InstallGitHooksTask and UninstallGitHooksTask to their own abstract classes

## [1.6.4] - 2025-10-11

### CHANGED
- Update Gradle Wrapper

## [1.6.3] - 2025-10-10

### CHANGED
- Added git hooks auto-install for Gradle

## [1.6.2] - 2025-10-10

### Added
- Added git hooks

## [1.6.1] - 2025-10-10

### Added
- Added CHANGELOG.md

## [1.6.0] - 2025-10-10

### Changed
- rewrite GitHub Actions workflows

## [1.5.5] - 2025-10-10

### Added
- Add show bead number

### Changed
- Block orientantion change
- Change color for `beadColorFor` and add color for previous beads.
- Make BEAD_LARGE bigger
- change BooleanSelector from CheckBox to Switch

## [1.5.4] - 2025-10-01

### Changed
- update dependencies

### Removed
- Deleted manes plugin
- Removing deprecated parts

## [1.5.3] - 2025-06-12

### Added
- added PrayerTitle to display actual prayer type
- Add README.md

### Changed
- Update V3 to V4 upload-artifacts in GHA
- change ui.parts to ui.helper
- update dependencies

## [1.5.2] - 2025-05-22

### Added
- Add logo
- Add reset button
- Add confirm dialog
- Add change prayer type

### Changed
- Upgrade SDK to 36
- Fix display rosary
- Fix tap on rosary to next
- Default language is system language instead of EN

## [1.5.1] - 2025-05-21

### Removed
- Deleting duplicated entry in strings.xml

## [1.5.0] - 2025-05-20

### Added
- Add Hilt dependency
- Add RosaryBottomAppBar and RosaryTopAppBar @StringRes prayerId instead of prayer (String)
- Add RosaryApplication
- Add localized context
- Create SettingsRepository to inject in RosaryViewModel
- Add CompositionLocalProvider for LocalContext
- Create AppLogger

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
- Add language support
- Create LanguageSelector for SettingsScreen
- Add language files

## [1.4.1] - 2025-05-17

### Changed

- Make prayer localization changeable

## [1.4.0] - 2025-05-17

### Changed

- Make bottomBar scrollable

## [1.3.0] - 2025-05-17

### Added

- Add dialog for Settings

## [1.2.3] - 2025-05-17

### Fixed

- Fixed additional prayers

## [1.2.2] - 2025-05-15

### Fixed

- Fixed Rosary generation

## [1.2.1] - 2025-05-15

### Added

- Added Rosary generation

## [1.2.0] - 2025-05-15

### Added

- Added Rosary Canvas drawing

## [1.1.1] - 2025-05-15

### Added

- Added MainLocalView

## [1.1.0] - 2025-05-14

### Added

- Added first components

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
