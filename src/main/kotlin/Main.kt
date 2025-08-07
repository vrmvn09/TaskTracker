package org.example

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun main() {
    loadTasks()

    var running = true

    while (running) {
        println("\nВыберите действие:")
        println("1. Добавить задачу")
        println("2. Посмотреть задачи")
        println("3. Изменить задачу")
        println("4. Удалить задачу")
        println("5. Выход")
        print("Ваш выбор: ")

        val choice = readlnOrNull()

        when (choice) {
            "1" -> {
                print("Введите название задачи: ")
                val title = readlnOrNull()
                if (title.isNullOrBlank()) {
                    println("Название задачи не может быть пустым.")
                    continue
                }
                println("Введите описание задачи (необязательно):")
                val description = readlnOrNull()

                val newTask = Task(
                    id = nextId,
                    title = title,
                    description = description,
                    createdAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")),
                    status = TaskStatus.PENDING
                )
                tasks.add(newTask)
                nextId++
                saveTasks()
                println("Задача '${newTask.title}' добавлена. ID: ${newTask.id}")
            }

            "2" -> {
                println("\nВыберите фильтр:")
                println("1. Все задачи")
                println("2. Только выполненные")
                println("3. Только невыполненные")
                print("Ваш выбор: ")
                val filterChoice = readlnOrNull()

                println("\n--- Список задач ---")
                var foundTasks = false
                for (task in tasks) {
                    val isDone = (task.status == TaskStatus.DONE)
                    if ((filterChoice == "2" && isDone) ||
                        (filterChoice == "3" && !isDone) ||
                        (filterChoice == "1" || filterChoice == null)
                    ) {
                        println("ID: ${task.id}")
                        println("Название: ${task.title}")
                        println("Описание: ${task.description ?: "Нет описания"}")
                        println("Дата создания: ${task.createdAt}")
                        println("Статус: ${if (isDone) "Выполнена" else "Не выполнена"}")
                        println("--------------------")
                        foundTasks = true
                    }
                }
                if (!foundTasks) {
                    println("Список задач пуст.")
                }
            }

            "3" -> {
                println("Введите ID задачи, которую хотите изменить:")
                val idToEdit = readlnOrNull()?.toIntOrNull()
                var taskToEdit: Task? = null
                for (task in tasks) {
                    if (task.id == idToEdit) {
                        taskToEdit = task
                        break
                    }
                }

                if (taskToEdit == null) {
                    println("Задача с таким ID не найдена.")
                    continue
                }

                println("Текущая задача: ${taskToEdit.title}")
                println("Введите новое название (оставьте пустым, чтобы не менять):")
                val newTitle = readlnOrNull()
                if (!newTitle.isNullOrBlank()) {
                    taskToEdit.title = newTitle
                }

                println("Текущее описание: ${taskToEdit.description ?: "Нет описания"}")
                println("Введите новое описание (оставьте пустым, чтобы не менять):")
                val newDescription = readlnOrNull()
                if (!newDescription.isNullOrBlank()) {
                    taskToEdit.description = newDescription
                }

                println("Текущий статус: ${if (taskToEdit.status == TaskStatus.DONE) "Выполнена" else "Не выполнена"}")
                println("Изменить статус? (y/n)")
                val changeStatus = readlnOrNull()
                if (changeStatus?.lowercase() == "y") {
                    taskToEdit.status =
                        if (taskToEdit.status == TaskStatus.DONE) TaskStatus.PENDING else TaskStatus.DONE
                }
                saveTasks()
                println("Задача ID ${taskToEdit.id} успешно обновлена.")
            }

            "4" -> {
                println("Введите ID задачи, которую хотите удалить:")
                val idToDelete = readlnOrNull()?.toIntOrNull()
                var foundTask = false
                val iterator = tasks.iterator()
                while (iterator.hasNext()) {
                    val task = iterator.next()
                    if (task.id == idToDelete) {
                        iterator.remove()
                        foundTask = true
                        break
                    }
                }

                if (foundTask) {
                    saveTasks()
                    println("Задача ID $idToDelete успешно удалена.")
                } else {
                    println("Задача с таким ID не найдена.")
                }
            }

            "5" -> {
                running = false
                println("Выход из приложения. До свидания!")
            }

            else -> println("Неверный выбор. Пожалуйста, выберите действие от 1 до 5.")
        }
    }
}