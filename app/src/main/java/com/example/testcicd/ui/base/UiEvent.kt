package com.example.testcicd.ui.base

import java.util.UUID

open class UiEvent {
    val uniqueId: String = UUID.randomUUID().toString()
}
