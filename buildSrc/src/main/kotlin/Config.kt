import org.gradle.api.JavaVersion

object Versions {
    val javaVersion = JavaVersion.VERSION_1_8
    // See https://docs.aws.amazon.com/glue/latest/dg/release-notes.html to check supported scala and spark versions
    const val scalaVersion = "2.11"

    //Plugins
    const val versionsPlugin = "0.28.0"
    const val shadowJarPlugin = "6.0.0"

    // Libs
    const val sparkSql = "2.4.3"
    const val scalaLib = "${scalaVersion}.12"
    const val config = "1.4.1"
    const val glue = "1.0.0" // there are no 2.0.0 yet for some reason

    // Libs for testing
    const val sparkBaseTest = "${sparkSql}_0.14.0"
    const val scalaTestRunner = "0.1.8"
    const val junit = "5.7.0"
}

object Plugins {
    const val versions = "com.github.ben-manes.versions"
    const val shadowJar = "com.github.johnrengelman.shadow"
}

object Libs {
    const val config = "com.typesafe:config:${Versions.config}"
    const val sparkSql = "org.apache.spark:spark-sql_2.11:${Versions.sparkSql}"
    const val scalaLib = "org.scala-lang:scala-library:${Versions.scalaLib}"
    const val awsGlue = "com.amazonaws:AWSGlueETL:${Versions.glue}"

    // Test libraries
    const val junit = "org.junit.jupiter:junit-jupiter-api:${Versions.junit}"
    const val junitEngine = "org.junit.jupiter:junit-jupiter-engine:${Versions.junit}"
    const val junitParams = "org.junit.jupiter:junit-jupiter-params:${Versions.junit}"
    const val sparkBaseTest = "com.holdenkarau:spark-testing-base_${Versions.scalaVersion}:${Versions.sparkBaseTest}"
    const val scalaTestRunner = "co.helmethair:scalatest-junit-runner:${Versions.scalaTestRunner}"
}