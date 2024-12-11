package com.blackhito.data.models.mappers

interface IMapper<F, T> {
    fun mapFrom(from: F): T
}