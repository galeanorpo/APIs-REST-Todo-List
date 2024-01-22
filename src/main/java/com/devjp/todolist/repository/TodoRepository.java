package com.devjp.todolist.repository;

import com.devjp.todolist.model.Task;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TodoRepository extends CrudRepository<Task, Long> {
    Optional<Task> findByTitle(String title);
}
