package com.razacx.project.test

import com.fasterxml.jackson.module.kotlin.readValue
import com.razacx.project.*
import com.razacx.project.config.createKoinModule
import com.razacx.project.config.createObjectMapper
import com.razacx.project.config.tables
import io.ktor.application.Application
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.server.testing.*
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.AfterEach
import org.koin.core.module.Module
import org.koin.experimental.builder.singleBy

open class IntegrationTest : UnitTest() {

    // Can't be private since we can't inline our fromJson function otherwise
    protected val objectMapper = createObjectMapper()

    @AfterEach
    internal fun tearDown() {
        transaction {
            SchemaUtils.drop(*tables.toTypedArray())
        }
    }

    protected open fun overrideKoinModuleDefinitions() {
        // Not abstract since you are not always required to override a definition
    }

    private fun createTestKoinModule(): Module {
        val koinModule = createKoinModule()
        koinModule.singleBy<DatabaseConnector, DatabaseConnectorTestImpl>(override = true)
        koinModule.singleBy<DateProvider, DateProviderTestImpl>(override = true)
        overrideKoinModuleDefinitions()
        return koinModule
    }

    protected fun <R> integrationTest(test: TestApplicationEngine.() -> R): R {
        return withTestApplication(createKtorModule(createTestKoinModule()), test)
    }

    protected fun post(application: TestApplicationEngine, route: String, body: String): TestApplicationResponse {
        return with(application.handleRequest(HttpMethod.Post, route) {
            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody(body)
        }) { response }
    }

    protected fun get(application: TestApplicationEngine, route: String): TestApplicationResponse {
        return with(application.handleRequest(HttpMethod.Get, route)) { response }
    }

    protected fun toJson(obj: Any): String {
        return objectMapper.writeValueAsString(obj)
    }

    protected inline fun <reified R> fromJson(json: String): R {
        return objectMapper.readValue(json)
    }

}
