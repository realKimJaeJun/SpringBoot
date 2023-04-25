package kr.co.Todo.controller;

import kr.co.Todo.service.TodoService;
import kr.co.Todo.vo.TodoVO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TodoController {
    @Autowired
    private TodoService service;

    @GetMapping("/app/todos")
    public List<TodoVO> todos() {
        return service.selectTodoList();
    }

    @PostMapping("/app/todo")
    public void createTodo(@RequestBody TodoVO vo) {
        service.createTodo(vo);
    }



    @DeleteMapping("/app/clear")
    public void clearTodo() {
        service.clearTodo();
    }

}
