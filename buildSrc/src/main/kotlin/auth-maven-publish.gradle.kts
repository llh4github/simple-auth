plugins {
    id("kotlin")
    id("maven-publish")
    id("signing")
}
val jarSources by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.main.map { it.allSource })
}

val jarJavadoc by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
}

publishing {
    repositories {
        maven {
            name = "central"
            url = uri("https://s01.oss.sonatype.org/content/repositories/releases/")
            credentials {
                username = project.findProperty("OSSRH_USERNAME") as String? ?: System.getenv("OSSRH_USERNAME")
                password = project.findProperty("OSSRH_PASSWORD") as String? ?: System.getenv("OSSRH_PASSWORD")
            }
        }

        maven {
            name = "snapshot"
            url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots")
            credentials {
                username = project.findProperty("OSSRH_USERNAME") as String? ?: System.getenv("OSSRH_USERNAME")
                password = project.findProperty("OSSRH_PASSWORD") as String? ?: System.getenv("OSSRH_PASSWORD")
            }
        }
    }
    publications {

        create<MavenPublication>("dist") {
            from(components["java"])
            artifact(jarSources)
            artifact(jarJavadoc)

            groupId = project.group.toString()
            artifactId = project.name
            version = project.version.toString()
        }

    }
}