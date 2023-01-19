package kr.co.Sboard;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.co.Sboard.dao.UserDAO;
import kr.co.Sboard.repository.UserRepo;
import kr.co.Sboard.service.UserService;
import kr.co.Sboard.vo.UserVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class SboardApplicationTests {

	
	void contextLoads() {
	}
	
	@Autowired
	private UserDAO dao;
	
	@Autowired
	private UserService service;
	
	@Autowired
	private UserRepo repo;
	
	
	public void insertTest() {
		UserVO vo = UserVO.builder()
						.uid("testman12")
						.pass1("1234")
						.name("김모평")
						.nick("MopyeongKim")
						.email("testman12@gmail.com")
						.hp("010-2222-2221")
						.zip("53820")
						.addr1("영국")
						.addr2("맨체스터")
						.regip("0:0:0:0:0:0:0:1")
						.build();
		
		int result = service.insertUser(vo);
		log.info("result :"+result);
	}
	
	
	public void countUid() {
		
		int result = repo.countByUid("testman1");
		log.info("result : "+result);
	}
	
	

}
