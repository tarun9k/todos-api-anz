package com.todos.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todos.exceptions.ResourceNotFoundException;
import com.todos.models.Todo;
import com.todos.repositories.TodosRepository;
import com.todos.services.TodosService;

@Service
public class TodosServiceDBImpl implements TodosService {

	@Autowired
	private TodosRepository todosRepository;

	@Override
	public List<Todo> getAllTodos() {
		return todosRepository.findAll();
	}

	@Override
	public Todo getTodoById(int id) {
		/*
		 * Optional<Todo> todo = todosRepository.findById(id); if(todo.isPresent())
		 * return todo.get(); throw new ResourceNotFoundException("todos", "id", id);
		 */
		return todosRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("todos", "id", id));
	}

	

	@Override
	public Todo saveTodo(Todo todo) {
		return todosRepository.save(todo);
	}
	@Override
	public List<Todo> getTodosByUser(String user) {
		return todosRepository.findByUser(user);
	}

	@Override
	public Todo updateTodo(String name, int id, Todo todo) {
		Todo existingTodo = todosRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("todos", "id", id));
		if (name.equals(existingTodo.getUser())) {
			existingTodo.setDescription(todo.getDescription());
			existingTodo.setTargetDate(todo.getTargetDate());
			existingTodo.setDone(todo.isDone());
			// save to db
			todosRepository.save(existingTodo);
			return existingTodo;
		} else
			return null;
	}

	@Override
	public boolean deleteTodo(int id) {
		todosRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("todos", "id", id));
		todosRepository.deleteById(id);
		return true;
	}

}
