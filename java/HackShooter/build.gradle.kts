plugins {
    id("java")
    application
}

group = "net.utcode"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

application {
    mainClass = "net.utcode.Main"
    applicationDefaultJvmArgs = listOf("-Djava.library.path=" + layout.projectDirectory.asFile.parentFile.parentFile.toString() + "/build/bin/windows", "--enable-native-access=ALL-UNNAMED")
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test{
    useJUnitPlatform()
}

tasks.compileJava{
    options.compilerArgs.addAll(listOf("-h", layout.projectDirectory.asFile.parentFile.parentFile.toString() + "/build/jni-headers"))
}

tasks.jar{
    doFirst{
        manifest{
            attributes("Main-Class" to application.mainClass.get())
        }
    }

    doLast {
        copy {
            from(archiveFile.get())
            into(layout.projectDirectory.asFile.parentFile.parentFile.toString() + "/build/bin/windows")
        }
    }
}

tasks.register<Exec>("jpackage"){
    dependsOn(tasks.jar)

    delete(layout.projectDirectory.asFile.parentFile.parentFile.toString() + "/build/app/windows/HackShooter")
    commandLine("jpackage",
        "--name", "HackShooter",
        "--input", layout.projectDirectory.asFile.parentFile.parentFile.toString() + "/build/bin/windows",
        "--main-jar", tasks.jar.get().archiveFile.get().asFile.name,
        //"--main-class", application.mainClass.get(),
        "--type", "app-image",
        "--dest", layout.projectDirectory.asFile.parentFile.parentFile.toString() + "/build/app/windows",
        "--java-options", "--enable-native-access=ALL-UNNAMED",
        "--java-options", $$"-Djava.library.path=$APPDIR")
}