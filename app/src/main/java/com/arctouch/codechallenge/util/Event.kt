package com.arctouch.codechallenge.util

open class Event<T>(
        val isLoading: Boolean = false,
        val error: Throwable? = null,
        val data: T? = null){

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Event<*>

        if (isLoading != other.isLoading) return false
        if (error != other.error) return false
        if (data != other.data) return false

        return true
    }

    override fun hashCode(): Int {
        var result = isLoading.hashCode()
        result = 31 * result + (error?.hashCode() ?: 0)
        result = 31 * result + (data?.hashCode() ?: 0)
        return result
    }
}