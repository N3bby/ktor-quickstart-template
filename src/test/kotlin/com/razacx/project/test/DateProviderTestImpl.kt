package com.razacx.project.test

import com.razacx.project.DateProvider
import java.time.LocalDateTime

class DateProviderTestImpl: DateProvider {
    var time: LocalDateTime = LocalDateTime.now()
    override fun now(): LocalDateTime = time
}
