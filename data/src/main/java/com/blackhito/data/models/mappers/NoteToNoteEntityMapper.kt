package com.blackhito.data.models.mappers

import com.blackhito.data.models.NoteEntity
import com.blackhito.domain.models.Note

internal object NoteToNoteEntityMapper: IMapper<Note, NoteEntity> {
    override fun mapFrom(from: Note): NoteEntity {
        return NoteEntity(
            id = from.id,
            dateStart = from.dateStart,
            dateFinish = from.dateFinish,
            name = from.name,
            description = from.description
        )
    }
}