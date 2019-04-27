package com.razacx.project

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.razacx.project.domain.note.NoteRepository
import com.razacx.project.domain.note.NoteRepositoryImpl
import com.razacx.project.domain.note.NoteTable
import com.razacx.project.route.notesRoute
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.jackson.JacksonConverter
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.jetbrains.exposed.sql.SchemaUtils.createMissingTablesAndColumns
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.Logger.slf4jLogger
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.experimental.builder.singleBy
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
fun createBeanDefinitions() = module(createdAtStart = true) {
    singleBy<DatabaseConnector, DataBaseConnectorImpl>()
    singleBy<NoteRepository, NoteRepositoryImpl>()
    singleBy<DateProvider, DateProviderImpl>()
}

fun createObjectMapper(): ObjectMapper {
    return jacksonObjectMapper()
        .registerModule(JavaTimeModule())
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
}

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

val tables = listOf(NoteTable)
fun Application.initDb() {
    val dbConnector by inject<DatabaseConnector>()
    dbConnector.connect()
    transaction {
        createMissingTablesAndColumns(*tables.toTypedArray())
    }
}
