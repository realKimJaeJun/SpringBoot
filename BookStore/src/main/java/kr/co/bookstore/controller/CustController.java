package kr.co.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.bookstore.service.CustService;
import kr.co.bookstore.vo.CustVO;

@Controller
public class CustController {

	@Autowired
	private CustService service;
	
	@GetMapping("/customer/list")
	public String list(Model model) {
		List<CustVO> custs = service.selectCusts();
		model.addAttribute("custs", custs);
		return "/customer/list";
	}
	
	@GetMapping("/customer/register")
	public String register() {
		return "/customer/register";
	}
	
	@PostMapping("/customer/register")
	public String register(CustVO vo) {
		service.insertCust(vo);
		return "redirect:/customer/list";
	}
	
	@GetMapping("/customer/modify")
	public String modify(int custId, Model model) {
		CustVO cust = service.selectCust(custId);
		model.addAttribute("cust", cust);
		return "/customer/modify";
	}
	
	@PostMapping("/customer/modify")
	public String modify(CustVO vo) {
		service.updateCust(vo);
		return "redirect:/customer/list";
	}
	
	@GetMapping("/customer/delete")
	public String delete(int custId) {
		service.deleteCust(custId);
		return "redirect:/customer/list";
	}
}
