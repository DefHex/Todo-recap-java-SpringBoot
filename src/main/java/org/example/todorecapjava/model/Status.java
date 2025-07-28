package org.example.todorecapjava.model;
import lombok.Getter;

@Getter
public enum Status {
    OPEN("pending"),
    IN_PROGRESS("doing"),
    DONE("done");
    private final String status;

    Status(String status) {
        this.status = status;
    }
}
