package kr.co.Todo.controller;

import kr.co.Todo.service.MainService;
import kr.co.Todo.vo.TodoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    @Autowired
    private MainService service;

    @GetMapping({"/", "/index"})
    public String index(Model model) {
        Map<Integer, List<TodoVO>> data = service.selectTodoList();

        model.addAttribute("data", data);
        return "index";
    }

    @ResponseBody
    @PostMapping("todoUpdate")
    public Map<String, Object> todoUpdate(@RequestBody Map<String, String> data) {
        int result = 0;

        result = service.updateTodoList(data);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("result", result);

        return resultMap;
    }

    @ResponseBody
    @PostMapping("todoCreate")
    public Map<String, Object> todoCreate(@RequestBody TodoVO vo) {
        int result = 0;

        result = service.createTodoList(vo);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("result", result);
        resultMap.put("vo", vo);

        return resultMap;
    }

    @ResponseBody
    @PostMapping("todoDelete")
    public Map<String, Object> todoDelete(@RequestBody TodoVO vo) {
        int result = 0;

        result = service.deleteTodoList(vo);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("result", result);

        return resultMap;
    }
}
