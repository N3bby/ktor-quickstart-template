package com.razacx.project.route

import com.razacx.project.DateProvider
import com.razacx.project.test.IntegrationTest
import io.ktor.http.HttpStatusCode.Companion.Created
import io.ktor.http.HttpStatusCode.Companion.OK
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.koin.ktor.ext.inject

class NotesRouteKtTest : IntegrationTest() {

    @Test
    fun `post creates note with random id and current timestamp`(): Unit = integrationTest {
        val dateProvider by application.inject<DateProvider>()
        val author = "some author"
        val message = "some message"

        val createNoteResponse = post(this, "/note", toJson(CreateNoteJson(author, message)))
        assertThat(createNoteResponse.status()).isEqualTo(Created)
        val noteId = fromJson<NoteIdJson>(createNoteResponse.content!!)

        val getNoteResponse = get(this, "/note/${noteId.id}")
        assertThat(getNoteResponse.status()).isEqualTo(OK)
        val note = fromJson<NoteJson>(getNoteResponse.content!!)

        assertThat(note)
            .isEqualTo(
                NoteJson(
                    id = noteId.id,
                    author = author,
                    message = message,
                    timestamp = dateProvider.now()
                )
            )
    }

    @Test
    internal fun `get gives list of all notes`(): Unit = integrationTest {
        val dateProvider by application.inject<DateProvider>()
        val author = "some author"
        val message = "some message"
        val noteId1 = fromJson<NoteIdJson>(post(this, "/note", toJson(CreateNoteJson(author, message))).content!!)
        val noteId2 = fromJson<NoteIdJson>(post(this, "/note", toJson(CreateNoteJson(author, message))).content!!)

        val notes = fromJson<List<NoteJson>>(get(this, "/note").content!!)
        assertThat(notes).containsExactlyInAnyOrder(
            NoteJson(
                id = noteId1.id,
                author = author,
                message = message,
                timestamp = dateProvider.now()
            ),
            NoteJson(
                id = noteId2.id,
                author = author,
                message = message,
                timestamp = dateProvider.now()
            )
        )
    }

}
