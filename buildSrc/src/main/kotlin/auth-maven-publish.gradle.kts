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
    publications {

        create<MavenPublication>("dist") {
            from(components["java"])
            artifact(jarSources)
            artifact(jarJavadoc)

            groupId = project.group.toString()
            artifactId = project.name
            version = project.version.toString()
        }

        repositories {
            maven {
                name = "central"
                url = uri("https://s01.oss.sonatype.org/content/repositories/releases/")
                credentials {
                    username = System.getenv("OSSRH_USERNAME")
                    password = System.getenv("OSSRH_PASSWORD")
                }
            }
            maven {
                name = "snapshot"
                url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots")
                credentials {
                    username = System.getenv("OSSRH_USERNAME")
                    password = System.getenv("OSSRH_PASSWORD")
                }
            }
        }


    }
}
signing {
    val keyId = System.getenv("GPG_KEY_ID")
    val secretKey = System.getenv("GPG_SECRET_KEY")
    val password = System.getenv("GPG_PASSWORD")
//    setRequired {
//        !project.version.toString().endsWith("SNAPSHOT")
//    }
    isRequired = false
    useInMemoryPgpKeys(keyId, secretKey, password)
    sign(publishing.publications["dist"])
}