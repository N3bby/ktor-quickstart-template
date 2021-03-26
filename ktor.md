
# KTor

## What is it?
Official JetBrains framework for creating web applications in Kotlin.
https://ktor.io/

## Why do I use it?
- Simple
- Fairly lightweight (1s startup, <100MB heap size on smalls projects)
- Easy to configure
    - No autoconfiguration magic
- Native Kotlin library -> works well with Kotlin

## Cons
- Since it's pretty small, you might have to create some things yourself or use third party libraries (loading configuration, test setup, ...)

## Minimal example
*Versions might be outdated*
```gradle
compile "io.ktor:ktor-server-core:1.1.3"
compile "io.ktor:ktor-server-netty:1.1.3"
compile "ch.qos.logback:logback-classic:1.2.3"
```

```kotlin
fun main() {
    embeddedServer(Netty, port = 8080) {
        routing {
            get("/") {
                call.respondText("Hello, world!")
            }
        }
    }.start(wait = true)
}
```