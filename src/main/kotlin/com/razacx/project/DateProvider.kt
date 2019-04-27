package com.razacx.project

import java.time.LocalDateTime

interface DateProvider {
    fun now(): LocalDateTime
}

class DateProviderImpl : DateProvider {
    override fun now(): LocalDateTime = LocalDateTime.now()
}
