package com.todoapp.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Todo model class.
 */
public class TodoTest {

    private Todo todo;

    @BeforeEach
    void setUp() {
        todo = new Todo(1, "Test Task", "Test Description");
    }

    @Test
    void testTodoCreation() {
        assertNotNull(todo);
        assertEquals(1, todo.getId());
        assertEquals("Test Task", todo.getTitle());
        assertEquals("Test Description", todo.getDescription());
        assertFalse(todo.isCompleted());
        assertEquals("MEDIUM", todo.getPriority());
        assertNotNull(todo.getCreatedAt());
    }

    @Test
    void testTodoCreationWithPriority() {
        Todo highPriorityTodo = new Todo(2, "Urgent Task", "Very important", "HIGH");
        assertEquals("HIGH", highPriorityTodo.getPriority());
    }

    @Test
    void testDefaultConstructor() {
        Todo defaultTodo = new Todo();
        assertFalse(defaultTodo.isCompleted());
        assertEquals("MEDIUM", defaultTodo.getPriority());
        assertNotNull(defaultTodo.getCreatedAt());
    }

    @Test
    void testSetters() {
        todo.setId(10);
        todo.setTitle("Updated Title");
        todo.setDescription("Updated Description");
        todo.setCompleted(true);
        todo.setPriority("HIGH");

        assertEquals(10, todo.getId());
        assertEquals("Updated Title", todo.getTitle());
        assertEquals("Updated Description", todo.getDescription());
        assertTrue(todo.isCompleted());
        assertEquals("HIGH", todo.getPriority());
    }

    @Test
    void testToggleCompleted() {
        assertFalse(todo.isCompleted());
        todo.setCompleted(true);
        assertTrue(todo.isCompleted());
        todo.setCompleted(false);
        assertFalse(todo.isCompleted());
    }

    @Test
    void testEquals() {
        Todo sameTodo = new Todo(1, "Different Title", "Different Desc");
        Todo differentTodo = new Todo(2, "Test Task", "Test Description");

        assertEquals(todo, sameTodo);
        assertNotEquals(todo, differentTodo);
        assertNotEquals(todo, null);
        assertNotEquals(todo, "string");
        assertEquals(todo, todo);
    }

    @Test
    void testHashCode() {
        Todo sameTodo = new Todo(1, "Different Title", "Different Desc");
        assertEquals(todo.hashCode(), sameTodo.hashCode());
    }

    @Test
    void testToString() {
        String str = todo.toString();
        assertTrue(str.contains("Test Task"));
        assertTrue(str.contains("MEDIUM"));
    }
}
