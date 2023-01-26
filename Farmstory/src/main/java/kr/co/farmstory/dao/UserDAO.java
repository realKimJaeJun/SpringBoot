package kr.co.farmstory.dao;

import kr.co.farmstory.vo.TermsVO;
import kr.co.farmstory.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserDAO {
    /**
     * 가입 약관 불러오기
     * @return
     */
    public TermsVO selectTerms();

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
}
