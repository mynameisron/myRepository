package com.company.study.dao.impl;
 
import java.util.List;
import java.util.Map;
 
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
 
import com.company.study.dao.BoardDao;
import com.company.study.domain.Board;
import com.company.study.domain.BoardReply;
 
@Repository("boardDao")
public class BoardDaoImpl implements BoardDao{
     
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
    public List<Board> getContentList(Map<String, Object> paramMap) {
        return sqlSession.selectList("selectContent", paramMap);
    }
 
    @Override
    public Board getContentView(Map<String, Object> paramMap) {
        return sqlSession.selectOne("selectContentView", paramMap);
    }
 
    @Override
    public int regReply(Map<String, Object> paramMap) {
        return sqlSession.insert("insertBoardReply", paramMap);
    }
 
    @Override
    public List<BoardReply> getReplyList(Map<String, Object> paramMap) {
        return sqlSession.selectList("selectBoardReplyList", paramMap);
    }
 
    @Override
    public int delReply(Map<String, Object> paramMap) {
        return sqlSession.delete("deleteBoardReply", paramMap);
    }
 
    @Override
    public int getBoardCheck(Map<String, Object> paramMap) {
        return sqlSession.selectOne("selectBoardCnt", paramMap);
    }
 
    @Override
    public int delBoard(Map<String, Object> paramMap) {
        return sqlSession.delete("deleteBoard", paramMap);
    }
 
}
