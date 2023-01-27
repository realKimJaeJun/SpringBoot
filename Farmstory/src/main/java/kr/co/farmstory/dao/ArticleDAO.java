package kr.co.farmstory.dao;

import kr.co.farmstory.vo.ArticleVO;
import kr.co.farmstory.vo.FileVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ArticleDAO {

    public List<ArticleVO> selectArticles(int start);

    public ArticleVO selectArticle(int no);

    public int insertArticle(ArticleVO vo);

    public int updateArticle(ArticleVO vo);

    public int deleteArticle(int no);

    public int selectCountTotal();

    public int insertFile(FileVO vo);

    public FileVO selectFile(int fno);

    public int updateDownload(int fno);

}
