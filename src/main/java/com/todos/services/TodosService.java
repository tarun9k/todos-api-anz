package com.todos.services;

import java.util.List;

import com.todos.models.Todo;

public interface TodosService {
	
	List<Todo> getAllTodos();
	
	Todo getTodoById(int id);
	
	List<Todo> getTodosByUser(String user);
	
	Todo saveTodo(Todo todo);
	
	Todo updateTodo(String name, int id, Todo todo);
	
	boolean deleteTodo(int id);

}
