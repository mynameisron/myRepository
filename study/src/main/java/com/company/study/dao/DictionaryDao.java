package com.company.study.dao;
 
import java.util.List;
import java.util.Map;
 
import com.company.study.domain.Dictionary;
 
public interface DictionaryDao {
     
    int regContent(Map<String, Object> paramMap);
     
    int getContentCnt(Map<String, Object> paramMap);
     
    List<Dictionary> getContentList(Map<String, Object> paramMap);
     
    Dictionary getContentView(Map<String, Object> paramMap);
     
    int getDictionaryCheck(Map<String, Object> paramMap);
     
    int delDictionary(Map<String, Object> paramMap);
     
}
