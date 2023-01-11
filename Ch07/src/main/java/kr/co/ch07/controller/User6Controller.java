package kr.co.ch07.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.ch07.service.User6Service;
import kr.co.ch07.vo.User6VO;


@Controller
@RequestMapping("/user6")
public class User6Controller {

	@Autowired
	private User6Service service;
	
	@GetMapping("/list")
	public String list(Model model) {
		
		List<User6VO> users = service.selectUser6s();
		model.addAttribute("users", users);
		return "/user6/list";
	}
	
	@GetMapping("/register")
	public String register() {
		return "/user6/register";
	}
	
	@PostMapping("/register")
	public String register(User6VO vo) {
		service.insertUser6(vo);
		return "redirect:/user6/list";
	}
	
	@GetMapping("/modify")
	public String modify(String uid, Model model) {
		User6VO user = service.selectUser6(uid);
		model.addAttribute("user", user);
		return "/user6/modify";
	}
	
	@PostMapping("/modify")
	public String modify(User6VO vo) {
		service.updateUser6(vo);
		return "redirect:/user6/list";
	}
	
	@GetMapping("/delete")
	public String delete(String uid) {
		service.deleteUser6(uid);
		return "redirect:/user6/list";
	}
	
	
}
