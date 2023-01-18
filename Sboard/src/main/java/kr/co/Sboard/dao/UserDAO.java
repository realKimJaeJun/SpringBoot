package kr.co.Sboard.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.Sboard.vo.TermsVO;
import kr.co.Sboard.vo.UserVO;


@Mapper
@Repository
public interface UserDAO {
	
	/**
	 * 유저 회원가입
	 * @param vo
	 * @return
	 */
	public int insertUser(UserVO vo);
	
	/**
	 * 가입 유저 아이디 불러오기
	 * @param uid
	 * @return
	 */
	public UserVO selectUser(String uid);
	
	public List<UserVO> selectUsers();
	/**
	 * 유저 정보 변경하기
	 * @param vo
	 * @return
	 */
	public int updateUser(UserVO vo);
	
	/**
	 * 유저 탈퇴
	 * @param uid
	 * @return
	 */
	public int deleteUser(String uid);
	
	/**
	 * 가입 약관 불러오기
	 * @return
	 */
	public TermsVO selectTerms();
}
