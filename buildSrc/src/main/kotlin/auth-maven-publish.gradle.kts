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
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/llh4github/simple-auth")
            credentials {
                username = project.findProperty("USERNAME_GITHUB") as String? ?: System.getenv("USERNAME_GITHUB")
                password = project.findProperty("PAT_GITHUB") as String? ?: System.getenv("PAT_GITHUB")
//                username = System.getenv("GITHUB_ACTOR")
//                password = System.getenv("GITHUB_TOKEN")
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