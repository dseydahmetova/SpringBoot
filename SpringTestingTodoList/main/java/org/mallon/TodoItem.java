package org.mallon;

public class TodoItem {
    public boolean isComplete() {
        return complete;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    boolean complete;
    String title;
    String description;

    public TodoItem(String title) {
        this(title, "");
    }

    public TodoItem(String title, String description) {
        this.title = title;
        this.description = description;
        this.complete = false;
    }

    public void setDescription(String description) {
        if (!description.equals(title)) {
            this.description = description;
        }
    }

    public boolean setComplete() {
//        if (!this.complete) { this.complete = true; return true; }
//        else { return false; }
        return true;
    }
}
