package sample.model;

import java.sql.Timestamp;

public class Task {

    private int userID;
    private int taskID;
    private String task;
    private Timestamp date;
    private String description;

    public int getUserID() { return userID; }

    public void setUserID(int userID) { this.userID = userID; }

    public int getTaskID() {return taskID; }

    public void setTaskID(int taskID) {  this.taskID = taskID; }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Task() {
    }

    public Task(String task, Timestamp date, String description, int userID) {
        this.task = task;
        this.date = date;
        this.description = description;
        this.userID = userID;
    }
}
