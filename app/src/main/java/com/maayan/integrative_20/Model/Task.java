package com.maayan.integrative_20.Model;

public class Task {

    private String status;
    private String content;

    public Task() {
    }

    public Task(String status, String content) {
        this.status = status;
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Task{" +
                "status='" + status + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
