import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.junit.jupiter.api.Test;
import org.mallon.TodoItem;
import org.mallon.TodoList;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TodoListTest {
    TodoList list;

    @Mock
    TodoItem item, item1, item2;

    @BeforeEach
    void setup() {
        list = new TodoList("Test List");

        item = mock(TodoItem.class);
        item1 = mock(TodoItem.class);
        item2 = mock(TodoItem.class);
        when(item1.isComplete()).thenReturn(false);
        when(item.isComplete()).thenReturn(false);
        when(item2.isComplete()).thenReturn(false);
    }

    @Test
    public void testAddTodo() {
        TodoList list = new TodoList("Test List");
        boolean result = list.addTodo(item);
        Iterable<TodoItem> allTodosInTheList = list.getAllTodos();
        List<TodoItem> todoListItems = new ArrayList<>();
        for (TodoItem item: allTodosInTheList) {
            todoListItems.add(item);
        }
        assertTrue(result, "Should return true, when item is added");
        assertTrue(todoListItems.contains(item), "The list should contain item");

    }

    @Test
    public void testGetAllTodos() {
        list.addTodo(item);
        list.addTodo(item1);
        list.addTodo(item2);
        when(item.isComplete()).thenReturn(true);

        Iterable<TodoItem> allTodosInTheList = list.getAllTodos();
        List<TodoItem> todoListItems = new ArrayList<>();
        for (TodoItem item: allTodosInTheList) {
            todoListItems.add(item);
        }
        assertEquals(3, todoListItems.size(), "getAllTodos size should be 3");
        assertTrue(todoListItems.contains(item), "The list should contain item");
    }

    @Test
    public void testGetTodos() {
        list.addTodo(item);
        list.addTodo(item1);
        list.addTodo(item2);
        when(item.isComplete()).thenReturn(true);

        Iterable<TodoItem> completedList = list.getTodos();
        List<TodoItem> incompleteItems = new ArrayList<>();
        for (TodoItem item: completedList) {
            incompleteItems.add(item);
        }
        assertEquals(2, incompleteItems.size(), "getTodos() should return all incomplete todos");
        assertFalse(incompleteItems.contains(item), "The list should not contain the complete item");

    }

    @Test
    public void testCompleteAll() {
        when(item1.isComplete()).thenReturn(true);
        list.addTodo(item);
        list.addTodo(item1);
        int completedTodos = list.completeAll();

        verify(item).setComplete();
        verify(item1, never()).setComplete();  // This one is already complete.
        assertEquals(1, completedTodos, "completeAll() should return all incomplete todos");
    }

    @Test
    public void testGetTodosThatAreComplete() {
        list.addTodo(item);
        list.addTodo(item1);
        list.addTodo(item2);
        when(item.isComplete()).thenReturn(true);
        when(item2.isComplete()).thenReturn(true);

        Iterable<TodoItem> completedList = list.getTodos(true);
        List<TodoItem> completedItems = new ArrayList<>();
        for (TodoItem item: completedList) {
            completedItems.add(item);
        }
        assertEquals(2, completedItems.size(), "getTodos(true) should return all complete todos");
        assertFalse(completedItems.contains(item1), "The list should not contain the incomplete item1");
    }
}
