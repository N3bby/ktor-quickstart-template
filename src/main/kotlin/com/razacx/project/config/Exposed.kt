package com.razacx.project.config

import com.razacx.project.domain.note.NoteTable
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

val tables = listOf(NoteTable)
fun createOrUpdateTables() {
    transaction {
        SchemaUtils.createMissingTablesAndColumns(*tables.toTypedArray())
    }
}
