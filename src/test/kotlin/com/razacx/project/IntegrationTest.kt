package com.razacx.project

import com.razacx.project.launcher.coreModule
import io.ktor.application.Application
import io.ktor.server.testing.TestApplicationEngine
import io.ktor.server.testing.withTestApplication

open class IntegrationTest: UnitTest() {

    protected fun <R> integrationTest(test: TestApplicationEngine.() -> R): R =
        withTestApplication(Application::coreModule, test)

}
