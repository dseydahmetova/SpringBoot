import org.junit.jupiter.api.Test;
import org.mallon.TodoItem;
import static org.junit.jupiter.api.Assertions.*;

public class TodoItemIntegration {

    @Test
    public void TestSetDescriptionNewValue() {
        TodoItem item = new TodoItem("Title");
        String newDescription = "New description";
        item.setDescription(newDescription);
        assertEquals(newDescription, item.getDescription(), "Description updated");
    }

    @Test
    public void TestSetDescriptionWhenValueSameAsTitle() {
        TodoItem item = new TodoItem("Title");
        String newDescription = "Title";
        item.setDescription(newDescription);
        assertEquals("", item.getDescription(), "Description is the same as the title ");
    }

//    @Test
//    public void TestSetDescriptionWrongValue() {
//        TodoItem item = new TodoItem("Title", "Description");
//        String newDescription = "New description";
//
//        item.setDescription(newDescription);
//
//        assertEquals("Wrong Description", item.getDescription(), "Description should be updated to the new value");    }
//
//}

    @Test
    public void testInitialCompleteState() {
        TodoItem item = new TodoItem("Title");
        assertFalse(item.isComplete(), "Initially, a TodoItem's complete field is false");
    }

    @Test
    public void testSetCompleteWhenFalse() {
        TodoItem item = new TodoItem("Title");
        boolean result = item.setComplete();
        assertEquals(true, result,"If setComplete() is called, the method should return true");
    }

    @Test
    public void testSetCompleteWhenTrue() {
        TodoItem item = new TodoItem("Title", "Description");
        item.setComplete(); // SetComplete() changed to true
        boolean result = item.setComplete(); // calling second time
        assertFalse(result, "If setComplete() is called when complete is already true, the method should return false");
        assertTrue(item.isComplete(), "If setComplete() is called when complete is already true, complete should remain true");
    }
}
