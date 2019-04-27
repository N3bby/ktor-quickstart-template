package com.razacx.project.domain.note

import java.time.LocalDateTime
import java.util.*

data class Note(val id: UUID, val author: String, val message: String, val timestamp: LocalDateTime)
