import org.junit.jupiter.api.Test;
import org.mallon.TodoItem;
import org.mallon.TodoList;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class TodoListIntegration {
    @Test
    public void testAddTodo() {
        TodoItem item = new TodoItem("Test Item");
        TodoList list = new TodoList("Test List");
        boolean result = list.addTodo(item);
        assertTrue(result, "Should return true, when item is added");
    }

    @Test
    public void testGetAllTodos() {
        TodoList list = new TodoList("Test List");
        TodoItem item1 = new TodoItem("Test Item1");
        TodoItem item2 = new TodoItem("Test Item2");

        list.addTodo(item1);
        list.addTodo(item2);

        Iterable<TodoItem> allTodosInTheList = list.getAllTodos();
        List<TodoItem> todoListItems = new ArrayList<>();
        for (TodoItem item: allTodosInTheList) {
            todoListItems.add(item);
        }
        assertEquals(2, todoListItems.size(), "getAllTodos size should be 2");
        assertTrue(todoListItems.contains(item1), "The list should contain item1");
        assertTrue(todoListItems.contains(item2), "The list should contain item2");
    }


    @Test
    public void testGetTodosThatAreComplete() {
        TodoList list = new TodoList("Test List");
        TodoItem item1 = new TodoItem("Test Item1");
        TodoItem item2 = new TodoItem("Test Item2");

        list.addTodo(item1);
        list.addTodo(item2);
        item1.setComplete();

        Iterable<TodoItem> completedList = list.getTodos(true);
        List<TodoItem> completedItems = new ArrayList<>();
        for (TodoItem item: completedList) {
            completedItems.add(item);
        }
        assertEquals(1, completedItems.size(), "getTodos(true) should return all complete todos");
        assertTrue(completedItems.contains(item1), "The list should contain the complete item1");

    }

    @Test
    public void testGetTodosThatAreIncomplete() {
        TodoList list = new TodoList("Test List");
        TodoItem item1 = new TodoItem("Test Item1");
        TodoItem item2 = new TodoItem("Test Item2");

        list.addTodo(item1);
        list.addTodo(item2);
        item1.setComplete();

        Iterable<TodoItem> incompleteList = list.getTodos(false);
        List<TodoItem> incompleteItems = new ArrayList<>();
        for (TodoItem item: incompleteList) {
            incompleteItems.add(item);
        }
        assertEquals(1, incompleteItems.size(), "getTodos(false) should return all incomplete todos");
        assertTrue(incompleteItems.contains(item2), "The list should contain the complete item2");
    }


    @Test
    public void testGetTodosIncompleteWithZeroArgument() {
        TodoList list = new TodoList("Test List");
        TodoItem item1 = new TodoItem("Test Item1");
        TodoItem item2 = new TodoItem("Test Item2");

        list.addTodo(item1);
        list.addTodo(item2);
        item1.setComplete();

        Iterable<TodoItem> incompleteList = list.getTodos();
        List<TodoItem> incompleteItems = new ArrayList<>();
        for (TodoItem item: incompleteList) {
            incompleteItems.add(item);
        }
        assertEquals(1, incompleteItems.size(), "getTodos() should return all incomplete todos");
        assertTrue(incompleteItems.contains(item2), "The list should contain the complete item2");
    }

}
