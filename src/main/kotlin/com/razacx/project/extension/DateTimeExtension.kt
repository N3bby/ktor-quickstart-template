package com.razacx.project.extension

import org.joda.time.DateTime
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

fun DateTime.toSystemLocalDateTime(): LocalDateTime {
    return LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault())
}
