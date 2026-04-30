package com.todoapp.service;

import com.todoapp.model.Todo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the TodoService class.
 */
public class TodoServiceTest {

    private TodoService todoService;

    @BeforeEach
    void setUp() {
        todoService = new TodoService();
    }

    // ===== ADD TESTS =====

    @Test
    void testAddTodo() {
        Todo todo = todoService.addTodo("Test Task", "Description", "MEDIUM");
        assertNotNull(todo);
        assertEquals("Test Task", todo.getTitle());
        assertEquals("Description", todo.getDescription());
        assertEquals("MEDIUM", todo.getPriority());
        assertFalse(todo.isCompleted());
    }

    @Test
    void testAddTodoWithNullTitle() {
        assertThrows(IllegalArgumentException.class, () -> {
            todoService.addTodo(null, "Description", "MEDIUM");
        });
    }

    @Test
    void testAddTodoWithEmptyTitle() {
        assertThrows(IllegalArgumentException.class, () -> {
            todoService.addTodo("  ", "Description", "MEDIUM");
        });
    }

    @Test
    void testAddTodoWithNullPriority() {
        Todo todo = todoService.addTodo("Task", "Desc", null);
        assertEquals("MEDIUM", todo.getPriority());
    }

    @Test
    void testAddTodoWithEmptyPriority() {
        Todo todo = todoService.addTodo("Task", "Desc", "");
        assertEquals("MEDIUM", todo.getPriority());
    }

    @Test
    void testAddTodoWithNullDescription() {
        Todo todo = todoService.addTodo("Task", null, "HIGH");
        assertEquals("", todo.getDescription());
    }

    @Test
    void testAddMultipleTodosUniqueIds() {
        Todo t1 = todoService.addTodo("Task 1", "Desc", "LOW");
        Todo t2 = todoService.addTodo("Task 2", "Desc", "HIGH");
        Todo t3 = todoService.addTodo("Task 3", "Desc", "MEDIUM");

        assertNotEquals(t1.getId(), t2.getId());
        assertNotEquals(t2.getId(), t3.getId());
    }

    // ===== GET TESTS =====

    @Test
    void testGetAllTodos() {
        todoService.addTodo("Task 1", "Desc", "LOW");
        todoService.addTodo("Task 2", "Desc", "HIGH");

        List<Todo> todos = todoService.getAllTodos();
        assertEquals(2, todos.size());
    }

    @Test
    void testGetAllTodosEmpty() {
        List<Todo> todos = todoService.getAllTodos();
        assertTrue(todos.isEmpty());
    }

    @Test
    void testGetTodoById() {
        Todo added = todoService.addTodo("Find Me", "Desc", "LOW");
        Optional<Todo> found = todoService.getTodoById(added.getId());

        assertTrue(found.isPresent());
        assertEquals("Find Me", found.get().getTitle());
    }

    @Test
    void testGetTodoByIdNotFound() {
        Optional<Todo> found = todoService.getTodoById(999);
        assertFalse(found.isPresent());
    }

    // ===== UPDATE TESTS =====

    @Test
    void testUpdateTodo() {
        Todo todo = todoService.addTodo("Original", "Old Desc", "LOW");
        boolean updated = todoService.updateTodo(todo.getId(), "Updated", "New Desc", "HIGH");

        assertTrue(updated);
        Optional<Todo> found = todoService.getTodoById(todo.getId());
        assertTrue(found.isPresent());
        assertEquals("Updated", found.get().getTitle());
        assertEquals("New Desc", found.get().getDescription());
        assertEquals("HIGH", found.get().getPriority());
    }

    @Test
    void testUpdateTodoNotFound() {
        boolean updated = todoService.updateTodo(999, "Title", "Desc", "LOW");
        assertFalse(updated);
    }

    @Test
    void testUpdateTodoPartial() {
        Todo todo = todoService.addTodo("Original", "Old Desc", "LOW");
        todoService.updateTodo(todo.getId(), null, "New Desc", null);

        Optional<Todo> found = todoService.getTodoById(todo.getId());
        assertTrue(found.isPresent());
        assertEquals("Original", found.get().getTitle()); // unchanged
        assertEquals("New Desc", found.get().getDescription());
        assertEquals("LOW", found.get().getPriority()); // unchanged
    }

