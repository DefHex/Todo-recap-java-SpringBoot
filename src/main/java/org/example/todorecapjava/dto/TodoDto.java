package org.example.todorecapjava.dto;

import org.example.todorecapjava.model.Status;

public record TodoDto(String description, Status status) {}