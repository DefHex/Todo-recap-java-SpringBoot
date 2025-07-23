package org.example.todorecapjava.repository;

import org.example.todorecapjava.model.Todo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends MongoRepository<Todo,String> {
    String id(String id);
}
