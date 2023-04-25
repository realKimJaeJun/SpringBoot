package kr.co.Todo.dao;

import kr.co.Todo.vo.TodoVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TodoDAO {
    public List<TodoVO> selectTodoList();

    public int createTodo(TodoVO vo);

    public TodoVO selectTodo(int no);

    public void deleteTodo(int no);

    public void clearTodo();
}
