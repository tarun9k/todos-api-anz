package com.todos.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todos.exceptions.ResourceNotFoundException;
import com.todos.models.Todo;
import com.todos.services.TodosService;

@CrossOrigin(origins = "http://localhost:4200", 
methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE} , maxAge = 3600)
@RestController
@RequestMapping("/api/v1/todos")
public class TodosController {
	
	Logger logger = LoggerFactory.getLogger(TodosController.class);
	
	@Autowired
	private TodosService todosService;
	
	@GetMapping
	public List<Todo> getAllTodos() {
		/*
		 * logger.trace("Log Level Trace"); logger.debug("Log Level Debug");
		 * logger.info("Log Level Info"); logger.warn("Log Level Warn");
		 * logger.error("Log Level Error");
		 */
		return todosService.getAllTodos();
	}
	
	@GetMapping("user/{name}")
	public List<Todo> getTodosByUser(@PathVariable String name){
		return todosService.getTodosByUser(name);
	}
	
	@GetMapping("{id}")	
	public Todo getTodoById(@PathVariable int id) {
		return todosService.getTodoById(id);
	}
	
	@PostMapping
	public ResponseEntity<Todo> saveTodo(@RequestBody Todo todo) {
		Todo newTodo = todosService.saveTodo(todo);
		return new ResponseEntity<Todo>(newTodo,HttpStatus.CREATED);
	}
	
	@PutMapping("user/{name}/{id}")
	public ResponseEntity<Todo> updateTodo(@PathVariable String name, @PathVariable int id, @RequestBody Todo todo){
		Todo updatedTodo = todosService.updateTodo(name, id, todo);
		if (updatedTodo == null) {
			logger.warn("The todo object is null and throwing resource not found exception");
			throw new ResourceNotFoundException("todos", "name", name);
		}
		return new ResponseEntity<Todo>(updatedTodo, HttpStatus.OK);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Boolean> deleteTodo(@PathVariable int id){
		boolean result = todosService.deleteTodo(id);
		if(!result) {
			throw new ResourceNotFoundException("todos", "id", id);
		}
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}

}







