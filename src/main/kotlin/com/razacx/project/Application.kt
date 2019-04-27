package com.razacx.project

import com.razacx.project.config.createBeanDefinitions
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
import org.koin.ktor.ext.inject

fun main() {
    beanDefinitions = createBeanDefinitions()
    embeddedServer(
        factory = Netty,
        port = 8080,
        module = Application::main
    ).start(wait = true)
}

// TODO Find a way to pass this to Application.main(). Mutability is bad
var beanDefinitions: Module? = null

fun Application.main() {
    install(ContentNegotiation) {
        register(ContentType.Application.Json, JacksonConverter(createObjectMapper()))
    }
    install(Koin) {
        slf4jLogger()
        modules(beanDefinitions!!)
    }
    initDb()
    routing {
        notesRoute()
    }
}

fun Application.initDb() {
    val dbConnector by inject<DatabaseConnector>()
    dbConnector.connect()
    createOrUpdateTables()
}
