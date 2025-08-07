package org.example

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

val file = File("tasks.json")
val json = Json { prettyPrint = true }
val tasks = mutableListOf<Task>()
var nextId = 1

fun loadTasks() {
    if (file.exists()) {
        try {
            val jsonString = file.readText()
            if (jsonString.isNotBlank()) {
                val loadedTasks = json.decodeFromString<List<Task>>(jsonString)
                tasks.addAll(loadedTasks)
                nextId = (tasks.maxOfOrNull { it.id } ?: 0) + 1
            }
        } catch (e: Exception) {
            println("Ошибка при загрузке файла: ${e.message}")
        }
    }
}

fun saveTasks() {
    try {
        val jsonString = json.encodeToString(tasks)
        file.writeText(jsonString)
    } catch (e: Exception) {
        println("Ошибка при сохранении файла: ${e.message}")
    }
}
