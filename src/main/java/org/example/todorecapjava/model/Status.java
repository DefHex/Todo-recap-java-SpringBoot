package org.example.todorecapjava.model;
import lombok.Getter;

@Getter
public enum Status {
    OPEN("pending"),
    IN_PROGRESS("doing"),
    DONE("done");
    private String status;

    Status(String status) {
        this.status = status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
