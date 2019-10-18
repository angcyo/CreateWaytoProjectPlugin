# Project-wide Gradle settings.
# IDE (e.g. Android Studio) users:
# Gradle settings configured through the IDE *will override*
# any settings specified in this file.
# For more details on how to configure your build environment visit
# http://www.gradle.org/docs/current/userguide/build_environment.html
# Specifies the JVM arguments used for the daemon process.
# The setting is particularly useful for tweaking memory settings.
org.gradle.jvmargs=-Xmx2048M -Dkotlin.daemon.jvm.options\="-Xmx2048M"
# When configured, Gradle will run in incubating parallel mode.
# This option should only be used with decoupled projects. More details, visit
# http://www.gradle.org/docs/current/userguide/multi_project_builds.html#sec:decoupled_projects
# org.gradle.parallel=true
# Kotlin code style for this project: "official" or "obsolete":
kotlin.code.style=official

org.gradle.parallel=true
android.enableR8=false
android.enableD8=true

android.useAndroidX=true
android.enableJetifier=true

# 开启混淆
minify_enabled=true
minify_enabled_debug=false

kotlin_version=1.3.50
replugin_version=2.3.3
objectboxVersion = 2.3.4

M_SDK=21
T_SDK=29
C_SDK=29

key_file=key_file.properties


