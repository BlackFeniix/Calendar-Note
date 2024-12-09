package com.blackhito.ui.screens.addnote

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AddNewNoteViewModel: ViewModel() {
    private val _name = MutableStateFlow("")
    private val name = _name.asStateFlow()

    private val _description = MutableStateFlow("")
    private val description = _description.asStateFlow()

    private val _dateStart = MutableStateFlow(0L) //todo
    private val dateStart = _dateStart.asStateFlow()

    private val _dateFinish = MutableStateFlow(0L) //todo
    private val dateFinish = _dateFinish.asStateFlow()

    fun getName() = name
    fun setName(newName: String) {
        _name.update { newName }
    }

    fun getDescription() = description
    fun setDescription(newDescription: String) {
        _description.update { newDescription }
    }

    fun getDateStart() = dateStart
    fun setDateStart(newDateFinish: Long) {
        _dateStart.update { newDateFinish }
    }

    fun getDateFinish() = dateFinish
    fun setDateFinish(newDateFinish: Long) {
        _dateFinish.update { newDateFinish }
    }

    fun createNote() {

    }
}