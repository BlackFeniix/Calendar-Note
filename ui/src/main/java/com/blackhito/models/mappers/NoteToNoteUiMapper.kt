package com.blackhito.models.mappers

import com.blackhito.domain.models.Note
import com.blackhito.models.NoteUI

internal object NoteToNoteUiMapper: IMapper<Note, NoteUI> {
    override fun mapFrom(from: Note): NoteUI {
        return NoteUI(
            id = from.id,
            dateStart = from.dateStart,
            dateFinish = from.dateFinish,
            name = from.name,
            description = from.description
        )
    }
}