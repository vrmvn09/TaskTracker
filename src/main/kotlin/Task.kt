package org.example

import kotlinx.serialization.Serializable

@Serializable
data class Task(
    val id: Int,
    var title: String,
    var description: String?,
    val createdAt: String,
    var status: TaskStatus
)

@Serializable
enum class TaskStatus {
    PENDING,
    DONE
}