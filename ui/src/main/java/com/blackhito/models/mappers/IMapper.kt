package com.blackhito.models.mappers

interface IMapper<F, T> {
    fun mapFrom(from: F): T
}