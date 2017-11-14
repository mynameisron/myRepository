package com.company.study.service.impl;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
 
import javax.annotation.Resource;
 
import org.springframework.stereotype.Service;
 
import com.company.study.dao.DictionaryDao;
import com.company.study.domain.Dictionary;
import com.company.study.service.DictionaryService;
 
@Service("dictionaryService")
public class DictionaryServiceImpl implements DictionaryService {
 
    @Resource(name="dictionaryDao")
    private DictionaryDao dictionaryDao;
 
    @Override
    public int regContent(Map<String, Object> paramMap) {
        return dictionaryDao.regContent(paramMap);
    }
 
    @Override
    public int getContentCnt(Map<String, Object> paramMap) {
        return dictionaryDao.getContentCnt(paramMap);
    }
 
    @Override
    public List<Dictionary> getContentList(Map<String, Object> paramMap) {
        return dictionaryDao.getContentList(paramMap);
    }
 
    @Override
    public Dictionary getContentView(Map<String, Object> paramMap) {
        return dictionaryDao.getContentView(paramMap);
    }
 
    @Override
    public int getDictionaryCheck(Map<String, Object> paramMap) {
        return dictionaryDao.getDictionaryCheck(paramMap);
    }
 
    @Override
    public int delDictionary(Map<String, Object> paramMap) {
        return dictionaryDao.delDictionary(paramMap);
    }
 
}
