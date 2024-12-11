package com.blackhito.data.di

import com.blackhito.data.repositoryImpl.NoteRepositoryImpl
import com.blackhito.domain.repository.INoteRepository
import org.koin.dsl.module

val repositoryModule = module{
    single<INoteRepository> { NoteRepositoryImpl(get()) }
}