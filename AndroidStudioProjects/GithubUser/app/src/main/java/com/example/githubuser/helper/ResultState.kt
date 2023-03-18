package com.example.githubuser.helper

sealed class ResultState<out R> private constructor() {
    data class Success<out T>(val data: T) : ResultState<T>()
    data class Error<out S>(val error: Event<String>) : ResultState<S>()
    object Loading : ResultState<Nothing>()
}
