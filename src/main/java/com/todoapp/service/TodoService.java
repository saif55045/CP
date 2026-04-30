package com.todoapp.service;

import com.todoapp.model.Todo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Service class for managing Todo items (in-memory storage).
 */
public class TodoService {
    private final List<Todo> todos;
    private final AtomicInteger idCounter;

    public TodoService() {
        this.todos = new CopyOnWriteArrayList<>();
        this.idCounter = new AtomicInteger(1);
    }

    /**
     * Add a new todo item.
     */
    public Todo addTodo(String title, String description, String priority) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        if (priority == null || priority.trim().isEmpty()) {
            priority = "MEDIUM";
        }
        Todo todo = new Todo(idCounter.getAndIncrement(), title.trim(), 
                            description != null ? description.trim() : "", priority.toUpperCase());
        todos.add(todo);
        return todo;
    }

    /**
     * Get all todos.
     */
    public List<Todo> getAllTodos() {
        return new ArrayList<>(todos);
    }

    /**
     * Get a todo by its ID.
     */
    public Optional<Todo> getTodoById(int id) {
        return todos.stream().filter(t -> t.getId() == id).findFirst();
    }

    /**
     * Update a todo item.
     */
    public boolean updateTodo(int id, String title, String description, String priority) {
        Optional<Todo> optTodo = getTodoById(id);
        if (optTodo.isPresent()) {
            Todo todo = optTodo.get();
            if (title != null && !title.trim().isEmpty()) {
                todo.setTitle(title.trim());
            }
            if (description != null) {
                todo.setDescription(description.trim());
            }
            if (priority != null && !priority.trim().isEmpty()) {
                todo.setPriority(priority.toUpperCase());
            }
            return true;
        }
        return false;
    }

    /**
     * Toggle the completion status of a todo.
     */
    public boolean toggleTodo(int id) {
        Optional<Todo> optTodo = getTodoById(id);
        if (optTodo.isPresent()) {
            Todo todo = optTodo.get();
            todo.setCompleted(!todo.isCompleted());
            return true;
        }
        return false;
    }

    /**
     * Delete a todo by its ID.
     */
    public boolean deleteTodo(int id) {
        return todos.removeIf(t -> t.getId() == id);
    }

    /**
     * Get count of all todos.
     */
    public int getTodoCount() {
        return todos.size();
    }

    /**
     * Get count of completed todos.
     */
    public int getCompletedCount() {
        return (int) todos.stream().filter(Todo::isCompleted).count();
    }

    /**
     * Get count of pending todos.
     */
    public int getPendingCount() {
        return (int) todos.stream().filter(t -> !t.isCompleted()).count();
    }

    /**
     * Get todos filtered by completion status.
     */
    public List<Todo> getTodosByStatus(boolean completed) {
        return todos.stream()
                .filter(t -> t.isCompleted() == completed)
                .collect(Collectors.toList());
    }

    /**
     * Get todos filtered by priority.
     */
    public List<Todo> getTodosByPriority(String priority) {
        return todos.stream()
                .filter(t -> t.getPriority().equalsIgnoreCase(priority))
                .collect(Collectors.toList());
    }

    /**
     * Clear all completed todos.
     */
    public int clearCompleted() {
        List<Todo> completed = getTodosByStatus(true);
        todos.removeAll(completed);
        return completed.size();
    }
}
