package kr.co.Sboard.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.Sboard.vo.ArticleVO;

@Mapper
@Repository
public interface ArticleDAO {
	
	public List<ArticleVO> selectArticles();

	public ArticleVO selectArticle(int no);
	
	public void writeArticle(ArticleVO vo);
	
	public void modifyArticle(ArticleVO vo);
	
	public void deleteArticle(int no);
	
}