    // ===== TOGGLE TESTS =====

    @Test
    void testToggleTodo() {
        Todo todo = todoService.addTodo("Toggle Me", "Desc", "MEDIUM");
        assertFalse(todo.isCompleted());

        boolean toggled = todoService.toggleTodo(todo.getId());
        assertTrue(toggled);
        assertTrue(todoService.getTodoById(todo.getId()).get().isCompleted());

        todoService.toggleTodo(todo.getId());
        assertFalse(todoService.getTodoById(todo.getId()).get().isCompleted());
    }

    @Test
    void testToggleTodoNotFound() {
        boolean toggled = todoService.toggleTodo(999);
        assertFalse(toggled);
    }

    // ===== DELETE TESTS =====

    @Test
    void testDeleteTodo() {
        Todo todo = todoService.addTodo("Delete Me", "Desc", "MEDIUM");
        assertEquals(1, todoService.getTodoCount());

        boolean deleted = todoService.deleteTodo(todo.getId());
        assertTrue(deleted);
        assertEquals(0, todoService.getTodoCount());
    }

    @Test
    void testDeleteTodoNotFound() {
        boolean deleted = todoService.deleteTodo(999);
        assertFalse(deleted);
    }

    // ===== COUNT TESTS =====

    @Test
    void testCounts() {
        todoService.addTodo("Task 1", "Desc", "LOW");
        todoService.addTodo("Task 2", "Desc", "HIGH");
        Todo t3 = todoService.addTodo("Task 3", "Desc", "MEDIUM");

        todoService.toggleTodo(t3.getId()); // mark as completed

        assertEquals(3, todoService.getTodoCount());
        assertEquals(1, todoService.getCompletedCount());
        assertEquals(2, todoService.getPendingCount());
    }

    // ===== FILTER TESTS =====

    @Test
    void testGetTodosByStatus() {
        todoService.addTodo("Pending 1", "Desc", "LOW");
        Todo completed = todoService.addTodo("Completed 1", "Desc", "HIGH");
        todoService.toggleTodo(completed.getId());

        List<Todo> pendingList = todoService.getTodosByStatus(false);
        List<Todo> completedList = todoService.getTodosByStatus(true);

        assertEquals(1, pendingList.size());
        assertEquals(1, completedList.size());
        assertEquals("Completed 1", completedList.get(0).getTitle());
    }

    @Test
    void testGetTodosByPriority() {
        todoService.addTodo("Low Task", "Desc", "LOW");
        todoService.addTodo("High Task 1", "Desc", "HIGH");
        todoService.addTodo("High Task 2", "Desc", "HIGH");

        List<Todo> highPriority = todoService.getTodosByPriority("HIGH");
        assertEquals(2, highPriority.size());

        List<Todo> lowPriority = todoService.getTodosByPriority("LOW");
        assertEquals(1, lowPriority.size());
    }

    // ===== CLEAR COMPLETED TESTS =====

    @Test
    void testClearCompleted() {
        todoService.addTodo("Keep", "Desc", "LOW");
        Todo done1 = todoService.addTodo("Done 1", "Desc", "HIGH");
        Todo done2 = todoService.addTodo("Done 2", "Desc", "MEDIUM");

        todoService.toggleTodo(done1.getId());
        todoService.toggleTodo(done2.getId());

        int cleared = todoService.clearCompleted();
        assertEquals(2, cleared);
        assertEquals(1, todoService.getTodoCount());
        assertEquals("Keep", todoService.getAllTodos().get(0).getTitle());
    }

    @Test
    void testClearCompletedWhenNone() {
        todoService.addTodo("Not Done", "Desc", "LOW");
        int cleared = todoService.clearCompleted();
        assertEquals(0, cleared);
        assertEquals(1, todoService.getTodoCount());
    }
}
