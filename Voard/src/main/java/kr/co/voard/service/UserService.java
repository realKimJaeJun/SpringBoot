package kr.co.voard.service;

import kr.co.voard.dao.UserDAO;
import kr.co.voard.repository.UserRepo;
import kr.co.voard.vo.TermsVO;
import kr.co.voard.vo.UserVO;
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
    private PasswordEncoder passwordEncoder;

    public void insertUser(UserVO vo) {

        String passEncoded = passwordEncoder.encode(vo.getPass1());
        vo.setPass(passEncoded);

        dao.insertUser(vo);
    }

    // 아이디 중복체크
    public int countUid(String uid){
        return repo.countByUid(uid);
    }

    public TermsVO selectTerms() {
        return dao.selectTerms();
    }
    public UserVO selectUser(String uid) {
        return dao.selectUser(uid);
    }
    public List<UserVO> selectUsers() {
        return dao.selectUsers();
    }
    public void updateUser(UserVO vo) {
        dao.updateUser(vo);
    }
    public void deleteUser(String uid) {
        dao.deleteUser(uid);
    }

}
