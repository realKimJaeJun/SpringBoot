package kr.co.ch09.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.ch09.service.User1Service;
import kr.co.ch09.vo.User1VO;

@Controller
public class User1Controller {

	@Autowired
	private User1Service service;
	
	@ResponseBody
	@CrossOrigin(origins = "*")
	@GetMapping("/user1s")
	public List<User1VO> list1() {
		List<User1VO> users = service.selectUser1s();
		return users;
	}
	
	@ResponseBody
	@GetMapping("/user1")
	public User1VO list2(String uid) {
		return service.selectUser1(uid);
	}
	
	@ResponseBody
	@GetMapping("/user1/{id}")
	public User1VO list3(@PathVariable("id") String uid) {
		return service.selectUser1(uid);
	}
	
	@ResponseBody
	@CrossOrigin(origins = "*")
	@PostMapping("/user1")
	public Map<String, Integer> register(@RequestBody User1VO vo) {		
		int result = service.insertUser1(vo);
		
		Map<String, Integer> resultMap = new HashMap<>();
		resultMap.put("result", result);
		
		return resultMap;
	}
	
	@ResponseBody
	@CrossOrigin(origins = "*")
	@PutMapping("/user1")
	public List<User1VO> modify(@RequestBody User1VO vo) {
		int result = service.updateUser1(vo);
		
		return service.selectUser1s();
	}
	
	@ResponseBody
	@CrossOrigin(origins = "*")
	@DeleteMapping("/user1/{id}")
	public List<User1VO> delete(@PathVariable("id") String uid) {
		int result = service.deleteUser1(uid);

		return service.selectUser1s();
	}
	
}
