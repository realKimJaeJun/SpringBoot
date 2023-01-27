package kr.co.farmstory.controller;

import kr.co.farmstory.entity.UserEntity;
import kr.co.farmstory.security.MyUserDetails;
import kr.co.farmstory.service.ArticleService;
import kr.co.farmstory.vo.ArticleVO;
import kr.co.farmstory.vo.FileVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
public class BoardController {

    @Autowired
    private ArticleService service;

    /**
     * 글 목록 메서드
     * @param myUser
     * @param model
     * @param group
     * @param cate
     * @param pg
     * @return
     */
    @GetMapping("board/list")
    public String list(@AuthenticationPrincipal MyUserDetails myUser, Model model, String group, String cate, String pg){
        UserEntity user = myUser.getUser();

        int currentPage = service.getCurrentPage(pg);
        int start = service.getLimitStart(currentPage);
        long total = service.getTotalCount();
        int lastPage = service.getLastPageNum(total);
        int pageStartNum = service.getPageStartNum(total, start);
        int groups[] = service.getPageGroup(currentPage, lastPage);

        List<ArticleVO> articles = service.selectArticles(start);

        model.addAttribute("group", group);
        model.addAttribute("cate", cate);
        model.addAttribute("user", user);
        model.addAttribute("articles", articles);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("lastPage", lastPage);
        model.addAttribute("pageStartNum", pageStartNum);
        model.addAttribute("groups", groups);

        return "board/list";
    }

    /**
     * 글 수정 메서드
     * @param no
     * @param model
     * @param group
     * @param cate
     * @return
     */
    @GetMapping("/board/modify")
    public String modify(@RequestParam("no") int no, Model model, String group, String cate){
        ArticleVO article = service.selectArticle(no);

        model.addAttribute("article", article);
        model.addAttribute("group", group);
        model.addAttribute("cate", cate);
        return "/board/modify";
    }
    /** 글 수정 PostMapping
     * @param no
     * @param vo
     * @param req
     * @param myUser
     * @param model
     * @param group
     * @param cate
     * @return
     */
    @PostMapping("/board/modify")
    public String modify(@RequestParam("no") int no, ArticleVO vo, HttpServletRequest req, @AuthenticationPrincipal MyUserDetails myUser, Model model, String group, String cate){
        ArticleVO article = service.selectArticle(no);

        model.addAttribute("article", article);
        model.addAttribute("group", group);
        model.addAttribute("cate", cate);

        vo.setUid(myUser.getUser().getUid());
        vo.setRegip(req.getRemoteAddr());
        service.updateArticle(vo);
        return "redirect:board/view";
    }

    /**
     * 글 보기 메서드
     * @param model
     * @param group
     * @param cate
     * @param no
     * @return
     */
    @GetMapping("board/view")
    public String view(Model model, String group, String cate, @RequestParam("no") int no){
        ArticleVO article = service.selectArticle(no);

        model.addAttribute("article", article);
        model.addAttribute("group", group);
        model.addAttribute("cate", cate);
        return "board/view";
    }

    /**
     * 글 작성 메서드
     * @param model
     * @param group
     * @param cate
     * @return
     */
    @GetMapping("/board/write")
    public String write(Model model, String group, String cate){
        model.addAttribute("group", group);
        model.addAttribute("cate", cate);
        return "/board/write";
    }

    /**
     * 글 작성  PostMapping
     * @param model
     * @param group
     * @param cate
     * @param vo
     * @param req
     * @param myUser
     * @return
     */
    @PostMapping("/board/write")
    public String write(Model model, String group, String cate, ArticleVO vo, HttpServletRequest req, @AuthenticationPrincipal MyUserDetails myUser){
        vo.setUid(myUser.getUser().getUid());
        vo.setRegip(req.getRemoteAddr());
        service.insertArticle(vo);

        model.addAttribute("group", group);
        model.addAttribute("cate", cate);
        return "redirect:list";
    }

    /**
     * 파일 다운로드
     * @param fno
     * @return
     * @throws IOException
     */
    @GetMapping("download")
    public ResponseEntity<Resource> download(int fno) throws IOException {
        FileVO vo = service.selectFile(fno);
        ResponseEntity<Resource> respEntity = service.fileDownload(vo);

        return respEntity;
    }
}
