package com.razacx.project.route

import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.route

fun Routing.helloWorldRoute() {
    route("/") {
        get {
            this.call.respond("Hello world!")
        }
    }
}
