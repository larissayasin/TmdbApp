package com.arctouch.codechallenge.util

open class GenericEvent(
        val isLoading: Boolean = false,
        val error: Throwable? = null,
        val data: Any? = null)