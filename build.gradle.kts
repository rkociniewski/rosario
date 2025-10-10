// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
}

tasks.register("installGitHooks") {
    description = "Install Git hooks"
    group = "git hooks"

    doLast {
        val hooksDir = File(rootDir, ".git/hooks")
        val scriptsDir = File(rootDir, ".githooks")

        if (!hooksDir.exists()) {
            println("⚠️  .git/hooks directory not found")
            return@doLast
        }

        if (!scriptsDir.exists()) {
            println("⚠️  .githooks directory not found")
            return@doLast
        }

        val hooks = listOf(
            "commit-msg",
            "pre-commit",
            "prepare-commit-msg",
            "post-commit",
            "pre-push"
        )

        hooks.forEach {
            val source = File(scriptsDir, it)
            val target = File(hooksDir, it)

            if (source.exists()) {
                // Create relative symlink
                val relativePath = "../../.githooks/$it"
                target.delete()

                // Make source executable
                source.setExecutable(true)

                // Create symlink (Unix/Linux/Mac)
                try {
                    val process = ProcessBuilder("ln", "-sf", relativePath, target.absolutePath)
                        .redirectErrorStream(true)
                        .start()

                    process.waitFor()
                    println("✅ Installed $it")
                } catch (_: Exception) {
                    // Fallback: copy file on Windows
                    source.copyTo(target, overwrite = true)
                    target.setExecutable(true)
                    println("✅ Copied $it")
                }
            }
        }

        println("🎉 Git hooks installed successfully!")
    }
}

tasks.register("uninstallGitHooks") {
    description = "Uninstall Git hooks"
    group = "git hooks"

    doLast {
        val hooksDir = File(rootDir, ".git/hooks")

        val hooks = listOf(
            "commit-msg",
            "pre-commit",
            "prepare-commit-msg",
            "post-commit",
            "pre-push"
        )

        hooks.forEach { hook ->
            val target = File(hooksDir, hook)
            if (target.exists()) {
                target.delete()
                println("🗑️  Removed $hook")
            }
        }

        println("Git hooks uninstalled")
    }
}

// Auto-install hooks after project sync
tasks.named("prepareKotlinBuildScriptModel") {
    dependsOn("installGitHooks")
}

// Or install on first build
afterEvaluate {
    tasks.getByPath(":app:preBuild").dependsOn(":installGitHooks")
}
