package com.example.pomodoro.dto;

import java.util.List;

public class Tareas {
    private List<Todos> todos;
    private int total;

    public List<Todos> getTodos() {
        return todos;
    }

    public void setTodos(List<Todos> todos) {
        this.todos = todos;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
