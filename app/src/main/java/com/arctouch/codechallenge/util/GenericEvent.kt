package com.arctouch.codechallenge.util

open class GenericEvent<T>(
        val isLoading: Boolean = false,
        val error: Throwable? = null,
        val data: T? = null)