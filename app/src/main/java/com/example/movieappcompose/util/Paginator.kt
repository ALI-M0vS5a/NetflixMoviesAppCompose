package com.example.movieappcompose.util


interface Paginator<Key,Item> {
    suspend fun loadNextItems()
    fun reset()
}