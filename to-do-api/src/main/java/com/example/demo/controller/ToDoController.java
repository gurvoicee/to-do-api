package com.example.demo.controller;

import com.example.demo.assembler.ToDoAssembler;
import com.example.demo.entity.ToDo;
import com.example.demo.repository.ToDoRepository;
import org.hibernate.EntityMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import java.util.List;

@RestController
public class ToDoController {
    @Autowired
    ToDoRepository repository;
    @Autowired
    ToDoAssembler assembler;
    @PostMapping("/todos")
    public ResponseEntity<EntityModel<ToDo>> create(@RequestBody ToDo toDo){
        repository.save(toDo);
        return ResponseEntity.ok(assembler.toModel(toDo));
    }
    @GetMapping("/todos/{id}")
    public EntityModel<ToDo> getOne(@PathVariable Long id){
        ToDo toDo = repository.findById(id).orElseThrow(()->new RuntimeException());
        return assembler.toModel(toDo);
    }
    @GetMapping("/todos")
    public CollectionModel<EntityModel<ToDo>> getAll(){
        List<ToDo> toDoList = repository.findAll();
        return assembler.toCollectionModel(toDoList);
    }
    @PutMapping("/todos/{id}")
    public EntityModel<ToDo> update(@PathVariable Long id,@RequestBody ToDo newToDo){
        ToDo toDo = repository.findById(id).orElseThrow(()->new RuntimeException());
        toDo.setTodoDescription(newToDo.getTodoDescription());
        toDo.setTodoTitle(newToDo.getTodoTitle());
        repository.save(toDo);
        return assembler.toModel(toDo);
    }

    @PutMapping("/todos/{id}/complete")
    public EntityModel<ToDo> updateComplete(@PathVariable Long id){
        ToDo toDo = repository.findById(id).orElseThrow(()->new RuntimeException());
        toDo.setComplete(true);
        repository.save(toDo);
        return assembler.toModel(toDo);
    }
}
