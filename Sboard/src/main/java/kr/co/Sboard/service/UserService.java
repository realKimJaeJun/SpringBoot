package kr.co.Sboard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kr.co.Sboard.dao.UserDAO;
import kr.co.Sboard.repository.UserRepo;
import kr.co.Sboard.vo.TermsVO;
import kr.co.Sboard.vo.UserVO;

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
	public TermsVO selectTerms() {
		return dao.selectTerms();
	}
	
	/**
	 * 유저 회원가입
	 * @param vo
	 * @return
	 */
	public int insertUser(UserVO vo) {
		vo.setPass(encoder.encode(vo.getPass1()));
		return dao.insertUser(vo);
	}
	
	/**
	 * 가입 유저 아이디 불러오기
	 * @param uid
	 * @return
	 */
	public UserVO selectUser(String uid) {
		return dao.selectUser(uid);
	}
	
	public List<UserVO> selectUsers() {
		return dao.selectUsers();
	}
	
	/**
	 * 유저 정보 변경
	 * @param vo
	 * @return
	 */
	public int updateUser(UserVO vo) {
		return dao.updateUser(vo);
	}
	
	/**
	 * 유저 탈퇴
	 * @param uid
	 * @return
	 */
	public int deleteUser(String uid) {
		return dao.deleteUser(uid);
	}
	
	/**
	 * 중복 체크를 위한 DB의 count
	 * @param uid
	 * @return
	 */
	public int countByUid(String uid) {
		return repo.countByUid(uid);
	}
	
	public int countByNick(String nick) {
		return repo.countByNick(nick);
	}
	
	public int countByHp(String hp) {
		return repo.countByHp(hp);
	}
}
