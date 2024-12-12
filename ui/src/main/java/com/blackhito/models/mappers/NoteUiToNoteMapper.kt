package com.blackhito.models.mappers

import com.blackhito.domain.models.Note
import com.blackhito.models.NoteUI

internal object NoteUiToNoteMapper: IMapper<NoteUI, Note> {
    override fun mapFrom(from: NoteUI): Note {
        return Note(
            id = from.id,
            dateStart = from.dateStart,
            dateFinish = from.dateFinish,
            name = from.name,
            description = from.description
        )
    }
}