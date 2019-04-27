package com.razacx.project.config

import com.razacx.project.DataBaseConnectorImpl
import com.razacx.project.DatabaseConnector
import com.razacx.project.DateProvider
import com.razacx.project.DateProviderImpl
import com.razacx.project.domain.note.NoteRepository
import com.razacx.project.domain.note.NoteRepositoryImpl
import org.koin.dsl.module
import org.koin.experimental.builder.singleBy

fun createBeanDefinitions() = module(createdAtStart = true) {
    singleBy<DatabaseConnector, DataBaseConnectorImpl>()
    singleBy<NoteRepository, NoteRepositoryImpl>()
    singleBy<DateProvider, DateProviderImpl>()
}
