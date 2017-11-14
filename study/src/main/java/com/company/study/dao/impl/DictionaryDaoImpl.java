package com.company.study.dao.impl;
 
import java.util.List;
import java.util.Map;
 
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
 
import com.company.study.dao.DictionaryDao;
import com.company.study.domain.Dictionary;

@Repository("dictoinaryDao")
public class DictionaryDaoImpl implements DictionaryDao{
     
    @Autowired
    private SqlSession sqlSession;
  
    public void setSqlSession(SqlSession sqlSession){
        this.sqlSession = sqlSession;
    }
     
    @Override
    public int regContent(Map<String, Object> paramMap) {
        return sqlSession.insert("insertContent", paramMap);
    }
 
    @Override
    public int getContentCnt(Map<String, Object> paramMap) {
        return sqlSession.selectOne("selectContentCnt", paramMap);
    }
     
    @Override
    public List<Dictionary> getContentList(Map<String, Object> paramMap) {
        return sqlSession.selectList("selectContent", paramMap);
    }
 
    @Override
    public Dictionary getContentView(Map<String, Object> paramMap) {
        return sqlSession.selectOne("selectContentView", paramMap);
    }
 
    @Override
    public int getDictionaryCheck(Map<String, Object> paramMap) {
        return sqlSession.selectOne("selectDictionaryCnt", paramMap);
    }
 
    @Override
    public int delDictionary(Map<String, Object> paramMap) {
        return sqlSession.delete("deleteDictionary", paramMap);
    }
 
}
