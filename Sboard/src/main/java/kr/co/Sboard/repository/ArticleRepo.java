package kr.co.Sboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.Sboard.entity.ArticleEntity;

public interface ArticleRepo extends JpaRepository<ArticleEntity, Integer>{
	
}
