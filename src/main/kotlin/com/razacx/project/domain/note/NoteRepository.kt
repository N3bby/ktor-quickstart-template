package com.razacx.project.domain.note

import com.razacx.project.extension.toSystemLocalDateTime
import com.razacx.project.extension.toUtcJodaTime
import org.jetbrains.exposed.sql.*
import java.util.*

object NoteTable: Table("note") {
    val id = uuid("id").primaryKey()
    val author = varchar("author", 50)
    val message = text("message")
    val timestamp = datetime("timestamp")
}

fun toDomain(resultRow: ResultRow): Note {
    return Note(
        id = resultRow[NoteTable.id],
        author = resultRow[NoteTable.author],
        message = resultRow[NoteTable.message],
        timestamp = resultRow[NoteTable.timestamp].toSystemLocalDateTime()
    )
}

interface NoteRepository {
    fun save(note: Note)
    fun findAll(): List<Note>
    fun find(id: UUID): Note
}

class NoteRepositoryImpl: NoteRepository {

    override fun save(note: Note) {
        NoteTable.insert {
            it[id] = note.id
            it[author] = note.author
            it[message] = note.message
            it[timestamp] = note.timestamp.toUtcJodaTime()
        }
    }

    override fun findAll(): List<Note> {
        return NoteTable.selectAll().map(::toDomain)
    }

    override fun find(id: UUID): Note {
        return NoteTable.select { NoteTable.id eq id }
            .map(::toDomain)
            .first()
    }

}
