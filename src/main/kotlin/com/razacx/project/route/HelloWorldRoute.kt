package com.razacx.project.route

import com.razacx.project.service.HelloWorldService
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.route
import org.koin.ktor.ext.inject

fun Routing.helloWorldRoute() {
    val helloWorldService by inject<HelloWorldService>()
    route("/") {
        get {
            this.call.respond(helloWorldService.getHelloWorld())
        }
    }
}
