package kr.co.voard.dao;

import kr.co.voard.vo.TermsVO;
import kr.co.voard.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserDAO {

    public void insertUser(UserVO vo);
    public TermsVO selectTerms();
    public UserVO selectUser(String uid);
    public List<UserVO> selectUsers();
    public void updateUser(UserVO vo);
    public void deleteUser(String uid);
}
