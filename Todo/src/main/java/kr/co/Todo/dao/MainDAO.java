package kr.co.Todo.dao;

import kr.co.Todo.vo.TodoVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface MainDAO {

    public int createTodoList(TodoVO vo);

    public List<TodoVO> selectTodoList();

    public int updateTodoList(Map<String, String> data);

    public int deleteTodoList(TodoVO vo);
}
