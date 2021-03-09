package com.lzr.lbase

/**
 * UI Notitify callback
 */
interface UINCallback<T> {
    fun callback(t: T?)
}