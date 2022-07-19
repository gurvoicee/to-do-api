package com.example.demo.assembler;

import com.example.demo.controller.ToDoController;
import com.example.demo.entity.ToDo;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ToDoAssembler implements RepresentationModelAssembler<ToDo, EntityModel<ToDo>> {
    @Override
    public EntityModel<ToDo> toModel(ToDo entity) {
        EntityModel<ToDo> toDoEntityModel = EntityModel.of(entity);
        toDoEntityModel.add(linkTo(methodOn(ToDoController.class).getOne(entity.getId())).withRel("self"));
        toDoEntityModel.add(linkTo(methodOn(ToDoController.class).getAll()).withRel("todolists"));
        if(entity.isComplete()==false){
            toDoEntityModel.add(linkTo(methodOn(ToDoController.class).update(entity.getId(),entity)).withRel("update"));
        }
        return toDoEntityModel;
    }
    @Override
    public CollectionModel<EntityModel<ToDo>> toCollectionModel(Iterable<? extends ToDo> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }
}
