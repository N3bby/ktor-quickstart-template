package com.razacx.project.launcher

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.razacx.project.route.helloWorldRoute
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.jackson.JacksonConverter
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main() {
    embeddedServer(
        factory = Netty,
        port = 8080,
        module = Application::main
    ).start(wait = true)
}

fun Application.main() {
    install(ContentNegotiation) {
        register(ContentType.Application.Json, JacksonConverter(jacksonObjectMapper()))
    }
    routing {
        helloWorldRoute()
    }
}
