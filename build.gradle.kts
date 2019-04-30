import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    kotlin("jvm") version "1.3.10"
}

group = "com.jaspervanmerle.vrtcontest2019"
version = "1.0.0"

repositories {
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
}

val kotlinBundler = KotlinBundler(this)

var runArgs = listOf<String>()

val testsDirectory = File("src/main/resources/tests")
val testNames = testsDirectory.list().map { it.substringBefore('.') }.sorted()

tasks {
    withType<Wrapper> {
        distributionType = Wrapper.DistributionType.ALL
    }

    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }

    withType<JavaExec> {
        doFirst {
            main = "${project.group}.MainKt"
            args = runArgs
        }
    }
}

for (name in testNames) {
    task("test$name") {
        group = "test"
        dependsOn("compileKotlin")
        finalizedBy("run")

        doLast {
            runArgs = listOf(name)
        }
    }
}

task("testAll") {
    group = "test"
    dependsOn("compileKotlin")
    finalizedBy("run")

    doLast {
        runArgs = testNames
    }
}

task("bundle") {
    group = "distribution"
    dependsOn("compileKotlin")

    doLast {
        kotlinBundler.bundleToFile("main.kt")
    }
}
