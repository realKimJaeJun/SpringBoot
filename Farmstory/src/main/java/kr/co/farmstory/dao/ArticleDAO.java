package kr.co.farmstory.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ArticleDAO {

    public List<ArticleDAO> selectArticles(int start);

    public int
}
