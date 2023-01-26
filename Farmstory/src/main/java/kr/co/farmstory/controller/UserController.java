package kr.co.farmstory.controller;

import kr.co.farmstory.service.UserService;
import kr.co.farmstory.vo.TermsVO;
import kr.co.farmstory.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    private UserService service;

    /**
     * 로그인 컨트롤러(selectUser, selectUsers)
     * @return
     */
    @GetMapping("user/login")
    public String login(){
        return "user/login";
    }
    @PostMapping("user/login")
    public String login(String uid, HttpSession session){
        UserVO user = service.selectUser(uid);
        if(user != null){
            // 회원이 맞을 경우
            session.setAttribute("sessUser", user);
            return "redirect:/";
        }else {
            // 회원이 아닌 경우
            return "redirect:/user/login?success=100";
        }
    }

    /**
     * 회원가입 컨트롤러 (insertUser)
     * @return
     */
    @GetMapping("user/register")
    public String register(){
        return "user/register";
    }
    @PostMapping("user/register")
    public String register(UserVO vo, HttpServletRequest req){
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
    public String terms(Model model){
        TermsVO vo = service.selectTerms();
        model.addAttribute(vo);
        return "user/terms";
    }
}
