package org.example.todorecapjava.model;
import lombok.With;
@With
public record Todo(String description, String id,  Status status) {}