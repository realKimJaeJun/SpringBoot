package kr.co.farmstory.service;

import kr.co.farmstory.dao.UserDAO;
import kr.co.farmstory.repository.UserRepo;
import kr.co.farmstory.vo.TermsVO;
import kr.co.farmstory.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserDAO dao;

    @Autowired
    private UserRepo repo;

    @Autowired
    private PasswordEncoder encoder;

    /**
     * 가입 약관 불러오기
     * @return
     */
    public TermsVO selectTerms(){
        return dao.selectTerms();
    }

    /**
     * 유저 회원가입
     * @param vo
     * @return
     */
    public int insertUser(UserVO vo){
        vo.setPass(encoder.encode(vo.getPass1()));
        return dao.insertUser(vo);
    }

    /**
     * 가입 유저 아이디 불러오기
     * @param uid
     * @return
     */
    public UserVO selectUser(String uid){
        return dao.selectUser(uid);
    }
    public List<UserVO> selectUsers(){
        return dao.selectUsers();
    }
}
