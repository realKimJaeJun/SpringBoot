package kr.co.Todo.service;

import kr.co.Todo.dao.MainDAO;
import kr.co.Todo.vo.TodoVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MainService {

    private MainDAO dao;

    public int createTodoList(TodoVO vo) {
        return dao.createTodoList(vo);
    }

    public Map<Integer, List<TodoVO>> selectTodoList() {
        List<TodoVO> list = dao.selectTodoList();
        return list.stream().collect(Collectors.groupingBy(TodoVO::getStatus));
    }

    public int updateTodoList(Map<String, String> data) {
        return dao.updateTodoList(data);
    }

    public int deleteTodoList(TodoVO vo) {
        return dao.deleteTodoList(vo);
    }
}
