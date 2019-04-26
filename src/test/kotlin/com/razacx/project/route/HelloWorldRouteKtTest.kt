package com.razacx.project.route

import org.junit.jupiter.api.Test
import com.razacx.project.IntegrationTest
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.server.testing.handleRequest
import me.tatarka.assertk.assert
import me.tatarka.assertk.assertions.isEqualTo

class HelloWorldRouteKtTest: IntegrationTest() {

    @Test
    fun `root returns hello world`() = integrationTest {
        with(handleRequest(HttpMethod.Get, "/")) {
            assert(response.status()).isEqualTo(OK)
            assert(response.content).isEqualTo("Hello world!")
        }
    }

}
