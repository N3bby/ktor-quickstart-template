package com.razacx.project

import com.razacx.project.config.createKoinModule
import com.razacx.project.config.createObjectMapper
import com.razacx.project.config.createOrUpdateTables
import com.razacx.project.route.notesRoute
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.jackson.JacksonConverter
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.koin.Logger.slf4jLogger
import org.koin.core.module.Module
import org.koin.ktor.ext.Koin
import org.koin.ktor.ext.get
import org.koin.ktor.ext.inject

fun main() {
    embeddedServer(
        factory = Netty,
        port = 8080,
        module = createKtorModule(createKoinModule())
    ).start(wait = true)
}

fun createKtorModule(koinModule: Module): Application.() -> Unit {
    fun Application.main() {
        install(Koin) {
            slf4jLogger()
            modules(koinModule)
        }
        install(ContentNegotiation) {
            register(ContentType.Application.Json, JacksonConverter(get()))
        }
        initDb()
        routing {
            notesRoute()
        }
    }
    return Application::main
}

fun Application.initDb() {
    val dbConnector by inject<DatabaseConnector>()
    dbConnector.connect()
    createOrUpdateTables()
}
