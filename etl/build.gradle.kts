import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

group = "com.inventale.platform"
version = "0.1"

plugins {
    scala
    id(Plugins.shadowJar) version Versions.shadowJarPlugin
    id(Plugins.versions)
}

dependencies {
    compileOnly(Libs.sparkSql)
    compileOnly(Libs.awsGlue)
    implementation(Libs.config)
    implementation(Libs.scalaLib)

    testImplementation(Libs.junit)
    testImplementation(Libs.junitEngine)
    testImplementation(Libs.junitParams)
    testImplementation(Libs.sparkBaseTest)
    testImplementation(Libs.sparkSql)
    testImplementation(Libs.scalaLib)
    testImplementation(Libs.scalaTestRunner)
}

java {
    sourceCompatibility = Versions.javaVersion
    targetCompatibility = Versions.javaVersion
}

tasks.getByName<ShadowJar>("shadowJar") {
    isZip64 = true
    // For spark jars, see https://imperceptiblethoughts.com/shadow/configuration/merging/
    mergeServiceFiles()
    archiveFileName.set("etl-glue.jar")
}

tasks {
    build {
        dependsOn(shadowJar)
    }
}