package kr.co.Sboard.controller;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartRequest;

import kr.co.Sboard.entity.UserEntity;
import kr.co.Sboard.security.MyUserDetails;
import kr.co.Sboard.service.ArticleService;
import kr.co.Sboard.vo.ArticleVO;
import kr.co.Sboard.vo.FileVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ArticleController {

	@Autowired
	private ArticleService service;
	
	/**
	 * 글 목록 컨트롤러
	 * @return
	 */
	@GetMapping("list")
	public String list(@AuthenticationPrincipal MyUserDetails myUser, Model model, String pg) {
		UserEntity user = myUser.getUser();
		
		int currentPage = service.getCurrentPage(pg);
		int start = service.getLimitStart(currentPage);
		long total = service.getTotalCount();
		int lastPage = service.getLastPageNum(total);
		int pageStartNum = service.getPageStartNum(total, start);
		int groups[] = service.getPageGroup(currentPage, lastPage);
		
		List<ArticleVO> articles = service.selectArticles(start);
		
		model.addAttribute("user", user);
		model.addAttribute("articles", articles);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("lastPage", lastPage);
		model.addAttribute("pageStartNum", pageStartNum);
		model.addAttribute("groups", groups);
		
		return "list";
	}
	
	@GetMapping("modify")
	public String modify(@RequestParam("no") int no, Model model) {
		ArticleVO article = service.selectArticle(no);
		model.addAttribute("article", article);
		return "modify";
	}
	
	@PostMapping("modify")
	public String modify(@RequestParam("no") int no, ArticleVO vo, HttpServletRequest req, @AuthenticationPrincipal MyUserDetails myUser, Model model) {
		ArticleVO article = service.selectArticle(no);
		model.addAttribute("article", article);
		
		vo.setUid(myUser.getUser().getUid());
		vo.setRegip(req.getRemoteAddr());
		service.updateArticle(vo);
		return "redirect:/view";
	}
	
	@GetMapping("view")
	public String view(@RequestParam("no") int no, Model model) {
		ArticleVO article = service.selectArticle(no);
		model.addAttribute("article", article);
		return "view";
	}
	
	@GetMapping("write")
	public String write() {
		return "write";
	}
	
	@PostMapping("write")
	public String write(ArticleVO vo, HttpServletRequest req, @AuthenticationPrincipal MyUserDetails myUser) {
		vo.setUid(myUser.getUser().getUid());
		vo.setRegip(req.getRemoteAddr());
		service.insertArticle(vo);
		return "redirect:/list";
	}
	
	@GetMapping("download")
	public ResponseEntity<Resource> download(int fno) throws IOException {
		FileVO vo = service.selectFile(fno);
		ResponseEntity<Resource> respEntity = service.fileDownload(vo);
		
		return respEntity;
	}
}
