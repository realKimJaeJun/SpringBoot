package kr.co.Todo.service;

import kr.co.Todo.dao.TodoDAO;
import kr.co.Todo.vo.TodoVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

    private TodoDAO dao;

    public List<TodoVO> selectTodoList() {
        return dao.selectTodoList();
    }

    public int createTodo(TodoVO vo) {
        return dao.createTodo(vo);
    }

    public TodoVO selectTodo(int no) {
        return dao.selectTodo(no);
    }

    public void deleteTodo(int no) {
        dao.deleteTodo(no);
    }

    public void clearTodo() {
        dao.clearTodo();
    }
}
