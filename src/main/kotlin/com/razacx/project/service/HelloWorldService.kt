package com.razacx.project.service

interface HelloWorldService {
    fun getHelloWorld(): String
}

class HelloWorldServiceImpl: HelloWorldService {
    override fun getHelloWorld(): String {
        return "Hello world!"
    }
}
