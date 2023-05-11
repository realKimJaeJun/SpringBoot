package kr.co.voard.repository;

import kr.co.voard.entity.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepo extends JpaRepository<ArticleEntity, Integer> {
}
