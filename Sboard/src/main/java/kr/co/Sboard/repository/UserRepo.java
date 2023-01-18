package kr.co.Sboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.Sboard.entity.UserEntity;

public interface UserRepo extends JpaRepository<UserEntity, String>{
	
	public int countByUid(String uid);
	public int countByNick(String nick);
	public int countByHp(String hp);

}
