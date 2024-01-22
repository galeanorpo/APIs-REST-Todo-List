package com.devjp.todolist.controller;

import com.devjp.todolist.model.Task;
import com.devjp.todolist.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/todolist")
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

    @GetMapping(path = "/")
    public ResponseEntity<?> getTasks(){
        Iterable<Task> tasks = this.todoRepository.findAll();
        return ResponseEntity.ok(tasks);
    }

    @PostMapping(path = "/add")
    public ResponseEntity<?>  saveTask(@RequestBody Task task){
        Optional<Task> resTask = this.todoRepository.findByTitle(task.getTitle());
        if(!resTask.isPresent()){
            this.todoRepository.save(task);
            return ResponseEntity.ok(task);
        }
        return ResponseEntity.badRequest().body("This name is not avaible");
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody Task task){
        if(this.todoRepository.existsById(id)){
            Task updateTask = this.todoRepository.findById(id).get();
            updateTask.setTitle(task.getTitle());
            updateTask.setDescription(task.getDescription());
            this.todoRepository.save(updateTask);
            return ResponseEntity.ok(task);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id){
        if(this.todoRepository.existsById(id)){
            this.todoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.badRequest().body("This id not exist...");
        }
    }
}
