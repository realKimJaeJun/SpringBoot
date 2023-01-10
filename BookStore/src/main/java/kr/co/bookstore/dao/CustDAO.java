package kr.co.bookstore.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.bookstore.vo.CustVO;

@Repository
public class CustDAO {
	
	@Autowired
	private SqlSessionTemplate mybatis;
	
	public void insertCust(CustVO vo) {
		mybatis.insert("cust.insertCust", vo);
	}
	
	public CustVO selectCust(int custId) {
		return mybatis.selectOne("cust.selectCust", custId);
	}
	
	public List<CustVO> selectCusts() {
		return mybatis.selectList("cust.selectCusts");
	}
	
	public void updateCust(CustVO vo) {
		mybatis.update("cust.updateCust", vo);
	}
	
	public void deleteCust(int custId) {
		mybatis.delete("cust.deleteCust", custId);
	}
}
