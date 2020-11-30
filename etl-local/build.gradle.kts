plugins {
    scala
}

group = "com.inventale.platform"
version = "0.1"

java {
    sourceCompatibility = Versions.javaVersion
    targetCompatibility = Versions.javaVersion
}

dependencies {
    implementation(project(":etl"))
    implementation(Libs.sparkSql)
    implementation(Libs.awsGlue)  {
        exclude(group = "com.fasterxml.jackson.core")
        exclude(group = "com.fasterxml.jackson.module")
    }
}
