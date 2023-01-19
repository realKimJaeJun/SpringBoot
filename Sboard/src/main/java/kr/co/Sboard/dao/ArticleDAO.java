package kr.co.Sboard.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.Sboard.vo.ArticleVO;
import kr.co.Sboard.vo.FileVO;

@Mapper
@Repository
public interface ArticleDAO {
	
	public List<ArticleVO> selectArticles(int start);

	public int selectCountTotal();
	
	public ArticleVO selectArticle(int no);
	
	public int insertArticle(ArticleVO vo);
	
	public int updateArticle(ArticleVO vo);
	
	public int deleteArticle(int no);
	
	public int insertFile(FileVO vo);
	
}
