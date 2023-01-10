package kr.co.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.user.vo.User3VO;

@Mapper
@Repository
public interface User3DAO {
	
	public void insertUser3(User3VO vo);
	
	public User3VO selectUser3(String uid);
	
	public List<User3VO> selectUser3s();
	
	public void updateUser3(User3VO vo);
	
	public void deleteUser3(String uid);
}
