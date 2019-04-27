package com.razacx.project

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.razacx.project.route.helloWorldRoute
import com.razacx.project.service.HelloWorldService
import com.razacx.project.service.HelloWorldServiceImpl
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.jackson.JacksonConverter
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.koin.Logger.slf4jLogger
import org.koin.dsl.module
import org.koin.experimental.builder.singleBy
import org.koin.ktor.ext.Koin

fun main() {
    embeddedServer(
        factory = Netty,
        port = 8080,
        module = Application::main
    ).start(wait = true)
}

val koinModule = module(createdAtStart = true) {
    singleBy<HelloWorldService, HelloWorldServiceImpl>()
}

fun Application.main() {
    install(ContentNegotiation) {
        register(ContentType.Application.Json, JacksonConverter(jacksonObjectMapper()))
    }
    install(Koin) {
        slf4jLogger()
        modules(koinModule)
    }
    routing {
        helloWorldRoute()
    }
}
