package kr.co.Sboard.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.Sboard.service.UserService;
import kr.co.Sboard.vo.TermsVO;
import kr.co.Sboard.vo.UserVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class UserController {
	
	@Autowired
	private UserService service;

	/**
	 * 로그인 컨트롤러
	 * @return
	 */
	@GetMapping("user/login")
	public String login() {
		return "user/login";
	}
	
	@PostMapping("/user/login")
	public String login(String uid, HttpSession session) {
		
		UserVO user = service.selectUser(uid);
		
		if(user != null) {
			// 회원이 맞을 경우
			session.setAttribute("sessUser", user);
			return "redirect:/list";
			
		}else {
			// 회원이 아닌 경우
			return "redirect:/user/login?success=100";
		}
	}
	
	/**
	 * 회원가입 컨트롤러
	 * @return
	 */
	
	@GetMapping("user/register")
	public String register() {
		return "user/register";
	}
	
	@PostMapping("user/register")
	public String register(UserVO vo, HttpServletRequest req) {
		vo.setRegip(req.getRemoteAddr()); // regip 불러오기
		int result = service.insertUser(vo);
		return "redirect:/user/login?success="+result;
	}
	
	/**
	 * 가입약관 컨트롤러
	 * @param model
	 * @return
	 */
	@GetMapping("user/terms")
	public String terms(Model model) {
		TermsVO vo = service.selectTerms();
		model.addAttribute(vo);
		return "user/terms";
	}
	
	/**
	 * 중복체크를 위한 DB의 컬럼 count
	 * @param uid
	 * @return
	 */
	@ResponseBody
	@GetMapping("user/checkUid")
	public Map<String, Integer> checkUid(@RequestParam("uid") String uid) {
		
		log.info("uid : " + uid);
		int result = service.countByUid(uid);
		
		Map<String, Integer> resultMap = new HashMap<>();
		resultMap.put("result", result);
		
		return resultMap;
	}
	
	@ResponseBody
	@GetMapping("user/checkNick")
	public Map<String, Integer> checkNick(@RequestParam("nick") String nick) {
		
		log.info("nick : " + nick);
		int result = service.countByNick(nick);
		
		Map<String, Integer> resultMap = new HashMap<>();
		resultMap.put("result", result);
		
		return resultMap;
	}
	
	@ResponseBody
	@GetMapping("user/checkHp")
	public Map<String, Integer> checkHp(@RequestParam("hp") String hp) {
		
		log.info("hp : " + hp);
		int result = service.countByHp(hp);
		
		Map<String, Integer> resultMap = new HashMap<>();
		resultMap.put("result", result);
		
		return resultMap;
	}
}
