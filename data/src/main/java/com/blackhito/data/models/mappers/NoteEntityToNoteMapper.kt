package com.blackhito.data.models.mappers

import com.blackhito.data.models.NoteEntity
import com.blackhito.domain.models.Note

internal object NoteEntityToNoteMapper: IMapper<NoteEntity, Note> {
    override fun mapFrom(from: NoteEntity): Note {
        return Note(
            id = from.id,
            dateStart = from.dateStart,
            dateFinish = from.dateFinish,
            name = from.name,
            description = from.description
        )
    }
}